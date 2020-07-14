package com.example.myapplication.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.ArrayList;

public class FragContact extends Fragment{

    private ListView lv;

    public static FragContact newinstance(){
        FragContact fragContact = new FragContact();
        return fragContact;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frag_contact, container, false);

        lv = (ListView) rootView.findViewById(R.id.list);


        // 폰 주소록
        ArrayList<Person> phone_address = ContactUtil.getAddressBook(getContext());

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

    // 연락처 list click했을 경우
    public void doSelectFriend(Person p)
    {
        //전화 열기
        /*
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + p.getNumber())) ;
        startActivity(intent);
         */

        //전화 걸기
        /*
        Intent tt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + p.getNumber())) ;
        startActivity(tt);
        */

        // 문자 보내기
        /*
        Uri smsUri = Uri.parse("tel:" + p.getNumber());
        Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
        intent.putExtra("address", p.getNumber());
        intent.putExtra("sms_body", "input message");
        intent.setType("vnd.android-dir/mms-sms");
        startActivity(intent);
        */

        Log.e("####", p.getName() + ", " + p.getNumber());
    }
}
