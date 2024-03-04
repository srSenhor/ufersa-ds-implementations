package br.edu.ufersa.starnetwork.process;

import java.io.IOException;
import java.net.Socket;

public class ProcessClient {
    
    private Socket server;
    public Socket client;
    private String host;
    private int port;
    private int pid;

    public ProcessClient(String host, int port, int pid) {
        this.host = host;
        this.port = port;
        this.pid = pid;
        this.exec();
    }

    private void exec() {
        try {
            
            server = new Socket(host, port);
            System.out.println("\nsuccessfully connected in star with "
                                    + server.getInetAddress().getHostAddress()
                                    + ":"
                                    + server.getPort());

           ProcessClientImpl client = new ProcessClientImpl(server, port, pid);
           Thread t0 = new Thread(client);
           t0.start();

        } catch (IOException e) {
            System.err.println("I/O error. Please, try again. Error: " + e.getMessage() );    
            throw new RuntimeException(e);
        }
    }

    
}
