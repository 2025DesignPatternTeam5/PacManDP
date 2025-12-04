package game;

import game.pacmanEffect.EffectCommand;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BuffPanel extends JPanel {
    private ArrayList<EffectCommand> displayedEffects = new ArrayList<>();

    public BuffPanel() {
        this.setBackground(Color.BLACK);
        // 아이콘들이 왼쪽부터 차례대로 쌓이도록 FlowLayout 사용
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));

        this.setPreferredSize(new Dimension(200, 50));
    }

    public void addEffect(EffectCommand effect) {
        this.displayedEffects.add(effect);
        repaint();
    }

    public void removeEffect(EffectCommand effect) {
        this.displayedEffects.remove(effect);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int iconSize = 32;
        int gap = 15;

        int startX = 20;
        int startY = 5;

        for (int i = 0; i < displayedEffects.size(); i++) {
            EffectCommand effect = displayedEffects.get(i);

            if (effect.getEffectImage() != null) {
                int currentX = startX + (i * (iconSize + gap));
                g.drawImage(effect.getEffectImage(), currentX, startY, iconSize, iconSize, null);
                int barY = startY + iconSize + 3; // 아이콘 바로 아래

                g.setColor(Color.DARK_GRAY);
                g.fillRect(currentX, barY, iconSize, 5);

                g.setColor(Color.GREEN);

                // 0.0 ~ 1.0 비율 적용
                float ratio = effect.getDurationRatio();
                if (ratio < 0) ratio = 0;

                int barWidth = (int) (iconSize * ratio);
                g.fillRect(currentX, barY, barWidth, 5);
            }
        }
    }
}