
package view;

import controller.UserController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class RegisterForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtKonfirmasiPassword;
    private final UserController userController = new UserController();

    public RegisterForm() {
        initComponents();
        clearFields();
    }

    private void initComponents() {
        AppTheme.setupFrame(this, "Register - Pengingat Jadwal Kuliah", 1060, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.BACKGROUND);
        setContentPane(root);

        root.add(createLeftPanel(), BorderLayout.WEST);
        root.add(createRegisterPanel(), BorderLayout.CENTER);

    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(AppTheme.PRIMARY_DARK);
        leftPanel.setPreferredSize(new Dimension(400, 620));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(58, 42, 42, 42));

        JLabel appName = new JLabel(AppTheme.htmlWidth("Buat Akun<br>Baru", 290));
        appName.setForeground(Color.WHITE);
        appName.setFont(new Font("Segoe UI", Font.BOLD, 32));
        appName.setAlignmentX(LEFT_ALIGNMENT);

        JLabel desc = new JLabel(AppTheme.htmlWidth("Daftarkan username dan password baru. Setelah berhasil, kamu bisa login kembali menggunakan akun tersebut.", 245));
        desc.setForeground(AppTheme.SOFT_BLUE);
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        desc.setAlignmentX(LEFT_ALIGNMENT);

        JPanel badge = new AppTheme.RoundedPanel(24, new Color(59, 130, 246));
        badge.setLayout(new BorderLayout());
        badge.setMaximumSize(new Dimension(320, 100));
        badge.setBorder(new EmptyBorder(16, 20, 16, 20));
        JLabel badgeText = new JLabel(AppTheme.htmlWidth("Form register selalu dibuka dalam keadaan kosong", 230));
        badgeText.setForeground(Color.WHITE);
        badgeText.setFont(new Font("Segoe UI", Font.BOLD, 15));
        badge.add(badgeText, BorderLayout.CENTER);
        badge.setAlignmentX(LEFT_ALIGNMENT);

        leftPanel.add(appName);
        leftPanel.add(Box.createVerticalStrut(18));
        leftPanel.add(desc);
        leftPanel.add(Box.createVerticalStrut(36));
        leftPanel.add(badge);
        leftPanel.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("Registrasi Akun Mahasiswa");
        footer.setForeground(new Color(191, 219, 254));
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        footer.setAlignmentX(LEFT_ALIGNMENT);
        leftPanel.add(footer);

        return leftPanel;
    }

    private JPanel createRegisterPanel() {
        JPanel formArea = new JPanel(new GridBagLayout());
        formArea.setBackground(AppTheme.BACKGROUND);
        formArea.setBorder(new EmptyBorder(30, 20, 30, 30));

        AppTheme.RoundedPanel card = AppTheme.card();
        card.setPreferredSize(new Dimension(500, 515));

        JPanel cardContent = new JPanel();
        cardContent.setOpaque(false);
        cardContent.setLayout(new BoxLayout(cardContent, BoxLayout.Y_AXIS));

        JLabel title = AppTheme.title("Register");
        JLabel subtitle = AppTheme.subtitle(AppTheme.htmlWidth("Isi data akun baru. Semua kolom wajib diisi.", 320));
        JLabel lblUsername = AppTheme.label("Username");
        JLabel lblPassword = AppTheme.label("Password");
        JLabel lblKonfirmasi = AppTheme.label("Konfirmasi Password");

        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblKonfirmasi.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtUsername = AppTheme.textField();
        txtPassword = AppTheme.passwordField();
        txtKonfirmasiPassword = AppTheme.passwordField();
        txtUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtKonfirmasiPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnDaftar = AppTheme.button("Daftar", AppTheme.SUCCESS);
        JButton btnKembali = AppTheme.button("Kembali", AppTheme.PRIMARY);

        btnDaftar.addActionListener(e -> daftar());
        btnKembali.addActionListener(e -> kembaliLogin());

        cardContent.add(title);
        cardContent.add(Box.createVerticalStrut(8));
        cardContent.add(subtitle);
        cardContent.add(Box.createVerticalStrut(28));
        cardContent.add(lblUsername);
        cardContent.add(Box.createVerticalStrut(6));
        cardContent.add(txtUsername);
        cardContent.add(Box.createVerticalStrut(15));
        cardContent.add(lblPassword);
        cardContent.add(Box.createVerticalStrut(6));
        cardContent.add(txtPassword);
        cardContent.add(Box.createVerticalStrut(15));
        cardContent.add(lblKonfirmasi);
        cardContent.add(Box.createVerticalStrut(6));
        cardContent.add(txtKonfirmasiPassword);
        cardContent.add(Box.createVerticalStrut(25));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        actions.setAlignmentX(Component.LEFT_ALIGNMENT);
        actions.setOpaque(false);
        actions.add(btnDaftar);
        actions.add(Box.createHorizontalStrut(14));
        actions.add(btnKembali);
        cardContent.add(actions);

        JLabel hint = AppTheme.subtitle(AppTheme.htmlWidth("Setelah daftar berhasil, halaman login akan dikosongkan.", 320));
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        hint.setBorder(new EmptyBorder(22, 0, 0, 0));
        cardContent.add(hint);

        card.add(cardContent, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        formArea.add(card, gbc);
        return formArea;
    }

    private void daftar() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String konfirmasi = new String(txtKonfirmasiPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty() || konfirmasi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom register wajib diisi.");
            return;
        }

        if (!password.equals(konfirmasi)) {
            JOptionPane.showMessageDialog(this, "Password dan konfirmasi password tidak sama.");
            txtPassword.setText("");
            txtKonfirmasiPassword.setText("");
            txtPassword.requestFocus();
            return;
        }

        boolean berhasil = userController.register(username, password);
        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Register berhasil. Silakan login dengan akun baru.");
            clearFields();
            kembaliLogin();
        } else {
            JOptionPane.showMessageDialog(this, "Register gagal. Username mungkin sudah terdaftar atau koneksi database bermasalah.");
        }
    }

    private void kembaliLogin() {
        clearFields();
        new LoginForm().setVisible(true);
        dispose();
    }

    private void clearFields() {
        if (txtUsername != null) {
            txtUsername.setText("");
            txtPassword.setText("");
            txtKonfirmasiPassword.setText("");
            txtUsername.requestFocus();
        }
    }
}
