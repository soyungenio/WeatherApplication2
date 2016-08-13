package com.example.marat.someapplication.Http;
/*
	This class makes a url for a request.
*/
public class UrlMaker {
	static public String getUrlByCityName(String CityName){
		CityName = CityName.replace(" ","_");
		return "http://api.openweathermap.org/data/2.5/weather?q=" + CityName + "&APPID=21b803990b251380632b16cabd4f36ce&units=metric";
	} 
}
