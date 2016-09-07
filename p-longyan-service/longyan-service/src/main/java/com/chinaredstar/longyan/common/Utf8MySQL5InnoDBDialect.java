package com.chinaredstar.longyan.common;

import org.hibernate.dialect.MySQL5InnoDBDialect;

public class Utf8MySQL5InnoDBDialect extends MySQL5InnoDBDialect {
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}