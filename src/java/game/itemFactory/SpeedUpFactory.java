package game.itemFactory;

import game.entities.items.Item;
import game.entities.items.SpeedUp;

public class SpeedUpFactory extends AbstractItemFactory {
    @Override
    public Item makeItem(int xPos, int yPos) {
        return new SpeedUp(xPos, yPos);
    }
}
