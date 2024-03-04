package br.edu.ufersa.utils;

public enum ProcessID {
    P1(5000), P2(5001), P3(5002), P4(5003);

    public int value;
    ProcessID(int value) { 
        this.value = value; 
    }
}
