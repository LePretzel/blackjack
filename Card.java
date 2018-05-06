/**
 * Name:    Vince Hammers
 * Date:    4/21/2018
 * File(s): Card.java
 * Course:  CPS 176-H2
 * Purpose: To represent a standard playing card.
 */

public class Card {
    
    /**
     * The possible values for a card's suit
     */
    enum Suit {
        CLUBS,
        DIAMONDS,
        HEARTS,
        SPADES;
        
        /**
         * Gets a string representation if the same naming format as the image
         * files. First letter capitalized, the rest lower case.
         * 
         * @return A String representation of the suit
         */
        public String getString() {
            
            String str = this.name();
            
            // Keep only the first letter capital
            return str.substring(0, 1) +
                   str.substring(1, str.length()).toLowerCase();
        }
    }
    
    /**
     * The possible values for a card's rank
     */
    enum Rank {
        ACE, TWO, THREE, FOUR,
        FIVE, SIX, SEVEN, EIGHT,
        NINE, TEN, JACK, QUEEN,
        KING;
        
        /**
         * Gets a string representation if the same naming format as the image
         * files. First letter capitalized, the rest lower cased for face cards.
         * The numerical representation for the rest.
         * 
         * @return A string representation of the rank
         */
        public String getString() {
            
            String str;
            
            // Get the numeric value of the non-face cards.
            if (this != ACE && this != JACK && this != QUEEN && this != KING) {
                
                str = String.format("%d", this.ordinal() + 1);
            }
            // Do the same thing as the suit getString method for the face cards.
            else {
                str = this.name();
                
                return str.substring(0, 1) +
                       str.substring(1, str.length()).toLowerCase();
            }
            
            return str;
        }
    }
    
    private Rank rank; // The rank of the card.
    private Suit suit; // The suit of the card.
    
    /**
     * Sets the value of rank and suit by passing enum constants.
     * 
     * @param r The enum constant to set rank to
     * @param s The enum constant to set suit to
     */
    public Card(Rank r, Suit s) {
        rank = r;
        suit = s;
    }
    
    /**
     * Sets the value of rank and suit by comparing their ordinal's against integers.
     * Helpful for creating many cards with for loops.
     * 
     * @param r The ordinal of the enum value
     * @param s The ordinal of the enum value
     */
    public Card(int r, int s) {
        
        // Default values in case of incorrect input
        rank = Rank.TWO;
        suit = Suit.CLUBS;

        for (Rank i : Rank.values()) {
            if (r == i.ordinal()) {
                rank = i;
            }
        }
        
        for (Suit i : Suit.values()) {
            if (s == i.ordinal()) {
                suit = i;
            }
        }
    }
    
    /**
     * Accessor method for rank
     * 
     * @return the value of the rank field
     */
    public Rank getRank() {
        
        return rank;
    }
    
    /**
     * Accessor method for suit
     * 
     * @return The value of the suit field
     */
    public Suit getSuit() {
        
        return suit;
    }
    
    /**
     * Creates and returns a filepath to the image that matches this card's rank 
     * and suit so that it can be rendered.
     * 
     * @return The full filepath to the matching jpg file
     */
    public String getFilepath() {
        
        String filepath = "images\\";
        
        filepath += rank.getString() + "_" + suit.getString() + ".jpg";
        
        return filepath;
    }
    
    /**
     * Returns a string in the format rank of suit. ex. QUEEN of HEARTS
     * 
     * @return A string containing the rank and suit of this card.
     */
    public String toString() {
        return rank + " of " + suit;
    }
    
    /**
     * Compares the rank and suit fields of this object against another card object
     * to determine if they are equal.
     * 
     * @param c another Card object
     * @return true if the fields are equal, false otherwise
     */
    public boolean equals(Card c) {
        
        boolean result = false;
        
        if (rank == c.rank && suit == c.suit) {
            result = true;
        }
        return result;
    }
}
