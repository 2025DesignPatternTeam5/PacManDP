package game.itemFactory;

import game.entities.items.Item;
import game.entities.items.Phantom;

public class PhantomFactory extends AbstractItemFactory {
    @Override
    public Item makeItem(int xPos, int yPos) {
        return new Phantom(xPos, yPos);
    }
}
