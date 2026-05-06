package Catalog;

public interface PublicationFactory {
    Publication create(String name, int year, Genres genre, String author, String identifier, int counts);
}
