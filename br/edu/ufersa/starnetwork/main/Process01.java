package br.edu.ufersa.starnetwork.main;

import br.edu.ufersa.starnetwork.process.InitializeProcess;
import br.edu.ufersa.utils.ProcessID;
import br.edu.ufersa.utils.ProcessType;

public class Process01 {
    public static void main(String[] args) {
        new InitializeProcess(ProcessType.COORDINATOR, ProcessID.P1, ProcessID.P1);
    }
}
