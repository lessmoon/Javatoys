package lessmoon.gchat.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class SwingConsole {
    public static void run (final JFrame f, final int width,final int height) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                f.setTitle(f.getClass().getSimpleName());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setSize(width,height);
                f.setResizable(false);
                f.setVisible(true);
                f.setLocationRelativeTo(null);
            }
        });
    }
}
