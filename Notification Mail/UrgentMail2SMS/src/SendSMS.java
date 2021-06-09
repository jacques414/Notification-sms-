import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;

public class SendSMS {
	String url = "";
	String textMessage="";
	String Message="";
	
	
	public String Send(String phoneNumber,String Message,String token ) throws Exception
	{
		textMessage = URLEncoder.encode( Message	, "UTF-8");
		this.Message=Message;
		url = "http://ws.red.sfr.fr/red-ws/red-b2c/resources/sms/send?responseType=xml&token="
			+ token + "&to=" + phoneNumber + "&type=PhoneNumber&msg="
			+ textMessage;
	{
		//Envoyer une requête GET 
		URL client = new URL(url);
		URLConnection conn = client.openConnection();
		InputStream responseBody = conn.getInputStream();
		
		//Convertir les données en XML document 
		Document response = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(responseBody);
		responseBody.close();

		XPath xPath = XPathFactory.newInstance().newXPath();
		String erreur = xPath.evaluate("/sms/errorCode", response);

		//Retourne  le message envoyer
		if (erreur.equals("0")) return Message; 
		//Retourne les informations d’erreur 
		else return xPath.evaluate("/sms/error", response);
		
	}

}
}