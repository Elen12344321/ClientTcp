package clienttcp1;

import commands.TCP_commands;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final byte CMD_MSG_SENT = 16;
    private static final byte CMD_FILE_SENT = 21;
    private static final byte CMD_LOGIN_OK_NEW = 6;
    private static final byte CMD_LOGIN_OK = 7;
    private static final byte CMD_RECEIVE_MSG_EMPTY = 26;
    private static final byte CMD_RECEIVE_FILE_EMPTY = 31;
    private static final byte SERVER_ERROR = 100;
    private static final byte SERIALIZATION_ERROR = 102;
    private static final byte INCORRECT_COMMAND = 103;
    private static final byte WRONG_PARAMS = 104;
    private static final byte LOGIN_WRONG_PASSWORD = 110;
    private static final byte LOGIN_FIRST = 112;

    private boolean isCommand = false;
    private String comm;
    private Object command;

    //StringBuffer stringBuffer = new StringBuffer("");
    //String string = null;
    private Message message;


    private String pathFile = "C:\\Users\\Admin\\";
    private boolean WEcho = false;
    private boolean WList = false;
    private boolean WMsg = false;
    private boolean WFile = false;

    public byte[] perform(String text_from_client) throws IOException {
        return CommPars(TextPars(text_from_client));
    }

    private String TextPars(String text_from_client) {
        StringBuffer stringBuffer = new StringBuffer("");
        String string = null;

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

    private byte[] CommPars(String text_from_client) throws IOException {
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
                        message = new Message();
                        message.setLogin(Mass[1]);
                        return (byte[]) serial(CMD_LOGIN,new String[]{Mass[0],Mass[1]});//new byte[]{CMD_LOGIN};
                    case CMD_LIST:
                     WList = true;
                        return new byte[]{CMD_LIST};
                    case CMD_MSG:
                        return serial(CMD_MSG, new String[]{message.getLogin(),parsComm1(text_from_client)});
                    case CMD_RECIVE_MSG:
                      WMsg = true;
                        return new byte[]{CMD_RECEIVE_MSG};
                 }
            }
        }

        return null;
    }


    public String Response(byte[] mess) {
        switch (mess[0]) {
            case CMD_PING_RESPONSE:
                return "PING";
            case SERIALIZATION_ERROR:
                return "SERIALIZATION_ERROR";
            case INCORRECT_COMMAND:
                return "INCORRECT_COMMAND";
            case CMD_LOGIN_OK_NEW:
                return "LOGIN_OK_NEW";
            case SERVER_ERROR:
                return "SERVER_ERROR";
            case CMD_RECEIVE_FILE_EMPTY:
                return "RECEIVE_FILE_EMPTY";
            case WRONG_PARAMS:
                return "WRONG_PARAMS";
            case LOGIN_WRONG_PASSWORD:
                return "WRONG_PASSWORD";
            case LOGIN_FIRST:
                return "LOGIN_FIRST";
            case CMD_LOGIN_OK:
                return "LOGIN_OK";
            case CMD_MSG_SENT:
                return "Message sent.";
            case CMD_FILE_SENT:
                return "File_SENT.";
            case CMD_RECEIVE_MSG_EMPTY:
                return "RECEIVE_MSG_EMPTY";
            default: {
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
        String[] string=arguments.split(" ");
        String g=string[0].toString();
        String[] strin1=g.split("-");
        string[0]=strin1[1];
        String d=string[1].toString();
        String[] strin=d.split("-");
        string[1]=strin[1];
        //string[2]=string[1];
        //string[1]=string[0];
        System.out.println(string.length);
        System.out.println(string[0]);
        System.out.println(string[1]);
        //System.out.println(string[2]);
        return string;
    }
    private byte[] parsComm3(byte command, byte String1[]) {
        byte[] string=new byte[3];
        string[0]= command;
        string[1]= String1[0];
        string[2]= String1[1];
        return string;
    }
/*private byte[] handleMsg(ConnectionHandler connectionHandler, byte[] command) {
		if (!this.server.isAuthenticated(connectionHandler))
			return LOGIN_FIRST;

		String[] args = null;
		try {
			args = deserialize(command, 1, String[].class);
		} catch (Exception ex) {
			return SERIALIZATION_ERROR;
		}

		if (args.length != 2 || args[0] == null || args[1] == null)
			return WRONG_PARAMS;

		boolean succeed = this.server.sendMessage(connectionHandler, args[0], args[1]);

		return succeed ? CMD_MSG_SENT : FAILED_SENDING;
	}*/
    private byte[] rebuild(byte firstByte, byte[] byteArray) {
        byte[] rebuild= new byte[byteArray.length + 1];
        rebuild[0] = firstByte;
        for (int i = 1; i < rebuild.length; i++) {
            rebuild[i] = byteArray[i - 1];
        }
        return rebuild;
    }
    private byte[] serial(byte command, Object object) {
        try {
            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                 ObjectOutputStream os = new ObjectOutputStream(out)) {
                os.writeObject(object);
                byte[] bytes = out.toByteArray();
                return rebuild(command, bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
     /*private byte[] serial(Object object, String[] strings) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream os = new ObjectOutputStream(out)) {
            os.writeObject(object);
            return out.toByteArray();
        }
    }*/

     /*
     * @SuppressWarnings("unchecked")
	private <T> T deserialize(byte[] data, int offset, Class<T> clazz) throws ClassNotFoundException, IOException {
		try (ByteArrayInputStream stream = new ByteArrayInputStream(data, offset, data.length - offset);
				ObjectInputStream objectStream = new ObjectInputStream(stream)) {
			return (T) objectStream.readObject();
		}
	}
     * */
    @SuppressWarnings("unchecked")
    private <T> T deserialize(byte[] data, int offset, Class<T> clazz){
        try {
            try (ByteArrayInputStream stream = new ByteArrayInputStream(data, offset, data.length - offset);
                 ObjectInputStream objectStream = new ObjectInputStream(stream)) {
                return (T) objectStream.readObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
/*private byte[] handleList(ConnectionHandler connectionHandler) {
		if (!this.server.isAuthenticated(connectionHandler))
			return LOGIN_FIRST;

		try {
			return serialize(this.server.getUsers());
		} catch (Exception ex) {
			return SERVER_ERROR;
		}
	}
*/
////
private String parsResp(byte[] resp) {

    System.out.println(Byte.toUnsignedInt(resp[0]));
    //Echo response
    if(WEcho){
        WEcho = false;
        return new String(resp);
        //
    } else if (WList || WMsg) {
        WList = false;
        WMsg = false;
        StringBuffer stringBuffer = new StringBuffer();
        String[] listName = null;
        listName = deserialize(resp, 0, String[].class);
        for (String s : listName) {
            stringBuffer.append(s);
        }
        return stringBuffer.toString();
    }
    else if (WFile) {
         WFile = false;
         return null;
    } else return null;
}



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

