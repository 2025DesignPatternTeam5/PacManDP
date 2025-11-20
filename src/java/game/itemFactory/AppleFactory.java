package game.itemFactory;

import game.entities.items.Apple;
import game.entities.items.Item;

public class AppleFactory extends AbstractItemFactory {
    @Override
    public Item makeItem(int xPos, int yPos) {
        return new Apple(xPos, yPos);
    }
}
