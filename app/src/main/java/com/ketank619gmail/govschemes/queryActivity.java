package com.ketank619gmail.govschemes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class queryActivity extends AppCompatActivity {
    SchemeModel schemeModel;
    EditText subject, description;
 SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        schemeModel=(SchemeModel) getIntent().getSerializableExtra("schemeModel");
        preferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);

        subject = (EditText) findViewById(R.id.subject);
        description = (EditText) findViewById(R.id.Description);




    }

    public void save_details(View view){
        final String subj= subject.getText().toString().trim();
        final String desc = description.getText().toString().trim();

        add_query(subj,desc,schemeModel.getId(),preferences.getString("user_id",null));

    }


    public void add_query(final String subject, final String description, final String Schemeid,final String token){
        final ProgressDialog dialog;
        dialog = new ProgressDialog(this);
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


                    if (error) {

                        Toast.makeText(getApplicationContext(),"Error Occured...Try Again",Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(getApplicationContext(),"Query Added Successfully",Toast.LENGTH_LONG).show();

                        finish();
                    }




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

                Toast.makeText(getApplicationContext(),"Login Error: " + error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();


                params.put("Subject", subject);
                params.put("Description", description);
                params.put("token", token);

                params.put("Scheme_id", Schemeid);


                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "");

    }
}
