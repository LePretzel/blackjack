import java.util.ArrayList;  // Needed for the deck.
import java.util.List;       // Needed for the deck. 
import java.util.Random;     // Needed to shuffle randomly.

/**
 * Name:    Vince Hammers
 * Date:    4/21/2018
 * File(s): Dealer.java
 * Course:  CPS 176-H2
 * Purpose: Extends the Player class to be able to shuffle and deal.
 */

public class Dealer extends Player {

    private List<Card> deck; // The deck of cards that will be dealt from.
    
    /**
     * This constructor initializes the dealer's name and calls the shuffle method.
     * 
     * @param n The dealer's name.
     */
    public Dealer(String n) {
        
        super(n);
        shuffle();
    }
    
    
    /**
     * Initializes the deck and shuffles by selecting a random card in the range of 
     * the shuffleList's size. 
     */
    public void shuffle() {
        
        Random rand = new Random();
        List<Card> shuffleList = new ArrayList<Card>();
        
        // Initialize the 52 card deck in order.
        for (int r = 0; r < 13; r++) {
            for (int s = 0; s < 4; s++) {
                shuffleList.add(new Card(r, s));
            }
        }

        // Move the cards in the shuffleList to the deck in random order.
        deck = new ArrayList<Card>();
        
        for (int i = 0; i < 52; i++) {
            deck.add(shuffleList.remove(rand.nextInt(shuffleList.size())));
        }
    }
    
    /**
     * This method adds a card to a player's hand.
     * 
     * @param p The player to deal to.
     */
    public void deal(Player p) {
        
        p.addCard(deck.remove(deck.size()-1));
    }
    
    /**
     * This method adds n cards to a player's hand.
     * 
     * @param p The player to deal to.
     * @param n The number of cards to deal.
     */
    public void deal(Player p, int n) {
        
        for (int i = 0; i < n; i++) {
            p.addCard(deck.remove(deck.size()-1));
        }
    }
    
    /**
     * This method is used to find out the number of cards remaining in the deck.
     * 
     * @return The amount of cards in the deck.
     */
    public int getDeckSize() {
       
        return deck.size(); 
    }
}
