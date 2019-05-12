package client;

import commands.TCP_commands;
import lpi.server.rmi.IServer;
import lpi.server.soap.*;
import lpi.server.soap.Message;
//import myApp.soapProxy.lpi.server.soap.*;
//import myApp.soapProxy.lpi.server.soap.Ping;
//import myApp.soapProxy.lpi.server.soap.Message;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//@WebService(serviceName = "ChatServer", portName = "ChatServerProxy", endpointInterface = "lpi.server.soap.IServer")

public class ConnectionHendlerSoap implements  Runnable, Closeable{
    private Registry rgs;
    private IChatServer server;
    private int Port;
    private String Host;
   // private Ping ping1;
    //private Echo echo1;
    private ProtocolManager protocolManager = new ProtocolManager();
    ChatServer serverWrapper;
    IChatServer serverProxy;
    URL url = new URL("http://localhost:4321/chat?wsdl");
    QName qname = new QName("http://soap.server.lpi/", "ChatServer");
    Service service;// = Service.create(url, qname);

    public ConnectionHendlerSoap(String Host, int Port) throws MalformedURLException {
        this.Port = Port;
        this.Host = Host;
        serverWrapper = new ChatServer( url, qname);
       serverProxy = serverWrapper.getChatServerProxy();
    }
    /////////////////////////
    IChatServer hello;// = service.getPort(IChatServer.class);

   /// hello=service(IChatServer.class);
    public void perform(String text_from_client) throws IOException {
        CommPars_1(protocolManager.TextPars(text_from_client));
    }
    private String log_send;
    private String pathFile = "C:\\Users\\Admin\\Idea\\";
    private void CommPars_1(String text_from_client) throws IOException {
        Matcher matcher;
        /////////////////
        for (final TCP_commands comm : TCP_commands.values()) {
            /////////////pattern match
            matcher = Pattern.compile(comm.getReg()).matcher(text_from_client);
            if (matcher.find()) {
                try {
                    switch (comm) {
                        case CMD_PING:
                            //ping1;
                            serverProxy.ping();
                            bool = true;
                            break;
                        case CMD_ECHO:
                            //resp
                            string = new String(serverProxy.echo(new String(protocolManager.parsComm1(text_from_client))));
                            //  string = new String(echo1.setText(new String(protocolManager.parsComm1(text_from_client)));
                            //serverProxy.echo();
                            bool = true;
                            break;
                        case CMD_LOGIN:
                            if (ident == null) {
                                String[] mass = protocolManager.parsComm2(text_from_client);
                                String[] mass1 = protocolManager.parsComm3(mass[1].toString());
                                log_send = mass1[0];
                                //log-1 pass-2
                                ident = serverProxy.login(mass1[0], mass[2]);
                                //System.out.println(mass[1].toString());
                                //System.out.println(mass[2].toString());
                                //System.out.println(mass1[0].toString());
                                ///////////////////////
                                string = new String("log ok");
                            }else string = new String("no log");
                            bool = true;
                            break;
                        case CMD_LIST:
                            if (ident == null) {
                            //identification
                            string = new String("list:" + " " + (serverProxy.listUsers(ident)));
                            }else string = new String("no log");
                            bool = true;
                            break;

                        case CMD_MSG:
                            if (ident == null) {
                            String[] Mass = protocolManager.parsComm2(text_from_client);
                            String[] Mass1 = protocolManager.parsComm3(Mass[1].toString());
                          //  System.out.println(Mass1[0].toString()+"1");
                            //System.out.println(Mass1[1].toString());
                            // String[] Str1 = new String[0];
                            //ident=new sendMessage;
                            serverProxy.sendMessage(ident, Setter_1(log_send, Mass1[0], Mass[2]));
                            string = new String("Ok! send message");
                            }else string = new String("no log");
                            bool = true;
                            break;
                       // case CMD_RECIVE_MSG:
                         //`   break;

                        case CMD_FILE:
                            if (ident == null) {
                            String[] namefile = protocolManager.parsComm2(text_from_client);
                            String[] namefile1=protocolManager.parsComm3(namefile[2].toString());
                            String[] namefile2=protocolManager.parsComm3(namefile[1].toString());
                            Path pathFile1 = Paths.get(namefile1[0].toString());
                            String Path = pathFile+pathFile1.toString();
                            //create file
                            File file = new File(Path);
                            System.out.println(Path);
                            // log-send, log-res, file-name, path to file
                            serverProxy.sendFile(ident, Setter_2(log_send, namefile2[0], file.getName(), Files.readAllBytes(file.toPath())));
                            string = new String("Ok!  send file with name" + " " + pathFile1.toString());
                            }else string = new String("no log");
                            bool = true;
                            break;
                        case CMD_RECIVE_FILE:
                            break;
                        case EXIT:
                            if (ident == null) {
                            close();
                            string = new String("connect close");
                            }else string = new String("no log");
                            bool = true;
                            break;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ArgumentFault argumentFault) {
                    argumentFault.printStackTrace();
                } catch (ServerFault serverFault) {
                    serverFault.printStackTrace();
                } catch (LoginFault loginFault) {
                    loginFault.printStackTrace();
                }
            }
        } bool = false;
    }
    ////////////////////////////
    private String Str(String[] string1) {
        StringBuffer string2 = new StringBuffer();
        //for (int i=0;i<string1.length-1;i++) {
        //  string2.append(string1 + "");}
        for (String string3: string1) {
            string2.append(string3 + "");
        }
        return string2.toString();
    }

    private Message Setter_1(String str_1, String str_2, String str_3) {
        Message mess;
        mess= new Message();
        //mess=Message.setSendler(str_1);
        mess.setSender(str_1);
        mess.setReceiver(str_2);
        mess.setMessage(str_3);
        return mess;
    }


    ////////////////////////////
    private FileInfo Setter_2(String str1, String str2, String str3, byte[] path) {
        FileInfo file = new FileInfo();
        file.setSender(str1);
        ////////////////////////////////
        file.setReceiver(str2);

        file.setFilename(str3);
        file.setFileContent(path);
        return file;
    }
    ///////////////////////////////
    public boolean regClient() {
        //try {
            //create object registry
           // this.rgs= LocateRegistry.getRegistry(Host, Port);
            ///get object
            //this.server = (IChatServer) rgs.lookup("lpi.server.rmi");
        /*
        try {
            //create object registry
            this.rgs= LocateRegistry.getRegistry(Host, Port);
            ///get object
            this.server = (IServer) rgs.lookup("lpi.server.rmi");
            return true;
        }
        */try{
            this.rgs= LocateRegistry.getRegistry(Host, Port);
        this.serverWrapper = new ChatServer(url, qname);
        this.serverProxy = serverWrapper.getChatServerProxy();
       // this.serverProxy = (IChatServer) Service.create(url, qname);
        return true;}
        catch (IOException e){return false;}

    }
    //////////////////////////

    private String string;
    String ident;
    @Override

    public void close() throws IOException {
        if (this.rgs != null) {
           // try {
                    if (ident != null) {
                    //server.exit(ident);
                    ident = null;
          //      } //} catch (AccessException e) {
                //e.printStackTrace();
            //} catch (RemoteException e) {
              //  e.printStackTrace();
            }
        //}
    }}

    public void Host(String text) {
    }

    public void Port(int parseInt) {
    }

    public  Registry rregistry() {
        return rgs;
    }

    public Object ident() {
        return null;
    }

    public String inText() { return string; }

    public void outText(String string) {
        this.string=string;
    }
    private boolean bool = false;

    @Override
    public void run() {

    }



}
