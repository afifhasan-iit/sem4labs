public interface PaymentGateway {
    void process();
}



class Paypal implements PaymentGateway {
    public void process(){
        System.out.println("Processing credit card payment");
    }
}

class Credit implements PaymentGateway, RefundablePaymentGateway{
    public void process(){
        System.out.println("Processing credit card payment");

    }
    public void refund(){
        System.out.println("Refund done");
    }
}