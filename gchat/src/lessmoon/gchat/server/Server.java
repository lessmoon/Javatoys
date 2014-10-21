package lessmoon.gchat.server;

import lessmoon.gchat.util.*;

import java.util.*;
import java.io.*;
import java.net.*;
import java.text.*;

public class Server {
    static public final int PORT = 6789;
    ServerSocket ss ;
    ArrayList<DataOutputStream> ospool = new ArrayList<DataOutputStream>();
    
    public Server() throws IOException {
        ss = new ServerSocket(PORT);
    }
    
    class MsgGetter implements Runnable {
        Socket              con;
        BufferedReader      inFromClient;
        DataOutputStream    outToClient;
        String              id;
        //static public final int TIMEOUT = 100;

        public MsgGetter(Socket c,DataOutputStream otc)  throws IOException {
            con = c;
            inFromClient = new BufferedReader(new InputStreamReader(con.getInputStream())); 
            outToClient  = otc;
        }

        public void run() {
            try{
                id = inFromClient.readLine(); /*The client send his name first*/
                String sentence = null;
                sendMsg(new Message("System:Welcome!Here is our new friend " + id + "!"));
                while(true){
                    sendMsg( new Message(id + "@" + (new SimpleDateFormat("hh:mm:ss")).format(new Date()) +  " Said: "  + inFromClient.readLine()) );
                }
            } catch(IOException e){
                System.out.println("Connection over");
            } finally {
                /* Not thread safe*/
                synchronized (ospool){
                    ospool.remove(outToClient);
                }
                try {
                    sendMsg( new Message(id + "@" + (new SimpleDateFormat("hh:mm:ss")).format(new Date()) +  " quit chatroom." ));
                } catch(IOException e){
                    System.out.print(e);
                }
            }
        }
    }

    public void run() throws IOException {
        DataOutputStream dos = null;
        while(true){
            System.out.println("Wait connecting...");
            Socket con = ss.accept();
            System.out.println("Connected ok!");
            ospool.add(dos = new DataOutputStream(con.getOutputStream()));
            new Thread(new MsgGetter(con,dos)).start();
        }
    }

    public void sendMsg(Message msg) throws IOException{
        synchronized (ospool){
            for(DataOutputStream os : ospool){
                os.writeBytes(msg.toString() + "\n");
            }
        }
    }
    
    public static void main(String[] args) throws IOException  {
        (new Server()).run();
    }
}
