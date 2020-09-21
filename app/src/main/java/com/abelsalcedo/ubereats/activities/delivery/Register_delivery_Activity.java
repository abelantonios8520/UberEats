package com.abelsalcedo.ubereats.activities.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.abelsalcedo.ubereats.R;
import com.abelsalcedo.ubereats.includes.MyToolbar;
import com.abelsalcedo.ubereats.models.Delivery;
import com.abelsalcedo.ubereats.providers.AuthProvider;
import com.abelsalcedo.ubereats.providers.DeliveryProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class Register_delivery_Activity extends AppCompatActivity {

    AuthProvider mAuthProvider;
    DeliveryProvider mDeliveryProvider;

    // VIEWS
    Button mButtonRegister;
    TextInputEditText mTextInputNam;
    TextInputEditText mTextInputAp;
    TextInputEditText mTextInputDni;
    TextInputEditText mTextInputTel;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_delivery_);

        MyToolbar.show(this, "Registro de Emprendedor", true);

        mAuthProvider = new AuthProvider();
        mDeliveryProvider = new DeliveryProvider();

        mDialog = new SpotsDialog.Builder().setContext(Register_delivery_Activity.this).setMessage("Espere un momento").build();

        mButtonRegister = findViewById(R.id.btnRegister);
        mTextInputNam = findViewById(R.id.textInputNam);
        mTextInputAp = findViewById(R.id.textInputAp);
        mTextInputDni = findViewById(R.id.textInputDni);
        mTextInputTel = findViewById(R.id.textInputTelef);
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickRegister();
            }
        });
    }

    void clickRegister() {
        final String name = mTextInputNam.getText().toString();
        final String ape = mTextInputAp.getText().toString();
        final String dni = mTextInputDni.getText().toString();
        final String telf = mTextInputTel.getText().toString();
        final String email = mTextInputEmail.getText().toString();
        final String password = mTextInputPassword.getText().toString();

        if (!name.isEmpty() && !ape.isEmpty() && !dni.isEmpty() && !telf.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 6) {
                mDialog.show();
                register(name, ape, dni, telf, email, password);
            }
            else {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    void register(final String name, final String ape, final String dni, final String telef, final String email, String password) {
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if (task.isSuccessful()) {
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Delivery delivery = new Delivery(id, name, ape, dni, telef, email);
                    create(delivery);
                }
                else {
                    Toast.makeText(Register_delivery_Activity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void create(Delivery delivery) {
        mDeliveryProvider.create(delivery).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(RegisterDriverActivity.this, "El registro se realizo exitosamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register_delivery_Activity.this, MapDeliveryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(Register_delivery_Activity.this, "El registro se realizo exitosamente", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Register_delivery_Activity.this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}