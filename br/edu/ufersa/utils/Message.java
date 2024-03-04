package br.edu.ufersa.utils;

import java.io.Serializable;

public class Message implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private MessageType type;
    private String sender;
    private String receiver;
    private String content;
    
    public Message(String sender, String receiver, String content) {
        this.setSender(sender); 
        this.setReceiver(receiver);
        this.setType(receiver);
        this.setContent(content);
    }
    
    public MessageType getType() {
        return type;
    }
    private void setType(String receiver) {
        if (receiver.equalsIgnoreCase("all")) {
            this.type = MessageType.BROADCAST;
        } else {
            this.type = MessageType.UNICAST;
        }
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        if (sender != null && !sender.isEmpty()) {
            this.sender = sender;
        } else {
            System.out.println("Nenhum remetente informado");
        }
    }

    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        if (receiver != null) {
            this.receiver = receiver;
        } else {
            System.out.println("Nenhum destinatario informado");
        }
    }

    public String getContent() {
        return content;
    }
    private void setContent(String content) {
        if (sender != null) {
            this.content = content;
        } else {
            System.out.println("Nenhum payload atribuido");
        }
    }

    @Override
    public String toString() {
        return "\n[Message\n"
            + "Type: " + type.name() + "\n"
            + "From: " + sender + "\n"
            + "To: " + receiver + "\n" 
            + "Content: " + content + "]\n";
    }

    
}