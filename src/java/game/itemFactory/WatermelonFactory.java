package game.itemFactory;

import game.entities.items.Watermelon;
import game.entities.items.Item;

public class WatermelonFactory extends AbstractItemFactory {
    @Override
    public Item makeItem(int xPos, int yPos) {
        return new Watermelon(xPos, yPos);
    }
}
