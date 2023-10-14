package adventuregame.models.character;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Character {
    private int id;
    private String name;
    private double damage;
    private double health;
    private double initialHealth;
    private double money;

    public Character(int id, String name, double damage, double health, double money) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.health = health;
        this.initialHealth = health;
        this.money = money;
    }
}
