package com.example.summercoding;

import android.provider.BaseColumns;

public final class DataBases {

    public static final class CreateDB implements BaseColumns{
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String CONTENT = "content";
        public static final String _TABLENAME0 = "Schedule";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +TITLE+" text not null , "
                +DATE+" text not null , "
                +CONTENT+" text not null );";
    }
}
