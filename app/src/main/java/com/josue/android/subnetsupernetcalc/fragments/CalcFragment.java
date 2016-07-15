package com.josue.android.subnetsupernetcalc.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.josue.android.subnetsupernetcalc.R;
import com.josue.android.subnetsupernetcalc.activities.ItemDetailActivity;
import com.josue.android.subnetsupernetcalc.adapters.ListCalcAdapter;
import com.josue.android.subnetsupernetcalc.adapters.ListCalcListener;
import com.josue.android.subnetsupernetcalc.models.Network;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalcFragment extends Fragment implements ListCalcListener {
    @Bind(R.id.firstOctet)
    TextInputEditText firstOctet;
    @Bind(R.id.secondOctet)
    TextInputEditText secondOctet;
    @Bind(R.id.thirdOctet)
    TextInputEditText thirdOctet;
    @Bind(R.id.fourthOctet)
    TextInputEditText fourthOctet;
    @Bind(R.id.netmask)
    TextInputEditText netmask;
    @Bind(R.id.calcRecyclerView)
    RecyclerView recyclerLaView;

    ListCalcAdapter adapter;

    int first ;
    int second ;
    int thrird ;
    int fourth ;
    int mask ;

    Context context;

    private final int EXAMPLE_FIRST= 192;
    private final int EXAMPLE_SECOND= 168;
    private final int EXAMPLE_THIRD= 2;
    private final int EXAMPLE_FOURTH= 12;
    private final int EXAMPLE_NETMASK = 27;
    //boolean inited = false;
    public CalcFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calc, container, false);
        ButterKnife.bind(this, view);
        InitReclyclerView();
        initValuesDefault();
        sizeControl();
        hideKeyboard();
        return view;
    }

    private void InitReclyclerView() {
        if (adapter == null) {
            adapter = new ListCalcAdapter(this);
        }
        recyclerLaView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerLaView.setAdapter(adapter);
    }

    private void initValuesDefault() {
        first = EXAMPLE_FIRST;
        second = EXAMPLE_SECOND;
        thrird = EXAMPLE_THIRD;
        fourth = EXAMPLE_FOURTH;
        mask = EXAMPLE_NETMASK;
    }

    private void sizeControl() {

        firstOctet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                largerControl(firstOctet);
            }

            @Override
            public void afterTextChanged(Editable s) {
                equalControl(firstOctet, 1);
            }
        });

        secondOctet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                largerControl(secondOctet);
            }

            @Override
            public void afterTextChanged(Editable s) {
                equalControl(secondOctet, 2);
            }
        });

        thirdOctet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                largerControl(thirdOctet);
            }

            @Override
            public void afterTextChanged(Editable s) {
                equalControl(thirdOctet, 3);
            }
        });

        fourthOctet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                largerControl(fourthOctet);
            }

            @Override
            public void afterTextChanged(Editable s) {
                equalControl(fourthOctet, 4);
            }
        });

        netmask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (netmask.length() >= 2) {
                    netmask.selectAll();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activarErrorNetmask(netmask);
                if (netmask.length() > 2) {
                    netmask.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                int size = netmask.length();
                String input = netmask.getText().toString().trim();
                changeValues(5, size, input);
                if (size == 2) {
                    netmask.selectAll();
                    hideKeyboardTV();
                }
            }
        });

    }




    private void equalControl(TextInputEditText element, int position) {
        int size = element.length();
        String input = element.getText().toString().trim();
        changeValues(position, size, input);
        if (size == 3) {
            validateInput(element);
            switch (position){
                case 1:
                    secondOctet.requestFocus();
                    break;
                case 2:
                    thirdOctet.requestFocus();
                    break;
                case 3:
                    fourthOctet.requestFocus();
                    break;
                case 4:
                    netmask.requestFocus();
                    break;
            }
        }
    }

    private void changeValues(int position, int size, String input) {
        if (size > 0) {
            int aux = Integer.parseInt(input);
            switch (position){
                case 1:
                    first = aux;
                    break;
                case 2:
                    second = aux;
                    break;
                case 3:
                    thrird =  aux;
                    break;
                case 4:
                    fourth = aux;
                    break;
                case 5:
                    mask = aux;
                    break;
            }
        }else{
            switch (position){
                case 1:
                    first = EXAMPLE_FIRST;
                    break;
                case 2:
                    second = EXAMPLE_SECOND;
                    break;
                case 3:
                    thrird = EXAMPLE_THIRD;
                    break;
                case 4:
                    fourth = EXAMPLE_FOURTH;
                    break;
                case 5:
                    mask = EXAMPLE_NETMASK;
                    break;
            }
        }

    }

    private void largerControl(TextInputEditText element) {
        int size = element.length();
        if (size > 3 ) {
            element.setText("");
        }

    }

    private void validateInput(TextInputEditText textInputLayout) {
        int input = Integer.parseInt(textInputLayout.getText().toString().trim());
        if (input > 255) {
            textInputLayout.setError("N >= 0 y N <= 255");
        }
    }

    private void hideKeyboard() {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void hideKeyboardTV() {
        InputMethodManager inputMethodManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    inputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException npe) {
            Log.e(getActivity().getLocalClassName(), Log.getStackTraceString(npe));
        }
    }



    private void activarErrorNetmask(TextInputEditText textInputLayout) {
        int size = textInputLayout.length();
        if ( size > 0){
            int input = Integer.parseInt(textInputLayout.getText().toString().trim());
            if ((input < 8) || (input > 30)) {
                textInputLayout.setError("N >= 8 y N <= 30");
            }
        }else{
            changeValues(5, size, textInputLayout.getText().toString().trim());
        }

    }

    public void addElement() {
        if(existsError()){
            Network network = new Network(first, second, thrird, fourth, mask);
            network.calcular();
            adapter.DeleteElement();
            adapter.addElement(network.getNetworkAddress());
            //adapter.addElement(network.getIpAddress(1));
            adapter.addElement(network.getNetmask(1));
            adapter.addElement(network.getBroadcast());
            adapter.addElement(network.getFirstAddress());
            adapter.addElement(network.getLastAddress());
            //adapter.addElement(network.getMaximunAddress());
            adapter.addElement(network.getMaximunHost());
        }else{
            Toast.makeText(getActivity(),"Existe errores por solucionar", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean existsError() {
        return ((first <= 255) && (second <= 255) && (thrird <= 255) && (fourth <= 255) && ((mask >=8) && (mask <= 32)));
    }

    @Override
    public void onItemCLick(String item, int position) {
        Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
        intent.putExtra("DATO", item);
        intent.putExtra("POSITION", position);
        startActivity(intent);
    }
}
