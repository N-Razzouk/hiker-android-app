package scoutarad.android.hiker;

public class Hiker {
	
	public static double calculateDistance(double startLatitude, 
			double startLongitude, 
			double endLatitude, 
			double endLongitude, String unit) 
	{
	      double Theta = startLongitude - endLongitude;
	      double distance = Math.sin(DegreesToRadians(startLatitude)) 
	    		  * Math.sin(DegreesToRadians(endLatitude)) 
	    		  + Math.cos(DegreesToRadians(startLatitude)) 
	    		  * Math.cos(DegreesToRadians(endLatitude)) 
	    		  * Math.cos(DegreesToRadians(Theta));
	      distance = Math.acos(distance);
	      distance = RadiansToDegrees(distance);
	      distance = distance * 60 * 1.1515;
	      if (unit == "Km") {
	        distance = distance * 1.609344;
	      } 
	      else if (unit == "Mi") {
	        distance = distance * 0.8684;
	      }
	      else if (unit == "m"){
	    	  distance = (distance * 1.609344) * 1000;
	      }
	      return (distance);
	}

	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	    /*::  This function converts decimal degrees to radians             :*/
	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double DegreesToRadians(double deg) 
    {
      return (deg * Math.PI / 180.0);
    }

	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	    /*::  This function converts radians to decimal degrees             :*/
	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double RadiansToDegrees(double rad)
    {
      return (rad * 180.0 / Math.PI);
    }
    
    public static String getTime(long miliseconds) {       
        long elapsedTime = miliseconds;  
        String format = String.format("%%0%dd", 2);  
        elapsedTime = elapsedTime / 1000;  
        String seconds = String.format(format, elapsedTime % 60);  
        String minutes = String.format(format, (elapsedTime % 3600) / 60);  
        String hours = String.format(format, elapsedTime / 3600);
        String time =  hours + ":" + minutes + ":" + seconds;  
        return time;  
    }  
}
