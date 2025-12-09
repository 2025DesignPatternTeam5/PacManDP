package game;

import game.entities.PacGum;
import game.entities.Pacman;
import game.entities.SuperPacGum;
import game.entities.ghosts.Ghost;
import game.entities.items.Item;
import game.ghostStates.FrightenedMode;
import game.pacmanEffect.EffectCommand;

import javax.swing.*;
import java.awt.*;

public class UIPanel extends JPanel implements Observer {
    public static int width;
    public static int height;

    private int score = 0;
    private JLabel scoreLabel;

    private LifeUIPanel lifePanel;
    private BuffPanel buffPanel;

    public UIPanel(int width, int height) {
        this.width = width;
        this.height = height;

        // [레이아웃] 세로로 배치 (Y_AXIS)
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);

        //  점수 라벨 (맨 위)
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(scoreLabel.getFont().deriveFont(20.0F));
        scoreLabel.setForeground(Color.white);

        // 정렬을 위해 Panel에 담음
        JPanel scoreContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        scoreContainer.setBackground(Color.BLACK);
        scoreContainer.add(scoreLabel);
        this.add(scoreContainer);

        // 버프 패널
        buffPanel = new BuffPanel();

        // 정렬을 위해 Panel에 담음
        JPanel buffContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buffContainer.setBackground(Color.BLACK);
        buffContainer.add(buffPanel);
        this.add(buffContainer);

        // 라이프 패널
        lifePanel = new LifeUIPanel(3000);
    }

    public LifeUIPanel getLifePanel() {
        return lifePanel;
    }

    public void updateScore(int incrScore) {
        this.score += incrScore;
        this.lifePanel.increaseScore(incrScore);
        this.scoreLabel.setText("Score: " + score);
    }

    public int getScore() {
        return score;
    }

    public void scoreReset(){
        this.score = 0;
    }

    // --- Observer 구현 ---

    @Override
    public void updatePacGumEaten(PacGum pg) {
        updateScore(10);
    }

    @Override
    public void updateSuperPacGumEaten(SuperPacGum spg) {
        updateScore(100);
    }

    @Override
    public void updateGhostCollision(Ghost gh) {
        if (gh.getState() instanceof FrightenedMode) {
            updateScore(500);
        }
    }

    @Override
    public void updateItemEaten(Item item) {
        updateScore(item.getPoint());
    }

    @Override
    public void updatePacmanDead() {
        lifePanel.lifeDecrease();
    }

    @Override
    public void updateEffectAdded(EffectCommand effect) {
        buffPanel.addEffect(effect);
    }

    @Override
    public void updateEffectRemoved(EffectCommand effect) {
        buffPanel.removeEffect(effect);
    }

    @Override
    public void updateEffectTick(EffectCommand effect) {
        buffPanel.repaint();
    }

    public void setPacman(Pacman pacman) {
        lifePanel.setPacman(pacman);
        lifePanel.updateLifeUI();
    }
}