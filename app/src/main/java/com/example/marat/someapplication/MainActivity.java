package com.example.marat.someapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marat.someapplication.Http.HttpClient;
import com.example.marat.someapplication.Ui.CustomAdapter;

public class MainActivity extends AppCompatActivity {

    SQL sql;
    HttpClient httpClient;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Dialog dialogremove;
    Dialog dialogadd;
    EditText cityName;
    ListView listOfCities;
    Intent intent;
    ProgressDialog progDialog;
    static String[][] nameTemp;
    int numtoremove;
    DialogInterface.OnClickListener click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        ////////////////////////////////////////////////////////////////////////////
        new StartAsync(this).execute();
    }
    //method for button add, opens dialog
    public void Add(View view){
        dialogadd.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialogadd.show();
    }
    public void updateList(){
        if(sql.checkFirstStarting()){
            System.out.println("yes, it's written");
            nameTemp = new String[][]{{"Kazan", "Moscow","Sochi","Paris","Berlin"},
                                      {"0","0","0","0","0"}} ;
           // CustomAdapter cusAdapt = new CustomAdapter(this,nameTemp);/////////????????????
           // listOfCities.setAdapter(cusAdapt);///////????????????????
        }
        else{
            System.out.println("no need to write");
            nameTemp = sql.readForList();
            CustomAdapter cusAdapt = new CustomAdapter(this,nameTemp);
            listOfCities.setAdapter(cusAdapt);
        }
    }
    public void update() {
        if(httpClient.isOnline(this)){
            if(sql.checkFirstStarting())
                sql.first();
            //make request for all cities in array
            for(int i=0;i<nameTemp[0].length;i++){
                httpClient.getHttp(nameTemp[0][i],"update",this);
            }
            if(nameTemp[0].length<1){
                progDialog.dismiss();
            }
        }else {
            if(sql.checkFirstStarting()){
                CustomAdapter cusAdapt = new CustomAdapter(this,nameTemp);/////////????????????
                listOfCities.setAdapter(cusAdapt);///////????????????????
            }
            progDialog.dismiss();
            Toast.makeText(this, "Network conection filed", Toast.LENGTH_SHORT).show();
        }
    }
    public class StartAsync extends AsyncTask<String, Void, Void>
    {
        private MainActivity activity;
        StartAsync(MainActivity activity){
            this.activity = activity;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progDialog = new ProgressDialog(activity);
            progDialog.setMessage("Loading");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.show();

            ///////////////////////////////////////For removing
            //This dialog for deleting a city
            dialogremove = new AlertDialog.Builder(activity)
                    .setTitle("Menu").setItems(new String[]{"Remove"}, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dlg, int position)
                        {
                            if ( position == 0 )
                            {
                                //this method specifies num of city and delete it
                                sql.deleteCity(nameTemp[0][numtoremove]);
                                updateList();
                            }
                        }
                    }).create();

            //this edittext to get city name, edittext is inside of the dialog
            cityName = new EditText(activity);
        }
        @Override
        protected Void doInBackground(String... strings) {
            //Swipe for refresh
            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
            sql = new SQL(activity, progDialog);

            //////////////////////////////////////////////////////////////////////////////////Main listview
            intent = new Intent(activity, WeatherActivity.class);
            //This is main listview containing all the cities
            listOfCities = (ListView) findViewById(R.id.cities);
            ///////////////////////////////////////////////////////////////////////////////////////////

            /////////////////////////////////////////////////////////////////////The dialog for adding new city
            //This listener for the dialog to add city
            click = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case Dialog.BUTTON_POSITIVE:
                            //Check if internet connection is available
                            if(httpClient.isOnline(MainActivity.this)){
                                if(!cityName.getText().toString().matches("")){
                                    //makes request for weather data for city
                                    httpClient.getHttp(cityName.getText().toString(),"onedata",MainActivity.this);
                                    cityName.setText("");}
                                else
                                    Toast.makeText(MainActivity.this, "Field is empty", Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(MainActivity.this, "Network conection filed", Toast.LENGTH_SHORT).show();
                            break;
                        case Dialog.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            httpClient = new HttpClient(activity, progDialog){
                @Override
                public void getData(String data, String status){
                            DataOfCity city = new DataOfCity(data);
                            sql.updateCity(city);
                            if(status=="onedata") {
                                updateList();
                                progDialog.dismiss();
                            }else{
                                if (nameTemp[0][nameTemp[0].length - 1].equals(city.getName())) {
                                    updateList();
                                    progDialog.dismiss();
                                    System.out.println("The list has updated");
                                }
                            }
                }
            };
            //This method updates listview
            System.out.println("Before updatelist");
            updateList();
            System.out.println("after updatelist");
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            super.onPostExecute(res);

            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
                @Override
                public void onRefresh() {
                    mSwipeRefreshLayout.setRefreshing(false);
                    //This method updates information about of all the cities
                    update();
                }
            });
            //////////////////////////////////////////////////////////////////////////////////Main listview
            //Listener for main listview, click opens new activity and sends data
            listOfCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position,long id) {
                    intent.putExtra("CITY_NUM", id);
                    intent.putExtra("CITY_NAME", nameTemp[0][(int)id]);
                    startActivity(intent);
                }});
            //Long tap to delete city from the main listview
            listOfCities.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
                    numtoremove = (int)id;
                    dialogremove.show();
                    return true;
                }
            });

            //Layout for the dialog
            LinearLayout ll =new LinearLayout(activity);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.addView(cityName);
            //Finally we create the dialog
            dialogadd = new AlertDialog.Builder(activity)
                    .setTitle("City name").setPositiveButton("ok", click)
                    .setNegativeButton("cancel",click).setView(ll).create();

            update();
        }
    }
}
