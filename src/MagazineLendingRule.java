public class MagazineLendingRule implements LendingRule{

    @Override
    public int MaxLoanDays() {
        return 7;
    }

    @Override
    public int MaxCopiesPerClient() {
        return 3;
    }
}
