/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RazeSource;

import Development.AI;
import SampleAI.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author carlw
 */
public class AISelection extends javax.swing.JFrame {
    //maximum players the game can handel 
    private final static int maxPlayers = 4;

    /**
     * Creates new frame AISelection
     */
    public AISelection() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Begin = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jComboBox6 = new javax.swing.JComboBox();
        jComboBox7 = new javax.swing.JComboBox();
        jComboBox8 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Begin.setText("Start");
        Begin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BeginMouseClicked(evt);
            }
        });

        jTextField1.setEditable(false);
        jTextField1.setText(" Team 1 ");

        jTextField2.setEditable(false);
        jTextField2.setText(" Team 2 ");

        jTextField3.setEditable(false);
        jTextField3.setText(" Team 4 ");

        jTextField4.setEditable(false);
        jTextField4.setText(" Team 3 ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No Team", "Red", "Orange", "Yellow", "Green", "Blue", "Purple" }));
        jComboBox1.setToolTipText("");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No Team", "Red", "Orange", "Yellow", "Green", "Blue", "Purple" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No Team", "Red", "Orange", "Yellow", "Green", "Blue", "Purple" }));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No Team", "Red", "Orange", "Yellow", "Green", "Blue", "Purple" }));

        jLabel1.setText("Team:");

        jLabel2.setText("AI Type:");

        jLabel3.setText("Team Colour:");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FSDummy AI", "FSHarvester AI", "Foresight AI", "User Control AI" }));

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FSDummy AI", "FSHarvester AI", "Foresight AI", "User Control AI" }));

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FSDummy AI", "FSHarvester AI", "Foresight AI", "User Control AI" }));

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FSDummy AI", "FSHarvester AI", "Foresight AI", "User Control AI" }));

        jLabel4.setFont(new java.awt.Font("OCR A Extended", 0, 48)); // NOI18N
        jLabel4.setText("AI Selection");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Begin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel1))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField1)
                                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox6, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField2)
                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox7, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField4)
                                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox8, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField3)
                                    .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(17, 17, 17)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(Begin, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * The button to start the game
     *
     * @param evt when the button is pressed
     */
    private void BeginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BeginMouseClicked
        // TODO add your handling code here:   
        //finds the number of players playing         
        AI[] hold = new AI[maxPlayers];
         hold[0] = makeAI(jComboBox5.getSelectedItem().toString());
         hold[1] = makeAI(jComboBox6.getSelectedItem().toString());
         hold[2] = makeAI(jComboBox7.getSelectedItem().toString());
         hold[3] = makeAI(jComboBox8.getSelectedItem().toString());
         new GameFrame(hold).setVisible(true);       
        this.dispose();
        /** this part can be uncommented to inculde a bugged way to
         *  allow for no ai for a team (this isn't supported by GameFrame/Testing Panel/Map)
        int players = 0;
        if (!jComboBox5.getSelectedItem().toString().equals("No AI")) {
            players++;
            hold[0] = makeAI(jComboBox5.getSelectedItem().toString());
        }
        if (!jComboBox6.getSelectedItem().equals("No AI")) {
            players++;
            hold[1] = makeAI(jComboBox6.getSelectedItem().toString());
        }
        if (!jComboBox7.getSelectedItem().equals("No AI")) {
            players++;
            hold[2] = makeAI(jComboBox7.getSelectedItem().toString());
        }
        if (!jComboBox8.getSelectedItem().equals("No AI")) {
            players++;
            hold[3] = makeAI(jComboBox8.getSelectedItem().toString());
        }

        AI[] AIplayers = new AI[players];
        int helper = 0;
        for (int i = 0; i < players; i++) {
            while (hold[helper] == null && helper + 1 < maxPlayers) {
                helper++;
            }
            AIplayers[i] = hold[helper];
        }
        
        //makes a gameframe and deletes the AI selection frame        

        if (players > 0) {
            //edited
            new GameFrame(AIplayers).setVisible(true);
        } else {
            new GameFrame().setVisible(true);
        }
        this.dispose();
        */ 
    }//GEN-LAST:event_BeginMouseClicked

    /*
        makes a new AI given it's name
    */
    private AI makeAI(String AI) {
        AI out;
        switch (AI) {
            case "FSDummy AI":
                out = new FSDummyAI();
                break;
            case "FSHarvester AI":
                out = new FSHarvesterAI();
                break;
            case "Foresight AI":
                out = new ForesightAI();
                break;
            case "User Control AI":
                out = new UserControlAI();
                break;
            default:
                out =  new UserControlAI();
            //to add an ai add using thr below format also add the choice to all combo boxes (5, 6, 7, and 8)
            //case "Insert Name AI": out = new InsertNameAI(); break;
        }
        return out;
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
            java.util.logging.Logger.getLogger(AISelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AISelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AISelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AISelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AISelection().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Begin;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
