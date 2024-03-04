package br.edu.ufersa.ringnetwork.process;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.edu.ufersa.utils.Message;
import br.edu.ufersa.utils.MessageType;

public class ProcessServerImpl implements Runnable {

    private ObjectInputStream input;
    private String pid;
    private Socket client;
    private Socket nextClient;
    private boolean connected = true;

    public ProcessServerImpl(Socket client, Socket nextClient, int port) {
        this.client = client;
        this.nextClient = nextClient;
        pid = "" + port;
    }

    @Override
    public void run() {
        Message message;

        try {

            input = new ObjectInputStream(client.getInputStream());

            while (connected) {
                message = (Message) input.readObject();

                if (message.getContent().equalsIgnoreCase("fim")) {
                    connected = false;
                } else if (message.getType() == MessageType.BROADCAST && !message.getSender().equals(pid)) {
                    System.out.println(message);
                    System.out.println("\nsending to the next process...");
                    sendMessage(message);
                } else if (message.getType() == MessageType.UNICAST && !message.getReceiver().equals(pid)) {
                    System.out.println("\nsending to the next process...");
                    sendMessage(message);
                } else {
                    System.out.println(message);
                }
            }

            input.close();
            System.out.println("closing the connection...");
            client.close();

        } catch (IOException e) {
            System.err.println("\nI/O error. Please, try again. Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("\nClass not founded. Please, try again. Error: " + e.getMessage());
        }
    }

    private void sendMessage(Message message) {

        try {
            ObjectOutputStream out = ProcessClientImpl.output;
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
        }
    }

}
