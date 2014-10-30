package lessmoon.maze.algorithm;

import lessmoon.maze.util.*;

import java.util.Random;

/**
 * 
 * @author lessmoon
 *	A class for this game
 *	Includes some functions to use,and some enums
 */
public class MazeMath
{
	final static Random Ran = new Random();

	/*
	 * @return a random integer 
	 */
	static public int RandomInt(int a,int b){
		return (Ran.nextInt(b - a) + a);
	}
}

