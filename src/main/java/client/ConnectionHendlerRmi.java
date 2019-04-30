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

    public void perform(String textFromLabel) throws IOException {
        CommPars_1(protocolManager.TextPars(textFromLabel));
    }
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
                            server.ping();
                            bool = true;
                            break;
                        case CMD_ECHO:
                            //resp
                            string = new String(server.echo(new String(protocolManager.parsComm1(text_from_client))));
                            bool = true;
                            break;
                        case CMD_LOGIN:
                            String[] mass = protocolManager.parsComm2(text_from_client);
                            String[] mass1 = protocolManager.parsComm3(mass[1].toString());
                                //log-1 pass-2

                            ident = server.login(mass1[0], mass[2]);
                            System.out.println(mass[1].toString());
                            System.out.println(mass[2].toString());
                            System.out.println(mass1[0].toString());
                               ///////////////////////
                            string = new String("log ok");
                            bool = true;
                            break;
                        case CMD_LIST:
                            //identification
                            string = new String("list:" +" "+ Str(server.listUsers(ident)));
                            bool = true;
                            break;

                        case CMD_MSG:
                            String[] itemForMsg = protocolManager.parsComm2(text_from_client);
                            server.sendMessage(ident, new IServer.Message(itemForMsg[1], itemForMsg[2]));
                            string = new String("Ok! send message");
                            bool = true;
                            break;
                        case CMD_FILE:
                            String[] namefile = protocolManager.parsComm2(text_from_client);
                            Path pathFile1 = Paths.get(namefile[2]);
                            System.out.println(namefile[2].toString());
                            //add name file
                            String Path=pathFile+pathFile1.toString();
                            //create file
                            File file = new File(Path);
                            System.out.println(Path);
                            server.sendFile(ident, new IServer.FileInfo(namefile[1].toString(), file));
                            string= new String("Ok!  send file with name"+" "+pathFile1.toString());
                            bool = true;
                            break;


                        case EXIT:
                            close();
                            string = new String("connect close");
                           break;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
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

}
