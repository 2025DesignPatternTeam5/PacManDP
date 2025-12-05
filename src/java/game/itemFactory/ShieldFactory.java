package game.itemFactory;

import game.entities.items.Item;
import game.entities.items.Shield;

public class ShieldFactory extends AbstractItemFactory {
    @Override
    public Item makeItem(int xPos, int yPos) {
        return new Shield(xPos, yPos);
    }
}
