/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Development.AI;
import Development.DummyAI;
import Entities.Map;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Jia Jia
 */
public class GameFrame extends javax.swing.JFrame {

    AI[] ais;

    /**
     * Creates new form GameFrame
     */
    public GameFrame() {
        ais=new AI[4];
        for(int x=0;x<ais.length;x++){
            ais[x]=new DummyAI();
        }
        setup();
    }

    public GameFrame(AI[] ais) {
        this.ais = ais;
        setup();
    }

    public AI[] getAIs() {
        System.out.print("");
        return ais;
    }

    public void setup() {
        initComponents();
        setTitle("Space;Raze");
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/spaceraze.png"));
        //This is needed to pass this instance of GameFrame to the panel since when I override the Panel constructor, it breaks the preview in the Frame. - Jia Jia
        testingPanel1.setFrame(this);
        testingPanel1.setupMap();
        scoreboard1.linkMap(testingPanel1.GameBoard);

    }

    boolean resetConfirm = false;

    // Jia Jia: timer activates to reset the reset button state if ignored.
    Timer t = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            resetConfirm = false;
            jButton1.setText("Reset");
            t.stop();
        }
    });

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        testingPanel1 = new GUI.TestingPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        scoreboard1 = new GUI.Scoreboard();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        testingPanel1.setPreferredSize(new java.awt.Dimension(600, 600));
        testingPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                testingPanel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout testingPanel1Layout = new javax.swing.GroupLayout(testingPanel1);
        testingPanel1.setLayout(testingPanel1Layout);
        testingPanel1Layout.setHorizontalGroup(
            testingPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        testingPanel1Layout.setVerticalGroup(
            testingPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jToggleButton1.setText("Play");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10%", "50%", "100%", "200%", "300%" }));
        jComboBox1.setSelectedIndex(2);
        jComboBox1.setToolTipText("");
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout scoreboard1Layout = new javax.swing.GroupLayout(scoreboard1);
        scoreboard1.setLayout(scoreboard1Layout);
        scoreboard1Layout.setHorizontalGroup(
            scoreboard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        scoreboard1Layout.setVerticalGroup(
            scoreboard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(testingPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, 185, Short.MAX_VALUE)
                    .addComponent(scoreboard1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scoreboard1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 253, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addComponent(testingPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        //play and stop button
        testingPanel1.requestFocus();
        testingPanel1.togglePlay();
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    /**
     * By Jia Jia: Reset game button.
     *
     * @param evt
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //keep focus on game so keyboard inputs still work
        testingPanel1.requestFocus();
        if (resetConfirm) {
            //reset the map
            testingPanel1.GameBoard.reset();
            testingPanel1.selected = null;
            //stop the game if its still playing
            if (testingPanel1.playing) {
                jToggleButton1.doClick();
            }
            //reset confirmation sequence
            resetConfirm = false;
            jButton1.setText("Reset");
        } else {
            resetConfirm = true;
            jButton1.setText("Are You Sure?");
            t.start();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * By Jia Jia: Applies applicable change based on what was selected from the
     * combo box.
     *
     * @param evt
     */
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        testingPanel1.requestFocus();
        //set the game speed to the one matching the option selected
        switch (jComboBox1.getSelectedItem().toString()) {
            case "10%":
                testingPanel1.gameSpeed = 100;
                break;
            case "50%":
                testingPanel1.gameSpeed = 15;
                break;
            case "100%":
                testingPanel1.gameSpeed = 10;
                break;
            case "200%":
                testingPanel1.gameSpeed = 5;
                break;
            case "300%":
                //Actually 333.33...%
                testingPanel1.gameSpeed = 3;
                break;
            default:
                break;
        }
        //update the timer so it uses the new speed
        testingPanel1.timerUpdate();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    /**
     * Jia Jia: Focus on the panel when clicked
     *
     * @param evt
     */
    private void testingPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_testingPanel1MouseClicked
        testingPanel1.requestFocus();
    }//GEN-LAST:event_testingPanel1MouseClicked

    /**
     * Jia Jia: Repaint the scoreboard
     */
    public void updateScore() {
        scoreboard1.repaint();
    }

    /**
     * By Jia Jia: Allows third-parties to click the play button.
     */
    public void clickToggleButton1() {
        jToggleButton1.doClick();
    }

    /**
     * By Jia Jia: Allows third-parties to select from the drop down.
     */
    public void selectComboBox1(int index) {
        jComboBox1.setSelectedIndex(index);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JToggleButton jToggleButton1;
    private GUI.Scoreboard scoreboard1;
    private GUI.TestingPanel testingPanel1;
    // End of variables declaration//GEN-END:variables
}
