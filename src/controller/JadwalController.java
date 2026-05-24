package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import model.DatabaseConnection;

public class JadwalController {

    // =========================
    // CREATE / INSERT DATA
    // =========================
    public void simpanData(
            String username,
            String matkul,
            String hari,
            String jam,
            String ruangan
    ) {

        try {

            Connection conn =
                    DatabaseConnection.connect();

            if (conn == null) {
                System.out.println("Operasi dibatalkan karena koneksi database gagal.");
                return;
            }

            String sql =
                    "INSERT INTO jadwal(username, mata_kuliah, hari, jam, ruangan) VALUES(?,?,?,?,?)";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, matkul);
            ps.setString(3, hari);
            ps.setString(4, jam);
            ps.setString(5, ruangan);

            ps.executeUpdate();

            System.out.println("Data berhasil disimpan");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public void simpanData(String matkul, String hari, String jam, String ruangan) {
        simpanData("Mahasiswa", matkul, hari, jam, ruangan);
    }

    // =========================
    // READ DATA
    // =========================
    public void tampilData(String username) {

        try {

            Connection conn =
                    DatabaseConnection.connect();

            if (conn == null) {
                System.out.println("Operasi dibatalkan karena koneksi database gagal.");
                return;
            }

            String sql =
                    "SELECT * FROM jadwal WHERE username=? ORDER BY id DESC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                System.out.println(
                        "ID : " + rs.getInt("id")
                );

                System.out.println(
                        "Mata Kuliah : "
                        + rs.getString("mata_kuliah")
                );

                System.out.println(
                        "Hari : "
                        + rs.getString("hari")
                );

                System.out.println(
                        "Jam : "
                        + rs.getString("jam")
                );

                System.out.println(
                        "Ruangan : "
                        + rs.getString("ruangan")
                );

                System.out.println("===================");

            }

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public void tampilData() {
        tampilData("Mahasiswa");
    }

    // =========================
    // UPDATE DATA
    // =========================
    public void updateData(
            String username,
            int id,
            String matkul,
            String hari,
            String jam,
            String ruangan
    ) {

        try {

            Connection conn =
                    DatabaseConnection.connect();

            if (conn == null) {
                System.out.println("Operasi dibatalkan karena koneksi database gagal.");
                return;
            }

            String sql =
                    "UPDATE jadwal SET "
                    + "mata_kuliah=?, "
                    + "hari=?, "
                    + "jam=?, "
                    + "ruangan=? "
                    + "WHERE id=? AND username=?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, matkul);
            ps.setString(2, hari);
            ps.setString(3, jam);
            ps.setString(4, ruangan);
            ps.setInt(5, id);
            ps.setString(6, username);

            ps.executeUpdate();

            System.out.println("Data berhasil diupdate");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public void updateData(int id, String matkul, String hari, String jam, String ruangan) {
        updateData("Mahasiswa", id, matkul, hari, jam, ruangan);
    }

    // =========================
    // DELETE DATA
    // =========================
    public void hapusData(String username, int id) {

        try {

            Connection conn =
                    DatabaseConnection.connect();

            if (conn == null) {
                System.out.println("Operasi dibatalkan karena koneksi database gagal.");
                return;
            }

            String sql =
                    "DELETE FROM jadwal WHERE id=? AND username=?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setString(2, username);

            ps.executeUpdate();

            System.out.println("Data berhasil dihapus");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public void hapusData(int id) {
        hapusData("Mahasiswa", id);
    }
}
