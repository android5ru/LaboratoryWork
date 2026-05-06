package Catalog;

public class Book extends Publication {
    public Book(String name, int year, Genres genre, String author, String ISBN, int counts) {
        super(name, year, genre, author, ISBN, counts);
    }

    @Override
    public void printPublication() {
        System.out.println("Название книги: " + this.name);
        System.out.println("Год выпуска: " + this.year);
        System.out.println("Жанр книги: " + this.genre);
        System.out.println("Автор книги: " + this.author);
        System.out.println("ISBN книги: " + this.identifier);
        System.out.println("Количество книг: " + this.counts);
    }

    @Override
    public PublicationType getTypeName(){
        return PublicationType.BOOK;
    }
}
