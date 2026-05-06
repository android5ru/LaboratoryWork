package Client;

import java.time.temporal.ChronoUnit;

public class ReturnedState implements LoanState{

    @Override
    public boolean canReturn() {
        return false;
    }

    @Override
    public String getStatusName() {
        return "Возвращен";
    }

    @Override
    public long getOverdueDays(Loan loan) {
        long days = ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate());
        return days > 0 ? days : 0;
    }
}
