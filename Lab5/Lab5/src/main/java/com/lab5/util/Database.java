package com.lab5.util;


import com.lab5.entity.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Database {
    public static Map<String, Student> map = new HashMap<>();

    static {
        map.put(getKey(), new Student("SV001", "Ly Thai To bai 3", true, 9.5));
        map.put(getKey(), new Student("SV002", "Le Trong Tan bai 3", true, 4.5));
        map.put(getKey(), new Student("SV003", "Nguyen Thi Minh Khai bai 3", false, 9.5));
        map.put(getKey(), new Student("SV004", "Doan Trung Truc bai 3", true, 6.0));
    }

    public static String getKey() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}