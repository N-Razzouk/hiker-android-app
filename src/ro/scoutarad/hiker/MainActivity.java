package ro.scoutarad.hiker;

import scoutarad.android.hiker.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }
    
    public void onNewTrackClick (View view) 
    {
	    checkStatus();
    	
    	Intent intent = new Intent(this, NewMap.class);
    	startActivity(intent);
	}
    
    public void onListTracksClick(View view)
    {
	    checkStatus();

    	Intent intent = new Intent(this, ListTracks.class);
    	startActivity(intent);
    }
    
    public boolean checkStatus(){
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
	    boolean enabledGPS = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    boolean enabledWiFi = service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	    
	    if (!enabledGPS) 
	    {
	    	Toast.makeText(MainActivity.this, "GPS signal not found!", Toast.LENGTH_LONG).show();
	        return false;
	    }
        if(!enabledWiFi || !isOnline())
        {
        	Toast.makeText(MainActivity.this, "Network signal not found!", Toast.LENGTH_LONG).show();
        	return false;
        }   
        
        return true;
    }
    
    public boolean isOnline() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }   
}
