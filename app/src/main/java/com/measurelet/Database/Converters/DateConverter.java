package com.measurelet.Database.Converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

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
