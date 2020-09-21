package com.abelsalcedo.ubereats.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abelsalcedo.ubereats.R
import com.abelsalcedo.ubereats.activities.cliente.MapClienteActivity
import com.abelsalcedo.ubereats.activities.delivery.MapDeliveryActivity
import com.abelsalcedo.ubereats.includes.MyToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dmax.dialog.SpotsDialog

class loginActivity : AppCompatActivity() {
    var mTextInputEmail: TextInputEditText? = null
    var mTextInputPassword: TextInputEditText? = null
    var mButtonLogin: Button? = null
    var mAuth: FirebaseAuth? = null
    var mDatabase: DatabaseReference? = null
    var mDialog: AlertDialog? = null
    var mPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        MyToolbar.show(this, "Login de usuario", true)
        mTextInputEmail = findViewById(R.id.textInputEmail)
        mTextInputPassword = findViewById(R.id.textInputPassword)
        mButtonLogin = findViewById(R.id.btnLogin)
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
        mDialog = SpotsDialog.Builder().setContext(this@loginActivity).setMessage("Espere un momento").build()
        mPref = applicationContext.getSharedPreferences("typeUser", Context.MODE_PRIVATE)
        mButtonLogin?.run { setOnClickListener(View.OnClickListener { login() }) }

    }

    private fun login() {
        val email = mTextInputEmail!!.text.toString()
        val password = mTextInputPassword!!.text.toString()
        if (!email.isEmpty() && !password.isEmpty()) {
            if (password.length >= 6) {
                mDialog!!.show()
                mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = mPref!!.getString("user", "")
                        if (user == "cliente") {
                            mDatabase!!.child("Users").child("Clientes").child(mAuth!!.uid!!).addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        //mostarToastDown("ATENCION", "Este no es un usuario permitido");
                                        Toast.makeText(this@loginActivity, "Registro exitoso cliente", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this@loginActivity, MapClienteActivity::class.java)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        startActivity(intent)
                                    } else {
                                        //mostarToastDown("ATENCION", "Este no es un usuario permitido");
                                        Toast.makeText(this@loginActivity, "No es un usuario permitido", Toast.LENGTH_SHORT).show()
                                        mAuth!!.signOut()
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {}
                            })
                        } else {
                            mDatabase!!.child("Users").child("Deliverys").child(mAuth!!.uid!!).addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        Toast.makeText(this@loginActivity, "Registro exitoso delivery", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this@loginActivity, MapDeliveryActivity::class.java)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(this@loginActivity, "No es un usuario permitido", Toast.LENGTH_SHORT).show()
                                        mAuth!!.signOut()
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {}
                            })
                        }
                    } else {
                        Toast.makeText(this@loginActivity, "La contraseña o el password son incorrectos", Toast.LENGTH_SHORT).show()
                    }
                    mDialog!!.dismiss()
                }
            } else {
                Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "La contraseña y el email son obligatorios", Toast.LENGTH_SHORT).show()
        }
    }

    fun forgotPassword(view:View){
        startActivity(Intent(this,ForgotPassActivity::class.java))
    }
}