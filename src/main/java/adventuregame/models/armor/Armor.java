package adventuregame.models.armor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Armor {
    private int id;
    private String name;
    private double defence;
    private double price;

    public Armor(int id, String name, double defence, double price) {
        this.id = id;
        this.name = name;
        this.defence = defence;
        this.price = price;
    }
}
