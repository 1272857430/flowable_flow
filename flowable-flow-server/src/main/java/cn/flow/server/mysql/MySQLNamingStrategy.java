package cn.flow.server.mysql;

import org.hibernate.cfg.ImprovedNamingStrategy;

public class MySQLNamingStrategy extends ImprovedNamingStrategy {

    @Override
    public String tableName(String tableName) {
        return tableName.toUpperCase();
    }
}
