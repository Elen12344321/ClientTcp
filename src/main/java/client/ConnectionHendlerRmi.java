package client;

import commands.TCP_commands;
import lpi.server.rmi.IServer;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectionHendlerRmi implements Closeable{
    private Registry rgs;
    private IServer server;
    private int Port;
    private String Host;
    private ProtocolManager protocolManager = new ProtocolManager();

    public ConnectionHendlerRmi(String Host, int Port) {
        this.Port = Port;
        this.Host = Host;
    }
    /////////////////////////
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
    public void perform(String textFromLabel) throws RemoteException {
        CommPars_1(protocolManager.TextPars(textFromLabel));
    }

    private void CommPars_1(String text_from_client) throws RemoteException {
        Matcher matcher;
        /////////////////
        for (final TCP_commands comm : TCP_commands.values()) {
            /////////////pattern match
            matcher = Pattern.compile(comm.getReg()).matcher(text_from_client);
            if (matcher.find()) {
                switch (comm) {
                    case CMD_PING:
                     //   return new byte[]{CMD_PING};
                       server.ping();
                        bool = true; break;
                    case CMD_ECHO:
                        //resp
                        string = new String(server.echo(new String(protocolManager.parsComm1(text_from_client))));
                        bool = true;
                        break;
                    case CMD_LOGIN:
                        //String[] item = parsComm2(text_from_client);
                        //message = new Message();
                        //message.setLogin(item[1]);
                        //return serial(CMD_LOGIN, new String[]{item[1], item[2]});
                        //String[] mass = serial(CMD_LOGIN, new String[]{item[1], item[2]});
                           String[] mass = protocolManager.parsComm2(text_from_client);
                            //log-1 pass-2
                            ident = server.login(mass[1], mass[2]);
                            ///////////////////////
                            string = new String("log ok");
                         //   resiveInfoChecker();

                        bool=true;
                    case CMD_LIST:
                        //identification
                        string = new String("list:" + Str(server.listUsers(ident)));
                         bool = true;
                        break;
                        //WList = true;
                        //return new byte[]{CMD_LIST};

                    case CMD_MSG:
                        ////////////  System.out.println(message.getLogin());
                        //return serial(CMD_MSG, new String[]{
                          //      message.getLogin(), parsComm1(text_from_client)});
                    case CMD_FILE:
                        //return sendFile(text_from_client);
                    case CMD_RECIVE_MSG:
                        //WMsg = true;
                        //return new byte[]{
                          //      CMD_RECEIVE_MSG
                        //};
                    case CMD_RECIVE_FILE:
                        //WFile = true;
                        //return new byte[]{
                          //      CMD_RECEIVE_FILE
                        };

                }}
        //}
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


    ///////////////////////////////
    public boolean regClient() {
        try {
            //create object registry
            this.rgs= LocateRegistry.getRegistry(Host, Port);
            ///get object
            this.server = (IServer) rgs.lookup("lpi.server.rmi");
            return true;
        } catch (NotBoundException | RemoteException e) {
               e.printStackTrace();
                this.rgs = null;
                 this.server = null;
            ident = null;
            return false; }
    }
    //////////////////////////

    private String string;
    String ident;
    @Override
    public void close() throws IOException {
        if (this.rgs != null) {
            try {
                if (ident != null) {
                    server.exit(ident);
                    ident = null;
                } } catch (AccessException e) {
                     e.printStackTrace();
                    } catch (RemoteException e) {
                           e.printStackTrace();
                       }
                 }
    }



}
