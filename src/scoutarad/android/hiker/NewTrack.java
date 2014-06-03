package scoutarad.android.hiker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;


public class NewTrack extends Activity implements 
	GooglePlayServicesClient.ConnectionCallbacks,
	GooglePlayServicesClient.OnConnectionFailedListener,
	LocationListener {

	private static final float MIN_DISTANCE = 20; //Meters
    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 1000;

    private static double LAST_LOC_LATITUDE;
    private static double LAST_LOC_LONGITUDE;
	    
    private int firstuse=1;
    private int currentLocation=0;
    private long lastPause;
    
    private LocationClient locationClient;
    private LocationRequest locationRequest;
	
    GoogleMap newMap;
    Marker startMarker;
    Marker endMarker;
    ProgressBar progressBar;
    ToggleButton toggleButton;
    Chronometer timer;
    Button stopButton;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_track);
	    
		progressBar = (ProgressBar) findViewById(R.id.newTrackProgressBar);
	    toggleButton = (ToggleButton) findViewById(R.id.newTrackToggleButton);
	    timer = (Chronometer) findViewById(R.id.newTrackClock);
	    stopButton = (Button) findViewById(R.id.newTrackStopButton);

	    //render map. TODO: add try/catch expression
	    initilizeMap();	    
	    
	    //Fused Location Client + Request
	    locationClient = new LocationClient(this,this,this);
		locationRequest=LocationRequest.create();
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		locationRequest.setInterval(UPDATE_INTERVAL);
		locationRequest.setFastestInterval(FASTEST_INTERVAL);

	    //START / PAUSE
	    toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
				{
					if(firstuse==1)
					{		
						//First Location [using FUSED_LOCATION]
						Location location = locationClient.getLastLocation();
						addStartPointer(location);
						//Timer
						timer.setBase(SystemClock.elapsedRealtime());
						timer.start();
						firstuse=0;
					}
					else
					{
						timer.setBase(timer.getBase() + SystemClock.elapsedRealtime() - lastPause);
						timer.start();
					}
					//Start Location Updates [using FUSED_LOCATION]
					locationClient.requestLocationUpdates(locationRequest, NewTrack.this);
				}
				else
				{
					lastPause = SystemClock.elapsedRealtime();
					timer.stop();
					if(locationClient.isConnected())
						locationClient.removeLocationUpdates(NewTrack.this);
				}
				
			}
		});
	    stopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				timer.stop();
				if(locationClient.isConnected())
					locationClient.removeLocationUpdates(NewTrack.this);
				locationClient.disconnect();
				
				LatLng endLocation = new LatLng(LAST_LOC_LATITUDE, LAST_LOC_LONGITUDE);
				endMarker = newMap.addMarker(new MarkerOptions()
	             .position(endLocation)
	             .title("Start")
	             .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
				toggleButton.setEnabled(false);
			}
		});
	   
	}
	
	@Override
	public void onLocationChanged(Location location) 
	{
			LatLng previousLoc = new LatLng(LAST_LOC_LATITUDE, LAST_LOC_LONGITUDE);
			currentLocation++;	
			
			double lat =  location.getLatitude();
	        double lng = location.getLongitude();
	        LatLng currentLoc = new LatLng(lat, lng);
	        LAST_LOC_LATITUDE=lat;
	        LAST_LOC_LONGITUDE=lng;

	        @SuppressWarnings("unused")
			Polyline polyline = newMap.addPolyline(new PolylineOptions().add(previousLoc, currentLoc).width(5).color(Color.RED));
	        
	        //newMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 15));
	        //writeon
	        Toast.makeText(getApplicationContext(), "Line Added - " + currentLocation, Toast.LENGTH_SHORT).show();
	}	
	
	@Override
	protected void onStart()
	{
	    toggleButton.setChecked(false);	  
		locationClient.connect();
		super.onStart();
	}
	
	@Override
	protected void onStop()
	{
		if(locationClient.isConnected())
			locationClient.removeLocationUpdates(this);
		locationClient.disconnect();
		super.onStop();
	}
	
    private void initilizeMap() 
    {
        if (newMap == null) 
        {
        	newMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.newMapFragment)).getMap();
            // check if map is created successfully or not
            if (newMap == null) 
                Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
        }

    }	
    
    public void addStartPointer(Location location) {

        double lat =  location.getLatitude();
        double lng = location.getLongitude();
		LAST_LOC_LATITUDE = lat;
		LAST_LOC_LONGITUDE = lng;

        LatLng startLocation = new LatLng(lat, lng);

        startMarker = newMap.addMarker(new MarkerOptions()
             .position(startLocation)
             .title("Start")
             .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));  
        
        newMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 15)); 
    }

	@Override
	public void onConnectionFailed(ConnectionResult result) {}
	@Override
	public void onConnected(Bundle bundle) {}
	@Override
	public void onDisconnected() {}

}
