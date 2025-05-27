package minesweeper.view.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class RoundedButton extends JButton {
    private int arcSize = 20; // Размер закругления
    private final Color BACKGROUND_COLOR = new Color(55, 89, 112); // Темно-зеленый
    private final Color TEXT_COLOR = new Color(202, 210, 217);
    private final Color GRID_COLOR = new Color(22, 36, 46); // Темнее фона
    private final Font CELL_FONT = new Font("Arial", Font.BOLD, 16);

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); // Отключаем стандартную заливку
        setFocusPainted(false);
        setBorder(new LineBorder(GRID_COLOR.darker(), 2, true));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, arcSize, arcSize);
        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arcSize, arcSize);
        g2.dispose();
    }
}