package com.example.myapplication.ui;

import android.content.Context;
import android.graphics.Bitmap;
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


        // 폰 주소록
        ArrayList<Person> phone_address = ContactUtil.getAddressBook(getContext());

        /*ArrayList<Person> m_orders = new ArrayList<Person>();
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
            }});*/
        PersonAdapter personAdapter = new PersonAdapter(getContext(), R.layout.view_friend_list, phone_address);
        lv.setAdapter(personAdapter);
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
}
