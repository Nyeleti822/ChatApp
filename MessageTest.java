/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp;

/**
 *
 * @author Student
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message message = new Message(
                "0012345678", 0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");
        assertTrue(message.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        String longText = "A".repeat(251);

        Message message = new Message(
                "0012345679", 0,
                "+27718693002", longText);
        assertFalse(message.checkMessageLength());
    }

    @Test
    public void testRecipientNumberCorrectlyFormatted() {
        Message message = new Message(
                "0012345678", 0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");
        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell());
    }

    @Test
    public void testRecipientNumberIncorrectlyFormatted() {
        Message message = new Message(
                "0012345679", 0,
                "08575975889",
                "Hi Keegan, did you receive the payment?");
        assertEquals(
                "Cell phone number is incorrectly formatted or "
                + "does not contain a valid international code.",
                message.checkRecipientCell());
    }

    @Test
    public void testMessageHash() {
        Message message = new Message(
                "0012345678", 0,
                "+27718693002",
                "Hi tonight");
        assertEquals(
                "00:0:HITONIGHT",
                message.createMessageHash());
    }

    @Test
    public void testMessageIDGenerated() {
        String messageID = Message.generateMessageID();
        assertNotNull(messageID);
        assertTrue(messageID.matches("\\d{10}"));
    }

    @Test
    public void testMessageSent() {
        Message message = new Message(
                "0012345680", 0,
                "+27718693002", "Hi Mike");
        assertEquals(
                "Message successfully sent",
                message.SentMessage(1));
    }

    @Test
    public void testMessageDisregarded() {
        Message message = new Message(
                "0012345681", 0,
                "+27718693002", "Hi Keegan");
        assertEquals(
                "Press 0 to delete the message",
                message.SentMessage(0));
    }

    @Test
    public void testMessageStored() {
        Message message = new Message(
                "0012345682", 0,
                "+27718693002", "Hi Keegan");
        assertEquals(
                "Message successfully stored",
                message.SentMessage(2));
    }
}