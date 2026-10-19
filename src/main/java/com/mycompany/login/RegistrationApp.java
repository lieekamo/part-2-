/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.login;
import java.util.Scanner;
/**
 *
 * @author lieek
 */
public class RegistrationApp {
    
public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
 
        System.out.println("=== Registration ===");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
 
        Login login = new Login(firstName, lastName);
 
        System.out.print("Enter username (must contain '_' and be no more than 5 characters): ");
        String username = scanner.nextLine();
        System.out.print("Enter password (8+ chars, capital letter, number, special char): ");
        String password = scanner.nextLine();
        System.out.print("Enter SA cell number (e.g. +27838968976): ");
        String cellPhoneNumber = scanner.nextLine();
 
        String registrationResult = login.registerUser(username, password, cellPhoneNumber);
        System.out.println(registrationResult);
 
        if (registrationResult.contains("registered successfully")) {
            System.out.println();
            System.out.println("=== Login ===");
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();
            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();
 
            boolean success = login.loginUser(loginUsername, loginPassword);
            System.out.println(login.returnLoginStatus(success));
        }
 
        scanner.close();
    }
public String QuickChat (){
        Scanner in = new Scanner(System.in);
 
        // 1. Only logged-in users may continue.
        // ASSUMPTION: adjust these two lines to match your Part 1 Login class.
        Login login = new Login();
        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();
        if (!login.loginUser(username, password)) {
            System.out.println("Login failed. Exiting.");
            
        }
 
        // 2. Welcome message
        System.out.println("Welcome to QuickChat.");
 
        // 5. User sets how many messages they will enter
        int maxMessages = readInt(in, "How many messages do you wish to enter? ");
        int entered = 0;
 
        // 4. Run until the user quits
        boolean running = true;
        while (running) {
            System.out.println("\n1) Send Messages\n2) Show recently sent messages\n3) Quit");
            int option = readInt(in, "Choose an option: ");
 
            switch (option) {
                case 1:
                    if (entered >= maxMessages) {
                        System.out.println("You have reached your message limit.");
                        break;
                    }
                    if (sendOne(in)) {
                        entered++;
                    }
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    running = false;
                    System.out.println("Total messages sent: " + Message.returnTotalMessages());
                    break;
                default:
                    System.out.println("Please choose 1, 2 or 3.");
            }
        }
}
 
    // Captures one message; returns true if it counted towards the limit
    private static boolean sendOne(Scanner in) {
        System.out.print("Recipient (e.g. +27718693002): ");
        String recipient = in.nextLine();
        System.out.print("Message: ");
        String text = in.nextLine();
 
        if (text.trim().isEmpty()) {
            System.out.println("Message cannot be empty.");
            return false;
        }
 
        Message msg = new Message(recipient, text);
 
        String cellResult = msg.checkRecipientCell();
        System.out.println(cellResult);
        if (!cellResult.startsWith("Cell phone number successfully")) {
            return false;
        }
 
        String lengthResult = msg.checkMessageLength();
        System.out.println(lengthResult);
        if (!lengthResult.equals("Message ready to send.")) {
            return false;
        }
 
        System.out.println("Message ID generated: " + msg.getMessageID());
        System.out.println("Message Hash: " + msg.getMessageHash());
 
        int choice = readInt(in, "1) Send  2) Disregard  3) Store : ");
        System.out.println(msg.sentMessage(choice));
        return choice == 1 || choice == 3;
    }
 
    private static int readInt(Scanner in, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(in.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}

