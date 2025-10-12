package com.example.androidApp.model.entity.type_converters;

import static com.example.androidApp.model.entity.type_converters.DateConverter.date2String;
import static com.example.androidApp.model.entity.type_converters.DateConverter.string2Date;

import androidx.room.TypeConverter;

import java.util.Date;

public class EventDateConverter {
    @TypeConverter
    public String fromEventDate(Date eventDate) {
        return date2String(eventDate, "yyyy-MM-dd HH:mm:ss");
    }

    @TypeConverter
    public Date toEventDate(String eventDate) {
        return string2Date(eventDate, "yyyy-MM-dd HH:mm:ss");
    }
}
