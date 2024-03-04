package br.edu.ufersa.utils;

public enum MessageType {
    BROADCAST(0), UNICAST(1);

    public int value;
    MessageType(int value) { this.value = value; }
}
