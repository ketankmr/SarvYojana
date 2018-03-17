package com.ketank619gmail.govschemes;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.facebook.accountkit.AccountKit;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    BoomMenuButton bmb;


    private ViewPager viewPager;


    CircularImageView CircularImage;
    SharedPreferences preferences;
    Toolbar toolbar;

    final ArrayList<SchemeModel> Scheme= new ArrayList<>();


    SchemeFragment schemeFragment;
    ComplainFragment complainFragment;
    SettingsFragment settingsFragment;
    MenuItem prevMenuItem;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);

        toolbar = (Toolbar) findViewById(R.id.toolbarsdfs);

        CircularImage= (CircularImageView)toolbar.findViewById(R.id.CircularImage);

        getSchemes(All_Links.values.get_schemes);
        getComplains(All_Links.values.get_complains);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        bmb = (BoomMenuButton) findViewById(R.id.bmb);

        bmb.setShadowEffect(true);
        final ArrayList<String> categ= new ArrayList<>();
        final ArrayList<Integer> img_res= new ArrayList<>();
        categ.add("Rural Development");
        categ.add("Health");
        categ.add("Education");
        categ.add("Startup");


        img_res.add(R.drawable.rural);
        img_res.add(R.drawable.health);
        img_res.add(R.drawable.book);
        img_res.add(R.drawable.startup);


        CircularImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Gson gson = new Gson();
                    Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                     i.putExtra("Scheme",gson.toJson(Scheme));
                    startActivity(i);

            }
        });


        for (int i = 0; i <categ.size(); i++) {
            final HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(img_res.get(i))
                    .normalText(categ.get(i))
                    .textSize(16)
                    .normalColor(Color.parseColor("#BD6142"))
                    .imagePadding(new Rect(10,10,0,10));

            bmb.addBuilder(builder);
            builder.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("cat_selected",categ.get(index));
                    editor.apply();
                    viewPager.getAdapter().notifyDataSetChanged();

                }
            });
        }


        IntentFilter iffr= new IntentFilter("com.ketank619gmail.govschemes.CUSTOM_EVENT");
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice , iffr);



        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_call:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_chat:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_contact:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                if(position==0){
                    bmb.setVisibility(View.VISIBLE);
                }
                else {
                    bmb.setVisibility(View.GONE);
                }

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        setupViewPager(viewPager);

        getStates(All_Links.values.get_states);
    }

    public void new_complain(View view){
        Intent intent = new Intent(this, Add_Complain.class);
        startActivity(intent);
         overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }

    public void logout (View view){
        AccountKit.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        schemeFragment=new SchemeFragment();
        complainFragment=new ComplainFragment();
        settingsFragment=new SettingsFragment();
        adapter.addFragment(schemeFragment);
        adapter.addFragment(complainFragment);
        adapter.addFragment(settingsFragment);
        viewPager.setAdapter(adapter);
    }



    public void getSchemes (String url ){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading Schemes");
        dialog.setTitle("Please Wait...");
        dialog.show();
        dialog.setCancelable(false);





        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray p) {
                        // display response
                        Log.d("Response", p.toString());
                        try {

                            if (p.length()>0){

                                for(int i=0;i<p.length();i++){
                                    SchemeModel schemeModel = new SchemeModel();
                                    JSONObject row = p.getJSONObject(i);
                                     schemeModel.setScheme(row.getString("SCHEME"));
                                    schemeModel.setDescription(row.getString("DESCRIPTION"));
                                    schemeModel.setId(row.getString("ID"));
                                    schemeModel.setUrl(row.getString("Url"));
                                    schemeModel.setCATEGORY(row.getString("CATEGORY"));

                                   Scheme.add(schemeModel);

                                }

                                SharedPreferences.Editor editor = preferences.edit();

                                Gson gson = new Gson();

                                editor.putString("Scheme",gson.toJson(Scheme));


                                editor.apply();

                                viewPager.getAdapter().notifyDataSetChanged();

                                dialog.dismiss();



                            }


                        } catch (Exception e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,"Exception :"+e,Toast.LENGTH_LONG);
                            dialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "");
                        Toast.makeText(MainActivity.this,"VolleyError :"+error,Toast.LENGTH_LONG);
                        dialog.dismiss();
                    }
                }
        );


        AppController.getInstance().addToRequestQueue(getRequest);


    }


    private BroadcastReceiver onNotice= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Update your RecyclerView here using notifyItemInserted(position);
            Bundle extras = intent.getExtras();
            String status;

            if (extras != null) {
                status = extras.getString("Key");

                // and get whatever type user account id is

                if (status.equalsIgnoreCase("new_complain")) {

                    getComplains(All_Links.values.get_complains);
                                }


            }

        }
    };

    public void getComplains (String url ){

        final ArrayList<String> Problem= new ArrayList<>();
        final ArrayList<String> Description= new ArrayList<>();
        final ArrayList<Integer> Count= new ArrayList<>();
        final ArrayList<Integer> ID= new ArrayList<>();
        final ArrayList<String> Confirmed= new ArrayList<>();



        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray p) {
                        // display response
                        Log.d("Response", p.toString());
                        try {

                            if (p.length()>0){

                                for(int i=p.length()-1;i>0;i--){
                                    JSONObject row = p.getJSONObject(i);
                                    ID.add(row.getInt("ID"));
                                    Problem.add(row.getString("Problem"));
                                    Description.add(row.getString("DESCRIPTION"));
                                    Count.add(row.getInt("Count"));
                                    Confirmed.add(row.getString("Confirmed"));


                                }

                                SharedPreferences.Editor editor = preferences.edit();

                                Gson gson = new Gson();
                                editor.putString("Problem_id",gson.toJson(ID));
                                editor.putString("Problem",gson.toJson(Problem));
                                editor.putString("complain_desc",gson.toJson(Description));
                                editor.putString("complain_count",gson.toJson(Count));
                                editor.putString("confirmed",gson.toJson(Confirmed));


                                editor.apply();

                                viewPager.getAdapter().notifyDataSetChanged();



                            }


                        } catch (Exception e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,"Exception :"+e,Toast.LENGTH_LONG);

                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "");
                        Toast.makeText(MainActivity.this,"VolleyError :"+error,Toast.LENGTH_LONG);

                    }
                }
        );


        AppController.getInstance().addToRequestQueue(getRequest);


    }


    public void getStates (String url){
        final ArrayList<String> States= new ArrayList<>();
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray p) {
                        // display response
                        Log.d("Response", p.toString());
                        try {

                            if (p.length()>0){

                                for(int i=0;i<p.length();i++){

                                    States.add(p.getJSONObject(i).getString("Name"));

                                    SharedPreferences.Editor editor = preferences.edit();

                                    Set<String> states = new HashSet<String>();
                                    states.addAll(States);
                                    editor.putStringSet("states",states);
                                    editor.apply();
                                }


                            }


                        } catch (Exception e) {
                            // JSON error
                            e.printStackTrace();


                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "");
                    }
                }
        );


        AppController.getInstance().addToRequestQueue(getRequest);


    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cat_selected",null);
        editor.apply();
        super.onBackPressed();
    }

}
