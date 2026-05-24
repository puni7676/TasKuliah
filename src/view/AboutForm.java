package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class AboutForm extends JFrame {

    private final String username;

    public AboutForm() {
        this("Mahasiswa");
    }

    public AboutForm(String username) {
        this.username = username;
        initComponents();
    }

    private void initComponents() {
        AppTheme.setupFrame(this, "Tentang Aplikasi - Pengingat Jadwal Kuliah", 900, 560);

        JPanel root = AppTheme.root();
        setContentPane(root);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JPanel titleBox = new JPanel();
        titleBox.setOpaque(false);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.add(AppTheme.title("Tentang Aplikasi"));
        titleBox.add(Box.createVerticalStrut(5));
        titleBox.add(AppTheme.subtitle("Informasi singkat tentang project Pengingat Jadwal Kuliah."));

        JButton btnBack = AppTheme.button("Kembali", AppTheme.PRIMARY_DARK);
        btnBack.addActionListener(e -> backToDashboard());
        header.add(titleBox, BorderLayout.WEST);
        header.add(btnBack, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        AppTheme.RoundedPanel card = AppTheme.card();
        card.setLayout(new BorderLayout(18, 18));
        root.add(card, BorderLayout.CENTER);

        JPanel hero = new AppTheme.RoundedPanel(24, AppTheme.PRIMARY);
        hero.setPreferredSize(new Dimension(0, 120));
        hero.setLayout(new BorderLayout());
        hero.setBorder(new EmptyBorder(20, 24, 20, 24));

        JLabel appName = new JLabel("Pengingat Jadwal Kuliah");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 26));
        appName.setForeground(Color.WHITE);
        JLabel tagline = new JLabel("Aplikasi desktop sederhana untuk membantu mahasiswa mengelola jadwal dan tugas.");
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tagline.setForeground(new Color(219, 234, 254));
        JPanel heroText = new JPanel();
        heroText.setOpaque(false);
        heroText.setLayout(new BoxLayout(heroText, BoxLayout.Y_AXIS));
        heroText.add(appName);
        heroText.add(Box.createVerticalStrut(7));
        heroText.add(tagline);
        hero.add(heroText, BorderLayout.CENTER);

        JPanel infoGrid = new JPanel(new GridLayout(2, 2, 14, 14));
        infoGrid.setOpaque(false);
        infoGrid.add(infoBox("Fitur Login", "User dapat login dan register menggunakan data dari database."));
        infoGrid.add(infoBox("Data Jadwal", "Menyimpan mata kuliah, hari, jam, dan ruangan."));
        infoGrid.add(infoBox("Data Tugas", "Mencatat nama tugas, mata kuliah, deadline, dan status."));
        infoGrid.add(infoBox("Laporan", "Menampilkan ringkasan data dan tabel laporan yang bisa dicetak."));

        JLabel footer = AppTheme.subtitle("Dibuat menggunakan Java Swing, konsep OOP, MVC sederhana, dan MySQL.");

        card.add(hero, BorderLayout.NORTH);
        card.add(infoGrid, BorderLayout.CENTER);
        card.add(footer, BorderLayout.SOUTH);
    }

    private JPanel infoBox(String title, String text) {
        AppTheme.RoundedPanel box = new AppTheme.RoundedPanel(20, new Color(248, 250, 252));
        box.setLayout(new BorderLayout(8, 8));
        box.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(AppTheme.TEXT);
        JLabel lblText = new JLabel("<html>" + text + "</html>");
        lblText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblText.setForeground(AppTheme.MUTED);

        box.add(lblTitle, BorderLayout.NORTH);
        box.add(lblText, BorderLayout.CENTER);
        return box;
    }

    private void backToDashboard() {
        new Dashboard(username).setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AboutForm().setVisible(true));
    }
}
