package com.abelsalcedo.ubereats.activities.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.abelsalcedo.ubereats.R;
import com.abelsalcedo.ubereats.includes.MyToolbar;
import com.abelsalcedo.ubereats.models.Cliente;
import com.abelsalcedo.ubereats.providers.AuthProvider;
import com.abelsalcedo.ubereats.providers.ClienteProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {
    AuthProvider mAuthProvider;
    ClienteProvider mClienteProvider;

    // VIEWS
    Button mButtonRegister;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputPassword;
    TextInputEditText mTextInputApe;
    TextInputEditText mTextInputTel;
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        MyToolbar.show(this, "Registro de usuario", true);

        mAuthProvider = new AuthProvider();
        mClienteProvider = new ClienteProvider();

        mDialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).setMessage("Espere un momento").build();


        mButtonRegister = findViewById(R.id.btnRegister);
        mTextInputName = findViewById(R.id.textInputName);
        mTextInputApe = findViewById(R.id.textInputApe);
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
        final String name = mTextInputName.getText().toString();
        final String ape = mTextInputApe.getText().toString();
        final String telef = mTextInputTel.getText().toString();
        final String email = mTextInputEmail.getText().toString();
        final String password = mTextInputPassword.getText().toString();


        if (!name.isEmpty() && !ape.isEmpty() && !telef.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 6) {
                mDialog.show();
                register(name, ape, telef, email, password);
            } else {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    void register(final String name, final String ape, final String telef, final String email, String password) {
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if (task.isSuccessful()) {
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Cliente cliente = new Cliente(id, name, ape, telef, email);
                    create(cliente);
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void create(Cliente cliente) {
        mClienteProvider.create(cliente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, MapClienteActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}