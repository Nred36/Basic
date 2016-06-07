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
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author naree1878
 */
public class FinalProject extends JApplet implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    Graphics2D myPic;
    Image dbImage, master;
    private Graphics dbg;
    Timer timer;
    int x, y, mode = 0;
    String[] picz = new String[5];
    Image[] in = new Image[5];
    int[][] count = new int[10][2];
    int num = 6, pic = (int) Math.ceil(Math.random()), w, h;
    BufferedImage img[] = new BufferedImage[num * num];
    int crnd = (int) Math.ceil(Math.random() * 9);

    public FinalProject() {//program name

        timer = new Timer(60, this);
        timer.setInitialDelay(100);     //starts timer
        timer.start();
        /**
         * @param args the command line arguments
         */

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        Timer run = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        }
        );
        run.setRepeats(true);
        run.start();
        try {//READ
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
        JFrame f = new JFrame(""); //name on program
        JApplet applet = new FinalProject();        //sets up the window
        f.getContentPane().add("Center", applet);
        applet.init();
        f.pack();

        f.setVisible(true); //makes it visible
        f.setResizable(false);//makes in unsizable
        f.setBounds(25, 25, 1185, 649);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //stops program if you x out the window
    }
// <editor-fold defaultstate="collapsed" desc="paint">

    public void paint(Graphics g) {
        dbImage = createImage(getWidth(), getHeight());      //creats and image the size of the screen
        dbg = dbImage.getGraphics();        //double buffers the panel
        paintComponent(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }
// </editor-fold>

    public void paintComponent(Graphics g) {
        myPic = (Graphics2D) g;

        if (mode == 0) {
            myPic.setFont(new Font("Dialog", Font.PLAIN, 15));
            myPic.drawString("Game 1", 242, 292);
            myPic.drawString("Game 2", getWidth() / 2 - myPic.getFontMetrics().stringWidth("Game 2"), 292);
            myPic.drawString("Game 3", getWidth() - getWidth() / 4 - myPic.getFontMetrics().stringWidth("Game 3"), 292);

            myPic.drawRect(240, 276, 54, 20);

        } else if (mode == 1) {
            myPic.drawString("Counting", getWidth() / 2 - myPic.getFontMetrics().stringWidth("Counting"), 92);

            for (int i = 0; i < crnd; i++) {
                myPic.drawRect(count[i][0], count[i][1], 50, 50);
            }
        } else if (mode == 2) {

        } else if (mode
                == 3) {
            int c = 0;
            for (int y = 0; y < num; y++) {
                for (int x = 0; x < num; x++) {
                    myPic.drawImage(img[c++], w * x + x, h * y + y, null);
                }
            }
        }
    }

    public void split() {
        w = in[pic].getWidth(null) / num;
        h = in[pic].getHeight(null) / num;
        int c = 0;
        for (int x = 0; x < num; x++) {
            for (int y = 0; y < num; y++) {
                img[c] = new BufferedImage(w, h, 5);
                Graphics2D gr = img[c++].createGraphics();
                gr.drawImage(in[pic], 0, 0, w, h, w * y, h * x, w * y + w, h * x + h, null);
                gr.dispose();
            }
        }
    }

    public void counting() {
        int tempx, tempy;
        System.out.println(crnd);
        for (int i = 0; i < crnd; i++) {
            tempx = (int) Math.ceil(Math.random() * (getWidth() - 90));
            tempy = (int) Math.ceil(Math.random() * (getHeight() - 140) + 70);
            Rectangle r = new Rectangle(tempx, tempy, 70, 70);
            int t = 0;
            for (int c = 0; c < i; c++) {
                if (r.intersects(count[c][0], count[c][1], 70, 70)) {
                    System.out.println("DD");
                    t = 123;
                    c = 123;
                } else {
                    t = c;
                }
            }
            if (t != 123) {
                count[i][0] = tempx;
                count[i][1] = tempy;
            } else {
                i--;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e
    ) {
        repaint();
        requestFocus();
        setFocusTraversalKeysEnabled(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (mode == 1) {
            if (e.getKeyChar() == '1' || e.getKeyChar() == '2' || e.getKeyChar() == '3' || e.getKeyChar() == '4' || e.getKeyChar() == '5' || e.getKeyChar() == '1' || e.getKeyChar() == '6' || e.getKeyChar() == '7' || e.getKeyChar() == '8' || e.getKeyChar() == '9') {
                if (crnd == Integer.parseInt(String.valueOf(e.getKeyChar()))) {
                    System.out.println("Correct");
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e
    ) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public void mousePressed(MouseEvent e) {
        Rectangle m = new Rectangle(e.getX(), e.getY());
        Rectangle b1 = new Rectangle(240, 276, 54, 20);
        if (m.intersects(b1)) {
            mode = 1;

            counting();
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
