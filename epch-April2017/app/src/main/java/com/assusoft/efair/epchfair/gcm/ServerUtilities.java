package com.assusoft.efair.epchfair.gcm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.util.Log;

import com.epch.efair.delhifair.R;


public final class ServerUtilities {
	private static final int MAX_ATTEMPTS = 100;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();
 //
    /**
     * Register this account/device pair within the server.
     *
     */
    public static int register(final Context context, String name, String email, final String regId) {
        Log.i(CommonUtilities.TAG, "registering device (regId = " + regId + ")");
        String serverUrl = CommonUtilities.SERVER_URL;
        Map<String, String> params = new HashMap<String, String>();
       
        params.put("name", name);
        params.put("email", email);
        params.put("regId", regId);
        
       
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register on our server
        // As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(CommonUtilities.TAG, "Attempt #" + i + " to register");
            try {
              
                String resp= post(serverUrl, params);
                resp=resp!=null?resp:" ";
               // GCMRegistrar.setRegisteredOnServer(context, true);
                if(resp.equals("pass")){
                	return 1;
                }
                
            } catch (IOException e) {
                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.e(CommonUtilities.TAG, "Failed to register on attempt " + i + ":" + e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Log.d(CommonUtilities.TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d(CommonUtilities.TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return 0;
                }
                // increase backoff packplusnentially
                backoff *= 2;
            }
        }
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
        
        return 0;
       // CommonUtilities.displayMessage(context, message);
    }

    /**
     * Unregister this account/device pair within the server.
     */
    static void unregister(final Context context, final String regId) {
        
        try {
          //  post(serverUrl, params);
         //   GCMRegistrar.setRegisteredOnServer(context, false);
            String message = context.getString(R.string.server_unregistered);
           // CommonUtilities.displayMessage(context, message);
            SoapObject request=new SoapObject(CommonUtilities.NAME_SPACE,CommonUtilities.UNREGISTER_USER);
            PropertyInfo pi=new PropertyInfo();
    		pi.setName("regId");
    		pi.setType(String.class);
    		pi.setValue(regId);
    		request.addProperty(pi);
    		
    		SoapObject response = null;
    		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
    				SoapEnvelope.VER11);
    		//envelope.dotNet = true;
    		envelope.setOutputSoapObject(request);
    		HttpTransportSE androidHttpTransport = new HttpTransportSE(
    				CommonUtilities.SERVER_URL);
    		try {
    			androidHttpTransport.call(CommonUtilities.NAME_SPACE + CommonUtilities.UNREGISTER_USER, envelope);
    			response = (SoapObject) envelope.bodyIn;
    			String resp=response.getProperty(0).toString();
    			Log.i("RESP", resp);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		
    		
        } catch (Exception e) {
            // At this point the device is unregistered from GCM, but still
            // registered in the server.
            // We could try to unregister again, but it is not necessary:
            // if the server tries to send a message to the device, it will get
            // a "NotRegistered" error message and should unregister the device.
            String message = context.getString(R.string.server_unregister_error,
                    e.getMessage());
          //  CommonUtilities.displayMessage(context, message);
        }
    }

    
    private static String  post(String endpoint, Map<String, String> params)
            throws IOException {   	
    	JSONObject json=new JSONObject(params);
    	String userData=json.toString();
    	Log.i("RESP",userData);
    	String serverResponse=null;
        SoapObject request=new SoapObject(CommonUtilities.NAME_SPACE,CommonUtilities.SUBMIT_USER_DETAILS);
        PropertyInfo pi=new PropertyInfo();
		pi.setName("data");
		pi.setType(String.class);
		pi.setValue(userData);
		request.addProperty(pi);
		
		
		SoapObject response = null;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.implicitTypes = false;
		envelope.dotNet=false;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				CommonUtilities.SERVER_URL);
		try {
			androidHttpTransport.call(CommonUtilities.NAME_SPACE+CommonUtilities.SUBMIT_USER_DETAILS, envelope);
			response = (SoapObject) envelope.bodyIn;
			if(response!=null){
				serverResponse=response.getProperty(0).toString();
				JSONObject jsonResp=new JSONObject(serverResponse);
				serverResponse=jsonResp.getString("status");
				}else{
					serverResponse="fail";
				}
			Log.i("RESP", serverResponse);
			//or we can use
			//response=(SoapObject) envelope.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return serverResponse;
        
      }
}
