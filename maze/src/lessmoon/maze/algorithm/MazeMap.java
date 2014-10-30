package lessmoon.maze.algorithm;

import lessmoon.maze.util.*;

import java.util.TreeSet;
import java.util.Vector;


/**
 * 
 * @author lessmoon
 *
 *	A class for generating and storing maze info 
 */
public class MazeMap
{
	public MazeMap(){
	}
	
	/*
	 * @param h the height of this maze
	 * @param w the width of thie maze
	 * To change the maze's size,and it will ask for a new memory to store info
	 */
	public void ChangeSize(int w,int h){
		if(h != H
			|| w != W){
			H = h;
			W = w;
			Map = new Node[w][h];
			for(int i = 0; i < w;i++){
				for(int j = 0; j < h;j++){
					Map[i][j] = new Node();
				}
			}
		}
	}

	/**
	 * @return the height of this map
	 */
	public int Height(){
		return H;
	}
	
	/**
	 * @return the width of this map
	 */
	public int Width(){
		return W;
	}

	/**
	 * Get the Node at position (X,Y)
	 * @param X
	 * @param Y
	 * @return the node at position (X,Y)
	 */
	public Node At(int X,int Y){
            return Map[X][Y];
    }

	/**
	 * Find if the node at ManPos is linked(directly) to a Dir
	 * @param ManPos
	 * @param Dir
	 * @return
	 */
	public boolean IsValid(_2DVector ManPos,MazeDirection Dir){
		    switch(Dir){
				case SOUTH:
					return Map[ManPos.X][ManPos.Y].SouthIsLinked;
				case NORTH:
					return (ManPos.Y > 0
							&& Map[ManPos.X][ManPos.Y - 1].SouthIsLinked);
				case EAST:
					return Map[ManPos.X][ManPos.Y].EastIsLinked;
				case WEST:
					return (ManPos.X > 0
							&& Map[ManPos.X - 1][ManPos.Y].EastIsLinked);
				default:// HERE:
					return false;
			}
	}

	public boolean IsValid(int X,int Y,MazeDirection Dir){
	    switch(Dir){
			case SOUTH:
				return Map[X][Y].SouthIsLinked;
			case NORTH:
				return (Y > 0
						&& Map[X][Y - 1].SouthIsLinked);
			case EAST:
				return Map[X][Y].EastIsLinked;
			case WEST:
				return (X > 0
						&& Map[X - 1][Y].EastIsLinked);
			default:// HERE:
				return false;
		}
}
	/**
	 * Auotomatically generate a maze
	 * Anyway,if the size of Maze if too big (more than 100*100),it will be somewhat slow
	 */
    public void GenerateMaze(){
            C.Clear();
            for(int i = 0;i< this.W;i++)
                for(int j = 0;j < this.H;j++){
                    C.Insert(new _2DVector(i,j));
                }
			//C.Show();
            Vector<_2DVector> Con = new Vector<_2DVector>();

            //[1]
            {
				TreeSet<_2DVector> i = null;
				_2DVector j = null;
                while(C.Size() > 1){

                    i = RandomGetSet();//L1:Random select a Set

                    for(_2DVector iter : i){
                        Con.add(iter);
                    }

                    while(Con.size() > 0){
                        int index = MazeMath.RandomInt(0,Con.size());
                        j = Con.elementAt(index);//Random select a Node

                        MazeDirection NEXT = RandomNeighbour(j,i);//select one node from its neighbours

                        if(NEXT != MazeDirection.HERE){//if there is no neighbour to select,goto next LOOP
                            this.SetLinked(j,NEXT);
                            C.Union(C.FindContainerOf(j.Plus(new _2DVector(NEXT))),i);
                            break;//if a path has been connected,exit this loop
                        }
						Con.set(index,Con.lastElement());
                        Con.removeElementAt(Con.size() - 1);
                    }
                    Con.clear();
                }
            }
            //[1]
        }

