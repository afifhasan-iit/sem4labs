//DONE

// Scenario:
// The code is using a `switch` statement to handle the processing of different order types. 
// However, as new order types are added, you will need to modify the `switch` statement in many places 
// throughout the code, which leads to code duplication and introduces the risk of inconsistent logic in 
// different parts of the system. This becomes difficult to maintain and scale over time.
//
// Task for Students:
// Refactor the code to remove the `switch` statement by using polymorphism. Each `OrderType` should be a 
// subclass or have its own class with a specific `processOrder()` method. 
// Apply the **Replace Conditional with Polymorphism** refactoring technique.



public interface ProcessOrder{
    void processOrder();
}

class OnlineOrder implements ProcessOrder{
    @Override
    public void processOrder() {
        System.out.println("Processing online order");
    }
}

class OfflineOrder implements ProcessOrder{
    @Override
    public void processOrder() {
        System.out.println("Processing offline order");
    }
}

class SpecialOrder implements ProcessOrder{
    @Override
    public void processOrder() {
        System.out.println("Processing special order");
    }
}

class Main{
    public static void main(String[] args) {
        ProcessOrder onlineOrder = new OnlineOrder();
        onlineOrder.processOrder();
    }
}