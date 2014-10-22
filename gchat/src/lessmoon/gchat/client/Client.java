package lessmoon.gchat.client;

import lessmoon.gchat.util.*;
import lessmoon.gchat.server.*;

import java.util.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.concurrent.*;

public class Client {
    Socket                        ss;
    DataOutputStream              os = null;
    
    BlockingQueue<Message>        inqueue = new LinkedBlockingQueue<Message>();
    BlockingQueue<String>         outqueue = new LinkedBlockingQueue<String>();
    String                        name;
    static public final  String   IP = "127.0.0.1";
    static public final  int      PORT =   Server.PORT;
    public Client(String name) {
        this.name = name;
    }
    
    class MsgGetter implements Runnable {
        Socket         con;
        BufferedReader inFromServer;
        //static public final int TIMEOUT = 100;
        
        public MsgGetter(Socket c) throws IOException {
            con = c;
            inFromServer = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8")); 
        }

        Message getMsg() throws Exception {
            String id   = inFromServer.readLine();
            String date = inFromServer.readLine();/*Ignore message*/
            String content = inFromServer.readLine();
            return new Message(id,(new SimpleDateFormat("hh:mm:ss")).parse(date),content);/*repack the message*/
        }
        
        
        public void run(){
            try{
                while(true){
                    showMsg(getMsg());
                }
            } catch(Exception e) {
                System.out.print(e);
            } finally {
                try{
                    con.close();
                } catch(Exception e) {
                    System.out.print(e);
                }
            }
        }
    }

    public void run() throws Exception {
        ss = new Socket(IP,PORT);
        os = new DataOutputStream(ss.getOutputStream());
        sendMsg( new Message(name,""));
        new Thread((new MsgGetter(ss))).start();
        while(true){
            sendMsg( new Message(name,getMsg()));
        }
    }

    /*Blocked when nothing in the inqueue*/
    public Message fetchMsg() throws Exception {
        return inqueue.take();
    }
    
    public void pushMsg(String content)throws Exception{
        outqueue.put(content);
    }
    
    private void sendMsg(Message msg) throws IOException {
        synchronized (os){
            os.write(msg.toString().getBytes("UTF-8"));
        }
    }
    
    private String getMsg() throws Exception {
        return outqueue.take();
    }
    
    private void showMsg(Message msg)throws Exception{
        inqueue.put(msg);
    }

    public static void main(String[] args) throws  Exception  {
        final Client test = new Client("mom");
        new Thread(new Runnable(){
            public void run(){
                try{
                    test.run();
                } catch(Exception e){
                    System.err.println(e);
                }
            }
        }).start();
        
        new Thread(new Runnable(){
            public void run(){
                try{
                    while(true){
                        System.out.print(test.fetchMsg());
                    }
                } catch(Exception e){
                    System.err.println(e);
                }
            }
        }).start();

        while(true){
            test.pushMsg((new Scanner(System.in)).nextLine());
        }
    }
}
