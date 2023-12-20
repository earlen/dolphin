//Distributor.java

import java.util.Map;

public class Distributor {
    private String name;
    private Map<String, Double> candyCosts; // Map ID to cost per unit

    public Distributor(String name, Map<String, Double> candyCosts) {
        this.name = name;
        this.candyCosts = candyCosts;
    }

}
