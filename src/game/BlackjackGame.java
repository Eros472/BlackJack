/*
Contributors: Erick Hambardzumyan, Jonah Lin, Yiming Lu
Assignment: Group Project - Blackjack
Class: CS 1400
Date: 05-15-2024
 */

package game;
import game_components.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackjackGame {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;

    final JFrame frame;
    final JPanel playerPanel;
    final JPanel dealerPanel;
    final JTextArea playerHandTextArea;
    final JLabel playerScoreLabel;
    final JTextArea dealerHandTextArea;
    final JLabel dealerScoreLabel;
    final JButton hitButton;
    final JButton standButton;

    public BlackjackGame() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();

        frame = new JFrame("Blackjack game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        playerPanel = new JPanel(new BorderLayout());
        dealerPanel = new JPanel(new BorderLayout());

        // The border stuff for the hand panels
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        playerPanel.setBorder(BorderFactory.createTitledBorder(border, "Player"));
        dealerPanel.setBorder(BorderFactory.createTitledBorder(border, "Dealer"));

        playerHandTextArea = new JTextArea(5, 20);
        playerHandTextArea.setEditable(false);
        playerHandTextArea.setLineWrap(true);
        JScrollPane playerScrollPane = new JScrollPane(playerHandTextArea);

        dealerHandTextArea = new JTextArea(5, 20);
        dealerHandTextArea.setEditable(false);
        dealerHandTextArea.setLineWrap(true);
        JScrollPane dealerScrollPane = new JScrollPane(dealerHandTextArea);

        playerPanel.add(playerScrollPane, BorderLayout.CENTER);
        dealerPanel.add(dealerScrollPane, BorderLayout.CENTER);

        playerScoreLabel = new JLabel("Score: ");
        dealerScoreLabel = new JLabel("Score: ");

        JPanel playerInfoPanel = new JPanel(new BorderLayout());
        playerInfoPanel.add(playerScoreLabel, BorderLayout.NORTH);

        JPanel dealerInfoPanel = new JPanel(new BorderLayout());
        dealerInfoPanel.add(dealerScoreLabel, BorderLayout.NORTH);

        playerPanel.add(playerInfoPanel, BorderLayout.SOUTH);
        dealerPanel.add(dealerInfoPanel, BorderLayout.SOUTH);

        hitButton = new JButton("Hit"); //create the "Hit" button in the GUI
        hitButton.addActionListener(new ActionListener() {  //created to let program know what should be done when a "hit" action is performed
            @Override   //overrides function based on what action is performed (hit or stand)
            public void actionPerformed(ActionEvent e) {
                Card newCard = deck.drawCard(); //when user hits, a new card is drawn from the shuffled deck
                playerHand.addCard(newCard);    //the new card is added to player's hand
                updateGUI();  // Ensure GUI is updated to display the new card


                // Check if the game should end after the card is dealt
                if (playerHand.calculateScore() > 21) {
                    hitButton.setEnabled(false);
                    determineWinner(false); //pass false because there is no instant win (Blackjack)
                }

                else if (playerHand.calculateScore() == 21) {   //if player hand is 21 after hitting, move to the dealer's turn
                    dealerTurn();
                    dealerHandTextArea.setText(dealerHand.toString());
                    dealerScoreLabel.setText("Score: " + dealerHand.calculateScore());  //display dealer's score after dealer's turn finishes
                    determineWinner(false); //pass false because there is no Blackjack
                }

            }


        });


        standButton = new JButton("Stand"); //create the "Stand" button in the GUI
        standButton.addActionListener(new ActionListener() {    //created to let program know what should be done when a "stand" action is performed
            @Override   //overrides function based on what action is performed (hit or stand)
            public void actionPerformed(ActionEvent e) {
                dealerTurn();   //after player stands, move turn to dealer
                // Update the GUI to reveal the dealer's full hand
                determineWinner(false);
            }
        });

        //GUI display and border design for hit and stand buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);

        frame.add(playerPanel, BorderLayout.WEST);
        frame.add(dealerPanel, BorderLayout.EAST);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        //deal the initial cards to both player and dealer
        dealInitialCards();
        //update GUI to reveal initial scores
        updateGUI();

        //GUI setup stuff
        frame.pack();
        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    public void dealInitialCards() {   //function that deals 2 cards to player and 2 cards to dealer and checks if there is an immediate win (Blackjack)
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());

        checkImmediateWin();
    }

    public void checkImmediateWin() {  //function that checks if the player has an immediate win (AKA "Blackjack")
        boolean immediateWin = false;   //boolean variable that will be used if player has Blackjack or not
        if (playerHand.calculateScore() == 21) {    //if player's score is 21
            if (playerHand.getSize() == 2) {    //if player has 2 cards dealt only (initial cards)
                immediateWin = true;    //immediateWin is true; player has Blackjack
                dealerHandTextArea.setText(dealerHand.toString());  //set the text display for dealer's score
                dealerScoreLabel.setText("Score: " + dealerHand.calculateScore());  //display dealer's score
                determineWinner(immediateWin);  //check if dealer has Blackjack too (tie). If not, then player wins.

            }
        }
    }
    public void updateGUI() {   //function that updates the GUI to display updated scores for player and dealer
        playerHandTextArea.setText(playerHand.toString());
        playerScoreLabel.setText("Score: " + playerHand.calculateScore());  //displays player's current score

        // Show only the first card of the dealer and hide the score
        if (dealerHand.getSize() > 0) { //if dealer is dealt the initial two cards
            Card firstCard = dealerHand.getCard(0); //retrieve face-up card
            dealerHandTextArea.setText(firstCard.toString() + "\n[Hidden Card]");   //display first card + "hidden card"
            dealerScoreLabel.setText("Score: " + firstCard.getValue()); //get value of face-up card
        } else {    //else display dealer's score after dealer's turn ends
            dealerHandTextArea.setText("");
            dealerScoreLabel.setText("Score: ");
        }
    }

    public void dealerTurn() { //function for dealer's turn
        while (dealerHand.calculateScore() < 17) {  //while dealer's current score is < 17, keep hitting
            dealerHand.addCard(deck.drawCard());
        }
        // Update GUI to show full hand after dealer's turn (or when dealer's score >= 17)
        dealerHandTextArea.setText(dealerHand.toString());
        dealerScoreLabel.setText("Score: " + dealerHand.calculateScore());
    }

    public void determineWinner(boolean instantWin) {  //function to determine winner, with boolean instantWin passed as parameter
                                                        //to indicate if player has Blackjack
        int playerScore = playerHand.calculateScore();  //calculate player score
        int dealerScore = dealerHand.calculateScore();  //calculate dealer score

        String resultMessage;   //string variable for determining game result message

        if (playerScore > 21) { //if playerScore exceeds 21, they "bust"
            resultMessage = "Bust! You lose.";  //display losing message for player
        } else if (dealerScore > 21 || playerScore > dealerScore) { //else if dealer "busts" or player has a higher hand than dealer
            resultMessage = "You win!"; //display winning message for player
        } else if (playerScore == dealerScore) {    //if player's hand is equal to dealer's hand
            resultMessage = "It's a tie.";  //display tie
        }
          else if (playerScore == 21 && instantWin) {   //if player's score is 21 and has a Blackjack
            resultMessage = "Blackjack! You win!";  //display instant win message
        }
        else {  //else dealer wins
            resultMessage = "Dealer wins.";
        }

        JOptionPane.showMessageDialog(frame, resultMessage, "Game Result", JOptionPane.INFORMATION_MESSAGE);    //GUI update for displaying result message
        //restartGame();
        SwingUtilities.invokeLater(this::restartGame);  //Ensure restart is also delayed to avoid immediate prompts
    }




    public void restartGame() {    //resets the game after a current game finishes
        //Reset deck and hands
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();

        dealInitialCards();

        hitButton.setEnabled(true);

        // Ensure the GUI is reset, including hiding the dealer's score
        updateGUI();
    }

    /*public static void main(String[] args) {
        new BlackjackGame();
    }*/
}