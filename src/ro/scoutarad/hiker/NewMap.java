package ro.scoutarad.hiker;

import java.util.ArrayList;
import java.util.List;

import ro.scoutarad.hiker.database.MapsHandler;
import scoutarad.android.hiker.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewMap extends Activity {

	MapsHandler db = new MapsHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_map);
	}
	
	public void onNewClick(View view){
		
		final EditText textfield = (EditText) findViewById(R.id.newTrackEditText);		
		int identic = 0;
		
		List<String> MapList = new ArrayList<String>();
		MapList = db.getAllMaps();
		
		for (String map : MapList) {
			if(map.equals(textfield.getText().toString()))
				identic = 1;
		}
		
		if(TextUtils.isEmpty(textfield.getText().toString()))
			Toast.makeText(getApplicationContext(), "Please enter a map name!", Toast.LENGTH_LONG).show();
		else
		{
			if(identic==0)
			{
				db.addMap(textfield.getText().toString());
				
				Intent intent = new Intent(this, NewTrack.class);
				intent.putExtra("MapName", textfield.getText().toString());
		    	startActivity(intent);
		    	
		    	Toast.makeText(getApplicationContext(), "Generating new track...", Toast.LENGTH_LONG).show();				
			}
			else
				Toast.makeText(getApplicationContext(), "This name already exists!", Toast.LENGTH_LONG).show();

		}
	}
}
