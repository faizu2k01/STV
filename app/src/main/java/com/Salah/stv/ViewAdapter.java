package com.Salah.stv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.Salah.stv.R;

import java.util.ArrayList;

public class ViewAdapter extends PagerAdapter {

    Context context;
    ArrayList<Integer> arrayListimage;

    public ViewAdapter(Context context,ArrayList<Integer> arrayListimage)
    {
        this.context = context;
        this.arrayListimage = arrayListimage;
    }

    @Override
    public int getCount() {
        return arrayListimage.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapterview,null);
        ImageView imageView = view.findViewById(R.id.imageOfview);
        Glide.with(context).asBitmap().load(arrayListimage.get(position)).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==0)
                {
                    Intent intent = new Intent(context, Pdf1_Activity.class);
                    context.startActivity(intent);
                }
                else if(position == 1){
                    Intent intent = new Intent(context, Pdf2_Activity.class);
                    context.startActivity(intent);
                }
                else if(position == 2){
                    Intent intent = new Intent(context, Pdf3_Activity.class);
                    context.startActivity(intent);
                }else if(position == 3){
                    Intent intent = new Intent(context, Pdf4_Activity.class);
                    context.startActivity(intent);
                }else if(position == 4)
                {
                    Intent intent = new Intent(context, Pdf5_Activity.class);
                    context.startActivity(intent);
                }
            }
        });

        container.addView(view,0);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
