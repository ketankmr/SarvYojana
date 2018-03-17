package com.ketank619gmail.govschemes;


/**
 * Created by Ketan-PC on 10/26/2017.
 */


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;



import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Querylist_Adapter extends RecyclerView.Adapter<Querylist_Adapter.MyViewHolder> {

    Context context;
    ArrayList<QueryModel> queries;
    SharedPreferences preferences;


    public Querylist_Adapter(Context context, ArrayList queries) {
        this.context = context;
        this.queries =queries ;

        preferences= context.getSharedPreferences("mypref",Context.MODE_PRIVATE);

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.query_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.question.setText(queries.get(position).getQuestion());
        holder.answer.setText(queries.get(position).getAnswer());




    }

    @Override
    public int getItemCount() {
        return queries.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's

        TextView question,answer;


        public MyViewHolder(View itemView) {
            super(itemView);

            question = (TextView) itemView.findViewById(R.id.textView3);

            answer=(TextView) itemView.findViewById(R.id.textView2);

        }
    }


}