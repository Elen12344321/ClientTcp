package client;

import commands.TCP_commands;
import lpi.server.rmi.IServer;

import java.io.Closeable;
import java.io.IOException;
import java.rmi.registry.Registry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectionHendlerRmi implements Closeable{
    private Registry rgs;
    private IServer server;
    private int Port;
    private String Host;

    public ConnectionHendlerRmi(String hostname, int port) {
        this.Port = Port;
        this.Host = Host;
    }
    /////////////////////////
    private void CommPars_1(String text_from_client) {
        Matcher matcher;
        /////////////////
        for (final TCP_commands comm : TCP_commands.values()) {
            /////////////pattern match
            matcher = Pattern.compile(comm.getReg()).matcher(text_from_client);
            if (matcher.find()) {
                switch (comm) {
                    case CMD_PING:
                     //   return new byte[]{CMD_PING};
                    case CMD_ECHO:
                       // WEcho = true;
                        //return serial(CMD_ECHO, new String(parsComm1(text_from_client)));
                    case CMD_LOGIN:
                        //String[] item = parsComm2(text_from_client);
                        //message = new Message();
                        //message.setLogin(item[1]);
                        //return serial(CMD_LOGIN, new String[]{item[1], item[2]});
                    case CMD_LIST:
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
        //return null;
    }
    //////////////////////////
    @Override
    public void close() throws IOException {

    }
}
