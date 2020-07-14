package com.example.myapplication.ui;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;


import java.util.ArrayList;

/**
 * Author CodeBoy722
 *
 * This Activity get a path to a folder that contains images from the MainActivity Intent and displays
 * all the images in the folder inside a RecyclerView
 */

public class ImageDisplay extends Fragment implements itemClickListener {

    RecyclerView imageRecycler;
    ArrayList<pictureFacer> allpictures;
    ProgressBar load;
    String foldePath;
    TextView folderName;

    public static ImageDisplay newinstance(){
        ImageDisplay imageDisplay = new ImageDisplay();
        return imageDisplay;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_image_display, container, false);
        Bundle bundle = getArguments();
        folderName = rootView.findViewById(R.id.foldername);
        //folderName.setText(getIntent().getStringExtra("folderName"));
        //foldePath =  getIntent().getStringExtra("folderPath");
        folderName.setText(bundle.getString("folderName"));
        foldePath = bundle.getString("folderPath");

        allpictures = new ArrayList<>();
        imageRecycler = rootView.findViewById(R.id.recycler);
        imageRecycler.addItemDecoration(new MarginDecoration(getContext()));
        imageRecycler.hasFixedSize();
        load = rootView.findViewById(R.id.loader);


        if(allpictures.isEmpty()){
            load.setVisibility(View.VISIBLE);
            allpictures = getAllImagesByFolder(foldePath);
            imageRecycler.setAdapter(new picture_Adapter(allpictures, getContext(),this));
            load.setVisibility(View.GONE);
        }else{

        }
        return rootView;
    }

    /**
     *
     * @param holder The ViewHolder for the clicked picture
     * @param position The position in the grid of the picture that was clicked
     * @param pics An ArrayList of all the items in the Adapter
     */
    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {
        pictureBrowserFragment browser = pictureBrowserFragment.newInstance(pics,position,getContext());

        // Note that we need the API version check here because the actual transition classes (e.g. Fade)
        // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
        // ARE available in the support library (though they don't do anything on API < 21)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //browser.setEnterTransition(new Slide());
            //browser.setExitTransition(new Slide()); uncomment this to use slide transition and comment the two lines below
            browser.setEnterTransition(new Fade());
            browser.setExitTransition(new Fade());
        }

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addSharedElement(holder.picture, position+"picture");
        transaction.add(R.id.displayContainer, browser);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onPicClicked(String pictureFolderPath, String folderName) {

    }

    /**
     * This Method gets all the images in the folder paths passed as a String to the method and returns
     * and ArrayList of pictureFacer a custom object that holds data of a given image
     * @param path a String corresponding to a folder path on the device external storage
     */
    public ArrayList<pictureFacer> getAllImagesByFolder(String path){
        ArrayList<pictureFacer> images = new ArrayList<>();
        Uri allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.ImageColumns.DATA , MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE};
        Cursor cursor = getContext().getContentResolver().query( allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[] {"%"+path+"%"}, null);
        try {
            cursor.moveToFirst();
            do{
                pictureFacer pic = new pictureFacer();

                pic.setPicturName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));

                pic.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));

                pic.setPictureSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));

                images.add(pic);
            }while(cursor.moveToNext());
            cursor.close();
            ArrayList<pictureFacer> reSelection = new ArrayList<>();
            for(int i = images.size()-1;i > -1;i--){
                reSelection.add(images.get(i));
            }
            images = reSelection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }


}
