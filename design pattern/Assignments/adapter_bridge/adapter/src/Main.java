

interface PaymentGateway {
    void pay(PaymentRequest req);
}

class PaymentRequest {
    private final String transactionId;
    private final double amount;
    private final String currency;
    private final String customer;

    public PaymentRequest(String transactionId, double amount, String currency, String customer) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.customer = customer;
    }

    public String getTransactionId() { return transactionId; }
    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getCustomer() { return customer; }
}

class CheckoutService {
    private final PaymentGateway gateway;

    public CheckoutService(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    public void processOrder(PaymentRequest request) {
        System.out.println("Processing order for " + request.getCustomer());
        gateway.pay(request);
    }
}



class StripeAPI {
    public void makeCharge(double amountInCents, String currency, String customerEmail) {
        System.out.println("Stripe: charged " + amountInCents + " cents (" + currency + ") to " + customerEmail);
    }
}

class StripeAdapter implements PaymentGateway {
    private final StripeAPI stripeApi = new StripeAPI();

    @Override
    public void pay(PaymentRequest req) {
        double amountInCents = req.getAmount() * 100;
        stripeApi.makeCharge(amountInCents, req.getCurrency(), req.getCustomer());
    }
}



class PayPalAPI {
    public void sendPayment(String payerAccount, double amount) {
        System.out.println("PayPal: sent " + amount + " from account " + payerAccount);
    }
}

class PayPalAdapter implements PaymentGateway {
    private final PayPalAPI payPalApi = new PayPalAPI();

    @Override
    public void pay(PaymentRequest req) {
        payPalApi.sendPayment(req.getCustomer(), req.getAmount());
    }
}

class BkashAPI {
    public void processTransaction(String phoneNumber, double taka, String txnId) {
        System.out.println("bKash: processed " + taka + " taka from " + phoneNumber + " (txn: " + txnId + ")");
    }
}

class BkashAdapter implements PaymentGateway {
    private final BkashAPI bkashApi = new BkashAPI();

    @Override
    public void pay(PaymentRequest req) {
        bkashApi.processTransaction(req.getCustomer(), req.getAmount(), req.getTransactionId());
    }
}


public class Main {
    public static void main(String[] args) {

        PaymentRequest req1 = new PaymentRequest("TXN001", 49.99, "USD", "alice@example.com");
        PaymentRequest req2 = new PaymentRequest("TXN002", 25.00, "USD", "bob@example.com");
        PaymentRequest req3 = new PaymentRequest("TXN003", 1500.00, "BDT", "01711000000");

        CheckoutService stripeCheckout = new CheckoutService(new StripeAdapter());
        stripeCheckout.processOrder(req1);

        CheckoutService paypalCheckout = new CheckoutService(new PayPalAdapter());
        paypalCheckout.processOrder(req2);

        CheckoutService bkashCheckout = new CheckoutService(new BkashAdapter());
        bkashCheckout.processOrder(req3);
    }
}