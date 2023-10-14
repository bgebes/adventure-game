package adventuregame.providers;

import adventuregame.bin.Game;
import adventuregame.models.item.Item;
import adventuregame.models.location.Location;
import adventuregame.models.location.battleLocation.BattleLocation;
import adventuregame.models.location.normalLocation.safehouse.SafeHouse;
import adventuregame.models.location.normalLocation.toolstore.ToolStore;
import adventuregame.models.obstacle.Obstacle;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.temporal.ValueRange;
import java.util.ArrayList;

public class LocationProvider {
    public static ArrayList<Location> locations;

    static {
        locations = new ArrayList<Location>();
        locations.add(new ToolStore(0, "Tool Store", "You can buy weapon and armor!"));
        locations.add(new SafeHouse(1, "Safe House", "Your health will be restore!"));

        ArrayList<Obstacle> obstacles = ObstacleProvider.obstacles;
        ArrayList<Item> items = ItemProvider.items;

        locations.add(
                new BattleLocation(2, "Cave", "\tGo to the cave but be careful! Zombies are everywhere!",
                        (Obstacle) GenericProvider.findObjectById(obstacles, 0),
                        ValueRange.of(1, 3),
                        (Item) GenericProvider.findObjectById(items, 0)));

        locations.add(
                new BattleLocation(3, "Forest", "Go to the forest but be careful! Vampires are everywhere!",
                        (Obstacle) GenericProvider.findObjectById(obstacles, 1),
                        ValueRange.of(1, 3),
                        (Item) GenericProvider.findObjectById(items, 1)));

        locations.add(
                new BattleLocation(4, "River", "\tGo to the river but be careful! Bears are everywhere!",
                        (Obstacle) GenericProvider.findObjectById(obstacles, 2),
                        ValueRange.of(1, 3),
                        (Item) GenericProvider.findObjectById(items, 2)));

        locations.add(
                new BattleLocation(5, "Pit", "\tGo to the mine but be careful! Snakes are everywhere!",
                        (Obstacle) GenericProvider.findObjectById(obstacles, 3),
                        ValueRange.of(1, 5),
                        (Item) GenericProvider.findObjectById(items, 3)));
    }

    public static Location selectLocation(Game game) {
        try {
            StringBuilder builder = new StringBuilder();

            builder.append("______________________________________________________________________________\n");
            builder.append("\tWhere do you want to go? \n");

            for (Location l : locations) {
                String line = String.format("\t%d-%s\t%s\n",
                        l.getId() + 1, l.getName(), l.getMessage());

                builder.append(line);
            }

            builder.append("----------------------------------------------------------------------");
            System.out.println(builder.toString());

            System.out.print("Response: ");
            int id = GenericProvider.scanner.nextInt();
            if (!GenericProvider.checkIntInRange(locations.get(0).getId() + 1, locations.get(locations.size() - 1).getId() + 1, id)) {
                return selectLocation(game);
            }

            Location latestLocation = (Location) GenericProvider.findObjectById(locations, id - 1);
            if (latestLocation instanceof BattleLocation && ((BattleLocation) latestLocation).getAward() == null) {
                System.out.printf("You already combatted on %s area! Choose an different area!\n",
                        latestLocation.getName());
                return selectLocation(game);
            }

            System.out.printf("Congratulations! You're in %s!\n", latestLocation.getName());
            System.out.println(latestLocation.getMessage());
            latestLocation.onLocation(game);

            return latestLocation;

        } catch (Exception e) {
            return selectLocation(game);
        }
    }
}
