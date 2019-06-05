package client;

import commands.TCP_commands;//
import org.glassfish.hk2.api.MultiException;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConnectionHandlerRest implements Closeable {

    private javax.ws.rs.client.Client client;
    private static String ping = new String("http://localhost:8080/chat/server/ping");
    private static String echo = new String("http://localhost:8080/chat/server/echo");
    private static String login = new String("http://localhost:8080/chat/server/user");
    private static String list = new String("http://localhost:8080/chat/server/users");
    private static String msg;
    //private static String URL_RECEIVE_MSG;
    private static String file;
    //private static String URL_RECEIVE_FILE;

    private ProtocolManager protocolManager = new ProtocolManager();

    private boolean isReg = false;
    private boolean Fal = false;
    private volatile String string;
    private String mass;


    private TimerTask Tt1;
    private TimerTask Tt2;
    private Timer t1;
    private Timer t2;

    private Response resp;
    private Arr arr;
    private String url;
    private UInfo UInfo;


    public String get_Echo() {
        return string;
    }

    public void set_Echo(String resp) {
        this.string = resp;
    }
    public String Url() {
        return url;
    }
   private java.util.Base64.Encoder cod = java.util.Base64.getEncoder();
    private java.util.Base64.Decoder dec = java.util.Base64.getDecoder();
    private String file_1;

    public ConnectionHandlerRest() {
        //constructor
        this.client = javax.ws.rs.client.ClientBuilder.newClient();
    }


    public void perform(String text_from_client) throws IOException {
        CommPars_1(protocolManager.TextPars(text_from_client));
    }
    public static long start, end, time;
    private String pathFile = "C:\\Users\\Admin\\Idea\\";

    private void CommPars_1(String text_from_client) {
        Matcher matcher;
        try {
            for (final TCP_commands comm : TCP_commands.values()) {
                matcher = Pattern.compile(comm.getReg()).matcher(text_from_client);
                if (matcher.find()) {
                    try {
                        switch (comm) {
                            case CMD_PING:
                                string = client.target(ping).request(MediaType.TEXT_PLAIN_TYPE).get(String.class);
                                url = ping; isReg = true;
                                break;
                            case CMD_ECHO:
                                String echoText = new String(protocolManager.commPars1(text_from_client));
                                string = client.target(echo).request(MediaType.TEXT_PLAIN_TYPE).post(Entity.text(echoText), String.class);
                                url = echo;
                                isReg = true;
                                break;
                            case CMD_LOGIN:
                                String[] str = protocolManager.commPars2(text_from_client);
                                loger(str);
                                url = login;
                                 isReg = true;
                                break;
                            case CMD_LIST:
                                arr = client.target(list).request(MediaType.APPLICATION_JSON_TYPE).get(Arr.class);
                                string= new String("users:"+ arr.items.toString());
                                mass = arr.items.toString();
                                url = list;
                                isReg = true;
                                break;
                            case CMD_MSG:
                                String[] str1 = protocolManager.commPars2(text_from_client);
                                mess(str1);
                                url= msg;
                                isReg = true;
                                break;
                            case CMD_FILE:
                                str1 = protocolManager.commPars2(text_from_client);
                                file(str1);
                                isReg = true;
                                break;
                        }
         //              System.out.println(resp.getStatus());
                    } catch (NotAuthorizedException e) {
                        string = new String("no login");
                    }
                }
            } } catch (ProcessingException e) {
                        close();
        }
    }

    @Override
    public void close() {
        if (client != null) {
            client.close();

        }
    }
    private void loger(String[] itemForLogin) {
        UInfo = new UInfo();
        //
        //        UInfo.setLogin(itemForLogin[0]);

        //UInfo.setLogin(itemForLogin[0]);
        UInfo.setLogin(itemForLogin[1]);
        UInfo.setPassword(itemForLogin[2]);
     ////////////////////////////////////////////////////////
        Entity<UInfo> userInfoEntity = Entity.entity(UInfo, MediaType.APPLICATION_JSON_TYPE);
        resp = client.target(login).request(MediaType.TEXT_PLAIN_TYPE).put(userInfoEntity);
        this.client.register(HttpAuthenticationFeature.basic(UInfo.getLogin(), UInfo.getPassword()));
       string = new String("user registry ok!");
       msg = new String("http://localhost:8080/chat/server/" + UInfo.getLogin() + "/messages");
        file = new String("http://localhost:8080/chat/server/" + UInfo.getLogin() + "/files");
    }

    private void mess(String[] itemForMsg) {
        msg = new String("http://localhost:8080/chat/server/" + itemForMsg[1] + "/messages");
        //////////////////////////////////
        resp = client.target(msg).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.text(itemForMsg[2]));
        if (resp.getStatus() == Response.Status.CREATED.getStatusCode())
            string= new String("Ok! Send message");
        else string = new String("Err msg!");
    }

    private void file(String[] itemForFile) {
        file= new String("http://localhost:8080/chat/server/" + itemForFile[1] + "/files");
        Path pathToFile = Paths.get("C:\\Users\\Admin\\Idea\\Idea.txt");
        ////////////////////////////////////////////////
        File fileToSend = new File(pathToFile.toString());
        try {
            switch (file_1 = cod.encodeToString(Files.readAllBytes(fileToSend.toPath()))) {
            }    } catch (IOException e) {
            e.printStackTrace();
        }
        FileInfo fI_1 = new FileInfo(UInfo.getLogin(), fileToSend.getName(), file_1);
        resp = client.target(file).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(fI_1));
        if (resp.getStatus() == Response.Status.CREATED.getStatusCode())
            string = new String("Send file ok!");
        else string = new String("Error file");
    }






}