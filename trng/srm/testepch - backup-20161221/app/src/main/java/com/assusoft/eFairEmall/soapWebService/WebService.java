package com.assusoft.eFairEmall.soapWebService;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.assusoft.eFairEmall.entities.ExhibitorProducts;
import com.assusoft.eFairEmall.entities.Products;
import com.assusoft.eFairEmall.entities.Users;
import com.assusoft.eFairEmall.util.Util;
import com.assusoft.eFairEmall.util.WebDataFile;
import com.epch.efair.delhifair.Commons;

@SuppressLint("NewApi")
public class WebService {
	Context context;
	public static final String RENDOM_ALPHA_NUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final int RENDOM_STR_LENGTH=20;
	public static boolean isResponseOk=false;
	public static int requestCount=0;
	static Random rnd;
	static WebDataFile webDataFile;
	public static final String SALT="dhfjGHT54%$*hj&@RTFGH^&*760847";
	 public static long contentLengthOfInitDb;
	 public static final String RESPONSE_STATUS_PASS="pass";
	 public static final String STATUS_CODE="statusCode";
	 public static final String RESPONSE_STATUS_FAIL="fail";
	 public static final String STATUS="status";
	 public static final String RESPONSE_RESULT="result";
	 public static final String RESPONSE_ERROR="error";
	 public static final String REGISTERED_STATUS="registered";
	 public static final String REGISTERED_STATUS_F="registered_F";
	 public static String USER_REGISTRTION="User Registration";
	@SuppressLint("NewApi")
	
	
	
	/***** Update record method *****************************/
	public static String sendUserFeedback(String param) {
			SoapObject request = feedBack(param);
			SoapObject response = invokeService(request, Util.BASE_URL,
					Util.NAMESPACE, Util.USER_FEEDBACK);
			String message = getResponse(response);
			if(message==null||message.isEmpty())
			{   Log.i("WEB_DATA","Data not found from server");
			   //Again requesting when data is not found
			   response = invokeService(request, Util.BASE_URL,
						Util.NAMESPACE, Util.USER_FEEDBACK);
			   message = getResponse(response);
			}
			
			return message;
		}
		public static SoapObject feedBack(String param) {
			
			SoapObject request = new SoapObject(Util.NAMESPACE, Util.USER_FEEDBACK);
			//add  parameter to request
			PropertyInfo pi = new PropertyInfo();
			pi.setName("details");
			pi.setType(String.class);
			pi.setValue(param);
			request.addProperty(pi);
			
			return request;
		}
	/***** database initialize method *****************************/
	public static String getDatabaseInitialize(Context context,String name) {
		String message=null;
		try{
		SoapObject request = initializeDatabase(name);
		SoapObject response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.DATABASE_INITIALIZE);
		 message = getResponse(response);
		if(message==null||message.isEmpty())
		{   Log.i("WEB_DATA","Data not found from server");
		   //Again requesting when data is not found
		   response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.DATABASE_INITIALIZE);
		   message = getResponse(response);
		}
		webDataFile=new WebDataFile(context);
		Commons.webDataFile=webDataFile;
		webDataFile.writeToFile(message);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return message;
		
	}
   public static SoapObject initializeDatabase(String nameKey) {
		
		SoapObject request = new SoapObject(Util.NAMESPACE, Util.DATABASE_INITIALIZE);
		PropertyInfo pi = new PropertyInfo();
		pi.setName("param");
		pi.setType(String.class);
		pi.setValue(nameKey);
		request.addProperty(pi);
		return request;
	}
   /***** Update record method *****************************/
   public static String getUpdatedRecords(Context context,String timeStamp) {
		SoapObject request = updatedRecords(timeStamp);//flag=0 then download file otherwise updated record
		SoapObject response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.GET_UPDATED_RECORD);
		String message = getResponse(response);
		//Log.i("response:",message);
		//Log.i("Response Time",""+(System.currentTimeMillis()-com.epch.efair.delhifair.MainActivity.startTimeDownload));
		webDataFile=Commons.webDataFile;
		if(webDataFile!=null)
		{
		webDataFile.writeToFile_update(message);
		}else{
			webDataFile=new WebDataFile(context);
			webDataFile.writeToFile_update(message);
			Commons.webDataFile=webDataFile;
		}
		return message;
	}
	public static SoapObject updatedRecords(String timeStamp) {
		
		SoapObject request = new SoapObject(Util.NAMESPACE, Util.GET_UPDATED_RECORD);
		//add  parameter to request
		PropertyInfo pi = new PropertyInfo();
		pi.setName("param");
		pi.setType(String.class);
		pi.setValue(timeStamp);
		request.addProperty(pi);
		
		return request;
	}
	 /***** Send Mail method *****************************/
	   public static String sendMailService(String mailData,Context context) {
			SoapObject request = sendMail(mailData);//flag=0 then download file otherwise updated record
			SoapObject response = invokeService(request, Util.BASE_URL,
					Util.NAMESPACE, Util.SEND_MAIL);
			String message = getResponse(response);
			if(message==null||message.isEmpty())
			{   Log.i("WEB_DATA","Data not found from server");
			   //Again requesting when data is not found
			   response = invokeService(request, Util.BASE_URL,
					Util.NAMESPACE, Util.SEND_MAIL);
			   message = getResponse(response);
			}
			return message;
		}
   public static SoapObject sendMail(String mailData) {
		
		SoapObject request = new SoapObject(Util.NAMESPACE, Util.SEND_MAIL);
		//add  parameter to request
		PropertyInfo pi = new PropertyInfo();
		pi.setName("param");
		pi.setType(String.class);
		pi.setValue(mailData);
		request.addProperty(pi);
		
		return request;
	}
   /***** Give resource method *****************************/
   public static String giveResoucefile(String param) {
		SoapObject request = giveResource(param);//flag=0 then download file otherwise updated record
		SoapObject response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.GIVE_RESOURCE);
		String message = getResponse(response);
		if(message==null||message.isEmpty())
		{   Log.i("WEB_DATA","Data not found from server");
		   //Again requesting when data is not found
		   response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.GIVE_RESOURCE);
		   message = getResponse(response);
		}
		return message;
	}
