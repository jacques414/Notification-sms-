import java.io.*;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;

public class UrgentMail extends HttpServlet {

   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void doPost(HttpServletRequest request, HttpServletResponse response) 
   throws IOException, ServletException {
// Récupération des paramètres envoyés : adresse mail, mot de passe, numéro de téléphone  
	final String email=request.getParameter("email");
	final String password=request.getParameter("password");
	final String phonenumber=request.getParameter("phonenumber");
		
// Thread qui surveille la boite email
	Thread thr=new Thread()
	{
	
	public void run()
		{
		
	Urgent urgentClient=new Urgent(email,password,phonenumber);
	Date date= new Date();
	
	while(date!=null)
	{    			
	
	try {
		sleep(10000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
     date=urgentClient.readUrgentMail(date);
	}
	}
	};
	thr.start();

	// Envoyer la réponse
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head>");
    out.println("<title>Réponse</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>Votre demande a été prise en compte</h1>");
    out.println("</body>");
    out.println("</html>");
   }
}