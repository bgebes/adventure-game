package adventuregame.models.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private int id;
    private String name;
    private boolean isClaimed;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
        this.isClaimed = false;
    }
}
