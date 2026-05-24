package view;

import controller.TugasController;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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
import javax.swing.table.DefaultTableModel;
import model.DatabaseConnection;

public class TugasForm extends JFrame {

    private final String username;
    private JTextField txtId;
    private JTextField txtNamaTugas;
    private JTextField txtMataKuliah;
    private JTextField txtDeadline;
    private JComboBox<String> cmbStatus;
    private JTable table;
    private DefaultTableModel tableModel;
    private final TugasController controller = new TugasController();

    public TugasForm() {
        this("Mahasiswa");
    }

    public TugasForm(String username) {
        this.username = username;
        initComponents();
        loadTable();
    }

    private void initComponents() {
        AppTheme.setupFrame(this, "Data Tugas - Pengingat Jadwal Kuliah", 1080, 680);

        JPanel root = AppTheme.root();
        setContentPane(root);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JPanel titleBox = new JPanel();
        titleBox.setOpaque(false);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.add(AppTheme.title("Data Tugas Kuliah"));
        titleBox.add(Box.createVerticalStrut(5));
        titleBox.add(AppTheme.subtitle("Catat tugas, mata kuliah, deadline, dan status pengerjaan."));

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

        JLabel formTitle = new JLabel("Form Tugas");
        formTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        formTitle.setForeground(AppTheme.TEXT);
        formCard.add(formTitle, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        txtId = AppTheme.textField();
        txtId.setEnabled(false);
        txtNamaTugas = AppTheme.textField();
        txtMataKuliah = AppTheme.textField();
        txtDeadline = AppTheme.textField();
        cmbStatus = AppTheme.comboBox(new String[]{"Belum Selesai", "Selesai"});

        form.add(AppTheme.label("ID"), AppTheme.gbc(0, 0));
        form.add(txtId, AppTheme.gbc(1, 0));
        form.add(AppTheme.label("Nama Tugas"), AppTheme.gbc(0, 1));
        form.add(txtNamaTugas, AppTheme.gbc(1, 1));
        form.add(AppTheme.label("Mata Kuliah"), AppTheme.gbc(0, 2));
        form.add(txtMataKuliah, AppTheme.gbc(1, 2));
        form.add(AppTheme.label("Deadline"), AppTheme.gbc(0, 3));
        form.add(txtDeadline, AppTheme.gbc(1, 3));
        form.add(AppTheme.label("Status"), AppTheme.gbc(0, 4));
        form.add(cmbStatus, AppTheme.gbc(1, 4));

        JLabel hint = AppTheme.subtitle("Format deadline: yyyy-mm-dd, contoh 2026-01-01");
        
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

        JPanel leftContent = new JPanel(new BorderLayout(10, 15));
        leftContent.setOpaque(false);
        leftContent.add(form, BorderLayout.NORTH);
        leftContent.add(hint, BorderLayout.CENTER);
        leftContent.add(buttonPanel, BorderLayout.SOUTH);
        formCard.add(leftContent, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nama Tugas", "Mata Kuliah", "Deadline", "Status"}, 0) {
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
        JLabel tableTitle = new JLabel("Tabel Tugas");
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
        controller.simpanTugas(username, txtNamaTugas.getText().trim(), txtMataKuliah.getText().trim(), txtDeadline.getText().trim(), cmbStatus.getSelectedItem().toString());
        JOptionPane.showMessageDialog(this, "Data tugas berhasil disimpan.");
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
        controller.updateTugas(username, id, txtNamaTugas.getText().trim(), txtMataKuliah.getText().trim(), txtDeadline.getText().trim(), cmbStatus.getSelectedItem().toString());
        JOptionPane.showMessageDialog(this, "Data tugas berhasil diubah.");
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
            controller.hapusTugas(username, id);
            JOptionPane.showMessageDialog(this, "Data tugas berhasil dihapus.");
            loadTable();
            clearForm();
        }
    }

    private boolean validateInput() {
        if (txtNamaTugas.getText().trim().isEmpty() || txtMataKuliah.getText().trim().isEmpty() || txtDeadline.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tugas, mata kuliah, dan deadline wajib diisi.");
            return false;
        }
        try {
            LocalDate.parse(txtDeadline.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Format deadline harus yyyy-MM-dd. Contoh: 2026-05-21");
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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tugas WHERE username=? ORDER BY id DESC");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nama_tugas"),
                    rs.getString("mata_kuliah"),
                    rs.getString("deadline"),
                    rs.getString("status")
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
            txtNamaTugas.setText(table.getValueAt(row, 1).toString());
            txtMataKuliah.setText(table.getValueAt(row, 2).toString());
            txtDeadline.setText(table.getValueAt(row, 3).toString());
            cmbStatus.setSelectedItem(table.getValueAt(row, 4).toString());
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtNamaTugas.setText("");
        txtMataKuliah.setText("");
        txtDeadline.setText("");
        cmbStatus.setSelectedIndex(0);
        table.clearSelection();
    }

    private void backToDashboard() {
        new Dashboard(username).setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TugasForm().setVisible(true));
    }
}
