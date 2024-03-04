package br.edu.ufersa.starnetwork.process;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.SortedMap;
import java.util.TreeMap;

import br.edu.ufersa.utils.ProcessID;

public class ProcessServer {
    
    private ServerSocket server;
    public static SortedMap<Integer, Socket> clientList;
    public static SortedMap<Integer, ObjectOutputStream> clientsOuts;
    private int port;
    private int coorPort;

    public ProcessServer(ProcessID pid) {
        this.port = pid.value;
        clientList = new TreeMap<>();
        clientsOuts = new TreeMap<>();
        this.exec();
    }
    
    private void exec() {

        try {

            server = new ServerSocket(port);
            System.out.println("server has been launched in port:"
                                + server.getLocalPort());

            System.out.println("waiting for a connection...");

            while (true) {
                Socket client = server.accept(); 
                
                System.out.println("connection with "
                                    + client.getInetAddress().getHostAddress()
                                    + ":"
                                    + port);

                                    
                clientList.put(port, client);
                clientsOuts.put(port, new ObjectOutputStream(client.getOutputStream()));
                ProcessServerImpl s = new ProcessServerImpl(client, port);
                Thread t0 = new Thread(s);
                t0.start();
            }

        } catch (IOException e) {
            System.err.println("I/O error. Please, try again. Error: " + e.getMessage() );
        }

    }

}
