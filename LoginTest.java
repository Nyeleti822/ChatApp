/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.loginapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */

public class LoginTest {

    @Test
    public void testUsernameCorrectlyFormatted() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertEquals(
                "Username successfully captured.",
                user.checkUserNameMessage()
        );
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login user = new Login(
                "kyle!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertEquals(
                "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
                user.checkUserNameMessage()
        );
    }

    @Test
    public void testPasswordCorrectlyFormatted() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertEquals(
                "Password successfully captured.",
                user.checkPasswordMessage()
        );
    }

    @Test
    public void testPasswordIncorrectlyFormatted() {
        Login user = new Login(
                "kyl_1",
                "password",
                "+27838968976",
                "John",
                "Smith"
        );

        assertEquals(
                "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                user.checkPasswordMessage()
        );
    }

    @Test
    public void testCellPhoneCorrectlyFormatted() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertEquals(
                "Cell number successfully captured.",
                user.checkCellPhoneMessage()
        );
    }

    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553",
                "John",
                "Smith"
        );

        assertEquals(
                "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.",
                user.checkCellPhoneMessage()
        );
    }

    @Test
    public void testLoginSuccessful() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertTrue(user.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertFalse(user.loginUser("wrong", "wrong"));
    }

    @Test
    public void testUsernameCorrectlyFormattedTrue() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertTrue(user.checkUserName());
    }

    @Test
    public void testUsernameIncorrectlyFormattedFalse() {
        Login user = new Login(
                "kyle!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertFalse(user.checkUserName());
    }

    @Test
    public void testPasswordMeetsRequirementsTrue() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testPasswordDoesNotMeetRequirementsFalse() {
        Login user = new Login(
                "kyl_1",
                "password",
                "+27838968976",
                "John",
                "Smith"
        );

        assertFalse(user.checkPasswordComplexity());
    }

    @Test
    public void testCellPhoneCorrectlyFormattedTrue() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneIncorrectlyFormattedFalse() {
        Login user = new Login(
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553",
                "John",
                "Smith"
        );

        assertFalse(user.checkCellPhoneNumber());
    }
}
