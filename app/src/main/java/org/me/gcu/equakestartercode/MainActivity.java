/**
 * MainActivity
 * @author Dev Seebaluck
 * @matric S1903333
 **/

package org.me.gcu.equakestartercode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import org.me.gcu.equakestartercode.Fragment.About_Fragment;
import org.me.gcu.equakestartercode.Fragment.Home_Fragment;
import org.me.gcu.equakestartercode.Fragment.List_Fragment;
import org.me.gcu.equakestartercode.Fragment.MapFragment;
import org.me.gcu.equakestartercode.Fragment.Detail_Fragment;

import org.me.gcu.equakestartercode.Model.Item;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private TextView rawDataDisplay;
    private Button startButton;
    private String result;
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public static Fragment fragment;
    private Bundle bundle;
    private ArrayList<Item> data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        bundle = new Bundle();

        if(savedInstanceState != null){
            fragment =  getSupportFragmentManager().getFragment(savedInstanceState,"fragment");
            data = (ArrayList<Item>) savedInstanceState.getSerializable("data");
        }else {
            try {
                data = new MyParser().execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            fragment = new List_Fragment();
        }

        bundle.putSerializable("data", data);
        fragment.setArguments(bundle);

        //getting List_Fragment data to home page
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment , "MY_FRAGMENT").commit();

        /**
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.index:
                        //item.setChecked(true);
                        //displayMessage("index Selected...");
                        //drawerLayout.closeDrawers();
                        //return true;
                        item.setChecked(true);
                        List_Fragment list = new List_Fragment();
                        bundle.putSerializable("data", data);
                        list.setArguments(bundle);
                        fragment = list;
                        displayMessage("Home");
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.map:
                        //item.setChecked(true);
                        //displayMessage("Map Selected...");
                        //drawerLayout.closeDrawers();
                        //return true;
                        item.setChecked(true);
                        MapFragment map = new MapFragment();
                        bundle.putSerializable("data", data);
                        map.setArguments(bundle);
                        fragment = map;
                        displayMessage("Map");
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.search:
                        item.setChecked(true);
                        displayMessage("Search");
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.about:
                        item.setChecked(true);
                        displayMessage("About Us");
                        drawerLayout.closeDrawers();
                        return true;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment , "MY_FRAGMENT").commit();
                return true;
                //return false;
            }
        });
         **/

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, "MY_FRAGMENT").commit();

        //rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        //startButton = (Button)findViewById(R.id.startButton);
        //startButton.setOnClickListener(this);
        //Log.e("MyTag","after startButton");
        // More Code goes here
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.index:
                item.setChecked(true);
                List_Fragment list = new List_Fragment();
                bundle.putSerializable("data", data);
                list.setArguments(bundle);
                fragment = list;
                displayMessage("Home");
                drawerLayout.closeDrawers();
                break;


            case R.id.map:
                item.setChecked(true);
                MapFragment map = new MapFragment();
                bundle.putSerializable("data", data);
                map.setArguments(bundle);
                fragment = map;
                displayMessage("Map");
                drawerLayout.closeDrawers();
                break;

            case R.id.search:
                item.setChecked(true);
                displayMessage("Search");
                drawerLayout.closeDrawers();
                Home_Fragment home = new Home_Fragment();
                bundle.putSerializable("data", data);
                home.setArguments(bundle);
                fragment = home;
                break;


            case R.id.about:
                item.setChecked(true);
                displayMessage("About Us");
                drawerLayout.closeDrawers();
                fragment = new About_Fragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment , "MY_FRAGMENT").commit();
        return true;
    }


    private void displayMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    class MyParser extends AsyncTask<Void, ArrayList<Item>, ArrayList<Item>> {
        private ArrayList<Item> data = new ArrayList<>();
        private NodeList nodes;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Item> doInBackground(Void... voids) {
            data = getData();
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<Item> items) {
            super.onPostExecute(items);
        }

        @Override
        protected void onProgressUpdate(ArrayList<Item>... values) {
            super.onProgressUpdate(values);
        }

        private ArrayList getData(){
            ArrayList<Item> data = null;

            try {
                java.net.URL url = new URL("https://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
                URLConnection conn = url.openConnection();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(conn.getInputStream());

                nodes = doc.getElementsByTagName("item");
                data  = parseData(nodes);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return data;
        }

        private ArrayList<Item> parseData(NodeList nodes){
            Item newItem = null;
            ArrayList<Item> items = new ArrayList<Item>();
            Element line = null;

            for (int i = 0; i < nodes.getLength(); i++) {
                newItem = new Item();

                Element element = (Element) nodes.item(i);

                //Adding the title
                NodeList title = element.getElementsByTagName("title");
                line = (Element) title.item(0);
                newItem.setDescription(line.getTextContent());

                //Adding the description
                NodeList description = element.getElementsByTagName("description");
                line = (Element) description.item(0);
                String temp = line.getTextContent();

                for (String dt : temp.split(";")) {
                    String var = dt.substring(0, dt.indexOf(":")).trim();

                    if(var.equalsIgnoreCase("Origin date/time")){
                        newItem.setDate(dt.substring(dt.indexOf(":")+1).trim());
                    }else if(var.equalsIgnoreCase("Location")){
                        newItem.setLocation(dt.substring(dt.indexOf(":")+1).trim());
                    }else if(var.equalsIgnoreCase("Lat/long")){
                        String spl[] = dt.substring(dt.indexOf(":")+1).split(",");
                        newItem.setLat(spl[0].trim());
                        newItem.setLon(spl[1].trim());
                    }else if(var.equalsIgnoreCase("Magnitude")){
                        newItem.setMagnitude(dt.substring(dt.indexOf(":")+1).trim());
                    }else if(var.equalsIgnoreCase("Depth")){
                        newItem.setDepth(dt.substring(dt.indexOf(":")+1).trim());
                    }
                }

                items.add(newItem);
            }

            return items;
        }
    }

    /**
    public void onClick(View aview)
    {
        Log.e("MyTag","in onClick");
        startProgress();
        Log.e("MyTag","after startProgress");
    }
**/

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "fragment", fragment);
        outState.putSerializable("data", data);
    }

    private String findFragment(){
        Fragment myFragment = (Fragment) getSupportFragmentManager().findFragmentByTag("MY_FRAGMENT");
        String fragment_name = "";

        if(myFragment instanceof Detail_Fragment){
            fragment_name = "DetailFragment";
        }else if(myFragment instanceof Home_Fragment){
            fragment_name = "HomeFragment";
        }else if(myFragment instanceof ListFragment){
            fragment_name = "ListFragment";
        }else if(myFragment instanceof About_Fragment){
            fragment_name = "AboutFragment";
        }

        return fragment_name;
    }


    /**
    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag","after ready");
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e("MyTag",inputLine);

                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
            }

            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    rawDataDisplay.setText(result);
                }
            });
        }

    }
     **/

}