package game.itemFactory;

import game.entities.items.Apple;
import game.entities.items.Banana;
import game.entities.items.Item;

public class BananaFactory extends AbstractItemFactory {
    @Override
    public Item makeItem(int xPos, int yPos) {
        return new Banana(xPos, yPos);
    }
}
