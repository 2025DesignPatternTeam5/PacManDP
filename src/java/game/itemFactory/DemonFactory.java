package game.itemFactory;

import game.entities.items.Demon;
import game.entities.items.Item;

public class DemonFactory extends AbstractItemFactory{
    @Override
    public Item makeItem(int xPos, int yPos) {
        return new Demon(xPos, yPos);
    }
}
