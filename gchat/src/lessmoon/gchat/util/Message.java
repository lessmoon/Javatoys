package lessmoon.gchat.util;

import java.util.*;
import java.text.*;

public class Message {
    public final String content;
    public final String srcid;
    public final Date   time;
    
    public Message(final String from,final String c){
        srcid   = from;
        content = c;
        time    = new Date();
    }

    public Message(final String from,final Date t,final String c){
        srcid   = from;
        time    = t;
        content = c;
    }
    
    public String toString(){
        return srcid + "\n" + (new SimpleDateFormat("hh:mm:ss")).format(time) + "\n" + content + "\n";
    }
}
