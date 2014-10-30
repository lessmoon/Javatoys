package lessmoon.maze.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

import lessmoon.maze.algorithm.*;
import lessmoon.maze.util.*;

public class Maze extends JFrame {
    MazeMap map = new MazeMap();
    private final int   MAP_WIDTH;
    private final int   MAP_HEIGHT;
    private final int   TILE_WIDTH;
    private _2DVector   man = new _2DVector(0,0);
    private _2DVector   dest ;
    private int         gamestate = RUNNING;
    static final  int   RUNNING = 1,STOP = 2,LOSE = 3,WIN = 4;
    
    
    public Maze(int w,int h,int t){
        BorderLayout bl = new BorderLayout();
        setLayout(bl);
        MAP_WIDTH = w;
        MAP_HEIGHT = h;
        TILE_WIDTH = t;
        dest = new _2DVector(MAP_WIDTH - 1,MAP_HEIGHT - 1);
        map.ChangeSize(MAP_WIDTH,MAP_HEIGHT);
        map.GenerateMaze();
        //map.show();
        addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:  case KeyEvent.VK_A:
                    if(map.IsValid(man,MazeDirection.WEST) )
                        man.X --;
                    else
                        return;
                    break;
                case  KeyEvent.VK_RIGHT:case KeyEvent.VK_D:
                    if(map.IsValid(man,MazeDirection.EAST))
                        man.X ++;
                    else 
                        return;
                    break;
                case KeyEvent.VK_UP:    case KeyEvent.VK_W:
                    if(map.IsValid(man,MazeDirection.NORTH))
                        man.Y --;
                    else 
                        return;
                    break;
                case KeyEvent.VK_DOWN:  case KeyEvent.VK_S:
                    if(map.IsValid(man,MazeDirection.SOUTH))
                        man.Y ++;
                    else 
                        return;
                    break;
                case KeyEvent.VK_SPACE:
                    /* Tips! */
                    break;
                default:
                    return;
                }
                repaint();
                if(man.Equals(dest)){
                    JOptionPane.showMessageDialog(null,"Congratulations,You win!");
                }
            }
        });
        JPanel jpc = new JPanel();
        JPanel jp = new JPanel(){
            public void paint(Graphics g){
                super.paint(g);
                g.setColor(Color.BLUE);
                int x = 0;
                for(int i = 0 ; i < MAP_WIDTH;i++){
                    x += TILE_WIDTH;
                    int y = 0;
                    for(int j = 0 ; j < MAP_HEIGHT;j++){
                        y += TILE_WIDTH;
                        //System.out.println("("+i + ","+j+")");
                        if(!map.At(i,j).EastIsLinked){
                            //System.out.println("("+i + ","+j+",|)");
                            g.drawLine(x,y - TILE_WIDTH,x,y);
                            
                        }
                        if(!map.At(i,j).SouthIsLinked){
                            //System.out.println("("+i + ","+j+",-)");
                            g.drawLine(x - TILE_WIDTH,y,x,y);
                        }
                    }
                }
                g.drawLine(0,0,0,MAP_HEIGHT * TILE_WIDTH);
                g.drawLine(0,0,MAP_WIDTH * TILE_WIDTH,0);
                g.drawLine(MAP_WIDTH * TILE_WIDTH,0,MAP_WIDTH * TILE_WIDTH,MAP_HEIGHT * TILE_WIDTH);
                g.drawLine(0,MAP_HEIGHT * TILE_WIDTH,MAP_WIDTH * TILE_WIDTH,MAP_HEIGHT * TILE_WIDTH);
                
                g.setColor(Color.RED);
                g.fillRect(dest.X* TILE_WIDTH + 1,dest.Y* TILE_WIDTH + 1,TILE_WIDTH - 1,TILE_WIDTH - 1);
                /*Draw the little man!*/
                g.setColor(Color.GREEN);
                g.fillRect(man.X * TILE_WIDTH + 1,man.Y * TILE_WIDTH + 1,TILE_WIDTH - 1,TILE_WIDTH - 1);
            }
        };
        jp.setPreferredSize(new Dimension(MAP_WIDTH * TILE_WIDTH + 1,MAP_HEIGHT * TILE_WIDTH + 1));
        JPanel fbar = new JPanel();
        fbar.setLayout(new GridLayout(1,3));
        fbar.setPreferredSize(new Dimension(MAP_WIDTH * TILE_WIDTH + 1,20));
        final JLabel min = new JLabel("0",JLabel.CENTER),l = new JLabel(":",JLabel.CENTER),sec = new JLabel("0",JLabel.CENTER);
        fbar.add(min);
        fbar.add(l);
        fbar.add(sec);
        javax.swing.Timer timer = new javax.swing.Timer(1000,new ActionListener(){
            public void actionPerformed(ActionEvent e){
                counter --;
                min.setText("" + counter/60);
                sec.setText("" + counter%60);
                if(counter == 0){
                    JOptionPane.showMessageDialog(null,"Time over,you lost!");
                    ((javax.swing.Timer)e.getSource()).stop();
                }
            }
            int counter = MAP_WIDTH * MAP_HEIGHT/3;
        });
        timer.start();
        add(jp,BorderLayout.CENTER);
        add(fbar,BorderLayout.SOUTH);
    }
    
    public void init_game(int height,int width){
        
    }
    
    public void change_state(final int new_state){
        
    }
    
    static public void main(String[] args){
        SwingConsole.run(new Maze(30,30,8));
    }
}