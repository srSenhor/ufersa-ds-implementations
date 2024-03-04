package br.edu.ufersa.ringnetwork.process;

import java.io.IOException;
import java.net.Socket;

public class ProcessClient {
    
    private Socket server;
    public Socket client;
    private String host;
    private int port;

    public ProcessClient(String host, Socket next, int port) {
        this.host = host;
        this.client = next;
        this.port = port;
        this.exec();
    }

    private void exec() {
        try {
            
            server = new Socket(host, port);
            System.out.println("\nsuccessfully connected in ring with "
                                    + server.getInetAddress().getHostAddress()
                                    + ":"
                                    + server.getPort());

           ProcessClientImpl client = new ProcessClientImpl(server, this.client, port);
           Thread t0 = new Thread(client);
           t0.start();

        } catch (IOException e) {
            System.err.println("I/O error. Please, try again. Error: " + e.getMessage() );    
            throw new RuntimeException(e);
        }
    }

    
}
