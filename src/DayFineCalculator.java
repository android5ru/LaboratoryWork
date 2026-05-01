public class DayFineCalculator implements FineCalculator{
    private double ratePerDay;
    public DayFineCalculator(double ratePerDay){this.ratePerDay = ratePerDay;}

    @Override
    public double calculate(long overdueDays) {
        return overdueDays*ratePerDay;
    }
}
