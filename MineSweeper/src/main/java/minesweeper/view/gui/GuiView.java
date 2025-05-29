package minesweeper.view.gui;

import minesweeper.controller.MinesweeperController;
import minesweeper.model.GameDifficult;
import minesweeper.observ.*;
import minesweeper.record.Record;
import minesweeper.record.RecordManager;
import minesweeper.view.MinesweeperView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GuiView implements MinesweeperView {
    private MinesweeperController controller;
    private RecordManager recordManager;
//    private int counter = 0;
    private JFrame mainFrame;
    private JPanel currentPanel;

    private JLabel timeLabel;
    private JLabel statusLabel;
    private JButton[][] cellButtons;

    private ContextField field;
    private ContextGameState gameState;
    private int time;
    private ContextDifficult difficult;
    private int height;
    private int width;

    private final int CELL_SIZE = 30;
    private final Color BACKGROUND_COLOR = new Color(55, 89, 112);
    private final Color TEXT_COLOR = new Color(202, 210, 217);
    private final Color GRID_COLOR = new Color(22, 36, 46);
    private final Font CELL_FONT = new Font("Arial", Font.BOLD, 16);

    private ImageIcon FLAG_ICON;
    private ImageIcon MINE_ICON;
    private ImageIcon DEFAULT_ICON;
    private ImageIcon BACKGROUND_IMAGE;

    @Override
    public void setController(MinesweeperController controller) {
        this.controller = controller;
        this.recordManager = controller.getRecordManager();
    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(this::guiStart);
    }

    private void guiStart() {
        mainFrame = new JFrame("Сапёр");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 600);
//        mainFrame.setResizable(false);

        FLAG_ICON = loadImage("flag.png", CELL_SIZE, CELL_SIZE);
        MINE_ICON = loadImage("mine.png", CELL_SIZE, CELL_SIZE);
        DEFAULT_ICON = loadImage("default.png", CELL_SIZE, CELL_SIZE);
        BACKGROUND_IMAGE = loadImage("back.png", 600, 500);
        showMainMenu();
        mainFrame.setVisible(true);
    }

    private ImageIcon loadImage(String path, int HEIGHT, int WIDTH) {
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource(path));
            Image scaledImage = originalIcon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("Не удалось загрузить иконку: " + path);
            return new ImageIcon();
        }
    }

    private void showMainMenu() {
        clearFrame();
        currentPanel = new JPanel(new GridBagLayout());
        currentPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Сапёр", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        currentPanel.add(titleLabel, gbc);

        JButton newGameButton = createStyledButton("Новая игра");
        newGameButton.addActionListener(e -> showDifficultySelection());
        currentPanel.add(newGameButton, gbc);

        JButton recordsButton = createStyledButton("Таблица рекордов");
        recordsButton.addActionListener(e -> showRecordsTable());
        currentPanel.add(recordsButton, gbc);

        JButton exitButton = createStyledButton("Выход");
        exitButton.addActionListener(e -> controller.selectExitCommand());
        currentPanel.add(exitButton, gbc);

        mainFrame.add(currentPanel);
        mainFrame.setSize(500, 600);
        mainFrame.revalidate();
    }

    private JButton createStyledButton(String text) {
        RoundedButton button = new RoundedButton(text);
        button.setBackground(GRID_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }

    private void showDifficultySelection() {
        clearFrame();
        currentPanel = new JPanel(new GridBagLayout());
        currentPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Выберите сложность", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_COLOR);
        currentPanel.add(titleLabel, gbc);

        JButton easyButton = createStyledButton("Легкая (9x9, 10 бомб)");
        easyButton.addActionListener(e -> controller.startGame(GameDifficult.EASY));
        currentPanel.add(easyButton, gbc);

        JButton mediumButton = createStyledButton("Средняя (15x15, 40 бомб)");
        mediumButton.addActionListener(e -> controller.startGame(GameDifficult.MEDIUM));
        currentPanel.add(mediumButton, gbc);

        JButton hardButton = createStyledButton("Сложная (20x20, 99 бомб)");
        hardButton.addActionListener(e -> controller.startGame(GameDifficult.HARD));
        currentPanel.add(hardButton, gbc);

        JButton customButton = createStyledButton("Свои параметры");
        customButton.addActionListener(e -> showCustomGameDialog());
        currentPanel.add(customButton, gbc);

        JButton backButton = createStyledButton("Назад");
        backButton.addActionListener(e -> showMainMenu());
        currentPanel.add(backButton, gbc);

        mainFrame.add(currentPanel);
        mainFrame.setSize(500, 600);
        mainFrame.revalidate();
    }

    private void showCustomGameDialog() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
//        panel.setBackground(BACKGROUND_COLOR);
        JLabel heightLabel = new JLabel("Высота:");
        heightLabel.setForeground(Color.BLACK);
//        JTextField heightField = createStyledTextField("9");
        JTextField heightField = new JTextField("9");
        JLabel widthLabel = new JLabel("Ширина:");
        widthLabel.setForeground(Color.BLACK);
        JTextField widthField = new JTextField("9");

        JLabel minesLabel = new JLabel("Мины:");
        minesLabel.setForeground(Color.BLACK);
        JTextField minesField = new JTextField("10");

        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(widthLabel);
        panel.add(widthField);
        panel.add(minesLabel);
        panel.add(minesField);

        int result = JOptionPane.showConfirmDialog(mainFrame, panel,
                "Свои параметры", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int height = Integer.parseInt(heightField.getText());
                int width = Integer.parseInt(widthField.getText());
                int mines = Integer.parseInt(minesField.getText());

                if (height < 1 || width < 1 || mines < 1) {
                    throw new NumberFormatException();
                }

                controller.startGame(new int[]{height, width, mines});
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Параметры должны быть числами больше 0",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
                showCustomGameDialog();
            }
        }
    }

