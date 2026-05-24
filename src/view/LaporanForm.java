package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.DatabaseConnection;

public class LaporanForm extends JFrame {

    private final String username;
    private JLabel lblJadwal;
    private JLabel lblTugas;
    private JLabel lblSelesai;
    private JLabel lblBelum;
    private JTable tableJadwal;
    private JTable tableTugas;
    private DefaultTableModel modelJadwal;
    private DefaultTableModel modelTugas;

    public LaporanForm() {
        this("Mahasiswa");
    }

    public LaporanForm(String username) {
        this.username = username;
        initComponents();
        loadData();
    }

    private void initComponents() {
        AppTheme.setupFrame(this, "Laporan - Pengingat Jadwal Kuliah", 1080, 680);

        JPanel root = AppTheme.root();
        setContentPane(root);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JPanel titleBox = new JPanel();
        titleBox.setOpaque(false);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.add(AppTheme.title("Laporan Akademik"));
        titleBox.add(Box.createVerticalStrut(5));
        titleBox.add(AppTheme.subtitle("Ringkasan jadwal dan tugas kuliah yang tersimpan."));

        JPanel headerButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerButtons.setOpaque(false);
        JButton btnRefresh = AppTheme.button("Refresh", AppTheme.SECONDARY);
        JButton btnBack = AppTheme.button("Kembali", AppTheme.PRIMARY_DARK);
        btnRefresh.addActionListener(e -> loadData());
        btnBack.addActionListener(e -> backToDashboard());
        headerButtons.add(btnRefresh);
        headerButtons.add(btnBack);

        header.add(titleBox, BorderLayout.WEST);
        header.add(headerButtons, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(18, 18));
        content.setOpaque(false);
        root.add(content, BorderLayout.CENTER);

        JPanel cards = new JPanel(new GridLayout(1, 4, 14, 14));
        cards.setOpaque(false);
        lblJadwal = numberLabel();
        lblTugas = numberLabel();
        lblSelesai = numberLabel();
        lblBelum = numberLabel();
        cards.add(summaryCard("Jadwal", lblJadwal, AppTheme.PRIMARY));
        cards.add(summaryCard("Tugas", lblTugas, AppTheme.SECONDARY));
        cards.add(summaryCard("Selesai", lblSelesai, AppTheme.SUCCESS));
        cards.add(summaryCard("Belum", lblBelum, AppTheme.WARNING));

        modelJadwal = new DefaultTableModel(new Object[]{"ID", "Mata Kuliah", "Hari", "Jam", "Ruangan"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelTugas = new DefaultTableModel(new Object[]{"ID", "Nama Tugas", "Mata Kuliah", "Deadline", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableJadwal = new JTable(modelJadwal);
        tableTugas = new JTable(modelTugas);
        AppTheme.styleTable(tableJadwal);
        AppTheme.styleTable(tableTugas);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.addTab("Laporan Jadwal", createTablePanel(tableJadwal, "Cetak Jadwal"));
        tabs.addTab("Laporan Tugas", createTablePanel(tableTugas, "Cetak Tugas"));

        AppTheme.RoundedPanel tableCard = AppTheme.card();
        tableCard.add(tabs, BorderLayout.CENTER);

        content.add(cards, BorderLayout.NORTH);
        content.add(tableCard, BorderLayout.CENTER);
    }

    private JLabel numberLabel() {
        JLabel label = new JLabel("0");
        label.setFont(new Font("Segoe UI", Font.BOLD, 28));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JPanel summaryCard(String title, JLabel number, Color color) {
        AppTheme.RoundedPanel card = AppTheme.smallCard(color);
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        card.add(label, BorderLayout.NORTH);
        card.add(number, BorderLayout.CENTER);
        return card;
    }

    private JPanel createTablePanel(JTable table, String buttonText) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        JButton btnPrint = AppTheme.button(buttonText, AppTheme.PRIMARY);
        btnPrint.addActionListener(e -> printTable(table));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        top.setOpaque(false);
        top.add(btnPrint);
        panel.add(top, BorderLayout.NORTH);
        panel.add(AppTheme.scrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void loadData() {
        lblJadwal.setText(String.valueOf(count("SELECT COUNT(*) FROM jadwal WHERE username=?")));
        lblTugas.setText(String.valueOf(count("SELECT COUNT(*) FROM tugas WHERE username=?")));
        lblSelesai.setText(String.valueOf(count("SELECT COUNT(*) FROM tugas WHERE username=? AND status='Selesai'")));
        lblBelum.setText(String.valueOf(count("SELECT COUNT(*) FROM tugas WHERE username=? AND status='Belum Selesai'")));
        loadJadwal();
        loadTugas();
    }

    private int count(String sql) {
        try {
            Connection conn = DatabaseConnection.connect();
            if (conn == null) {
                return 0;
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    private void loadJadwal() {
        modelJadwal.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.connect();
            if (conn == null) {
                return;
            }
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM jadwal WHERE username=? ORDER BY id DESC");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelJadwal.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("mata_kuliah"),
                    rs.getString("hari"),
                    rs.getString("jam"),
                    rs.getString("ruangan")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat laporan jadwal: " + e.getMessage());
        }
    }

    private void loadTugas() {
        modelTugas.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.connect();
            if (conn == null) {
                return;
            }
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tugas WHERE username=? ORDER BY id DESC");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelTugas.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nama_tugas"),
                    rs.getString("mata_kuliah"),
                    rs.getString("deadline"),
                    rs.getString("status")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat laporan tugas: " + e.getMessage());
        }
    }

    private void printTable(JTable table) {
        try {
            boolean complete = table.print();
            if (complete) {
                JOptionPane.showMessageDialog(this, "Laporan berhasil dikirim ke printer/dialog cetak.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mencetak laporan: " + e.getMessage());
        }
    }

    private void backToDashboard() {
        new Dashboard(username).setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LaporanForm().setVisible(true));
    }
}
