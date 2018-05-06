import java.awt.Dimension;      // Needed to resize window.
import java.io.*;               // Needed to write to Names.txt.
import java.util.Scanner;       // Needed to read from Names.txt.
import java.util.Random;        // Needed for randomization.

import javax.swing.JFrame;      // Needed for game window.
import javax.swing.JOptionPane; // Needed to get user input.

/**
 * Name:    Vince Hammers
 * Date:    4/27/2018
 * File(s): BlackJack.java
 * Course:  CPS 176-H2
 * Purpose: The application's entry point, responsible for all of the game logic.
 */

public class BlackJack {
    
    public static void main(String[] args) throws IOException {       
        
        final int WINDOW_SIZE = 500; // The length and width of the game window.
        final String NAMEPATH = "Names.txt";    // The filepath for Names.txt
        
        String[] names;     // The list of dealer names from the text file
        int[] dealerScores; // The scores for each dealer name
        String input;       // To store input from JOptionPane
        Player player;      // The player
        Dealer dealer;      // The dealer
        Random rand;        // To select names and shuffle deck at random.
        File namesFile;     // The file object for Names.txt
        Scanner in;         // To read input from Names.txt
        PrintWriter pw;     // To print the names and scores back to Names.txt
        
        int count;          // The number of lines in Names.txt
        boolean running;    // Game will end if set to false
        int choice;         // To get input for whether to hit and continue playing.
        int randResult;     // The number returned by Random
        
        JFrame window;      // The window for the game
        CardPanel panel;    // The JPanel subclass that the card are drawn on.
        
        
        // Create game window
        window = new JFrame("Blackjack");
        window.setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        window.setVisible(true);
        
        // Initialize random.
        rand = new Random();
        
        // Read the text file
        try {
            
            namesFile = new File(NAMEPATH);
            
            in = new Scanner(namesFile);
            count = 0;
            // Get the number of lines in Names.txt
            while(in.hasNextLine()) {
                count++;
                in.nextLine();
            }
            in.close();                  // Prevents a resource leak.
            
            // Read the names from Names.txt into the names array.
            in = new Scanner(namesFile); // Reinitialize to read from the first line.
            names = new String[count/2];
            dealerScores = new int[count/2];
            
            for (int i = 0; i < count/2; i++) {
                names[i] = in.nextLine();
                dealerScores[i] = Integer.parseInt(in.nextLine());
            }
            
            in.close();
            
            // Assign a random name to the dealer.
            // Using randResult so the score will match the name.
            randResult = rand.nextInt(names.length);
            
            dealer = new Dealer(names[randResult]);
            dealer.setRoundsWon(dealerScores[randResult]);
        }
        catch(IOException e) {
            
            // At least you still have good ol' Jimmy.
            dealer = new Dealer("Jimmy Gump");
            
            // Arbitrary but allows the game to compile in case of an exception.
            randResult = rand.nextInt(4);
            names = new String[randResult];
            dealerScores = new int[randResult];
        }
        
        
        // Welcome the user.
        JOptionPane.showMessageDialog(window, "Welcome to the casino.");
        
        // Get user input for player name.
        input = JOptionPane.showInputDialog(window, "What's your name?");
        
        // Introduce the dealer.
        JOptionPane.showMessageDialog(window, "Your dealer will be " + 
                                      dealer.getName());
        
        if (!input.equals("")) {
            player = new Player(input);
        }
        else {
            player = new Player("player1");
        }
        
        // main game loop
        running = true;
        
        while (running) {
           
            player.setPlaying(true);
            dealer.setPlaying(true);
            dealer.deal(player, 2);
            dealer.deal(dealer, 2);  
                
            // Show cards
            panel = new CardPanel(window, dealer, player);
            window.add(panel);
            window.setVisible(true);
            
            // Blackjack player
            if (getHandValue(player) == 21) {
                if (getHandValue(dealer) == 21) {
                    JOptionPane.showMessageDialog(window, "You and " +
                                                  dealer.getName() + 
                                                  "both got blackjack. \n" +
                                                   "this game is a tie.");
                    panel.drawHiddenCard();
                    dealer.setPlaying(false);
                    player.setPlaying(false);
                }
                else {
                    JOptionPane.showMessageDialog(window, player.getName() +  
                                                    " got blackjack!");
                    panel.drawHiddenCard();
                    dealer.setPlaying(false);
                    player.setPlaying(false);
                    player.addRoundsWon();
                    showWinner(window, player);
                }
            }
            // Blackjack dealer
            if (getHandValue(dealer) == 21) {
                    JOptionPane.showMessageDialog(window, dealer.getName() + 
                                                  " got blackjack!");
                    panel.drawHiddenCard();
                    dealer.setPlaying(false);
                    player.setPlaying(false);
                    dealer.addRoundsWon();
                    showWinner(window, dealer);
            }
            
            // The player's turn
            while (player.isPlaying() && getHandValue(player) != 21) {
                choice = JOptionPane.showConfirmDialog(window, "Do you want to hit?");
                if (choice == 0) {
                    dealer.deal(player);
                    panel.repaint();
                }
                else {
                    player.setPlaying(false);
                }
                // If the player busts.
                if (getHandValue(player) > 21) {
                    
                    JOptionPane.showMessageDialog(window, player.getName() + 
                            " busted. \n");
                    dealer.addRoundsWon();
                    showWinner(window, dealer);
                    panel.drawHiddenCard();
                    player.setPlaying(false);
                    dealer.setPlaying(false); // Go to the next round.
                    
                }
            }
                
            // The dealer's turn
            while (dealer.isPlaying()) {
                
                panel.drawHiddenCard();
                
                if (getHandValue(dealer) < 17 ) {
                    dealer.deal(dealer);
                    panel.drawHiddenCard();
                }
                
                // If the dealer busts
                if (getHandValue(dealer) > 21) {
                    
                    JOptionPane.showMessageDialog(window, dealer.getName() +
                            " busted. \n");
                    player.addRoundsWon();
                    showWinner(window, player);
                    
                    
                    dealer.setPlaying(false);
                }
                // The dealer stops hitting if 17 or higher
                else if (getHandValue(dealer) > 16) {
                    
                    dealer.setPlaying(false);
                    
                    if (getHandValue(dealer) > getHandValue(player)) {
                        dealer.addRoundsWon();
                        showWinner(window, dealer);
                    }
                    else if (getHandValue(dealer) < getHandValue(player)) {
                        player.addRoundsWon();
                        showWinner(window, player);
                    }
                    else {
                        JOptionPane.showMessageDialog(window, "This round is a tie.");
                    }
                }
            }
            
            player.clearHand();
            dealer.clearHand();
            
            // Shuffle when the deck gets low.
            // Ensures that it's not possible to run out of cards.
            if (dealer.getDeckSize() < rand.nextInt(10) + 17) {
                dealer.shuffle();
                JOptionPane.showMessageDialog(window, "The deck has been shuffled.");
            }
            
            // Keeps playing until the user doesn't hit yes.
            choice = JOptionPane.showConfirmDialog(window,
                    "Do you want to continue playing?");
            
            if (choice != 0) {
                running = false;
            }
        }

        // Thank the user for playing and display win counts.
        JOptionPane.showMessageDialog(window, "Thank you for playing!");
        JOptionPane.showMessageDialog(window, dealer.getName() + ": " + 
                                      dealer.getRoundsWon() + " wins\n" +
                                      player.getName() + ": " +
                                      player.getRoundsWon() + " wins");
        
        // Write the names and updated scores back to the file.
        try {
            // Store the updated win count back in the same array.
            dealerScores[randResult] = dealer.getRoundsWon();
            
            pw = new PrintWriter(new File(NAMEPATH));
            
            for (int i = 0; i < names.length; i++) {
                pw.println(names[i]);
                pw.println(dealerScores[i]);
            }
            
            pw.close();
        }
        catch (IOException e) {
            
        }
        
        
        System.exit(0);
    }
    
