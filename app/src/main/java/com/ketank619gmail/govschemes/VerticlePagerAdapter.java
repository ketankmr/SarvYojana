package com.ketank619gmail.govschemes;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Ketan on 21-Aug-17.
 */

public class VerticlePagerAdapter extends PagerAdapter {



    Context mContext;
    ArrayList<SchemeModel> Scheme;
    LayoutInflater mLayoutInflater;

    public VerticlePagerAdapter(Context context,ArrayList Scheme) {
        mContext = context;
        this.Scheme=Scheme;

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Scheme.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.scheme_item, container, false);

        TextView label = (TextView) itemView.findViewById(R.id.textView);
        TextView Schm = (TextView) itemView.findViewById(R.id.title);
        ImageView img = (ImageView) itemView.findViewById(R.id.imageView);
        ImageButton query = (ImageButton) itemView.findViewById(R.id.query);
        label.setText(Scheme.get(position).getDescription());
        Schm.setText(Scheme.get(position).getScheme());


        Glide
                .with(mContext)
                .load(Scheme.get(position).getUrl())
                .placeholder(R.drawable.loading)
                .fitCenter()
                .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into(img);

        container.addView(itemView);


     query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,queryActivity.class);
                intent.putExtra("schemeModel",Scheme.get(position));
                mContext.startActivity(intent);
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }





}