public static SoapObject giveResource(String mailData) {
	
	SoapObject request = new SoapObject(Util.NAMESPACE, Util.GIVE_RESOURCE);
	//add  parameter to request
	PropertyInfo pi = new PropertyInfo();
	pi.setName("details");
	pi.setType(String.class);
	pi.setValue(mailData);
	request.addProperty(pi);
	
	return request;
}
/******************************Check for update ********************************/
public static String checkForUpdates(String param) {
	SoapObject request = updates(param);//flag=0 then download file otherwise updated record
	SoapObject response = invokeService(request, Util.BASE_URL,
			Util.NAMESPACE, Util.CHECK_FOR_UPDATES);
	String message = getResponse(response);
	if(message==null||message.isEmpty())
	{   Log.i("WEB_DATA","Data not found from server");
	   //Again requesting when data is not found
	   response = invokeService(request, Util.BASE_URL,
			Util.NAMESPACE, Util.CHECK_FOR_UPDATES);
	   message = getResponse(response);
	}
	return message;
}
public static SoapObject updates(String mailData) {

	SoapObject request = new SoapObject(Util.NAMESPACE, Util.CHECK_FOR_UPDATES);
	//add  parameter to request
	PropertyInfo pi = new PropertyInfo();
	pi.setName("details");
	pi.setType(String.class);
	pi.setValue(mailData);
	request.addProperty(pi);
	
	return request;
}
   /***** submitVisitor method *****************************/
   public static String getSubmitVisitor(String user) {
		SoapObject request = submitVisitor(user);//flag=0 then download file otherwise updated record
		SoapObject response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.SUBMIT_VISITOR);
		String message = getResponse(response);
		if(message==null||message.isEmpty())
		{   Log.i("WEB_DATA","Data not found from server");
		   //Again requesting when data is not found
		   response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.SUBMIT_VISITOR);
		   message = getResponse(response);
		}
		return message;
	}
