public class PublicationFactory {
    public static Publication create(PublicationType type, String name, int year, Genres genre, String author, String identifier, int counts){
        return switch (type){
            case BOOK -> new Book(name, year, genre, author, identifier, counts);
            case MAGAZINE -> new Magazine(name, year, genre, author, identifier, counts);
        };
    }
}
