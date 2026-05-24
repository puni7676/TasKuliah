package view;

import controller.JadwalController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.DatabaseConnection;

public class JadwalForm extends JFrame {

    private final String username;
    private JTextField txtId;
    private JTextField txtMataKuliah;
    private JComboBox<String> cmbHari;
    private JTextField txtJam;
    private JTextField txtRuangan;
    private JTable table;
    private DefaultTableModel tableModel;
    private final JadwalController controller = new JadwalController();

    public JadwalForm() {
        this("Mahasiswa");
    }

    public JadwalForm(String username) {
        this.username = username;
        initComponents();
        loadTable();
    }

    private void initComponents() {
        AppTheme.setupFrame(this, "Data Jadwal - Pengingat Jadwal Kuliah", 1080, 680);

        JPanel root = AppTheme.root();
        setContentPane(root);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JPanel titleBox = new JPanel();
        titleBox.setOpaque(false);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.add(AppTheme.title("Data Jadwal Kuliah"));
        titleBox.add(Box.createVerticalStrut(5));
        titleBox.add(AppTheme.subtitle("Tambah, ubah, dan hapus jadwal perkuliahan."));

        JButton btnBack = AppTheme.button("Kembali", AppTheme.PRIMARY_DARK);
        btnBack.addActionListener(e -> backToDashboard());
        header.add(titleBox, BorderLayout.WEST);
        header.add(btnBack, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(18, 18));
        content.setOpaque(false);
        root.add(content, BorderLayout.CENTER);

        AppTheme.RoundedPanel formCard = AppTheme.card();
        formCard.setLayout(new BorderLayout(10, 10));

        JLabel formTitle = new JLabel("Form Jadwal");
        formTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        formTitle.setForeground(AppTheme.TEXT);
        formCard.add(formTitle, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        txtId = AppTheme.textField();
        txtId.setEnabled(false);
        txtMataKuliah = AppTheme.textField();
        cmbHari = AppTheme.comboBox(new String[]{"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"});
        txtJam = AppTheme.textField();
        txtRuangan = AppTheme.textField();

        form.add(AppTheme.label("ID"), AppTheme.gbc(0, 0));
        form.add(txtId, AppTheme.gbc(1, 0));
        form.add(AppTheme.label("Mata Kuliah"), AppTheme.gbc(0, 1));
        form.add(txtMataKuliah, AppTheme.gbc(1, 1));
        form.add(AppTheme.label("Hari"), AppTheme.gbc(0, 2));
        form.add(cmbHari, AppTheme.gbc(1, 2));
        form.add(AppTheme.label("Jam"), AppTheme.gbc(0, 3));
        form.add(txtJam, AppTheme.gbc(1, 3));
        form.add(AppTheme.label("Ruangan"), AppTheme.gbc(0, 4));
        form.add(txtRuangan, AppTheme.gbc(1, 4));

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setOpaque(false);
        JButton btnSimpan = AppTheme.button("Simpan", AppTheme.SUCCESS);
        JButton btnUbah = AppTheme.button("Ubah", AppTheme.PRIMARY);
        JButton btnHapus = AppTheme.button("Hapus", AppTheme.DANGER);
        JButton btnClear = AppTheme.button("Bersihkan", AppTheme.MUTED);
        btnSimpan.addActionListener(e -> saveData());
        btnUbah.addActionListener(e -> updateData());
        btnHapus.addActionListener(e -> deleteData());
        btnClear.addActionListener(e -> clearForm());
        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnUbah);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnClear);

        JPanel leftContent = new JPanel(new BorderLayout(10, 18));
        leftContent.setOpaque(false);
        leftContent.add(form, BorderLayout.CENTER);
        leftContent.add(buttonPanel, BorderLayout.SOUTH);
        formCard.add(leftContent, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Mata Kuliah", "Hari", "Jam", "Ruangan"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        AppTheme.styleTable(table);
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());

        AppTheme.RoundedPanel tableCard = AppTheme.card();
        tableCard.setLayout(new BorderLayout(10, 12));
        JPanel tableHeader = new JPanel(new BorderLayout());
        tableHeader.setOpaque(false);
        JLabel tableTitle = new JLabel("Tabel Jadwal");
        tableTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        tableTitle.setForeground(AppTheme.TEXT);
        JButton btnRefresh = AppTheme.button("Refresh", AppTheme.SECONDARY);
        btnRefresh.addActionListener(e -> loadTable());
        tableHeader.add(tableTitle, BorderLayout.WEST);
        tableHeader.add(btnRefresh, BorderLayout.EAST);
        tableCard.add(tableHeader, BorderLayout.NORTH);
        tableCard.add(AppTheme.scrollPane(table), BorderLayout.CENTER);

        content.add(formCard, BorderLayout.WEST);
        content.add(tableCard, BorderLayout.CENTER);
    }

    private void saveData() {
        if (!validateInput()) {
            return;
        }
        controller.simpanData(username, txtMataKuliah.getText().trim(), cmbHari.getSelectedItem().toString(), txtJam.getText().trim(), txtRuangan.getText().trim());
        JOptionPane.showMessageDialog(this, "Data jadwal berhasil disimpan.");
        loadTable();
        clearForm();
    }

    private void updateData() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diubah dari tabel.");
            return;
        }
        if (!validateInput()) {
            return;
        }
        int id = Integer.parseInt(txtId.getText().trim());
        controller.updateData(username, id, txtMataKuliah.getText().trim(), cmbHari.getSelectedItem().toString(), txtJam.getText().trim(), txtRuangan.getText().trim());
        JOptionPane.showMessageDialog(this, "Data jadwal berhasil diubah.");
        loadTable();
        clearForm();
    }

    private void deleteData() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus dari tabel.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText().trim());
            controller.hapusData(username, id);
            JOptionPane.showMessageDialog(this, "Data jadwal berhasil dihapus.");
            loadTable();
            clearForm();
        }
    }

    private boolean validateInput() {
        if (txtMataKuliah.getText().trim().isEmpty() || txtJam.getText().trim().isEmpty() || txtRuangan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mata kuliah, jam, dan ruangan wajib diisi.");
            return false;
        }
        return true;
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.connect();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Koneksi database gagal. Pastikan MySQL aktif dan database sudah di-import.");
                return;
            }
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM jadwal WHERE username=? ORDER BY id DESC");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("mata_kuliah"),
                    rs.getString("hari"),
                    rs.getString("jam"),
                    rs.getString("ruangan")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan data: " + e.getMessage());
        }
    }

    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtId.setText(table.getValueAt(row, 0).toString());
            txtMataKuliah.setText(table.getValueAt(row, 1).toString());
            cmbHari.setSelectedItem(table.getValueAt(row, 2).toString());
            txtJam.setText(table.getValueAt(row, 3).toString());
            txtRuangan.setText(table.getValueAt(row, 4).toString());
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtMataKuliah.setText("");
        cmbHari.setSelectedIndex(0);
        txtJam.setText("");
        txtRuangan.setText("");
        table.clearSelection();
    }

    private void backToDashboard() {
        new Dashboard(username).setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JadwalForm().setVisible(true));
    }
}
