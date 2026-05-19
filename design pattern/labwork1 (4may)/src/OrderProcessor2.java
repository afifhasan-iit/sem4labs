class OrderProcessor2 {

    public double processOrder(String[][] order, String paymentType, String shippingType) {

        CalculateTotal calc = new CalculateTotal();
        double total = calc.calculateTotal((order));


        ApplyDiscount dis = new ApplyDiscount();
        double discountedTotal = dis.applyD(total);

        PaymentProcess pp = new PaymentProcess();
        pp.paymentProcess(paymentType);

        Standard st = new Standard();
        st.shipping(shippingType);

        Notification notif = new Notification();
        notif.showNotification();


        return discountedTotal;
    }
}