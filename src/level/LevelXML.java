package level;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class LevelXML {
	
	private static final String[][][] returnValue = null;

	/**
	 * 
	 * Parses the XML file into an array format (horizontal, vertical, {tile type, entity type, degrees(angles)})
	 * 
	 * @param file XML file
	 * @return XML output
	 */
	
	@SuppressWarnings("null")
	String[][][] loadLevel(String file) {
		try {
			File xml = new File(file);
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(xml);
			
			document.getDocumentElement().normalize();
			
			NodeList nList = document.getElementsByTagName("level");
			
			String[][][] returnValue = null;
			
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				
				//rows
				if(node.getNodeType() == Node.DOCUMENT_NODE) {
					NodeList list1 = node.getChildNodes();
					
					for (int j = 0; j < list1.getLength(); j++) {
						Node node1 = list1.item(j);
						
						if(node1.getNodeType() == Node.DOCUMENT_NODE) {
							NodeList list2 = node1.getChildNodes();
							
							for (int k = 0; k < list2.getLength(); k++) {
								Node node2 = list2.item(k);
								
								if(node2.getNodeType() == Node.ELEMENT_NODE) {
									Element element = (Element) node2;
									
									returnValue[i][j][0] = element.getElementsByTagName("tile").item(0).getTextContent();
									returnValue[i][j][1] = element.getElementsByTagName("mobType").item(0).getTextContent();
									returnValue[i][j][2] = element.getElementsByTagName("deg").item(0).getTextContent();
								}
							}
						}
					}
				}
				
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
		return returnValue;
	}

}
