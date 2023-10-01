
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class URLTextReader {

    public int[][] Reader(String link) {

        ArrayList<String> lines = new ArrayList<>();
        int maze[][] = null;

        try {

            URL url = new URL(link); //http://bilgisayar.kocaeli.edu.tr/prolab2/url1.txt

            // read text returned by server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            while ((line = in.readLine()) != null) {
                System.out.println(line);
                lines.add(line);

            }
            maze = new int[lines.size()][lines.get(0).length()];

            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[0].length; j++) {
                    maze[i][j] = Integer.parseInt(lines.get(j).charAt(i) + "");

                }
            }
            
            // Green Random Box
            // 22 
            /*
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[0].length; j++) {
                    
                    if(maze[i][j] == 2)
                    {
                        maze[i][j] = 6;
                    }
                    

                }
            }
            */
            

            in.close();

        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }

        return maze;

    }

}
