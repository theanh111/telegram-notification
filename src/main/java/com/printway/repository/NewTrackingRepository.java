package com.printway.repository;

import com.printway.config.Database;
import java.sql.*;

public class NewTrackingRepository {
    private final String url = getDatabase().getUrl();
    private final String username = getDatabase().getUsername();
    private final String password = getDatabase().getPassword();

    public NewTrackingRepository() {
    }

    public Database getDatabase() {
        Database database = new Database();
        database.getUrl();
        database.getUsername();
        database.getPassword();
        return database;
    }

    public Integer countErrorTracking() {
        Integer totalErrorTracking = null;
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement statement = conn.createStatement();
            String sqlQuery = "SELECT COUNT(multran_log) as totalErrorTracking FROM tblnewtracking N WHERE LENGTH(multran_log)>0 AND N.shipping_country = 'US' AND (LENGTH(N.partner_tracking_number) IS NULL) AND N.shipping_city NOT IN ('APO', 'AE', 'FPO','PR', 'VI', 'GU') AND (LENGTH(N.dtime_last_changed) IS NULL)";
            ResultSet rs = statement.executeQuery(sqlQuery);
            if (rs.next()) {
                totalErrorTracking = rs.getInt("totalErrorTracking");
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalErrorTracking;
    }

    public Integer countLabeled(String currentDate) {
        Integer totalLabeled = null;
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sqlQuery = "SELECT COUNT(1) as totalLabeled FROM (SELECT distinct O.order_id FROM tblneworders O WHERE O.status_production=6 AND O.dtime_entered LIKE ? AND O.order_id IN (SELECT N.order_id FROM tblnewtracking N )) TMP WHERE 1=1";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, currentDate + "%");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                totalLabeled = rs.getInt("totalLabeled");
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalLabeled;
    }

    public Integer countShipped(String currentDate) {
        Integer totalShipped = null;

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sqlQuery = "SELECT COUNT(1) as totalShipped FROM (SELECT distinct O.order_id FROM tblneworders O WHERE O.status_production=6 AND O.dtime_entered LIKE ? AND O.order_id IN (SELECT N.order_id FROM tblnewtracking N WHERE N.shipped=1)) TMP WHERE 1=1 ";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, currentDate + "%");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                totalShipped = rs.getInt("totalShipped");
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalShipped;
    }
}