public static SoapObject submitVisitor(String user) {
	
	SoapObject request = new SoapObject(Util.NAMESPACE, Util.SUBMIT_VISITOR);
	//add  parameter to request
	PropertyInfo pi = new PropertyInfo();
	pi.setName("param");
	pi.setType(String.class);
	pi.setValue(user);
	request.addProperty(pi);
	
	return request;
}
/***** Register for space method *****************************/
public static String getRegisterForSpace(String user) {
		SoapObject request = registerForSpace(user);//flag=0 then download file otherwise updated record
		SoapObject response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.REGISTER_FOR_SPACE);
		String message = getResponse(response);
		if(message==null||message.isEmpty())
		{   Log.i("WEB_DATA","Data not found from server");
		   //Again requesting when data is not found
		   response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.REGISTER_FOR_SPACE);
		   message = getResponse(response);
		}
		return message;
	}
public static SoapObject registerForSpace(String user) {
	
	SoapObject request = new SoapObject(Util.NAMESPACE, Util.REGISTER_FOR_SPACE);
	//add  parameter to request
	PropertyInfo pi = new PropertyInfo();
	pi.setName("param");
	pi.setType(String.class);
	pi.setValue(user);
	request.addProperty(pi);
	
	return request;
}

/**Visitor Registration for delhi fair*/
	public static String getRegisterForVisitorDelhifair(String user) {
		SoapObject request = registerForDelhifair(user);//flag=0 then download file otherwise updated record
		SoapObject response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.VISITPR_REGISTRATION_FOR_DELHIFAIR);
		String message = getResponse(response);
		if(message==null||message.isEmpty())
		{   Log.i("WEB_DATA","Data not found from server");
		   //Again requesting when data is not found
		   response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.VISITPR_REGISTRATION_FOR_DELHIFAIR);
		   message = getResponse(response);
		}
		return message;
	}
	public static SoapObject registerForDelhifair(String user) {
	
		SoapObject request = new SoapObject(Util.NAMESPACE, Util.VISITPR_REGISTRATION_FOR_DELHIFAIR);
		//add  parameter to request
		PropertyInfo pi = new PropertyInfo();
		pi.setName("param");
		pi.setType(String.class);
		pi.setValue(user);
		request.addProperty(pi);
		
		return request;
	}
	/***** Register for space method *****************************/
	public static String setPromotionalInfo(String user) {
		SoapObject request = promotionalInfo(user);//flag=0 then download file otherwise updated record
		SoapObject response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.SET_PROMOTIONAL_INFO);
		String message = getResponse(response);
		if(message==null||message.isEmpty())
		{   Log.i("WEB_DATA","Data not found from server");
		   //Again requesting when data is not found
		   response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.SET_PROMOTIONAL_INFO);
		   message = getResponse(response);
		}
		return message;
	}
	
