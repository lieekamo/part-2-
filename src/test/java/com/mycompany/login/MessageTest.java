/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.login;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 *
 * @author lieek
 */
public class MessageTest {
    
// Test Case 1 data
    private final Message msg1 = new Message("0012345678", 0, "+27718693002",
            "Hi Mike, can you join us for dinner tonight?");
    // Test Case 2 data
    private final Message msg2 = new Message("0198765432", 1, "08575975889",
            "Hi Keegan, did you receive the payment?");
 
    @Test
    public void testMessageLengthSuccess() {
        assertEquals("Message ready to send.", msg1.checkMessageLength());
    }
 
    @Test
    public void testMessageLengthFailure() {
        String longText = "a".repeat(260);
        Message m = new Message("0012345678", 2, "+27718693002", longText);
        assertEquals("Message exceeds 250 characters by 10, please reduce the size.",
                m.checkMessageLength());
    }
 
    @Test
    public void testRecipientSuccess() {
        assertEquals("Cell phone number successfully captured.", msg1.checkRecipientCell());
    }
 
    @Test
    public void testRecipientFailure() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an "
                + "international code. Please correct the number and try again.",
                msg2.checkRecipientCell());
    }
 
    @Test
    public void testMessageHashTestCase1() {
        assertEquals("00:0:HITONIGHT", msg1.createMessageHash());
    }
 
    @Test
    public void testMessageHashesInLoop() {
        Message[] messages = {msg1, msg2};
        String[] expected = {"00:0:HITONIGHT", "01:1:HIPAYMENT"};
        for (int i = 0; i < messages.length; i++) {
            assertEquals(expected[i], messages[i].createMessageHash());
        }
    }
 
    @Test
    public void testMessageIDCreated() {
        Message m = new Message("+27718693002", "Hello there");   // auto-generated ID
        assertTrue(m.checkMessageID());
        assertEquals(10, m.getMessageID().length());
    }
 
    @Test
    public void testSentMessageSend() {
        assertEquals("Message successfully sent.", msg1.sentMessage(1));
    }
 
    @Test
    public void testSentMessageDisregard() {
        assertEquals("Press 0 to delete the message.", msg2.sentMessage(2));
    }
 
    @Test
    public void testSentMessageStore() {
        assertEquals("Message successfully stored.", msg1.sentMessage(3));
    }
}
 
//Reference list 

//Chacon, S. and Straub, B., 2014. Git branching: Basic branching and merging. [online] Available at: https://git-scm.com/book/en/v2/Git-Branching-Basic-Branching-and-Merging [Accessed 14 October 2026].

//Google, [s.a.]. Gson user guide. [online] Available at: https://google.github.io/gson/UserGuide.html [Accessed 14 October 2026].

//JUnit, [s.a.]. JUnit 5 user guide. [online] Available at: https://junit.org/junit5/docs/current/user-guide/ [Accessed 14 October 2026].

//Oracle, [s.a.]. Lesson: Regular expressions. [online] Available at: https://docs.oracle.com/javase/tutorial/essential/regex/index.html [Accessed 14 October 2026].