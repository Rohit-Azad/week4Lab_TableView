package com.example.week4lab;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DisplayTableActivity extends AppCompatActivity {
    //declaring variables
    ArrayList<Australia> ausInfo;
    Map<String, ArrayList<Australia>> map;
    ArrayList<Australia> mapValue;
    ArrayList<Australia> mapList;
    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_table);

        //initializing various views
        HorizontalScrollView hScrollView = (HorizontalScrollView) findViewById(R.id.scroller);
        ausInfo =processData();//adding data to mapList from processData() function
        mapList =new ArrayList<>();
        mapValue =new ArrayList<>();
        map = new HashMap<String, ArrayList<Australia>>();
        heading=new TextView(this);

        //Creating a hashmap witrh admin field as key
        for (Australia australia : ausInfo) {
            String key  = australia.admin;
            if(map.containsKey(key)){
                mapList = map.get(key);
                mapList.add(australia);
            }else{
                mapList.add(australia);
                map.put(key, mapList);
            }
        }

        if ( ausInfo != null ) {
            hScrollView.removeAllViews();//removing all views

            //declaring various layouts
            LinearLayout layout = new LinearLayout(this);
            TableLayout tLayout;
            TableLayout tLayout_2;
            TableRow tr_head;
            TableRow tr_admin;

            //declaring label textviews
            TextView label_city;
            TextView label_country;
            TextView label_population;
            TextView label_populationProper;
            TextView label_ISO2;
            TextView label_capital;
            TextView label_latitude;
            TextView label_longitude;
            TextView label_admin;
            layout.setOrientation(LinearLayout.VERTICAL);

            //setting parameters for various layouts
            LinearLayout.LayoutParams llParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.FILL_PARENT);
            TableRow.LayoutParams tRowParams=new TableRow.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            TableLayout.LayoutParams tLayoutParams=new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView tViewSpacing ;
            heading.setText("Australia Information");//Main heading
            heading.setTextSize(36);
            heading.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.addView(heading);

            //looping through hashmap
            for (Map.Entry<String, ArrayList<Australia>> entry : map.entrySet()) {
                String key = entry.getKey();
                mapValue = entry.getValue();

                //initializing layouts and textviews
                tLayout = new TableLayout(this);
                tLayout_2 = new TableLayout(this);
                tr_head = new TableRow(this);
                tr_admin = new TableRow(this);
                tViewSpacing = new TextView(this);

                //setting parameters to layouts
                tViewSpacing.setLayoutParams(llParams);
                tViewSpacing.setTextSize(18);
                tr_head.setLayoutParams(tRowParams);
                tr_admin.setLayoutParams(tRowParams);
                tLayout.setLayoutParams(tLayoutParams);
                tLayout_2.setLayoutParams(tLayoutParams);
                tr_head.setBackgroundColor(Color.BLACK);
                tr_admin.setBackgroundColor(Color.BLUE);

                //initializing textviews for table headers
                label_city = new TextView(this);
                label_country = new TextView(this);
                label_admin = new TextView(this);
                label_capital = new TextView(this);
                label_ISO2 = new TextView(this);
                label_population = new TextView(this);
                label_populationProper = new TextView(this);
                label_latitude = new TextView(this);
                label_longitude = new TextView(this);

                //using custom function to set labels to the table header
                tableDataGenerator(label_city,"City",tr_head);
                tableDataGenerator(label_latitude,"Latitude",tr_head);
                tableDataGenerator(label_longitude,"Longitude",tr_head);
                tableDataGenerator(label_population,"Population",tr_head);
                tableDataGenerator(label_country,"Country",tr_head);
                tableDataGenerator(label_ISO2,"ISO2",tr_head);
                tableDataGenerator(label_capital,"Capital",tr_head);
                tableDataGenerator(label_populationProper,"Population_P",tr_head);

                tLayout.removeView(tr_admin);
                TextView tv1=new TextView(this);
                tableDataGenerator(tv1,"Admin: " +key,tr_admin);

                //adding rows to table layout
                tLayout_2.addView(tr_admin);
                tLayout.addView(tr_head);


                //looping through ashmap values
                for(int i = 0; i< mapValue.size(); i++)
                {
                    //grouping by key mapValue
                    if(mapValue.get(i).admin.equals(key))
                    {
                        //creating rows for data
                        TableRow tr_data = new TableRow(this);
                        TableRow tr_data2 = new TableRow(this);

                        //setting ids to rows
                        tr_data.setId(100 + i);
                        tr_data2.setId(200 + i);

                        //setting layout parameters
                        tr_data.setLayoutParams(tRowParams);
                        tr_data2.setLayoutParams(tRowParams);

                        //data row background color
                        tr_data.setBackgroundColor(Color.GRAY);
                        tr_data2.setBackgroundColor(Color.GRAY);

                        //declaring table data textviews along with their ids
                        // with counter increasing with every iteration
                        TextView city = new TextView(this);
                        city.setId(100 + i);
                        TextView country = new TextView(this);
                        country.setId(100 + i);
                        TextView admin = new TextView(this);
                        admin.setId(100 + i);
                        TextView capital = new TextView(this);
                        capital.setId(100 + i);
                        TextView ISO2 = new TextView(this);
                        ISO2.setId(100 + i);
                        TextView population = new TextView(this);
                        population.setId(i);
                        TextView populationProper = new TextView(this);
                        populationProper.setId(100 + i);
                        TextView latitude = new TextView(this);
                        latitude.setId(100 + i);
                        TextView longitude = new TextView(this);
                        longitude.setId(100 + i);

                        //populating the tables with values grouped by key i.e. admin
                        tableDataGenerator(city, mapValue.get(i).city, tr_data);
                        tableDataGenerator(latitude, mapValue.get(i).lat, tr_data);
                        tableDataGenerator(longitude, mapValue.get(i).lng, tr_data);
                        tableDataGenerator(population, mapValue.get(i).population, tr_data);
                        tableDataGenerator(country, mapValue.get(i).country, tr_data);
                        tableDataGenerator(ISO2, mapValue.get(i).iso2, tr_data);
                        tableDataGenerator(capital, mapValue.get(i).capital, tr_data);
                        tableDataGenerator(populationProper, mapValue.get(i).population_proper, tr_data);

                        //adding row to table layout
                        tLayout.addView(tr_data);
                        tViewSpacing.setText("\n");
                    }
                }
                //adding textviews and table layouts to linear layout
                layout.addView(tViewSpacing);
                layout.addView(tLayout_2);
                layout.addView(tLayout);
            }
            //adding linear layout to scrollview
            hScrollView.addView(layout);
        }

    }

    //function to geneate table rows
    private void tableDataGenerator(TextView name, String label, TableRow tr)
    {
        tr.removeView(name);
        name.setText(label);
        name.setTextColor(Color.WHITE);
        name.setTextSize(18);
        name.setPadding(8, 8, 8, 8);
        tr.addView(name);
    }

    //function to process JSON file
    private ArrayList<Australia> processData() {
        String json = null;
        try {
            InputStream is = getAssets().open("au.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        ArrayList<Australia> temp = new ArrayList<>();
        try {

            JSONArray ar = new JSONArray(json);
            JSONObject element;
            Australia a;
            for (int i=0 ; i < ar.length(); i++) {
                element = ar.getJSONObject(i);
                a = new Australia();
                a.city = element.getString("city");
                a.admin = element.getString("admin");
                a.country = element.getString("country");
                a.population_proper = element.getString("population_proper");
                a.population = element.getString("population");
                a.lat = element.getString("lat");
                a.lng = element.getString("lng");
                a.iso2 = element.getString("iso2");
                a.capital = element.getString("capital");
                temp.add(a);
            }
            return temp;
        } catch (JSONException e) {
            Log.d("MainActivity", e.getMessage());
        }

        return null;
    }
}
