package Horoscope;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class HoroscopeService {
    static Scanner input = new Scanner(System.in);
    static String zodiacSign;
    static VKExampleAPI vkExampleAPI = new VKExampleAPI();
    static HoroscopeParser parser = new HoroscopeParser();
    static Map<String, String> horoscopes;

/* 1. Регистрация(имя, дата рождения) - определяет знак зодиака
   2. Покажи мой гороскоп - показывает текущий гороскоп на вас
   3. Покажи весь гороскоп - показывает соответственно весь гороскоп */


    // регистрация
    public void register() {
        System.out.print("Введите дату рождения в формате dd.MM.yyyy: ");
        String birthday = input.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(birthday, formatter);

        // zodiacChoose
        zodiacSign = getZodiacSign(date);
        System.out.println("Ваш знак зодиака: " + zodiacSign);
    }

    // получение знака зодиака(через ENUM)
    public static String getZodiacSign(LocalDate date) {
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();

        String[] zodiacSigns = {
                "Козерог", "Водолей", "Рыбы", "Овен", "Телец", "Близнецы",
                "Рак", "Лев", "Дева", "Весы", "Скорпион", "Стрелец"
        };

        int[] endDays = {19, 18, 20, 19, 20, 20, 22, 22, 22, 22, 21, 21};

        if (day <= endDays[month - 1]) {
            return zodiacSigns[month - 1];
        } else {
            return zodiacSigns[month % 12];
        }
    }


    // CLI-борд с опциональностью:

    public void consoleHelloMenu() {
        int choice = 0;
        String jsonResponse = vkExampleAPI.createRequest();
        horoscopes = parser.getHoroscopes(jsonResponse);

        System.out.println("Здравствуйте, программа успешно запустилась!");
        register();

        while (true) {
            System.out.println("1 - ваш гороскоп на сегодняшний день");
            System.out.println("2 - посмотреть общий гороскоп");
            System.out.println("3 - прервать работу программы");
            System.out.print("Вы должны принять решение: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    //  программа вызывает гороскоп на 1 зодиак
                    showDailyHoroscopeForUser();
                    break;
                case 2:
                    showAllHoroscopes();
                    break;
                case 3:
                    System.out.println("Программа прервана!");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
                    break;
            }
        }
    }

    public void showDailyHoroscopeForUser() {
        String userHoroscope = horoscopes.get(zodiacSign); // Добавляем символ знака зодиака

        if(userHoroscope != null) {
            System.out.print("Ваш гороскоп на сегодня: " + userHoroscope);
        } else {
            System.out.println("Гороскоп для вашего знака зодиака не найден.");
            System.out.println("Ваш знак зодиака: " + zodiacSign);
        }
    }

    public static void showAllHoroscopes() {
        System.out.println("Общий гороскоп на " + LocalDate.now());
        for(Map.Entry<String,String> horoscope : horoscopes.entrySet()) {
            System.out.println("Знак зодиака: " + horoscope.getKey());
            System.out.println("Знак зодиака: " + horoscope.getValue());
        }
    }
}
