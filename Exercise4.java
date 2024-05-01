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
    		Map<String, Location> mapCityNameToLocation = new HashMap<>();
			FileReader file = new FileReader(fileName);
			BufferedReader in = new BufferedReader(file);
			//reads first line, splits it into separate items, then process items with step of 3 (3 items per loop) to get data for fields in Location
			String line = in.readLine();
			String[] items = line.split(";");
			for(int i = 0; i < items.length; i+=3) {
				String cityName = items[i];
				Double x = Double.parseDouble(items[i+1]);
				Double y = Double.parseDouble(items[i+2]);
				Location location = new Location(cityName, x, y);
				mapCityNameToLocation.put(cityName, location);
				graph.add(location);
			}
			
			//reads line by line and splits every line into 4 items
			while((line = in.readLine()) != null) {
				String[] elements  = line.split(";");
				String from = elements[0];
				String to = elements[1];
				String edgeName = elements[2];
				int edgeWeight = Integer.parseInt(elements[3]);
				graph.connect(mapCityNameToLocation.get(from), mapCityNameToLocation.get(to), edgeName, edgeWeight);
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
      	try {
			FileReader file = new FileReader(fileName);
			BufferedReader in = new BufferedReader(file);
			String line;
			
			//reads line by line and splits it into 3 items (Person's name, name of record, name of artist)
			while((line = in.readLine()) != null) {
				String[] elements  = line.split(";");
				String personName = elements[0];
				String recordName = elements[1];
				String artistName = elements[2];
				Person person = new Person(personName);
				Record record = new Record(recordName, artistName);
				graph.add(person);
				graph.add(record);
                graph.connect(person, record, "", 0);
			}
			
			in.close();
			file.close();	
    	}catch (FileNotFoundException e) {
			System.err.println("Filen " + fileName + " kan inte öppnas!");
		}catch (IOException e) {
			System.err.println("IO-fel har inträffat!");
		}
 
    }

}
