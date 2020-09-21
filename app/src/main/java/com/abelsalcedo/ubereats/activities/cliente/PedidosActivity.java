package com.abelsalcedo.ubereats.activities.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.abelsalcedo.ubereats.R;
import com.abelsalcedo.ubereats.activities.MainActivity;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class PedidosActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnImgHora;
    private TextView mHora;
    private int hora, minutos;
    private Button mbtnGuardar;
    private CircleImageView mCircleImageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        btnImgHora = findViewById(R.id.ImgbtnHora);
        mHora = findViewById(R.id.txtHora);
        mbtnGuardar = findViewById(R.id.btnGuardar);

        btnImgHora.setOnClickListener(this);
        mCircleImageBack = findViewById(R.id.circleImageBack);

        mCircleImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mbtnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PedidosActivity.this, DetalleEntregaActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        final Calendar h = Calendar.getInstance();
        hora =h.get(Calendar.HOUR_OF_DAY);
        minutos=h.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHora.setText(hourOfDay+":"+minute);
            }
        }, hora, minutos,false);
        timePickerDialog.show();
    }
}