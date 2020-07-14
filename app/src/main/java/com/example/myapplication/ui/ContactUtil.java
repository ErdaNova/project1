package com.example.myapplication.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;

public class ContactUtil {

    /**
     * 내 전화번호 가져오기
     * @param context
     * @param isIDD 국제전화 규격 적용 여부
     * @return
     */
    public static String myPhoneNumber(Context context, boolean isIDD)
    {
        TelephonyManager phone = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (isIDD)
            return getIDD(phone.getLine1Number());
        else
            return phone.getLine1Number();
    }
    /**
     * 내 전화번호 가져오기
     * @param context
     * @return 전화번호
     */
    public static String myPhoneNumber(Context context)
    {
        return myPhoneNumber(context, false);
    }


    /**
     * 주소록에 있는 전화번호 목록 가져오기
     * @param context
     * @param isIDD 국제전화 규격 적용 여부
     * @return 주소록의 전화번호
     */
    public static ArrayList<String> contactPhoneNumber(Context context, boolean isIDD)
    {
        ArrayList<String> phone = new ArrayList<String>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
        while(cursor.moveToNext())
        {
            int index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String s = cursor.getString(index);

            if (isIDD)
                phone.add(getIDD(s));
            else
                phone.add(s);
        }

        return phone;
    }
    /**
     * 주소록에 있는 전화번호 목록 가져오기
     * @param context
     * @return 주소록의 전화번호
     */
    public static ArrayList<String> contactPhoneNumber(Context context)
    {
        return contactPhoneNumber(context, false);
    }


    /**
     * 주소록의 이름, 전화번호 맵을 가져온다
     * @param context
     * @param isIDD 국제전화 규격 적용 여부
     * @return 이름, 전화번호 map
     */
    public static ArrayList<Person> getAddressBook(Context context, boolean isIDD)
    {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.Contacts._ID
        };
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, sortOrder);
        LinkedHashSet<Person> hashlist = new LinkedHashSet<>();
        while(cursor.moveToNext())
        {
            Person Item = new Person();
            if (isIDD)
                Item.setNumber(getIDD(cursor.getString(0)));
            else
                Item.setNumber(cursor.getString(0));
            Item.setName(cursor.getString(1));
            Item.setPhoto_id(cursor.getLong(2));
            Item.setPerson_id(cursor.getLong(3));

            hashlist.add(Item);

//          Log.e("####getAddressBook", name + " : "+phone);
        }
        ArrayList<Person> result = new ArrayList<>(hashlist);
        return result;
    }
    /**
     * 주소록의 이름, 전화번호 맵을 가져온다
     * @param context
     * @return 이름, 전화번호 map
     */
    public static ArrayList<Person> getAddressBook(Context context)
    {
        return getAddressBook(context, false);
    }

    /**
     * 국제전화 형식으로 변경한다.
     * @param phone
     * @return 국제전화번호 규격
     */
    public static String getIDD(String phone)
    {
        String result = phone;

        try {
            result = "82" + Long.parseLong(phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}


//출처: https://susemi99.tistory.com/801 [쎄미 - 우물쭈물하다가 내 이럴 줄 알았지]