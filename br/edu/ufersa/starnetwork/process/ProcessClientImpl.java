package br.edu.ufersa.starnetwork.process;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import br.edu.ufersa.utils.Message;

public class ProcessClientImpl implements Runnable {

    private String pid;
    private Socket server;
    private boolean connected = true;
    public static ObjectOutputStream output;


    public ProcessClientImpl(Socket server, int port, int pid) {
        this.pid = "" + pid;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            
            String prev_pid = "";
            switch (Integer.parseInt(pid)) {
                case 5000:
                    prev_pid = "5003";
                    break;
                case 5001:
                    prev_pid = "5000";
                    break;
                case 5002:
                    prev_pid = "5001";
                    break;
                case 5003:
                    prev_pid = "5002";
                    break;
                default:
                    break;
            }
             
            Scanner input = new Scanner(System.in);
            output = new ObjectOutputStream(server.getOutputStream());

            String messageContent, receiver;

            Runnable r0 = new Runnable() {
                @Override
                public void run() {
                    try {
                        ObjectInputStream in;
                        while (true) {
                            in = new ObjectInputStream(server.getInputStream());
                            Message msg = (Message) in.readObject();
                            System.out.println(msg);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            };

            Thread t0 = new Thread(r0);
            t0.start();
                    
            while (connected) {
                Thread.sleep(5000L);
                System.out.print("[" 
                                + server.getInetAddress().getHostAddress() 
                                + ":" 
                                + prev_pid 
                                + "]type a message: ");
                messageContent = input.nextLine();
                
                
                if (messageContent.equalsIgnoreCase("exit")) {
                    connected = false;
                } else {
                    System.out.print("receiver:\n" 
                                        + "[5000]\n"
                                        + "[5001]\n"
                                        + "[5002]\n"
                                        + "[5003]\n"
                                        + "[all]\n");
                    receiver = input.nextLine();

                    if (
                    receiver.equalsIgnoreCase("5000") ||
                    receiver.equalsIgnoreCase("5001") ||
                    receiver.equalsIgnoreCase("5002") ||
                    receiver.equalsIgnoreCase("5003") ||
                    receiver.equalsIgnoreCase("all")){

                        Message message = new Message(prev_pid, receiver, messageContent);
                        System.out.println("sending the message to " + receiver);
                        output.reset();
                        output.writeObject(message);
                        output.flush();

                    } else {
                    System.err.println("this receiver doesn't exists, please try again...");
                    }
            }
            
            output.close();
            input.close();
            server.close();
        }

       } catch (Exception e) {
        e.printStackTrace();
       } finally {
        System.out.println("Conexão finalizada");
       }
    }

}
