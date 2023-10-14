package adventuregame.providers;

import adventuregame.bin.Game;
import adventuregame.models.character.Character;
import adventuregame.models.weapon.Weapon;

import java.time.temporal.ValueRange;
import java.util.ArrayList;

public class WeaponProvider {
    public static ArrayList<Weapon> weapons;

    static {
        weapons = new ArrayList<Weapon>();
        weapons.add(new Weapon(0, "Pistol", 2, 25));
        weapons.add(new Weapon(1, "Sword", 3, 35));
        weapons.add(new Weapon(2, "Rifle", 7, 45));
    }

    public static boolean buyWeapon(Game game) {
        try {
            Character character = game.getCharacter();

            StringBuilder builder = new StringBuilder();

            builder.append("------------------------------- WEAPONS -------------------------------\n");
            for (Weapon w : weapons) {
                String line = String.format("id: %d\t Name: %s\t Damage: %.2f\t Price: %.2f\n",
                        w.getId() + 1, w.getName(), w.getDamage(), w.getPrice());

                builder.append(line);
            }

            builder.append("----------------------------------------------------------------------");
            System.out.println(builder.toString());

            System.out.print("Response: ");
            int id = GenericProvider.scanner.nextInt();
            if (!GenericProvider.checkIntInRange(weapons.get(0).getId() + 1, weapons.get(weapons.size() - 1).getId() + 1, id)) {
                return buyWeapon(game);
            }

            Weapon bought = (Weapon) GenericProvider.findObjectById(weapons, id - 1);
            if (bought.getPrice() <= character.getMoney()) {
                character.setMoney(character.getMoney() - bought.getPrice());
                game.setWeapon(bought);

                System.out.printf("Congratulations! You have %s\n", bought.getName());
                return true;
            } else {
                System.out.printf("Sad! You didn't have %s because you didn't have enought money!\n", bought.getName());
                return false;
            }

        } catch (Exception e) {
            return buyWeapon(game);
        }
    }
}
