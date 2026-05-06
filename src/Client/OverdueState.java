package Client;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class OverdueState implements LoanState{
    @Override
    public boolean canReturn() {
        return true;
    }

    @Override
    public String getStatusName() {
        return "Просрочен";
    }

    @Override
    public long getOverdueDays(Loan loan) {
        return ChronoUnit.DAYS.between(loan.getDueDate(), LocalDate.now());
    }
}
