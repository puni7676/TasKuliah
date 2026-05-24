package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.DatabaseConnection;

public class UserController {

    // LOGIN USER
    public boolean login(String username, String password) {

        try {

            Connection conn =
                    DatabaseConnection.connect();

            if (conn == null) {
                System.out.println("Login dibatalkan karena koneksi database gagal.");
                return false;
            }

            String sql =
                    "SELECT * FROM user "
                    + "WHERE username=? "
                    + "AND password=?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                System.out.println("Login berhasil");

                return true;

            } else {

                System.out.println("Username atau password salah");

                return false;

            }

        } catch (Exception e) {

            System.out.println(e.getMessage());

            return false;

        }
    }

    // CEK USERNAME SUDAH ADA ATAU BELUM
    public boolean usernameSudahAda(String username) {
        try {
            Connection conn = DatabaseConnection.connect();

            if (conn == null) {
                System.out.println("Cek username dibatalkan karena koneksi database gagal.");
                return true;
            }

            String sql = "SELECT id FROM user WHERE username=? LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    // REGISTER USER
    public boolean register(String username, String password) {

        try {

            Connection conn =
                    DatabaseConnection.connect();

            if (conn == null) {
                System.out.println("Register dibatalkan karena koneksi database gagal.");
                return false;
            }

            if (usernameSudahAda(username)) {
                System.out.println("Username sudah terdaftar");
                return false;
            }

            String sql =
                    "INSERT INTO user(username, password) VALUES(?, ?)";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();

            System.out.println("Register berhasil");
            return true;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return false;

        }
    }
}
