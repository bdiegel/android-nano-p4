package com.honu.haha;

import java.util.Random;

public class Joker {

    // randomly select one of our dorky puns
    public static String tellPun() {
        return geekyPuns[random.nextInt(15)];
    }

    static Random random = new Random();

    static final String[] geekyPuns = new String[] {
          "If Apple made a car, would it have Windows?",
          "Never trust atoms, they make up everything.",
          "To the guy who invented Zero: Thanks for nothing!",
          "They should make a Minecraft movie, it would be a blockbuster.",
          "I'm reading a book about anti-gravity. It's impossible to put down.",
          "Did you hear about the restaurant on the moon? Great food but no atmosphere",
          "How do you know when the moon is going broke? It's down to its last quarter.",
          "What does a subatomic duck say? Quark.",
          "Lost an electron? You really want to keep an ion them.",
          "Math puns are the first sine of madness!",
          "I don't see the point in whole numbers",
          "The dead batteries were given out free of charge",
          "Local Area Network in Australia: the LAN down under.",
          "Sign on the door of an internet hacker. 'Gone Phishing'.",
          "Exception taken;"
    };

}
