package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import model.DatabaseConnection;

public class Dashboard extends JFrame {

    private final String username;
    private JLabel lblTotalJadwal;
    private JLabel lblTotalTugas;
    private JLabel lblBelumSelesai;

    public Dashboard() {
        this("Mahasiswa");
    }

    public Dashboard(String username) {
        this.username = username;
        initComponents();
        loadSummary();
    }

    private void initComponents() {
        AppTheme.setupFrame(this, "Dashboard - Pengingat Jadwal Kuliah", 1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = AppTheme.root();
        setContentPane(root);

        root.add(createSidebar(), BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout(18, 18));
        main.setOpaque(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = AppTheme.title("Dashboard");
        JLabel subtitle = AppTheme.subtitle("Halo, " + username + ". Pantau jadwal kuliah dan tugas kamu di sini.");
        JPanel headerText = new JPanel();
        headerText.setOpaque(false);
        headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));
        headerText.add(title);
        headerText.add(Box.createVerticalStrut(5));
        headerText.add(subtitle);

        JButton btnRefresh = AppTheme.button("Refresh", AppTheme.SECONDARY);
        btnRefresh.addActionListener(e -> loadSummary());
        header.add(headerText, BorderLayout.WEST);
        header.add(btnRefresh, BorderLayout.EAST);

        JPanel cards = new JPanel(new GridLayout(1, 3, 16, 16));
        cards.setOpaque(false);
        lblTotalJadwal = createNumberLabel();
        lblTotalTugas = createNumberLabel();
        lblBelumSelesai = createNumberLabel();
        cards.add(summaryCard("Total Jadwal", lblTotalJadwal, "Mata kuliah tersimpan", AppTheme.PRIMARY));
        cards.add(summaryCard("Total Tugas", lblTotalTugas, "Tugas tercatat", AppTheme.SUCCESS));
        cards.add(summaryCard("Belum Selesai", lblBelumSelesai, "Tugas perlu dikerjakan", AppTheme.WARNING));

        AppTheme.RoundedPanel info = AppTheme.card();
        info.setLayout(new BorderLayout(12, 12));
        JLabel infoTitle = new JLabel("Menu Utama");
        infoTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        infoTitle.setForeground(AppTheme.TEXT);
        JLabel infoBody = new JLabel("<html>Gunakan menu di sebelah kiri untuk mengelola jadwal, tugas, melihat laporan, dan informasi aplikasi. Tampilan ini dibuat lebih rapi dengan warna biru akademik, kartu ringkasan, dan tombol navigasi.</html>");
        infoBody.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoBody.setForeground(AppTheme.MUTED);
        info.add(infoTitle, BorderLayout.NORTH);
        info.add(infoBody, BorderLayout.CENTER);

        JPanel topContent = new JPanel(new BorderLayout(18, 18));
        topContent.setOpaque(false);
        topContent.add(cards, BorderLayout.NORTH);
        topContent.add(info, BorderLayout.CENTER);

        main.add(header, BorderLayout.NORTH);
        main.add(topContent, BorderLayout.CENTER);
        root.add(main, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        AppTheme.RoundedPanel sidebar = new AppTheme.RoundedPanel(24, AppTheme.PRIMARY_DARK);
        sidebar.setPreferredSize(new Dimension(225, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(24, 20, 24, 20));

        JLabel app = new JLabel("Kuliah Reminder");
        app.setForeground(Color.WHITE);
        app.setFont(new Font("Segoe UI", Font.BOLD, 20));
        app.setAlignmentX(LEFT_ALIGNMENT);

        JLabel desc = new JLabel("Aplikasi Jadwal");
        desc.setForeground(new Color(191, 219, 254));
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        desc.setAlignmentX(LEFT_ALIGNMENT);

        sidebar.add(app);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(desc);
        sidebar.add(Box.createVerticalStrut(30));

        JButton btnDashboard = AppTheme.navButton("Dashboard");
        JButton btnJadwal = AppTheme.navButton("Data Jadwal");
        JButton btnTugas = AppTheme.navButton("Data Tugas");
        JButton btnLaporan = AppTheme.navButton("Laporan");
        JButton btnAbout = AppTheme.navButton("Tentang Aplikasi");
        JButton btnLogout = AppTheme.navButton("Logout");
        btnLogout.setBackground(AppTheme.DANGER);

        btnDashboard.addActionListener(e -> loadSummary());
        btnJadwal.addActionListener(e -> open(new JadwalForm(username)));
        btnTugas.addActionListener(e -> open(new TugasForm(username)));
        btnLaporan.addActionListener(e -> open(new LaporanForm(username)));
        btnAbout.addActionListener(e -> open(new AboutForm(username)));
        btnLogout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });

        addNav(sidebar, btnDashboard);
        addNav(sidebar, btnJadwal);
        addNav(sidebar, btnTugas);
        addNav(sidebar, btnLaporan);
        addNav(sidebar, btnAbout);
        sidebar.add(Box.createVerticalGlue());
        addNav(sidebar, btnLogout);

        return sidebar;
    }

    private void addNav(JPanel panel, JButton button) {
        button.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
    }

    private void open(JFrame frame) {
        frame.setVisible(true);
        dispose();
    }

    private JLabel createNumberLabel() {
        JLabel label = new JLabel("0");
        label.setFont(new Font("Segoe UI", Font.BOLD, 34));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }

    private JPanel summaryCard(String title, JLabel number, String desc, Color color) {
        AppTheme.RoundedPanel card = AppTheme.smallCard(color);
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(Color.WHITE);
        JLabel lblDesc = new JLabel(desc);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDesc.setForeground(new Color(240, 249, 255));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(number, BorderLayout.CENTER);
        card.add(lblDesc, BorderLayout.SOUTH);
        return card;
    }

    private void loadSummary() {
        lblTotalJadwal.setText(String.valueOf(count("SELECT COUNT(*) FROM jadwal WHERE username=?")));
        lblTotalTugas.setText(String.valueOf(count("SELECT COUNT(*) FROM tugas WHERE username=?")));
        lblBelumSelesai.setText(String.valueOf(count("SELECT COUNT(*) FROM tugas WHERE username=? AND status='Belum Selesai'")));
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
    }
}
