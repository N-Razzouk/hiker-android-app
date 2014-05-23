package scoutarad.android.hiker;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;


public class NewTrack extends Activity {

	private static final float MIN_DISTANCE = 10;// 40 meters
    private static final long MIN_TIME = 1000 * 10;

    private static double LAST_LOC_LATITUDE;
    private static double LAST_LOC_LONGITUDE;
	    
	GoogleMap newMap;
	private LocationManager locationManager;
	android.location.LocationListener listener;
    Marker startPerc;
    private String provider;
    private int firstuse=1;
    private int firstLoc=1;
    private long lastPause;
    
    ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_track);
		
	    final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.newTrackToggleButton);
	    final Chronometer timer = (Chronometer) findViewById(R.id.newTrackClock);
	    Button stopButton = (Button) findViewById(R.id.newTrackStopButton);
	    progressBar = (ProgressBar) findViewById(R.id.newTrackProgressBar);
		//render map. TODO: add try/catch expression		
	    initilizeMap();
	    toggleButton.setChecked(false);
	    //START / PAUSE
	    toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
				{
					if(firstuse==1)
					{		
						progressBar.setVisibility(ProgressBar.VISIBLE);
						progressBar.setProgress(0);
						Toast.makeText(getApplicationContext(), "Please wait! Initializing...", Toast.LENGTH_LONG).show();
						startTracking();
						timer.setBase(SystemClock.elapsedRealtime());
						timer.start();
					}
					else
					{
						timer.setBase(timer.getBase() + SystemClock.elapsedRealtime() - lastPause);
						timer.start();
					}
					firstuse=0;
				}
				else if(firstuse!=1)
				{
					toggleButton.setChecked(true);
					lastPause = SystemClock.elapsedRealtime();
					timer.stop();
				}
				
			}
		});
	    stopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				timer.stop();
		        if(locationManager != null){
		            locationManager.removeUpdates(listener);
		        }
			}
		});
	   
	}
    public void addStartPointer(Location location) {

        double lat =  location.getLatitude();
        double lng = location.getLongitude();

        LatLng coordinate = new LatLng(lat, lng);

        startPerc = newMap.addMarker(new MarkerOptions()
             .position(coordinate)
             .title("Start")
             .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));  
        
        newMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 15)); 
    }
    
    private void initilizeMap() 
    {
        if (newMap == null) 
        {
        	newMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.newMap)).getMap();

            // check if map is created successfully or not
            if (newMap == null) 
            {
                Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }
        }

    }
    
    private void startTracking()
    {
    	//LOCATION MANAGER
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
    	listener = new android.location.LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			
			if(firstLoc==1)
			{
				progressBar.setVisibility(ProgressBar.GONE);
				LAST_LOC_LATITUDE = location.getLatitude();
				LAST_LOC_LONGITUDE = location.getLongitude();
				addStartPointer(location);
				firstLoc=0;
			}
			else
			{
				LatLng previousLoc = new LatLng(LAST_LOC_LATITUDE, LAST_LOC_LONGITUDE);
					
				double lat =  location.getLatitude();
		        double lng = location.getLongitude();
		        LatLng currentLoc = new LatLng(lat, lng);
		        LAST_LOC_LATITUDE=lat;
		        LAST_LOC_LONGITUDE=lng;
		        
		        Polyline polyline = newMap.addPolyline(new PolylineOptions().add(previousLoc, currentLoc).width(5).color(Color.RED));
		        
		        //newMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 15));
		        //writeon
		        Toast.makeText(getApplicationContext(), "Line Added!", Toast.LENGTH_SHORT).show();
			}
			}
		@Override
		public void onProviderDisabled(String provider) {}
		@Override
		public void onProviderEnabled(String provider) {}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}
		};
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, listener);	
		
		
    }
}
