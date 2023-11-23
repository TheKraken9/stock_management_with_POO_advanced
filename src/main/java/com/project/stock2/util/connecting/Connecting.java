package com.project.stock2.util.connecting;

import java.sql.Connection;

public class Connecting {
    public static Connection getConnection(String base) throws Exception {
        try {
            if(base.equalsIgnoreCase("mysql")){
                Class.forName("com.mysql.cj.jdbc.Driver");
                return java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "root");
            } else {
                Class.forName("org.postgresql.Driver");
                return java.sql.DriverManager.getConnection("jdbc:postgresql://localhost:5432/stock2", "postgres", "postgres");
            }
        }catch (Exception e){
            throw e;
        }
    }

    public static String generateSequence(String param, Integer number, Integer length){
        String sequence = "";
        for(int i = 0; i < length - number.toString().length(); i++){
            sequence += "0";
        }
        return param + sequence + number;
    }
}

