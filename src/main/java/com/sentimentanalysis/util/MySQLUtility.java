package com.sentimentanalysis.util;

import java.sql.DriverManager;
import java.sql.Connection;

public class MySQLUtility
{
    public static Connection connect() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/sentimentanalysisdb", "root", "root");
    }
}