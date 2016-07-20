package com.josue.android.subnetsupernetcalc.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.josue.android.subnetsupernetcalc.R;
import com.josue.android.subnetsupernetcalc.models.Network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewGroupDetail)
    LinearLayout viewGroupDetail;

    @Bind(R.id.subtitle0)
    TextView subtitle0;
    @Bind(R.id.subtitle1)
    TextView subtitle1;
    @Bind(R.id.subtitle2)
    TextView subtitle2;

    @Bind(R.id.containerView0)
    LinearLayout containerView0;
    @Bind(R.id.containerView1)
    LinearLayout containerView1;
    @Bind(R.id.containerView2)
    LinearLayout containerView2;

    @Bind(R.id.titleDetail)
    TextView titleDetail;
    @Bind(R.id.txtInput)
    TextView textInput;

    @Bind(R.id.secondFloatbtn)
    FloatingActionButton floatBtn;

    Intent intent;
    String  input;
    Network network;

    ArrayList<TextView> listView ;
    ArrayList<TextView> listSubred ;

    int one ;
    int two ;
    int three ;
    int four ;
    int mask ;

    int position_selected;

    String [] default_mask = {"0", "128", "192", "224", "240", "248", "252", "254", "255"};

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

        intent = getIntent();
        input = intent.getStringExtra("DATO");
        int position = intent.getIntExtra("POSITION", 1);

        one = intent.getIntExtra("ONE", 1);
        two = intent.getIntExtra("TWO", 1);
        three = intent.getIntExtra("THREE", 1);
        four = intent.getIntExtra("FOUR", 1);
        position_selected = intent.getIntExtra("SELECTED", 1);

        mask = intent.getIntExtra("SIZE_MASK", 1);

        textInput.setText(input);

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

        listView = new ArrayList<TextView>();

        String dato = intent.getStringExtra("NETMASK");
        LinearLayout . LayoutParams  params  =  new  LinearLayout . LayoutParams (
                containerView0.getLayoutParams().WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT );
        params . weight =  1 ;

        subtitle0.setText("Valores de una mascara");
        for (int i = 0; i < default_mask.length; i++) {
            listView.add(new TextView(this));
            listView.get(i).setLayoutParams(params);

            if (default_mask[i].equals(dato)){
                listView.get(i).setText(default_mask[i]+", ");
                listView.get(i).setTextColor(listView.get(i).getContext()
                                                .getResources().getColor(R.color.colorAzul));
                containerView0.addView(listView.get(i));
            }else{
                if (i == 8) {
                    listView.get(i).setText(default_mask[i]);
                    containerView0.addView(listView.get(i));
                }else {
                    listView.get(i).setText(default_mask[i]+", ");
                    containerView0.addView(listView.get(i));
                }
            }
        }

        subtitle1.setText("Calculo del tamaño de las subareas");
        TextView contSize = new TextView(this);
        contSize.setLayoutParams(params);
        int aux = 256 - Integer.parseInt(dato);
        contSize.setText("Tamaño   =     256");

        TextView contMask = new TextView(this);
        contMask.setLayoutParams(params);
        contMask.setText("-       "+dato);
        contMask.setTextColor(contMask.getContext()
                .getResources().getColor(R.color.colorAzul));

        TextView contRes = new TextView(this);
        contRes.setLayoutParams(params);
        contRes.setText("=    "+aux);

        containerView1.addView(contSize);
        containerView1.addView(contMask);
        containerView1.addView(contRes);



        listSubred = new ArrayList<TextView>();
        subtitle2.setText("Calculo de las subredes");




        String aux_sebred = "";

        int aux_contador = 0;
        for (int i = 0; i < mask; i++) {
            listSubred.add(new TextView(this));
            listSubred.get(i).setLayoutParams(params);
            switch (position_selected){
                case 1:
                    aux_sebred = aux_contador+ "." + 0 + "." + 0 + "." +0;
                    if (aux_sebred.equals(input)){
                        listSubred.get(i).setText(aux_sebred);
                        listSubred.get(i).setTextSize(20);
                        listSubred.get(i).setTextColor(contMask.getContext()
                                .getResources().getColor(R.color.colorAzul));
                    }else{
                        listSubred.get(i).setText(aux_sebred);
                        listSubred.get(i).setTextSize(20);
                    }
                break;
                case 2:
                    aux_sebred = one + "." + aux_contador + "." + 0 + "." +0;
                    if (aux_sebred.equals(input)){
                        listSubred.get(i).setText(aux_sebred);
                        listSubred.get(i).setTextSize(20);
                        listSubred.get(i).setTextColor(contMask.getContext()
                                .getResources().getColor(R.color.colorAzul));
                    }else{
                        listSubred.get(i).setText(aux_sebred);
                        listSubred.get(i).setTextSize(20);
                    }
                break;
                case 3:
                    aux_sebred = one + "." + two + "." + aux_contador+ "." +0;
                    if (aux_sebred.equals(input)){
                        listSubred.get(i).setText(aux_sebred);
                        listSubred.get(i).setTextSize(20);
                        listSubred.get(i).setTextColor(contMask.getContext()
                                .getResources().getColor(R.color.colorAzul));
                    }else{
                        listSubred.get(i).setText(aux_sebred);
                        listSubred.get(i).setTextSize(20);
                    }
                break;
                case 4:
                    aux_sebred = one + "." + two + "." + three + "." +aux_contador;
                    if (aux_sebred.equals(input)){
                        listSubred.get(i).setText(aux_sebred);
                        listSubred.get(i).setTextSize(20);
                        listSubred.get(i).setTextColor(contMask.getContext()
                                .getResources().getColor(R.color.colorAzul));
                    }else{
                        listSubred.get(i).setText(aux_sebred);
                        listSubred.get(i).setTextSize(20);
                    }
                break;
            }
            containerView2.addView(listSubred.get(i));
            aux_contador += aux;
        }
    }

}
