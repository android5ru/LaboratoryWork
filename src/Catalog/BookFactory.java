package Catalog;

public class BookFactory implements PublicationFactory{
    @Override
    public Publication create(String name, int year, Genres genre, String author, String identifier, int counts) {
        return new Book(name, year, genre, author, identifier, counts);
    }
}
