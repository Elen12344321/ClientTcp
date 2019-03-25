package clienttcp1;

import commands.TCP_commands;

import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import static java.lang.System.out;

public class ProtocolManager {
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
    StringBuffer stringBuffer = new StringBuffer("");
    String string = null;
    public byte[] perform(String text_from_client) {
        return CommPars(TextPars(text_from_client));
    }

    private String TextPars(String text_from_client) {
        if (text_from_client.charAt(0) == '#') {
            isCommand = true;
            return text_from_client;
        }
        else if (text_from_client.charAt(0) == ' ') {
            for (int i = 0; i < text_from_client.length(); i++) {
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
      out.println(string);
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
                    default:
                        return null;
                }
            }
        }

        return null;
    }

    public String Response(byte[] mess) {
        switch (mess[0]) {
            case CMD_PING_RESPONSE:
                return "Ping!";
            default: {
               return new String(mess);
            }
       }
    }




}

