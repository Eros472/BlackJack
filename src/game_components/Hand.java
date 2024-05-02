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

public class Hand {    //class for the hand of player and dealer
    private final List<Card> cards; //creates a list of type game_components.Card named "cards"

    public Hand() { //constructor that initializes a new Array List for cards
        cards = new ArrayList<>();
    }   //create list of cards for player and dealer hand

    public void addCard(Card card) {    //adds game_components.Card parameter object to 'cards' list
        cards.add(card);
    }   //add card to dealer or player's hand

    public int calculateScore() {   //method to calculate current score of player or dealer
        int score = 0;
        int numAces = 0;
        for (Card card : cards) {
            score += card.getValue();
            if (card.getRank().equals("A")) {
                numAces++;
            }
        }
        while (numAces > 0 && score > 21) {
            score -= 10;
            numAces--;
        }
        return score;
    }

    public String toString() {  //displays card
        StringBuilder handString = new StringBuilder();
        for (Card card : cards) {
            handString.append(card).append("\n");
        }
        return handString.toString();
    }

    public int getSize() {
        return cards.size();
    }   //gets current number of cards in player or dealer

    public List<Card> getCards() {
        return cards;
    }   //returns card list

    public Card getCard(int index) {    //gets a specific card from the card list (from the player or dealer's current hand)
        if (index >= 0 && index < cards.size()) {
            return cards.get(index);
        }
        return null;    //if no cards return null
    }
}

