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
    int mapX = 1200;
    int mapY = 1200;
    int teams = 4;
    int refreshRate = 1;
    int gameSpeed = 10;
    boolean playing = false;
    boolean showRes = false;
    int graphicsMode = 0;
    Point mouse = new Point(0, 0);
    Image img = Toolkit.getDefaultToolkit().getImage("src/spaceRazeBackground1.png");
    GameFrame gameframe;
    Controllable selected = null;

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
            if (GameBoard.getTime() >= Map.maxTime) {
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
             * O to manually take control of the unit your pointer is over. Press O again to lose control.
             * W to move forward, A and D to turn the unit you control.
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
                    case KeyEvent.VK_SPACE:
                        gameframe.clickToggleButton1();
                        break;
                    case KeyEvent.VK_O:
                        manualOverride();
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
                        if (graphicsMode > 2) {
                            graphicsMode = 0;
                        }
                        break;
                    case KeyEvent.VK_W:
                        if (selected != null) {
                            selected.setThrustF(100);
                        }
                        break;
                    case KeyEvent.VK_A:
                        if (selected != null) {
                            selected.setThrustRotR(100);
                        }
                        break;
                    case KeyEvent.VK_D:
                        if (selected != null) {
                            selected.setThrustRotL(100);
                        }
                        break;
                    case KeyEvent.VK_N:
                        if (selected instanceof Ship) {
                            ((Ship) selected).fireBullet();
                        }
                        break;
                    case KeyEvent.VK_M:
                        if (selected instanceof Ship) {
                            ((Ship) selected).pulse();
                        }
                        break;
                }
            }
        }

//        public void keyHeld(KeyEvent key) {
//            if (selected != null) {
//                switch (key.getKeyCode()) {
//                    case KeyEvent.VK_W:
//                        if (selected != null) {
//                            selected.setThrustF(100);
//                        }
//                        break;
//                    case KeyEvent.VK_A:
//                        if (selected != null) {
//                            selected.setThrustRotR(100);
//                        }
//                        break;
//                    case KeyEvent.VK_D:
//                        if (selected != null) {
//                            selected.setThrustRotL(100);
//                        }
//                        break;
//                    case KeyEvent.VK_N:
//                        if (selected instanceof Ship) {
//                            ((Ship) selected).fireBullet();
//                        }
//                        break;
//                    case KeyEvent.VK_M:
//                        if (selected instanceof Ship) {
//                            ((Ship) selected).pulse();
//                        }
//                        break;
//                }
//            }
//        }
        public void keyReleased(KeyEvent key) {
            //
            pressed.remove(key.getKeyCode());
            //
            if (selected != null) {
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_W:
                        selected.setThrustF(0);
                        break;
                    case KeyEvent.VK_A:
                        selected.setThrustRotR(0);
                        break;
                    case KeyEvent.VK_D:
                        selected.setThrustRotL(0);
                        break;
                }
            }
        }
    };

    /**
     * Jia Jia
     */
    MouseListener mListener = new MouseListener() {
        //right click to do one game loop
        public void mouseClicked(MouseEvent ms) {
            if (SwingUtilities.isRightMouseButton(ms)) {
                GameBoard.moveAll();
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

    /**
     * Jia Jia
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
            if (zoom < 0.02) {
                zoom = 0.02;
            }
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

    public void timerUpdate() {
        t2.setDelay(gameSpeed);
    }

    /**
     * By Jia Jia:
     */
    private void manualOverride() {
        for (Controllable c : GameBoard.getControllables()) {
            //detect if the cursor is over a controllable
            double dist = Math.sqrt(Math.pow(((mouse.x - (offsetX * zoom)) / zoom) - (c.getPos().x), 2) + (Math.pow(((mouse.y - (offsetY * zoom)) / zoom) - (c.getPos().y), 2)));
            if (dist <= c.getRadius()) {
                selected = c;
                return;
            } else {
                selected = null;
            }
        }
    }

    /**
     * By
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
        for (Harvestable h : GameBoard.getHarvest()) {
            if (h != null) {
                h.draw(g, zoom, offsetX, offsetY);
            }
        }
        for (Bullet b : GameBoard.getBullets()) {
            b.draw(g, zoom, offsetX, offsetY);
        }
        //highlight the player's character
        if (selected != null) {
            highlight(g);
        }
        for (Controllable c : GameBoard.getControllables()) {
            c.draw(g, zoom, offsetX, offsetY);
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
        //show the button list GUI before the game starts
        if (GameBoard.getTime() == 0) {
            showKeys(g);
        }
    }

    Font tiny = new Font("TimesRoman", Font.PLAIN, 10);

    /**
     * Jia Jia: Shows pressing what keys does what.
     *
     * @param g
     */
    private void showKeys(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(-1, 268, getWidth() + 1, 72);
        g.setColor(Color.BLACK);
        g.drawRect(-1, 268, getWidth() + 1, 72);
        g.setFont(tiny);
        g.drawString("This software is brought to you by Foresight Software: Carl Wu, John Popovici, and Jia Jia Chen.", 5, 280);
        g.drawString("Special Thanks to Luke Classen, Sean Zhang, and the legendary Mr. RD.", 5, 295);
        g.drawString("Press R to show developer stats, G to change background palette, SPACE to start/stop the simulation,", 5, 320);
        g.drawString("MOUSE2 to do one game loop, O to take manual control of a controllable, W, A, and D to move, and N and M to use abilities.", 5, 335);
    }

    /**
     * Jia Jia: Highlights the selected entity
     *
     * @param g
     */
    private void highlight(Graphics g) {
        int rad = selected.getRadius() + 3;
        int x1 = (int) ((selected.getPos().x - rad + offsetX) * zoom);
        int y1 = (int) ((selected.getPos().y - rad + offsetY) * zoom);
        g.setColor(Color.ORANGE);
        g.fillOval(x1, y1, (int) (rad * 2 * zoom), (int) (rad * 2 * zoom));
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

        //Background stuff by Jia Jia
        //Draw background
        switch (graphicsMode) {
            case 0:
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(Color.CYAN);
                //Draw gameboard background
                g.fillRect((int) (offsetX * zoom), (int) (offsetY * zoom), (int) (mapX * zoom), (int) (mapY * zoom));
                break;
            case 1:
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(Color.BLACK);
                g.fillRect((int) (offsetX * zoom), (int) (offsetY * zoom), (int) (mapX * zoom), (int) (mapY * zoom));
                break;
            case 2:
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                g.drawImage(img, (int) (offsetX * zoom), (int) (offsetY * zoom), (int) (mapX * zoom), (int) (mapY * zoom), this);

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
                break;
        }

        updateGraphics(g);

        //testing
        /*
        GameBoard.getControllables()[0].setThrustF(100);
        ((Ship) (GameBoard.getControllables()[0])).fireBullet(); 
        GameBoard.getControllables()[1].setThrustF(100);
        GameBoard.getControllables()[2].setThrustF(100);
        GameBoard.getControllables()[3].setThrustF(100);
        GameBoard.getControllables()[4].setThrustF(100);
        GameBoard.getControllables()[5].setThrustF(100);
        GameBoard.getControllables()[6].setThrustF(100);
        GameBoard.getControllables()[7].setThrustF(100);
        GameBoard.getControllables()[8].setThrustF(100);
        GameBoard.getControllables()[9].setThrustF(100);
         */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
