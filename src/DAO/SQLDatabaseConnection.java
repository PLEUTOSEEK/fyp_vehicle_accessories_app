/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.*;

public class SQLDatabaseConnection {

    // Connect to your database.
    // Replace server name, username, and password with your credentials
    private static Connection getConnection(String sqlServer, String db, String user, String password) {

        String connectionUrl
                = "jdbc:sqlserver://" + sqlServer + ":1433;"
                + "database=" + db + ";"
                + "user=" + user + ";"
                + "password=" + password + ";"
                + "encrypt=true;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";


        /*String connectionUrl
                = "jdbc:sqlserver://" + sqlServer + ":1400;"
                + "DatabaseName=" + db + ";"
                + "user=" + user + ";"
                + "password=" + password + ";"
                + "encrypt=false;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";*/
        try {
            // Code here.
            Connection connection = DriverManager.getConnection(connectionUrl);

            System.out.println("Success Connect to " + db);
            return connection;

        } // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection openConn() {
        /*Connection con = getConnection(
                "vehicle-accessories--server.database.windows.net",
                "vehicle-accessories",
                "TeeZhuoXuan@vehicle-accessories--server",
                "oS6*3y57SnP2lv7tkBehY5Y6YbmR7n");*/

        Connection con = getConnection(
                "localhost",
                "vehicle-accessories-2022-8-9-8-57",
                "sa",
                "wKKT@NBKP3sxiC8");

        return con;
    }

    public static void main(String[] args) {
        openConn();
    }
}
