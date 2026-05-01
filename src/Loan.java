import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
    private static int idCounter = 0;
    private int id;
    private Client client;
    private Publication publication;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean isReturned;

    public Loan(Client client, Publication publication, int loanDays) {
        this.id = idCounter++;
        this.client = client;
        this.publication = publication;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(loanDays);   //Строка для стандартной работы модели
        //this.dueDate = borrowDate.minusDays(10);      //Строка для демонстрации просрочки срока
        this.isReturned = false;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Publication getPublication() {
        return publication;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void markReturned(){
        this.returnDate = LocalDate.now();
        this.isReturned = true;
    }

    public boolean isOverdue(){
        if (isReturned){
            return  returnDate.isAfter(dueDate);
        }
        return LocalDate.now().isAfter(dueDate);
    }

    public long getOverdueDays(){
        if (!isOverdue()) return 0;
        LocalDate checkDate = isReturned ? returnDate : LocalDate.now();
        return ChronoUnit.DAYS.between(dueDate, checkDate);
    }

    public void printLoan(){
        System.out.println("  " + publication.getName() + " (вернуть до: " + dueDate + ")");
    }
}
