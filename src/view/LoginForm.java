package view;

import controller.UserController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private final UserController userController = new UserController();

    public LoginForm() {
        initComponents();
        resetFields();
    }

    private void initComponents() {
        AppTheme.setupFrame(this, "Login - Pengingat Jadwal Kuliah", 1060, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.BACKGROUND);
        setContentPane(root);

        root.add(createLeftPanel(), BorderLayout.WEST);
        root.add(createLoginPanel(), BorderLayout.CENTER);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(AppTheme.PRIMARY_DARK);
        leftPanel.setPreferredSize(new Dimension(400, 620));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(58, 42, 42, 42));

        JLabel appName = new JLabel(AppTheme.htmlWidth("Pengingat Jadwal<br>Kuliah", 290));
        appName.setForeground(Color.WHITE);
        appName.setFont(new Font("Segoe UI", Font.BOLD, 32));
        appName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel desc = new JLabel(AppTheme.htmlWidth(
                "Aplikasi sederhana untuk mengatur jadwal perkuliahan, tugas, dan deadline agar lebih tertata.",
                245
        ));
        desc.setForeground(AppTheme.SOFT_BLUE);
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel badge = new AppTheme.RoundedPanel(24, new Color(59, 130, 246));
        badge.setLayout(new BorderLayout());
        badge.setMaximumSize(new Dimension(320, 100));
        badge.setBorder(new EmptyBorder(16, 20, 16, 20));

        JLabel badgeText = new JLabel(AppTheme.htmlWidth(
                "Kelola jadwal dan tugas dalam satu tempat",
                230
        ));
        badgeText.setForeground(Color.WHITE);
        badgeText.setFont(new Font("Segoe UI", Font.BOLD, 15));
        badge.add(badgeText, BorderLayout.CENTER);
        badge.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(appName);
        leftPanel.add(Box.createVerticalStrut(18));
        leftPanel.add(desc);
        leftPanel.add(Box.createVerticalStrut(36));
        leftPanel.add(badge);
        leftPanel.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("Java Swing + MySQL");
        footer.setForeground(new Color(191, 219, 254));
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(footer);

        return leftPanel;
    }

    private JPanel createLoginPanel() {
        JPanel formArea = new JPanel(new GridBagLayout());
        formArea.setBackground(AppTheme.BACKGROUND);
        formArea.setBorder(new EmptyBorder(30, 20, 30, 30));

        AppTheme.RoundedPanel card = AppTheme.card();
        card.setPreferredSize(new Dimension(500, 500));

        JPanel cardContent = new JPanel();
        cardContent.setOpaque(false);
        cardContent.setLayout(new BoxLayout(cardContent, BoxLayout.Y_AXIS));
        cardContent.setBorder(new EmptyBorder(25, 14, 25, 14));

        JLabel title = AppTheme.title("Masuk Akun");
        JLabel subtitle = AppTheme.subtitle(AppTheme.htmlWidth(
                "Gunakan username dan password yang sudah terdaftar.",
                360
        ));

        JLabel lblUsername = AppTheme.label("Username");
        JLabel lblPassword = AppTheme.label("Password");

        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        title.setMaximumSize(new Dimension(440, 42));
        subtitle.setMaximumSize(new Dimension(440, 48));

        txtUsername = AppTheme.textField();
        txtPassword = AppTheme.passwordField();

        txtUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtUsername.setMaximumSize(new Dimension(440, 42));
        txtPassword.setMaximumSize(new Dimension(440, 42));

        JButton btnLogin = AppTheme.button("Login", AppTheme.PRIMARY);
        JButton btnRegister = AppTheme.button("Daftar Akun", AppTheme.SUCCESS);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> bukaRegister());

        cardContent.add(title);
        cardContent.add(Box.createVerticalStrut(8));
        cardContent.add(subtitle);
        cardContent.add(Box.createVerticalStrut(32));

        cardContent.add(lblUsername);
        cardContent.add(Box.createVerticalStrut(6));
        cardContent.add(txtUsername);
        cardContent.add(Box.createVerticalStrut(18));

        cardContent.add(lblPassword);
        cardContent.add(Box.createVerticalStrut(6));
        cardContent.add(txtPassword);
        cardContent.add(Box.createVerticalStrut(28));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        actions.setOpaque(false);
        actions.setAlignmentX(Component.LEFT_ALIGNMENT);
        actions.setMaximumSize(new Dimension(440, 50));

        actions.add(btnLogin);
        actions.add(Box.createHorizontalStrut(14));
        actions.add(btnRegister);

        cardContent.add(actions);

        JLabel hint = AppTheme.subtitle(AppTheme.htmlWidth(
                "Default akun: Username: admin / Password: 123",
                360
        ));
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        hint.setMaximumSize(new Dimension(440, 48));
        hint.setBorder(new EmptyBorder(28, 0, 0, 0));

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

    private void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi.");
            return;
        }

        boolean berhasil = userController.login(username, password);

        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Login berhasil. Selamat datang, " + username + "!");
            new Dashboard(username).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Login gagal. Cek username, password, atau koneksi database.");
        }
    }

    private void bukaRegister() {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setVisible(true);
        dispose();
    }
    
    public void resetFields() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtUsername.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}