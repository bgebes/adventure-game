package adventuregame.models.location.battleLocation;

import adventuregame.bin.Game;
import adventuregame.models.character.Character;
import adventuregame.models.item.Item;
import adventuregame.models.location.Location;
import adventuregame.models.obstacle.Obstacle;
import adventuregame.providers.*;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedInputStream;
import java.lang.reflect.Array;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class BattleLocation extends Location {
    private ArrayList<Obstacle> obstacles;
    private Obstacle obstacle;
    private int obstacleCount;
    private ValueRange obstacleCountRange;
    private Item award;

    public BattleLocation(int id, String name, String message, Obstacle obstacle, ValueRange obstacleCountRange, Item award) {
        super(id, name, message);

        setObstacle(obstacle);
        setObstacleCount(0);
        setObstacleCountRange(obstacleCountRange);
        setAward(award);
    }

    public void combat(Game game) {
        int firstAttack = GenericProvider.getRandomInt(1, 2);

        Character character = game.getCharacter();

        Runnable playerAttackFunction = () -> {
            for (Obstacle o : obstacles) {
                CharacterProvider.attack(game, o);
            }

            System.out.printf("%s has been attacked to %s! %s's health is %.2f\n",
                    character.getName(),
                    obstacles.get(0).getName(),
                    obstacles.get(0).getName(),
                    obstacles.get(0).getHealth());
        };

        Runnable obstacleAttackFunction = () -> {
            ObstacleProvider.attack(game, obstacles.get(0));

            System.out.printf("%s has been attacked to %s! %s's health is %.2f\n",
                    obstacle.getName(),
                    character.getName(),
                    character.getName(),
                    character.getHealth());
        };

        Runnable firstAttackFunction, secondAttackFunction;
        if (firstAttack == 1) {
            firstAttackFunction = playerAttackFunction;
            secondAttackFunction = obstacleAttackFunction;
        } else {
            firstAttackFunction = obstacleAttackFunction;
            secondAttackFunction = playerAttackFunction;
        }

        System.out.printf("Combat Start! %s's Health: %.2f, %s's Health: %.2f\n",
                character.getName(), character.getHealth(), obstacles.get(0).getName(), obstacles.get(0).getHealth());

        while (character.getHealth() != 0.0 && getTotalHealthOfObstacles() != 0.0) {
            firstAttackFunction.run();
            if (character.getHealth() == 0.0 || getTotalHealthOfObstacles() == 0.0) break;
            secondAttackFunction.run();
        }

        if (character.getHealth() == 0.0) {
            System.out.printf("Game Over! %s has been died!\n", character.getName());
            System.exit(0);
        } else if (getTotalHealthOfObstacles() == 0.0) {
            System.out.printf("You Win! %ss has been died!\n", obstacles.get(0).getName());

            if (award != null) {
                ItemProvider.claimItem(game.getItems(), award, game);
                award = null;
            }

            character.setMoney(character.getMoney() + obstacles.get(0).getAward() * obstacleCount);
            System.out.printf("Your money is $ %.2f now!\n", character.getMoney());
        }
    }

    @Override
    public boolean onLocation(Game game) {
        setObstacleCount(GenericProvider.getRandomInt(
                (int) obstacleCountRange.getMinimum(),
                (int) obstacleCountRange.getMaximum()));
        combat(game);

        LocationProvider.selectLocation(game);
        return true;
    }

    public double getTotalHealthOfObstacles() {
        return obstacles.stream().mapToDouble(Obstacle::getHealth).sum();
    }

    public void setObstacleCount(int obstacleCount) {
        this.obstacleCount = obstacleCount;
        this.obstacles = new ArrayList<Obstacle>();

        for (int i = 0; i < obstacleCount; i++) {
            this.obstacles.add(
                    new Obstacle(
                            obstacle.getId(),
                            obstacle.getName(),
                            obstacle.getDamage(),
                            obstacle.getHealth(),
                            obstacle.getAward()));
        }
    }
}
