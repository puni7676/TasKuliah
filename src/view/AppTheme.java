
package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class AppTheme {

    public static final Color BACKGROUND = new Color(241, 245, 249);
    public static final Color CARD = Color.WHITE;
    public static final Color PRIMARY = new Color(37, 99, 235);
    public static final Color PRIMARY_DARK = new Color(30, 64, 175);
    public static final Color SECONDARY = new Color(14, 165, 233);
    public static final Color SUCCESS = new Color(34, 197, 94);
    public static final Color WARNING = new Color(245, 158, 11);
    public static final Color DANGER = new Color(239, 68, 68);
    public static final Color MUTED = new Color(100, 116, 139);
    public static final Color TEXT = new Color(15, 23, 42);
    public static final Color BORDER = new Color(203, 213, 225);
    public static final Color SOFT_BLUE = new Color(219, 234, 254);

    private static final Font FONT_PLAIN = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_MEDIUM = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 30);

    public static void setupFrame(JFrame frame, String title, int width, int height) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        UIManager.put("OptionPane.messageFont", FONT_PLAIN);
        UIManager.put("OptionPane.buttonFont", FONT_MEDIUM);

        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setMinimumSize(new Dimension(Math.min(width, 980), Math.min(height, 600)));
        frame.setLocationRelativeTo(null);
    }

    public static JPanel root() {
        JPanel panel = new JPanel(new BorderLayout(22, 22));
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        return panel;
    }

    public static RoundedPanel card() {
        RoundedPanel panel = new RoundedPanel(24, CARD);
        panel.setLayout(new BorderLayout(16, 16));
        panel.setBorder(new EmptyBorder(24, 26, 24, 26));
        return panel;
    }

    public static RoundedPanel smallCard(Color color) {
        RoundedPanel panel = new RoundedPanel(22, color);
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(18, 20, 18, 20));
        return panel;
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_TITLE);
        label.setForeground(TEXT);
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        return label;
    }

    public static JLabel sectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel subtitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(MUTED);
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        return label;
    }

    public static JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_MEDIUM);
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel whiteLabel(String text, int size, int style) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", style, size));
        label.setForeground(Color.WHITE);
        return label;
    }

    public static JTextField textField() {
        JTextField field = new JTextField();
        styleInput(field);
        return field;
    }

    public static JPasswordField passwordField() {
        JPasswordField field = new JPasswordField();
        styleInput(field);
        return field;
    }

    private static void styleInput(JTextField field) {
        field.setFont(FONT_PLAIN);
        field.setForeground(TEXT);
        field.setBackground(Color.WHITE);
        field.setCaretColor(PRIMARY_DARK);
        field.setPreferredSize(new Dimension(310, 42));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(9, 12, 9, 12)
        ));
    }

    public static JComboBox<String> comboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(FONT_PLAIN);
        combo.setForeground(TEXT);
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(310, 42));
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        combo.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        return combo;
    }

    public static JButton button(String text, Color color) {
        JButton button = new ThemedButton(text, color, 14);
        button.setPreferredSize(new Dimension(145, 44));
        button.setMinimumSize(new Dimension(120, 40));
        button.setBorder(new EmptyBorder(10, 22, 10, 22));
        return button;
    }

    public static JButton navButton(String text) {
        JButton button = new ThemedButton(text, PRIMARY, 13);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(185, 42));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
        return button;
    }

    public static void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(191, 219, 254));
        table.setSelectionForeground(TEXT);
        table.setGridColor(new Color(226, 232, 240));
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setForeground(Color.WHITE);
        header.setBackground(PRIMARY_DARK);
        header.setOpaque(true);
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);
        header.setPreferredSize(new Dimension(header.getWidth(), 38));

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                setOpaque(true);
                setBackground(PRIMARY_DARK);
                setForeground(Color.WHITE);
                setFont(new Font("Segoe UI", Font.BOLD, 13));
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(30, 64, 175)));
                return this;
            }
        };
        header.setDefaultRenderer(headerRenderer);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                    c.setForeground(TEXT);
                }
                return c;
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
    }

    public static JScrollPane scrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(148, 163, 184);
                this.trackColor = new Color(241, 245, 249);
            }
        });
        return scrollPane;
    }

    public static GridBagConstraints gbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(8, 0, 8, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = x == 1 ? GridBagConstraints.HORIZONTAL : GridBagConstraints.NONE;
        gbc.weightx = x == 1 ? 1.0 : 0.0;
        return gbc;
    }

    public static String htmlWidth(String text, int width) {
        return "<html><div style='width:" + width + "px'>" + text + "</div></html>";
    }


    private static Color adjust(Color color, int amount) {
        int r = Math.max(0, Math.min(255, color.getRed() + amount));
        int g = Math.max(0, Math.min(255, color.getGreen() + amount));
        int b = Math.max(0, Math.min(255, color.getBlue() + amount));
        return new Color(r, g, b);
    }

    private static class ThemedButton extends JButton {
        private final int radius = 14;

        ThemedButton(String text, Color color, int fontSize) {
            super(text);
            setFont(new Font("Segoe UI", Font.BOLD, fontSize));
            setForeground(Color.WHITE);
            setBackground(color);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color base = getBackground();
            if (!isEnabled()) {
                base = new Color(148, 163, 184);
            } else if (getModel().isPressed()) {
                base = adjust(base, -28);
            } else if (getModel().isRollover()) {
                base = adjust(base, 18);
            }

            g2.setColor(base);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color backgroundColor;

        public RoundedPanel(int radius, Color backgroundColor) {
            this.radius = radius;
            this.backgroundColor = backgroundColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            if (backgroundColor.equals(CARD) || backgroundColor.equals(new Color(248, 250, 252))) {
                g2.setColor(new Color(226, 232, 240));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
