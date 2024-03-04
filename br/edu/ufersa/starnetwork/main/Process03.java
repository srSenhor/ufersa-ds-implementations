package br.edu.ufersa.starnetwork.main;

import br.edu.ufersa.starnetwork.process.InitializeProcess;
import br.edu.ufersa.utils.ProcessID;
import br.edu.ufersa.utils.ProcessType;

public class Process03 {
    public static void main(String[] args) {
        new InitializeProcess(ProcessType.WORKER, ProcessID.P3, ProcessID.P1);
    }
}
