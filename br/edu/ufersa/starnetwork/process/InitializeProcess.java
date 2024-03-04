package br.edu.ufersa.starnetwork.process;

import br.edu.ufersa.utils.ProcessID;
import br.edu.ufersa.utils.ProcessType;

public class InitializeProcess {

    private ProcessType type;
    private static int coordPort;

    public InitializeProcess(ProcessType type, ProcessID pid, ProcessID coordPid) {
        this.type = type;
        this.start(pid, coordPid);
    }

    private void start(ProcessID pid, ProcessID coordPid){
        if (type == ProcessType.COORDINATOR) {
            new ProcessServer(pid);
        } else {
            new ProcessClient("localhost", pid.value, coordPid.value);
        }
    }

    

}
