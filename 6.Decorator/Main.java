
//-------------------------------------------------Product and its concrete impl (for setting the base)--------------------------------------//
// Product Interface
interface Pizza {
    public int cost();
}

// Concrete Product (for setting the base on top of which we need to decorate)
class PlainPizza implements Pizza {
    public int cost(){ return 100; }
};


//-------------------------------------------------Decorator and its concrete impl ------------------------------------------------------//

// -------------------------------------------------
// Decorator and its concrete implementations
// -------------------------------------------------

// Decorator
abstract class PizzaDecorator implements Pizza {

    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }
}


// Onion topping
class OnionTopping extends PizzaDecorator {

    public OnionTopping(Pizza pizza) {
        super(pizza);
    }

    @Override
    public int cost() {
        return pizza.cost() + 30;
    }
}


// Tomato topping
class TomatoTopping extends PizzaDecorator {

    public TomatoTopping(Pizza pizza) {
        super(pizza);
    }

    @Override
    public int cost() {
        return pizza.cost() + 20;
    }
}


// -------------------------------------------------
// Main
// -------------------------------------------------

public class Main {

    public static void main(String[] args) {

        // 1. Manual decoration
        // First make the base
        PlainPizza base = new PlainPizza();
        // Then decorate
        OnionTopping onion = new OnionTopping(base);
        TomatoTopping tomatoOnionPizza = new TomatoTopping(onion);

        System.out.println("Cost1: " + tomatoOnionPizza.cost());


        // 2. Nested decoration

        Pizza pizza = new TomatoTopping( new OnionTopping( new PlainPizza()) );

        System.out.println( "Cost2: " + pizza.cost());
    }
}