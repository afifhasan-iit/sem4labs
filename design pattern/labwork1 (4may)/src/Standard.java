public class Standard {
    public void shipping(String shippingType){
        if (shippingType.equals("standard")) {
            System.out.println("Standard shipping selected");
        } else if (shippingType.equals("express")) {
            System.out.println("Express shipping selected");
        }
    }
}
