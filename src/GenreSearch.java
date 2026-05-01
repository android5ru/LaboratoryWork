public class GenreSearch implements SearchStrategy{
    private Genres genre;

    public GenreSearch(Genres genre){
        this.genre = genre;
    }
    @Override
    public boolean matches(Publication publication){
        return publication.getGenre() == genre;
    }
}
