/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp;

/**
 *
 * @author Student
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
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
    private String sender = "Unknown";

    private static int totalMessagesSent = 0;

    private static final List<Message> messages = new ArrayList<>();
    private static final Set<String> usedIDs = new HashSet<>();

    private static String[] sentMessages = new String[0];
    private static String[] disregardedMessages = new String[0];
    private static String[] storedMessages = new String[0];
    private static String[] messageHashes = new String[0];
    private static String[] messageIDs = new String[0];

    private static final String FILE_NAME = "messages.json";

    private static final Gson gson =
            new GsonBuilder().setPrettyPrinting().create();

    // Constructor
    public Message(String messageID, int numMessagesSent,
                   String recipient, String message) {

        this.messageID = messageID;
        this.numMessagesSent = numMessagesSent;
        this.recipient = recipient;
        this.message = message;
        this.status = "PENDING";
    }

    // Generate unique message ID
    public static String generateMessageID() {

        String id;

        do {
            id = String.valueOf(ThreadLocalRandom.current()
                    .nextLong(1000000000L, 10000000000L));
        } while (!usedIDs.add(id));

        return id;
    }

    // Validate message ID
    public boolean checkMessageID() {
        return messageID != null
                && messageID.matches("\\d{1,10}");
    }

    // Validate recipient cellphone number
    public String checkRecipientCell() {

        if (recipient != null
                && recipient.matches("\\+27\\d{9}")) {

            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or "
                + "does not contain a valid international code.";
    }

    // Validate message length
    public boolean checkMessageLength() {
        return message != null && message.length() <= 250;
    }

    public String checkMessageLengthMessage() {

        if (checkMessageLength()) {
            return "Message ready to send.";
        }

        int length = message == null ? 0 : message.length();

        return "Message exceeds 250 characters by "
                + Math.max(0, length - 250) + "; reduce the size.";
    }

    // Create message hash
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

        messageHash = (firstTwoID + ":"
                + numMessagesSent + ":"
                + firstWord + lastWord).toUpperCase();

        return messageHash;
    }

    // Send, disregard or store message
    public String SentMessage(int choice) {

        switch (choice) {

            case 1:
                status = "SENT";
                messages.add(this);
                totalMessagesSent++;
                updateArrays();
                return "Message successfully sent";

            case 0:
                status = "DISREGARDED";
                messages.add(this);
                updateArrays();
                return "Press 0 to delete the message";

            case 2:
                status = "STORED";
                messages.add(this);
                updateArrays();
                return "Message successfully stored";

            default:
                return "Invalid choice.";
        }
    }

    // Populate the five arrays
    private static void updateArrays() {

        List<String> sent = new ArrayList<>();
        List<String> disregarded = new ArrayList<>();
        List<String> stored = new ArrayList<>();
        List<String> hashes = new ArrayList<>();
        List<String> ids = new ArrayList<>();

        for (Message msg : messages) {

            if ("SENT".equals(msg.status)) {
                sent.add(msg.message);
            }

            if ("DISREGARDED".equals(msg.status)) {
                disregarded.add(msg.message);
            }

            if ("STORED".equals(msg.status)) {
                stored.add(msg.message);
            }

            hashes.add(msg.messageHash);
            ids.add(msg.messageID);
        }

        sentMessages = sent.toArray(new String[0]);
        disregardedMessages = disregarded.toArray(new String[0]);
        storedMessages = stored.toArray(new String[0]);
        messageHashes = hashes.toArray(new String[0]);
        messageIDs = ids.toArray(new String[0]);
    }

    // Array getters
    public static String[] getSentMessages() {
        return sentMessages;
    }

    public static String[] getDisregardedMessages() {
        return disregardedMessages;
    }

    public static String[] getStoredMessages() {
        return storedMessages;
    }

    public static String[] getMessageHashes() {
        return messageHashes;
    }

    public static String[] getMessageIDs() {
        return messageIDs;
    }

    // Load messages from JSON
    public static void loadStoredMessages() {

        try (FileReader reader = new FileReader(FILE_NAME)) {

            Message[] loadedMessages =
                    gson.fromJson(reader, Message[].class);

            messages.clear();
            totalMessagesSent = 0;

            if (loadedMessages != null) {

                for (Message msg : loadedMessages) {

                    messages.add(msg);

                    if (msg.messageID != null) {
                        usedIDs.add(msg.messageID);
                    }

                    if ("SENT".equals(msg.status)) {
                        totalMessagesSent++;
                    }
                }
            }

            updateArrays();

        } catch (IOException e) {
            System.out.println("No stored messages file found yet.");
        }
    }

    // Store message
    public void storeMessage() {
        saveMessages();
    }

    // Save messages to JSON
    public static void saveMessages() {

        try (FileWriter writer = new FileWriter(FILE_NAME)) {

            gson.toJson(messages, writer);

        } catch (IOException e) {

            System.out.println(
                    "Error storing messages: " + e.getMessage());
        }
    }

    // Display senders and recipients
    public static String displaySendersAndRecipients() {

        StringBuilder output = new StringBuilder();

        for (Message msg : messages) {

            if ("STORED".equals(msg.status)) {

                output.append("Sender: ")
                        .append(msg.sender)
                        .append("\nRecipient: ")
                        .append(msg.recipient)
                        .append("\n\n");
            }
        }

        return output.length() == 0
                ? "No stored messages found."
                : output.toString();
    }

    // Display longest sent or stored message
    public static String displayLongestMessage() {

        Message longest = null;

        for (Message msg : messages) {

            if (("SENT".equals(msg.status)
                    || "STORED".equals(msg.status))
                    && msg.message != null
                    && (longest == null
                    || msg.message.length()
                    > longest.message.length())) {

                longest = msg;
            }
        }

        return longest == null
                ? "No messages found."
                : longest.message;
    }

    // Search by message ID
    public static String searchMessageID(String id) {

        if (id == null) {
            return "Message ID not found.";
        }

        for (Message msg : messages) {

            if (id.equals(msg.messageID)) {
                return msg.message;
            }
        }

        return "Message ID not found.";
    }

    // Search messages by recipient
    public static String searchRecipient(String recipient) {

        StringBuilder output = new StringBuilder();

        for (Message msg : messages) {

            if (recipient != null
                    && recipient.equals(msg.recipient)
                    && ("SENT".equals(msg.status)
                    || "STORED".equals(msg.status))) {

                output.append(msg.message).append("\n");
            }
        }

        return output.length() == 0
                ? "No messages found for this recipient."
                : output.toString();
    }

    // Delete message using hash
    public static String deleteMessage(String hash) {

        if (hash == null) {
            return "Message hash not found.";
        }

        for (int i = 0; i < messages.size(); i++) {

            Message msg = messages.get(i);

            if (hash.equals(msg.messageHash)) {

                String deletedMessage = msg.message;

                messages.remove(i);

                updateArrays();
                saveMessages();

                return "Message: \"" + deletedMessage
                        + "\" successfully deleted.";
            }
        }

        return "Message hash not found.";
    }

    // Display full report of sent messages
    public static String displayStoredReport() {

        StringBuilder output = new StringBuilder();

        for (Message msg : messages) {

            if ("SENT".equals(msg.status)) {

                output.append("Message Hash: ")
                        .append(msg.messageHash)
                        .append("\nRecipient: ")
                        .append(msg.recipient)
                        .append("\nMessage: ")
                        .append(msg.message)
                        .append("\n---------------------\n");
            }
        }

        return output.length() == 0
                ? "No sent messages found."
                : output.toString();
    }

    // Display messages
    public static String printMessages() {
        return displayStoredReport();
    }

    // Return total sent messages
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Set sender
    public void setSender(String sender) {
        this.sender = sender;
    }

    // Getters
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

    public String getSender() {
        return sender;
    }
}