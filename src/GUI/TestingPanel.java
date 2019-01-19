/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Development.Command;
import Blocks.Pos;
import Entities.*;
import Entities.Map;
import Entities.Team;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author carlw look at dis -> https://github.com/sean01zhang/ICS4UE_CodeRulers
 */
public class TestingPanel extends javax.swing.JPanel {

    Map GameBoard;
    Drone d;
    Drone e;
    Ship s;
    Ship chaser;

    double zoom = 1;
    int offsetX = 0;
    int offsetY = 0;
    int mapX = 600;
    int mapY = 600;
    int teams = 4;
    int refreshRate = 10;
    int gameSpeed = 10;
    //Game time default (30000) is 5 minutes on 100% (game timer at 10)
    int maxTime = 30000;
    boolean playing = false;
    boolean showRes = false;
    int graphicsMode = 0;
    Point mouse = new Point(0, 0);
    Image img = Toolkit.getDefaultToolkit().getImage("src/spaceRazeBackground1.png");
    GameFrame gameframe;

    //---------------------------GUI-UTILITIES----------------------------------
    //Graphics timer
    Timer t = new Timer(refreshRate, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            repaint();
        }
    });
    //Game interaction timer
    Timer t2 = new Timer(gameSpeed, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (GameBoard.getTime() >= maxTime) {
                gameframe.clickToggleButton1();
                endGame();
            } else {
                GameBoard.moveAll();
            }
        }
    });

    /*
     * By Jia Jia: Looks for keyboard inputs, storing them in an array to be
     * processed. Thanks to this revolutionary method, multiple keys can be
     * pressed at once.
     */
    KeyListener kListener = new KeyListener() {
        public void keyTyped(KeyEvent key) {
        }
        private final Set<Integer> pressed = new HashSet<Integer>();

        public void keyPressed(KeyEvent key) {
            pressed.add(key.getKeyCode());
            /*
             * Keyboard buttons:
             * 0-5 for Changing speeds, with 0 being the slowest.
             * R to toggle the developer resources.
             * G to change game skin.
             */
            for (Integer k : pressed) {
                switch (k) {
                    case KeyEvent.VK_1:
                        gameframe.selectComboBox1(0);
                        break;
                    case KeyEvent.VK_2:
                        gameframe.selectComboBox1(1);
                        break;
                    case KeyEvent.VK_3:
                        gameframe.selectComboBox1(2);
                        break;
                    case KeyEvent.VK_4:
                        gameframe.selectComboBox1(3);
                        break;
                    case KeyEvent.VK_5:
                        gameframe.selectComboBox1(4);
                        break;
                    case KeyEvent.VK_R:
                        if (showRes) {
                            showRes = false;
                        } else {
                            showRes = true;
                        }
                        break;
                    case KeyEvent.VK_G:
                        graphicsMode++;
                        if (graphicsMode > 1) {
                            graphicsMode = 0;
                        }
                        break;
                }
            }
        }
//            public void keyHeld(KeyEvent key) {
//                switch (key.getKeyCode()) {
//                    case KeyEvent.VK_LEFT:
//                        xOff += 1;
//                        break;
//                    case KeyEvent.VK_RIGHT:
//                        xOff -= 1;
//                        break;
//                    case KeyEvent.VK_UP:
//                        yOff += 1;
//                        break;
//                    case KeyEvent.VK_DOWN:
//                        yOff -= 1;
//                        break;
//                }
//            }

        public void keyReleased(KeyEvent key) {
            pressed.remove(key.getKeyCode());
        }
    };

    /*
     * By Jia Jia: Right click toggles whether or not the game is in play.
     */
    MouseListener mListener = new MouseListener() {
        public void mouseClicked(MouseEvent ms) {
            if (SwingUtilities.isRightMouseButton(ms)) {
                gameframe.clickToggleButton1();
            }
        }

        public void mouseEntered(MouseEvent ms) {
        }

        public void mouseExited(MouseEvent ms) {
        }

        public void mousePressed(MouseEvent ms) {
        }

        public void mouseReleased(MouseEvent ms) {
        }
    };

    /*
     * By Jia Jia: Allows camera movement via dragging the mouse.
     */
    MouseMotionListener mMListener = new MouseMotionListener() {
        public void mouseDragged(MouseEvent ms) {
            //add the change in mouse position when dragging to the camera position
            offsetX -= mouse.x - ms.getPoint().x;
            offsetY -= mouse.y - ms.getPoint().y;
            //prevent the camera from moving too far
            if (offsetX < -mapX) {
                offsetX = -mapX;
            }
            if (offsetX > mapX) {
                offsetX = mapX;
            }
            if (offsetY < -mapY) {
                offsetY = -mapY;
            }
            if (offsetY > mapY) {
                offsetY = mapY;
            }
            mouse = ms.getPoint();
        }

        public void mouseMoved(MouseEvent ms) {
            mouse = ms.getPoint();
        }
    };

    /*
     * By Jia Jia: Allows zoom modification via mouse wheel.
     */
    MouseWheelListener mWListener = new MouseWheelListener() {
        public void mouseWheelMoved(MouseWheelEvent ms) {
            double zoomChange = (double) (ms.getWheelRotation()) / 50;
            zoom -= zoomChange;
//            int centerX = offsetX + (int) (mapX / 2 * zoom);
//            int centerY = offsetY + (int) (mapY / 2 * zoom);
//            offsetX = offsetX + mouse.x - (int) (mapX / 2);
//            offsetY = offsetY + mouse.y - (int) (mapY / 2);
        }
    };
    //--------------------------------------------------------------------------

    public TestingPanel() {
        initComponents();
        GameBoard = new Map(teams, mapX, mapY);
        GameBoard.reset();
        GameBoard.getControllables()[0].setThrustF(10);

        //John's testing entities
        d = new Drone(100.0, 100.0, 135.0, -1, GameBoard);
        e = new Drone(700.0, 100.0, -135.0, -1, GameBoard);
        s = new Ship(200.0, 400.0, 135.0, -1, GameBoard);
        chaser = new Ship(50.0, 50.0, -90, -1, GameBoard);

        //attach the listeners when constructing the panel
        addMouseListener(mListener);
        addMouseMotionListener(mMListener);
        addMouseWheelListener(mWListener);
        addKeyListener(kListener);
        setFocusable(true);
        t.start();
    }

    public void setFrame(GameFrame gameframe) {
        this.gameframe = gameframe;
    }

    public void timerReset() {
        t2.setDelay(gameSpeed);
    }

    /**
     * By Jia Jia: Wraps up the game.
     */
    private void endGame() {

    }

    /**
     * By Jia Jia: Toggles the state of the game.
     */
    public void togglePlay() {
        if (playing) {
            playing = false;
            t2.stop();
        } else {
            playing = true;
            t2.start();
        }
    }

    /**
     * By Jia Jia: Draw each item on the map.
     */
    private void updateGraphics(Graphics g) {
        for (Controllable c : GameBoard.getControllables()) {
            c.draw(g, zoom, offsetX, offsetY);
        }
        for (Bullet b : GameBoard.getBullets()) {
            b.draw(g, zoom, offsetX, offsetY);
        }
        for (Harvestable h : GameBoard.getHarvest()) {
            if (h != null) {
                h.draw(g, zoom, offsetX, offsetY);
            }
        }
        for (Planet p : GameBoard.getPlanets()) {
            p.draw(g, zoom, offsetX, offsetY);
        }
//        for (Effect e : m.getEffects()) {
//            e.draw(g, zoom, offsetX, offsetY);
//        }
        gameframe.updateScore();
        //activate developer GUI
        if (showRes) {
            updateDev(g);
        }
    }

    static private final int xDev = 180;
    Font norm = new Font("TimesRoman", Font.PLAIN, 12);

    /**
     * By Jia Jia: Draw the developer information on the board.
     *
     * @param g
     * @param m
     */
    private void updateDev(Graphics g) {
        //if developer resources is on
        for (Controllable c : GameBoard.getControllables()) {
            c.showRes(g, zoom, offsetX, offsetY);
        }
        /* 
         * Panel Size: size of this panel in pixels
         * Cursor Panel: the position of the cursor relative to the origin of the panel
         * Cursor Map: the position the cursor points to on the map, taking zoom level and offset into account
         * Map Offset: where the map's origin is offset to
         * Zoom: the multiplier for rendered game objects
         */
        String[] temp = {
            "Panel Size: " + this.getWidth() + ", " + this.getHeight(),
            "Cursor Panel: " + mouse.x + ", " + mouse.y,
            "Cursor Map: " + Math.round((mouse.x - (offsetX * zoom)) / zoom) + ", " + Math.round((mouse.y - (offsetY * zoom)) / zoom),
            "Map Offset: " + (int) (offsetX * zoom) + ", " + (int) (offsetY * zoom),
            "Zoom: " + zoom
        };
        //auto-scale the y to the number of lines in temp
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, xDev, temp.length * 16 + 12);
        g.setColor(Color.PINK);
        g.drawRect(0, 0, xDev, temp.length * 16 + 12);
        g.setFont(norm);
        for (int x = 0; x < temp.length; x++) {
            g.drawString(temp[x], 5, x * 16 + 18);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void paintComponent(Graphics g) {
        //a.setThrustF(5);
//        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

        //Background stuff by Jia Jia
        //Draw background
        switch (graphicsMode) {
            case 0:
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(Color.CYAN);
                break;
            case 1:
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(Color.BLACK);
                break;
        }
        //Draw gameboard background
        g.fillRect((int) (offsetX * zoom), (int) (offsetY * zoom), (int) (mapX * zoom), (int) (mapY * zoom));

        updateGraphics(g);

        //testing
        GameBoard.getControllables()[0].setThrustF(100);
//        ((Ship) (GameBoard.getControllables()[0])).fireBullet();

        /*
         Way to do AI: As Demonstrated by John
         entity.draw(g, zoom, offsetX, offsetY);
         //algorithm code
         entity.move();
         */
        d.draw(g, zoom, offsetX, offsetY);
        d.setThrustF(100);
        d.move();

        e.draw(g, zoom, offsetX, offsetY);
        e.setThrustF(100);
        e.move();

        s.draw(g, zoom, offsetX, offsetY);
        s.setThrustF(100);
        s.setThrustRotR(25); //ccw
        s.move();

        chaser.draw(g, zoom, offsetX, offsetY);
        Command.chase(chaser, s, 50);
        chaser.move();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
