//Candy.java

public class Candy {
    private String name;
    private int stock;
    private int capacity;
    private int id;

    public Candy(String _name, int inStock, int maxStockCapacity, int id) {
        this.name = _name;
        this.stock = inStock;
        this.capacity = maxStockCapacity;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getStockLevel() {
        return this.stock;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getID() {
        return this.id;
    }

}
