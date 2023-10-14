package adventuregame.models.obstacle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Obstacle {
    private int id;
    private String name;
    private double damage;
    private double health;
    private double award;

    public Obstacle(int id, String name, double damage, double health, double award) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.health = health;
        this.award = award;
    }
}
