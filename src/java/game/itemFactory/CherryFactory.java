package game.itemFactory;

import game.entities.items.Item;
import game.entities.items.Cherry;

public class CherryFactory extends AbstractItemFactory {
    @Override
    public Item makeItem(int xPos, int yPos) {
        return new Cherry(xPos, yPos);
    }
}
