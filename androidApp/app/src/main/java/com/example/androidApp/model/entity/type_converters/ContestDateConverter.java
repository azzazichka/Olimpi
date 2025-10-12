package com.example.androidApp.model.entity.type_converters;

import static com.example.androidApp.model.entity.type_converters.DateConverter.date2String;
import static com.example.androidApp.model.entity.type_converters.DateConverter.string2Date;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ContestDateConverter {
    @TypeConverter
    public String fromContestDate(Date contestDate) {
        return date2String(contestDate, "yyyy-MM-dd");
    }

    @TypeConverter
    public Date toContestDate(String contestDate) {
        return string2Date(contestDate, "yyyy-MM-dd");
    }
}
