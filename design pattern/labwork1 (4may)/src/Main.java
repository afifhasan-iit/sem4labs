
public class Main {
    public static void main(String[] args) {
        // Sample order: {itemName, price}
        String[][] order = {
                {"Item1", "50.0"},
                {"Item2", "60.0"}
        };

        String paymentType = "credit";   // or "paypal"
        String shippingType = "express"; // or "standard"

        OrderProcessor processor = new OrderProcessor();
        double total = processor.processOrder(order, paymentType, shippingType);
        System.out.println("Final total: " + total);


        System.out.println("\n\n\n\n");

        OrderProcessor2 processor2 = new OrderProcessor2();
        double total2 = processor2.processOrder(order,paymentType,shippingType);

        System.out.println("Final total: " + total2);
    }
}