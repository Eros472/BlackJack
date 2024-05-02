/*
Contributors: Erick Hambardzumyan, Jonah Lin, Yiming Lu
Assignment: Group Project - Blackjack
Class: CS 1400
Date: 05-15-2024
 */

package game_components;

public class Card {    //creates Card class
    private String suit;    //creates String to display card suit (D, H, C, S)
    private String rank;    //Ace, Jack, Queen, King, 10, 9, 8 etc
    private int value;  //value of rank in Blackjack

    public Card(String suit, String rank) { //constructor that initializes card suits and ranks
        this.suit = suit;
        this.rank = rank;
        determineInitialValue();    //determines value of cards
    }

    public void determineInitialValue() {   //determines value of cards
        switch (rank) {
            case "A":
                this.value = 11; // Default value of Ace = 11
                break;
            case "J":
            case "Q":
            case "K":
                this.value = 10;    //J, Q, K = 10
                break;
            default:
                this.value = Integer.parseInt(rank);    //10, 9, 8-2
        }
    }

    public String getRank() {   //gets the rank of current card
        return rank;
    }   //returns rank of card

    public int getValue() { //gets the value of current card
        return value;
    }   //returns value of card


    @Override
    public String toString() {  //Displayed in GUI for player and dealer (ex: 4 of Spades)
        return rank + " of " + suit;
    }   //used to display specific card
}
