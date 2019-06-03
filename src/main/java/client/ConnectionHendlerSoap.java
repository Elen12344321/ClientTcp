package client;

import commands.TCP_commands;
import lpi.server.soap.Message;
import lpi.server.soap.*;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.ClientResponse;
import sun.net.www.http.HttpClient;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import javax.xml.ws.Response;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConnectionHendlerSoap implements  Runnable, Closeable{
     private Registry rgs;
    //Client client = new Client();
    private IChatServer server;
    private int Port;
    private String Host;
   // private Ping ping1;
    //private Echo echo1;
   private static final String REST_URI = "http://localhost:8082/spring-jersey/resources/employees";

    private ProtocolManager protocolManager = new ProtocolManager();
    ChatServer serverWrapper;
    IChatServer serverProxy;

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/com.vogella.jersey.first").build();
    }
  Client config;// = new Client();
    Client request;
    //Client cl=new Client();
    public ConnectionHendlerSoap(String Host, int Port) throws MalformedURLException {
        this.Port = Port;
        this.Host = Host;
        String uri = String.format("http://%s:%d/chat", "localhost", 8080);

       // ResourceConfig resourceConfig = new DefaultResourceConfig();
       // resourceConfig.getSingletons().add(this);
        //resourceConfig.getProperties().put("com.sun.jersey.spi.container.ContainerRequestFilters",
              //  "lpi.server.rest.AuthorizationFilter");

        try {
          //  this.server = GrizzlyServerFactory.createHttpServer(URI.create(uri), resourceConfig);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new RuntimeException("Failed to start REST Server", e);
        }
        /*this.request = new Client() {
            @Override
            public Configuration getConfiguration() {
                return null;
            }

            @Override
            public Client property(String s, Object o) {
                return null;
            }

            @Override
            public Client register(Class<?> aClass) {
                return null;
            }

            @Override
            public Client register(Class<?> aClass, int i) {
                return null;
            }

            @Override
            public Client register(Class<?> aClass, Class<?>... classes) {
                return null;
            }

            @Override
            public Client register(Class<?> aClass, Map<Class<?>, Integer> map) {
                return null;
            }

            @Override
            public Client register(Object o) {
                return null;
            }

            @Override
            public Client register(Object o, int i) {
                return null;
            }

            @Override
            public Client register(Object o, Class<?>... classes) {
                return null;
            }

            @Override
            public Client register(Object o, Map<Class<?>, Integer> map) {
                return null;
            }

            @Override
            public void close() {

            }

            @Override
            public WebTarget target(String s) {
                return null;
            }

            @Override
            public WebTarget target(URI uri) {
                return null;
            }

            @Override
            public WebTarget target(UriBuilder uriBuilder) {
                return null;
            }

            @Override
            public WebTarget target(Link link) {
                return null;
            }

            @Override
            public Invocation.Builder invocation(Link link) {
                return null;
            }

            @Override
            public SSLContext getSslContext() {
                return null;
            }

            @Override
            public HostnameVerifier getHostnameVerifier() {
                return null;
            }
        };*/
     }
    /////////////////////////
    IChatServer hello;// = service.getPort(IChatServer.class);

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

                                String[] mass = protocolManager.parsComm2(text_from_client);
                                String[] mass1 = protocolManager.parsComm3(mass[1].toString());
                                log_send = mass1[0];
                                //log-1 pass-2
                                ident = serverProxy.login(mass1[0], mass[2]);
                                File_Rsvr();
                                ///////////////////////
                                string = new String("log ok");
                                bool = true;
                            break;
                        case CMD_LIST:
                            if (ident != null) {
                            //identification
                            string = new String("list:" + " " + (serverProxy.listUsers(ident)));
                            }else string = new String("no log");
                            bool = true;
                            break;

                        case CMD_MSG:
                            if (ident != null) {
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
                            if (ident != null) {
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
                            // File_Rsvr();
                            string = new String("Ok!  send file with name" + " " + pathFile1.toString());
                            }else string = new String("no log");
                            bool = true;
                            break;
                        case CMD_RECIVE_FILE:
                            break;
                        case EXIT:
                            if (ident != null) {
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
   public String mass;
   private String mass_1;

  //Timer time1,timer;
    private void File_Rsvr () {
        //private class File_Rsvr exstends TimeTask{
      //  TimerTask time2 = null;
        Timer time1;
        time1= new Timer();
        TimerTask timer;
        timer= new TimerTask() {
            ///////////////////////////////////////
            public void run() {
                if (ident == null) {
                    try {
                        close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
               else {  if (mass!= null) {
                    mass_1=null;
                    try {
                        mass_1 = serverProxy.listUsers(ident).toString();
                    } catch (ArgumentFault argumentFault) {
                        argumentFault.printStackTrace();
                    } catch (ServerFault serverFault) {
                        serverFault.printStackTrace();
                    }
                    if (!((mass).equals(mass_1))) {
                        string = new String("update list of active users: " + mass_1);
                        mass = mass_1;      }
                    else{
                      //  string = new String("list of active users with no update: " + mass_1);
                    }
                   } else {
                    try {
                        ////////////////////////////
                        mass = (serverProxy.listUsers(ident)).toString();
                    } catch (ArgumentFault argumentFault) {
                        argumentFault.printStackTrace();
                    } catch (ServerFault serverFault) {
                        serverFault.printStackTrace();
                    }
                    string = new String("list of active users: " + mass);
                }}
            }
        };
        /////////////////////////////////
        time1.schedule(timer,50);
    }
    ////////////////////////////////////
    public boolean regClient() {
        try {

           // Client request = new Client("http://localhost:8080/RESTfulExample/json/product/get");
            //request.accept("application/json");
            //ClientResponse<String> response = request.get(String.class);



        } catch (ClassCastException e) {

            e.printStackTrace();

        }
        return true;
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
                    mass= null;
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

