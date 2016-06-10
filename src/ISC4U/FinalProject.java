/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ISC4U;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author naree1878
 */
public class FinalProject extends JApplet implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    //Graphic Variables
    Graphics2D myPic;
    Image dbImage, master;
    private Graphics dbg;
    Timer timer;
    //Used across the board
    String[] picz = new String[7];
    Image[] in = new Image[7];
    int rnd, last = 99;
    String dis = "";
    int mode = 0;
    //used only in counting
    int crnd = (int) Math.ceil(Math.random() * 10);
    int[][] count = new int[10][2];
    int correct = 0, tries = 0;

    //used only in split
    int num = 3, pic = (int) Math.ceil(Math.random()), w, h;
    BufferedImage img[] = new BufferedImage[num * num];

    public FinalProject() {//program name

        timer = new Timer(60, this);
        timer.setInitialDelay(100);     //Starts a timer that refreshes the grpahics every 100ms
        timer.start();

        addMouseListener(this);//adds listners which check for presses in their respective types
        addMouseMotionListener(this);
        addKeyListener(this);

        try {
            FileReader fr = new FileReader("save.txt"); //reads from text file (located in "files"
            BufferedReader br = new BufferedReader(fr);
            //read and puts each line in the text document into a variable
            for (int i = 0; i < picz.length; i++) {
                picz[i] = br.readLine();
                in[i] = new ImageIcon(picz[i]).getImage();
            }
            fr.close();
            br.close();
        } catch (IOException a) {
            System.out.println("Couldn't Load");//if it fails
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame(""); //creats and names the jframe
        JApplet applet = new FinalProject(); //makes the program into a japplet
        f.getContentPane().add("Center", applet); //adds the applet to the jframe     
        f.setVisible(true); //makes it visible
        f.setResizable(false);//makes in unresizable
        f.setBounds(25, 25, 1185, 649);//sets its position on the screen
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //stops program if you x out the window
    }
// <editor-fold defaultstate="collapsed" desc="paint">

    public void paint(Graphics g) {
        dbImage = createImage(getWidth(), getHeight());      //creats and image the size of the screen
        dbg = dbImage.getGraphics();        //double buffers the panel
        paintComponent(dbg);//runs paintcomponent with the double buffer
        g.drawImage(dbImage, 0, 0, this);
    }
// </editor-fold>

    public void paintComponent(Graphics g) {
        myPic = (Graphics2D) g;

        if (mode == 0) { //for start screen
            myPic.setFont(new Font("Dialog", Font.PLAIN, 15));
            myPic.drawString("Game 1", getWidth() / 4 - (myPic.getFontMetrics().stringWidth("Game 1")) / 2, 292);
            myPic.drawString("Game 2", getWidth() / 2 - (myPic.getFontMetrics().stringWidth("Game 2")) / 2, 292);
            myPic.drawString("Game 3", getWidth() - getWidth() / 4 - (myPic.getFontMetrics().stringWidth("Game 3")) / 2, 292);

            myPic.drawRect(getWidth() / 4 - (myPic.getFontMetrics().stringWidth("Game 1")) / 2 - 2, 276, 54, 20);
            myPic.drawRect(getWidth() / 2 - (myPic.getFontMetrics().stringWidth("Game 2")) / 2 - 2, 276, 56, 20);
            myPic.drawRect(getWidth() - getWidth() / 4 - (myPic.getFontMetrics().stringWidth("Game 3")) / 2 - 2, 276, 56, 20);
        } else if (mode == 1) {//for counting
            myPic.setFont(new Font("Dialog", Font.PLAIN, 30));
            myPic.drawString("Counting", getWidth() / 2 - (myPic.getFontMetrics().stringWidth("Counting") / 2), 42);

            for (int i = 0; i < crnd; i++) {
                myPic.drawImage(in[rnd], count[i][0], count[i][1], 75, 75, null);//draws the blocks in there random position
            }

            myPic.drawString(dis, getWidth() / 2 - (myPic.getFontMetrics().stringWidth(dis) / 2), 542);//writes correct/incorrect

            myPic.setFont(new Font("Dialog", Font.PLAIN, 15));
            myPic.drawString("Correct: " + correct, getWidth() / 6 - (myPic.getFontMetrics().stringWidth("Correct: " + correct) / 2), 542);
            myPic.drawString("Tries: " + tries, getWidth() / 6 - (myPic.getFontMetrics().stringWidth("Tries: " + tries) / 2), 562);
            if (tries > 0) {
                myPic.drawString("Percent Correct: " + Math.round((double) correct / tries * 100) + "%", getWidth() / 6 - (myPic.getFontMetrics().stringWidth("Percent Correct: " + (100)) / 2), 582);
            }
        } else if (mode == 2) {//for schedule
            myPic.drawImage(in[6], 900, getHeight()/2-250,100,100, null);
            myPic.drawImage(in[6], 900, getHeight()/2-50,100,100, null);
            myPic.drawImage(in[6], 900, getHeight()/2+150,100,100, null);
        } else if (mode == 3) {//for split
            myPic.fillRect(0, 0, getWidth(), getHeight());
            int c = 0;
            for (int y = 0; y < num; y++) {
                for (int x = 0; x < num; x++) {
                    myPic.drawImage(img[c++], w * x + x, h * y + y, null);//draws the image grid
                }
            }
        }
    }

    public void counting() {
        int tempx, tempy;
        for (int i = 0; i < crnd; i++) {//creats the number of images predetimed
            tempx = (int) Math.ceil(Math.random() * (getWidth() - 120) + 20);//creats new random x and y
            tempy = (int) Math.ceil(Math.random() * (getHeight() - 160) + 50);
            Rectangle r = new Rectangle(tempx, tempy, 100, 100);//creates a rectangle where the new potentional shape will be
            int t = 0;
            for (int c = 0; c < i; c++) {//goes through all previous shapes
                if (r.intersects(count[c][0], count[c][1], 100, 100)) {// checks if the new shape whould be to close to an exesting one
                    t = 123;//makes sure next if is false
                    c = 123;//leaves the loop
                }
            }
            if (t != 123) {//there is no problem with the new shapes postion
                count[i][0] = tempx;//sets the new shapes x and y
                count[i][1] = tempy;
            } else {
                i--;//goes back to the previous iteration
            }
        }
    }

    public void schedule() {

    }

    public void split() {
        w = in[pic].getWidth(null) / num;//gets the width and height of the picture
        h = in[pic].getHeight(null) / num;
        int n = 0;
        for (int x = 0; x < num; x++) {
            for (int y = 0; y < num; y++) {
                img[n] = new BufferedImage(w, h, 5);//makes the picture in a buffered image the same size and type
                Graphics2D gr = img[n++].createGraphics();//creates a new graphics object out of the picture array (so it is recorded in the array)
                gr.drawImage(in[pic], 0, 0, w, h, w * y, h * x, w * y + w, h * x + h, null);//draws the picture in  parts 
                gr.dispose();
                //destroys the graphics object
            }
        }
        Random rnd = ThreadLocalRandom.current();//shuffles the place in the array
        for (int i = img.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            BufferedImage a = img[index];
            img[index] = img[i];
            img[i] = a;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e
    ) {
        repaint();//if something happens repaint
        requestFocus();//if something happens ie: click off window it forces its self to the front (needed for key presses)
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());
        if ((e.getKeyChar() == KeyEvent.VK_ESCAPE)) {
            mode = 0;
        }
        if (mode == 1) {//only lets numbers be pressed
            if ((e.getKeyChar() == '0' || e.getKeyChar() == '1' || e.getKeyChar() == '2' || e.getKeyChar() == '3' || e.getKeyChar() == '4' || e.getKeyChar() == '5' || e.getKeyChar() == '6' || e.getKeyChar() == '7' || e.getKeyChar() == '8' || e.getKeyChar() == '9') && !dis.equals("Correct")) {
                if (crnd == Integer.parseInt(String.valueOf(e.getKeyChar())) || (crnd == last * 10)) {//checks if the key pressed is what was generated
                    dis = "Correct";
                    tries++;
                    correct++;
                    last = 99;//resets last
                } else if (e.getKeyChar() == '1' || e.getKeyChar() == '2') {
                    last = Integer.parseInt(String.valueOf(e.getKeyChar()));//sets last

                } else {
                    dis = "Incorrect";
                    tries++;
                }
            } else if (dis.equals("Correct")) {
                dis = "";
                crnd = (int) Math.ceil(Math.random() * 9);//sets up next round
                rnd = (int) Math.ceil(Math.random() * 2 + 4);
                counting();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e
    ) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e
    ) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e
    ) {
        Rectangle m = new Rectangle(e.getX(), e.getY(), 1, 1);
        Rectangle b1 = new Rectangle(getWidth() / 4 - (myPic.getFontMetrics().stringWidth("Game 1")) / 2 - 2, 276, 54, 20);
        Rectangle b2 = new Rectangle(getWidth() / 2 - (myPic.getFontMetrics().stringWidth("Game 2")) / 2 - 2, 276, 56, 20);
        Rectangle b3 = new Rectangle(getWidth() - getWidth() / 4 - (myPic.getFontMetrics().stringWidth("Game 3")) / 2 - 2, 276, 56, 20);
        if (mode == 0 && m.intersects(b1)) {//looks for clicks on buttons
            mode = 1;
            rnd = (int) Math.ceil(Math.random() * 2 + 4);//runs corresponding game
            counting();
        } else if (mode == 0 && m.intersects(b2)) {
            mode = 2;
            schedule();
        } else if (mode == 0 && m.intersects(b3)) {
            mode = 3;
            split();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {
    }
// <editor-fold defaultstate="collapsed" desc="Mouse Enter and Exit">

    @Override
    public void mouseEntered(MouseEvent e
    ) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e
    ) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//</editor-fold>

    @Override
    public void mouseDragged(MouseEvent e
    ) {

    }

    @Override
    public void mouseMoved(MouseEvent e
    ) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
