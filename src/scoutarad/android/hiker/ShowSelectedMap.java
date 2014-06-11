package scoutarad.android.hiker;

import java.util.ArrayList;
import java.util.List;

import scoutarad.android.hiker.database.MapPoint;
import scoutarad.android.hiker.database.MapPointsHandler;
import scoutarad.android.hiker.database.MapsHandler;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class ShowSelectedMap extends Activity {
	
	private static double LAST_LAT;
	private static double LAST_LONG; 
	
	GoogleMap Map;
	
	private String MapName;
	MapPointsHandler db = new MapPointsHandler(this);
	MapsHandler mapDatabase = new MapsHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_selected_map);
		
		initilizeMap();
		
		MapName = getIntent().getExtras().getString("MapName");
		TextView time = (TextView) findViewById(R.id.showMapTime);
		TextView distance = (TextView) findViewById(R.id.showMapDistance);
		
		List<String> dt = new ArrayList<String>();
		dt = mapDatabase.getMap(MapName);
		time.setText(dt.get(0));
		distance.setText(dt.get(1));
		
		renderTrack();
		
	}
	
    private void initilizeMap() 
    {
        if (Map == null) 
        {
        	Map = ((MapFragment) getFragmentManager().findFragmentById(R.id.showMapFragment)).getMap();
            // check if map is created successfully or not
            if (Map == null) 
                Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
        }
    }	
    
    private void renderTrack()
    {
    	List<MapPoint> MapPoints = new ArrayList<MapPoint>();
		MapPoints = db.getAllPointsForMap(MapName);
		
		MapPoint firstPoint = MapPoints.get(0);
		double lat = firstPoint.getLatitude();
		double lng = firstPoint.getLongitude();
		LAST_LAT = lat;
		LAST_LONG = lng;
		
		LatLng startLocation = new LatLng(lat, lng);

		
        Map.addMarker(new MarkerOptions()
        .position(startLocation)
        .title("Start")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))); 
        
		Map.animateCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 15)); 
		
		for(int i=1;i<MapPoints.size();i++)
		{
			LatLng first = new LatLng(LAST_LAT, LAST_LONG);
			
			MapPoint currentPoint = MapPoints.get(i);
			double latitude = currentPoint.getLatitude();
			double longitude = currentPoint.getLongitude();
			LAST_LAT = latitude;
			LAST_LONG = longitude;
			
			LatLng second = new LatLng(latitude, longitude);
			
			Map.addPolyline(new PolylineOptions().add(first, second).width(5).color(Color.RED));
		}
		
		LatLng finishPosition = new LatLng(LAST_LAT, LAST_LONG);
		
		Map.addMarker(new MarkerOptions()
        .position(finishPosition)
        .title("Finish")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))); 
		
    }
}
