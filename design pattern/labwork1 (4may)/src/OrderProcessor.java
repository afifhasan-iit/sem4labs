class OrderProcessor {

    public double processOrder(String[][] order, String paymentType, String shippingType) {
        double total = 0;
// Calculate total
        for (String[] item : order) {
            total += Double.parseDouble(item[1]);
        }
// Apply discount
        if (total > 100) {
            total *= 0.9;
        }
// Payment processing
        if (paymentType.equals("credit")) {
            System.out.println("Processing credit card payment");
        } else if (paymentType.equals("paypal")) {
            System.out.println("Processing PayPal payment");
        }
// Shipping
        if (shippingType.equals("standard")) {
            System.out.println("Standard shipping selected");
        } else if (shippingType.equals("express")) {
            System.out.println("Express shipping selected");
        }
// Notification
        System.out.println("Sending email notification");
        return total;
    }
}