package scoutarad.android.hiker;

import java.util.ArrayList;
import java.util.List;

import scoutarad.android.hiker.database.MapsHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ListTracks extends Activity {

	MapsHandler db = new MapsHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_tracks);
		
		List<String> MapList = new ArrayList<String>();
		MapList = db.getAllMaps();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MapList);
		ListView list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int arg2,
					long arg3) {
				Intent intent = new Intent(getApplicationContext(), ShowSelectedMap.class);
		    	startActivity(intent);
				
			}

			
		});
		
		//add listview listener
	}


}
