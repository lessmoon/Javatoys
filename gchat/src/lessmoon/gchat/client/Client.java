package lessmoon.gchat.client;

import lessmoon.gchat.util.*;

import java.util.*;
import java.io.*;
import java.net.*;

public class Client {
    Socket ss ;
    DataOutputStream os = null;
    int  port;
    
    public Client(int p) throws IOException {
        port = p;
    }
    
    class MsgGetter implements Runnable {
        Socket         con;
        BufferedReader inFromServer;
        //static public final int TIMEOUT = 100;
        
        public MsgGetter(Socket c) throws IOException {
            con = c;
            inFromServer = new BufferedReader(new InputStreamReader(con.getInputStream())); 
        }

        public void run(){
            try{
                String sentence = null;
                while(true){
                    sentence = inFromServer.readLine() ;
                    showMsg(new Message(sentence));
                }
            } catch(IOException e) {
                System.out.print(e);
            } finally {
                try{
                    con.close();
                } catch(IOException e) {
                    System.out.print(e);
                }
            }
        }
    }

    public void run() throws IOException {
        System.out.print("Let us call you :");
        Scanner s = new Scanner(System.in);
        String name = s.nextLine();
        ss = new Socket("127.0.0.1",port);
        os = new DataOutputStream(ss.getOutputStream());
        sendMsg( new Message(name + "\n"));
        new Thread((new MsgGetter(ss))).start();
        while(true){
            sendMsg( new Message(new Message(s.nextLine()) + "\n"));
        }
    }

    public void showMsg(Message msg) throws IOException {
        System.out.println(msg);
    }
    
    public void sendMsg(Message msg) throws IOException {
        os.writeBytes(msg.toString());
    }
    
    public static void main(String[] args) throws IOException  {
        (new Client(6789)).run();
    }
}
