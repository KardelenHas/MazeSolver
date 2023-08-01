package maze;

import java.util.Stack;

public class Solver_Ordinal {
	/**
	 * Solves the maze using a stack as an auxiliary data structures. <br>
	 * The solution is obtained by visiting every neighbor of the current top and looking for a path that contains 
	 * this neighbor. Any point none of whose neighbors yield a solution is popped from the stack. <br>
	 * NOTE-1: One solution for the input file "MazeConf1.txt": [[5,0], [5,1], [4,1], [3,1], [3,2], [3,3], [2,3], [1,3], [1,4], [1,5], [1,6], [1,7], [1,8]].
	 * NOTE-2: Notice that there might exist multiple solutions for an input. Any correct answer will be accepted. 
	 * NOTE-3: Use only <code>push</code>, <code>pop</code>, <code>peek</code> and <code>isEmpty</code> on <code>history</code>.
	 * NOTE-4: You may edit ONLY Solver_Ordinal.solve. Do not edit the other classes/methods.
	 * @param maze Maze to be solved
	 * @return The path from the entry point to the exit point (exit on the top, entry at the bottom)
	 */
	public static Stack<Point2D> solve(Maze maze) {
		//history of points that mark our path from start to the current position
		Stack<Point2D> history = new Stack<>();

		Point2D point = maze.getEntry();
		Point2D next = maze.getEntry();
		
		do {
			Point2D up = new Point2D(point.getY()-1,point.getX());
			Point2D right = new Point2D(point.getY(),point.getX()+1);
			Point2D down = new Point2D(point.getY()+1,point.getX());
			Point2D left = new Point2D(point.getY(),point.getX()-1);
			
			if(!point.isNeighborVisited(Point2D.Direction.ABOVE) && !maze.isWall(up)) {
				point.markNeighborVisited(Point2D.Direction.ABOVE);
				next=up;
				next.markNeighborVisited(Point2D.Direction.BELOW);
				history.push(point);
				point=next;
			}
			else if(!point.isNeighborVisited(Point2D.Direction.RIGHT) && !maze.isWall(right)) {
				point.markNeighborVisited(Point2D.Direction.RIGHT);
				next=right;
				next.markNeighborVisited(Point2D.Direction.LEFT);
				history.push(point);
				point=next;
			}
			else if(!point.isNeighborVisited(Point2D.Direction.BELOW) && !maze.isWall(down)) {
				point.markNeighborVisited(Point2D.Direction.BELOW);
				next=down;
				next.markNeighborVisited(Point2D.Direction.ABOVE);
				history.push(point);
				point=next;
			}
			else if(!point.isNeighborVisited(Point2D.Direction.LEFT) && !maze.isWall(left)) {
				point.markNeighborVisited(Point2D.Direction.LEFT);
				next=left;
				next.markNeighborVisited(Point2D.Direction.RIGHT);
				history.push(point);
				point=next;
			}
			else {
				point=history.pop();
			}
		} while(!(point.equals(maze.getExit())));
		
		history.push(maze.getExit());
		
		return history;
	}
	
	
	public static void main(String[] args) throws Exception{
		//read the configuration file
		Maze maze = new Maze("mazeConf1.txt");
		//output the configuration version of the maze
		System.out.println(maze.toString());
		
		//solve the maze
		Stack<Point2D> solution = solve(maze);
		//output the solution
		System.out.println(solution + "\n");
		
		//output the maze such that every visited point of the solution
		//   is marked with an "X".
		String output = "";
		for(int i = 0; i < maze.getNumRows(); i++) {
			String line = "Row " + i + ":\t";
			for(int j = 0; j < maze.getNumCols(); j++) {
				Point2D curr = new Point2D(i, j);
				if(maze.isWall(curr)) {
					line = line + "1";
				} else if(solution.contains(curr)) {
					line = line + "X";
				} else {
					line = line + "0";
				}
			}
			output = output + line + "\n";
		}
		System.out.println(output);
	}
}
