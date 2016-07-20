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
import android.widget.SeekBar;
import android.widget.Spinner;
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
    @Bind(R.id.size_mask)
    SeekBar size_mask;

    @Bind(R.id.calcRecyclerView)
    RecyclerView recyclerLaView;

    ListCalcAdapter adapter;

    int first;
    int second;
    int thrird;
    int fourth;
    int mask;

    Network network;
    String clase = null;

    private final int EXAMPLE_FIRST = 192;
    private final int EXAMPLE_SECOND = 168;
    private final int EXAMPLE_THIRD = 2;
    private final int EXAMPLE_FOURTH = 12;
    private final int EXAMPLE_NETMASK = 24;

    int mask_default_a = 8;
    int mask_default_b = 16;
    int mask_default_c = 24;

    private static final String ID_NETMASK = "NETMASK";

    private  static final  String [] MASK_A = {"8", "9", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    private  static final  String [] MASK_B = {"16", "17", "18", "19", "20", "21", "22", "23",
            "24", "25", "26", "27", "28", "29", "30"};
    private  static final  String [] MASK_C = {"24", "25", "26", "27", "28", "29", "30"};
    int stop = 0;
    int increase = 0;
    int stop_despues = 0;



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
        clase = "C";

        size_mask.setProgress(0);
        size_mask.setMax(6);

        network = new Network(first, second, thrird, fourth, mask);
        network.calcular();

    }

    private void sizeControl() {

        firstOctet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                largerControl(fourthOctet);
            }

            @Override
            public void afterTextChanged(Editable s) {
                equalControl(fourthOctet, 4);
                hideKeyboardTV();
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

                }
            }
        });

        size_mask.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (clase) {
                    case "A":
                        netmask.setText(MASK_A[progress]);
                    break;
                    case "B":
                        netmask.setText(MASK_B[progress]);
                    break;
                    case "C":
                        netmask.setText(MASK_C[progress]);
                    break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    private void reiniciarTamanio(int max) {
        size_mask.setMax(max);
        size_mask.setProgress(0);

    }

    private void equalControl(TextInputEditText element, int position) {
        int size = element.length();
        String input = element.getText().toString().trim();
        changeValues(position, size, input);
        if (size == 3) {
            validateInput(element);
            switch (position) {
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
            switch (position) {
                case 1:
                    first = aux;
                    controlClase();
                    break;
                case 2:
                    second = aux;
                    break;
                case 3:
                    thrird = aux;
                    break;
                case 4:
                    fourth = aux;
                    break;
                case 5:
                    mask = aux;
                    cambiarClase();
                    activarErrorNetmask();
                    break;
            }
        } else {
            switch (position) {
                case 1:
                    first = EXAMPLE_FIRST;
                    controlClase();
                    /*firstOctet.setError("Error campo vacío");
                    first = -1;*/
                    break;
                case 2:
                    second = EXAMPLE_SECOND;
                    /*secondOctet.setError("Error campo vacío");
                    second = -1;*/
                    break;
                case 3:
                    thrird = EXAMPLE_THIRD;
                    /*thirdOctet.setError("Error campo vacío");
                    thrird = -1;*/
                    break;
                case 4:
                    fourth = EXAMPLE_FOURTH;
                    /*fourthOctet.setError("Error campo vacío");
                    fourth = -1;*/
                    break;
                case 5:
                    /*netmask.setError("Error campo vacío");
                    mask = -1;*/
                    break;
            }

        }

    }

    private void cambiarClase() {
        netmask.refreshDrawableState();
    }


    private void controlClase() {
        if (first >= 0 && first <= 127) {
            clase = "A";
            reiniciarTamanio(22);
            ponerValorDefecto();
        } else {
            if (first >= 128 && first <= 191) {
                clase = "B";
                reiniciarTamanio(14);
                ponerValorDefecto();
            } else {
                if (first >= 192 && first <= 223) {
                    clase = "C";
                    reiniciarTamanio(6);
                    ponerValorDefecto();
                } else {
                    if (first >= 224 && first <= 239) {
                        clase = "D";
                        ponerValorDefecto();
                    } else {
                        if (first >= 240 && first <= 255) {
                            clase = "E";
                            ponerValorDefecto();
                        }
                    }
                }
            }
        }
    }

    private void ponerValorDefecto() {
        Toast.makeText(getActivity(), clase, Toast.LENGTH_SHORT).show();
        switch (clase) {
            case "A":
                if(!(mask >= 8 && mask <=30)){
                    netmask.setText("8");
                }
            break;
            case "B":
                if(!(mask >= 16 && mask <=30)){
                    netmask.setText("16");
                }
            break;
            case "C":
                if(!(mask >= 24 && mask <=30)){
                    netmask.setText("24");
                }
            break;
            default:

                break;
        }
    }

    private void largerControl(TextInputEditText element) {
        int size = element.length();
        if (size > 3) {
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


    private void activarErrorNetmask() {
        int input = mask;
        if (first >= 0 && first <= 127) {
            if ((input < 8) || (input > 30)) {
                netmask.setError("N >= 8 y N <= 30");
            }
        } else {
            if (first >= 128 && first <= 191) {
                if ((input < 16) || (input > 30)) {
                    netmask.setError("N >= 8 y N <= 30");
                }
            } else {
                if (first >= 192 && first <= 223) {
                    if ((input < 24) || (input > 30)) {
                        netmask.setError("N >= 24 y N <= 30");
                    }
                }
            }
        }
    }

    private boolean existsError() {
        boolean opcion1 = ((first != -1) && (second != -1) && (thrird != -1) && (fourth != -1) && (mask != -1) && (mask != -1));
        boolean opcion2 = ((first <= 255) && (second <= 255) && (thrird <= 255) && (fourth <= 255) && ((mask >= 8) && (mask <= 30)));
        boolean opcion3 = false;
        switch (clase) {
            case "A":
                opcion3 = (mask >=8 && mask <= 30);
                break;
            case "B":
                opcion3 = (mask >=16 && mask <= 30);
                break;
            case "C":
                opcion3 = (mask >=24 && mask <= 30);
                break;
        }

        return ((opcion1 && opcion2) && opcion3);
    }

    public void addElement() {
        if(!esClaseReservada()) {
            if (existsError()) {
                network.changeDates(first, second, thrird, fourth, mask);
                network.calcular();
                adapter.DeleteElement();
                adapter.addElement(network.getNetwork());
                adapter.addElement(network.getAddress(1));
                adapter.addElement(network.getNetmask(1));
                adapter.addElement(network.getBroadcast());
                adapter.addElement(network.getFirstLastHost());
                //adapter.addElement(network.getLastAddress());
                //adapter.addElement(network.getMaximunAddress());
                //adapter.addElement(network.getMaximunHost());
            } else {
                Toast.makeText(getActivity(), "Existe errores por solucionar", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private boolean esClaseReservada() {
        boolean resp = false;
        switch (clase) {
            case "D":
                resp = true;
                Toast.makeText(getActivity(), "MENSAJE CLASE D", Toast.LENGTH_SHORT).show();
                break;
            case "E":
                resp = true;
                Toast.makeText(getActivity(), "MENSAJE CLASE E", Toast.LENGTH_SHORT).show();
                break;
            default:
                resp = false;
                break;
        }
        return resp;
    }

    @Override
    public void onItemCLick(String item, int position) {
        Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
        intent.putExtra("DATO", item);
        intent.putExtra("POSITION", position);
        //intent.putExtra(ID_NETMASK, network.getIdMask()+"");
        intent.putExtra("ONE", first);
        intent.putExtra("TWO", second);
        intent.putExtra("THREE", thrird);
        intent.putExtra("FOUR", fourth);
        //intent.putExtra("SELECTED",network.getPositionSelected());
        Toast.makeText(getActivity(), network.getNumberSubred() + "", Toast.LENGTH_SHORT).show();
        intent.putExtra("SIZE_MASK", network.getNumberSubred());

        startActivity(intent);
    }
}
