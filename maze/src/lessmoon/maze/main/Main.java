package lessmoon.maze.main;

import java.util.*;

import lessmoon.maze.ui.*;

public class Main {
       static public void main(String[] args) throws Exception {
            int width = 40,height= 30;
            for(int i = 0 ; i < args.length;i++){
                switch(args[i]){
                case "-w":
                    if( ++i < args.length){
                        width = Integer.parseInt(args[i]);
                    } else {
                        System.err.println("Option `-w' should be followed by the number of the width.");
                    }
                    break;
                case "-he":
                    if( ++i < args.length){
                        height = Integer.parseInt(args[i]);
                    } else {
                        System.err.println("Option `-he' should be followed by the number of the height.");
                    }
                    break;
                case "--help":
                case "-h":
                    System.out.println("Usage Segment:");
                    System.out.println("[-w width of the maze] set the width of the maze,default is 40");
                    System.out.println("[-he height of the maze]  set the height of the maze,default is 30");
                    System.out.println("[-h --help ] show this help ");
                    return;
                default:
                    System.err.println("Unknown option `" + args[i] + "' found.");
                    return;
                }
            }
            
            SwingConsole.run(new Maze(width,height));
       }
}
