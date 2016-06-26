package com.eg.Makany.Models.BA;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.json.JSONException;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.alchemyapi.api.AlchemyAPI;


public class MakanyAlchemy {
	
	final static String ALCHEMY_KEY = "2b4fc971215f193b42ebb0ca7e1052164dddc5f7";

	// Create an AlchemyAPI object.
	static AlchemyAPI alchemyObj= AlchemyAPI.GetInstanceFromString(ALCHEMY_KEY);
	
	public MakanyAlchemy() {
		// TODO Auto-generated constructor stub
	}
	
	private static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
	
	public static Vector<String> getFromAlchemy(String text) 
	{
		Document doc;
		try {
			
			doc = alchemyObj.TextGetTaxonomy(text);
			String theXmlResult = getStringFromDocument(doc);
			org.json.JSONObject xmlJSONObj = XML.toJSONObject(theXmlResult);
			
			
			org.json.JSONArray arr = xmlJSONObj.getJSONObject("results").getJSONObject("taxonomy").getJSONArray("element");
			System.out.println(arr.toString());
			
			
			Vector<String> rtn = new Vector <String>();
			for (int i = 0 ; i < arr.length(); i++)
			{
				org.json.JSONObject J = arr.getJSONObject(i);
				String label = J.getString("label");
				String score = J.getString("score");
				
				System.out.println("label " + label + " score " + score);
				rtn.add(label + ";" + score);

			}
			return rtn;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error " + e.getMessage());
			return null;
		}
		 
		
		
	}
	
	public static Vector<String> AnalyzePhoto(String URL) 
	{
		Document doc;
		try {
			doc = alchemyObj.URLGetRankedImageKeywords(URL);
			 String theXmlResult = getStringFromDocument(doc);
				org.json.JSONObject xmlJSONObj = XML.toJSONObject(theXmlResult);
				
				org.json.JSONArray arr = xmlJSONObj.getJSONObject("results").getJSONObject("imageKeywords").getJSONArray("keyword");
		        
		        Vector<String> rtn = new Vector <String>();
		        
				for (int i = 0 ; i < arr.length(); i++)
				{
					org.json.JSONObject J = arr.getJSONObject(i);
					String text = J.getString("text");
					String score = J.getString("score");
					
					System.out.println("text " + text + " score " + score);
					rtn.add(text + ";" + score);
					
				}
				return rtn;
		}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error " + e.getMessage());
			return null;
		} 
	       
	}
	
	

	
}
