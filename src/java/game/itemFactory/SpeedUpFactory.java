package game.itemFactory;

import game.entities.items.Item;
import game.entities.items.SpeedUp;
import game.entities.items.watermelon;

public class SpeedUpFactory extends AbstractItemFactory {
    @Override
    public Item makeItem(int xPos, int yPos) {
        return new SpeedUp(xPos, yPos);
    }
}
