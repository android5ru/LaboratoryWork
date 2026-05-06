import Catalog.Genres;
import Catalog.Publication;
import Catalog.PublicationBuilder;
import Catalog.PublicationType;
import Client.Client;
import Client.Loan;
import FineCalculator.FineCalculator;
import FineCalculator.DayFineCalculator;
import FineCalculator.ProgressiveFineCalculator;
import LendingRule.LendingRule;
import Search.AuthorSearch;
import Search.GenreSearch;
import Search.NameSearch;
import Search.SearchStrategy;

import java.util.List;
import java.util.Scanner;

public class LibraryConsole {
    private final Library library;
    private final Scanner scanner;

    public LibraryConsole(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Библиотека");
        while (true) {
            printMenu();
            System.out.print("Введите команду: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            try {
                if (!handleCommand(input)) {
                    break;
                }
            } catch (Exception e) {
                System.err.println("Ошибка выполнения: " + e.getMessage());
            }
        }

        System.out.println("Завершение работы");
    }

    private void printMenu() {
        System.out.println("\nМеню");
        System.out.println(" 1 - Добавить нового читателя");
        System.out.println(" 2 - Добавить новое издание");
        System.out.println(" 3 - Изменить правило выдачи");
        System.out.println(" 4 - Изменить штраф");
        System.out.println(" 5 - Поиск изданий");
        System.out.println(" 6 - Выдать издание читателю");
        System.out.println(" 7 - Вернуть издание");
        System.out.println(" 8 - Показать каталог и активных читателей");
        System.out.println(" 9 - Выход из программы");
    }

    private boolean handleCommand(String cmd) {
        switch (cmd) {
            case "1": addClient(); return true;
            case "2": addPublication(); return true;
            case "3": newLendingRule(); return true;
            case "4": newFineCalculator(); return true;
            case "5": searchPublications(); return true;
            case "6": issuePublication(); return true;
            case "7": returnPublication(); return true;
            case "8": printInfo(); return true;
            case "9": return false;
            default:
                System.out.println("Неизвестная команда. Попробуйте снова.");
                return true;
        }
    }

    private void addClient(){
        System.out.println("\nДобавление нового читателя:");
        String name = readString("Введите ИФ: ");
        library.newClient(name);
        System.out.println("Читатель добавлен");
    }

    private void addPublication() {
        System.out.println("\nДобавление издания:");
        System.out.println("Тип: 1-Книга, 2-Журнал");
        int typeChoice = readValidInt("Выберете тип: ", 1, 2);

        String name = readString("Название: ");
        int year = readInt("Год выпуска: ");
        System.out.println("Жанры: 1-ARTISTIC_LITERATURE, 2-SCIENTIFIC, 3-EDUCATIONAL, 4-FANTASTIC, 5-DETECTIVE");
        int genreChoice = readValidInt("Выберете жанр: ", 1, 5);
        Genres[] genres = Genres.values();
        Genres genre = genres[genreChoice - 1];

        String author = readString("Автор / Редакция: ");
        String identifier = readString("ISBN / Номер выпуска: ");
        int counts = readInt("Количество экземпляров: ");

        PublicationType type = (typeChoice == 1) ? PublicationType.BOOK : PublicationType.MAGAZINE;

        Publication pub = new PublicationBuilder()
                .type(type)
                .name(name)
                .year(year)
                .genre(genre)
                .author(author)
                .identifier(identifier)
                .counts(counts)
                .build();

        library.newPublication(pub);
        System.out.println("Издание успешно добавлено в каталог.");
    }

