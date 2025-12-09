package game;

import game.entities.Pacman;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

//애플리케이션의 시작 지점
public class GameLauncher {
    private static UIPanel uiPanel;

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("Pacman");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gameWindow = new JPanel(new BorderLayout());

        //게임 영역 생성
        try {
            gameWindow.add(new GameplayPanel(448,496), BorderLayout.CENTER); //448 496
        } catch (IOException e) {
            e.printStackTrace();
        }

        //점수 표시 UI 생성
        uiPanel = new UIPanel(448,90);//256 496
        gameWindow.add(uiPanel, BorderLayout.NORTH);
        gameWindow.add(uiPanel.getLifePanel(), BorderLayout.SOUTH);

        window.setContentPane(gameWindow);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static UIPanel getUIPanel() {
        return uiPanel;
    }
    public static void setPacman(Pacman pacman) { uiPanel.setPacman(pacman); }
}
