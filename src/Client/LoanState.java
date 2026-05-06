package Client;

public interface LoanState {
    boolean canReturn();
    long getOverdueDays(Loan loan);
    String getStatusName();
}
