package com.michaelyvars.guacamole.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static String getFormattedTime(int time) {
        SimpleDateFormat simpleDateFormat;

        if (time >= 3600)
            simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        else
            simpleDateFormat = new SimpleDateFormat("mm:ss");

        Date date = new Date(time * 1000L);
        return simpleDateFormat.format(date);
    }
}
