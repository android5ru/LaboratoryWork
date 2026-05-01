public class PublicationBuilder {
    private PublicationType type;
    private String name;
    private int year;
    private Genres genre;
    private String author;
    private String identifier;
    private int counts;

    public PublicationBuilder type(PublicationType type) {
        this.type = type;
        return this;
    }

    public PublicationBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PublicationBuilder year(int year) {
        this.year = year;
        return this;
    }

    public PublicationBuilder genre(Genres genre) {
        this.genre = genre;
        return this;
    }

    public PublicationBuilder author(String author) {
        this.author = author;
        return this;
    }

    public PublicationBuilder identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public PublicationBuilder counts(int counts) {
        this.counts = counts;
        return this;
    }

    public Publication build(){
        return PublicationFactory.create(type, name, year, genre, author, identifier, counts);
    }

}
