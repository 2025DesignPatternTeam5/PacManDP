package game.itemFactory;

import game.entities.items.watermelon;
import game.entities.items.Item;

public class WatermelonFactory extends AbstractItemFactory {
    @Override
    public Item makeItem(int xPos, int yPos) {
        return new watermelon(xPos, yPos);
    }
}
