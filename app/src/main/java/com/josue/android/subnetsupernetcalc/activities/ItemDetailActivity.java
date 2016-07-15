package com.josue.android.subnetsupernetcalc.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.josue.android.subnetsupernetcalc.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemDetailActivity extends AppCompatActivity {
    @Bind(R.id.titleDetail)
    TextView titleDetail;
    @Bind(R.id.txtInput)
    TextView textInput;
    @Bind(R.id.txtProcedimiento)
    TextView txtProcedimiento;
    @Bind(R.id.secondFloatbtn)
    FloatingActionButton floatBtn;

    private static final String TITLE_NETWORK = "Calculo Network";
    private static final String TITLE_NETMASK = "Calculo Netmask";
    private static final String TITLE_BROADCAST = "Calculo Broadcast";
    private static final String TITLE_FIRST = "Calculo Primera Direccion";
    private static final String TITLE_LAST = "Calculo Ultima Direccion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String dato = intent.getStringExtra("DATO");
        int position = intent.getIntExtra("POSITION", 1);
        Toast.makeText(this, position+"", Toast.LENGTH_SHORT).show();
        textInput.setText(dato);
        controlMostrar(position);

    }


    @OnClick(R.id.secondFloatbtn)
    public void handleClickBnt() {
        finish();
    }

    private void controlMostrar(int position) {
        switch (position) {
            case 0:
                titleDetail.setText(TITLE_NETWORK);
                showProceduresNetwork();
                break;
            case 1:
                titleDetail.setText(TITLE_NETMASK);
                break;
            case 2:
                titleDetail.setText(TITLE_BROADCAST);
                break;
            case 3:
                titleDetail.setText(TITLE_FIRST);
                break;
            case 4:
                titleDetail.setText(TITLE_LAST);
                break;
            default:
                titleDetail.setText("defaul");
                break;
        }
    }

    private void showProceduresNetwork() {
        txtProcedimiento.setText(
                "204.17.5.0\n" +
                "204.17.5.32\n"+
                "204.17.5.64\n" +
                "204.17.5.96\n"+
                "204.17.5.128\n" +
                "204.17.5.160\n"+
                "204.17.5.192\n" +
                "204.17.5.224"
        );
    }

}
