
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pooqw
 */
public class Robot2 {
    //PathFinding pathfinding = new PathFinding();  // it can't be: PathFinding pathfinding = new PathFinding() BECAUSE IT MAKES A LOOP 
    
    
    
        //PathFinding pathfinding = new PathFinding();  // it can't be: PathFinding pathfinding = new PathFinding() BECAUSE IT MAKES A LOOP 
    private int cells;
    private int checks;
    private int length;
;

    private boolean solving;
    Node[][] map;

    public Robot2(int cells,int checks, int length, int CSIZE, boolean solving, Node[][] map) {
        this.cells = cells;
        this.checks = checks;
        this.length = length;
        this.solving = solving;
        this.map = map;

        //PathFinding.CCC++;
    }

    
    
    private boolean pravista=false;

        public RETURNER2 exploreNeighbors(Node current, int hops) {	//EXPLORE NEIGHBORS
            ArrayList<Node> explored = new ArrayList<Node>();	//LIST OF NODES THAT HAVE BEEN EXPLORED
            
            outerloop:
            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                    if((a==1 && b==0) || (a==0 && b==1) || (a==-1 && b==0) || (a==0 && b==-1))
                    {
                       int xbound = current.getX() + a;
                       int ybound = current.getY() + b;
                    
                    if ((xbound > -1 && xbound < cells) && (ybound > -1 && ybound < cells)) {	//MAKES SURE THE NODE IS NOT OUTSIDE THE GRID
                        Node neighbor = map[xbound][ybound];
                        
                        
                        
                        
                        if ((neighbor.getType() != 2) && (neighbor.getType() != 55) && (neighbor.getType() != 3) && (neighbor.getType() != 6) && (neighbor.getType() != 8)) {	//CHECKS IF THE NODE IS NOT A WALL AND THAT IT HAS NOT BEEN EXPLORED
                            explore(neighbor, current.getX(), current.getY(), hops);	//EXPLORE THE NODE
                            explored.add(neighbor);	//ADD THE NODE TO THE LIST
                            System.out.println("POSXX22: " + xbound + " " + ybound);
                            System.out.println("type: " + neighbor.getType());
                            System.out.println("hops: " + neighbor.getHops());
                            
                            if(!solving)
                                break outerloop;
                        }
                        // continue by else with a++ or b++
                    } 
                    }
                    
                    
                }
            }
            
            System.out.println("******");
            
            RETURNER2 returner = new RETURNER2(explored, length, solving, checks);
            return returner;
        }

        public void explore(Node current, int lastx, int lasty, int hops) {	//EXPLORE A NODE
            if (current.getType() != 0 && current.getType() != 1) //CHECK THAT THE NODE IS NOT THE START OR FINISH
            {
                current.setType(4);	//SET IT TO EXPLORED
            }
            if (current.getType() == 4) //CHECK THAT THE NODE IS NOT THE START OR FINISH
            {
                current.setType(55);	//SET IT TO EXPLORED
            }
            
            
            
            current.setLastNode(lastx, lasty);	//KEEP TRACK OF THE NODE THAT THIS NODE IS EXPLORED FROM
            current.setHops(hops);	//SET THE HOPS FROM THE START
            
            System.out.println(checks);
            
            if (current.getType() == 1) {	//IF THE NODE IS THE FINISH THEN BACKTRACK TO GET THE PATH
                backtrack(current.getLastX(), current.getLastY(), hops);
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // by Ahmadista
                solving = false;
                pravista=true;
                return;
                
            }
        }

        public void backtrack(int lx, int ly, int hops) {	//BACKTRACK
            
            length = hops++;
            while (hops > 2) {	//BACKTRACK FROM THE END OF THE PATH TO THE START
                Node current = map[lx][ly];
                current.setType(5);
                lx = current.getLastX();
                ly = current.getLastY();
                hops--;
            }
            length-=1; // CHANGING THE LAST LENGTH
            solving = false;
        }
}

class RETURNER2
{
    private ArrayList<Node> explored;
    private int length;
    private boolean solving;
    private int checks;

    public RETURNER2(ArrayList<Node> explored, int length, boolean solving, int checks) {
        this.explored = explored;
        this.length = length;
        this.solving = solving;
        this.checks = checks;
    }

    public ArrayList<Node> getExplored() {
        return explored;
    }

    public int getLength() {
        return length;
    }

    public boolean getSolving() {
        return solving;
    }

    public int getChecks() {
        return checks;
    }
    
    
}
