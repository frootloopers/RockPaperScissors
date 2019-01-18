/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.*;
import Entities.Map;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Jia Jia Chen
 */
public class DebugPanel extends javax.swing.JPanel {

    GameFrame origin;

    Timer t = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            repaint();
        }
    });

    /**
     * Creates new form DebugPanel
     */
    public DebugPanel(GameFrame origin) {
        this.origin = origin;
        initComponents();
        t.start();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        Map temp = origin.getMapData();
        Controllable[] controls = temp.getControllables();
        for (int x = 0; x < controls.length; x++) {
            g.drawString(controls[x].getPos().x + " " + controls[x].getPos().y, 0, x * 10);
        }
        Harvestable[] harvest = temp.getHarvest();
        for (int x = 0; x < harvest.length; x++) {
            g.drawString(harvest[x].getPos().x + " " + harvest[x].getPos().y, 100, x * 10);
        }
        Planet[] planets = temp.getPlanets();
        for (int x = 0; x < planets.length; x++) {
            g.drawString(planets[x].getPos().x + " " + planets[x].getPos().y, 200, x * 10);
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