    private void newLendingRule(){
        System.out.println("Изменение правил выдачи");
        System.out.println("Тип: 1-Книга, 2-Журнал");
        int typeChoice = readValidInt("Выберете тип: ", 1, 2);

        PublicationType type = (typeChoice == 1) ? PublicationType.BOOK : PublicationType.MAGAZINE;

        int maxDays = readValidInt("Максимальный срок выдачи (дней): ", 1, 365);
        int maxCopies = readValidInt("Максимум экземпляров на читателя: ", 1, 10);

        LendingRule newRule = new LendingRule() {
            @Override public int maxLoanDays() { return maxDays; }
            @Override public int maxCopiesPerClient() { return maxCopies; }
        };

        library.registerRule(type, newRule);
    }
    private void newFineCalculator(){
        System.out.println("Изменение штрафа");
        System.out.println("1 - Простой (фикс. ставка за день)");
        System.out.println("2 - Прогрессивный (первая неделя дешевле)");

        int choice = readValidInt("Выберите тип: ", 1, 2);

        FineCalculator calculator;
        if (choice == 1) {
            double rate = readValidInt("Ставка за день (руб.): ", 1, 100);
            calculator = new DayFineCalculator(rate);
        } else {
            calculator = new ProgressiveFineCalculator();
        }

        library.setFineCalculator(calculator);
    }

    private void searchPublications() {
        System.out.println("\nПоиск:");
        System.out.println("1 - По названию, 2 - По автору, 3 - По жанру");
        int choice = readInt("Выберите тип поиска: ");

        SearchStrategy strategy;
        switch (choice) {
            case 1 -> strategy = new NameSearch(readString("Введите часть названия: "));
            case 2 -> strategy = new AuthorSearch(readString("Введите часть имени автора: "));
            case 3 -> {
                System.out.println("Доступные жанры: " + java.util.Arrays.toString(Genres.values()));
                String genreName = readString("Введите жанр точно как в списке: ");
                try {
                    strategy = new GenreSearch(Genres.valueOf(genreName.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    System.out.println("Жанр не найден. Поиск отменён.");
                    return;
                }
            }
            default -> {
                System.out.println("Неверный выбор.");
                return;
            }
        }

        library.search(strategy);
    }

    private void issuePublication() {
        System.out.println("\nВыдача издания:");

        List<Client> clients = library.getClients();
        if (clients.isEmpty()) {
            System.out.println("️ Нет зарегистрированных читателей.");
            return;
        }
        System.out.println("\nЧитатели:");
        library.getClients().forEach(Client::printClient);
        int clientId = readInt("Введите ID читателя: ");
        Client client = clients.stream().filter(c -> c.getId() == clientId).findFirst().orElse(null);
        if (client == null) { System.out.println("Читатель не найден."); return; }

        List<Publication> pubs = library.getPublications().stream()
                .filter(p -> p.getCounts() > 0)
                .toList();
        if (pubs.isEmpty()) {
            System.out.println("Нет доступных изданий.");
            return;
        }

        System.out.println("\nДоступные издания:");
        library.getPublications().forEach(Publication::smallPrintPublication);
        String pubId = readString("Введите идентификатор издания: ");
        Publication pub = pubs.stream().filter(p -> p.getIdentifier().equals(pubId)).findFirst().orElse(null);
        if (pub == null) { System.out.println("Издание не найдено или отсутствует."); return; }
        Loan loan = library.issueBook(client, pub);

        if (loan != null) {
            System.out.println("Выдача успешна");
        } else {
            System.out.println("Не удалось выдать (проверьте лимиты или наличие).");
        }
    }

    private void returnPublication() {
        System.out.println("\nВозврат издания:");
        List<Loan> activeLoans = library.getLoans().stream()
                .filter(l -> l.canReturn())
                .toList();

        if (activeLoans.isEmpty()) {
            System.out.println("Нет активных займов.");
            return;
        }

        System.out.println("\nАктивные займы:");
        library.getLoans().forEach(Loan::printLoan);

        int loanId = readInt("Введите ID займа для возврата: ");
        Loan loan = activeLoans.stream().filter(l -> l.getId() == loanId).findFirst().orElse(null);

        if (loan == null) {
            System.out.println("Займ не найден.");
            return;
        }

        library.returnBook(loan);
    }

    private void printInfo() {
        System.out.println("\nКаталог:");
        library.getPublications().forEach(Publication::printPublication);

        System.out.println("\nЧитатели:");
        library.getClients().forEach(Client::printClient);
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное целое число.");
            }
        }
    }

    private int readValidInt(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Значение должно быть от " + min + " до " + max + ".");
        }
    }
}