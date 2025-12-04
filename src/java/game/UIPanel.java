package game;

import game.entities.PacGum;
import game.entities.SuperPacGum;
import game.entities.ghosts.Ghost;
import game.entities.items.Item;
import game.ghostStates.FrightenedMode;
import game.pacmanEffect.EffectCommand;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//UI 패널
public class UIPanel extends JPanel implements Observer {
    public static int width;
    public static int height;

    private int score = 0;
    private JLabel scoreLabel;
    private ArrayList<EffectCommand> displayedEffects = new ArrayList<>();

    public UIPanel(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(scoreLabel.getFont().deriveFont(20.0F));
        scoreLabel.setForeground(Color.white);
        this.add(scoreLabel, BorderLayout.WEST);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 배경(검은색) 지우기

        // 활성화된 효과들을 순회하며 이미지의 지속시간 바 그리기
        drawEffects(g);
    }

    private void drawEffects(Graphics g) {
        int iconSize = 32; // 이미지 크기
        int gap = 15;      // 이미지 사이 간격
        int startX = 20;   // 그리기 시작 X 좌표
        int startY = 50;   // 그리기 시작 Y 좌표 (점수 아래쪽)

        for (int i = 0; i < displayedEffects.size(); i++) {
            EffectCommand effect = displayedEffects.get(i);

            // EffectCommand에 이미지가 있는 경우에만 그림
            if (effect.getEffectImage() != null) {
                int currentX = startX + (i * (iconSize + gap));

                // 1. 이미지 그리기
                g.drawImage(effect.getEffectImage(), currentX, startY, iconSize, iconSize, null);

                // 2. 지속시간 게이지 바 (Progress Bar) 그리기
                // 배경 바 (회색)
                g.setColor(Color.DARK_GRAY);
                g.fillRect(currentX, startY + iconSize + 5, iconSize, 5);

                // 진행 바 (초록색) - 남은 시간에 비례하여 길이 조절
                g.setColor(Color.GREEN);

                // EffectCommand의 getDurationRatio() (0.0 ~ 1.0)를 사용하여 너비 계산
                // Pacman 내부에서 timer가 계속 업데이트되므로, 이 값은 실시간으로 줄어듦
                int barWidth = (int) (iconSize * effect.getDurationRatio());
                g.fillRect(currentX, startY + iconSize + 5, barWidth, 5);
            }
        }
    }

    public void updateScore(int incrScore) {
        this.score += incrScore;
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
    public void updateItemEaten(Item item) {
        updateScore(item.getPoint());
        // 추가적으로 아이템을 먹었으면 해당 이미지를 panel 하단에 출력하게 하면 좋을 듯.
    }

    @Override
    public void updateEffectAdded(EffectCommand effect) {
        this.displayedEffects.add(effect);
        repaint();
    }

    // [Observer 구현] 효과가 끝났다는 알림을 받음
    @Override
    public void updateEffectRemoved(EffectCommand effect) {
        this.displayedEffects.remove(effect);
        repaint();
    }

    @Override
    public void updateEffectTick(EffectCommand effect) {
        this.repaint();
    }
}
