package com.ketank619gmail.govschemes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

import static android.content.ContentValues.TAG;


public class SchemeFragment extends Fragment {
    VerticalViewPager verticalViewPager;
    VerticlePagerAdapter adapter;
    View view;
    SharedPreferences preferences;
    ArrayList<SchemeModel> Scheme= new ArrayList<>();


    public SchemeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_scheme, container, false);
        preferences = getActivity().getSharedPreferences("mypref",Context.MODE_PRIVATE);
        verticalViewPager = (VerticalViewPager) view.findViewById(R.id.verticleViewPager);

        Gson gson = new Gson();
        String schme = preferences.getString("Scheme", null);
        String category= preferences.getString("cat_selected", null);

        Type type = new TypeToken<ArrayList<SchemeModel>>() {}.getType();

        if(schme!=null) {



            if(category!=null){
                ArrayList<SchemeModel> schm= new ArrayList<>();
                for(SchemeModel sc:Scheme){
                    if (category.equalsIgnoreCase(sc.getCATEGORY())){
                        schm.add(sc);
                    }
                }
                adapter = new VerticlePagerAdapter(getActivity(), schm);
                verticalViewPager.setAdapter(adapter);


            }else {


                Scheme = gson.fromJson(schme, type);

                adapter = new VerticlePagerAdapter(getActivity(), Scheme);
                verticalViewPager.setAdapter(adapter);
                ;

            }
            }



        return view;
    }




}
