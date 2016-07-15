package com.josue.android.subnetsupernetcalc.models;

/**
 * Created by Josue on 07/07/2016.
 */
public class Network {
    private NumberIp first_octet;
    private NumberIp second_octet;
    private NumberIp third_octet;
    private NumberIp fourth_octet;

    private String network_address = "";
    private String broadcast = "" ;
    private String first_address = "";
    private String last_address = "";
    private String maximun_address = "";
    private String maximun_host = "";
    private String network_bits = "";
    private String host_bits = "";

    private Netmask netmask;
    private int seccion_net;
    private int range;

    private static final int AUXRANGE = 256;
    private static final int SIZE_MASK = 32;
    private static final double BASE = 2;


    public Network(int firstInput, int secondInput, int thirdInput, int fourthInput, int inputMask) {
        first_octet = new NumberIp(firstInput);
        second_octet = new NumberIp(secondInput);
        third_octet = new NumberIp(thirdInput);
        fourth_octet = new NumberIp(fourthInput);
        netmask = new Netmask(inputMask);
    }

    public void calcular() {
        calcRange();
        calcSeccionNetwork();
        calcularSubredes();
    }

    public void calc(){
        System.out.println("Address : " + getIpDecimal());
        System.out.println("Netmask : " + netmask.getMaskDecimal());
        System.out.println("Network : " + network_address);
        System.out.println("Broadcast : " + broadcast);
        System.out.println("first host : " + first_address);
        System.out.println("Last host : " + last_address);
    }


    private void calcSeccionNetwork() {
        switch(netmask.getPosition()) {
            case 1:
                seccion_net = first_octet.getNumberDecimal();
                break;
            case 2:
                seccion_net = second_octet.getNumberDecimal();
                break;
            case 3:
                seccion_net = third_octet.getNumberDecimal();
                break;
            case 4:
                seccion_net = fourth_octet.getNumberDecimal();
                break;
        }
    }

    private void calcRange(){
        range= AUXRANGE - netmask.getNumberSelection();
    }

    private void calcularSubredes() {
        int position = netmask.getPosition();
        int initial_ip = 0;
        int end_ip = range;
        boolean found = false;

        int first_aux = first_octet.getNumberDecimal();
        int second_aux = second_octet.getNumberDecimal();
        int third_aux = third_octet.getNumberDecimal();
        int fourth_aux = fourth_octet.getNumberDecimal();

        switch(position) {
            case 1:
                while (!found) {
                    if (seccion_net >= initial_ip && seccion_net < end_ip) {
                        first_address = initial_ip+".0.0.1";
                        last_address = (end_ip-1)+".255.255.254";
                        network_address = initial_ip+".0.0.0";
                        broadcast = (end_ip-1)+".255.255.255";
                        found = true;
                    }else {
                        initial_ip = end_ip;
                        end_ip += range;
                    }
                }
                break;

            case 2:
                while (!found) {
                    if (seccion_net >= initial_ip && seccion_net < end_ip) {
                        first_address = first_aux +"."+initial_ip+".0.1";
                        last_address = first_aux +"."+(end_ip-1)+".255.254";
                        network_address = first_aux +"."+initial_ip+".0.0";
                        broadcast = first_aux +"."+(end_ip-1)+".255.255";
                        found = true;
                    }else {
                        initial_ip = end_ip;
                        end_ip += range;
                    }
                }
                break;

            case 3:
                while (!found) {
                    System.out.println(seccion_net);
                    if (seccion_net >= initial_ip && seccion_net < end_ip) {
                        first_address = first_aux + "." + second_aux + "." + initial_ip + ".1";
                        last_address = first_aux + "." + second_aux + "." + (end_ip-1) + ".254";
                        network_address = first_aux + "." + second_aux + "." + initial_ip + ".0";
                        broadcast = first_aux + "." + second_aux + "." + (end_ip-1) + ".255";
                        found = true;
                    }else {
                        initial_ip = end_ip;
                        end_ip += range;
                    }
                }
                break;

            case 4:
                while (!found) {
                    if (seccion_net >= initial_ip && seccion_net < end_ip) {
                        first_address = first_aux + "." + second_aux + "." + third_aux + "." + (initial_ip + 1);
                        last_address = first_aux + "." + second_aux + "." + third_aux + "." + (end_ip-2);
                        network_address = first_aux + "." + second_aux + "." + third_aux + "." + initial_ip;
                        broadcast = first_aux + "." + second_aux + "." + third_aux + "." + (end_ip-1);
                        found = true;
                    }else {
                        initial_ip = end_ip;
                        end_ip += range;
                    }
                }
                break;
        }

    }

    private String getIpDecimal(){
        return first_octet.getNumberDecimal()+"."+second_octet.getNumberDecimal()
                +"."+third_octet.getNumberDecimal()+"."+fourth_octet.getNumberDecimal();
    }

    private String getIpBinary(){
        return first_octet.getNumberBinary()+"."+second_octet.getNumberBinary()
                +"."+third_octet.getNumberBinary()+"."+fourth_octet.getNumberBinary();
    }

    public void changeDates(int input_number1, int input_number2,
                               int input_number3, int input_number4, int input_number5) {
        first_octet.changeNumber(input_number1);
        second_octet.changeNumber(input_number2);
        third_octet.changeNumber(input_number3);
        fourth_octet.changeNumber(input_number4);
        netmask.changeMask(input_number5);
        calcular();
    }

    public String getNetworkAddress() {
        return network_address;
    }

    public String getIpAddress(int tipo) {
        String res = "";
        if (tipo == 1) {
            res = first_octet.getNumberDecimal()+"."+second_octet.getNumberDecimal()
                    +"."+third_octet.getNumberDecimal()+"."+fourth_octet.getNumberDecimal();
        }else{
            res = first_octet.getNumberBinary()+"."+second_octet.getNumberBinary()
                    +"."+third_octet.getNumberBinary()+"."+fourth_octet.getNumberBinary();
        }
        return res;
    }

    public String getNetmask(int tipo) {
        String res = "";
        if (tipo == 1) {
            res = netmask.getMaskDecimal();
        }else {
            res = netmask.getMaskBinary();
        }
        return res;
    }



    public String getBroadcast() {
        return broadcast;
    }

    public String getFirstAddress() {
        return first_address;
    }

    public String getLastAddress() {
        return last_address;
    }

    public String getMaximunAddress() {
        final double exponente = SIZE_MASK - netmask.getMaskSize();
        return (int)Math.pow(BASE, exponente)+"";
    }

    public String getMaximunHost() {
        final double exponente = SIZE_MASK - netmask.getMaskSize();
        return (int)(Math.pow(BASE, exponente)-2)+"";
    }

    public String getNetworkBits() {
        return network_bits;
    }

    public String getHostBits() {
        return host_bits;
    }

    public int getRange() {
        return range;
    }




}
