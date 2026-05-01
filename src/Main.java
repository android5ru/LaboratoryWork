import java.lang.ref.Cleaner;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {
    public static void main(String[] args) {
        Library lib = new Library();
        System.out.println("1. Регистрация читателей");
        lib.newClient("Иван Петров");
        lib.newClient("Анна Сидорова");
        Client ivan = lib.getClient(0);
        Client anna = lib.getClient(1);
        System.out.println("Зарегистрированы: " + ivan.getName() + ", " + anna.getName());

        System.out.println("2. Добавление изданий");
        lib.newPublication(new PublicationBuilder().type(PublicationType.BOOK).name("Война и мир").year(1869).
                genre(Genres.ARTISTIC_LITERATURE).author("Л.Н. Толстой").identifier("978-5-17-001").counts(3).build());


        lib.newPublication(new PublicationBuilder().type(PublicationType.BOOK).name("Java для начинающих").year(2024).
                genre(Genres.EDUCATIONAL).author("Дж. Смит").identifier("978-5-17-002").counts(2).build());

        lib.newPublication(new PublicationBuilder().type(PublicationType.MAGAZINE).name("Forbes").year(2024).
                genre(Genres.SCIENTIFIC).author("Редакция").identifier("№5-2024").counts(10).build());

        System.out.println("Добавлено 3 издания");


        System.out.println("\n3. Поиск изданий");
        System.out.println("\nПоиск по названию 'Война и мир':");
        lib.search(new NameSearch("Война и мир"));
        System.out.println("\nПоиск по автору 'Толстой':");
        lib.search(new AuthorSearch("Толстой"));
        System.out.println("\nПоиск по жанру EDUCATIONAL:");
        lib.search(new GenreSearch(Genres.EDUCATIONAL));

        System.out.println("\n4. Выдача изданий");
        Publication warAndPeace = lib.getPublications(0);
        Publication javaBook = lib.getPublications(1);
        Publication forbes = lib.getPublications(2);

        Loan loan1 = lib.issueBook(ivan, warAndPeace);
        Loan loan2 = lib.issueBook(anna, javaBook);

        lib.issueBook(ivan, warAndPeace); //2-й экземпляр
        lib.issueBook(anna, warAndPeace); //3-й экземпляр — последний

        System.out.println("\nПопытка выдать отсутствующий экземпляр:");
        lib.issueBook(ivan, warAndPeace);

        //Проверка лимита (для журналов лимит 3)
        System.out.println("\nПроверка лимита для журналов:");
        lib.issueBook(ivan, forbes);
        lib.issueBook(ivan, forbes);
        lib.issueBook(ivan, forbes);
        lib.issueBook(ivan, forbes); //Превышен лимит


        //Используется установка даты системы, поэтому все возвраты будут вовремя возвращены
        //Для демонстрации просрочки даты
        //В классе Loan в конструкторе есть подготовленная строка, которая поставит дату возврата на 10 назад от нынешней
        System.out.println("\n5. Возврат изданий");
        System.out.println("\nВозврат:");
        lib.returnBook(loan1);

        System.out.println("\nВозврат:");
        lib.returnBook(loan2);


        System.out.println("\n6. Динамическая смена правил");
        System.out.println("\nМеняем калькулятор штрафов на прогрессивный:");
        lib.setFineCalculator(new ProgressiveFineCalculator());
        Loan loan3 = lib.issueBook(ivan, javaBook);
        lib.returnBook(loan3);

        lib.registerRule(PublicationType.MAGAZINE, new LendingRule() {
            public int MaxLoanDays() { return 14; }
            public int MaxCopiesPerClient() { return 5; }
        });
        //Следующая выдача журнала будет по новым правилам

        System.out.println("\n7. Итоговое состояние");

        System.out.println("\nКниги Ивана:");
        ivan.printClient();
        System.out.println("\nДоступные экземпляры 'Война и мир':");
        System.out.println("  " + warAndPeace.getCounts() + " шт.");
    }
}