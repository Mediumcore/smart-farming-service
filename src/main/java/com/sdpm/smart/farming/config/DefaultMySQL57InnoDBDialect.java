package com.sdpm.smart.farming.config;

import org.hibernate.dialect.MySQL55Dialect;

/**
 * @author shirukai
 */
public class DefaultMySQL57InnoDBDialect extends MySQL55Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
