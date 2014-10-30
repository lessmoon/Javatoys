package lessmoon.maze.util;

import lessmoon.maze.algorithm.*;

/**
 *	2D Vector
 * @author lessmoon
 *
 */
public class _2DVector implements Comparable<_2DVector>{
	public int X = 0;
	public int Y = 0;
	
	public _2DVector(int X,int Y){
		this.X = X;
		this.Y = Y;
	}
	

	/**
	 * Generate a unit Vector by dir
	 * @param dir
	 */
	public _2DVector(MazeDirection dir){
		this.X = 0;
		this.Y = 0;
		switch(dir){
			case NORTH:
				this.Y = -1;
				break;
			case SOUTH:
				this.Y = 1;
				break;	
			case EAST:
				this.X = 1;
				break;
			case WEST:
				this.X = -1;
				break;
			default://HERE
				break;
		}
	}

	public _2DVector(_2DVector V){
		this.X = V.X;
		this.Y = V.Y;
	}
	
	/**
	 * Find if it Equals V
	 * @param V
	 * @return
	 */
	public boolean Equals(_2DVector V){
		return (this.X == V.X)
				&&(this.Y == V.Y);
	}
 
	public boolean Equals(int X,int Y){
		return (this.X == X)
				&&(this.Y == Y);
	}
 

	/**
	 * Find if it is different to V
	 * @param V
	 * @return
	 */
	public boolean IsDifferent(_2DVector V){
		return (this.Equals(V) == false);
	}
	
	public _2DVector Add(_2DVector V){
		this.X += V.X;
		this.Y += V.Y;
		return this;
	}

	public _2DVector Take(_2DVector V){
		this.Y -= V.Y;
		this.X -= V.X;
		return this;
	}

	public _2DVector Move(MazeDirection dir){
		switch(dir){
			case NORTH:
				this.Y -= 1;
				break;
			case SOUTH:
				this.Y += 1;
				break;	
			case EAST:
				this.X += 1;
				break;
			case WEST:
				this.X -= 1;
				break;
			default://HERE
				break;
		}
		return this;
	}

	/**
	 * Return the sum of two vectors
	 * @param V
	 * @return
	 */
	public _2DVector Plus(_2DVector V){
		return new _2DVector(this.X + V.X,this.Y + V.Y);
	}

	public _2DVector Plus(MazeDirection Dir){
		int X = this.X,
			Y = this.Y;
		switch(Dir){
			case NORTH:
				Y -= 1;
				break;
			case SOUTH:
				Y += 1;
				break;	
			case EAST:
				X += 1;
				break;
			case WEST:
				X -= 1;
				break;
			default://HERE
				break;
		}
		return new _2DVector(X,Y);
	}

	/**
	 * Return the Minus of two vectors
	 * @param V
	 * @return
	 */
	public _2DVector Minus(_2DVector V){
		return new _2DVector(this.X - V.X,this.Y - V.Y);
	}
	
	/**
	 * Return the innerproduct of two vectors
	 * @param V
	 * @return
	 */
	int Mult(_2DVector V){
		return (this.X * V.X + this.Y * V.Y);
	}

	@Override
	public String toString() {
		return "[" + this.X + "," + this.Y + "]";
	}

	/**
	 * Compare to another Vector
	 */
   @Override
   public int compareTo(_2DVector V) {  
        if(this.X != V.X||this.Y == V.Y)
            return V.X - this.X;
        else return V.Y - this.Y;
   }  

}
