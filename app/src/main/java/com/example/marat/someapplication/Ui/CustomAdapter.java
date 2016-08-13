package com.example.marat.someapplication.Ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.marat.someapplication.R;

public class CustomAdapter extends ArrayAdapter<String>{
    String[][] nameTemp;
    Context context;

     public CustomAdapter(Context context, String[][] nameTemp) {
         // TODO Auto-generated constructor stub
    	 super(context, R.layout.listviewstyle, nameTemp[0]);
    	 
         this.nameTemp = nameTemp;
         this.context = context;
     }
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		convertView = LayoutInflater.from(getContext()).inflate(R.layout.listviewstyle, parent, false);
		
		TextView txtTitle = (TextView) convertView.findViewById(R.id.item);
		//ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		TextView extratxt = (TextView) convertView.findViewById(R.id.textView1);
		
		txtTitle.setText(nameTemp[0][position]);
		//imageView.setImageResource(imgid[position]);
		extratxt.setText(nameTemp[1][position]+"°C");
        return convertView;
	}

}
