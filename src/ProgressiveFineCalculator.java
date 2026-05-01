public class ProgressiveFineCalculator implements FineCalculator{
    @Override
    public double calculate(long overdueDays) {
        if (overdueDays <= 7) return overdueDays * 1.0;
        return 7*1.0 + (overdueDays - 7) * 5.0;
    }
}
