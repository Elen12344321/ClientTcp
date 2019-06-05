package client;

//import lombok.Data;

//ProtocolManager;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

//@Data
public class ConnectionHendler implements Closeable {
    private String host;
    private int port;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ProtocolManager protocolManager;
    private Thread threadReadMessage;
    private Thread threadShowResponse;
    private Thread threadSendMessage;
    private TimerTask timerTaskRequestInfo;
    private Timer timerRequestInfo;

    private volatile boolean isClose = false;
    private volatile String responseText;

    public ConnectionHendler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void listenSocket() {
        //Create socket connection
        if (socket != null && socket.isConnected()) {
            close(true);
        }
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(this.host, this.port), 2000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            protocolManager = new ProtocolManager();

            if (socket.isConnected()) {
                System.out.println("Socket is created and connected!");
                this.isClose = false;
            }
        } catch (SocketTimeoutException te) {
            System.out.println("Socket timeout connection");
        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void close() throws IOException {
        close(false);
    }

    public void close(boolean selfClose) {
        if (socket == null) return;
        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
            this.isClose = true;
            System.out.println("Socket close");
        } catch (IOException e) {
            System.out.println("Can`t close connection: " + e);
        }
        socket = null;
    }

    public synchronized void sendMessage(String textInput) {
        byte[] command = protocolManager.perform(textInput);
        threadSendMessage = new Thread(() -> {
            try {
                dataOutputStream.writeInt(command.length);
                dataOutputStream.write(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        threadSendMessage.setName("ThreadSendMessage");
        threadSendMessage.start();
        threadSendMessage.interrupt();
    }

    public synchronized void sendRequestInfo() {
        timerTaskRequestInfo = new TimerTask() {
            @Override
            public void run() {
                if (protocolManager.isLog() && socket.isConnected()) {
                    try {
                        if (!threadSendMessage.isAlive()) {
                            dataOutputStream.writeInt(protocolManager.requestMsg().length);
                            dataOutputStream.write(protocolManager.requestMsg());
                            Thread.sleep(200);

                            dataOutputStream.writeInt(protocolManager.requestFile().length);
                            dataOutputStream.write(protocolManager.requestFile());
                            Thread.sleep(200);
                        } else threadSendMessage.join();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        cancel();
                    }
                }
            }
        };
        timerRequestInfo = new Timer("TimerTaskRequestInfo");
        timerRequestInfo.scheduleAtFixedRate(timerTaskRequestInfo, 500, 500);
    }

    public synchronized String readMessage() {
        threadReadMessage = new Thread(() -> {

        });
        threadReadMessage.setDaemon(true);
        threadReadMessage.start();
        threadReadMessage.setName("TheadReadMessage");

        return responseText;
    }

    //public void InHost(String host) {
      //  this.Host = host;
    //}

   // public String ReturnHost() {
      //  return Host;
  //  }
//



   // public String getText() {
       // return responseText;
    //}

    public void setHost(String host) {
    }

    public void setPort(int parseInt) {
    }

    public Socket getSocket() {
        return socket;
    }

    public String getResponseText() {
        return null;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}