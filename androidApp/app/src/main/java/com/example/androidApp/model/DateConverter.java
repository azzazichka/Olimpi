package com.example.androidApp.model;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    public static Date string2Date(String date, @Nullable String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd";
        }

        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String date2String(Date date, @Nullable String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd";
        }

        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
        return formatter.format(date);
    }
}
