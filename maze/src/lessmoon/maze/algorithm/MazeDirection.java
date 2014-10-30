package lessmoon.maze.algorithm;

/*
 * @author lessmoon
 *	Five directions for Map,Specially HERE is just for some special use
 */
public enum MazeDirection {
	NORTH,SOUTH,EAST,WEST,HERE;
    public MazeDirection opposite(){
        switch(this){
        case NORTH:
            return SOUTH;
        case SOUTH:
            return NORTH;
        case EAST:
            return WEST;
        case WEST:
            return EAST;
        default:
            return HERE;
        }
    }
}