package lessmoon.maze.algorithm;

/**
 * 
 * @author lessmoon
 *	A class for storing infomation of a position in the mazemap
 *	
 */
public class Node
{
    public boolean SouthIsLinked = false;//If some dirction is linked,that means there is no wall between the two position
    public boolean EastIsLinked = false;
	@Override
	public String toString() {
		return "[" + (this.SouthIsLinked?"V":" ") + "," + (this.EastIsLinked?">":" ") + "]";
	}
}