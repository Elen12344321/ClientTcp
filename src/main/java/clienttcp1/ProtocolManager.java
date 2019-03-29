package clienttcp1;

import commands.TCP_commands;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

//import static jdk.nashorn.internal.runtime.AstDeserializer.deserialize;

public class ProtocolManager {
    private static final Charset CHARSET = Charset.forName("UTF-8");

    private static final byte CMD_PING = 1;
    private static final byte CMD_PING_RESPONSE = 2;
    private static final byte CMD_ECHO = 3;
    private static final byte CMD_LOGIN = 5;
    private static final byte CMD_LIST = 10;
    private static final byte CMD_MSG = 15;
    private static final byte CMD_FILE = 20;
    private static final byte CMD_RECEIVE_MSG = 25;
    private static final byte CMD_RECEIVE_FILE = 30;

    private static final byte CMD_LOGIN_OK_NEW = 6;
    private static final byte CMD_LOGIN_OK = 7;
    private static final byte CMD_MSG_SENT = 16;
    private static final byte CMD_FILE_SENT = 21;
    private static final byte CMD_RECEIVE_MSG_EMPTY = 26;
    private static final byte CMD_RECEIVE_FILE_EMPTY = 31;

    // Errors
    private static final byte SERVER_ERROR = 100;
    private static final byte INCORRECT_CONTENT_SIZE = 101;

    private static final byte SERIALIZATION_ERROR =  102;
    private static final byte INCORRECT_COMMAND = 103;
    private static final byte WRONG_PARAMS = 104;

    private static final byte LOGIN_WRONG_PASSWORD = 110;
    private static final byte LOGIN_FIRST = 112;
    private static final byte FAILED_SENDING = 113;

    private boolean isCommand = false;
    private String comm;
    private Object command;

    //StringBuffer stringBuffer = new StringBuffer("");
    //String string = null;
    public byte[] perform(String text_from_client) {
        return CommPars(TextPars(text_from_client));
    }

    private String TextPars(String text_from_client) {
        StringBuffer stringBuffer = new StringBuffer("");
        String string = null;
        /*
        if (text_from_client.charAt(0) == '#') {
            isCommand = true;
            return text_from_client;
        }
        else if (text_from_client.charAt(0) == ' ') {
            for (int i = 1; i < text_from_client.length(); i++) {
                if (text_from_client.charAt(i) == ' ' && text_from_client.charAt(i + 1) == '#') {
                    for (int j = i + 1; j < text_from_client.length(); j++) {
                        stringBuffer.append(text_from_client.charAt(j));
                    }
                    string= stringBuffer.toString();
                    isCommand = true;
                   return string;
                 }
            }
        }
        else {
            return text_from_client;
        }
      System.out.println(string);
      return null;
      */
        if (text_from_client.charAt(0) == ' ') {
            for (int i = 1; i < text_from_client.length(); i++) {
                if (text_from_client.charAt(i) == ' ' && text_from_client.charAt(i + 1) == '%') {
                    for (int j = i + 1; j < text_from_client.length(); j++) {
                        stringBuffer.append(text_from_client.charAt(j));
                    }
                    string= stringBuffer.toString();
                    isCommand = true;
                    return string;
                }
            }
            if (string == null) {
                return text_from_client;
            }
        } else if (text_from_client.charAt(0) == '%') {
            isCommand = true;
            return text_from_client;
        } else {
            return text_from_client;
        }
        System.out.println(string);
        return null;
    }

    private byte[] CommPars(String text_from_client) {
        //Matcher object
        Matcher matc1;
        for (final TCP_commands comm : TCP_commands.values()) {
            //find commands in TCP_commands
            matc1 = Pattern.compile(comm.getReg()).matcher(text_from_client);
            if (matc1.find()) {
                switch (comm) {
                    case CMD_PING:
                        return new byte[]{CMD_PING};
                    case CMD_ECHO:
                        return rebuild(CMD_ECHO, parsComm1(text_from_client).getBytes());
                    case CMD_LOGIN:
                    String[] Mass=parsComm2(text_from_client);
                    try{
                        return serial(new Object[]{CMD_LOGIN,Mass[1],Mass[2]});//new byte[]{CMD_LOGIN};

                    }catch (IOException e){e.printStackTrace();
                    return null;}
                }
            }
        }

        return null;
    }

    public String Response(byte[] mess) {
        switch (mess[0]) {
            case CMD_PING_RESPONSE:
                return "Ping!";
            case SERIALIZATION_ERROR:
                return "Serialization error";
            case CMD_LOGIN_OK_NEW:
                return "Log!";
            default: {
                System.out.println(Byte.toUnsignedInt(mess[0]));
                return new String(mess);
            }
    }}
///////////
private String parsComm1(String command) {
    if (isCommand) {
        String arg = command.substring(command.indexOf(":") + 1, command.indexOf(";") - 1);
        System.out.println(arg);
        return arg;
    } else return command;
}

    private String[] parsComm2(String command) {
        String arguments = parsComm1(command);
        String[] string = arguments.split("(.)-");
        /*int i;
        StringBuffer stringBuffer1 = new StringBuffer("");
        String[] strings = new String[0];
        for (i=0;i<string.length-1;i++){
            stringBuffer1.append(string[i].split("(.)//s"));}
        strings[1]= stringBuffer1.toString();*/
        return string;
    }

    private byte[] rebuild(byte firstByte, byte[] byteArray) {
        byte[] rebuild= new byte[byteArray.length + 1];
        rebuild[0] = firstByte;
        for (int i = 1; i < rebuild.length; i++) {
            rebuild[i] = byteArray[i - 1];
        }
        return rebuild;
    }

    private byte[] serial(Object object) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream os = new ObjectOutputStream(out)) {
            os.writeObject(object);
            return out.toByteArray();
        }
    }

////
//
/*
private byte handleLogin(ConnectionHandler connection, byte command) throws IOException {

    String params = null;
    try {
        params = deserialize(command, 1, String[].class);
    } catch (Exception ex) {
        return SERIALIZATION_ERROR;
    }

    if (params.length != 2 || params[0] == null || params[1] == null)
        return WRONG_PARAMS;

    boolean userExists = this.server.userExists(params[0]);

    boolean success = this.server.login(params[0], params[1], connection);

    if (success) {
        if (userExists)
            return CMD_LOGIN_OK;
        else
            return CMD_LOGIN_OK_NEW;
    } else
        return LOGIN_WRONG_PASSWORD;

}*/
    /////

}

