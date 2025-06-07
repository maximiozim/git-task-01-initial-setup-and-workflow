package taskpokorna;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class NotepadEditor extends JFrame {

    private JTextArea textArea;
    private JFileChooser fileChooser; // Для відкриття/збереження файлів
    private JLabel statusBar; // Для майбутнього статусбару
    private JMenuBar menuBar; // ВИПРАВЛЕННЯ: Оголошено як поле класу

    public NotepadEditor() {
        setTitle("Простий редактор нотаток");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Центрувати вікно

        // --- Текстова область ---
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Початковий шрифт
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // --- Панель статусу (тимчасова заглушка для майбутнього лічильника) ---
        statusBar = new JLabel("Готовий.");
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        add(statusBar, BorderLayout.SOUTH);

        // --- Меню ---
        menuBar = new JMenuBar(); // ВИПРАВЛЕННЯ: Ініціалізовано поле класу
        setJMenuBar(menuBar);

        // Меню "Файл"
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        JMenuItem openItem = new JMenuItem("Відкрити");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Зберегти");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
        fileMenu.add(saveItem);

        fileMenu.addSeparator(); // Роздільник

        JMenuItem exitItem = new JMenuItem("Вихід");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Завершити програму
            }
        });
        fileMenu.add(exitItem);

        // Меню "Тема"
        JMenu themeMenu = new JMenu("Тема");
        menuBar.add(themeMenu);

        JMenuItem lightThemeItem = new JMenuItem("Світла тема");
        lightThemeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTheme(false); // false для світлої теми
            }
        });
        themeMenu.add(lightThemeItem);

        JMenuItem darkThemeItem = new JMenuItem("Темна тема");
        darkThemeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTheme(true); // true для темної теми
            }
        });
        themeMenu.add(darkThemeItem);

        // Ініціалізація JFileChooser
        fileChooser = new JFileChooser();
        // Додаємо фільтр для текстових файлів за замовчуванням
        fileChooser.setFileFilter(new FileNameExtensionFilter("Текстові файли (*.txt)", "txt"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Всі файли (*.*)", "*"));

        setVisible(true);
    }

    // --- Методи для обробки файлів ---
    private void openFile() {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                textArea.read(reader, null);
                statusBar.setText("Відкрито файл: " + selectedFile.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Помилка при відкритті файлу: " + ex.getMessage(),
                        "Помилка", JOptionPane.ERROR_MESSAGE);
                statusBar.setText("Помилка відкриття файлу.");
            }
        }
    }

    private void saveFile() {
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // Додаємо розширення .txt, якщо його немає
            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                textArea.write(writer);
                statusBar.setText("Збережено файл: " + selectedFile.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Помилка при збереженні файлу: " + ex.getMessage(),
                        "Помилка", JOptionPane.ERROR_MESSAGE);
                statusBar.setText("Помилка збереження файлу.");
            }
        }
    }

    // --- Метод для зміни теми ---
    private void setTheme(boolean isDark) {
        if (isDark) {
            // Темна тема
            textArea.setBackground(new Color(40, 44, 52)); // Темно-сірий фон
            textArea.setForeground(new Color(220, 220, 220)); // Світлий текст
            textArea.setCaretColor(new Color(220, 220, 220)); // Колір курсору

            // Зміна кольору меню та статусбару
            menuBar.setBackground(new Color(50, 50, 50));
            menuBar.setForeground(new Color(220, 220, 220)); // Колір тексту меню
            // Щоб змінити колір тексту для JMenu та JMenuItem, потрібно перебрати їх
            for (Component comp : menuBar.getComponents()) {
                if (comp instanceof JMenu) {
                    JMenu menu = (JMenu) comp;
                    menu.setForeground(new Color(220, 220, 220));
                    for (Component itemComp : menu.getMenuComponents()) {
                        if (itemComp instanceof JMenuItem) {
                            JMenuItem item = (JMenuItem) itemComp;
                            item.setBackground(new Color(60, 60, 60)); // Фон пунктів меню
                            item.setForeground(new Color(220, 220, 220)); // Текст пунктів меню
                        }
                    }
                }
            }

            statusBar.setBackground(new Color(50, 50, 50));
            statusBar.setForeground(new Color(220, 220, 220));
            statusBar.setOpaque(true); // Важливо для відображення фону статусбару

            // Зміна кольору кореневої панелі (може впливати на фон навколо текстової області)
            JRootPane rootPane = SwingUtilities.getRootPane(this);
            if (rootPane != null) {
                rootPane.setBackground(new Color(30, 30, 30));
            }

        } else {
            // Світла тема
            textArea.setBackground(Color.WHITE);
            textArea.setForeground(Color.BLACK);
            textArea.setCaretColor(Color.BLACK);

            // Зміна кольору меню та статусбару
            menuBar.setBackground(new Color(238, 238, 238)); // Стандартний світлий фон
            menuBar.setForeground(Color.BLACK);
            for (Component comp : menuBar.getComponents()) {
                if (comp instanceof JMenu) {
                    JMenu menu = (JMenu) comp;
                    menu.setForeground(Color.BLACK);
                    for (Component itemComp : menu.getMenuComponents()) {
                        if (itemComp instanceof JMenuItem) {
                            JMenuItem item = (JMenuItem) itemComp;
                            item.setBackground(new Color(245, 245, 245)); // Фон пунктів меню
                            item.setForeground(Color.BLACK); // Текст пунктів меню
                        }
                    }
                }
            }

            statusBar.setBackground(new Color(238, 238, 238));
            statusBar.setForeground(Color.BLACK);
            statusBar.setOpaque(false); // Зазвичай не потрібен непрозорий фон для світлої теми

            JRootPane rootPane = SwingUtilities.getRootPane(this);
            if (rootPane != null) {
                rootPane.setBackground(new Color(238, 238, 238));
            }
        }
        // Оновлюємо інтерфейс, щоб зміни кольорів застосувалися
        SwingUtilities.updateComponentTreeUI(this);
    }

    public static void main(String[] args) {
        // Запускаємо GUI в потоці AWT Event Dispatch Thread
        SwingUtilities.invokeLater(NotepadEditor::new);
    }
}
