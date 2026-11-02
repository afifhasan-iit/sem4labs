//Done

// Scenario:
// When the `processOrder()` method is called, it's passing around a large number of parameters. 
// This makes it hard to understand and use correctly, and any future changes in the way we store customer information 
// or order details will require us to update every method that uses this signature. If you introduce a new property for 
// `Customer`, such as `email`, every method call must be updated. This increases the risk of errors and increases the 
// maintenance burden.
//
// Task for Students:
// Refactor the `processOrder()` method by grouping related parameters into appropriate objects 
// (e.g., `Customer`, `Product`, `ShippingDetails`).
// Apply the Introduce Parameter Object refactoring technique.

public class OrderProcessor {

    public void processOrder(Customer customer, Product product, Order orderDetail) {
        System.out.println("order processing done");
    }

    public static void main(String[] args) {
        String customerName = "Rahim";
        String customerAddress = "Dhaka, Bangladesh";
        String customerPhone = "01711111111";
        String productName = "Laptop";
        int quantity = 1;
        double price = 50000;
        double discount = 0.1;

        String paymentMethod = "Credit Card";
        String shippingMethod = "Express Shipping";
        Customer customer = new Customer(customerName,customerAddress,customerPhone);
        Product product = new Product(productName,quantity,price,discount);
        Order orderDetail = new Order(paymentMethod,shippingMethod);



        OrderProcessor processor = new OrderProcessor();
        processor.processOrder(customer,product,orderDetail);
    }

}

class Customer{
    private String name;
    private String address;
    private String phone;

    public Customer(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

class Product{
    private String name;
    private int quantity;
    private double price;
    private double discount;

    public Product(String name, int quantity, double price, double discount) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}

class Order{
    private String paymentMethod;
    private String ShipmentMethod;

    public Order(String paymentMethod, String shipmentMethod) {
        this.paymentMethod = paymentMethod;
        ShipmentMethod = shipmentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShipmentMethod() {
        return ShipmentMethod;
    }

    public void setShipmentMethod(String shipmentMethod) {
        ShipmentMethod = shipmentMethod;
    }
}