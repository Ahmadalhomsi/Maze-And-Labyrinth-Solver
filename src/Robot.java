
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author pooqw
 */
class Robot {	//ALGORITHM CLASS

    //PathFinding pathfinding = new PathFinding();  // it can't be: PathFinding pathfinding = new PathFinding() BECAUSE IT MAKES A LOOP 
    private int cells;

    private int checks;
    private int length;
;

    private boolean solving;
    Node[][] map;

    public Robot(int cells,int checks, int length, int CSIZE, boolean solving, Node[][] map) {
        this.cells = cells;
        this.checks = checks;
        this.length = length;
        this.solving = solving;
        this.map = map;

        //PathFinding.CCC++;
    }

    public Robot() {
    }

    private boolean pravista = false;

    public RETURNER exploreNeighbors(Node current, int hops, int a, int b, ArrayList<Node> explored2) {	//EXPLORE NEIGHBORS
        ArrayList<Node> explored = new ArrayList<Node>();	//LIST OF NODES THAT HAVE BEEN EXPLORED
        ArrayList<Node> toBeExplored = new ArrayList<Node>();

        boolean Ocheck = false;
        boolean Icheck = false;

        int xbound = current.getX() + a;
        int ybound = current.getY() + b;

        System.out.println("\nOFUNC");
        if ((xbound > -1 && xbound < cells) && (ybound > -1 && ybound < cells)) {	//MAKES SURE THE NODE IS NOT OUTSIDE THE GRID
            System.out.println("FUNC");
            Ocheck = true;
            Node neighbor = map[xbound][ybound];

            

            /*
                        if(explored2.contains(neighbor) && (neighbor.getType() == 4) && hops == 26)
                           {
                               Icheck = true;
                               System.out.println("GGGGGGGG");
                               Icheck = true;
                            explore(neighbor, current.getX(), current.getY(), hops);	//EXPLORE THE NODE
                            explored.add(neighbor);	//ADD THE NODE TO THE LIST
                               
                           }
             */
            for (int k = -1; k <= 1; k++) {
                for (int s = -1; s <= 1; s++) {
                    if ((k == 1 && s == 0) || (k == 0 && s == 1) || (k == -1 && s == 0) || (k == 0 && s == -1)) {
                        int xbound2 = current.getX() + k;
                        int ybound2 = current.getY() + s;
                        if ((xbound2 > -1 && xbound2 < cells) && (ybound2 > -1 && ybound2 < cells)) {
                            Node neighbor5 = map[xbound2][ybound2];

                            if( neighbor5.getType() == 1)
                            {

                                toBeExplored.clear();
                                explore(neighbor5, current.getX(), current.getY(), hops, false);	//EXPLORE THE NODE
                                explored.add(neighbor5);	//ADD THE NODE TO THE LIST
                                solving= false;
                                RETURNER returner = new RETURNER(explored, toBeExplored, length, solving, checks, Ocheck, Icheck);
                                return returner;
                                
                            }
                        }
                    }

                }
            }
            
            
            
            System.out.println("------>>>: " + neighbor.getHops());
            System.out.println("------>>>2: " + hops);
            System.out.println("------>>>3: " + neighbor.getType());
            System.out.println("POS: " + xbound + " " + ybound);
            System.out.println("type: " + neighbor.getType());

            if ((neighbor.getHops() == -1 || neighbor.getHops() > hops) && (neighbor.getType() != 2) && (neighbor.getType() != 6) && (neighbor.getType() != 8)) {	//CHECKS IF THE NODE IS NOT A WALL AND THAT IT HAS NOT BEEN EXPLORED
                Icheck = true;
                System.out.println("IFUNC");
                explore(neighbor, current.getX(), current.getY(), hops, false);	//EXPLORE THE NODE
                explored.add(neighbor);	//ADD THE NODE TO THE LIST

            }
            
            /// toBeExplored
            outerloop:
            for (int k = -1; k <= 1; k++) {
                for (int s = -1; s <= 1; s++) {
                    if ((k == 1 && s == 0) || (k == 0 && s == 1) || (k == -1 && s == 0) || (k == 0 && s == -1)) {
                        int xbound2 = current.getX() + k;
                        int ybound2 = current.getY() + s;
                        if ((xbound2 > -1 && xbound2 < cells) && (ybound2 > -1 && ybound2 < cells)) {
                            Node neighbor2 = map[xbound2][ybound2];
                            if (neighbor2.getType() == 3) {
                                System.out.println("ADDED in FUNC");
                                toBeExplored.add(neighbor2);
                            }//  neighbor2.getType() == 1
                            if( neighbor2.getType() == 1)
                            {

                                toBeExplored.clear();
                                explore(neighbor2, current.getX(), current.getY(), hops, false);	//EXPLORE THE NODE
                                explored.add(neighbor2);	//ADD THE NODE TO THE LIST
                                solving= false;
                                break outerloop;
                            }
                        }
                    }

                }
            }
            
            System.out.println("POSXX: " + xbound + " " + ybound);
            System.out.println("type: " + neighbor.getType());
            

            /*
                        if((neighbor.getHops() != -1 && neighbor.getHops() < hops) && (explored2.contains(neighbor)))
                           {
                               Icheck = true;
                               System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKLLLL");
                               
                               
                           }
             */
            // continue by else with a++ or b++
        } else {
            System.out.println("FOUNDERS");
        }
        
        System.out.println("******");

        RETURNER returner = new RETURNER(explored, toBeExplored, length, solving, checks, Ocheck, Icheck);
        return returner;
    }

    public void explore(Node current, int lastx, int lasty, int hops, boolean fakeTrack) {	//EXPLORE A NODE
        if (current.getType() != 0 && current.getType() != 1) //CHECK THAT THE NODE IS NOT THE START OR FINISH
        {
            current.setType(4);	//SET IT TO EXPLORED
        }
        current.setLastNode(lastx, lasty);	//KEEP TRACK OF THE NODE THAT THIS NODE IS EXPLORED FROM
        current.setHops(hops);	//SET THE HOPS FROM THE START

        if (!fakeTrack) {
            checks++;
        } else {

        }

        System.out.println(checks);

        if (current.getType() == 1) {	//IF THE NODE IS THE FINISH THEN BACKTRACK TO GET THE PATH
            backtrack(current.getLastX(), current.getLastY(), hops);
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // by Ahmadista
            pravista = true;

        }
    }

    public void backtrack(int lx, int ly, int hops) {	//BACKTRACK

        length = hops;
        while (hops > 1) {	//BACKTRACK FROM THE END OF THE PATH TO THE START
            Node current = map[lx][ly];
            //current.setType(5);
            lx = current.getLastX();
            ly = current.getLastY();
            hops--;
        }
        solving = false;
    }
}

class RETURNER {

    private ArrayList<Node> explored;
    private ArrayList<Node> toBeExplored;
    private int length;
    private boolean solving;
    private int checks;
    private boolean Ocheck;
    private boolean Icheck;

    public RETURNER(ArrayList<Node> explored, ArrayList<Node> toBeExplored, int length, boolean solving, int checks, boolean Ocheck, boolean Icheck) {
        this.explored = explored;
        this.toBeExplored = toBeExplored;
        this.length = length;
        this.solving = solving;
        this.checks = checks;
        this.Ocheck = Ocheck;
        this.Icheck = Icheck;
    }

    public RETURNER() {

    }

    public boolean getOcheck() {
        return Ocheck;
    }

    public boolean getIcheck() {
        return Icheck;
    }

    public ArrayList<Node> getToBeExplored() {
        return toBeExplored;
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
