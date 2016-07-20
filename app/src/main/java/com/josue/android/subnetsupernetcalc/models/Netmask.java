package com.josue.android.subnetsupernetcalc.models;

/**
 * Created by Josue on 07/07/2016.
 */
public class Netmask{
    int size;
    int position;
    String first_size;
    String second_size;
    String third_size;
    String fourth_size;
    NumberIp first_octet;
    NumberIp second_octet;
    NumberIp third_octet;
    NumberIp fourth_octet;
    public Netmask(int size_input) {
        size = size_input;
        calcularMask();
    }

    public void calcularMask() {
        first_size =  "";
        second_size = "";
        third_size = "";
        fourth_size = "";
        darTamanios();
        crearOctetos();
    }

    private void darTamanios() {
        int aux_size = size;
        for (int i = 1; i <=32 ; i++) {
            if(i > 0 && i <= 8) {
                if (aux_size > 0 ) {
                    first_size += 1;
                    aux_size--;
                }else{
                    first_size += 0;
                }
            }

            if(i > 8 && i <= 16) {
                if (aux_size > 0 ) {
                    second_size += 1;
                    aux_size--;
                }else{
                    second_size += 0;
                }
            }

            if(i > 16 && i <= 24) {
                if (aux_size > 0 ) {
                    third_size += 1;
                    aux_size--;
                }else{
                    third_size += 0;
                }
            }

            if(i > 24 && i <= 32) {
                if (aux_size > 0 ) {
                    fourth_size += 1;
                    aux_size--;
                }else{
                    fourth_size += 0;
                }
            }
        }
    }

    private void crearOctetos() {
        first_octet = new NumberIp(first_size);
        second_octet = new NumberIp(second_size);
        third_octet = new NumberIp(third_size);
        fourth_octet = new NumberIp(fourth_size);
    }

    public int getNumberSelection() {
        int resp = 0;
        if (size > 0 && size <= 8) {
            resp = first_octet.getNumberDecimal();
            position = 1;
        }else{
            if (size > 8 && size <= 16) {
                resp = second_octet.getNumberDecimal();
                position = 2;
            }else{
                if (size > 16 && size <= 24) {
                    resp = third_octet.getNumberDecimal();
                    position = 3;
                }else{
                    if (size > 24 && size <= 32) {
                        resp = fourth_octet.getNumberDecimal();
                        position = 4;
                    }else{
                        resp = 0;
                    }
                }
            }
        }

        return resp;
    }

    public int getPosition() {
        return position;
    }

    public String getMaskDecimal() {
        return first_octet.getNumberDecimal()+"."+second_octet.getNumberDecimal()
                +"."+third_octet.getNumberDecimal()+"."+fourth_octet.getNumberDecimal();
    }

    public String getMaskBinary() {
        return first_octet.getNumberBinary()+"."+second_octet.getNumberBinary()
                +"."+third_octet.getNumberBinary()+"."+fourth_octet.getNumberBinary();
    }

    public void changeMask(int input_number) {
        size = input_number;
        calcularMask();
    }
    public int getMaskSize(){
        return size;
    }

}
