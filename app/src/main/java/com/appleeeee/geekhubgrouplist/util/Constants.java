package com.appleeeee.geekhubgrouplist.util;

import android.provider.ContactsContract;

public class Constants {

    public static final String[] FROM = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER};
    public static final int LOADER_ID = 1;
    public static final String ASC = " ASC";
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    public static final int PERMS_REQUEST_CODE = 123;
    public static final String GITHUB_HOST = "github.com";
    public static final String GOOGLE_HOST = "plus.google.com";
    public static final int RESULT_LOAD_IMAGE = 1;
    public static final String TYPE = "image/*";
    public static final String API_KEY = "AIzaSyCQTQH6bRN7kjHEymBW2y8Nam21ObklII4";
    public static final String DISPLAY_NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    public static final String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    public static final String KEY = "key";
    public static final String GIT_BASE_URL = "https://api.github.com";
    public static final String GOOGLE_BASE_URL = "https://www.googleapis.com";
    public static final String NAME_IN_LOW_CASE = "nameInLowerCase";
    public static boolean isCreated;


}