public static SoapObject promotionalInfo(String user) {
	
	SoapObject request = new SoapObject(Util.NAMESPACE, Util.SET_PROMOTIONAL_INFO);
	//add  parameter to request
	PropertyInfo pi = new PropertyInfo();
	pi.setName("param");
	pi.setType(String.class);
	pi.setValue(user);
	request.addProperty(pi);
	
	return request;
}
	public static String getTimestamp() {
		String message=null;
		try{
		SoapObject request = getTimeStampRequest();
		SoapObject response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.SERVER_TIME_STAMP);
		 message = getResponse(response);
		Log.i("WEB_DATA",message);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
		
	}
	
	public static SoapObject getTimeStampRequest() {
		
		SoapObject request = new SoapObject(Util.NAMESPACE, Util.SERVER_TIME_STAMP);
		
		return request;
	}
	
	public static String getDatabaseFile(String flag) {
		SoapObject request = getDatabaseFileRequest(flag);//flag=0 then download file otherwise updated record
		SoapObject response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.LOAD_DATABASE_FILE);
		String message = getResponse(response);
		Log.i("TIMESTAMP",message);
		return message;
	}
	public static SoapObject getDatabaseFileRequest(String flag) {
		
		SoapObject request = new SoapObject(Util.NAMESPACE, Util.LOAD_DATABASE_FILE);
		//add  parameter to request
		PropertyInfo pi = new PropertyInfo();
		pi.setName("creationDate");
		pi.setType(String.class);
		pi.setValue(flag);
		request.addProperty(pi);
		
		return request;
	}
	
	
	public static String submitUsers(Users user) {
		SoapObject request = getUserRequest(user);
		SoapObject response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.USER_URL);
		String message = getResponse(response);
		return message;
	}

	public static String submitProducts(Products product) {
		SoapObject request = WebService.getProductsRequest(product);
		SoapObject response = WebService.invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.PRODUCTS_URL);
		String message = getResponse(response);
		return message;
	}

	public static String submitExhibitorsProducts(ExhibitorProducts exbProduct) {
		SoapObject request = getExbitorsProductsRequest(exbProduct);
		SoapObject response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.EXIBITORS_PRODUCTS_URL);
		String message = getResponse(response);
		return message;
	}

	public static Users[] fetchBatch() {
		SoapObject request = new SoapObject(Util.NAMESPACE, Util.USER_URL);
		SoapObject response = invokeService(request, Util.BASE_URL,
				Util.NAMESPACE, Util.USER_URL);
		Users[] batches = retrieveUsersFromSoap(response);
		return batches;
	}

	public static SoapObject invokeService(SoapObject request, String URL,
			String NAMESPACE, String METHOD_NAME) {
		SoapObject response = null;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug = true;
		
		try { requestCount++;
			@SuppressWarnings("unchecked")
			List<HeaderProperty> respHeaders=androidHttpTransport.call(NAMESPACE + METHOD_NAME, envelope,new ArrayList<HeaderProperty>());
			
			response = (SoapObject) envelope.bodyIn;
			for(int ix=0; ix<respHeaders.size(); ix++) { 
				try {
					HeaderProperty hp = respHeaders.get(ix);
					if(hp.getKey().equalsIgnoreCase("X-Uncompressed-Content-Length"))
			        {                                  
			        	contentLengthOfInitDb=Integer.parseInt(hp.getValue());
			        	Log.i("WEB_DATA","Header"+ix+"="+hp.getKey()+" / "+hp.getValue());
			        }
			        Log.i("WEB_DATA","Header"+ix+"="+hp.getKey()+" / "+hp.getValue());
				} catch (Exception e) {
					// TODO: handle exception
					Log.i("WEB_DATA","Exception in respHeaders.get(ix):"+e);
				}      
		        
		} 
			//or we can use
			//response=(SoapObject) envelope.getResponse();
			isResponseOk=true;
			Log.i("WEB_DATA","response:"+response);
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("WEB_DATA","response exception:"+e);
			isResponseOk=false;
		}
		return response;
	}

	public static SoapObject getUserRequest(Users user) {
		SoapObject user1 = new SoapObject(Util.NAMESPACE, "Users");
		
		user1.addProperty("roleId", user.getRoleId());
		user1.addProperty("loginEmail", user.getLoginEmail());
		user1.addProperty("password", user.getPassword());
		user1.addProperty("status", user.getStatus());
		user1.addProperty("lastLoggedIn", user.getLastLoggedIn());
		
		user1.addProperty("secretQuestion", user.getSecretQuestion());
		user1.addProperty("secretAnswer", user.getSecretAnswer());
		
		user1.addProperty("creationDate", user.getCreationDate());
		user1.addProperty("updatedBy", user.getUpdatedBy());
		SoapObject request = new SoapObject(Util.NAMESPACE, Util.USER_URL);
		PropertyInfo pi = new PropertyInfo();
		pi.setName("user");
		pi.setType(user1.getClass());
		pi.setValue(user1);
		request.addProperty(pi);
		return request;
	}

	public static SoapObject getProductsRequest(Products product) {
		SoapObject product1 = new SoapObject(Util.NAMESPACE, "Products");
		product1.addProperty("productId", product.getProductId());
		product1.addProperty("productName", product.getProductName());
		product1.addProperty("productType", product.getProductType());
		product1.addProperty("productBrand", product.getProductBrand());
		product1.addProperty("description", product.getDescription());
		
		product1.addProperty("creationDate", product.getCreationDate());
		product1.addProperty("updatedBy", product.getUpdatedBy());

		SoapObject request = new SoapObject(Util.NAMESPACE, Util.PRODUCTS_URL);
		PropertyInfo pi = new PropertyInfo();
		pi.setName("products");
		pi.setType(product1.getClass());
		pi.setValue(product1);
		request.addProperty(pi);
		return request;
	}

	public static SoapObject getExbitorsProductsRequest(
			ExhibitorProducts exbProduct) {
		SoapObject exbProduct1 = new SoapObject(Util.NAMESPACE,
				"ExhibitorsProducts");
		exbProduct1.addProperty("productId", exbProduct.getProductId());
		exbProduct1.addProperty("exibitorsId", exbProduct.getExibitorsId());
		
		exbProduct1.addProperty("creationDate", exbProduct.getCreationDate());
		exbProduct1.addProperty("updateBy", exbProduct.getUpdatedBy());

		SoapObject request = new SoapObject(Util.NAMESPACE,
				Util.EXIBITORS_PRODUCTS_URL);
		PropertyInfo pi = new PropertyInfo();
		pi.setName("exbProduct");
		pi.setType(exbProduct1.getClass());
		pi.setValue(exbProduct1);
		request.addProperty(pi);
		return request;
	}

