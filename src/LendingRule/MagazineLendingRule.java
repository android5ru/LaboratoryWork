package LendingRule;

public class MagazineLendingRule implements LendingRule {
    private final int DEFAULT_MAX_LOAN_DAYS = 7;
    private final int DEFAULT_MAX_COPIES_PER_CLIENT = 3;
    @Override
    public int maxLoanDays() {
        return DEFAULT_MAX_LOAN_DAYS;
    }

    @Override
    public int maxCopiesPerClient() {
        return DEFAULT_MAX_COPIES_PER_CLIENT;
    }
}
