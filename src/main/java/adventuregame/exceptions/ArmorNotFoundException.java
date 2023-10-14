package adventuregame.exceptions;


public class ArmorNotFoundException extends RuntimeException {
    public ArmorNotFoundException() {
        super("Armor not found!");
    }
}
