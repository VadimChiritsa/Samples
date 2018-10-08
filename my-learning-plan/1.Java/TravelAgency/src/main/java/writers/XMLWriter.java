package writers;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class XMLWriter implements IWriter{

	@Override
	public void WriteToFile(String filePath, Object obj) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource((Document) obj);
			StreamResult result = new StreamResult(new File(filePath));
			
			transformer.transform(source, result);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
