package scoutarad.android.hiker.database;

public class MapPoint {

	String _MapName;
	int _PointNumber;
	long _Latitude;
	long _Longitude;
	
	//empty
	MapPoint(){
		
	}
	
	MapPoint(String MapName, int PointNumber, long Latitude, long Longitude)
	{
		this._MapName = MapName;
		this._PointNumber = PointNumber;
		this._Latitude = Latitude;
		this._Longitude = Longitude;
	}
	
	//MapName
	public String getMapName(){
		return this._MapName;
	}
	public void setMapName(String MapName){
		this._MapName = MapName;
	}
	
	//PointNumber
	public int getPointNumber(){
		return this._PointNumber;
	}
	public void setPointNumber(int PointNumber){
		this._PointNumber = PointNumber;
	}
	
	//Latitude
	public long getLatitude(){
		return this._Latitude;
	}
	public void setLatitude(long Latitude){
		this._Latitude = Latitude;
	}
	
	//Longitude
	public long getLongitude(){
		return this._Longitude;
	}
	public void setLongitude(long Longitude){
		this._Longitude = Longitude;
	}
	
}
