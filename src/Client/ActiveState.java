package Client;

public class ActiveState implements LoanState{

    @Override
    public boolean canReturn() {
        return true;
    }

    @Override
    public String getStatusName() {
        return "Активен";
    }

    @Override
    public long getOverdueDays(Loan loan) {
        return 0;
    }
}
