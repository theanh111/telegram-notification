package com.printway.repository;

import com.printway.config.Database;
import java.sql.*;

public class NewOrderRepository {
    private final String url = getDatabase().getUrl();
    private final String username = getDatabase().getUsername();
    private final String password = getDatabase().getPassword();

    public NewOrderRepository() {
    }

    public Database getDatabase() {
        Database database = new Database();
        database.getUrl();
        database.getUsername();
        database.getPassword();
        return database;
    }

    public Integer findNewestOrder(String oneHourBack, String currentDate) {
        Integer totalNewestOrder = null;
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sqlQuery = "SELECT COUNT(DISTINCT order_id) as totalNewestOrder FROM tblneworders O WHERE O.dtime_entered >= ? AND O.dtime_entered <= ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, currentDate + "%");
            statement.setString(2, oneHourBack + "%");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                totalNewestOrder = rs.getInt("totalNewestOrder");
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalNewestOrder;
    }

    public Integer countOrder(String currentDate) {
        Integer totalOrder = null;
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sqlQuery = "SELECT COUNT(1) as totalOrder FROM (SELECT distinct O.order_id FROM tblneworders O WHERE O.dtime_entered LIKE ? AND O.order_id IN (SELECT N.order_id FROM tblnewtracking N )) TMP WHERE 1=1";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, currentDate + "%");

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                totalOrder = rs.getInt("totalOrder");
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalOrder;
    }

    public Integer countPrintQuantity(String currentDate) {
        Integer totalPrint = null;
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sqlQuery = "SELECT SUM(print_quantity) as totalPrint FROM tblneworders O WHERE O.dtime_entered LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, currentDate + "%");

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                totalPrint = rs.getInt("totalPrint");
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalPrint;
    }

    public Integer countPrintedQuantity(String currentDate) {
        Integer totalPrinted = null;
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sqlQuery = "SELECT SUM(printed_quantity) as totalPrinted FROM tblneworders O WHERE O.dtime_entered LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, currentDate + "%");

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                totalPrinted = rs.getInt("totalPrinted");
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalPrinted;
    }

    public Integer countPackagedQuantity(String currentDate) {
        Integer totalPackaged = null;
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sqlQuery = "SELECT SUM(packaged_quantity) as totalPackaged FROM tblneworders O WHERE O.dtime_entered LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, currentDate + "%");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                totalPackaged = rs.getInt("totalPackaged");
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalPackaged;
    }

    public Integer countErrorQuantityIn(String currentDate) {
        Integer totalError = null;
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sqlQuery = "SELECT SUM(error_quantity_in) as totalError FROM tblneworders O WHERE O.dtime_entered LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, currentDate + "%");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                totalError = rs.getInt("totalError");
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return totalError;
    }
}