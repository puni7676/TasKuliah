package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.DatabaseConnection;

public class TugasController {

    // =========================
    // INSERT DATA
    // =========================
    public void simpanTugas(
            String username,
            String namaTugas,
            String mataKuliah,
            String deadline,
            String status
    ) {

        try {

            Connection conn =
                    DatabaseConnection.connect();

            if (conn == null) {
                System.out.println("Operasi dibatalkan karena koneksi database gagal.");
                return;
            }

            String sql =
                    "INSERT INTO tugas(username, nama_tugas, mata_kuliah, deadline, status) VALUES(?,?,?,?,?)";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, namaTugas);
            ps.setString(3, mataKuliah);
            ps.setString(4, deadline);
            ps.setString(5, status);

            ps.executeUpdate();

            System.out.println("Tugas berhasil disimpan");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public void simpanTugas(String namaTugas, String mataKuliah, String deadline, String status) {
        simpanTugas("Mahasiswa", namaTugas, mataKuliah, deadline, status);
    }

    // =========================
    // READ DATA
    // =========================
    public void tampilTugas(String username) {

        try {

            Connection conn =
                    DatabaseConnection.connect();

            if (conn == null) {
                System.out.println("Operasi dibatalkan karena koneksi database gagal.");
                return;
            }

            String sql =
                    "SELECT * FROM tugas WHERE username=? ORDER BY id DESC";

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
                        "Nama Tugas : "
                        + rs.getString("nama_tugas")
                );

                System.out.println(
                        "Mata Kuliah : "
                        + rs.getString("mata_kuliah")
                );

                System.out.println(
                        "Deadline : "
                        + rs.getString("deadline")
                );

                System.out.println(
                        "Status : "
                        + rs.getString("status")
                );

                System.out.println("===================");

            }

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public void tampilTugas() {
        tampilTugas("Mahasiswa");
    }

    // =========================
    // UPDATE DATA
    // =========================
    public void updateTugas(
            String username,
            int id,
            String namaTugas,
            String mataKuliah,
            String deadline,
            String status
    ) {

        try {

            Connection conn =
                    DatabaseConnection.connect();

            if (conn == null) {
                System.out.println("Operasi dibatalkan karena koneksi database gagal.");
                return;
            }

            String sql =
                    "UPDATE tugas SET "
                    + "nama_tugas=?, "
                    + "mata_kuliah=?, "
                    + "deadline=?, "
                    + "status=? "
                    + "WHERE id=? AND username=?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, namaTugas);
            ps.setString(2, mataKuliah);
            ps.setString(3, deadline);
            ps.setString(4, status);
            ps.setInt(5, id);
            ps.setString(6, username);

            ps.executeUpdate();

            System.out.println("Tugas berhasil diupdate");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public void updateTugas(int id, String namaTugas, String mataKuliah, String deadline, String status) {
        updateTugas("Mahasiswa", id, namaTugas, mataKuliah, deadline, status);
    }

    // =========================
    // DELETE DATA
    // =========================
    public void hapusTugas(String username, int id) {

        try {

            Connection conn =
                    DatabaseConnection.connect();

            if (conn == null) {
                System.out.println("Operasi dibatalkan karena koneksi database gagal.");
                return;
            }

            String sql =
                    "DELETE FROM tugas WHERE id=? AND username=?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setString(2, username);

            ps.executeUpdate();

            System.out.println("Tugas berhasil dihapus");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public void hapusTugas(int id) {
        hapusTugas("Mahasiswa", id);
    }
}
