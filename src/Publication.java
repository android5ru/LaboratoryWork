public abstract class Publication {
    protected String name;
    protected int year;
    protected Genres genre;
    protected String author;
    protected String identifier;
    protected int counts;

    public abstract void printPublication();
    public abstract PublicationType getTypeName();

    public Publication(String name, int year, Genres genre, String author, String identifier, int counts) {
        this.name = name;
        this.year = year;
        this.genre = genre;
        this.author = author;
        this.identifier = identifier;
        this.counts = counts;
    }

    public boolean tryBorrow(){
        if (counts > 0){
            counts--;
            return true;
        }
        return false;
    }
    public void returnCopy(){
        counts++;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public Genres getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getCounts() {
        return counts;
    }

}
