package com.example.marat.someapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.marat.someapplication.Http.HttpClient;

public class WeatherActivity extends AppCompatActivity {

	SQL sql;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);

		final ProgressDialog progDialog = new ProgressDialog(this);
		progDialog.setMessage("Loading");
		progDialog.setIndeterminate(false);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		sql = new SQL(this, progDialog);

		final TextView nameOfcity = (TextView) findViewById(R.id.textView9);
		final TextView discOfcity = (TextView) findViewById(R.id.textView2);
		final TextView tempOfcity = (TextView) findViewById(R.id.tempofcity);
		final TextView pressOfcity = (TextView) findViewById(R.id.textView4);
		final TextView humOfcity = (TextView) findViewById(R.id.textView6);
		final TextView speedOfcity = (TextView) findViewById(R.id.textView8);
		final TextView time = (TextView) findViewById(R.id.textView10);

		//JsonParser contains all needed methods for working with json
		//final JsonParser jsonParser = new JsonParser();

		Intent intent = getIntent();
		final int id = (int) intent.getLongExtra("CITY_NUM", 0);
		intent.getStringExtra("CITY_NAME");

		//get response and data from http
		HttpClient httpClient = new HttpClient(this, progDialog) {
			@Override
			public void getData(String data, String status) {
				DataOfCity city = new DataOfCity(data);
				sql.updateCity(city);

				//sets all textviews
				nameOfcity.setText(city.getName() + ", " + city.getCountry());
				discOfcity.setText(city.getSky());
				tempOfcity.setText(city.getTemp() + "°C");
				pressOfcity.setText(city.getPress() + "mm Hg");
				humOfcity.setText(city.getHum() + "%");
				speedOfcity.setText(city.getSpeed() + "m/s");
				time.setText(city.getTime());
			}
		};
		//make request to get data for opened city
		if (!httpClient.getHttp(MainActivity.nameTemp[0][id], null, this)) {
			Cursor cursor = sql.getCityByName(MainActivity.nameTemp[0][id]);
			System.out.println(cursor.getCount());
			int i = cursor.getCount();
			if (i != 0) {
				nameOfcity.setText(cursor.getString(0) + ", " + cursor.getString(1));
				discOfcity.setText(cursor.getString(2));
				tempOfcity.setText(cursor.getString(3) + "°C");
				pressOfcity.setText(cursor.getString(4) + "mm Hg");
				humOfcity.setText(cursor.getString(5) + "%");
				speedOfcity.setText(cursor.getString(6) + "m/s");
				time.setText(cursor.getString(7));
			} else {
				nameOfcity.setText(MainActivity.nameTemp[0][id]+", " + "N/A");
				discOfcity.setText("n/a");
				tempOfcity.setText(0 + "°C");
				pressOfcity.setText(0 + "mm Hg");
				humOfcity.setText(0 + "%");
				speedOfcity.setText(0 + "m/s");
				time.setText("n/a");
			}
		}
	}
}


