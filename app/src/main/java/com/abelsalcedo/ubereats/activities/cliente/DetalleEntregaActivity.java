package com.abelsalcedo.ubereats.activities.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.abelsalcedo.ubereats.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetalleEntregaActivity extends AppCompatActivity {

    private CircleImageView mCircleImageBack;
    private Button mGuardarDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_entrega);

        mCircleImageBack = findViewById(R.id.circleImageBack);
        mGuardarDetalle = findViewById(R.id.btnGuardarDetalle);

        mCircleImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mGuardarDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleEntregaActivity.this, MapClienteActivity.class);
                startActivity(intent);
            }
        });
    }
}