    /**
     * Set a node linked to Dir
     * @param V
     * @param Dir
     */
	private void SetLinked(_2DVector V,MazeDirection Dir){
		     switch(Dir){
                case SOUTH:
                    Map[V.X][V.Y].SouthIsLinked = true;
                    break;
                case NORTH:
                    Map[V.X][V.Y - 1].SouthIsLinked = true;
                    break;
                case EAST:
                    Map[V.X][V.Y].EastIsLinked = true;
                    break;
                case WEST:
                    Map[V.X - 1][V.Y].EastIsLinked = true;
                    break;
                default:// HERE:
                    break;
            }
	}

	/**
	 * Just used for GenerateMaze()
	 * To find if V is already pathed to Dir
	 * @param V
	 * @param Dir
	 * @param C
	 * @return
	 */
	private boolean IsLinked(_2DVector V,MazeDirection Dir,TreeSet<_2DVector> C){
		return C.contains(V.Plus(Dir));
	}
	
	/**
	 * Test for this maze generate functions 
	 * If generate() is ok,it returns H*W
	 * @param i
	 * @param j
	 * @param dir
	 * @return
	 */
	public int test(int i,int j,MazeDirection dir){
		int count = 1;
		_2DVector temp = new _2DVector(i,j);
		if( dir != MazeDirection.EAST
			&IsValid(temp,MazeDirection.EAST)
			&dir != MazeDirection.EAST){
			count += test(i + 1,j,MazeDirection.WEST);
		}
		if(	dir != MazeDirection.SOUTH
			&IsValid(temp,MazeDirection.SOUTH)){
			count += test(i,j + 1,MazeDirection.NORTH);
		}
		if( j > 0
			&dir != MazeDirection.NORTH
			&IsValid(temp,MazeDirection.NORTH)){
			count += test(i,j - 1,MazeDirection.SOUTH);
		}
		if( i > 0
			&dir != MazeDirection.WEST
			&IsValid(temp,MazeDirection.WEST)){
			count += test(i - 1,j,MazeDirection.EAST);
		}
		//System.out.println(count);
		return count;
	}
	
	/**
	 * Test for map
	 * Print the map
	 */
	public void show(){
		for(int i = 0 ;i < H;i++){
			for(int j = 0 ;j < W;j++){
				System.out.print(Map[j][i]);
			}
			System.out.print("\n");
		}
	}

	/**
	 * Just used for GenerateMaze()
	 * Randomly get a Set from the map collection
	 * @return
	 */
	private TreeSet<_2DVector>RandomGetSet(){
		return C.GetCache().elementAt(MazeMath.RandomInt(0,C.Size()));
	}

	/**
	 * Just used for GenerateMaze()
	 * Randomly get a neighbour to set linked
	 * if no neighbour is valid,return HERE
	 * @param V
	 * @param C
	 * @return
	 */
	private MazeDirection RandomNeighbour(_2DVector V,
                               TreeSet<_2DVector>  C){//V is in C
		Vector<MazeDirection> Box = new Vector<MazeDirection>();
		if(V.X > 0){
			if(!IsLinked(V,MazeDirection.WEST,C)){
				Box.add(MazeDirection.WEST);
			}
		}
		if(V.Y > 0){
			if(!IsLinked(V,MazeDirection.NORTH,C)){
				Box.add(MazeDirection.NORTH);
			}
		}
		if(V.Y < this.H - 1){//HEIGHT
			if(!IsLinked(V,MazeDirection.SOUTH,C)){
				Box.add(MazeDirection.SOUTH);
			}
		}
		if(V.X < this.W - 1){//WIDTH
			if(!IsLinked(V,MazeDirection.EAST,C)){
				Box.add(MazeDirection.EAST);
			}
		}

		 if(Box.size() == 0)//Not  available
			return MazeDirection.HERE;
		 return Box.elementAt(MazeMath.RandomInt(0,Box.size()));
	}

	static public void main(String[]args){
		MazeMap m = new MazeMap();
		m.ChangeSize(80,80);
		m.GenerateMaze();
		m.show();
		System.out.println(m.test(0,0,MazeDirection.NORTH));
	}

	private int H = 0;//The Height of this maze
	private int W = 0;//The Width of this maze
	private Node[][]Map = null;//The map of this maze
	private MySet<_2DVector> C = new MySet<_2DVector>();

}