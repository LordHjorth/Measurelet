package com.measurelet.Database.Converters;

import android.arch.persistence.room.TypeConverter;

import com.measurelet.Enums;

import java.util.UUID;

public class IntakeTypeConverter {
    @TypeConverter
    public static Enums.IntakeType toEnum(String sType){
        return sType == null ? null : Enums.IntakeType.valueOf(sType);
    }

    @TypeConverter
    public static String fromEnum(Enums.IntakeType intakeType){
        return intakeType == null ? null : intakeType.name();
    }
}
