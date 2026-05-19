public class ApplyDiscount {
    public double applyD(double total){
        if (total > 100) {
            total *= 0.9;
        }

        return total;
    }
}
