public class AuthorSearch implements SearchStrategy{
    private String author;

    public AuthorSearch(String author){
            this.author = author;
    }
    @Override
    public boolean matches(Publication publication){
        return publication.getAuthor().contains(author);
    }
}
