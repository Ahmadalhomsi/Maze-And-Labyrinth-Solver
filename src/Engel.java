/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pooqw
 */
public class Engel extends Node {
    
    /*
      0 = start, 1 = finish, 2 = wall, 3 = empty, 4 = checked, 5 = finalpath,
      6 = Engel 2 , 7 = Engel 2 Hided, 8 = Engel 3, 8 = Engel 3 Hided  
    */
    
        private int cellType;
        private int hops;
        private int x;
        private int y;
        private int lastX;
        private int lastY;
        private int finishx;
        private int finishy;

        public Engel (int type, int x, int y,int finishx , int finishy) {	//CONSTRUCTOR
            super(type, x, y, finishx, finishy);
            this.cellType = type;
        }

    @Override
    public void setHidedType(int HidedType) {
        super.setHidedType(HidedType); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public int getHidedType() {
        return super.getHidedType(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
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

        @Override
        public int getType() {
            return cellType;
        }

        public int getHops() {
            return hops;
        }

        @Override
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
