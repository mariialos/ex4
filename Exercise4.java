import java.util.Set;
import java.util.SortedSet;
import java.util.SortedMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Exercise4 {
	private Graph<Node> graph = new ListGraph<>();

    public void loadLocationGraph(String fileName){
    	try {
			FileReader file = new FileReader(fileName);
			BufferedReader in = new BufferedReader(file);
			String line = in.readLine();
			String[] items = line.split(";");
			for(int i = 0; i < items.length; i+=3) {
				String city = items[i];
				Double x = Double.parseDouble(items[i+1]);
				Double y = Double.parseDouble(items[i+2]);
				
			}
			
			
			in.close();
			file.close();	
    	}catch (FileNotFoundException e) {
			System.err.println("Filen " + fileName + " kan inte öppnas!");
		}catch (IOException e) {
			System.err.println("IO-fel har inträffat!");
		}
    	
    }

    public SortedMap<Integer, SortedSet<Record>> getAlsoLiked(Record item) {
       return null;
    }

    public int getPopularity(Record item) {
       return -1;
    }

    public SortedMap<Integer, Set<Record>> getTop5() {
       return null;
    }

    public void loadRecommendationGraph(String fileName) {
    }

}
