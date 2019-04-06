package clienttcp1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
    /*
    try {
                clientSocket = new Socket("localhost", 4321);
                outputStream = clientSocket.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);
                inputStream = clientSocket.getInputStream();
    */
    /*
                dataOutputStream.writeUTF("Hello");
                System.out.println("Client to server says: Hello");


            } catch (Exception e) {
                e.printStackTrace();
            }
    */
public class TcpClient extends javax.swing.JFrame {
    private Date data;
    private SimpleDateFormat timedata = new SimpleDateFormat("hh:mm:ss    ");
    private ConnectionHandler ConHend;
    private ProtocolManager ProtMan;
    private Thread thread;
   /*
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
       // Socket clientSocket = new Socket("localhost", 4321);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        sentence = inFromUser.readLine();*/
   /*
        outToServer.writeBytes(sentence + 'n');
        modifiedSentence = inFromServer.readLine();
        //System.out.println("FROM SERVER: " + modifiedSentence);
        clientSocket.close();*/
    public TcpClient() {
        //title
        super("ClientTCP");
        initComponents();
        ConHend = new ConnectionHandler("localhost", 4321);
        //connect button
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //host
                ConHend.InHost(jTextField1.getText());
                //port
                ConHend.InPort(Integer.parseInt(jTextField3.getText()));
                if (ConHend.getSocket() == null) {
                    ConHend.SocketConnect();
                    //if connect to server done
                    if (ConHend.getSocket().isConnected()) {
                        ConHend.InText();
                        // response
                        Listener();
                        jTextArea1.append(timedata.format(new Date()) + "Connect!\n");
                    //error
                    } else {
                        jTextArea1.append(timedata.format(new Date()) + "Connect error!\n");
                        ConHend.close(true);
                       // return;
                    }
                }
            }
        });
         //disconnect button
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ConHend.getSocket() != null) {
                    ConHend.close(true);
                    //interrupt
                    thread.interrupt();
                    if (ConHend.getSocket() == null) {
                        jTextArea1.append(timedata.format(new Date()) + "Disconnected!\n");
                    }
                }
            }
        });
          //send button
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // send messages
                if (ConHend.getSocket() == null) {
                    jTextArea1.append(timedata.format(new Date()) + "Connection error" + "\n");
                    return;
                }
                if (ConHend.getSocket().isConnected()) {
                    try {
                        ConHend.OutText(jTextField2.getText());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    jTextArea1.append(timedata.format(new Date()) + jTextField2.getText() + "\n");
                }
            }
        });
    }
//
    private void  Listener() {
        thread = new Thread(() -> {
            while (ConHend.getSocket().isConnected()) {
                if (ConHend.getText() != null) {
                    jTextArea1.append(timedata.format(new Date()) + ConHend.getText() + "\n");
                    ConHend.setText(null);
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
            java.util.logging.Logger.getLogger(TcpClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TcpClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TcpClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TcpClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TcpClient().setVisible(true);
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
