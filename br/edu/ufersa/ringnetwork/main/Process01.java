package br.edu.ufersa.ringnetwork.main;

import br.edu.ufersa.ringnetwork.process.ProcessServer;
import br.edu.ufersa.utils.ProcessID;

public class Process01 {
    public static void main(String[] args) {
        new ProcessServer(ProcessID.P1);
    }
}
