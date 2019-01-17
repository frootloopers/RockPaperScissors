/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Development.Command;
import Blocks.Pos;
import Entities.*;
import Foundation.Map;
import java.awt.Color;
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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
    Ship s;
    Ship target;
    Ship spin;

    double zoom = 1;
    int offsetX = 0;
    int offsetY = 0;
    int mapX = 600;
    int mapY = 600;
    int teams = 4;
    int refreshRate = 10;
    int gameSpeed = 10;
    boolean playing = false;
    boolean showRes = false;
    Point mouse = new Point(0, 0);
    GameFrame gameframe;

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
            GameBoard.moveAll();
        }
    });

    KeyListener kListener = new KeyListener() {
        public void keyTyped(KeyEvent key) {
        }

        private final Set<Integer> pressed = new HashSet<Integer>();

        public void keyPressed(KeyEvent key) {
            pressed.add(key.getKeyCode());
            for (Integer k : pressed) {
                switch (k) {
                    case KeyEvent.VK_1:
                        gameSpeed = 3;
                        break;
                    case KeyEvent.VK_2:
                        gameSpeed = 5;
                        break;
                    case KeyEvent.VK_3:
                        gameSpeed = 10;
                        break;
                    case KeyEvent.VK_4:
                        gameSpeed = 15;
                        break;
                    case KeyEvent.VK_5:
                        gameSpeed = 20;
                        break;
                    case KeyEvent.VK_R:
                        if (showRes) {
                            showRes = false;
                        } else {
                            showRes = true;
                        }
                        break;
                }
            }
            t2.setDelay(gameSpeed);
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

    /**
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

    /**
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

    /**
     * By Jia Jia: Allows zoom modification via mouse wheel.
     */
    MouseWheelListener mWListener = new MouseWheelListener() {
        public void mouseWheelMoved(MouseWheelEvent ms) {
            double zoomChange = (double) (ms.getWheelRotation()) / 100;
            zoom -= zoomChange;
        }
    };

    /**
     * Creates new form GamePanel
     */
    public TestingPanel(GameFrame gameframe) {
        initComponents();
        this.gameframe = gameframe;
        GameBoard = new Map(teams, mapX, mapY);
        GameBoard.reset();
        GameBoard.getControllables()[0].setThrustF(10);
//        d = new Drone(100.0, 100.0, 135.0, 1, GameBoard);
//        s = new Ship(500.0, 300.0, 135.0, 1, GameBoard);

        //attach the listeners when constructing the panel
        addMouseListener(mListener);
        addMouseMotionListener(mMListener);
        addMouseWheelListener(mWListener);
        addKeyListener(kListener);
        setFocusable(true);
        t.start();
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
    private void updateGraphics(Graphics g, Map m) {
        for (Controllable c : m.getControllables()) {
            c.draw(g, zoom, offsetX, offsetY);
        }
        if (showRes) {
            for (Controllable c : m.getControllables()) {
                c.showRes(g, zoom, offsetX, offsetY);
            }
        }
        for (Bullet b : m.getBullets()) {
            b.draw(g, zoom, offsetX, offsetY);
        }
        for (Harvestable h : m.getHarvest()) {
            if (h != null) {
                h.draw(g, zoom, offsetX, offsetY);
            }
        }
        for (Planet p : m.getPlanets()) {
            p.draw(g, zoom, offsetX, offsetY);
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
        Image img = Toolkit.getDefaultToolkit().getImage("src/spaceRazeBackground1.png");
        //a.setThrustF(5);
//        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        //Draw background
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, mapX, mapY);
        //Draw gameboard
        g.setColor(Color.WHITE);
        g.fillRect((int) (offsetX * zoom), (int) (offsetY * zoom), (int) (mapX * zoom), (int) (mapY * zoom));

        updateGraphics(g, GameBoard);
        GameBoard.getControllables()[0].setThrustF(100);

//        /*
//         Way to do AI:
//         entity.draw(g, zoom, offsetX, offsetY);
//         //algorithm
//         entity.move();
//         */
//        d.draw(g, zoom, offsetX, offsetY);
//        d.setThrustF(100);
//        d.move();
//
//        s.draw(g, zoom, offsetX, offsetY);
//        s.setThrustF(100);
//        s.setThrustRotR(100); //ccw
//        s.move();
//
//        target.draw(g, zoom, offsetX, offsetY);
//        spin.draw(g, zoom, offsetX, offsetY);
//        //Command.turnTo(spin, s.getPos(), 0.5);
//        Command.getTo(spin, s.getPos(), 2.5);
//        if (Command.turnTo(spin, target.getPos(), 0.5)) {
//            System.out.println("THRUSTING");
//            spin.setThrustF(100);
//        } else {
//            System.out.println("SPINNING");
//            spin.setThrustF(0);
//        }
//        spin.move();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
