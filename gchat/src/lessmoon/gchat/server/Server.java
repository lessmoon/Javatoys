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

        public MsgGetter(Socket c,DataOutputStream otc)  throws IOException {
            con = c;
            inFromClient = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8")); 
            outToClient  = otc;
        }

        Message getMsg() throws IOException {
            String nid   = inFromClient.readLine();
            String date = inFromClient.readLine();/*Ignore message*/
            String content = inFromClient.readLine();
            return new Message(nid,content);/*repack the message*/
        }

        public void run() {
            try{
                Message msg = getMsg();
                id = msg.srcid;
                sendMsg(new Message("System","Welcome to our new friend " + id + "."));
                while(true){
                    sendMsg(getMsg());
                }
            } catch(Exception e){
                System.out.println("Connection over");
            } finally {
                synchronized (ospool){
                    ospool.remove(outToClient);
                }
                try {
                    sendMsg( new Message("System",id + " quit chatroom." ));
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
                os.write(msg.toString().getBytes("UTF-8"));
            }
        }
    }
    
    public static void main(String[] args) throws IOException  {
        (new Server()).run();
    }
}
