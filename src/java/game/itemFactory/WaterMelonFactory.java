package game.itemFactory;

import game.entities.items.WaterMelon;
import game.entities.items.Item;

public class WaterMelonFactory extends AbstractItemFactory {
    @Override
    public Item makeItem(int xPos, int yPos) {
        return new WaterMelon(xPos, yPos);
    }
}
