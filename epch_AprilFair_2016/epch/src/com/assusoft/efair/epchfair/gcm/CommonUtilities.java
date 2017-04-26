package com.assusoft.efair.epchfair.gcm;

import com.assusoft.eFairEmall.util.Util;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {
	
	// give your server registration url here
    static final String SERVER_URL =Util.BASE_URL; 
    static final String SUBMIT_USER_DETAILS=Util.REGISTER_GCM_USER;
    static final String NAME_SPACE=Util.NAMESPACE;
    static final String UNREGISTER_USER="removeUnregisteredUsers";

    // Google project id
    
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String SERVER_REGISTRATION_STATUS="server_registration_status";
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    public static String SENDER_ID ="919643059845";

    /**
     * Tag used on log messages.
     */
    public static final String TAG = "Assurance GCM Demo";
    

    /**AIzaSyBa75v9q6YXdHqtZfN-tOht-0KPlbzJpeI
     * Tag used on log messages.
     */
   

   /* static final String DISPLAY_MESSAGE_ACTION =
            "com.androidhive.pushnotifications1.DISPLAY_MESSAGE";

   
    
    

    *//**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     *//*
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }*/
}
