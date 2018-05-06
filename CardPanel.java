import java.awt.Graphics;            // Needed for the Graphics class.
import java.awt.Image;               // Needed for the Image class.
import java.awt.image.BufferedImage; // Needed for the BufferedImage class.
import java.io.*;                    // Needed for file input and output.
import java.util.List;               // Needed for the List class.

import javax.imageio.ImageIO;        // Needed for reading and writing images
import javax.swing.JFrame;           // Needed for the JFrame class
import javax.swing.JPanel;           // Needed to subclass JPanel.

/**
 * Name:    Vince Hammers
 * Date:    5/01/2018
 * File(s): CardPanel.java
 * Course:  CPS 176-H2
 * Purpose: A JPanel subclass that draws cards for the blackjack game.
 */

class CardPanel extends JPanel{
    
    private static final long serialVersionUID = 1L; // Eclipse told me to put this.
    
    private BufferedImage bimg;    // The buffered image from reading a file.
    private Image image;           // The resized image from the buffered image.
    private List<Card> dealerHand; // The dealer's hand.
    private List<Card> playerHand; // The player's hand.
    private int imgWidth;          // The width of each card image.
    private int imgHeight;         // The height of each card image.
    private int frameHeight;       // The height of the JFrame.
    
    // The filepath for the face-down card image.
    private String hiddenCard = "images\\Backface_Blue.jpg";
    
    /**
     * This constructor gets the image sizes by dividing the width and height of
     * the JFrame so that they'll scale with the window.
     *  It also gets a reference to the dealer's and player's hands.
     * 
     * @param frame  The JFrame that this will contain this.
     * @param dealer The dealer object.
     * @param player The player object.
     */
    public CardPanel(JFrame frame, Player dealer, Player player) {
        
        imgWidth = frame.getWidth()/8;
        imgHeight = frame.getHeight()/5;
        frameHeight = frame.getHeight();
        
        dealerHand = dealer.getHand();
        playerHand = player.getHand();
        
    }
    
    /**
     * Used to draw the dealer and player's hands.
     * 
     * @param g A graphics object used to draw on the JPanel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        // Draw the dealer's face down card.
        try { 
            bimg = ImageIO.read(new File(hiddenCard));
            image = bimg.getScaledInstance(imgWidth, imgHeight,
                    Image.SCALE_SMOOTH);
            g.drawImage(image, 0, 0, this);

            // Draw the rest of the dealer's hand.
            for (int i = 1; i < dealerHand.size(); i++) { 
                    bimg = ImageIO.read(new File(dealerHand.get(i).getFilepath()));
                    image = bimg.getScaledInstance(imgWidth, imgHeight,
                            Image.SCALE_SMOOTH);
                    g.drawImage(image, imgWidth * i, 0, this);
            }
        
            // Draw the player's hand.
            for (int i = 0; i < playerHand.size(); i++) {
                    bimg = ImageIO.read(new File(playerHand.get(i).getFilepath()));
                    image = bimg.getScaledInstance(imgWidth, imgHeight,
                            Image.SCALE_SMOOTH);
                    g.drawImage(image, imgWidth * i,
                            frameHeight - frameHeight/4 - imgHeight/7, this);

            }
        }
        catch (IOException e) {
            
        }
    }
    
    /**
     * Draws the cards with the hidden card face-up.
     */
    public void drawHiddenCard() {
        
        hiddenCard = dealerHand.get(0).getFilepath();
        this.repaint();
    }
}
