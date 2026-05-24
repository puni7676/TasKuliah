package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static Connection connection;
    private static boolean migrated = false;

    private static final String URL = "jdbc:mysql://localhost:3303/kuliah_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() {
        return getConnection();
    }

    public static Connection getConnection() {

        try {
            if (connection != null && !connection.isClosed()) {
                ensureUserColumns(connection);
                return connection;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            ensureUserColumns(connection);

            System.out.println("Koneksi Berhasil");

        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL tidak ditemukan. Pastikan mysql-connector-j sudah ada di Libraries.");
        } catch (SQLException e) {
            System.out.println("Koneksi Gagal : " + e.getMessage());
        }

        return connection;
    }

    private static void ensureUserColumns(Connection conn) {
        if (migrated || conn == null) {
            return;
        }

        try {
            addColumnIfMissing(conn, "jadwal", "username", "VARCHAR(50) NULL");
            addColumnIfMissing(conn, "tugas", "username", "VARCHAR(50) NULL");
            migrated = true;
        } catch (Exception e) {
            System.out.println("Migrasi kolom username gagal: " + e.getMessage());
        }
    }

    private static void addColumnIfMissing(Connection conn, String tableName, String columnName, String definition) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS "
                + "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ? AND COLUMN_NAME = ?";

        PreparedStatement check = conn.prepareStatement(checkSql);
        check.setString(1, tableName);
        check.setString(2, columnName);
        ResultSet rs = check.executeQuery();

        if (rs.next() && rs.getInt(1) == 0) {
            Statement st = conn.createStatement();
            st.executeUpdate("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + definition + " AFTER id");
            st.close();
        }

        rs.close();
        check.close();
    }
}
