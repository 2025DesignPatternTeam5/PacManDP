package game.utils;

import game.GameplayPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;

//입력 처리를 관리하는 클래스
public class KeyHandler implements KeyListener {

    public static List<Key> keys = new ArrayList<>();

    public class Key {
        public boolean isPressed;

        public Key() {
            keys.add(this);
        }

        public void toggle(boolean pressed) {
            if (pressed != isPressed) {
                isPressed = pressed;
            }
        }
    }

    public Key k_up = new Key();
    public Key k_down = new Key();
    public Key k_left = new Key();
    public Key k_right = new Key();
    public Key k_y = new Key();
    public Key k_n = new Key();

    public KeyHandler(GameplayPanel game) {
        game.addKeyListener(this);
    }

    public void toggle(KeyEvent e, boolean pressed) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_Q) {
            k_left.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            k_right.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_Z) {
            k_up.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            k_down.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_Y) {
            k_y.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_N) {
            k_n.toggle(pressed);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e, false);
    }
}
