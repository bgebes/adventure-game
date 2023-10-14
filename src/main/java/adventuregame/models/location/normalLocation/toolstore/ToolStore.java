package adventuregame.models.location.normalLocation.toolstore;

import adventuregame.bin.Game;
import adventuregame.models.character.Character;
import adventuregame.models.location.normalLocation.NormalLocation;
import adventuregame.providers.ArmorProvider;
import adventuregame.providers.GenericProvider;
import adventuregame.providers.LocationProvider;
import adventuregame.providers.WeaponProvider;

import java.time.temporal.ValueRange;

public class ToolStore extends NormalLocation {
    public ToolStore(int id, String name, String message) {
        super(id, name, message);
    }

    private int menu(Character character) {
        try {
            System.out.printf("Your money is $ %.2f\n", character.getMoney());
            System.out.print("Which product do you want to buy?\n1-Weapon\n2-Armor\nResponse: ");
            int response = GenericProvider.scanner.nextInt();

            System.out.println("Given Response: " + response);

            if (!ValueRange.of(1, 2).isValidIntValue(response)) {
                return menu(character);
            } else {
                return response;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return menu(character);
        }
    }

    @Override
    public boolean onLocation(Game game) {
        int product = menu(game.getCharacter());

        switch (product) {
            case 1 -> WeaponProvider.buyWeapon(game);
            case 2 -> ArmorProvider.buyArmor(game);
        }

        LocationProvider.selectLocation(game);
        return true;
    }
}
