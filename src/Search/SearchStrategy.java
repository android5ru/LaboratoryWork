package Search;

import Catalog.Publication;

public interface SearchStrategy {
    boolean matches(Publication publication);
}
