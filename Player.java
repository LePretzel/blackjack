import java.util.ArrayList; // Needed for the hand list.
import java.util.List;      // Needed for the hand list.

/**
 * Name:    Vince Hammers
 * Date:    4/27/2018
 * File(s): Player.java
 * Course:  CPS 176-H2
 * Purpose: To represent a player and perform the necessary actions.
 */

public class Player {

    protected String name;    // The name of the player.
    
    private boolean playing;  // Set to false if player busts or stays.
    private List<Card> hand;  // The player's hand of cards.
    private int roundsWon;    // Number of rounds won.
    
    /**
     * This constructor initializes the hand list and assigns a 
     * string to the name field.
     * 
     * @param n The String to set the name of the player to
     */
    public Player(String n) {
        hand = new ArrayList<Card>();
        name = n;
    }
    
    /**
     * This is an accessor method for the name field.
     * 
     * @return The name of the player
     */
    public String getName() {
        
        return name;
    }
    
    /**
     * This is a mutator method for the playing field.
     * 
     * @param b A boolean value to set playing to
     */
    public void setPlaying(boolean b) {
        playing = b;
    }
    
    /**
     * Gets the value of the playing field.
     * 
     * @return The value of the playing field
     */
    public boolean isPlaying() {
        
        return playing;
    }
    
    /**
     * Assigns a value to roundsWon. Used to set roundsWon to the 
     * value that is read from the file.
     * 
     * @param r The number to set RoundsWon to.
     */
    public void setRoundsWon(int r) {
        
        roundsWon = r;
    }
    
    /**
     * This method increments roundsWon.
     */
    public void addRoundsWon() {
        roundsWon++;
    }
    
    /**
     * Accessor for the roundsWon field.
     * 
     * @return The number of rounds this player has won
     */
    public int getRoundsWon() {
        
        return roundsWon;
    }
    
    /**
     * Adds a card to the this player's hand.
     * 
     * @param c The card to add to the hand list
     */
    public void addCard(Card c) {
        
        hand.add(c);
    }
    
    /**
     * Reinitializes the hand list as an empty list.
     */
    public void clearHand() {
        
        hand = new ArrayList<Card>();
    }
    /**
     * Accessor method for the hand list.
     * 
     * @return A reference to the hand List
     */
    public List<Card> getHand() {
        
        return hand;
    }
    
    /**
     * Gets a reference to a specific card in the hand.
     * 
     * @param i The index of the card to get
     * @return  A reference to the card at index i
     */
    public Card getCardInHand(int i) {
        
        return hand.get(i);
    }
    
    /**
     * Gets the size of the hand list.
     * 
     * @return The size of the hand list
     */
    public int getHandSize() {
        
        return hand.size();
    }
    
    /**
     * Creates a representation of the object for use in JOptionPane messages.
     * 
     * @return A string showing name, the cards in hand, and number of wins.
     */
    public String toString() {
        
        String str = "Name:    " + name + "\n";
        str += "Hand: \n";
        for (Card c : hand) {
                str += "\t " + c + "\n";
        }
        str += "Number of Wins: " + roundsWon;
        return str;
    }
}
