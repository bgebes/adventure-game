package adventuregame.providers;

import adventuregame.bin.Game;
import adventuregame.models.character.Character;
import adventuregame.models.obstacle.Obstacle;

import java.util.ArrayList;

public class ObstacleProvider {
    public static ArrayList<Obstacle> obstacles;

    static {
        obstacles = new ArrayList<Obstacle>();
        obstacles.add(new Obstacle(0, "Zombie", 3, 10, 4));
        obstacles.add(new Obstacle(1, "Vampire", 4, 14, 7));
        obstacles.add(new Obstacle(2, "Bear", 7, 20, 12));
        obstacles.add(new Obstacle(3, "Snake", GenericProvider.getRandomInt(3, 6), 12, 0));
    }

    public static boolean attack(Game game, Obstacle obstacle) {
        Character character = game.getCharacter();

        double playerHealth = character.getHealth();
        if (playerHealth == 0.0) return false;

        double defence = game.getArmor() == null ? 0.0 : game.getArmor().getDefence();
        double obstacleDamage = obstacle.getDamage() - defence;

        double latestHealth = playerHealth - obstacleDamage;
        character.setHealth(latestHealth > 0 ? latestHealth : 0);

        return true;
    }
}
