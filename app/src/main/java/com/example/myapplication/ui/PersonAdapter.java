package com.example.myapplication.ui;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class PersonAdapter extends ArrayAdapter<Person>
{
    private ArrayList<Person> items;
    private final Context mContext;

    public PersonAdapter(Context context, int textViewResourceId, ArrayList<Person> items)
    {
        super(context, textViewResourceId, items);
        this.mContext = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.view_friend_list, null);
        }
        Person p = items.get(position);

        ImageView photo = (ImageView) v.findViewById(R.id.photo);
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView number = (TextView) v.findViewById(R.id.msg);
        Bitmap photo_bitmap = loadPhoto(mContext.getContentResolver(), p.getPerson_id(), p.getPhoto_id());

        if (photo_bitmap != null) {
            photo.setImageBitmap(photo_bitmap);
        }
        else {
            photo.setImageResource(R.drawable.ic_launcher_foreground);
        }
        name.setText(p.getName());
        number.setText("전화번호: "+ p.getNumber());

        return v;
    }

    public int getItemCount() {
        return items.size();
    }

    public Bitmap loadPhoto(ContentResolver cr, long id, long photo_id) {

        byte[] photoBytes = null;
        Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photo_id);
        Cursor c = cr.query(photoUri, new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO},
                null,null, null);
        try {
            if (c.moveToFirst())
                photoBytes = c.getBlob(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }

        if (photoBytes != null) {
            return resizingBitmap(BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length));
        } else {
            Log.d("<<CONTACT_PHOTO>>", "second try also failed");
        }
        return null;

    }

    public Bitmap resizingBitmap(Bitmap oBitmap) {
        if (oBitmap == null) {
            return null;
        }
        Bitmap rBitmap = null;

        /*float width = oBitmap.getWidth();
        float height = oBitmap.getHeight();
        float resizing_size = 120;
        if (width > resizing_size) {
            float mWidth = (float)(width / 100);
            float fScale = (float)(resizing_size / mWidth);
            width *= (fScale / 100);
            height *= (fScale / 100);

        } else if (height > resizing_size) {
            float mHeight = (float)(height / 100);
            float fScale = (float)(resizing_size / mHeight);
            width *= (fScale / 100);
            height *= (fScale / 100);
        }*/

        //Log.d("rBitmap : " + width + ", " + height);

        rBitmap = Bitmap.createScaledBitmap(oBitmap, 100, 80, true);
        return rBitmap;
    }
}