package com.example.db_successful;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
;

public class activityProfile extends AppCompatActivity {
    private static SQLiteHelper sqLiteHelper;
    GridView gridView;
    ArrayList<yourPhotos> list;

    CustomAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        BottomNavigationView navView = findViewById(R.id.nav_view);


        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intentHome = new Intent(activityProfile.this, MainActivity.class);
                        startActivity(intentHome);
                        break;

                    case R.id.navigation_search:
                        Intent intent = new Intent(activityProfile.this, com.example.db_successful.activitySearch.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_plus:
                        Intent intentPlus = new Intent(activityProfile.this, com.example.db_successful.activityPost.class);
                        startActivity(intentPlus);
                        break;

                    case R.id.navigation_heart:
                        Intent intentLike = new Intent(activityProfile.this, activityLike.class);
                        startActivity(intentLike);
                        break;

                    case R.id.navigation_user:

                        break;
                }
                return false;
            }
        });
//       try {

        gridView = findViewById(R.id.grid_your_photos);
        list = new ArrayList<>();
        adapter = new CustomAdapter(this, R.layout.grid_view_your_photos, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite


        SQLiteHelper sqLiteHelper = new SQLiteHelper(activityProfile.this, "dbINSTAGRAM.sqlite", null, 1);
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM APP");

        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            list.add(new yourPhotos(name, image, id));
        }
        adapter.notifyDataSetChanged();


        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(activityProfile.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        try {
                            if (item == 0) {
                                // update
                                SQLiteHelper sqLiteHelper = new SQLiteHelper(activityProfile.this, "dbINSTAGRAM.sqlite", null, 1);
                                Cursor c = sqLiteHelper.getData("SELECT id FROM APP");

                                ArrayList<Integer> arrID = new ArrayList<Integer>();
                                while (c.moveToNext()) {
                                    arrID.add(c.getInt(0));
                                }
                                // show dialog update at here
                                showDialogUpdate(activityProfile.this, arrID.get(position));

                            } else {
                                // delete
                                SQLiteHelper sqLiteHelper = new SQLiteHelper(activityProfile.this, "dbINSTAGRAM.sqlite", null, 1);
                                Cursor c = sqLiteHelper.getData("SELECT id FROM APP");

                                ArrayList<Integer> arrID = new ArrayList<Integer>();
                                while (c.moveToNext()) {
                                    arrID.add(c.getInt(0));
                                }
                                showDialogDelete(arrID.get(position));
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });


    }

    ImageView imageViewUpdate;
    ImageView TickForUpdate;
    private void showDialogUpdate(Activity activity, final int position) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.activity_update_profile);
        dialog.setTitle("Update");

        imageViewUpdate = (ImageView) dialog.findViewById(R.id.imageView_forUpdate);
        Button btnSelect =  dialog.findViewById(R.id.btnSelect);


        final EditText edtName = (EditText) dialog.findViewById(R.id.captionEditText_forUpdate);
        TickForUpdate = dialog.findViewById(R.id.tick_forUpdate);

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();



        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(
                        activityProfile.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });



        TickForUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SQLiteHelper sqLiteHelper = new SQLiteHelper(activityProfile.this, "dbINSTAGRAM.sqlite", null, 1);
                    activityProfile.sqLiteHelper.updateData( edtName.getText().toString().trim(),
                            activityPost.imageViewToByte(imageViewUpdate),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();

                }
                catch (Exception e){
                    Log.e("error", e.getMessage());
                }

                updatePhoto();
            }
        });
    }


    private void showDialogDelete(final int idImage){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(activityProfile.this);

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    SQLiteHelper sqLiteHelper = new SQLiteHelper(activityProfile.this, "dbINSTAGRAM.sqlite", null, 1);
                    activityProfile.sqLiteHelper.deleteData(idImage);
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updatePhoto();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }


    private void updatePhoto(){
        // get all data from sqlite

        SQLiteHelper sqLiteHelper = new SQLiteHelper(activityProfile.this, "dbINSTAGRAM.sqlite", null, 1);
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM APP");

        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            list.add(new yourPhotos(name, image, id));
        }
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewUpdate.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
