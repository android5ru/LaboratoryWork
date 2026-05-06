package Search;

import Catalog.Publication;

public class NameSearch implements SearchStrategy {
    private String name;

    public NameSearch(String name){
        this.name = name;
    }
    @Override
    public boolean matches(Publication publication){
        return publication.getName().contains(name);
    }
}
