/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.loginapp;

/**
 *
 * @author Student
 */
import java.util.Scanner;

public class ChatApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String firstName;
        String lastName;
        String username;
        String password;
        String cellPhoneNumber;

        System.out.println("=============================");
        System.out.println("======= REGISTRATION ========");
        System.out.println("=============================");

        System.out.println("Enter your first name: ");
        firstName = input.nextLine();

        System.out.println("Enter your last name: ");
        lastName = input.nextLine();

        do {
            System.out.println("Enter your username: ");
            username = input.nextLine();

            Login user = new Login(username, "", "", firstName, lastName);
            System.out.println(user.checkUserNameMessage());

            if (!user.checkUserName()) {
                System.out.println("Please try again.");
            }
        } while (!new Login(username, "", "", firstName, lastName).checkUserName());

        do {
            System.out.println("Enter your password: ");
            password = input.nextLine();

            Login user = new Login(username, password, "", firstName, lastName);
            System.out.println(user.checkPasswordMessage());

            if (!user.checkPasswordComplexity()) {
                System.out.println("Please try again.");
            }
        } while (!new Login(username, password, "", firstName, lastName).checkPasswordComplexity());

        do {
            System.out.println("Enter your cellphone number: ");
            cellPhoneNumber = input.nextLine();

            Login user = new Login(username, password, cellPhoneNumber, firstName, lastName);
            System.out.println(user.checkCellPhoneMessage());

            if (!user.checkCellPhoneNumber()) {
                System.out.println("Please try again.");
            }
        } while (!new Login(username, password, cellPhoneNumber, firstName, lastName).checkCellPhoneNumber());

        Login user = new Login(username, password, cellPhoneNumber, firstName, lastName);

        System.out.println("User has been successfully registered.");
        System.out.println();
        System.out.println("======== LOGIN ========");

        System.out.println("Enter your username: ");
        String enteredUsername = input.nextLine();

        System.out.println("Enter your password: ");
        String enteredPassword = input.nextLine();

        boolean loginSuccessful = user.loginUser(enteredUsername, enteredPassword);

        System.out.println(user.returnLoginStatus(loginSuccessful));

        if (!loginSuccessful) {
            System.out.println("You must log in successfully to use QuickChat.");
            input.close();
            return;
        }

        System.out.println("Welcome to QuickChat.");
        Message.loadStoredMessages();

        int numberOfMessages;

        do {
            System.out.print("How many messages would you like to enter? ");

            while (!input.hasNextInt()) {
                System.out.print("Please enter a whole number: ");
                input.next();
            }

            numberOfMessages = input.nextInt();
            input.nextLine();

            if (numberOfMessages < 0) {
                System.out.println("Please enter zero or a positive number.");
            }

        } while (numberOfMessages < 0);

        int messagesProcessed = 0;
        boolean running = true;

        while (running) {

            System.out.println("\n========= QUICKCHAT MENU =========");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages");
            System.out.print("Choose an option: ");

            String menuChoice = input.nextLine();

            switch (menuChoice) {

                case "1":

                    if (messagesProcessed >= numberOfMessages) {
                        System.out.println("You have reached the number of messages you chose to enter.");
                        break;
                    }

                    while (messagesProcessed < numberOfMessages) {

                        int messageNumber = messagesProcessed;
                        String messageID = Message.generateMessageID();
                        String recipient;

                        do {
                            System.out.print("Enter recipient cellphone number (+27...): ");
                            recipient = input.nextLine();

                            Message checkRecipient = new Message(
                                    messageID, messageNumber, recipient, "");

                            String recipientResult = checkRecipient.checkRecipientCell();

                            System.out.println(recipientResult);

                            if (!recipientResult.startsWith("Cell phone number successfully")) {
                                System.out.println("Please try again.");
                            } else {
                                break;
                            }

                        } while (true);

                        String messageText;

                        do {
                            System.out.print("Enter your message: ");
                            messageText = input.nextLine();

                            if (messageText.length() >= 250) {
                                System.out.println("Please enter a message of less than 250 characters.");
                            }

                        } while (messageText.length() >= 250);

                        Message currentMessage = new Message(
                                messageID, messageNumber, recipient, messageText);

                        currentMessage.setSender(username);
                        currentMessage.createMessageHash();

                        System.out.println("Choose what to do with this message:");
                        System.out.println("1. Send Message");
                        System.out.println("0. Disregard Message");
                        System.out.println("2. Store Message to send later");
                        System.out.print("Your choice: ");

                        String action = input.nextLine();

                        if (action.equals("1")) {

                            System.out.println(currentMessage.SentMessage(1));

                            System.out.println("Message ID: " + currentMessage.getMessageID());
                            System.out.println("Message Hash: " + currentMessage.getMessageHash());
                            System.out.println("Recipient: " + currentMessage.getRecipient());
                            System.out.println("Message: " + currentMessage.getMessage());

                        } else if (action.equals("0")) {

                            System.out.println(currentMessage.SentMessage(0));

                        } else if (action.equals("2")) {

                            System.out.println(currentMessage.SentMessage(2));
                            currentMessage.storeMessage();

                        } else {

                            System.out.println("Invalid choice. Message disregarded.");
                            currentMessage.SentMessage(0);
                        }

                        messagesProcessed++;

                        System.out.println("Messages processed: "
                                + messagesProcessed + " of " + numberOfMessages);

                        if (messagesProcessed < numberOfMessages) {
                            System.out.print("Press Enter to continue to the next message...");
                            input.nextLine();
                        }
                    }

                    System.out.println("Total messages sent: "
                            + Message.returnTotalMessages());

                    break;

                case "2":

                    System.out.println("\nRecently Sent Messages:");
                    System.out.println(Message.displayStoredReport());

                    break;

                case "3":

                    System.out.println("Thank you for using QuickChat.");
                    running = false;

                    break;

                case "4": {

                    boolean storedMenu = true;

                    while (storedMenu) {

                        System.out.println("\n==== STORED MESSAGES ====");
                        System.out.println("1. Display senders and recipients");
                        System.out.println("2. Display longest stored message");
                        System.out.println("3. Search by message ID");
                        System.out.println("4. Search by recipient");
                        System.out.println("5. Delete message using hash");
                        System.out.println("6. Display full message report");
                        System.out.println("7. Return to main menu");

                        System.out.print("Choose an option: ");
                        String storedChoice = input.nextLine();

                        switch (storedChoice) {

                            case "1":
                                System.out.println(Message.displaySendersAndRecipients());
                                break;

                            case "2":
                                System.out.println(Message.displayLongestMessage());
                                break;

                            case "3":
                                System.out.print("Enter message ID: ");
                                String searchID = input.nextLine();
                                System.out.println(Message.searchMessageID(searchID));
                                break;

                            case "4":
                                System.out.print("Enter recipient number: ");
                                String searchRecipient = input.nextLine();
                                System.out.println(Message.searchRecipient(searchRecipient));
                                break;

                            case "5":
                                System.out.print("Enter message hash: ");
                                String searchHash = input.nextLine();
                                System.out.println(Message.deleteMessage(searchHash));
                                break;

                            case "6":
                                System.out.println(Message.displayStoredReport());
                                break;

                            case "7":
                                storedMenu = false;
                                break;

                            default:
                                System.out.println("Invalid option.");
                        }
                    }

                    break;
                }

                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
            }
        }

        input.close();
    }
}

