package ro.scoutarad.hiker.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MapPointsHandler extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "MapPoints";
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
				+ COLUMN_LATITUDE + " REAL, "
				+ COLUMN_LONGITUDE + " REAL"
				+ ");";
				
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);		
	}
	
	public void addMapPoint(MapPoint mapPoint) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(COLUMN_MAPNAME, mapPoint.getMapName());
		values.put(COLUMN_POINTNUMBER, mapPoint.getPointNumber());
		values.put(COLUMN_LATITUDE, mapPoint.getLatitude());
		values.put(COLUMN_LONGITUDE, mapPoint.getLongitude());
		
		db.insert(TABLE_NAME, null, values);
		db.close();
	}
	
	public List<MapPoint> getAllPointsForMap(String MapName) {
		List<MapPoint> MapPoints = new ArrayList<MapPoint>();
		
		String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_MAPNAME + "='" + MapName + "';";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(sqlQuery, null);
		if(cursor.moveToFirst())
			do{
				MapPoint mp = new MapPoint();
				mp.setMapName(cursor.getString(1));
				mp.setPointNumber(cursor.getShort(2));
				mp.setLatitude(cursor.getDouble(3));
				mp.setLongitude(cursor.getDouble(4));
				MapPoints.add(mp);
			}while(cursor.moveToNext());
		return MapPoints;
	}
	
}
