package lessmoon.gchat.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.text.*;

import lessmoon.gchat.util.*;
import lessmoon.gchat.client.*;

public class GChat extends JFrame {
    public static final int TEXT_WIDTH = 44;
    public static final int TEXT_HEIGHT = 12;
    public static final int MAX_NAME_SIZE = 14;
    public static final int PORT = 6789;

    final JTextField      txt     = new JTextField(TEXT_WIDTH - 8);
    final JTextArea       content = new JTextArea("Got a new nickname,dude:",TEXT_HEIGHT,TEXT_WIDTH);
    final ButtonListener  bl      = new ButtonListener();
    final ButtonListenerSetName  setname = new ButtonListenerSetName();
    final JScrollPane     p       = new JScrollPane(content);
    Client                client  = null;

    public GChat() throws Exception {
        setLayout( new FlowLayout());
        content.setEditable(false);
        content.setLineWrap(true);
        add( p );
        add(txt);
        JButton b = new JButton("OK");
        b.addActionListener(setname);
        add(b);
        getRootPane().setDefaultButton(b);
    }
    
    void run() throws Exception{
       final Client c = client;
       new Thread(new Runnable(){
            public void run(){
                try{
                    c.run();
                } catch(Exception e){
                    content.setText("Connection faild.Please check your internet!");
                    e.printStackTrace();
                    return;
                }
            }
        }).start();

        new Thread(new Runnable(){
            public void run(){
                try{
                    while(true){
                        Message msg = c.fetchMsg();
                        String c = msg.srcid + "   " + (new SimpleDateFormat("hh:mm:ss")).format(msg.time) + "\n  " + msg.content + "\n";
                        content.append(c);
                        p.getViewport().setViewPosition(new Point(0,content.getLineCount() * TEXT_WIDTH));
                    }
                } catch(Exception e){
                    System.err.println(e);
                }
            }
        }).start();

    }

    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(!txt.getText().isEmpty()){
                try{
                    client.pushMsg(txt.getText());
                    txt.setText("");
                }catch(Exception ec){
                    ec.printStackTrace();
                }
            }
        }
    }
    
    class ButtonListenerSetName implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(!txt.getText().isEmpty()){
                String name = txt.getText();
                if(name.length() > MAX_NAME_SIZE){
                    content.setText(":(Your name is too long,dude(" + MAX_NAME_SIZE + "at most)." );
                    return;
                } else if (name.equalsIgnoreCase("System")){
                    content.setText(":(Don't use this nickname,dude." );
                    return;
                }
                client = new Client(txt.getText());
                try{
                    ((JButton)e.getSource()).removeActionListener(this);
                    ((JButton)e.getSource()).addActionListener(bl);
                    ((JButton)e.getSource()).setText("Send");
                    content.setText("");
                    txt.setText("");
                    run();
                } catch(Exception ec){
                    ec.printStackTrace();
                    System.exit(0);
                }
            }
        }
    }
}
