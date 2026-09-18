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
        } while (!new Login(username, "", "", firstName, lastName).checkUserName());

        do {
            System.out.println("Enter your password: ");
            password = input.nextLine();
            Login user = new Login(username, password, "", firstName, lastName);
            System.out.println(user.checkPasswordMessage());
        } while (!new Login(username, password, "", firstName, lastName).checkPasswordComplexity());

        do {
            System.out.println("Enter your cellphone number: ");
            cellPhoneNumber = input.nextLine();
            Login user = new Login(username, password, cellPhoneNumber, firstName, lastName);
            System.out.println(user.checkCellPhoneMessage());
        } while (!new Login(username, password, cellPhoneNumber, firstName, lastName).checkCellPhoneNumber());

        Login user = new Login(username, password,cellPhoneNumber, firstName, lastName);

        System.out.println("User has been successfully registered.");
        System.out.println();
        System.out.println("======== LOGIN ========");
        
        System.out.println("Enter your username: ");
        String enteredUsername = input.nextLine();

        System.out.println("Enter your password: ");
        String enteredPassword = input.nextLine();

        boolean loginSuccessful =

                user.loginUser(enteredUsername, enteredPassword);

        System.out.println(user.returnLoginStatus(loginSuccessful));

        input.close();

    }

}
