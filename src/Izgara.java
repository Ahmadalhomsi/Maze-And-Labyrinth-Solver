/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author pooqw
 */
class Izgara{	//MAP CLASS

    private int cells;
    private int delay;
    private int startx;
    private int starty;
    private int finishx;
    private int finishy;
    private int tool;
    private int checks;
    private int length;
    private int curAlg;
    private int WIDTH0;
    private int CSIZEs;
    private boolean solving;
    Node[][] map;

    public Izgara(int cells, int delay, int startx, int starty, int finishx, int finishy, int tool, int checks, int length, int CSIZEs, boolean solving, Node[][] map) {
        this.cells = cells;
        this.delay = delay;
        this.startx = startx;
        this.starty = starty;
        this.finishx = finishx;
        this.finishy = finishy;
        this.tool = tool;
        this.checks = checks;
        this.length = length;
        this.curAlg = curAlg;
        this.WIDTH0 = WIDTH0;
        this.CSIZEs = CSIZEs;
        this.solving = solving;
        this.map = map;

        //PathFinding.CCC++;
    }

    public StartAndEndPos Waller(int problemIndex, String link) { // Walling from text file

        /*
            int[][] maze
                 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // 13 // 13
                    {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                    {1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1},
                    {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
                    {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1},
                    {1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}

                    };
         */
 /*
            int[][] maze
                 = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // 13 // 13
                    {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                    {1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1},
                    {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
                    {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
                    {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}

                    };
         */
 /*
            int[][] maze={
                                        {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					{0,1,0,0,1,1,0,0,0,0,0,1,1,1,0,0,0,1,1,1,1,0,0,0},
					{0,1,0,0,1,0,0,0,0,1,1,1,0,1,0,0,1,1,0,0,1,0,0,0},
					{0,1,1,0,1,1,1,1,0,0,0,0,0,1,1,1,1,0,0,0,1,0,0,0},
					{0,0,1,0,0,0,0,1,1,1,1,0,0,1,0,1,0,0,0,1,1,1,0,0},
					{0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,1,0,0,1,1,0,1,0,0},
					{0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0},
					{0,1,1,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,1,1,1,0,0,0},
					{0,1,0,0,0,1,1,1,1,1,0,0,1,1,1,1,0,0,0,0,1,1,1,0},
					{0,1,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0},
					{0,0,1,1,1,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,1,0},
					{0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,1,0,1,0,0,1,0,0,0},
					{0,1,1,0,0,1,0,0,0,0,0,0,0,0,0,1,0,1,1,1,1,1,0,0},
					{0,0,1,0,0,1,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,1,0,0},
					{0,0,1,1,1,1,0,0,0,1,1,1,0,1,0,0,0,1,1,1,0,1,1,0},
					{0,0,1,0,0,1,1,1,1,1,0,0,1,1,0,0,0,1,0,1,0,0,1,0},
					{0,0,1,1,0,1,0,0,0,0,0,1,1,0,0,0,1,1,0,1,1,1,1,0},
					{0,0,0,1,0,1,0,0,1,1,1,1,0,0,0,0,1,0,0,0,0,0,0,0},
					{0,0,0,1,0,1,0,0,0,0,0,1,1,1,0,0,1,1,0,0,0,0,0,0},
					{0,1,1,1,0,1,0,0,0,1,1,0,0,0,1,0,0,1,1,1,1,1,0,0},
					{0,1,0,0,0,0,0,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0,0},
					{0,1,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0},
					{0,1,1,1,0,1,0,0,1,0,1,1,1,0,0,0,1,1,1,1,0,0,0,0},
					{0,0,0,0,0,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,1,0,0}};
         */
        int maze[][] = null;
        if (problemIndex == 0) {
            URLTextReader Utr = new URLTextReader();
            maze = Utr.Reader(link);
            

            if (maze == null) {
                System.out.println("***** URLTextReader Error *****");
            }

        } else {
            Labyrenther g = new Labyrenther();
            g.initializeGrid(true, cells, cells);
            maze = g.getGrid();
        }

        System.out.println("i: " + maze.length + " j: " + maze[0].length);

        tool = 2;
        StartAndEndPos SAEP = new StartAndEndPos();
        


        for (int x = 0; x < maze[0].length; x++) {
            for (int y = 0; y < maze.length; y++) {
                
                Random rd = new Random(); // creating Random object
                //System.out.println(rd.nextBoolean()); // displaying a random boolean
                boolean random[] = new boolean[9];
                
                for (int i = 0; i < 9; i++) {
                    random[i] = rd.nextBoolean();      
                }
                
                // Empty Blocker
                
                    random[0]=false;
                    random[2]=false;
                    random[7]=false;
                    random[8]=false;
                
                
                // CHAPRAZ 2 Checkers
                if( (!random[0]) && (!random[3]))
                {
                    random[1]=false;
                }
                
                if( (!random[1]) && (!random[2]))
                {
                    random[0]=false;
                }
                // Chapraz 2 checkers ending
                
                /*
                  2  2
                  2  2
                
                
                 3  3  3
                 3  3  3
                 3  3  3
                
                */
                
                /*
                for (int i = 0; i < 6; i++) {
                    
                    if(random[i] && (random[i+1] || random[i+3]))
                    {
                        
                    }
                    else
                    {
                        random[i] = true;
                        random[i+1]=true;
                    }
                    
                }
                */

                
                if (problemIndex == 0) {
                    
                    /*
                    System.out.println("PROBLEM 0 (1)" + maze[x][y]);
                    System.out.println("map: " + map[x][y].getType());
                    */
                    
                    /// 1 2 3 engel
                    
                    if ((maze[x][y] == 1) && (map[x][y].getType()==3)) {
                        map[x][y] = new Engel(2, x, y, finishx, finishy);
                        Engel current = (Engel) map[x][y];
                        
                    }
                    if ((maze[x][y] == 2) && (map[x][y].getType()==3) && (map[x][y].getHidedType() != 7)) {
                        
                        int count=0;
                        for (int i = 0; i < 2; i++) {
                            for (int j = 0; j < 2; j++) {
                                
                                if(random[count])
                                {
                                    map[x+i][y+j].setHidedType(7);
                                    
                                }
                                else
                                {
                                    map[x+i][y+j] = new Engel(6, x, y, finishx, finishy);
                                    Node current = (Node) map[x+i][y+j];
                                }
                                
                                
                                count++;
                            }
                            
                        }
                        
                            /* CAPRAZ CHECKING
                                if((map[x][y].getType()== 6) && (map[x+1][y+1].getType() == 6) && ((map[x+1][y].getType() != 6) || (map[x][y+1].getType() != 6)))
                                {
                                    map[x+1][y] = new Engel(6, x+1, y, finishx, finishy);
                                    Engel current = (Engel) map[x+1][y];
                                }
                                
                                if((map[x][y+1].getType()== 6) && (map[x+1][y].getType() == 6) && ((map[x][y].getType() != 6) || (map[x+1][y+1].getType() != 6)))
                                {
                                    map[x][y] = new Engel(6, x, y, finishx, finishy);
                                    Engel current = (Engel) map[x][y];
                                }
                                
                            */
                        
                        
                        
                    }
                    if ((maze[x][y] == 3) && (map[x][y].getType()==3) && (map[x][y].getHidedType() != 9) ) {
                        int count=0;
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                
                                if(random[count])
                                {
                                    map[x+i][y+j].setHidedType(9);
                                    
                                }
                                else
                                {
                                    map[x+i][y+j] = new Engel(8, x, y, finishx, finishy);
                                    Node current = (Node) map[x+i][y+j];
                                }
                                
                                
                                count++;
                            }
                            
                        }
                       
                        
                    }
                }
                // Problem 2
                else{
                    if (maze[x][y] == 1) {
                        map[x][y] = new Engel(2, x, y, finishx, finishy);
                        //System.out.println("C: " + map[x][y].getType());
                        Engel current = (Engel) map[x][y];
                        //System.out.println("E: " + current.getType());
                        if (current.getType() != 0 && current.getType() != 1) {
                            current.setType(tool);
                        }
                    }
                    if (maze[x][y] == 2) {
                        SAEP.setStartX(x);
                        SAEP.setStartY(y);

                    }
                    if (maze[x][y] == 3) {
                        SAEP.setEndX(x);
                        SAEP.setEndY(y);
                    }
                }

            }
        }
        SAEP.setMap(map);
        SAEP.setMaze(maze);
        return SAEP;
    }
    
    

}

class StartAndEndPos {

    private int StartX;
    private int StartY;
    private int EndX;
    private int EndY;
    private Node map[][];
    private int maze[][];

    public int[][] getMaze() {
        return maze;
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }
    
    
    public Node[][] getMap() {
        return map;
    }

    public void setMap(Node[][] map) {
        this.map = map;
    }

    public void setStartX(int StartX) {
        this.StartX = StartX;
    }

    public void setStartY(int StartY) {
        this.StartY = StartY;
    }

    public void setEndX(int EndX) {
        this.EndX = EndX;
    }

    public void setEndY(int EndY) {
        this.EndY = EndY;
    }

    public int getStartX() {
        return StartX;
    }

    public int getStartY() {
        return StartY;
    }

    public int getEndX() {
        return EndX;
    }

    public int getEndY() {
        return EndY;
    }

}
