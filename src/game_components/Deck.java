/*
Contributors: Erick Hambardzumyan, Jonah Lin, Yiming Lu
Assignment: Group Project - Blackjack
Class: CS 1400
Date: 05-15-2024
 */

package game_components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {    //class for the deck of cards
    private final List<Card> cards; //list of individual cards of type "game_components.Card"

    public Deck() { //constructor to create and initialize the deck of cards
        cards = new ArrayList<>();  //cards is an ArrayList
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"}; //String array of suits
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};    //String array of ranks

        for (String suit : suits) { //for a suit in suits array
            for (String rank : ranks) { //for a rank in ranks array
                cards.add(new Card(suit, rank));    //add a card object to the cards list with the corresponding suit and rank of a card
            }
        }
        Collections.shuffle(cards); //randomize cards
    }

    public Card drawCard() {    //draws a random card from a shuffled deck of cards
        return cards.removeFirst();
    }   //remove the first card from the deck to draw
}