    /**
     * Calculates the value of a player object's hand.
     * 
     * @param p The player(or dealer) object.
     * @return  The value of p's hand.
     */
    private static int getHandValue(Player p) {
        
        int total = 0;
        int aces = 0;
        
        for (int i = 0; i < p.getHandSize(); i++) {
            
            switch(p.getCardInHand(i).getRank()) {
            case ACE:
                aces++;
                break;
            case TWO:
                total += 2;
                break;
            case THREE:
                total += 3;
                break;
            case FOUR:
                total += 4;
                break;
            case FIVE:
                total += 5;
                break;
            case SIX:
                total += 6;
                break;
            case SEVEN:
                total += 7;
                break;
            case EIGHT:
                total += 8;
                break;
            case NINE: 
                total += 9;
                break;
            case TEN:
            case JACK:
            case QUEEN:
            case KING:
                total += 10;
                break;
            }
        }
        for (int i = 0; i < aces; i++) {
            if (total > 10) {
                total++;
            }
            else {
                total += 11;
            }
        }
                
        return total;
    }
    
    /**
     * A more convenient way to show the winning message.
     * 
     * @param frame A JFrame object
     * @param p     A player object
     */
    private static void showWinner(JFrame frame, Player p) {
        
        JOptionPane.showMessageDialog(frame, "Winner \n" + p + 
                    "\nTotal Points: " + getHandValue(p));
    }
}