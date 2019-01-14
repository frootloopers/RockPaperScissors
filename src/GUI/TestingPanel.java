/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.*;
import Foundation.Map;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    /**
     * Creates new form GamePanel
     */
    public TestingPanel() {
        initComponents();
        GameBoard = new Map(4);
        Timer t = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                repaint();
            }
        });
        //starts running the timer
        t.start();
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
        Ship a = new Ship(400.0, 400.0, 0.0, 5, GameBoard);
        //a.setThrustF(5);
        g.drawImage(img, 0, 0, 400, 300, this);
        a.draw(g, 1, 0, 0);

        Map map = new Map(1);
        Drone d = new Drone(100.0, 100.0, 135.0, 1, map);
        d.draw(g, 1.0, 1, 1);
        d.setThrustF(100);
        d.move();
        System.out.println(d.getPos().x);
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
