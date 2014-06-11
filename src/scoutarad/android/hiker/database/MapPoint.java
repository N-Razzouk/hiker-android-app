package scoutarad.android.hiker.database;

public class MapPoint {

	String _MapName;
	int _PointNumber;
	double _Latitude;
	double _Longitude;
	
	//empty
	MapPoint(){
		
	}
	
	public MapPoint(String MapName, int PointNumber, double Latitude, double Longitude)
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
	public double getLatitude(){
		return this._Latitude;
	}
	public void setLatitude(double Latitude){
		this._Latitude = Latitude;
	}
	
	//Longitude
	public double getLongitude(){
		return this._Longitude;
	}
	public void setLongitude(double Longitude){
		this._Longitude = Longitude;
	}
	
}
