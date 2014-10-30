package lessmoon.maze.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class SwingConsole {
    public static void run (final JFrame f/*, final int width,final int height*/) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                f.setTitle(f.getClass().getSimpleName());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setResizable(false);
                f.pack();
                f.setVisible(true);
                f.setLocationRelativeTo(null);
            }
        });
    }
}
