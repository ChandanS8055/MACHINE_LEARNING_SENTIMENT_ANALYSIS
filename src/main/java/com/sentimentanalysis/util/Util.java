package com.sentimentanalysis.util;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Util
{
    public static String generateID() {
        final SimpleDateFormat sdf = new SimpleDateFormat("ddMMhhmmsss");
        return sdf.format(new Timestamp(System.currentTimeMillis()));
    }
    
    public static String generateverificationcode() {
        final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmssSSS");
        return sdf.format(new Timestamp(System.currentTimeMillis()));
    }
}
