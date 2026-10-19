/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.login;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 *
 * @author lieek
 */
public class Message {
    
// Shared state: lives for as long as the program runs
    private static final List<Message> sentMessages = new ArrayList<>();
    private static int totalSent = 0;
    private static int nextMessageNumber = 0;
    private static final Path JSON_FILE = Paths.get("messages.json");
 
    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String text;
    private final String messageHash;
 
    // Normal constructor: ID and number are auto generated
    public Message(String recipient, String text) {
        this(generateID(), nextMessageNumber++, recipient, text);
    }
 
    // Constructor that lets tests supply the ID and number
    public Message(String messageID, int messageNumber, String recipient, String text) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.text = text;
        this.messageHash = createMessageHash();
    }
 
    private static String generateID() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }
 
    // Message ID must be no more than ten characters
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }
 
    // International code (+ and 1-3 digits) followed by no more than 10 digits
    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+\\d{1,3}\\d{1,10}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an "
                + "international code. Please correct the number and try again.";
    }
 
    // Message must be 250 characters or fewer
    public String checkMessageLength() {
        if (text.length() <= 250) {
            return "Message ready to send.";
        }
        int over = text.length() - 250;
        return "Message exceeds 250 characters by " + over + ", please reduce the size.";
    }
 
    // First two digits of ID : message number : FIRSTWORD + LASTWORD (uppercase)
    public String createMessageHash() {
        String[] words = text.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        return (messageID.substring(0, 2) + ":" + messageNumber + ":" + first + last).toUpperCase();
    }
 
    // 1 = send, 2 = disregard, 3 = store
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                totalSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }
 
    // All messages sent while the program is running
    public static String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append("Message ID: ").append(m.messageID).append("\n")
              .append("Message Hash: ").append(m.messageHash).append("\n")
              .append("Recipient: ").append(m.recipient).append("\n")
              .append("Message: ").append(m.text).append("\n\n");
        }
        return sb.toString();
    }
 
    public static int returnTotalMessages() {
        return totalSent;
    }
 
    // Appends this message to messages.json (uses Gson)
    public void storeMessage() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<List<Message>>() {}.getType();
        List<Message> stored = new ArrayList<>();
        try {
            if (Files.exists(JSON_FILE)) {
                List<Message> existing = gson.fromJson(Files.readString(JSON_FILE), listType);
                if (existing != null) {
                    stored = existing;
                }
            }
            stored.add(this);
            Files.writeString(JSON_FILE, gson.toJson(stored, listType));
        } catch (IOException e) {
            System.out.println("Could not store message: " + e.getMessage());
        }
    }
 
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
}
