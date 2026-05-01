public class BookLendingRule implements LendingRule{

    @Override
    public int MaxLoanDays() {
        return 21;
    }

    @Override
    public int MaxCopiesPerClient() {
        return 5;
    }
}
