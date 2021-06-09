import java.util.Date;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class Urgent {
	private String userName;
    private String password;
    private String receivingHost;
    private String phoneNumber;
    // Token SFR API pour tester L’application 
    final private String token="ae24de54fc6a711245ac4b470fcac072";
    // Votre propre Orange Access Key
    final private String accesskey="";
    
    Urgent(String userName,String password,String phoneNumber)
    {
        this.userName=userName;
        this.password=password;
        this.phoneNumber=phoneNumber;
    } 
    public Date readUrgentMail(Date date)
    { 
    // connexion à un compte gmail    
    	this.receivingHost="imap.gmail.com";
        Properties props2=System.getProperties();
        props2.setProperty("mail.store.protocol", "imaps");       
        Session session2=Session.getDefaultInstance(props2, null);	       
        try {
                    Store store=session2.getStore("imaps");
                    store.connect(this.receivingHost,this.userName, this.password);
                    Folder folder=store.getFolder("INBOX");
                    folder.open(Folder.READ_ONLY);
                    
            
                    //Récupération des emails 
                    
                    Message message[]=folder.getMessages();
                    String messg="";
                    int i;
                    for( i=0;i<message.length;i++){	                    
                    	messg=message[i].getSubject();
                    	// Si le massage est nouveau et il contient le mot urgent dans le sujet
                    	if(((messg.contains("Urgent")||messg.contains("urgent")))&&(message[i].getReceivedDate().after(date)))
                    	{	
                    // Si le numéro est un numéro SFR
                    		IsSFRNum sfrnum=new IsSFRNum();
                    //	Envoyer SMS via SFR API
                    		if (sfrnum.Test(phoneNumber, token)){
                    		SendSMS sendSMS=new SendSMS();	  
                    		System.out.print( sendSMS.Send(phoneNumber,"From:"
                            		+message[i].getFrom()[0].toString()+", Subject: "+message[i].getSubject() 
                            		+ ", At:"+message[i].getReceivedDate() ,token));
                    		}
                    		else
                    		{
                    // Sinon envoyer SMS via Orange API	
                    		OrangeSendSMS sendSMS=new OrangeSendSMS();	 		
                    		System.out.print( sendSMS.Send(phoneNumber,"From:"
                    		+message[i].getFrom()[0].toString()+", Subject: "+message[i].getSubject() 
                    		+ ", At:"+message[i].getReceivedDate() ,accesskey));
                    	}
                    	}
                    }
                    		folder.close(true);
                    		store.close();
                    		return message[i-1].getReceivedDate();
        		}
        					catch (Exception e) {
        					return null;
            }

    	}

}
