import java.util.ArrayList;
import java.util.List;

public class Client {
    private static int counter = 0;
    private final int id;
    private String name;
    private List<Loan> loans = new ArrayList<>();

    public Client(String name) {
        this.name = name;
        this.id = counter++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLoansCountByType(PublicationType type){
        int count = 0;
        for (Loan loan: loans){
            if(!loan.isReturned() && loan.getPublication().getTypeName() == type){
                count++;
            }
        }
        return count;
    }

    public List<Loan> getLoans(){
        return new ArrayList<>(loans);
    }

    public void addLoan(Loan loan){
        loans.add(loan);
    }

    public void removeLoan(Loan loan){
        loans.remove(loan);
    }

    public void printClient(){
        System.out.println("Список взятых книг:");
        for (Loan ln: loans){
            ln.printLoan();
        }
    }
}
