import Catalog.Genres;
import Catalog.PublicationBuilder;
import Catalog.PublicationType;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        library.newPublication(new PublicationBuilder()
                .type(PublicationType.BOOK).name("Война и мир").year(1869)
                .genre(Genres.ARTISTIC_LITERATURE).author("Толстой")
                .identifier("ISBN-001").counts(3).build());

        library.newPublication(new PublicationBuilder()
                .type(PublicationType.MAGAZINE).name("Forbes").year(2024)
                .genre(Genres.FANTASTIC).author("Редакция")
                .identifier("N5-24").counts(5).build());

        library.newClient("Иван Петров");
        library.newClient("Анна Сидорова");

        LibraryConsole console = new LibraryConsole(library);
        console.start();
    }
}