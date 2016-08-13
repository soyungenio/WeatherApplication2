package com.example.marat.someapplication;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                   // FeedEntry._ID  + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_CITY + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_COUN + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_SKY + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_TEMPERATURE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_PRESSURE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_HUMIDITY + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_WINDSPEED + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_TIME + TEXT_TYPE +" )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        // Weather table name
        public static final String TABLE_NAME = "weather";

        // Weather Table Columns names
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_COUN = "country";
        public static final String COLUMN_NAME_SKY = "sky";
        public static final String COLUMN_NAME_TEMPERATURE = "temp";
        public static final String COLUMN_NAME_PRESSURE = "press";
        public static final String COLUMN_NAME_HUMIDITY = "hum";
        public static final String COLUMN_NAME_WINDSPEED = "speed";
        public static final String COLUMN_NAME_TIME = "time";
    }
}


