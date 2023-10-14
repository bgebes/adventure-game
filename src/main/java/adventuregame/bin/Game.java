package adventuregame.bin;

import adventuregame.models.armor.Armor;
import adventuregame.models.character.Character;
import adventuregame.models.item.Item;
import adventuregame.models.location.Location;
import adventuregame.models.weapon.Weapon;
import adventuregame.providers.CharacterProvider;
import adventuregame.providers.GenericProvider;
import adventuregame.providers.LocationProvider;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Game {
    private Character character;
    private Location location;
    private ArrayList<Item> items = new ArrayList<>();
    private Weapon weapon;
    private Armor armor;

    public void start() {
        System.out.println("Adventure Game");
        System.out.print("What is your name? Please enter the name: ");
        String name = GenericProvider.scanner.next();
        System.out.printf("Hi %s, Welcome to the island!\n", name);

        setCharacter(CharacterProvider.selectCharacter());
        setLocation(LocationProvider.selectLocation(this));
    }
}
