import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Sarah Lum 11/25/19 choose to solve out of the four different mazes using BFS
 * or DFS
 */
public class Main {
	/**
	 * Main method opens and reads in maze file, puts maze in a 2d char array
	 * displays the menu
	 */
	public static void main(String[] args) {
		boolean isInput = true;

		char[][] maze = null;
		while (isInput) {
			System.out.println("Enter a number corresponding to the function: ");
			System.out.println("1. Maze 1 \n2. Maze 2 \n3. Maze 3" + "\n4. maze 4 \n5. Quit");
			boolean isTrue = true;
			int whichOp = CheckInput.getIntRange(1, 5);
			if (whichOp != 5) {
				maze = readMaze(whichOp);
				displayArray(maze);
			}

			else {
				System.out.print("Goodbye");
				isInput = false;
				isTrue = false;
			}

			while (isTrue) {
				System.out.print("1. Depth First Search" + "\n2. Breadth First Search");
				int dOrb = CheckInput.getIntRange(1, 2);
				if (dOrb == 1) {
					stackSolver(maze);
					displayArray(maze);
				} else {
					queueSolver(maze);
					displayArray(maze);
				}
				isTrue = false;
			}
		}
	}

	/**
	 * reads in the file of the maze
	 * 
	 * @param mazeNum
	 *            - the maze number being added in
	 * @return maze - the 2d char array
	 */
	public static char[][] readMaze(int mazeNum) {
		String theChar = "";
		int breadth = 0;
		int depth = 0;
		try {
			Scanner read = new Scanner(new File("maze" + mazeNum + ".txt")); //
			int counter = 0;

			while (read.hasNext()) {
				if (counter == 0) {
					String[] dimensions = read.nextLine().split(" ");
					breadth = Integer.parseInt(dimensions[0]);
					depth = Integer.parseInt(dimensions[1]);
				} else {
					theChar = theChar + "\n" + read.nextLine();
				}
				counter++;
			}
			read.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found ");
		}

		// construct the array
		// read in the first line, find the numbers and construct 2d array from those
		// numbers

		String text = theChar.replace("\n", "").replace("\r", "");
		char[] tempArr = text.toCharArray();

		// get rid of the new lines
		char[][] maze = new char[breadth][depth];

		int pop_count = 0;

		for (int i = 0; i < breadth; i++) {
			for (int j = 0; j < depth; j++) {
				if (pop_count < tempArr.length) {
					maze[i][j] = tempArr[pop_count];
					pop_count += 1;
				}
			}
		}
		System.out.println();
		return maze;
	}

	/**
	 * reads in the file of the maze
	 * 
	 * @param displayedArray
	 *            - array meant to be displayed
	 */
	public static void displayArray(char[][] displayedArray) {

		for (int n = 0; n < displayedArray.length; n++) {
			for (int m = 0; m < displayedArray[n].length; m++) {
				if (m == displayedArray[n].length - 1) {
					System.out.print(displayedArray[n][m] + "\n");

				} else {
					System.out.print(displayedArray[n][m]);
				}
			}

		}
	}

	// find start
	/**
	 * traverse grid until you reach 's' then create a new object point with that
	 * row/column for x and y
	 * 
	 * @param maze
	 *            - the 2d char array
	 * @return s - the Starting point found
	 */
	public static Point findStart(char[][] maze) {
		Point s = new Point();
		for (int n = 0; n < maze.length; n++) {
			for (int m = 0; m < maze[n].length; m++) {
				if (maze[n][m] == 's') {
					s = new Point(n, m);
				}
			}
		}
		return s;
	}

	/**
	 * Using the DFS with stacks, find the most efficient path that leads to f
	 * 
	 * @param maze
	 *            - the 2d char array
	 */
	public static void stackSolver(char[][] maze) {
		Stack<Point> stacko = new Stack<Point>();
		Point start = findStart(maze);
		stacko.push(start);

		while (!stacko.isEmpty()) {
			Point rem = stacko.pop();
			if (maze[rem.x][rem.y] == 'f') {
				break;
			}

			// up
			if ((rem.x - 1 >= 0 && rem.x - 1 < maze.length)
					&& (maze[rem.x - 1][rem.y] != '*' && maze[rem.x - 1][rem.y] != '.')) {
				maze[rem.x][rem.y] = '.';
				Point nrem = new Point(rem.x - 1, rem.y);
				stacko.push(nrem);

			}

			// down
			if ((rem.x + 1 >= 0 && rem.x + 1 < maze.length)
					&& (maze[rem.x + 1][rem.y] != '*' && maze[rem.x + 1][rem.y] != '.')) {
				maze[rem.x][rem.y] = '.';
				Point nrem = new Point(rem.x + 1, rem.y);
				stacko.push(nrem);
			}

			// right
			if ((rem.y + 1 >= 0 && rem.y + 1 < maze[rem.x].length)
					&& (maze[rem.x][rem.y + 1] != '*' && maze[rem.x][rem.y + 1] != '.')) {
				maze[rem.x][rem.y] = '.';
				Point nrem = new Point(rem.x, rem.y + 1);
				stacko.push(nrem);
			}

			// left
			if ((rem.y - 1 >= 0 && rem.y - 1 < maze[rem.x].length)
					&& (maze[rem.x][rem.y - 1] != '*' && maze[rem.x][rem.y - 1] != '.')) {
				maze[rem.x][rem.y] = '.';
				Point nrem = new Point(rem.x, rem.y - 1);
				stacko.push(nrem);
			}

		}

	}

	/**
	 * Using the BFS with stacks, track the path that leads to f
	 * 
	 * @param maze
	 *            - the 2d char array
	 */
	public static void queueSolver(char[][] maze) {
		Queue<Point> queues = new LinkedList<Point>();
		Point start = findStart(maze);
		queues.add(start);

		while (!queues.isEmpty()) {
			Point rem = queues.remove();
			if (maze[rem.x][rem.y] == 'f') {
				break;
			}

			// up
			if ((rem.x - 1 >= 0 && rem.x - 1 < maze.length)
					&& (maze[rem.x - 1][rem.y] != '*' && maze[rem.x - 1][rem.y] != '.')) {

				maze[rem.x][rem.y] = '.';
				Point nrem = new Point(rem.x - 1, rem.y);
				queues.add(nrem);

			}

			// down
			if ((rem.x + 1 >= 0 && rem.x + 1 < maze.length)

					&& (maze[rem.x + 1][rem.y] != '*' && maze[rem.x + 1][rem.y] != '.')) {

				maze[rem.x][rem.y] = '.';
				Point nrem = new Point(rem.x + 1, rem.y);
				queues.add(nrem);
			}

			// right
			if ((rem.y + 1 >= 0 && rem.y + 1 < maze[rem.x].length)
					&& (maze[rem.x][rem.y + 1] != '*' && maze[rem.x][rem.y + 1] != '.')) {
				maze[rem.x][rem.y] = '.';
				Point nrem = new Point(rem.x, rem.y + 1);
				queues.add(nrem);
			}

			// left
			if ((rem.y - 1 >= 0 && rem.y - 1 < maze[rem.x].length)
					&& (maze[rem.x][rem.y - 1] != '*' && maze[rem.x][rem.y - 1] != '.')) {
				maze[rem.x][rem.y] = '.';
				Point nrem = new Point(rem.x, rem.y - 1);
				queues.add(nrem);
			}
		}
	}
}
