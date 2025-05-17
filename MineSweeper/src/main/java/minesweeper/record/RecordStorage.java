package minesweeper.record;

import java.io.*;

public class RecordStorage {
    private static final String FILE_NAME = ".records";

    public static void save(RecordManager manager) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(manager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RecordManager load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (RecordManager) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Файл рекордов не найден, создаём новый");
        } catch (Exception e) {
            System.out.println("Ошибка при чтении файла рекордов:");
            e.printStackTrace();
        }
        return new RecordManager();
    }
}
