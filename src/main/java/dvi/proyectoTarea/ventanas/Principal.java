/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dvi.proyectoTarea.ventanas;

import dvi.proyectoTarea.recursos.dataaccess.DataAccess;
import dvi.proyectoTarea.recursos.objetos.Intent;
import dvi.proyectoTarea.recursos.objetos.Review;
import dvi.proyectoTarea.recursos.objetos.Usuari;
import dvi.proyectoTarea.recursos.varios.CustomTableCellRenderer;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alumne
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private int trainerId;
    private int tableFormat = 3;

    public Principal() {
        initComponents();
        initVLCPlayer();
        initTables();
        jPanel1.setVisible(false);
    }

    public void setTrainerId(int id) {
        this.trainerId = id;
    }

    private void initTables() {
        DefaultTableModel dtm1 = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // La celda no es editable
            }
        };
        dtm1.setColumnIdentifiers(new String[]{"Intent", "Usuari", "Exercici", "Inici", "Estat"});
        intentTable.setModel(dtm1);

        DefaultTableModel dtm2 = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // La celda no es editable
            }
        };
        dtm2.setColumnIdentifiers(new String[]{"Usuari", "ID"});
        userTable.setModel(dtm2);
        cargarUserTable();
        cargarPendingIntentTable();
    }

    private void cargarUserTable() {
        DataAccess dataAccess = new DataAccess();
        List<Object[]> userData = dataAccess.getUserData();
        DefaultTableModel dtm = (DefaultTableModel) userTable.getModel();
        dtm.setRowCount(0); // Clear the table before loading new data
        for (Object[] row : userData) {
            dtm.addRow(row);
        }
    }

    private void cargarIntentTablePU(int id) {
        DataAccess dataAccess = new DataAccess();
        ArrayList<Intent> Intents = dataAccess.getAttemptsPerUser(id);
        List<Object[]> IntentData = dataAccess.getIntentData(Intents);
        DefaultTableModel dtm = (DefaultTableModel) intentTable.getModel();
        dtm.setRowCount(0); // Clear the table before loading new data
        for (Object[] row : IntentData) {
            dtm.addRow(row);
        }

        tableFormat = 1;

        cargarTable();
    }

    private void cargarIntentTable() {
        DataAccess dataAccess = new DataAccess();
        ArrayList<Intent> Intents = dataAccess.getAttempts();
        List<Object[]> IntentData = dataAccess.getIntentData(Intents);
        DefaultTableModel dtm = (DefaultTableModel) intentTable.getModel();
        dtm.setRowCount(0); // Clear the table before loading new data
        for (Object[] row : IntentData) {
            dtm.addRow(row);
        }

        tableFormat = 2;

        cargarTable();
    }

    private void cargarPendingIntentTable() {
        DataAccess dataAccess = new DataAccess();
        ArrayList<Intent> pendingIntent = dataAccess.getAttemptsPendingReview();
        List<Object[]> pendingIntentData = dataAccess.getIntentData(pendingIntent);
        DefaultTableModel dtm = (DefaultTableModel) intentTable.getModel();
        dtm.setRowCount(0); // Clear the table before loading new data
        for (Object[] row : pendingIntentData) {
            dtm.addRow(row);
        }

        tableFormat = 3;

        cargarTable();
    }

    private void cargarTable() {
        pintarTabla();
        if (intentTable.getRowCount() > 0) {
            intentTable.setRowSelectionInterval(0, 0);
            cargarVideo();
        }
        commentControl();
    }

    private void pintarTabla() {
        intentTable.getColumnModel().getColumn(intentTable.getColumn("Estat").getModelIndex()).setCellRenderer(new CustomTableCellRenderer());
    }

    private void cargarVideo() {
        int selectedRow = intentTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) intentTable.getValueAt(selectedRow, 0); // Assuming the ID is in the first column (index 0)
            DataAccess dataAccess = new DataAccess();
            String videoFile = dataAccess.getVideoFile(id);
            if (videoFile != null && !videoFile.isEmpty()) {
                String videoPath = "src/main/java/dvi/proyectoTarea/imagenes/videos/" + videoFile;
                System.out.println("Loading video from: " + videoPath); // Debugging line
                mediaPlayerComponent.mediaPlayer().media().play(videoPath);
            } else {
                System.out.println("Video file is empty or null");
            }
        } else {
            System.out.println("No row selected");
        }
    }

    private void initVLCPlayer() {
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        videoPanel.setLayout(new BorderLayout());
        videoPanel.add(mediaPlayerComponent, BorderLayout.CENTER);
        videoPanel.revalidate();
        videoPanel.repaint();
    }

    private void jPanel1ComponentShown(java.awt.event.ComponentEvent evt) {
        cargarVideo();
    }

    private void commentControl() {
        int selectedRow = intentTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) intentTable.getValueAt(selectedRow, 0); // Assuming the ID is in the first column (index 0)
            DataAccess dataAccess = new DataAccess();
            Review review = dataAccess.getAttemptReview(id);
            if (review.getValoracio() != 0) {
                commentArea.setText(review.getComentari());
                jSlider1.setValue(review.getValoracio());
                inupReview.setText("Actualitza Review");
                delReview.setEnabled(true);
            } else {
                commentArea.setText("");
                jSlider1.setValue(3);
                inupReview.setText("Inserta Review");
                delReview.setEnabled(false);
            }
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

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        LoginButton = new javax.swing.JButton();
        Image = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        logout = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        intentTable = new javax.swing.JTable();
        videoPanel = new javax.swing.JPanel();
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentShown(evt);
            }
        });
        jScrollPane2 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        allIntentsButton = new javax.swing.JButton();
        pendingIntentsButton = new javax.swing.JButton();
        playButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        commentArea = new javax.swing.JTextArea();
        jSlider1 = new javax.swing.JSlider();
        delReview = new javax.swing.JButton();
        inupReview = new javax.swing.JButton();
        valorInfo = new javax.swing.JLabel();
        delIntent = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1200, 800));
        setMinimumSize(new java.awt.Dimension(1200, 800));
        setPreferredSize(new java.awt.Dimension(1200, 800));
        setResizable(false);
        setSize(new java.awt.Dimension(1200, 800));
        getContentPane().setLayout(null);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText(new String("https://negrefitnessclub.es/en"));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 680, -1, -1));

        LoginButton.setText("Login");
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginButtonActionPerformed(evt);
            }
        });
        jPanel2.add(LoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(542, 600, 130, -1));

        Image.setIcon(new javax.swing.ImageIcon("src/main/java/dvi/proyectoTarea/imagenes/logoMinima.png"));
        Image.setAlignmentY(0.0F);
        Image.setFocusable(false);
        jPanel2.add(Image, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 0, 1200, 780));

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 1200, 780);

        jPanel1.setLayout(null);

        logout.setText("Log Out");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        jPanel1.add(logout);
        logout.setBounds(5, 660, 130, 40);

        intentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        intentTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        intentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                intentTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(intentTable);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(0, 6, 284, 590);

        videoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("VideoFilePlayer"));

        javax.swing.GroupLayout videoPanelLayout = new javax.swing.GroupLayout(videoPanel);
        videoPanel.setLayout(videoPanelLayout);
        videoPanelLayout.setHorizontalGroup(
            videoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
        );
        videoPanelLayout.setVerticalGroup(
            videoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(videoPanel);
        videoPanel.setBounds(290, 6, 729, 420);

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        userTable.setFocusable(false);
        userTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        userTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(userTable);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(1025, 6, 149, 420);

        allIntentsButton.setText("Tots");
        allIntentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allIntentsButtonActionPerformed(evt);
            }
        });
        jPanel1.add(allIntentsButton);
        allIntentsButton.setBounds(5, 610, 130, 38);

        pendingIntentsButton.setText("Pendents");
        pendingIntentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pendingIntentsButtonActionPerformed(evt);
            }
        });
        jPanel1.add(pendingIntentsButton);
        pendingIntentsButton.setBounds(140, 610, 130, 38);

        playButton.setText("Play");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });
        jPanel1.add(playButton);
        playButton.setBounds(510, 430, 135, 38);

        pauseButton.setText("Pause");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });
        jPanel1.add(pauseButton);
        pauseButton.setBounds(670, 430, 135, 38);

        commentArea.setColumns(20);
        commentArea.setRows(5);
        jScrollPane3.setViewportView(commentArea);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(290, 480, 650, 220);

        jSlider1.setMaximum(5);
        jSlider1.setMinimum(1);
        jSlider1.setValue(3);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });
        jPanel1.add(jSlider1);
        jSlider1.setBounds(960, 480, 200, 20);

        delReview.setText("Borra Review");
        delReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delReviewActionPerformed(evt);
            }
        });
        jPanel1.add(delReview);
        delReview.setBounds(990, 620, 135, 38);

        inupReview.setText("Review");
        inupReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inupReviewActionPerformed(evt);
            }
        });
        jPanel1.add(inupReview);
        inupReview.setBounds(990, 550, 135, 38);
        jPanel1.add(valorInfo);
        valorInfo.setBounds(980, 500, 160, 30);

        delIntent.setText("Borra Intent");
        delIntent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delIntentActionPerformed(evt);
            }
        });
        jPanel1.add(delIntent);
        delIntent.setBounds(140, 660, 130, 40);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1180, 730);

        jMenu1.setText("File");

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(aboutMenuItem);

        exitMenuItem.setText("Exit and Help");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(exitMenuItem);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginButtonActionPerformed
        // TODO add your handling code here:
        UserLogin log = new UserLogin(this);
        log.setVisible(true);
    }//GEN-LAST:event_LoginButtonActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI("https://negrefitnessclub.es/en"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void userTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTableMouseClicked
        // TODO add your handling code here:
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) userTable.getValueAt(selectedRow, 1); // Assuming the ID is in the second column (index 1)
            cargarIntentTablePU(id);
        }

    }//GEN-LAST:event_userTableMouseClicked

    private void intentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_intentTableMouseClicked
        // TODO add your handling code here:
        cargarVideo();
        commentControl();
    }//GEN-LAST:event_intentTableMouseClicked

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
        jPanel1.setVisible(false);
        jPanel2.setVisible(true);
    }//GEN-LAST:event_logoutActionPerformed

    private void allIntentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allIntentsButtonActionPerformed
        // TODO add your handling code here:
        cargarIntentTable();
    }//GEN-LAST:event_allIntentsButtonActionPerformed

    private void pendingIntentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pendingIntentsButtonActionPerformed
        // TODO add your handling code here:
        cargarPendingIntentTable();
    }//GEN-LAST:event_pendingIntentsButtonActionPerformed

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        // TODO add your handling code here:
        if (mediaPlayerComponent != null && mediaPlayerComponent.mediaPlayer() != null) {
            mediaPlayerComponent.mediaPlayer().controls().pause();
        }
    }//GEN-LAST:event_pauseButtonActionPerformed

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        // TODO add your handling code here:
        if (mediaPlayerComponent != null && mediaPlayerComponent.mediaPlayer() != null) {
            mediaPlayerComponent.mediaPlayer().controls().play();
        }
    }//GEN-LAST:event_playButtonActionPerformed

    private void delReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delReviewActionPerformed
        // TODO add your handling code here:
        int selectedRow = intentTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) intentTable.getValueAt(selectedRow, 0); // Assuming the ID is in the first column (index 0)
            int response = JOptionPane.showConfirmDialog(this, "Estas segur que vols esborrar aquesta review?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                DataAccess dataAccess = new DataAccess();
                dataAccess.deleteReview(id);
                updateIntentTable();
                commentControl();
            }
        }
    }//GEN-LAST:event_delReviewActionPerformed

    private void inupReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inupReviewActionPerformed
        // TODO add your handling code here:
        int selectedRow = intentTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) intentTable.getValueAt(selectedRow, 0); // Assuming the ID is in the first column (index 0)
            DataAccess dataAccess = new DataAccess();
            Review review = new Review();
            review.setIdIntent(id);
            review.setComentari(commentArea.getText());
            review.setIdReviewer(trainerId);
            review.setValoracio(jSlider1.getValue());

            if (delReview.isEnabled()) {
                review.setId(dataAccess.getAttemptReview(id).getId());
                dataAccess.updateReview(review);
            } else {
                dataAccess.insertReview(review);
            }
            updateIntentTable();
            commentControl();
        }

    }//GEN-LAST:event_inupReviewActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        // TODO add your handling code here:
        int i = (jSlider1.getValue());
        if (i < 3) {
            valorInfo.setText(i + "  Suspès");
        } else {
            valorInfo.setText(i + "  Molt bo");
        }

    }//GEN-LAST:event_jSlider1StateChanged

    private void delIntentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delIntentActionPerformed
        // TODO add your handling code here:
        int selectedRow = intentTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) intentTable.getValueAt(selectedRow, 0); // Assuming the ID is in the first column (index 0)
            int response = JOptionPane.showConfirmDialog(this, "Estas segur que vols esborrar aquest intent?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                DataAccess dataAccess = new DataAccess();
                dataAccess.deleteIntent(id);
                updateIntentTable();
                commentControl();
            }
        }
        //Estas segur que vols esborrar aquest intent?
    }//GEN-LAST:event_delIntentActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        // TODO add your handling code here:
        JDialog aboutDialog = new JDialog(this, "About", true);
        aboutDialog.setSize(300, 200);
        aboutDialog.setLocationRelativeTo(this);
        JTextArea aboutText = new JTextArea("Diseñado por José David Guevara Rodriguez.\n Basado en el gimnasio de Can Picafort: https://negrefitnessclub.es/en.");
        aboutText.setEditable(false);
        aboutText.setWrapStyleWord(true);
        aboutText.setLineWrap(true);
        aboutText.setOpaque(false);
        aboutText.setFocusable(false);
        aboutText.setBackground(UIManager.getColor("Label.background"));
        aboutText.setFont(UIManager.getFont("Label.font"));
        aboutText.setBorder(UIManager.getBorder("Label.border"));
        aboutDialog.add(aboutText);
        aboutDialog.setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // TODO add your handling code here:
        // Custom button text
        Object[] options = {"Eh?"};
        int response = JOptionPane.showOptionDialog(this,
                "No hay escapatoria, nadie vendra a salvarnos",
                "Exit",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        // If the user clicks the "Eh?" button
        if (response == 0) {
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.BROWSE)) {
                        desktop.browse(new URI("https://www.leagueoflegends.com"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void updateIntentTable() {
        switch (tableFormat) {
            case 1 ->
                cargarIntentTablePU(trainerId);
            case 2 ->
                cargarIntentTable();
            case 3 ->
                cargarPendingIntentTable();
        }
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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Image;
    private javax.swing.JButton LoginButton;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton allIntentsButton;
    private javax.swing.JTextArea commentArea;
    private javax.swing.JButton delIntent;
    private javax.swing.JButton delReview;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JTable intentTable;
    private javax.swing.JButton inupReview;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JButton logout;
    private javax.swing.JButton pauseButton;
    private javax.swing.JButton pendingIntentsButton;
    private javax.swing.JButton playButton;
    private javax.swing.JTable userTable;
    private javax.swing.JLabel valorInfo;
    private javax.swing.JPanel videoPanel;
    // End of variables declaration//GEN-END:variables
}
