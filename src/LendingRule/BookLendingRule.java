package LendingRule;

public class BookLendingRule implements LendingRule {
    private final int DEFAULT_MAX_LOAN_DAYS = 21;
    private final int DEFAULT_MAX_COPIES_PER_CLIENT = 5;
    @Override
    public int maxLoanDays() {
        return DEFAULT_MAX_LOAN_DAYS;
    }

    @Override
    public int maxCopiesPerClient() {
        return DEFAULT_MAX_COPIES_PER_CLIENT;
    }
}