//    private JTextField createStyledTextField(String text) {
//        JTextField textField = new JTextField(text);
//        textField.setForeground(TEXT_COLOR);
//        textField.setBackground(GRID_COLOR);
//        textField.setCaretColor(TEXT_COLOR);
//        textField.setBorder(BorderFactory.createLineBorder(GRID_COLOR.darker(), 1));
//        textField.setFont(new Font("Arial", Font.PLAIN, 14));
//        textField.setHorizontalAlignment(JTextField.CENTER);
//        return textField;
//    }
//
    private void showRecordsTable() {
        clearFrame();
        currentPanel = new JPanel(new BorderLayout());
        currentPanel.setBackground(BACKGROUND_COLOR);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(BACKGROUND_COLOR);
        tabbedPane.setForeground(Color.BLACK);

        tabbedPane.addTab("Легкая", createDifficultyRecordsPanel(GameDifficult.EASY));
        tabbedPane.addTab("Средняя", createDifficultyRecordsPanel(GameDifficult.MEDIUM));
        tabbedPane.addTab("Сложная", createDifficultyRecordsPanel(GameDifficult.HARD));
        tabbedPane.addTab("Свои параметры", createDifficultyRecordsPanel(GameDifficult.CUSTOM));

        currentPanel.add(tabbedPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);
        JButton clearButton = createStyledButton("Очистить все записи");
        clearButton.addActionListener(e -> {
            controller.selectClearAllRecords();
            showRecordsTable();
        });


        JButton backButton = createStyledButton("Назад");
        backButton.addActionListener(e -> showMainMenu());

        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        currentPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainFrame.add(currentPanel);
        mainFrame.setSize(500, 500);
        mainFrame.revalidate();
    }

    private JPanel createDifficultyRecordsPanel(GameDifficult difficult) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        List<Record> records = recordManager.getRecords(difficult);

        String[] columnNames = {"Место", "Имя", "Время"};
        Object[][] data = new Object[records.size()][3];

        for (int i = 0; i < records.size(); i++) {
            data[i][0] = i + 1;
            data[i][1] = records.get(i).getName();
            data[i][2] = records.get(i).getTime() + " сек.";
        }

        JTable table = new JTable(data, columnNames);
        table.setBackground(BACKGROUND_COLOR);
        table.setForeground(TEXT_COLOR);
        table.setGridColor(GRID_COLOR);
        table.setFont(CELL_FONT);
        table.setRowHeight(25);

        table.getTableHeader().setBackground(GRID_COLOR);
        table.getTableHeader().setForeground(TEXT_COLOR);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(GRID_COLOR.darker(), 1));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void showGameField() {
        clearFrame();
        currentPanel = new JPanel(new BorderLayout());
        currentPanel.setBackground(BACKGROUND_COLOR);

        // Панель информации
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.setBackground(BACKGROUND_COLOR);

        timeLabel = new JLabel("Время: 0", SwingConstants.CENTER);
        timeLabel.setForeground(TEXT_COLOR);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 14));

        statusLabel = new JLabel("Сделайте первый ход", SwingConstants.CENTER);
        statusLabel.setForeground(TEXT_COLOR);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));

        infoPanel.add(timeLabel);
        infoPanel.add(statusLabel);

        currentPanel.add(infoPanel, BorderLayout.NORTH);

        JPanel fieldPanel = new JPanel(new GridLayout(height, width, 1, 1));
        fieldPanel.setBackground(GRID_COLOR);
        cellButtons = new JButton[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                button.setBackground(Color.DARK_GRAY);
                button.setForeground(TEXT_COLOR);
                button.setFont(CELL_FONT);
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createLineBorder(GRID_COLOR, 1));
                button.setIcon(DEFAULT_ICON);

                final int x = i;
                final int y = j;

                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (gameState != ContextGameState.PLAYING) return;

                        if (SwingUtilities.isLeftMouseButton(e)) {
                            controller.selectOpenCellCommand(x, y);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            controller.selectToggleFlagCommand(x, y);
                        }
                    }
                });

                cellButtons[i][j] = button;
                fieldPanel.add(button);
            }
        }

        JScrollPane scrollPane = new JScrollPane(fieldPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        currentPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = createStyledButton("Завершить игру");
        backButton.addActionListener(e -> controller.selecBackCommand());
        currentPanel.add(backButton, BorderLayout.SOUTH);

        mainFrame.add(currentPanel);
        mainFrame.setSize(width * CELL_SIZE + 30, height * CELL_SIZE + 120);
        mainFrame.revalidate();
    }

    @Override
    public void update(Context context) {
        SwingUtilities.invokeLater(() -> {
            if (!context.getOnlyTimeUpdate()) {
                // Полное обновление
                field = context.getField();
                gameState = context.getGameState();
                difficult = context.getDifficult();
                height = context.getHeight();
                width = context.getWidth();

                if (gameState == ContextGameState.PLAYING) {
                    showGameField();
                }
                updateField();
                updateStatus();
            }

            time = context.getTime();
            if (timeLabel != null) {
                timeLabel.setText("Время: " + time);
            }
        });
    }

    private void updateField() {
        if (cellButtons == null) return;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                JButton button = cellButtons[i][j];
                ContextCell cell = field.getCell(i, j);

                if (cell.getState() == ContextCellState.OPEN) {
                    button.setBackground(BACKGROUND_COLOR);
                    button.setIcon(null); // Убираем иконку

                    if (cell.getSym() == 'M') {
                        button.setIcon(MINE_ICON);
                    } else if (cell.getSym() != ' ') {
                        button.setText(String.valueOf(cell.getSym()));
                    } else {
                        button.setText("");
                    }
                } else if (cell.getState() == ContextCellState.FLAG) {
                    button.setIcon(FLAG_ICON);
                    button.setText("");
                } else {
                    button.setIcon(DEFAULT_ICON);
                    button.setText("");
                }
            }
        }
    }

    private void updateStatus() {
        if (statusLabel == null) return;

        switch (gameState) {
            case PLAYING:
                statusLabel.setText("Игра идет...");
                break;
            case WON:
                statusLabel.setText("Вы победили!");
                showGameResultDialog(true);
                break;
            case LOST:
                statusLabel.setText("Вы проиграли!");
                showGameResultDialog(false);
                break;
        }
    }

    private void showGameResultDialog(boolean won) {
        Object[] options;
        if (won) {
            options = new Object[]{"Новая игра", "Сохранить результат", "В меню"};
        } else {
            options = new Object[]{"Новая игра", "В меню"};
        }

        int choice = JOptionPane.showOptionDialog(mainFrame,
                "Ваше время: " + time + " сек.",
                won ? "Победа!" : "Поражение",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (won) {
            switch (choice) {
                case 0:
                    showDifficultySelection();
                    break;
                case 1:
                    saveRecord();
                    break;
                case 2:
                    showMainMenu();
                    break;
            }
        } else {
            switch (choice) {
                case 0:
                    showDifficultySelection();
                    break;
                case 1:
                    showMainMenu();
                    break;
            }
        }
    }

    private void saveRecord() {
        String name = JOptionPane.showInputDialog(mainFrame,
                "Введите ваше имя:", "Сохранение результата",
                JOptionPane.PLAIN_MESSAGE);

        if (name != null && !name.trim().isEmpty()) {
            controller.selectSaveRecords(name.trim());
            showMainMenu();
        }
    }

    private void clearFrame() {
        if (currentPanel != null) {
            mainFrame.remove(currentPanel);
        }
    }

}