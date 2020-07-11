package com.example.myapplication.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

public class FragContact extends Fragment{
    //private View view;
    private ListView lv;

    public static FragContact newinstance(){
        FragContact fragContact = new FragContact();
        return fragContact;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //view=inflater.inflate(R.layout.frag_contact, container, false);
        //return view;
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frag_contact, container, false);

        lv = (ListView) rootView.findViewById(R.id.list);

        ArrayList<Person> m_orders = new ArrayList<Person>();

        // 폰 주소록
        Map<String, String> phone_address = ContactUtil.getAddressBook(getContext());

        @SuppressWarnings("rawtypes")
        Iterator ite = phone_address.keySet().iterator();
        while(ite.hasNext())
        {
            String phone = ite.next().toString();
            String name = phone_address.get(phone).toString();
            m_orders.add(new Person(name, phone));
        }
        // 이름 순으로 정렬
        Collections.sort(m_orders, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });

        PersonAdapter m_adapter = new PersonAdapter(getContext(), R.layout.view_friend_list, m_orders);
        lv.setAdapter(m_adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowID)
            {
                doSelectFriend((Person)parent.getItemAtPosition(position));
            }});
        return rootView;
    }

    // 한명 선택했을 때
    public void doSelectFriend(Person p)
    {
        Log.e("####", p.getName() + ", " + p.getNumber());
    }

    private class PersonAdapter extends ArrayAdapter<Person>
    {
        private ArrayList<Person> items;

        public PersonAdapter(Context context, int textViewResourceId, ArrayList<Person> items)
        {
            super(context, textViewResourceId, items);
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
            if (p != null)
            {
                //ImageView photo = (ImageView) v.findViewById(R.id.photo);
                TextView tt = (TextView) v.findViewById(R.id.name);
                TextView bt = (TextView) v.findViewById(R.id.msg);
                //if (photo != null)
                //{
                //    photo.setImageDrawable(p.getPhoto());
                //}
                if (tt != null)
                {
                    tt.setText(p.getName());
                }
                if(bt != null)
                {
                    bt.setText("전화번호: "+ p.getNumber());
                }
            }
            return v;
        }
    }

    class Person
    {
        private Drawable Photo;
        private String Name;
        private String Number;

        public Person(Drawable _Photo, String _Name, String _Number)
        {
            this.Photo = _Photo;
            this.Name = _Name;
            this.Number = _Number;
        }

        public Person(String _Name, String _Number)
        {
            //this.Photo = "@drawable/cat";
            this.Name = _Name;
            this.Number = _Number;
        }

        public Drawable getPhoto() { return Photo; }

        public String getName()
        {
            return Name;
        }

        public String getNumber()
        {
            return Number;
        }
    }
}
