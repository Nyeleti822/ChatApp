/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp;

/**
 *
 * @author Student
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Message {

    private String messageID;
    private int numMessagesSent;
    private String recipient;
    private String message;
    private String messageHash;
    private String status;

    private static int totalMessagesSent = 0;
    private static final List<Message> messages = new ArrayList<>();
    private static final Set<String> usedIDs = new HashSet<>();

    public Message(String messageID, int numMessagesSent,
                   String recipient, String message) {
        this.messageID = messageID;
        this.numMessagesSent = numMessagesSent;
        this.recipient = recipient;
        this.message = message;
        this.status = "PENDING";
    }

    public static String generateMessageID() {
        String id;
        do {
            id = String.valueOf(
                    ThreadLocalRandom.current().nextLong(
                            1_000_000_000L, 10_000_000_000L));
        } while (!usedIDs.add(id));
        return id;
    }

    public boolean checkMessageID() {
        return messageID != null
                && messageID.matches("\\d{1,10}");
    }

    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("\\+27\\d{9}")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or "
                + "does not contain a valid international code.";
    }

    public boolean checkMessageLength() {
        return message != null && message.length() <= 250;
    }
    
    public String checkMessageLengthMessage() {
        if (message != null && message.length() <=250) {
            return "Message ready to send.";
        }
        int length = (message == null) ? 0: message.length();
        int excess = Math.max(0, length - 250);
        
        return "Message exceeds 250 characters by " + excess + "; "
                + "reduce the size.";
    }

    public String createMessageHash() {
        if (!checkMessageID() || message == null
                || message.trim().isEmpty()) {
            return "";
        }

        String[] words = message.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String firstTwoID = messageID.length() >= 2
                ? messageID.substring(0, 2) : messageID;

        messageHash = (firstTwoID + ":" + numMessagesSent + ":"
                + firstWord + lastWord).toUpperCase();
        return messageHash;
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                status = "SENT";
                messages.add(this);
                totalMessagesSent++;
                return "Message successfully sent";

            case 0:
                status = "DISREGARDED";
                return "Press 0 to delete the message";

            case 2:
                status = "STORED";
                messages.add(this);
                return "Message successfully stored";

            default:
                return "Invalid choice.";
        }
    }

    public static String printMessages() {
        if (messages.isEmpty()) {
            return "No messages have been sent or stored yet.";
        }

        StringBuilder output = new StringBuilder();
        for (Message msg : messages) {
            output.append("Message ID: ")
                    .append(msg.messageID).append("\n");
            output.append("Message Hash: ")
                    .append(msg.messageHash).append("\n");
            output.append("Recipient: ")
                    .append(msg.recipient).append("\n");
            output.append("Message: ")
                    .append(msg.message).append("\n\n");
        }
        return output.toString();
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public void storeMessage() {
        try (FileWriter writer = new FileWriter("messages.json")) {
            writer.write("[\n");

            for (int i = 0; i < messages.size(); i++) {
                Message msg = messages.get(i);

                writer.write("  {\n");
                writer.write("    \"messageID\": \""
                        + escapeJson(msg.messageID) + "\",\n");
                writer.write("    \"messageHash\": \""
                        + escapeJson(msg.messageHash) + "\",\n");
                writer.write("    \"recipient\": \""
                        + escapeJson(msg.recipient) + "\",\n");
                writer.write("    \"message\": \""
                        + escapeJson(msg.message) + "\",\n");
                writer.write("    \"status\": \""
                        + escapeJson(msg.status) + "\"\n");
                writer.write("  }");

                if (i < messages.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]\n");

        } catch (IOException e) {
            System.out.println(
                    "Error storing messages: " + e.getMessage());
        }
    }

    private static String escapeJson(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
    
    public String getMessageID() {
        return messageID;
    }
    public int getNumMessagesSent() {
        return numMessagesSent;
    }
    public String getRecipient() {
        return recipient;
    }
    public String getMessage() {
        return message;
    }
    public String getMessageHash() {
        return messageHash;
    }
    public String getStatus() {
        return status;
    }
}
