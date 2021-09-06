package com.example.db_successful;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<yourPhotos> yourPhotosList;

    public CustomAdapter(Context context, int layout, ArrayList<yourPhotos> yourPhotosList) {
        this.context = context;
        this.layout = layout;
        this.yourPhotosList = yourPhotosList;
    }

    @Override
    public int getCount() {
        return yourPhotosList.size();
    }

    @Override
    public Object getItem(int position) {
        return yourPhotosList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);


            holder.imageView = (ImageView) row.findViewById(R.id.image_first);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        yourPhotos photos = yourPhotosList.get(position);

        byte[] photosImage = photos.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(photosImage, 0, photosImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}