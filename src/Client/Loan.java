package Client;

import Catalog.Publication;

import java.time.LocalDate;

public class Loan {
    private static int idCounter = 0;
    private int id;
    private Client client;
    private Publication publication;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LoanState state = new ActiveState();

    public Loan(Client client, Publication publication, int loanDays) {
        this.id = idCounter++;
        this.client = client;
        this.publication = publication;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(loanDays);   //Строка для стандартной работы модели
        //this.dueDate = borrowDate.minusDays(10);      //Строка для демонстрации просрочки срока
    }

    public LoanState getState() {
        return state;
    }

    public void setState(LoanState state) {
        this.state = state;
    }

    public void checkStatus(){
        if(!(state.getStatusName().equals("Возвращен")) && LocalDate.now().isAfter(dueDate)){
            this.state = new OverdueState();
        }
    }
    public boolean canReturn(){
        checkStatus();
        return state.canReturn();
    }

    public String getStatus(){
        checkStatus();
        return state.getStatusName();
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

    public void markReturned(){
        this.returnDate = LocalDate.now();
        this.state = new ReturnedState();
    }

    public long getOverdueDays(){
        checkStatus();
        return state.getOverdueDays(this);
    }

    public void printLoan(){
        System.out.println(id + ". " + publication.getName() + " -> " + client.getName() + " (вернуть до: " + dueDate + ")");
    }
}
