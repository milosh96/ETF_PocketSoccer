package com.vms.etf_pocketsoccer.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vms.etf_pocketsoccer.R;

import androidx.annotation.*;

public class SpinnerHeadAdapter extends ArrayAdapter<String> {

    String[] names;
    int[] images;
    Context context;

    public SpinnerHeadAdapter(Context context, int resource, String[] names, int[] images) {
        super(context,resource,names);
        this.names = names;
        this.images = images;
        this.context=context;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=LayoutInflater.from(context).inflate(R.layout.spinner_item,null);

        TextView tv=view.findViewById(R.id.spinner_text);
        ImageView imageView=view.findViewById(R.id.spinner_img);

        tv.setText(names[position]);
        imageView.setImageResource(images[position]);

        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=LayoutInflater.from(context).inflate(R.layout.spinner_item,null);

        TextView tv=view.findViewById(R.id.spinner_text);
        ImageView imageView=view.findViewById(R.id.spinner_img);

        tv.setText(names[position]);
        imageView.setImageResource(images[position]);

        return view;
    }
}
