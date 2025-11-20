package game;

import game.entities.PacGum;
import game.entities.SuperPacGum;
import game.entities.ghosts.Ghost;
import game.ghostStates.FrightenedMode;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

//UI 패널
public class UIPanel extends JPanel implements Observer {
    public static int width;
    public static int height;

    private int score = 0;
    private JLabel scoreLabel;

    private LifeUIPanel lifePanel;

    public UIPanel(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(scoreLabel.getFont().deriveFont(20.0F));
        scoreLabel.setForeground(Color.white);
        this.add(scoreLabel, BorderLayout.WEST);
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

    //팩맨이 PacGum, SuperPacGum 또는 유령과 접촉하면 인터페이스에 알림이 가고, 그에 따라 표시된 점수를 갱신한다
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
        //팩맨이 유령과 접촉할 경우, 유령이 '겁먹은(frightened)' 모드일 때만 점수를 갱신한다
        if (gh.getState() instanceof FrightenedMode) {
            updateScore(500);
        }
    }

    @Override
    public void updatePacmanDead() {
        lifePanel.lifeDecrease();
    }
}
