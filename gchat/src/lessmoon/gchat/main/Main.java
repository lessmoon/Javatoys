package lessmoon.gchat.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

import lessmoon.gchat.ui.*;
import lessmoon.gchat.client.*;

public class Main {
    public static final int WIDTH  =  500,
                            HEIGHT =  300;
    public static void main(String[] args) throws Exception {
        try { 
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); 
        } catch (Exception e) {
            System.err.println("Error occurs while setting look and feel:" + e.getMessage() + ",abort.");
            return;
        }
        SwingConsole.run(new GChat(),WIDTH,HEIGHT);
    }
}
