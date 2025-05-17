package minesweeper.view.gui;

import minesweeper.CommandParser.CommandParser;
import minesweeper.controller.MinesweeperController;
import minesweeper.observ.Context;
import minesweeper.record.Record;
import minesweeper.record.RecordManager;
import minesweeper.view.MinesweeperView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class GuiView implements MinesweeperView {
    private MinesweeperController controller;
    private RecordManager recordManager;

    private JFrame mainFrame;
    private JFrame gameFrame;
    private JFrame recordsFrame;

    private JPanel gamePanel;
    private JLabel timerLabel;
    private JLabel statusLabel;
    private JButton[][] cellButtons;

    private Map<String, ImageIcon> icons = new HashMap<>();
    private int height;
    private int width;
    private final CommandParser parser = new CommandParser();

    @Override
    public void setController(MinesweeperController controller) {
        this.controller = controller;
        this.recordManager = controller.getRecordManager();
        loadIcons();
        createMainWindow();
    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> mainFrame.setVisible(true));
    }

    @Override
    public void displayGame() {

    }

    private void loadIcons() {
        try {
            icons.put("*", new ImageIcon(getClass().getResource("/images/closed.png")));
            icons.put("F", new ImageIcon(getClass().getResource("/images/flag.png")));
            icons.put("M", new ImageIcon(getClass().getResource("/images/mine.png")));
            icons.put(" ", new ImageIcon(getClass().getResource("/images/empty.png")));

            for (int i = 1; i <= 8; i++) {
                icons.put(String.valueOf(i), new ImageIcon(getClass().getResource("/images/" + i + ".png")));
            }
        } catch (Exception e) {
            System.err.println("Не удалось загрузить изображения: " + e.getMessage());
        }
    }

    private void createMainWindow() {
        mainFrame = new JFrame("Minesweeper");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 200);
        mainFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel titleLabel = new JLabel("Minesweeper", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, gbc);

        JButton newGameButton = new JButton("Новая игра");
        newGameButton.addActionListener(e -> showDifficultyDialog());
        panel.add(newGameButton, gbc);

        JButton recordsButton = new JButton("Таблица рекордов");
        recordsButton.addActionListener(e -> showRecordsDialog());
        panel.add(recordsButton, gbc);

        JButton exitButton = new JButton("Выход");
        exitButton.addActionListener(e -> controller.selectExitCommand());
        panel.add(exitButton, gbc);

        mainFrame.add(panel);
    }

    private void showDifficultyDialog() {
        JDialog dialog = new JDialog(mainFrame, "Выбор сложности", true);
        dialog.setLayout(new GridLayout(5, 1));
        dialog.setSize(300, 250);
        dialog.setLocationRelativeTo(mainFrame);

        JButton easyButton = new JButton("Легкая (9x9, 10 мин)");
        easyButton.addActionListener(e -> {
            controller.startGame("EASY");
            dialog.dispose();
        });

        JButton mediumButton = new JButton("Средняя (9x9, 30 мин)");
        mediumButton.addActionListener(e -> {
            controller.startGame("MEDIUM");
            dialog.dispose();
        });

        JButton hardButton = new JButton("Сложная (9x9, 50 мин)");
        hardButton.addActionListener(e -> {
            controller.startGame("HARD");
            dialog.dispose();
        });

        JButton customButton = new JButton("Свои параметры");
        customButton.addActionListener(e -> {
            showCustomGameDialog();
            dialog.dispose();
        });

        dialog.add(new JLabel("Выберите сложность:", SwingConstants.CENTER));
        dialog.add(easyButton);
        dialog.add(mediumButton);
        dialog.add(hardButton);
        dialog.add(customButton);

        dialog.setVisible(true);
    }

    private void showCustomGameDialog() {
        JDialog dialog = new JDialog(mainFrame, "Свои параметры", true);
        dialog.setLayout(new GridLayout(4, 2, 5, 5));
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(mainFrame);

        JTextField heightField = new JTextField();
        JTextField widthField = new JTextField();
        JTextField minesField = new JTextField();

        dialog.add(new JLabel("Высота:"));
        dialog.add(heightField);
        dialog.add(new JLabel("Ширина:"));
        dialog.add(widthField);
        dialog.add(new JLabel("Количество мин:"));
        dialog.add(minesField);

        JButton startButton = new JButton("Начать игру");
        startButton.addActionListener(e -> {
            try {
                int[] params = {
                        Integer.parseInt(heightField.getText()),
                        Integer.parseInt(widthField.getText()),
                        Integer.parseInt(minesField.getText())
                };
                controller.startGame(params);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Введите корректные числа!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(startButton);
        dialog.setVisible(true);
    }

    private void showRecordsDialog() {
        recordsFrame = new JFrame("Таблица рекордов");
        recordsFrame.setSize(400, 400);
        recordsFrame.setLocationRelativeTo(mainFrame);

        JTabbedPane tabbedPane = new JTabbedPane();
        String[] difficulties = {"EASY", "MEDIUM", "HARD", "CUSTOM"};

        for (String difficulty : difficulties) {
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (Record record : recordManager.getRecords(difficulty)) {
                listModel.addElement(record.getName() + ": " + record.getTime() + " сек.");
            }

            JList<String> recordsList = new JList<>(listModel);
            JScrollPane scrollPane = new JScrollPane(recordsList);

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(scrollPane, BorderLayout.CENTER);

            JButton clearButton = new JButton("Очистить");
            clearButton.addActionListener(e -> {
                controller.selectClearAllRecords();
                listModel.clear();
            });

            panel.add(clearButton, BorderLayout.SOUTH);
            tabbedPane.addTab(difficulty, panel);
        }

        JButton backButton = new JButton("Назад");
        backButton.addActionListener(e -> recordsFrame.dispose());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);

        recordsFrame.add(mainPanel);
        recordsFrame.setVisible(true);
    }

    private void createGameWindow() {
        gameFrame = new JFrame("Minesweeper - Игра");
        gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                controller.selectExitCommand();
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Панель статуса
        JPanel statusPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        timerLabel = new JLabel("Время: 0 сек.", SwingConstants.CENTER);
        statusLabel = new JLabel("Статус: Игра", SwingConstants.CENTER);
        statusPanel.add(timerLabel);
        statusPanel.add(statusLabel);

        // Игровое поле
        gamePanel = new JPanel(new GridLayout(height, width, 1, 1));
        cellButtons = new JButton[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(30, 30));

                if (icons.containsKey("*")) {
                    button.setIcon(icons.get("*"));
                } else {
                    button.setText("*");
                }

                final int x = i;
                final int y = j;

                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            controller.selectOpenCellCommand(x, y);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            controller.selectToggleFlagCommand(x, y);
                        }
                    }
                });

                cellButtons[i][j] = button;
                gamePanel.add(button);
            }
        }

        // Кнопки управления
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton newGameButton = new JButton("Новая игра");
        newGameButton.addActionListener(e -> showDifficultyDialog());

        JButton exitButton = new JButton("Выход");
        exitButton.addActionListener(e -> controller.selectExitCommand());

        controlPanel.add(newGameButton);
        controlPanel.add(exitButton);

        mainPanel.add(statusPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(gamePanel), BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        gameFrame.add(mainPanel);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(mainFrame);
    }

    private void showGameResultDialog(String title, String message, boolean showSaveButton) {
        JDialog dialog = new JDialog(gameFrame, title, true);
        dialog.setLayout(new BorderLayout(5, 5));
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(gameFrame);

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton newGameButton = new JButton("Новая игра");
        newGameButton.addActionListener(e -> {
            dialog.dispose();
            showDifficultyDialog();
        });

        if (showSaveButton) {
            JButton saveButton = new JButton("Сохранить результат");
            saveButton.addActionListener(e -> {
                String name = JOptionPane.showInputDialog(dialog, "Введите ваше имя:", "Сохранение рекорда", JOptionPane.PLAIN_MESSAGE);
                if (name != null && !name.isEmpty()) {
                    controller.selectSaveRecords(name);
                    dialog.dispose();
                }
            });
            buttonPanel.add(saveButton);
        }

        JButton exitButton = new JButton("Выход");
        exitButton.addActionListener(e -> {
            dialog.dispose();
            controller.selectExitCommand();
        });

        buttonPanel.add(newGameButton);
        buttonPanel.add(exitButton);

        dialog.add(messageLabel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    @Override
    public void update(Context context) {
        SwingUtilities.invokeLater(() -> {
            if (context.getOnlyTimeUpdate()) {
                // Обновляем только время
                timerLabel.setText("Время: " + context.getTime() + " сек.");
            } else {
                // Полное обновление игры
                String[][] field = context.getField();
                String gameState = context.getGameState();
                height = context.getHeight();
                width = context.getWidth();

                if (gameFrame == null) {
                    createGameWindow();
                    gameFrame.setVisible(true);
                    mainFrame.setVisible(false);
                }

                // Обновляем клетки поля
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        String cellValue = field[i][j];
                        JButton button = cellButtons[i][j];

                        if (icons.containsKey(cellValue)) {
                            button.setIcon(icons.get(cellValue));
                            button.setText("");
                        } else {
                            button.setIcon(null);
                            button.setText(cellValue);
                        }
                    }
                }

                // Обновляем статус игры
                switch (gameState) {
                    case "PLAYING":
                        statusLabel.setText("Статус: Игра");
                        break;
                    case "WON":
                        statusLabel.setText("Статус: Победа!");
                        showGameResultDialog("Победа!", "Вы выиграли!", true);
                        break;
                    case "LOST":
                        statusLabel.setText("Статус: Поражение");
                        showGameResultDialog("Поражение", "Вы проиграли!", false);
                        break;
                    case "EXIT":
                        gameFrame.dispose();
                        mainFrame.setVisible(true);
                        break;
                }

                // Обновляем время (даже при полном обновлении)
                timerLabel.setText("Время: " + context.getTime() + " сек.");
            }
        });
    }
}