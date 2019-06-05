package client;


import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class client_Rest extends javax.swing.JFrame {

   private SimpleDateFormat timedata = new SimpleDateFormat("hh:mm ");

    private Thread thread;


    private ConnectionHandlerRest ConnRest;

    public client_Rest() {
        super("client_rest");
        initComponents();


        this.ConnRest = new ConnectionHandlerRest();
        Listener();
/*
*  jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //host
                ConnRMI.Host(jTextField1.getText());
                //port
                ConnRMI.Port(Integer.parseInt(jTextField3.getText()));
                //registry
                if (ConnRMI.rregistry() == null) {
                    if (ConnRMI.regClient()) {
                        Listener();
                        jTextArea1.append(simpleDateFormat.format(new Date()) + "Connect \n");
                    } else {
                        jTextArea1.append(simpleDateFormat.format(new Date()) + "Connect error\n");
                    }
                } else {
                    jTextArea1.append(simpleDateFormat.format(new Date()));
                    return;
                }
            }
        });
        //disconnect button
        jButton2.addActionListener(new ActionListener() {
            @Override
            //close
            public void actionPerformed(ActionEvent e) {
                if (ConnRMI.rregistry() != null && ConnRMI.ident() != null) {

                    try {
                        ConnRMI.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    jTextArea1.append(simpleDateFormat.format(new Date()) + "Disconnect!\n");
                }
            }
        });
        //send button
* */
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sender();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ConnRest.close();
                e.getWindow().dispose();
            }
        });

        jTextField2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sender();
                }
            }
        });
    }

    private void sender() {
        try {
            ConnRest.perform(jTextField2.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        jTextArea1.append(timedata.format(new Date()) + jTextField2.getText() + "\n");
        jTextField1.setText(ConnRest.Url());
    }

    private void  Listener() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }//response
                    if (ConnRest.get_Echo() != null && !ConnRest.get_Echo().equals("")) {
                        jTextArea1.append(timedata.format(new Date()) + ConnRest.get_Echo() + "\n");
                        ConnRest.set_Echo(null);
                    }
                }
            }
        });
        thread.start();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
/* jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();*/
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        //jTextArea1.setEditable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        //jButton1.setText("CONNECT");
        //        jButton2.setText("DISCONNECT");
        //jTextArea1.setColumns(10);
        //        jTextArea1.setRows(10);
                jScrollPane1.setViewportView(jTextArea1);
        //        jTextField2.setText("%process: f-C:\\Users\\Admin\\Idea\\Idea.txt to-C:\\Users\\Admin\\Idea\\Idea.txt;");
        //        jButton3.setText("SEND");
        jTextField1.setText("http://localhost:8080/chat/server/");
        jLabel1.setText("URL");
       // jScrollPane1.setViewportView(jTextArea1);
        jTextField2.setText("%log: l-lllm p-ttttttttt;");
        jButton3.setText("SEND");
        //jTextField3.setText("3000");
        //////////////////////////////////////
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField2)
                                        .addGap(1, 1, 1)
                                        .addComponent(jButton3))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)


                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        //  .addComponent(jLabel3)
                                        //  .addGap(5, 5, 5)
                                        //.addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        //   .addComponent(jButton1)
                                        //    .addGap(5, 5, 5)
                                        //    .addComponent(jButton2)))
                                        .addContainerGap()).addGroup(layout.createSequentialGroup()
                                        .addContainerGap())));
        //vertial position
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        //.addComponent(jLabel3)
                                      //  .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                             //   .addComponent(jButton1)
                                              //  .addComponent(jButton2)))
                             //   .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton3)))
                                )));
        pack();
    }

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
            java.util.logging.Logger.getLogger(client_Rest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(client_Rest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(client_Rest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(client_Rest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new client_Rest().setVisible(true);

            }
        });
    }

    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;

    //ConsoleBuffer Java lib
    // End of variables declaration//GEN-END:variables
}