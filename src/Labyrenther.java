/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author pooqw
 */
public class Labyrenther {
    
    
        int squareSize;  // the size of the square cell in pixels
 
        Cell robotStart; // the initial position of the robot
        Cell targetPos;  // the position of the target
      
                
        int[][] grid;        // the grid
        Point[][] centers;   // the centers of the cells
        boolean searching;   // flag that the search is in progress
        boolean endOfSearch; // flag that the search came to an end

    public int[][] getGrid() {
        return grid;
    }
    
    private class Cell {
            int row;     // the row number of the cell(row 0 is the top)
            int col;     // the column number of the cell (Column 0 is the left)
            
            
            public Cell(int row, int col){
               this.row = row;
               this.col = col;
            }
            
        } // end nested class Cell
    
    /// This run first
    void initializeGrid(Boolean makeMaze,int rows, int columns) {  
            grid = new int[rows][columns];
            System.out.println(grid.length);
            
            boolean G = true;
            // the square maze must have an odd number of rows
            // the rows of the triangular maze must be at least 8 and a multiple of 4 
            if (makeMaze && (G? rows % 2 != 1 : rows % 4 != 0)){
                if (G)
                    rows--;
                else
                    rows = Math.max((rows/4)*4,8);
                
            }
            // a hexagonal grid must have an odd number of columns
            
            // the columns of the triangular maze must be rows+1
            
            // the columns of the square maze must be equal to rows
            if (makeMaze){
                columns = rows;
                
            }
            grid = new int[rows][columns];
            centers = new Point[rows][columns];
           

            //  Calculation of the edge and the height of the triangular cell
            
            
            //  Calculation of the size of the square cell
            if (makeMaze){
                squareSize = 500/(rows > columns ? rows : columns);
            }
            
            //  Calculation of the radius and the half height of the hexagonal cell
            
            
            //  Calculation of the coordinates of the cells' centers

            for (int r = 0; r < rows; r++)
                for (int c = 0; c < columns; c++){
                    
                    if (makeMaze)
                        centers[r][c] = new Point(11+c*squareSize+squareSize/2,
                                                  11+r*squareSize+squareSize/2);
                    
                }
            fillGrid(grid,robotStart,targetPos,rows,columns);
            if (makeMaze) {
                
                    MazeGenerator maze = new MazeGenerator(rows/2,columns/2);
                    for (int r = 0; r < rows; r++)
                        for (int c = 0; c < columns; c++)
                            if (maze.maze_str.substring(r*columns+c, r*columns+c+1).matches(".*[+-|].*"))
                                grid[r][c] = 1;
                    
                    System.out.println("ZZZZ");
                    for (int i = 0; i < grid.length; i++) {
                        for (int j = 0; j < grid[0].length; j++) {
                            System.out.print(grid[i][j] + " ");
                        }
                        System.out.println("");
                    }
                    
              
            }
          } // end initializeGrid()
    
    private void fillGrid(int grid [][],Cell robotStart2 ,Cell targetPos2, int rows,int columns) {
            /**
             * With the first click on button 'Clear' clears the data
             * of any search was performed (Frontier, Closed Set, Route)
             * and leaves intact the obstacles and the robot and target positions
             * in order to be able to run another algorithm
             * with the same data.
             * With the second click removes any obstacles also.
             */
            if (searching || endOfSearch){ 
                for (int r = 0; r < rows; r++)
                    for (int c = 0; c < columns; c++) {
                        if (grid[r][c] == 4 || grid[r][c] == 5 || grid[r][c] == 6)
                            grid[r][c] = 0;
                        if (grid[r][c] == 2)
                            robotStart2 = new Cell(r,c);
                        if (grid[r][c] == 3)
                            targetPos2 = new Cell(r,c);
                    }
                searching = false;
            } else {
                for (int r = 0; r < rows; r++)
                    for (int c = 0; c < columns; c++)
                        grid[r][c] = 0;
                
                    System.out.println("AA: " + rows);
                    System.out.println("AA2: " + columns);
                    robotStart2 = new Cell(1,1);
                    targetPos2 = new Cell(rows-2,columns-2);
                

            }
         
            // The first step of the other four algorithms is here
            // 1. OPEN SET: = [So], CLOSED SET: = []
      
         
            
            grid[targetPos2.row][targetPos2.col] = 3; 
            grid[robotStart2.row][robotStart2.col] = 2;
            
            
        } // end fillGrid()
    
    final static class MazeGenerator {
            private final int x;
            private final int y;
            private final int[][] maze;
            private String maze_str = "";
            
            
 
            public MazeGenerator(int x, int y) {
                this.x = x;
                this.y = y;
                maze = new int[this.x][this.y];
                generateMaze(0, 0);
                
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[0].length; j++) {
                        System.out.print(maze[i][j] + " ");
                    }
                    System.out.println("");
                }
                
                make_string();
            }
 
            public void make_string() {
                for (int i = 0; i < y; i++) {
                    // draw the north edge
                    for (int j = 0; j < x; j++)
                        maze_str = maze_str.concat((maze[j][i] & 1) == 0 ? "+-" : "+ ");
                    maze_str = maze_str.concat("+");
                    // draw the west edge
                    for (int j = 0; j < x; j++)
                        maze_str = maze_str.concat((maze[j][i] & 8) == 0 ? "| " : "  ");
                    maze_str = maze_str.concat("|");
                }
                // draw the bottom line
                for (int j = 0; j < x; j++)
                    maze_str = maze_str.concat("+-");
                maze_str = maze_str.concat("+");
                
                
            }
 
            private void generateMaze(int cx, int cy) {
                DIR[] dirs = DIR.values();
                Collections.shuffle(Arrays.asList(dirs));
                for (DIR dir : dirs) {
                    int nx = cx + dir.dx;
                    int ny = cy + dir.dy;
                    if (between(nx, x) && between(ny, y) && (maze[nx][ny] == 0)) {
                        maze[cx][cy] |= dir.bit;
                        maze[nx][ny] |= dir.opposite.bit;
                        generateMaze(nx, ny);
                    }
                }
            }
 
            private static boolean between(int v, int upper) {
                return (v >= 0) && (v < upper);
            }
 
            private static enum DIR {
                N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
                private final int bit;
                private final int dx;
                private final int dy;
                private DIR opposite;
 
                // use the static initializer to resolve forward references
                static {
                    N.opposite = S;
                    S.opposite = N;
                    E.opposite = W;
                    W.opposite = E;
                }
 
                private DIR(int bit, int dx, int dy) {
                    this.bit = bit;
                    this.dx = dx;
                    this.dy = dy;
                }
            };
        } // end nested class MazeGenerator
    
}


