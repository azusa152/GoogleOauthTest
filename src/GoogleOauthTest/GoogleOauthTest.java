package GoogleOauthTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class OIDC
 */
@WebServlet("/GoogleOauthTest")
public class GoogleOauthTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String backToken="";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoogleOauthTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 URL urlObtainToken =  new URL("https://accounts.google.com/o/oauth2/token");
		  HttpURLConnection connectionObtainToken =  (HttpURLConnection) urlObtainToken.openConnection();
		  // �]�w��connection�ϥ�POST
		  connectionObtainToken.setRequestMethod("POST");
		  connectionObtainToken.setDoOutput(true);
		   
		  // �}�l�ǰe�Ѽ� 
		  OutputStreamWriter writer  = new OutputStreamWriter(connectionObtainToken.getOutputStream());
		  writer.write("code="+request.getParameter("code")+"&");   // ���oGoogle�^�Ǫ��Ѽ�code
		  writer.write("client_id=709511805413-qgfbar0am6n85p1a9c8bppk1dp033d8a.apps.googleusercontent.com&");   // �o�̽бNxxxx�������ۤv��client_id
		  writer.write("client_secret=TcsBkpVsCS-H1iP7_onGIrRg&");   // �o�̽бNxxxx�������ۤv��client_serect
		  writer.write("redirect_uri=http://localhost:8080/GoogleOauthTest/GoogleOauthTest&");   // �o�̽бNxxxx�������ۤv��redirect_uri
		  writer.write("grant_type=authorization_code");  
		  writer.close();
		// �p�G�{�Ҧ��\
		  if (connectionObtainToken.getResponseCode() == HttpURLConnection.HTTP_OK){
		   StringBuilder sbLines   = new StringBuilder("");
		   
		   // ���oGoogle�^�Ǫ����(JSON�榡)
		   BufferedReader reader = 
		       new BufferedReader(new InputStreamReader(connectionObtainToken.getInputStream(),"utf-8"));
		   String strLine = "";
		   while((strLine=reader.readLine())!=null){
		    sbLines.append(strLine);
		   }
		   
		  
		  try {
		       // ��W�����^�Ӫ���ơA��iJSONObject���A�H��K�ڭ̪����s����Q�n���Ѽ�
		    JSONObject jo = new JSONObject(sbLines.toString());
		    
		    // �L�XGoogle�^�Ǫ�access token
		    response.getWriter().println(jo.getString("access_token")); 
		    backToken=jo.getString("access_token");
		   } catch (JSONException e) {
		    e.printStackTrace();
		   }
		  }
		  passToken(request, response);
	
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
	}
	protected void passToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		  URL urlObtainToken =  new URL("https://www.googleapis.com/oauth2/v1/tokeninfo");
		  HttpURLConnection connectionObtainToken =  (HttpURLConnection) urlObtainToken.openConnection();
		  // �]�w��connection�ϥ�POST
		  connectionObtainToken.setRequestMethod("POST");
		  connectionObtainToken.setDoOutput(true);
		
		  OutputStreamWriter writer  = new OutputStreamWriter(connectionObtainToken.getOutputStream());
		  writer.write("access_token="+backToken);   // ���oGoogle�^�Ǫ��Ѽ�code
		  writer.close();
		  if (connectionObtainToken.getResponseCode() == HttpURLConnection.HTTP_OK){
			   StringBuilder sbLines   = new StringBuilder("");
			   
			   // ���oGoogle�^�Ǫ����(JSON�榡)
			   BufferedReader reader = 
			       new BufferedReader(new InputStreamReader(connectionObtainToken.getInputStream(),"utf-8"));
			   String strLine = "";
			   while((strLine=reader.readLine())!=null){
			    sbLines.append(strLine);
			   }
			   
			  
			  try {
			       // ��W�����^�Ӫ���ơA��iJSONObject���A�H��K�ڭ̪����s����Q�n���Ѽ�
			    JSONObject jo = new JSONObject(sbLines.toString());
			    
			    // �L�XGoogle�^�Ǫ�access token
			    response.getWriter().println(jo.getString("email")); 
			    response.getWriter().println(jo.getString("verified_email")); 
			    
			   } catch (JSONException e) {
			    e.printStackTrace();
			   }
			  }
		
	}


}
