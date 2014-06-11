package scoutarad.android.hiker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import scoutarad.android.hiker.database.MapsHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ListTracks extends Activity {

	MapsHandler db = new MapsHandler(this);
	ListView list;
	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_tracks);
		
		generateMapList();

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Options");
		menu.add(1, 1, 1, "Details");
		menu.add(1, 2, 2, "Delete");

	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		if(item.getTitle() == "Delete")
		{
			AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item.getMenuInfo();
			String MapName = (String) list.getItemAtPosition(aInfo.position);
			
			db.deleteMap(MapName);
			adapter.remove(MapName);
			adapter.notifyDataSetChanged();	
		}
		return true;
	}
	
	public void generateMapList()
	{
		List<String> MapList = new ArrayList<String>();
		MapList = db.getAllMaps();
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MapList);
		list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(adapter);
		
		registerForContextMenu(list);
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), ShowSelectedMap.class);
				intent.putExtra("MapName",(String) parent.getItemAtPosition(position));
		    	startActivity(intent);
			}
		});		
	}
}
