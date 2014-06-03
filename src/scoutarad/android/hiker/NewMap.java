package scoutarad.android.hiker;

import scoutarad.android.hiker.database.MapsHandler;
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
		
		if(TextUtils.isEmpty(textfield.getText().toString()))
			Toast.makeText(getApplicationContext(), "Please enter a map name!", Toast.LENGTH_LONG).show();
		else
		{
			db.addMap(textfield.getText().toString());
			Intent intent = new Intent(this, NewTrack.class);
	    	startActivity(intent);
	    	Toast.makeText(getApplicationContext(), "Generating new track...", Toast.LENGTH_LONG).show();
		}
	}
}
