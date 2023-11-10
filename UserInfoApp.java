import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserInfoApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Ввод данных от пользователя
            System.out.println("Введите данные (Фамилия Имя Отчество дата рождения номер телефона пол):");
            String input = scanner.nextLine();

            // Проверка количества введенных данных
            String[] data = input.split(" ");
            if (data.length != 6) {
                throw new IllegalArgumentException("Неверное количество данных. Ожидается 6 параметров.");
            }

            // Распределение данных в переменные
            String lastName = data[0];
            String firstName = data[1];
            String middleName = data[2];
            String birthDateStr = data[3];
            String phoneNumberStr = data[4];
            String genderStr = data[5];

            // Парсинг даты рождения
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date birthDate;
            try {
                birthDate = dateFormat.parse(birthDateStr);
            } catch (ParseException e) {
                throw new InvalidDataFormatException("Неверный формат даты рождения. Ожидается dd.MM.yyyy.");
            }

            // Парсинг номера телефона
            long phoneNumber;
            try {
                phoneNumber = Long.parseLong(phoneNumberStr);
            } catch (NumberFormatException e) {
                throw new InvalidDataFormatException("Неверный формат номера телефона. Ожидается целое число.");
            }

            // Проверка пола
            if (!genderStr.equals("f") && !genderStr.equals("m")) {
                throw new InvalidDataFormatException("Неверный формат пола. Допустимые значения: f или m.");
            }

            // Создание строки для записи в файл
            String record = lastName + " " + firstName + " " + middleName + " " +
                            dateFormat.format(birthDate) + " " + phoneNumber + " " + genderStr;

            // Создание или добавление данных в файл
            writeToFile(lastName, record);

        } catch (InvalidDataFormatException e) {
            System.err.println("Ошибка ввода данных:");
            System.err.println(e.getMessage());
        } finally {
            // Закрытие сканнера
            scanner.close();
        }
    }

    private static void writeToFile(String fileName, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt", true))) {
            // Запись данных в файл с новой строкой
            writer.write(data);
            writer.newLine();
            System.out.println("Данные успешно записаны в файл: " + fileName);
        } catch (IOException e) {
            // Обработка ошибки записи в файл
            System.err.println("Ошибка записи в файл:");
            e.printStackTrace();
        }
    }
}

class InvalidDataFormatException extends RuntimeException {
    public InvalidDataFormatException(String message) {
        super(message);
    }
}
