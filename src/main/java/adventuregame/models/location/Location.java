package adventuregame.models.location;

import adventuregame.bin.Game;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Location {
    public int id;
    public String name;
    public String message;

    public Location(int id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    public abstract boolean onLocation(Game game);
}
