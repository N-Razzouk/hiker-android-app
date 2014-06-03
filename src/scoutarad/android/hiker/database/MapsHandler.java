package scoutarad.android.hiker.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MapsHandler extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "Maps";
	private static final int DATABASE_VERSION = 1;
	
	//TABLE NAME
	private static final String TABLE_NAME = "Maps";
	
	//TABLE COLUMNS
	private static final String COLUMN_ID = "ID";
	private static final String COLUMN_NAME = "Name";
	
	//SUPER CONSTRUCTOR
	public MapsHandler(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = 
				"CREATE TABLE " 
				+ TABLE_NAME
				+ "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_NAME + " TEXT"
				+ ");";
		db.execSQL(CREATE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DELETE TABLE IF EXISTS " + TABLE_NAME);
	}
		
	public void addMap(String MapName){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(COLUMN_NAME, MapName);
		db.insert(TABLE_NAME, null, values);
		db.close();
	}
	
	public List<String> getAllMaps(){
		List<String> MapList = new ArrayList<String>();
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst())
			do{
				MapList.add(cursor.getString(1));
			}while(cursor.moveToNext());
		
		return MapList;
	}
		
}