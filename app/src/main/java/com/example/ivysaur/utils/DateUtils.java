package com.example.ivysaur.utils;

import android.annotation.SuppressLint;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static Date date = new Date();

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public static Date toDate(EditText textDate) {
        try {
            date = simpleDateFormat.parse(textDate.getText().toString());
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String toStringDate(Date date) {
        return simpleDateFormat.format(date);
    }

}
