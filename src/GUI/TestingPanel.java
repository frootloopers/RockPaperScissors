/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Development.Command;
import Entities.*;
import Foundation.Map;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
    boolean playing = false;
    int refreshRate = 10;
    int gameSpeed = 10;

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
//            System.out.println();
            GameBoard.moveAll();
        }
    });

        //listen to the mouse (for when a user clicks)
        MouseListener mListener = new MouseListener() {
            public void mouseClicked(MouseEvent ms) {
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

        //listen to the mouse more closely (for when the user is holding a button down)
        MouseMotionListener mMListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent ms) {
            }

            public void mouseMoved(MouseEvent ms) {
            }
        };

    /**
     * Creates new form GamePanel
     */
    public TestingPanel() {
        initComponents();
        GameBoard = new Map(teams, mapX, mapY);
        GameBoard.reset();
        GameBoard.getControllables()[0].setThrustF(10);
//        d = new Drone(100.0, 100.0, 135.0, 1, GameBoard);
//        s = new Ship(500.0, 300.0, 135.0, 1, GameBoard);

        addMouseListener(mListener);
        addMouseMotionListener(mMListener);
        setFocusable(true);
    }
    
    

    /**
     * By Jia Jia:
     */
    public void togglePlay() {
        if (playing) {
            playing = false;
            t.stop();
            t2.stop();
        } else {
            playing = true;
            t.start();
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
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, mapX, mapY);

        updateGraphics(g, GameBoard);
        GameBoard.getControllables()[0].setThrustF(100);

        /*
         Way to do AI:
         entity.draw(g, zoom, offsetX, offsetY);
         //algorithm
         entity.move();
         */
        d.draw(g, zoom, offsetX, offsetY);
        d.setThrustF(100);
        d.move();

        s.draw(g, zoom, offsetX, offsetY);
        s.setThrustF(100);
        s.setThrustRotR(100); //ccw
        s.move();

        target.draw(g, zoom, offsetX, offsetY);
        spin.draw(g, zoom, offsetX, offsetY);
        //Command.turnTo(spin, s.getPos(), 0.5);
        Command.getTo(spin, s.getPos(), 2.5);
//        if (Command.turnTo(spin, target.getPos(), 0.5)) {
//            System.out.println("THRUSTING");
//            spin.setThrustF(100);
//        } else {
//            System.out.println("SPINNING");
//            spin.setThrustF(0);
//        }
        spin.move();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
