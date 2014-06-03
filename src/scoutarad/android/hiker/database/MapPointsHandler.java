package scoutarad.android.hiker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MapPointsHandler extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "Maps";
	private static final int DATABASE_VERSION = 1;
	
	//TABLE NAME
	private static final String TABLE_NAME = "MapPoints";
	
	//TABLE COLUMNS
	private static final String COLUMN_ID = "ID";
	private static final String COLUMN_MAPNAME = "MapName";
	private static final String COLUMN_POINTNUMBER = "PointNumber";
	private static final String COLUMN_LATITUDE = "Latitude";
	private static final String COLUMN_LONGITUDE = "Longitude";

	//SUPER CONSTRUCTOR
	public MapPointsHandler(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = 
				"CREATE TABLE " 
				+ TABLE_NAME
				+ "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_MAPNAME + " TEXT, "
				+ COLUMN_POINTNUMBER + " INTEGER, "
				+ COLUMN_LATITUDE + " TEXT, "
				+ COLUMN_LONGITUDE + " TEXT"
				+ ");";
				
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);		
	}
	
	//TODO ADD addMapPoint, getMapPointsForMap
}
