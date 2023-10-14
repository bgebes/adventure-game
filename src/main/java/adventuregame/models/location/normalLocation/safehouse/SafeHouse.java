package adventuregame.models.location.normalLocation.safehouse;

import adventuregame.bin.Game;
import adventuregame.models.character.Character;
import adventuregame.models.item.Item;
import adventuregame.models.location.normalLocation.NormalLocation;
import adventuregame.providers.CharacterProvider;
import adventuregame.providers.GenericProvider;
import adventuregame.providers.ItemProvider;
import adventuregame.providers.LocationProvider;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;

public class SafeHouse extends NormalLocation {
    public SafeHouse(int id, String name, String message) {
        super(id, name, message);
    }

    private void checkGameFinished(Game game) {
        ArrayList<Item> items = game.getItems();

        if (items.size() == ItemProvider.items.size()) {
            String character = game.getCharacter().getName();
            String armor = game.getArmor() != null ? game.getArmor().getName() : "None";
            String weapon = game.getWeapon() != null ? game.getWeapon().getName() : "None";

            System.out.println("----------------------------------------------------------------------");
            System.out.printf("\nYou finish the game!\nCharacter: %s\nArmor: %s\nWeapon: %s\n", character, armor, weapon);
            System.exit(0);
        }
    }

    @Override
    public boolean onLocation(Game game) {
        // Checking for Claimed All Items
        checkGameFinished(game);

        Character character = game.getCharacter();

        double currentHealth = character.getHealth();
        double initialHealth = character.getInitialHealth();

        System.out.printf("Your health has been restored from %.2f to %.2f!\n", currentHealth, initialHealth);
        character.setHealth(initialHealth);

        game.setLocation(LocationProvider.selectLocation(game));
        return true;
    }
}
