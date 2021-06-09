import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;


public class IsSFRNum  {
	String url = "";
	
	public boolean Test(String phoneNumber,String token) throws Exception
	{
		url = "http://ws.red.sfr.fr/red-ws/red-b2c/resources/sis/mvnoinfo?responseType=xml&token="
			+ token + "&userIdentifier=" + phoneNumber + "&type=PhoneNumber";		
		
        //Envoyer une requête GET 
		URL client = new URL(url);	
		URLConnection conn = client.openConnection();
		InputStream responseBody = conn.getInputStream();
		
		//Convertir les données en XML document 
		Document response = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(responseBody);
		responseBody.close();
		
		//Xpath expression pour récupérer le nom de l'opérateur du numéro
		XPath xPath = XPathFactory.newInstance().newXPath();
		String lignetype = xPath.evaluate("/sis/mvnoInfo/labelMvno", response);

		if (lignetype.equals("SFR")) return true; 
		else 
		{
			return false;
		}
		
		
	}

}
