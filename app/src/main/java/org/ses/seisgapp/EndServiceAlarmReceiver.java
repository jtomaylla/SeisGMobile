package org.ses.seisgapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

// make sure we use a WakefulBroadcastReceiver so that we acquire a partial wakelock
public class EndServiceAlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "StartServiceAlarmReceiver";
    private static PowerManager.WakeLock wakeLock = null;
    private static final String LOCK_TAG = "org.ses.seisgapp";

    /**
     * Method used to share the <code>WakeLock</code> created by this
     * <code>BroadcastReceiver</code>.
     * Note: this method is <code>synchronized</code> as it lazily creates
     * the <code>WakeLock</code>
     *
     * @param 		ctx			The <code>Context</code> object acquiring the
     * 							lock.
     */
    public static synchronized void acquireLock(Context ctx){
        if (wakeLock == null){
            PowerManager mgr = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
            wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, LOCK_TAG);
            wakeLock.setReferenceCounted(true);
        }
        wakeLock.acquire();
    }

    /**
     * Method used to release the shared <code>WakeLock</code>.
     */
//    public static synchronized void releaseLock(){
//        if (wakeLock != null){
//            wakeLock.release();
//        }
//    }

    /* (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        acquireLock(context);
        //context.startService(new Intent(context, LocationService.class));
        Log.i(TAG, "context.endService(context)");
        //context.startService(new Intent(context, GpsLocationService.class));
        //GpsTrackerActivity gpsActivity = new GpsTrackerActivity();
        //gpsActivity.initService();
        AppStatus.getInstance(context).endService(context);

    }
}
