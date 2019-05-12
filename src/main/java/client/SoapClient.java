package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SoapClient extends javax.swing.JFrame {
   // private Date data;
    private SimpleDateFormat timedata = new SimpleDateFormat("hh:mm ");



    private Thread thread;

    private ConnectionHendlerSoap ConnSoap;
    private ProtocolManager protocolmanager = new ProtocolManager();

    public SoapClient() throws MalformedURLException {
        //title
        super("ClientSoap");
        initComponents();
        ConnSoap = new ConnectionHendlerSoap("localhost", 4321);
        //connect button
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm  ");
//////////////////////////////////////////////////////////////////////////////////////////////////////////
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //host
                ConnSoap.Host(jTextField1.getText());
                //port
                ConnSoap.Port(Integer.parseInt(jTextField3.getText()));
                 //registry
                 if (ConnSoap.rregistry() == null) {
                    if (ConnSoap.regClient()) {
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
                if (ConnSoap.rregistry() != null && ConnSoap.ident() != null) {

                    try {
                        ConnSoap.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    jTextArea1.append(simpleDateFormat.format(new Date()) + "Disconnect!\n");
                }
            }
        });
        //send button
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                               if (ConnSoap.rregistry() == null) {
                    jTextArea1.append(simpleDateFormat.format(new Date()) + "Connection wrong" + "\n");
                   // return;
                }
                if (ConnSoap.rregistry() != null) {

                    try {
                        ConnSoap.perform(jTextField2.getText());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                    jTextArea1.append(simpleDateFormat.format(new Date()) + jTextField2.getText() + "\n");
                }
            }
        });
    }
    //
    private void  Listener() {
        thread = new Thread(() -> {
            while (ConnSoap.rregistry() != null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }//response
                if (ConnSoap.inText() != null && !ConnSoap.inText().equals("")) {
                    jTextArea1.append(timedata.format(new Date()) + ConnSoap.inText() + "\n");
                    ConnSoap.outText(null);
                }
            }
        });
        thread.start();
    }
    ////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jButton1.setText("CONNECT");
        jButton2.setText("DISCONNECT");
        jTextArea1.setColumns(10);
        jTextArea1.setRows(10);
        jScrollPane1.setViewportView(jTextArea1);
        jTextField2.setText("%log:log-lllm pass-pppp;");
        jButton3.setText("SEND");
        jTextField1.setText("localhost");
        jLabel1.setText("IP");
        jTextField3.setText("4321");
        jLabel3.setText("PORT");
///////////////////
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTextField2)
                                                .addGap(1, 1, 1)
                                                .addComponent(jButton3))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(5, 5, 5)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel3)
                                                .addGap(5, 5, 5)
                                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButton1)
                                                .addGap(5, 5, 5)
                                                .addComponent(jButton2)))
                                .addContainerGap())
        );
        //vertial position
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jButton1)
                                                .addComponent(jButton2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton3)))
        );
        pack();
    }
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SoapClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SoapClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SoapClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SoapClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new SoapClient().setVisible(true);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;


}