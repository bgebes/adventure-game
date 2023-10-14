package adventuregame.providers;

import adventuregame.bin.Game;
import adventuregame.models.character.Character;
import adventuregame.models.obstacle.Obstacle;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CharacterProvider {
    public static ArrayList<Character> characters;

    static {
        characters = new ArrayList<Character>();
        characters.add(new Character(0, "Samurai", 5, 21, 15));
        characters.add(new Character(1, "Archer", 7, 18, 20));
        characters.add(new Character(2, "Knight", 8, 24, 5));
    }

    public static Character selectCharacter() {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("____________________________________________________\n");
            builder.append("\tWhich character you want to be? \n");

            for (Character c : characters) {
                String line = String.format("\t%d-%s\tDamage: %.2f\tHealth: %.2f\tMoney: %.2f\n",
                        c.getId() + 1, c.getName(), c.getDamage(), c.getHealth(), c.getMoney());
                builder.append(line);
            }

            builder.append("____________________________________________________");
            System.out.println(builder.toString());

            System.out.print("Response: ");
            int id = GenericProvider.scanner.nextInt();
            if (!GenericProvider.checkIntInRange(characters.get(0).getId() + 1, characters.get(characters.size() - 1).getId() + 1, id)) {
                System.out.println("Out of list!");
                return selectCharacter();
            }

            Character latestCharacter = (Character) GenericProvider.findObjectById(characters, id - 1);
            System.out.printf("Congratulations! You're %s!\n", latestCharacter.getName());

            return latestCharacter;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return selectCharacter();
        }
    }

    public static boolean attack(Game game, Obstacle obstacle) {
        double obstacleHealth = obstacle.getHealth();
        if (obstacleHealth == 0.0) return false;

        Character character = game.getCharacter();

        double weaponDamage = game.getWeapon() == null ? 0.0 : game.getWeapon().getDamage();
        double playerDamage = character.getDamage() + weaponDamage;

        double latestHealth = obstacleHealth - playerDamage;
        obstacle.setHealth(latestHealth > 0 ? latestHealth : 0);

        return true;
    }
}