public static Users[] retrieveUsersFromSoap(SoapObject soap) {
	
	Users[] users = null;
	
    //SoapObject info = (SoapObject) soap.getProperty(0);
	
	 users=new Users[soap.getPropertyCount()];
	try{
	for (int i = 0; i < users.length; i++) {
		SoapObject info = (SoapObject) soap.getProperty(i);
		//best to use getProperty("userId")
		Users user = new Users();
		
			user.setRoleId(Integer.parseInt((info.getProperty("roleId").toString())));
			user.setLoginEmail(info.getProperty("loginEmail").toString());
		
			user.setPassword(info.getProperty("password").toString());
			user.setStatus(Integer.parseInt(info.getProperty("status").toString()));
			user.setLastLoggedIn(info.getProperty("lastLoggedIn").toString());
			
			//user.setLogoPhoto(info.getProperty(7).oString());
			user.setSecretQuestion(info.getProperty("secretQuestion").toString());
			user.setSecretAnswer(info.getProperty("secretAnswer").toString());
			
			user.setCreationDate(info.getProperty("creationDate").toString());
			user.setUpdatedBy(info.getProperty("updatedBy").toString());
		
      }
	}catch(Exception e){
		e.printStackTrace();
	}
	
	
  return users;
}

	// mymodification

	public static String[] extractRowcellData(String strrow) {
		String[] rowcellData = new String[8];
		StringTokenizer str = new StringTokenizer(strrow, ";");
		int i = 0;
		try {
			while (str.hasMoreTokens()) {

				String strex = str.nextToken();
				int index = strex.indexOf("=");

				rowcellData[i] = strex.substring(index + 1);
				Log.i("web", rowcellData[i] + "index is " + i);
				i++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return rowcellData;
	}

	// till modified
	public static String getResponse(SoapObject soap) {
		if(soap!=null){
		return soap.getProperty(0).toString();
		}
		else {
			return null;
		}

	}


public static String getHmac(String randomString, String salt) throws Exception{
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec(salt.getBytes(), "HmacSHA1"));
    byte[] bs = mac.doFinal(randomString
.getBytes());
    return byteArrayToHex(bs); // use your favorite hex converter
}

public static String byteArrayToHex(byte[] a) {
       StringBuilder sb = new StringBuilder();
       for(byte b: a)
          sb.append(String.format("%02x", b&0xff));
       Log.i("HasSHA256",sb.toString());
       return sb.toString();
    }
public static String randomString( int len ) 
{  rnd = new Random();
   StringBuilder sb = new StringBuilder( len );
   for( int i = 0; i < len; i++ ) 
      sb.append( RENDOM_ALPHA_NUMERIC.charAt( rnd.nextInt(RENDOM_ALPHA_NUMERIC.length()) ) );
   return sb.toString();
}
}
