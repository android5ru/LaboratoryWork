package FineCalculator;

public class ProgressiveFineCalculator implements FineCalculator {
    private final int FIRST_WEEK = 7;
    private final double FIRST_WEEK_FINE = 1.0;
    private final double AFTER_FINE = 5.0;
    @Override
    public double calculate(long overdueDays) {
        if (overdueDays <= FIRST_WEEK) return overdueDays * FIRST_WEEK_FINE;
        return FIRST_WEEK*FIRST_WEEK_FINE + (overdueDays - FIRST_WEEK) * AFTER_FINE;
    }
}
