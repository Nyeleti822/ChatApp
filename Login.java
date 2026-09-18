/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp;

/**
 *
 * @author Student
 */
public class Login {
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;
    
    public Login(String username, String password, String cellPhoneNumber, String firstName, String lastName) {
        this.username=username;
        this.password=password;
        this.cellPhoneNumber=cellPhoneNumber;
        this.firstName=firstName;
        this.lastName=lastName;
    }
    
    public boolean checkUserName() {
        return username.contains("_") && username.length() <=5;
    }
    
    public boolean checkPasswordComplexity() {
        boolean hasCapitalLetter=false;
        boolean hasNumber=false;
        boolean hasSpecialCharacter=false;
        
        if(password.length()<8) {
            return false;
        }
        
        for(int i = 0; i<password.length(); i++) {
            char currentCharacter = password.charAt(i);
            
            if(Character.isUpperCase(currentCharacter)) {
                hasCapitalLetter =true;
            }
            else if(Character.isDigit(currentCharacter)) {
                hasNumber = true;
            }
            else if(!Character.isLetterOrDigit(currentCharacter)) {
                hasSpecialCharacter = true;
            }
    }
        
        return hasCapitalLetter && hasNumber && hasSpecialCharacter;
    }
    
    public boolean checkCellPhoneNumber() {
        String cellPhoneRegex = "\\+27\\d{9}$";
        
        return cellPhoneNumber.matches(cellPhoneRegex);
    }
    
    public String checkUserNameMessage() {
        if(checkUserName()) {
            return "Username successfully captured.";
        }
        return "Username is not correctly formatted; "
                    + "please ensure that your username contains an "
                    + "underscore and is no more than five characters in length.";
    }
       
         public String checkPasswordMessage() {
          if (checkPasswordComplexity()) {
           return "Password successfully captured.";
        }
        return "Password is not correctly formatted; please ensure that the "
                    + "password contains at least eight characters, a capital letter, "
                    + "a number, and a special character.";
        }    
           
        public String checkCellPhoneMessage() {
        if (checkCellPhoneNumber()) {
            return "Cell number successfully captured.";
        }
         return "Cell number is incorrectly formatted or does not contain an international "
                 + "code; please correct the number and try again.";
        }
           
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername.equals(username) && enteredPassword.equals(password);
    }
    
    public String returnLoginStatus(boolean loginSuccessful) {
    if (loginSuccessful){
    return "Welcome "+ firstName+" "+lastName+", it is great to see you again!";
            }else{
    return "Username or password incorrect, please try again.";
    }
    }
}
       
    
     
    
    
    
