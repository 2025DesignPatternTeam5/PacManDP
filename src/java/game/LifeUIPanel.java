package game;

import game.entities.Pacman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LifeUIPanel extends JPanel {
    private int tempScore; //획득한 점수 버퍼
    private final int unitScore; //라이프가 오르는 기준 점수값
    private Pacman pacman;
    private final Image pacmanImage;
    private BufferedImage buffer; // 캐싱된 이미지
    private boolean dirty = true; // 다시 그릴 필요가 있는가?

    public LifeUIPanel(int unitScore) {
        this.pacmanImage = loadImage("img/life.png");
        this.tempScore = 0;
        this.unitScore = unitScore;
        this.setBackground(Color.black);
        setPreferredSize(new Dimension(448, 40));
        createBuffer();
        redrawBuffer();
    }

    public Image loadImage(String path) {
        try {
            return ImageIO.read(getClass().getClassLoader().getResource(path));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //UIPanel에서 점수 획득한 만큼 tempScore에 점수 쌓고, life로 변환 가능한지 검토
    public void increaseScore(int addScore) {
        tempScore += addScore;
        lifeIncrease();
    }

    //쌓인 점수로 라이프 증가 가능한만큼 증가
    private void lifeIncrease() {
        while(tempScore >= unitScore) {
            tempScore -= unitScore;
            pacman.increaseLifeCnt();
            setLifeUI();
        }
    }

    //라이프 감소
    public void lifeDecrease() {
        pacman.decreaseLifeCnt();
        setLifeUI();
    }

     private void createBuffer() {
        buffer = new BufferedImage(getPreferredSize().width, getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
    }

    //라이프 UI 업데이트
    public void setLifeUI() {
       dirty = true;
       repaint();
    }

    //실제 렌더링을 버퍼에 수행하는 함수
    private void redrawBuffer() {
        Graphics2D g = buffer.createGraphics();
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        g.setComposite(AlphaComposite.SrcOver);

        int x = 0;
        int lifeCnt = pacman == null ? 5 : pacman.getLifeCnt();
        for (int i = 0; i < lifeCnt; i++) {
            g.drawImage(pacmanImage, x, 0, null);
            x += pacmanImage.getWidth(null) + 5;
        }
        g.dispose();
        dirty = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // dirty일 때만 새로 그린다
        if (dirty) {
            redrawBuffer();
        }

        g.drawImage(buffer, 0, 5, null);
    }

    public void setPacman(Pacman pacman) { this.pacman = pacman; }
}
