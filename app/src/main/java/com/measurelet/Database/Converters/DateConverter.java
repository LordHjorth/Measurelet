package com.measurelet.Database.Converters;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.room.TypeConverter;

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long ldate){
        return ldate == null ? null : new Date(ldate);
    }

    @TypeConverter
    public static Long fromDate(Date date){

        return date == null ? null : date.getTime();
    }

}
