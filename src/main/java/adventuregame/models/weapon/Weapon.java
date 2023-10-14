package adventuregame.models.weapon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weapon {
    private int id;
    private String name;
    private double damage;
    private double price;

    public Weapon(int id, String name, double damage, double price) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.price = price;
    }
}
