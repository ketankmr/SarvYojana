package com.ketank619gmail.govschemes;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnsweredQueries extends Fragment {

    ArrayList<QueryModel> queries= new ArrayList<>();
    Querylist_Adapter adapter;
    View view;
    RecyclerView recyclerView;
    ProgressBar progressBar;


    public AnsweredQueries() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_answered_queries, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        progressBar=(ProgressBar) view.findViewById(R.id.progd);
        progressBar.setVisibility(View.GONE);
        adapter = new Querylist_Adapter(getActivity(),queries);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        return  view;
    }

    public void getquery(final String token){
        final ProgressDialog dialog;
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Please Wait...");
        dialog.setMessage("Adding Complaint");
        dialog.show();


        String url = All_Links.values.add_query;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("", "Login Response: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);

                    Boolean error = jObj.getBoolean("error");

                    dialog.dismiss();







                } catch (Exception e) {
                    // JSON error
                    e.printStackTrace();
                    dialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();


                params.put("token", token);




                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "");

    }



}
