
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Uygulama {


    JFrame frame;
    
    //VARIABLES
    private int cells = 24;
    private int delay = 30;

    private int startx = -1;
    private int starty = -1;
    private int finishx = -1;
    private int finishy = -1;
    private int tool = 0;
    private int problemIndex = 0;
    private int steps = 0;
    private int length = 0;

    private int WIDTH = 850;
    private final int HEIGHT = 650;
    private final int MSIZE = 600;
    private int CSIZE = MSIZE / cells;
    private long startTime;
    private long stopTime;
    private String link;
    
    //UTIL ARRAYS

    private String[] problem = {"Problem 1", "Problem 2"};
    //private String[] tools = {"Start", "Finish", "Wall", "Eraser"};
    //BOOLEANS
    private boolean searching = false;
    //UTIL
    Node[][] map;
    //Robot Alg = new Robot();
    Algorithm Alg = new Algorithm();
    Random r = new Random();
    
    //SLIDERS
    JSlider speed = new JSlider(0, 500, delay);

    //LABELS
    JLabel toolL = new JLabel("Problem :");

    JLabel delayL = new JLabel("Delay:");
    JLabel msL = new JLabel(delay + "ms");
    JTextField linkL = new JTextField("URL");

    JLabel stepL = new JLabel("Steps: " + steps);
    JLabel lengthL = new JLabel("Path Length: " + length);
    JLabel TimingL = new JLabel("Elapsed Time: 0");
    
    
    //BUTTONS
    JButton searchB = new JButton("Start Search");
    JButton resetB = new JButton("Reset");
    JButton genMapB = new JButton("Generate Map");
    JButton clearMapB = new JButton("Clear Map");

    JButton LinkChangeB = new JButton("Change Link");


    JComboBox problemBx = new JComboBox(problem);
    //PANELS
    JPanel toolP = new JPanel();
    //CANVAS
    Map canvas;
    
    //BORDER
    Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    // Main
    public static void main(String[] args) throws Exception {	//MAIN METHOD

        // UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");        //   10
        //   UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");        //   11
         UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");     //  12

        new Uygulama();

    }

    public Uygulama() {	//CONSTRUCTOR
        clearMap(); // if you delete this you will see the GUI when you move the mouse
        initialize();

    }

    public Uygulama(String S) {	//CONSTRUCTOR
        System.out.println("SECOND CONST");

    }

    public void generateMap() {	//GENERATE MAP
        clearMap();	//CREATE CLEAR MAP TO START

        if (problemIndex == 0) {

            link = linkL.getText();

            Izgara izgara = new Izgara(cells, delay, startx, starty, finishx, finishy, tool, steps, length, CSIZE, searching, map);
            StartAndEndPos SAEP = izgara.Waller(problemIndex, link); // Struct Return
            cells = SAEP.getMaze().length;
            clearMap();
            map = SAEP.getMap();

            
            // Select Random Location for start and end
            int k = ThreadLocalRandom.current().nextInt(0, cells);
            int l = ThreadLocalRandom.current().nextInt(0, cells);
            int m = ThreadLocalRandom.current().nextInt(0, cells);
            int n = ThreadLocalRandom.current().nextInt(0, cells);

            if ((k + l) == (m + n)) {
                k = ThreadLocalRandom.current().nextInt(0, cells);
                l = ThreadLocalRandom.current().nextInt(0, cells);
                m = ThreadLocalRandom.current().nextInt(0, cells);
                n = ThreadLocalRandom.current().nextInt(0, cells);
            }

            canvas.StartAndEnd(k, l, m, n);

        }

        if (problemIndex == 1) {

            //
            cells = Integer.parseInt(linkL.getText());
            
            // Blocking Even Cell (Izgara) Number
            if(cells%2 == 0)
            {
                cells++;
            }

            clearMap();
            Izgara izgara = new Izgara(cells, delay, startx, starty, finishx, finishy, tool, steps, length, CSIZE, searching, map);
            StartAndEndPos SAEP = izgara.Waller(problemIndex, null);
            map = SAEP.getMap();
            canvas.StartAndEnd(SAEP.getStartX(), SAEP.getStartY(), SAEP.getEndX(), SAEP.getEndY());

        }

    }

    public void clearMap() {	//CLEAR MAP
        finishx = -1;	//RESET THE START AND FINISH
        finishy = -1;
        startx = -1;
        starty = -1;
        map = new Node[cells][cells];	//CREATE NEW MAP OF NODES
        for (int x = 0; x < cells; x++) {
            for (int y = 0; y < cells; y++) {
                map[x][y] = new Node(3, x, y, finishx, finishy);	//SET ALL NODES TO EMPTY
            }
        }
        reset();	//RESET SOME VARIABLES
    }

    public void resetMap() {	//RESET MAP
        for (int x = 0; x < cells; x++) {
            for (int y = 0; y < cells; y++) {
                Node current = map[x][y];
                if (current.getType() == 4 || current.getType() == 5 || current.getType() == 55) //CHECK TO SEE IF CURRENT NODE IS EITHER CHECKED OR FINAL PATH
                {
                    map[x][y] = new Node(3, x, y, finishx, finishy);	//RESET IT TO AN EMPTY NODE
                }
            }
        }
        if (startx > -1 && starty > -1) {	//RESET THE START AND FINISH
            map[startx][starty] = new Node(0, startx, starty, finishx, finishy);
            map[startx][starty].setHops(0);
        }
        if (finishx > -1 && finishy > -1) {
            map[finishx][finishy] = new Node(1, finishx, finishy, finishx, finishy);
        }
        reset();	//RESET SOME VARIABLES
    }

    private void initialize() {	//INITIALIZE THE GUI ELEMENTS
        frame = new JFrame();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("RobotX");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        toolP.setBorder(BorderFactory.createTitledBorder(loweredetched, "Manage"));
        int extra = 25;
        int temp = 45;

        toolP.setLayout(null);
        toolP.setBounds(10, 10, 210, 600);

        searchB.setBounds(40, extra, 120, 25);
        toolP.add(searchB);
        extra += temp;

        resetB.setBounds(40, extra, 120, 25);
        toolP.add(resetB);
        extra += temp;

        genMapB.setBounds(40, extra, 120, 25);
        toolP.add(genMapB);
        extra += temp;

        clearMapB.setBounds(40, extra, 120, 25);
        toolP.add(clearMapB);
        extra += 40;

        extra += 25;


        toolL.setBounds(40, extra, 120, 25);
        toolP.add(toolL);
        extra += 25;

        problemBx.setBounds(40, extra, 120, 25);
        toolP.add(problemBx);

        TimingL.setBounds(15, extra + 175, 180, 25);
        toolP.add(TimingL);

        linkL.setBounds(15, extra + 145, 180, 25);
        toolP.add(linkL);

        extra += temp;

        extra += temp;

        delayL.setBounds(15, extra - 50, 50, 25);
        toolP.add(delayL);
        speed.setMajorTickSpacing(5);
        speed.setBounds(50, extra - 50, 100, 25);
        toolP.add(speed);
        msL.setBounds(160, extra - 50, 40, 25);
        toolP.add(msL);
        extra += temp;

        LinkChangeB.setBounds(40, extra - 30, 120, 25);
        toolP.add(LinkChangeB);

        extra += temp;

        stepL.setBounds(15, extra + 20, 100, 25);
        toolP.add(stepL);
        extra += temp;

        lengthL.setBounds(15, extra, 100, 25);
        toolP.add(lengthL);
        extra += temp;

        frame.getContentPane().add(toolP);

        canvas = new Map();
        canvas.setBounds(230, 10, MSIZE + 1, MSIZE + 1);
        frame.getContentPane().add(canvas);

        //canvas.Waller();
        /*
        Izgara izgara = new Izgara(cells, delay, startx, starty, finishx, finishy, tool, steps, length, CSIZE, searching, map);
        izgara.Waller(problemIndex ,null);
         */
        Update();

        searchB.addActionListener(new ActionListener() {		//ACTION LISTENERS
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                if ((startx > -1 && starty > -1) && (finishx > -1 && finishy > -1)) {
                    searching = true;
                }
            }
        });
        resetB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetMap();
                Update();
            }
        });
        genMapB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateMap();
                Update();
            }
        });
        clearMapB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMap();
                Update();
            }
        });

        //LinkChangeB
        LinkChangeB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (linkL.getText().equals("http://bilgisayar.kocaeli.edu.tr/prolab2/url1.txt")) {

                    linkL.setText("http://bilgisayar.kocaeli.edu.tr/prolab2/url2.txt");
                } else {
                    linkL.setText("http://bilgisayar.kocaeli.edu.tr/prolab2/url1.txt");
                }
            }
        });



        problemBx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                problemIndex = problemBx.getSelectedIndex();
                if (problemIndex == 0) {
                    linkL.setText("URL");
                } else {
                    linkL.setText("Size");
                }

            }
        });

        speed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                delay = speed.getValue();
                Update();
            }
        });

        startSearch();

    }



    public void startSearch() {	//START STATE

        if (searching) {
                    Alg.Algo();

        }
        pause();	//PAUSE STATE
    }

    public void pause() {	//PAUSE STATE
        System.out.println("----------------------------------- Stopped ---------------------------------------");
        int i = 0;
        while (!searching) {
            i++;
            if (i > 500) {
                i = 0;
            }
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                System.out.println("PAUSE ERROR");
            }
        }
        startSearch();	//START STATE
    }

    public void Update() {	//UPDATE ELEMENTS OF THE GUI

        CSIZE = MSIZE / cells;
        canvas.repaint();
        msL.setText(delay + "ms");
        lengthL.setText("Path Length: " + length);

        stepL.setText("Checks: " + steps);
    }

    public void reset() {	//RESET METHOD
        searching = false;
        length = 0;
        steps = 0;
    }

    public void delay() {	//DELAY METHOD
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
        }
    }

    class Map extends JPanel {	//MAP CLASS

        public void paintComponent(Graphics a) {	//REPAINT
            super.paintComponent(a);
            a.setColor(Color.decode("#030303")); // Black
            for (int x = 0; x < cells; x++) {	//PAINT EACH NODE IN THE GRID
                for (int y = 0; y < cells; y++) {
                    switch (map[x][y].getType()) {
                        case 0:
                            a.setColor(Color.GREEN);
                            break;
                        case 1:
                            a.setColor(Color.RED);
                            break;
                        case 2:
                            a.fillRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
                            a.setColor(Color.decode("#030303"));
                            a.drawRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
                            a.setColor(Color.ORANGE);
                            a.drawString(1 + "", x * CSIZE, (y * CSIZE) + 12);
                            a.setColor(Color.decode("#030303"));
                            break;
                        case 3:

                            if (map[x][y].getHidedType() == 7) {
                                //System.out.println("HIDED: " + x  + " " + y);
                                a.fillRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
                                a.setColor(Color.decode("#030303"));
                                a.drawRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
                                a.setColor(Color.PINK);
                                break;
                            }
                            if (map[x][y].getHidedType() == 9) {

                                a.fillRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
                                a.setColor(Color.PINK);
                                a.drawRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
                                a.setColor(Color.PINK);
                                break;
                            }
                            if (map[x][y].getHidedType() == 0) {
                                a.setColor(Color.GRAY);
                            }

                            break;
                        case 4:
                            a.setColor(Color.decode("#8F33FF")); // instead of CYAN
                            break;
                        case 5:
                            a.setColor(Color.YELLOW);
                            break;

                        case 6:
                            a.fillRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
                            a.setColor(Color.decode("#030303"));
                            a.drawRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
                            a.setColor(Color.ORANGE);
                            a.drawString(2 + "", x * CSIZE, (y * CSIZE) + 12);
                            a.setColor(Color.decode("#030303"));
                            break;

                        case 8:
                            a.fillRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
                            a.setColor(Color.decode("#030303"));
                            a.drawRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
                            a.setColor(Color.ORANGE);
                            a.drawString(3 + "", x * CSIZE, (y * CSIZE) + 12);
                            a.setColor(Color.decode("#030303"));
                            break;

                        case 55: // The Second algorithm movement
                            a.setColor(Color.decode("#8F33FF")); // instead of CYAN
                            break;
                    }
                    if ((map[x][y].getType() != 2) && (map[x][y].getType() != 6) && (map[x][y].getType() != 8)) {
                        a.fillRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
                        a.setColor(Color.decode("#030303"));
                        a.drawRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
                    }

                    //GREEN TEXT
                    if (map[x][y].getHidedType() == 7) {
                        a.setColor(Color.decode("#030303"));
                        a.drawString(2 + "", x * CSIZE, (y * CSIZE) + 12);
                        a.setColor(Color.decode("#030303"));
                    }

                    if (map[x][y].getHidedType() == 9) {
                        a.setColor(Color.decode("#030303"));
                        a.drawString(3 + "", x * CSIZE, (y * CSIZE) + 12);
                        a.setColor(Color.decode("#030303"));
                    }

                }
            }
        }

        public void StartAndEnd(int g, int p, int e, int r) {
            resetMap();	//RESET THE MAP WHENEVER CLICKED

            int tempStart = 1;

            for (int i = 0; i < 2; i++) {

                int x, y;

                if (tempStart == 1) { // Start
                    x = g;	// this should be random with maze wall check
                    y = p; // this should be random with maze wall check
                    tempStart = 11;
                    tool = 0;
                } else {  // End
                    x = e;	// this should be random with maze wall check
                    y = r;   // this should be random with maze wall check
                    tool = 1;
                }

                //System.out.println("IND: " + x + y);
                Node current = map[x][y];
                switch (tool) {
                    case 0: {	//START NODE
                        while ((current.getType() == 2) || (current.getType() == 6) || (current.getType() == 8) || (current.getHidedType() == 7) || (current.getHidedType() == 9)) {
                            x = ThreadLocalRandom.current().nextInt(0, cells);
                            y = ThreadLocalRandom.current().nextInt(0, cells);
                            current = map[x][y];
                        }
                        if ((current.getType() != 2) && (current.getType() != 6) && (current.getType() != 8)) {	//IF NOT WALL
                            if (startx > -1 && starty > -1) {	//IF START EXISTS SET IT TO EMPTY
                                map[startx][starty].setType(3);
                                map[startx][starty].setHops(-1);
                            }
                            current.setHops(0);
                            startx = x;	//SET THE START X AND Y
                            starty = y;
                            current.setType(0);	//SET THE NODE CLICKED TO BE START
                        }
                        break;
                    }
                    case 1: {//FINISH NODE
                        while ((current.getType() == 2) || (current.getType() == 6) || (current.getType() == 8) || (current.getHidedType() == 7) || (current.getHidedType() == 9)) {
                            x = ThreadLocalRandom.current().nextInt(0, cells);
                            y = ThreadLocalRandom.current().nextInt(0, cells);
                            current = map[x][y];
                        }
                        if ((current.getType() != 2) && (current.getType() != 6) && (current.getType() != 8)) {	//IF NOT WALL
                            if (finishx > -1 && finishy > -1) //IF FINISH EXISTS SET IT TO EMPTY
                            {
                                map[finishx][finishy].setType(3);
                            }
                            finishx = x;	//SET THE FINISH X AND Y
                            finishy = y;
                            current.setType(1);	//SET THE NODE CLICKED TO BE FINISH
                        }
                        break;
                    }
                    default:
                        if (current.getType() != 0 && current.getType() != 1) {
                            current.setType(tool);

                        }
                        break;
                }
                Update();

            }
        }
    }

    class Algorithm {	//ALGORITHM CLASS

        public void AStar() {
            int temp = delay;
            delay = 0;

            Robot2 robot2 = new Robot2(cells, steps, length, CSIZE, searching, map);
            ArrayList<Node> priority = new ArrayList<Node>();
            priority.add(map[startx][starty]);

            while (searching) { /// AHMAAAAAD

                if (priority.size() <= 0) {
                    searching = false;
                    break;
                }
                int hops = priority.get(0).getHops() + 1;
                RETURNER2 returner2 = robot2.exploreNeighbors(priority.get(0), hops);
                steps = returner2.getChecks();
                length = returner2.getLength();
                searching = returner2.getSolving();
                ArrayList<Node> explored = returner2.getExplored();

                if (explored.size() > 0) {
                    priority.remove(0);
                    priority.addAll(explored);
                    Update();
                    delay();

                } else {
                    priority.remove(0);

                }
                //sortQue(priority);	//SORT THE PRIORITY QUE

            }
            stopTime = System.nanoTime();
            // stop time
            if (startTime != 0) {
                long duration = stopTime - startTime;    //calculate the elapsed time

                double FinalTime = (double) duration / 1000000000;   //convert to ms
                System.out.println(String.format("Time %1.3f Seconds", FinalTime));

                //textDfs.setText(String.format("%1.3f ms", dfsTime));
                TimingL.setText(String.format("Elapsed Time: %1.3f Seconds", FinalTime));
            }

            delay = temp;
        }

        public void Algo() {
            
            if (delay == 0) {
                startTime = System.nanoTime();
                //System.out.println("++++++++++++++++++++++++++++++++++++++++++++ " + elapsedSeconds);
            }

            Robot robot = new Robot(cells, steps, length, CSIZE, searching, map);

            ArrayList<Node> priority = new ArrayList<Node>();

            ArrayList<Node> explored2 = new ArrayList<Node>();

            boolean up = false;
            boolean left = false;
            boolean right = false;
            boolean down = false;

            RETURNER returner = null;
            ArrayList<Node> explored = null;

            ArrayList<Node> toBeExplored = new ArrayList<Node>();

            priority.add(map[startx][starty]);
            
            while (searching) { /// AHMAAAAAD

                int aX = 0;
                int bX = 0;

                System.out.println("STATUS-> " + "Up: " + right + ", Left: " + left + ", Right: " + right + ", Down: " + down);

                boolean Ocheck = false;
                boolean Icheck = false;

                if (((!Ocheck) || (!Icheck)) && (!left) && (!right) && (!down)) {
                    System.out.println("COMONNNN");
                }

                if (priority.size() <= 0) {
                    searching = false;
                    break;
                }
                System.out.println("O COMONN");
                int hops = priority.get(0).getHops() + 1;

                if (((!Ocheck) || (!Icheck)) && (!left) && (!right) && (!down)) {

                    returner = robot.exploreNeighbors(priority.get(0), hops, aX, bX - 1, explored2);
                    Ocheck = returner.getOcheck();
                    Icheck = returner.getIcheck();
                    steps = returner.getChecks();
                    length = returner.getLength();
                    searching = returner.getSolving();
                    explored = returner.getExplored();

                    System.out.println("Up");

                    if (Icheck) {
                        up = true;
                    } else {
                        up = false;
                    }
                }
                if (((!Ocheck) || (!Icheck)) && (!up) && (!right) && (!down)) {
                    returner = robot.exploreNeighbors(priority.get(0), hops, aX - 1, bX, explored2);
                    Ocheck = returner.getOcheck();
                    Icheck = returner.getIcheck();
                    steps = returner.getChecks();
                    length = returner.getLength();
                    searching = returner.getSolving();
                    explored = returner.getExplored();

                    System.out.println("Left");

                    if (Icheck) {
                        left = true;
                    } else {
                        left = false;
                    }
                }
                if (((!Ocheck) || (!Icheck)) && (!left) && (!down) && (!up)) {
                    returner = robot.exploreNeighbors(priority.get(0), hops, aX + 1, bX, explored2);
                    Ocheck = returner.getOcheck();
                    Icheck = returner.getIcheck();
                    steps = returner.getChecks();
                    length = returner.getLength();
                    searching = returner.getSolving();
                    explored = returner.getExplored();

                    System.out.println("Right");

                    if (Icheck) {
                        right = true;
                    } else {
                        right = false;
                    }

                    System.out.println("Icheck: " + Icheck);
                }

                if (((!Ocheck) || (!Icheck)) && (!right)) {
                    returner = robot.exploreNeighbors(priority.get(0), hops, aX, bX + 1, explored2);
                    Ocheck = returner.getOcheck();
                    Icheck = returner.getIcheck();
                    steps = returner.getChecks();
                    length = returner.getLength();
                    searching = returner.getSolving();
                    explored = returner.getExplored();

                    System.out.println("Down");

                    if (Icheck) {
                        down = true;
                    } else {
                        down = false;
                    }
                }

                if (!(toBeExplored.isEmpty() && returner.getToBeExplored().isEmpty())) {
                    for (int i = 0; i < returner.getToBeExplored().size(); i++) {
                        toBeExplored.add(returner.getToBeExplored().get(i));
                        System.out.println("ADDDED");
                    }
                }

                for (int i = 0; i < toBeExplored.size(); i++) {
                    if (toBeExplored.get(i).getType() == 4) {
                        toBeExplored.remove(i);
                    }
                }

                if (explored.size() > 0) {
                    priority.remove(0);
                    priority.addAll(explored);
                    Update();
                    delay();

                } else {

                    System.out.println("TO ELSEE  " + toBeExplored.size());

                    if (!toBeExplored.isEmpty()) {
                        System.out.println("TELEPORTING...");
                        int AA;
                        int BB;
                        int minIndex = 0;
                        int min = 1000;

                        for (int i = 0; i < toBeExplored.size(); i++) {
                            AA = priority.get(0).getX() - toBeExplored.get(i).getX();
                            BB = priority.get(0).getY() - toBeExplored.get(i).getY();
                            int AA2 = Math.abs(AA);
                            int BB2 = Math.abs(BB);
                            if ((AA2 + BB2) < min) {
                                min = AA2 + BB2;
                                minIndex = i;
                            }

                        }

                        AA = priority.get(0).getX() - toBeExplored.get(minIndex).getX();
                        BB = priority.get(0).getY() - toBeExplored.get(minIndex).getY();

                        System.out.println("AA: " + toBeExplored.get(minIndex).getX() + " BB: " + toBeExplored.get(minIndex).getY());

                        returner = robot.exploreNeighbors(priority.get(0), hops, -AA, -BB, explored2);
                        Ocheck = returner.getOcheck();
                        Icheck = returner.getIcheck();
                        steps = returner.getChecks();
                        length = returner.getLength();
                        searching = returner.getSolving();
                        explored = returner.getExplored();

                        if (!(toBeExplored.isEmpty() && returner.getToBeExplored().isEmpty())) {
                            for (int i = 0; i < returner.getToBeExplored().size(); i++) {
                                toBeExplored.add(returner.getToBeExplored().get(i));
                                System.out.println("ADDDED");
                            }
                        }

                        for (int i = 0; i < toBeExplored.size(); i++) {
                            if (toBeExplored.get(i).getType() == 4) {
                                toBeExplored.remove(i);
                            }
                        }

                        priority.remove(0);
                        priority.addAll(explored);
                        Update();
                        delay();
                    }

                    if (steps == 10000) {
                        priority.remove(0);
                    }

                }
                
            }

            searching = true;

            Update();
            
            steps--; // just one time at the end
            
            Alg.AStar();
        }

    }

}
