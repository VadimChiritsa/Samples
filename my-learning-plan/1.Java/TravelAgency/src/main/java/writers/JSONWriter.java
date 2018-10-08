package writers;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

public class JSONWriter implements IWriter{
	
	public void WriteToFile(String filePath, Object obj) {
		
		try {
			FileWriter fw = new FileWriter(filePath);
			fw.write(((JSONObject)obj).toJSONString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
