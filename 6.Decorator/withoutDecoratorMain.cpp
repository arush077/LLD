class Pizza {
    public int cost() {
        return 100;
    }
}

class OnionPizza {
    public int cost() {
        return 130; // 100 + 30
    }
}

class TomatoPizza {
    public int cost() {
        return 120; // 100 + 20
    }
}

class OnionTomatoPizza {
    public int cost() {
        return 150; // 100 + 30 + 20
    }
}

public class withoutDecoratorMain {
    public static void main(String[] args) {
        OnionTomatoPizza pizza = new OnionTomatoPizza();
        System.out.println("Cost: " + pizza.cost());
    }
}
