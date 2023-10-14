package adventuregame.providers;


import adventuregame.bin.Game;
import adventuregame.exceptions.ArmorNotFoundException;
import adventuregame.models.armor.Armor;
import adventuregame.models.character.Character;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class ArmorProvider {
    public static ArrayList<Armor> armors;

    static {
        armors = new ArrayList<Armor>();
        armors.add(new Armor(0, "Weak Armor", 1, 15));
        armors.add(new Armor(1, "Medium Armor", 3, 25));
        armors.add(new Armor(2, "Strong Armor", 5, 40));

        /*
        Optional<Armor> optionalArmor = armors.stream().filter(armor1 -> Objects.equals("Weak Armor", armor1.getName())).findFirst();
        if (optionalArmor.isEmpty()) throw new ArmorNotFoundException();
        Armor armor = optionalArmor.get();

         */
    }

    public static boolean buyArmor(Game game) {
        try {
            Character character = game.getCharacter();

            StringBuilder builder = new StringBuilder();

            builder.append("------------------------------- ARMORS -------------------------------\n");
            for (Armor a : armors) {
                String line = String.format("id: %d\t Name: %s\t\t Defence: %.2f\t Price: %.2f\n",
                        a.getId() + 1, a.getName(), a.getDefence(), a.getPrice());

                builder.append(line);
            }

            builder.append("----------------------------------------------------------------------");
            System.out.println(builder.toString());

            System.out.print("Response: ");
            int id = GenericProvider.scanner.nextInt();
            if (!GenericProvider.checkIntInRange(armors.get(0).getId() + 1, armors.get(armors.size() - 1).getId() + 1, id)) {
                return buyArmor(game);
            }

            Armor bought = (Armor) GenericProvider.findObjectById(armors, id - 1);
            if (bought.getPrice() <= character.getMoney()) {
                character.setMoney(character.getMoney() - bought.getPrice());
                game.setArmor(bought);

                System.out.printf("Congratulations! You have %s!\n", bought.getName());
                return true;
            } else {
                System.out.printf("Sad! You didn't have %s because you didn't have enought money!\n", bought.getName());
                return false;
            }

        } catch (Exception e) {
            return buyArmor(game);
        }
    }
}
