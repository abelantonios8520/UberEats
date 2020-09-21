package com.abelsalcedo.ubereats.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.abelsalcedo.ubereats.activities.cliente.MapClienteActivity;
import com.abelsalcedo.ubereats.activities.cliente.RegisterActivity;
import com.abelsalcedo.ubereats.activities.delivery.MapDeliveryActivity;
import com.abelsalcedo.ubereats.activities.delivery.Register_delivery_Activity;
import com.abelsalcedo.ubereats.includes.MyToolbar;
import com.abelsalcedo.ubereats.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button mButtonIAmCliente;
    Button mButtonIAmColab;

    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        final SharedPreferences.Editor editor = mPref.edit();


        mButtonIAmCliente = findViewById(R.id.btnIAmCliente);
        mButtonIAmColab = findViewById(R.id.btnIAmColab);

        mButtonIAmCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user", "cliente");
                editor.apply();
                goToSelectAuth();
            }
        });
        mButtonIAmColab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user", "delivery");
                editor.apply();
                goToSelectAuth();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String user = mPref.getString("user", "");
            if (user.equals("cliente")) {
                Intent intent = new Intent(MainActivity.this, MapClienteActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(MainActivity.this, MapDeliveryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }

    private void goToSelectAuth() {
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthActivity.class);
        startActivity(intent);
    }
}
