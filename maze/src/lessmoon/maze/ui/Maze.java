package lessmoon.maze.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

import lessmoon.maze.algorithm.*;
import lessmoon.maze.util.*;

public class Maze extends JFrame {
    MazeMap map = new MazeMap();
    private  int   MAP_WIDTH;
    private  int   MAP_HEIGHT;
    private  int   TILE_WIDTH;
    private  final int CANVAS_WIDTH = 400;
    private _2DVector   man = new _2DVector(0,0);
    private _2DVector   dest ;
    private _2DVector   tip ;
    private int         gamestate = RUNNING;
    static final  int   RUNNING = 1,STOP = 2,LOSE = 3,WIN = 4;
    private JLabel timedisplay = null;
    javax.swing.Timer timer ;
    private  int    counter ;
    java.util.Stack<MazeDirection> pathtoDest = new java.util.Stack<MazeDirection>();
    public Maze(int w,int h){
        MAP_WIDTH = w;
        MAP_HEIGHT = h;
        TILE_WIDTH = CANVAS_WIDTH/w;
        dest = new _2DVector(MAP_WIDTH - 1,MAP_HEIGHT - 1);
        map.ChangeSize(MAP_WIDTH,MAP_HEIGHT);
        addKeyListener(new KeyAdapter(){
            public void keyReleased(KeyEvent e) {
                MazeDirection next = null;
                switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:  case KeyEvent.VK_A:
                    if(map.IsValid(man,MazeDirection.WEST) )
                        synchronized(man){
                            man.X --;
                            next = MazeDirection.WEST;
                        }
                    else
                        return;
                    break;
                case  KeyEvent.VK_RIGHT:case KeyEvent.VK_D:
                    if(map.IsValid(man,MazeDirection.EAST))
                        synchronized(man){
                            man.X ++;
                            next = MazeDirection.EAST;
                        }
                    else 
                        return;
                    break;
                case KeyEvent.VK_UP:    case KeyEvent.VK_W:
                    if(map.IsValid(man,MazeDirection.NORTH))
                        synchronized(man){
                            man.Y --;
                            next = MazeDirection.NORTH;
                        }
                    else 
                        return;
                    break;
                case KeyEvent.VK_DOWN:  case KeyEvent.VK_S:
                    if(map.IsValid(man,MazeDirection.SOUTH))
                        synchronized(man){
                            man.Y ++;
                            next = MazeDirection.SOUTH;
                        }
                    else 
                        return;
                    break;
                case KeyEvent.VK_SPACE:
                    tip = next();
                    repaint();
                    deccounter();
                    return;
                default:
                    return;
                }
                repaint();
                if(pathtoDest.peek() == next){
                    pathtoDest.pop();
                } else {
                    pathtoDest.push(next.opposite());
                }
                synchronized(man){
                    if(man.Equals(dest)){
                        change_state(WIN);
                    }
                }
            }
        });
        JPanel jp = new JPanel(){
            public void paint(Graphics g){
                synchronized(this){
                    super.paint(g);
                    g.translate(TILE_WIDTH/2,TILE_WIDTH/2);
                    g.setColor(Color.BLUE);
                    int x = 0;
                    synchronized(map){
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
                    }
                    g.drawLine(0,0,0,MAP_HEIGHT * TILE_WIDTH);
                    g.drawLine(0,0,MAP_WIDTH * TILE_WIDTH,0);
                    g.drawLine(MAP_WIDTH * TILE_WIDTH,0,MAP_WIDTH * TILE_WIDTH,MAP_HEIGHT * TILE_WIDTH);
                    g.drawLine(0,MAP_HEIGHT * TILE_WIDTH,MAP_WIDTH * TILE_WIDTH,MAP_HEIGHT * TILE_WIDTH);
                    
                    /*Draw the destnation!*/

                    g.setColor(Color.RED);
                    g.fillRect(dest.X * TILE_WIDTH + 1,dest.Y * TILE_WIDTH + 1,TILE_WIDTH - 1,TILE_WIDTH - 1);
                    
                    /*Draw the tip!!!*/
                    
                    if(tip != null){
                        g.setColor(Color.YELLOW);
                        g.fillRect(tip.X * TILE_WIDTH + 1,tip.Y * TILE_WIDTH + 1,TILE_WIDTH - 1,TILE_WIDTH - 1);
                        tip = null;
                    }

                    /*Draw the little man!*/
                    
                    g.setColor(Color.GREEN);
                    g.fillRect(man.X * TILE_WIDTH + 1,man.Y * TILE_WIDTH + 1,TILE_WIDTH - 1,TILE_WIDTH - 1);
                }
            }
        };
        jp.setPreferredSize(new Dimension(MAP_WIDTH * TILE_WIDTH + 1 + TILE_WIDTH,MAP_HEIGHT * TILE_WIDTH + 1 + TILE_WIDTH));
        JPanel fbar = new JPanel(new BorderLayout());
        timedisplay = new JLabel("<HTML><font size=\"5\" face=\"arial\" color=\"red\">Welcome!</font></HTML>",JLabel.CENTER);
        fbar.add(timedisplay,BorderLayout.NORTH);

