package readers;

import java.util.ArrayList;

import tours.Tour;

public interface IReader {
	
	/***
	 * 
	 * @param filePath 
	 * @return Array of Tour objects
	 */
	public ArrayList<Tour> Read(String filePath);
	
	//public List<Tour> Convert(JSONArray array);
	//It's interesting if i can convert from common object or i should run it in from 
	//separate files

}
