package br.edu.ufersa.utils;

public enum ProcessType {
        COORDINATOR(0), WORKER(1);

    public int value;
    ProcessType(int value) { 
        this.value = value; 
    }
    
    public int getValue() {
        return value;
    }
}
