package br.edu.ufersa.starnetwork.process;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.SortedMap;
import java.util.TreeMap;

import br.edu.ufersa.utils.Message;
import br.edu.ufersa.utils.MessageType;

public class ProcessServerImpl implements Runnable {

    private ObjectInputStream input;
    private String pid;
    private Socket client;
    private boolean connected = true;

    public ProcessServerImpl(Socket client, int port) {
        this.client = client;
        pid = "" + port;
    }

    @Override
    public void run() {
        Message message;

        try {

            input = new ObjectInputStream(client.getInputStream());

            while (connected) {
                message = (Message) input.readObject();

                if (message.getContent().equalsIgnoreCase("exit")) {
                    connected = false;
                } else if (message.getType() == MessageType.BROADCAST) {

                    System.out.println(message);
                    System.out.println("\nsending to the next process...");
                    for (Integer port : ProcessServer.clientList.keySet()) {
                        if (!message.getSender().equals(pid)) {
                            sendMessage(message, port);
                        }
                    }

                } else if (message.getType() == MessageType.UNICAST && !message.getReceiver().equals(pid)) {

                    System.out.println("\nsending to " + message.getReceiver());
                    int receiver = Integer.parseInt(message.getReceiver());
                    sendMessage(message, receiver);

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

    private void sendMessage(Message message, int port) {
        ObjectOutputStream receiverOut = ProcessServer.clientsOuts.get(port);
        try {
            ObjectOutputStream out = receiverOut;
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
        }
    }

}
