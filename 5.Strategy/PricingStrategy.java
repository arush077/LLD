
class Context{
    PricingStrategy pricingStrategy;

    Context(OptionType type){
        //Pehle factory ka obj banao
        PricingStrategyFactory pricingStrategyFactory = new PricingStrategyFactory();

        //Pehle factory se pricingStrategy nikalega
        PricingStrategy pricingStrategy = pricingStrategyFactory.getStrategy(type);  
    }

    public double calculatePrice(){
        //Use that pricingStrategy to calculate the amount value
        double amount = pricingStrategy.calculatePrice();
        return amount;
    }
}

//----------------------------------------Factory + Interface + Conc impl ---------------------------------// 
// Factory bhi bana lena ====> WHYYY???
// becuase NormalPricing, LuxuryPricing ye user thodi bataega konsa class select karna he 
// user selects OptionType = normal, then we should use NormalPricing wala class
// so we have a factory class which takes in the OptionType and returns konsi pricingStrategy Object hogi acc to OptionType
// Eg = user selects OptionType = normal, our factory makes an object of NormalPricing and calls calculatePrice of it
// Eg = user selects OptionType = luxury, our factory makes an object of LuxuryPricing and calls calculatePrice of it

class PricingStrategyFactory{
    PricingStrategy pricingStrategy;

    public PricingStrategy getStrategy(OptionType type){
        if(type == OptionType.NORMAL){pricingStrategy = new NormalPricing(); return pricingStrategy;}
        if(type == OptionType.LUXURY){pricingStrategy = new LuxuryPricing(); return pricingStrategy;}

        return null;
    }
}

interface PricingStrategy {
    double calculatePrice();
}

class NormalPricing implements PricingStrategy {
    public double calculatePrice() {
        return 100;
    }
}

class LuxuryPricing implements PricingStrategy {
    public double calculatePrice() {
        return 500;
    }
}

enum OptionType{
    NORMAL,
    LUXURY
}

class Main{
    public static void main(String[] args) {
        OptionType optionType = OptionType.LUXURY; //user selects luxury
        Context context = new Context(optionType); // user interacts with the context class
        context.calculatePrice();
    }
}