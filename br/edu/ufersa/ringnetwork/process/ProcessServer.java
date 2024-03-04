package br.edu.ufersa.ringnetwork.process;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.edu.ufersa.utils.ProcessID;

public class ProcessServer {
    
    private ServerSocket server;
    private Socket client;
    public Socket nextClient;
    private int port;

    public ProcessServer(ProcessID pid) {
        this.port = pid.value;
        this.exec();
    }
    
    private void exec() {

        try {

            server = new ServerSocket(port);
            System.out.println("server has been launched in port:"
                                + server.getLocalPort());

            Runnable r0 = new Runnable() {
                @Override
                public void run() {
                    boolean connected = false;
                    int nextPort = port + 1;
                    while (connected == false) {
                        try {
                            Thread.sleep(5000L);
                            if (nextPort > ProcessID.P4.value) { nextPort = ProcessID.P1.value; }
                            new ProcessClient("localhost", nextClient, nextPort);
                            connected = true;
                        } catch (Exception e) {
                            System.err.println("retrying to connect in a few secs...");
                            System.err.println("An error has ocurred. Error: " + e.getMessage());
                        }
                    } 
                }
            };
            Thread t0 = new Thread(r0);
            t0.start();

            System.out.println("waiting for a connection...");

            while (true) {
                client = server.accept();
                System.out.println("connection with "
                                    + client.getInetAddress().getHostAddress()
                                    + ":"
                                    + port);

                
                ProcessServerImpl s = new ProcessServerImpl(client, nextClient, port);
                Thread t1 = new Thread(s);
                t1.start();
            }

        } catch (IOException e) {
            System.err.println("I/O error. Please, try again. Error: " + e.getMessage() );
        }

    }

}
