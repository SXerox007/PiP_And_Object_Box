package com.conflux.coinflux.demoobjectbox.Constants;

/**
 * Developer: SUMIT_THAKUR
 * Dated: 05/07/17.
 */
public interface AppConstants {

    String ACTION_MEDIA_CONTROL = "media_control";

    /**
     * Intent extra for media controls from Picture-in-Picture mode.
     */
    String EXTRA_CONTROL_TYPE = "control_type";

    /**
     * The request code for play action PendingIntent.
     */
    int REQUEST_PLAY = 1;

    /**
     * The request code for pause action PendingIntent.
     */
    int REQUEST_PAUSE = 2;

    /**
     * The request code for info action PendingIntent.
     */
    int REQUEST_INFO = 3;

    /**
     * The intent extra value for play action.
     */
    int CONTROL_TYPE_PLAY = 1;

    /**
     * The intent extra value for pause action.
     */
    int CONTROL_TYPE_PAUSE = 2;
}
