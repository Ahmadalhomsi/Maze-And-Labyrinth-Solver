/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pooqw
 */
class Node {
    
    /*
      0 = start, 1 = finish, 2 = wall, 3 = empty, 4 = checked, 5 = Engel,
      6 = Engel 2 , 7 = Engel 2 Hided, 8 = Engel 3, 9 = Engel 3 Hided  
    */
    
        private int cellType;
        private int hops;
        private int x;
        private int y;
        private int lastX;
        private int lastY;
        private int finishx;
        private int finishy;
        private int HidedType;

        public Node (int type, int x, int y,int finishx , int finishy) {	//CONSTRUCTOR
            this.cellType = type;
            this.x = x;
            this.y = y;
            hops = -1;
            this.finishx = finishx;
            this.finishy = finishy;
        }

    public int getHidedType() {
        return HidedType;
    }

    public void setHidedType(int HidedType) {
        this.HidedType = HidedType;
    }


        public int getX() {
            return x;
        }		//GET METHODS

        public int getY() {
            return y;
        }

        public int getLastX() {
            return lastX;
        }

        public int getLastY() {
            return lastY;
        }

        public int getType() {
            return cellType;
        }

        public int getHops() {
            return hops;
        }

        public void setType(int type) {
            cellType = type;
        }		//SET METHODS

        public void setLastNode(int x, int y) {
            lastX = x;
            lastY = y;
        }

        public void setHops(int hops) {
            this.hops = hops;
        }
}
