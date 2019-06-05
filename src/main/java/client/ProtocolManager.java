package client;

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

    private boolean isWaitingEcho = false;
    private boolean isWaitingList = false;
    private boolean isWaitingMsg = false;
    private boolean isWaitingFile = false;

    private boolean isLog = false;

    public static String pathToDirectForDownloadFile = "C:\\Users\\";

    public byte[] perform(String text_from_client) {

        return CommPars(TextPars(text_from_client));
    }
    private byte[] CommPars(String textFromLable) {
        Matcher matcher;
        for (final TCP_commands command : TCP_commands.values()) {
            matcher = Pattern.compile(command.getReg()).matcher(textFromLable);
            if (matcher.find()) {
                switch (command) {
                    case CMD_PING:
                        return new byte[]{CMD_PING};
                    case CMD_ECHO:
                        isWaitingEcho = true;
                        return serial(CMD_ECHO, new String(commPars1(textFromLable)));
                    case CMD_LOGIN:
                        String[] itemForLogin = commPars2(textFromLable);
                        return serial(CMD_LOGIN, new String[]{itemForLogin[1], itemForLogin[2]});
                    case CMD_LIST:
                        return requestList();
                    case CMD_MSG:
                        String[] itemForMsg = commPars2(textFromLable);
                        return serial(CMD_MSG, new String[]{itemForMsg[1], itemForMsg[2]});
                    case CMD_FILE:
                        return sendFile(textFromLable);
                }
            }
        }
        return new byte[]{0};
    }

    public String responseHendler(byte[] message) {
        switch (message[0]) {
            case CMD_PING_RESPONSE:
                return "Ping OK!";
            case SERIALIZATION_ERROR:
                return "Serialization ERROR";
            case INCORRECT_COMMAND:
                return "Incorrect command!";
            case CMD_LOGIN_OK_NEW:
                return "New user registration OK.";
            case CMD_LOGIN_OK:
                isLog = true;
                return "Login OK.";
            case CMD_MSG_SENT:
                return "Message sent.";
            case CMD_FILE_SENT:
                return "File sent.";
            case CMD_RECEIVE_MSG_EMPTY:
                return "";
            case SERVER_ERROR:
                return "Server ERROR!";
            case CMD_RECEIVE_FILE_EMPTY:
                return "";
            case WRONG_PARAMS:
                return "Wrong params!";
            case LOGIN_WRONG_PASSWORD:
                return "Wrong password!";
            case LOGIN_FIRST:
                return "First set login!";
            default: {
                return parserMessageFromServer(message);
            }
        }
    }

    private String parserMessageFromServer(byte[] message) {

        System.out.println(Byte.toUnsignedInt(message[0]));
        if (isWaitingEcho) {
            isWaitingEcho = false;
            return new String(message);

        } else if (isWaitingFile) {
            isWaitingFile = false;
            Object[] objectFile = null;
            objectFile = deserialize(message, 0, Object[].class);
            Path path = Paths.get(pathToDirectForDownloadFile + objectFile[1]);
            byte[] byteFile = (byte[]) objectFile[2];
            File file = new File(path.toString());

            try {
                if (file.createNewFile()) {
                    Files.write(path, byteFile, StandardOpenOption.APPEND);
                    return "File - " + objectFile[1].toString() + " success comes.";
                } else if (file.exists()) {
                    file.delete();
                    file.createNewFile();
                    Files.write(path, byteFile, StandardOpenOption.APPEND);
                    return "File - " + objectFile[1].toString() + " is existed and rewrite.";
                } else return "File - " + objectFile[1].toString() + " can`t create.";

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        } else if (isWaitingList || isWaitingMsg) {
            isWaitingList = false;
            isWaitingMsg = false;
            StringBuffer stringBuffer = new StringBuffer();
            String[] listName = null;
            listName = deserialize(message, 0, String[].class);
            if (listName.length == 2) {
                stringBuffer.append(listName[0] + "- " + listName[1]);
            } else {
                for (String s : listName) {
                    stringBuffer.append(s);
                }
            }
            return stringBuffer.toString();

        } else return null;
    }

    public String TextPars(String text_from_client) {
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
        return null;
    }

    private byte[] requestList() {
        isWaitingList = true;
        isWaitingFile = false;
        isWaitingMsg = false;
        return new byte[]{CMD_LIST};
    }

    public byte[] requestMsg() {
        isWaitingMsg = true;
        isWaitingFile = false;
        isWaitingList = false;
        return new byte[]{CMD_RECEIVE_MSG};
    }

    public byte[] requestFile() {
        isWaitingFile = true;
        isWaitingMsg = false;
        isWaitingList = false;
        return new byte[]{CMD_RECEIVE_FILE};
    }

    private byte[] sendFile(String command) {
        String[] item = commPars2(command);
        Path pathToFiles = Paths.get(item[2]);
        File fileToSend = new File(pathToFiles.toString());
        System.out.println(pathToFiles.getFileName());
        byte[] fileToByteArray = readFileToByteArray(fileToSend);
        return serial(CMD_FILE, new Object[]{item[1], pathToFiles.getFileName().toString(), fileToByteArray});
    }

    private static byte[] readFileToByteArray(File file) {
        FileInputStream fis = null;
        byte[] bArray = new byte[(int) file.length()];
        try {
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();

        } catch (IOException ioExp) {
            ioExp.printStackTrace();
        }
        return bArray;
    }

    public String commPars1(String comm) {
        if (isCommand) {
            String arg = comm.substring(comm.indexOf(":") + 2, comm.indexOf(";"));
            return arg;
        } else return comm;
    }

    public String[] commPars2(String comm) {
        String arguments = commPars1(comm);
        String[] strings = arguments.split("(\\w)-");

        strings[1] = strings[1].substring(0, strings[1].length()-1);
        return strings;
    }
    private byte[] rebuild(byte firstByte, byte[] byteArray) {
        byte[] rebuild = new byte[byteArray.length + 1];
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

    @SuppressWarnings("unchecked")
    private <T> T deserialize(byte[] data, int offset, Class<T> clazz) {
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

    public boolean isLog() {
        return isLog;
    }
}
