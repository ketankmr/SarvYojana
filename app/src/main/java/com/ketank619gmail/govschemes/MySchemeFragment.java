package com.ketank619gmail.govschemes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MySchemeFragment extends Fragment {
    VerticalViewPager verticalViewPager;
    VerticlePagerAdapter adapter;
    View view;
    MaterialSpinner spinner;
    SharedPreferences preferences;
    ArrayList<SchemeModel> Scheme= new ArrayList<>();
    ArrayList<String>  eligibility= new ArrayList<>();
    Toolbar toolbar;


    public MySchemeFragment(ArrayList<SchemeModel> scheme) {

        this.Scheme = scheme;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        toolbar=  getActivity().findViewById(R.id.toolbarsdfs);
        view= inflater.inflate(R.layout.fragment_scheme, container, false);
        preferences = getActivity().getSharedPreferences("mypref",Context.MODE_PRIVATE);
        verticalViewPager = (VerticalViewPager) view.findViewById(R.id.verticleViewPager);

        spinner = (MaterialSpinner) toolbar.findViewById(R.id.spinner);

        spinner.setVisibility(View.GONE);

        spinner.setItems("All", "Pregnancy","Women Rights");


        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if (position==0){
                    ArrayList<SchemeModel> Scheme1= new ArrayList<>();
                    for (SchemeModel sch : Scheme){



                            if (sch.getCATEGORY().equalsIgnoreCase("Mother")||sch.getCATEGORY().equalsIgnoreCase("Women")){
                                Scheme1.add(sch);
                            }




                    }

                    adapter = new VerticlePagerAdapter(getActivity(), Scheme1);
                    verticalViewPager.setAdapter(adapter);

                }else if (position==1){
                    ArrayList<SchemeModel> Scheme1= new ArrayList<>();
                    for (SchemeModel sch : Scheme){



                            if (sch.getCATEGORY().equalsIgnoreCase("Mother")){
                                Scheme1.add(sch);
                            }




                    }

                    adapter = new VerticlePagerAdapter(getActivity(), Scheme1);
                    verticalViewPager.setAdapter(adapter);

                }else {
                    ArrayList<SchemeModel> Scheme1= new ArrayList<>();
                    for (SchemeModel sch : Scheme){


                            if (sch.getCATEGORY().equalsIgnoreCase("Women")){
                                Scheme1.add(sch);
                            }
                    }

                    adapter = new VerticlePagerAdapter(getActivity(), Scheme1);
                    verticalViewPager.setAdapter(adapter);

                }

            }
        });



       String elg= preferences.getString("eligibility",null);

        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        if(elg!=null){
            Gson gson = new Gson();
           eligibility = gson.fromJson(elg,type);

            ArrayList<SchemeModel> Scheme1= new ArrayList<>();


            for (SchemeModel sch : Scheme){

                if (eligibility.contains("mother")) {
                    spinner.setVisibility(View.VISIBLE);
                    if (sch.getCATEGORY().equalsIgnoreCase("Mother")||sch.getCATEGORY().equalsIgnoreCase("Women")){
                        Scheme1.add(sch);
                    }
                }
                if (eligibility.contains("children")&& eligibility.contains("student")) {
                    if (sch.getCATEGORY().equalsIgnoreCase("Education")){
                        Scheme1.add(sch);
                    }
                }
                    if (eligibility.contains("farmer")) {
                        if (sch.getCATEGORY().equalsIgnoreCase("Rural Development")){
                            Scheme1.add(sch);
                        }
                    }

                    if (eligibility.contains("Entrepreneur")) {
                        if (sch.getCATEGORY().equalsIgnoreCase("Startup")){
                            Scheme1.add(sch);
                        }
                    }/* if (sch.getCATEGORY().equalsIgnoreCase("Health")){
                    Scheme1.add(sch);
                }*/


            }

            adapter = new VerticlePagerAdapter(getActivity(), Scheme1);
            verticalViewPager.setAdapter(adapter);
        }else {


        adapter = new VerticlePagerAdapter(getActivity(), Scheme);
        verticalViewPager.setAdapter(adapter);
        }








        return view;



 }




}
