public class PaymentProcess {
    PaymentGateway pg;
    public void paymentProcess(String paymentType){
        if (paymentType.equals("credit")) {
            pg = new Credit();
        } else if (paymentType.equals("paypal")) {
           pg = new Paypal();
        }

        ProcessPayment pp = new ProcessPayment();
        pp.processPayment(pg);
    }

}