        add(fbar,BorderLayout.NORTH);
        add(jp,BorderLayout.CENTER);
        
        timer = new javax.swing.Timer(1000,new ActionListener(){
            public void actionPerformed(ActionEvent e){
                deccounter();
            }
        });
        init_game();
    }

    /**
     * Travalse a map and judge if the way is correct
     * @param i
     * @param j
     * @param Dir
     * @param TargetPos
     * @return
     */
    private boolean travel(int i,int j,MazeDirection Dir,_2DVector TargetPos)
    {
        if(TargetPos.Equals(i,j)){
            pathtoDest.push(MazeDirection.HERE);
            return true;
        }
        if(map.IsValid(i,j,MazeDirection.EAST)
                &&Dir != MazeDirection.EAST){
             if(travel(i + 1,j,MazeDirection.WEST,TargetPos)){
                pathtoDest.push(MazeDirection.EAST);
                return true;
             }
         }
         if(map.IsValid(i,j,MazeDirection.SOUTH)
            &&Dir != MazeDirection.SOUTH){
             if(travel(i,j + 1,MazeDirection.NORTH,TargetPos)){
                 pathtoDest.push(MazeDirection.SOUTH);
                 return true;
             }
         }
         if(map.IsValid(i,j,MazeDirection.NORTH)
            &&Dir != MazeDirection.NORTH){
             if(travel(i,j - 1,MazeDirection.SOUTH,TargetPos)){
                 pathtoDest.push(MazeDirection.NORTH);
                 return true;
             }
         }
         if(map.IsValid(i,j,MazeDirection.WEST)
            &&Dir != MazeDirection.WEST){
             if(travel(i - 1,j,MazeDirection.EAST,TargetPos)){
                 pathtoDest.push(MazeDirection.WEST);
                 return true;
             }
         }
         return false;
    }

    private void calculate(_2DVector ThisPos,_2DVector TarPos)
    {
        synchronized(pathtoDest){
            travel(ThisPos.X,ThisPos.Y,MazeDirection.HERE,TarPos);
        }
    }

    synchronized _2DVector next(){
        return man.Plus(pathtoDest.peek());
    }
    
    synchronized void deccounter(){
        counter --;
        timedisplay.setText("<HTML><font size=\"5\" face=\"arial\" color=\"red\">"+String.valueOf(counter/60)+":"+String.valueOf(counter%60)+"</font></HTML>");
        if(counter <= 0){
            synchronized(timer){
                timer.stop();
            }
            change_state(LOSE);
        }
    }
    
    public void init_game(){
        synchronized(man){
            man.X = 0;
            man.Y = 0;
        }
        change_state(RUNNING);
        synchronized(map){
            map.Reset();
            map.GenerateMaze();
        }
        synchronized(pathtoDest){
            pathtoDest.clear();
            calculate(man,dest);
        }
        counter = MAP_HEIGHT * MAP_WIDTH / 16 + pathtoDest.size() / 5 + 1;
        synchronized(timer){
            timer.start();
        }
    }

    public synchronized boolean change_state(final int new_state){
        switch(new_state){
        case WIN:
            if(gamestate != RUNNING){
                return false;
            }
            timer.stop();
            JOptionPane.showMessageDialog(this,"You win,once more?");
            break;
        case LOSE:
            if(gamestate != RUNNING){
                return false;
            }
            timer.stop();
            JOptionPane.showMessageDialog(this,"Time out,try again!");
            break;
        default:
            gamestate = new_state;
            return true;
        }
        init_game();
        this.repaint();
        return true;
    }
    
    static public void main(String[] args){
        SwingConsole.run(new Maze(40,30));
    }
}