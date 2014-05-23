package scoutarad.android.hiker;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
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
    
    public void onClick (View view) 
    {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
	    boolean enabledGPS = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    boolean enabledWiFi = service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	    
	    if (!enabledGPS) 
	    {
	    	Toast.makeText(MainActivity.this, "GPS signal not found", Toast.LENGTH_LONG).show();
	        return;
	    }
        if(!enabledWiFi)
        {
        	Toast.makeText(MainActivity.this, "Network signal not found", Toast.LENGTH_LONG).show();
        	return;
        }
	    
    	Intent intent = new Intent(this, NewTrack.class);
    	startActivity(intent);
    	Toast toast = Toast.makeText(getApplicationContext(), "Generating new track...", Toast.LENGTH_SHORT);
    	toast.show();
    	
    	
    	
	}
}
