package com.josue.android.subnetsupernetcalc.models;

/**
 * Created by Josue on 07/07/2016.
 */
public class Network {
    private NumberIp first_octet;
    private NumberIp second_octet;
    private NumberIp third_octet;
    private NumberIp fourth_octet;
    private Netmask netmask;

    private String network_address = "";
    private String broadcast = "";
    private String first_address = "";
    private String last_address = "";

    String clase = null;
    int size_clase;
    int seccion;
    int size_subred;
    int size_host;


    private static final int AUXRANGE = 256;
    //private static final int SIZE_MASK = 32;
    private static final double BASE = 2;

    public Network(int firstInput, int secondInput, int thirdInput, int fourthInput, int inputMask) {
        first_octet = new NumberIp(firstInput);
        second_octet = new NumberIp(secondInput);
        third_octet = new NumberIp(thirdInput);
        fourth_octet = new NumberIp(fourthInput);
        netmask = new Netmask(inputMask);
        calcular();
    }

    public void calcular() {
        identificarClase(first_octet.getNumberDecimal());
        calcSeccionNetwork();
        calcRange();
        calcularSubredes();
    }

    private void identificarClase(int number) {
        if (number >= 0 && number <= 127) {
            clase = "A";
            size_clase = 8;
        } else {
            if (number >= 128 && number <= 191) {
                clase = "B";
                size_clase = 16;
            } else {
                if (number >= 192 && number <= 223) {
                    clase = "C";
                    size_clase = 24;
                } else {
                    if (number >= 224 && number <= 239) {
                        clase = "D";
                    } else {
                        if (number >= 240 && number <= 255) {
                            clase = "E";
                        }
                    }
                }
            }
        }
    }

    private void calcSeccionNetwork() {
        switch (netmask.getPosition()) {
            case 1:
                seccion = first_octet.getNumberDecimal();
                break;
            case 2:
                seccion = second_octet.getNumberDecimal();
                break;
            case 3:
                seccion = third_octet.getNumberDecimal();
                break;
            case 4:
                seccion = fourth_octet.getNumberDecimal();
                break;
        }
    }

    private void calcRange() {
        size_host = AUXRANGE - netmask.getNumberSelection();
    }

    private void calcularSubredes() {
        int position = netmask.getPosition();
        int initial_ip = 0;
        int end_ip = size_host;
        boolean found = false;

        int first_aux = first_octet.getNumberDecimal();
        int second_aux = second_octet.getNumberDecimal();
        int third_aux = third_octet.getNumberDecimal();
        int fourth_aux = fourth_octet.getNumberDecimal();

        switch (position) {
            case 1:
                while (!found) {
                    if (seccion >= initial_ip && seccion < end_ip) {
                        first_address = initial_ip + ".0.0.1";
                        last_address = (end_ip - 1) + ".255.255.254";
                        network_address = initial_ip + ".0.0.0";
                        broadcast = (end_ip - 1) + ".255.255.255";
                        found = true;
                    } else {
                        initial_ip = end_ip;
                        end_ip += size_host;
                    }
                }
                break;

            case 2:
                while (!found) {
                    if (seccion >= initial_ip && seccion < end_ip) {
                        first_address = first_aux + "." + initial_ip + ".0.1";
                        last_address = first_aux + "." + (end_ip - 1) + ".255.254";
                        network_address = first_aux + "." + initial_ip + ".0.0";
                        broadcast = first_aux + "." + (end_ip - 1) + ".255.255";
                        found = true;
                    } else {
                        initial_ip = end_ip;
                        end_ip += size_host;
                    }
                }
                break;

            case 3:
                while (!found) {
                    System.out.println(seccion);
                    if (seccion >= initial_ip && seccion < end_ip) {
                        first_address = first_aux + "." + second_aux + "." + initial_ip + ".1";
                        last_address = first_aux + "." + second_aux + "." + (end_ip - 1) + ".254";
                        network_address = first_aux + "." + second_aux + "." + initial_ip + ".0";
                        broadcast = first_aux + "." + second_aux + "." + (end_ip - 1) + ".255";
                        found = true;
                    } else {
                        initial_ip = end_ip;
                        end_ip += size_host;
                    }
                }
                break;

            case 4:
                while (!found) {
                    if (seccion >= initial_ip && seccion < end_ip) {
                        first_address = first_aux + "." + second_aux + "." + third_aux + "." + (initial_ip + 1);
                        last_address = first_aux + "." + second_aux + "." + third_aux + "." + (end_ip - 2);
                        network_address = first_aux + "." + second_aux + "." + third_aux + "." + initial_ip;
                        broadcast = first_aux + "." + second_aux + "." + third_aux + "." + (end_ip - 1);
                        found = true;
                    } else {
                        initial_ip = end_ip;
                        end_ip += size_host;
                    }
                }
                break;
        }

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

    public String getClase() {
        return clase;
    }

    public int getNumberSubred() {
        double exponente = netmask.getMaskSize() - size_clase;
        return (int) Math.pow(BASE, exponente);
    }

    public String getNetwork() {
        return network_address;
    }

    public String getAddress(int type) {
        String resp = "";
        switch (type) {
            case 1:
                resp = first_octet.getNumberDecimal() + "." + second_octet.getNumberDecimal() + "." + third_octet.getNumberDecimal() + "." + fourth_octet.getNumberDecimal();
                break;
            case 2:
                resp = first_octet.getNumberBinary() + "." + second_octet.getNumberBinary() + "." + third_octet.getNumberBinary() + "." + fourth_octet.getNumberBinary();
                break;
        }
        return resp;
    }

    public String getNetmask(int type) {
        String resp = "";
        switch (type) {
            case 1:
                resp = netmask.getMaskDecimal();
                break;
            case 2:
                resp = netmask.getMaskBinary();
                break;
        }
        return resp;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public String getFirstLastHost() {
        return first_address + "\n" + last_address;
    }
}