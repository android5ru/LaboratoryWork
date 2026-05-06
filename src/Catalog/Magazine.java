package Catalog;

public class Magazine extends Publication {
    public Magazine(String name, int year, Genres genre, String editorial, String issueNumber, int counts) {
        super(name, year, genre, editorial, issueNumber, counts);
    }

    @Override
    public void printPublication() {
        System.out.println("Название журнала: " + this.name);
        System.out.println("Год выпуска: " + this.year);
        System.out.println("Жанр журнала: " + this.genre);
        System.out.println("Редакция журнала: " + this.author);
        System.out.println("Номер журнала: " + this.identifier);
        System.out.println("Количество журналов: " + this.counts);
    }

    @Override
    public PublicationType getTypeName(){
        return PublicationType.MAGAZINE;
    }


}
