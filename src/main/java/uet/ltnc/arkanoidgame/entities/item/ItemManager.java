package uet.ltnc.arkanoidgame.entities.item;

import javafx.scene.canvas.GraphicsContext;
import uet.ltnc.arkanoidgame.entities.ball.Ball;
import uet.ltnc.arkanoidgame.entities.paddle.Paddle;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    private final List<Item> items;

    public ItemManager() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }

    public int update(Paddle paddle, Ball ball) {
        int extraLives = 0;

        for (int i = items.size() - 1; i >= 0; i--) {
            Item item = items.get(i);

            item.update();
            item.collect(paddle, ball);

            if (item.isCollected() && item.isExtraLife()) {
                extraLives++;
            }

            if (!item.isActive()) {
                items.remove(i);
            }
        }
        return extraLives;
    }

    public void render(GraphicsContext gc) {
        for (Item item : items) {
            item.render(gc);
        }
    }

    public void clear() {
        items.clear();
    }

    public int getItemCount() {
        return items.size();
    }
}