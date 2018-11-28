package com.measurelet.Database.Converters;

import android.arch.persistence.room.TypeConverter;

import java.util.UUID;

public class IDConverter {

    @TypeConverter
    public static UUID toUUID(String sUUID){
        return sUUID == null ? UUID.randomUUID() : UUID.fromString(sUUID);
    }

    @TypeConverter
    public static String fromUUID(UUID id){

        return id == null ? null : id.toString();
    }
}
