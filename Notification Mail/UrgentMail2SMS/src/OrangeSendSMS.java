import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;

public class OrangeSendSMS {
	String url = "";
	String textMessage="";
	String Message="";
	
	
	public String Send(String phoneNumber,String Message,String accesskey ) throws Exception
	{
		textMessage = URLEncoder.encode( Message	, "UTF-8");
		this.Message=Message;
		url = "http://run.orangeapi.com/sms/sendSMS.xml?id="
			+ accesskey + "&from=38100"+"&to=" + phoneNumber + "&content="
			+ textMessage+"&ack=false";
	{
		//Envoyer une requête GET 
		URL client = new URL(url);
		URLConnection conn = client.openConnection();
		InputStream responseBody = conn.getInputStream();
		
		//Convertir les données en XML document 
		Document response = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(responseBody);
		responseBody.close();

		XPath xPath = XPathFactory.newInstance().newXPath();
		String erreur = xPath.evaluate("response/status/status_code", response);

		//Retourne  le message envoyer
		if (erreur.equals("200")) return Message; 
		//Retourne les informations d’erreur 
		else return xPath.evaluate("response/status/status_msg", response);
		
	}

}

}
