package Catalog;

public class MagazineFactory implements PublicationFactory{
    @Override
    public Publication create(String name, int year, Genres genre, String author, String identifier, int counts) {
        return new Magazine(name, year, genre, author, identifier, counts);
    }
}
