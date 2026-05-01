import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private List<Client> clients = new ArrayList<>();
    private List<Publication> publications = new ArrayList<>();;
    private List<Loan> loans = new ArrayList<>();

    private Map<PublicationType, LendingRule> lendingRules = new HashMap<>();
    private FineCalculator fineCalculator = new DayFineCalculator(1.5);

    public Library(){
        lendingRules.put(PublicationType.BOOK, new BookLendingRule());
        lendingRules.put(PublicationType.MAGAZINE, new MagazineLendingRule());
    }

    public void setFineCalculator(FineCalculator calculator){
        this.fineCalculator = calculator;
    }

    public void registerRule(PublicationType typeName, LendingRule rule){
        lendingRules.put(typeName, rule);
    }

    public void newClient(String name){
        clients.add(new Client(name));
    }

    public void newPublication(Publication publication){
        publications.add(publication);
    }

    public void search(SearchStrategy strategy){
        List<Publication> result = new ArrayList<>();
        for (Publication pub: publications){
            if(strategy.matches(pub)){
                result.add(pub);
            }
        }
        if (!result.isEmpty()){
            for (Publication pub: result){
                pub.printPublication();
            }
        }
        else{
            System.out.println("Ничего не найдено");
        }
    }

    public Loan issueBook(Client client, Publication publication){
        PublicationType typeName = publication.getTypeName();
        LendingRule rule = lendingRules.getOrDefault(typeName, new BookLendingRule());

        if (client.getLoansCountByType(typeName) >= rule.MaxCopiesPerClient()){
            System.out.println("Превышен лимит займов для этого типа изданий");
            return null;
        }

        if(!publication.tryBorrow()){
            System.out.println(publication.getName() + " - нет в наличии");
            return null;
        }

        Loan loan = new Loan(client, publication, rule.MaxLoanDays());
        client.addLoan(loan);
        loans.add(loan);

        System.out.println("Выдано: " + publication.getName() + " читателю " + client.getName() + ". Вернуть до: " + loan.getDueDate());
        return loan;
    }

    public void returnBook(Loan loan){
        if(loan.isReturned()){
            System.out.println("Эта книга уже возвращена");
            return;
        }

        loan.markReturned();
        loan.getPublication().returnCopy();
        loan.getClient().removeLoan(loan);
        System.out.println("Возвращено: " + loan.getPublication().getName() + " от " + loan.getClient().getName());

        long overdueDays = loan.getOverdueDays();
        double fine = fineCalculator.calculate(overdueDays);
        if(overdueDays > 0){
            System.out.println("Просрочен: " + overdueDays + " дн.");
            System.out.println("Штраф: " + fine + " руб.");
        }
        else{
            System.out.println("Возвращено вовремя");
        }
    }

    public Client getClient(int index) {
        return clients.get(index);
    }

    public Publication getPublications(int index){
        return publications.get(index);
    }
}
