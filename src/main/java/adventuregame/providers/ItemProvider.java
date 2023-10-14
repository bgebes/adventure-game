package adventuregame.providers;

import adventuregame.bin.Game;
import adventuregame.models.armor.Armor;
import adventuregame.models.character.Character;
import adventuregame.models.item.Item;
import adventuregame.models.weapon.Weapon;

import java.time.temporal.ValueRange;
import java.util.*;

public class ItemProvider {
    public static ArrayList<Item> items;

    static {
        items = new ArrayList<Item>();
        items.add(new Item(0, "Food"));
        items.add(new Item(1, "Firewood"));
        items.add(new Item(2, "Water"));
        items.add(new Item(3, "Weapon/Armor Drop"));
    }

    public static void claimItem(ArrayList<Item> itemArrayList, Item item, Game game) {
        itemArrayList.add(item);
        item.setClaimed(true);

        if (Objects.equals(item, items.get(3))) {
            dropItem(game);
        } else {
            System.out.printf("You claimed %s!\n", item.getName());
        }
    }

    public static void dropItem(Game game) {
        int chance = GenericProvider.getRandomInt(1, 100);

        ArrayList<Weapon> weapons = WeaponProvider.weapons;
        ArrayList<Armor> armors = ArmorProvider.armors;

        HashMap<ValueRange, Object> dropPossibilities = new HashMap<>();
        dropPossibilities.put(ValueRange.of(1, 3), GenericProvider.findObjectById(weapons, 2));
        dropPossibilities.put(ValueRange.of(4, 8), GenericProvider.findObjectById(weapons, 1));
        dropPossibilities.put(ValueRange.of(9, 15), GenericProvider.findObjectById(weapons, 0));
        dropPossibilities.put(ValueRange.of(16, 18), GenericProvider.findObjectById(armors, 2));
        dropPossibilities.put(ValueRange.of(19, 23), GenericProvider.findObjectById(armors, 1));
        dropPossibilities.put(ValueRange.of(24, 30), GenericProvider.findObjectById(armors, 0));
        dropPossibilities.put(ValueRange.of(31, 35), 10.0);
        dropPossibilities.put(ValueRange.of(36, 42), 5.0);
        dropPossibilities.put(ValueRange.of(43, 55), 1.0);
        dropPossibilities.put(ValueRange.of(56, 100), null);

        Optional<ValueRange> optional = dropPossibilities.keySet().stream()
                .filter(v -> v.isValidIntValue(chance)).findFirst();
        if (optional.isEmpty()) throw new RuntimeException("The random number couldn't enter the any value range");

        ValueRange valueRange = optional.get();
        Object drop = dropPossibilities.get(valueRange);

        if (drop instanceof Weapon weapon) {
            game.setWeapon(weapon);
            System.out.printf("You claimed %s!\n", weapon.getName());
        } else if (drop instanceof Armor armor) {
            game.setArmor(armor);
            System.out.printf("You claimed %s!\n", armor.getName());
        } else if (drop instanceof Double money) {
            Character character = game.getCharacter();
            character.setMoney(character.getMoney() + money);
            System.out.printf("You claimed %.2f money!\n", money);
        } else if (drop == null) {
            System.out.println("You claimed nothing!");
        }

    }
}
