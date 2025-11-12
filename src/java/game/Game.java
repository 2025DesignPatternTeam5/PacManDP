package game;

import game.entities.*;
import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;
import game.ghostFactory.*;
import game.ghostStates.EatenMode;
import game.ghostStates.FrightenedMode;
import game.utils.CollisionDetector;
import game.utils.CsvReader;
import game.utils.KeyHandler;

import java.awt.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

//게임 자체를 관리하는 클래스
public class Game implements Observer {
    //화면에 표시되는 모든 객체(엔티티)들을 목록으로 관리하기 위한 코드
    private List<Entity> objects = new ArrayList();
    private List<Ghost> ghosts = new ArrayList();
    private static List<Wall> walls = new ArrayList();

    private static Pacman pacman;
    private static Blinky blinky;

    private static boolean firstInput = false;

    public Game(){
        //게임 초기

        //레벨의 CSV파일 불러오기
        List<List<String>> data = null;
        try {
            data = new CsvReader().parseCsv(getClass().getClassLoader().getResource("level/level.csv").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        int cellsPerRow = data.get(0).size();
        int cellsPerColumn = data.size();
        int cellSize = 8;

        CollisionDetector collisionDetector = new CollisionDetector(this);
        AbstractGhostFactory abstractGhostFactory = null;

        //레벨에는 '그리드(격자)'가 있으며, CSV 파일의 각 칸마다 포함된 문자에 따라 그리드의 해당 칸에 특정 엔티티를 표시한다.
        for(int xx = 0 ; xx < cellsPerRow ; xx++) {
            for(int yy = 0 ; yy < cellsPerColumn ; yy++) {
                String dataChar = data.get(yy).get(xx);
                if (dataChar.equals("x")) { //벽 생성
                    objects.add(new Wall(xx * cellSize, yy * cellSize));
                }else if (dataChar.equals("P")) { //팩맨 생성
                    pacman = new Pacman(xx * cellSize, yy * cellSize);
                    pacman.setCollisionDetector(collisionDetector);

                    //팩맨의 여러 옵저버(관찰자) 등록
                    pacman.registerObserver(GameLauncher.getUIPanel());
                    pacman.registerObserver(this);
                }else if (dataChar.equals("b") || dataChar.equals("p") || dataChar.equals("i") || dataChar.equals("c")) { //여러 팩토리를 사용하여 유령들을 생성
                    switch (dataChar) {
                        case "b":
                            abstractGhostFactory = new BlinkyFactory();
                            break;
                        case "p":
                            abstractGhostFactory = new PinkyFactory();
                            break;
                        case "i":
                            abstractGhostFactory = new InkyFactory();
                            break;
                        case "c":
                            abstractGhostFactory = new ClydeFactory();
                            break;
                    }

                    Ghost ghost = abstractGhostFactory.makeGhost(xx * cellSize, yy * cellSize);
                    ghosts.add(ghost);
                    if (dataChar.equals("b")) {
                        blinky = (Blinky) ghost;
                    }
                }else if (dataChar.equals(".")) { //팩검(팩맨 먹이) 생성
                    objects.add(new PacGum(xx * cellSize, yy * cellSize));
                }else if (dataChar.equals("o")) { //슈퍼팩검 생성
                    objects.add(new SuperPacGum(xx * cellSize, yy * cellSize));
                }else if (dataChar.equals("-")) { //팩맨 게임에서 유령들이 시작하거나 되돌아오는 ‘유령의 집’ 영역의 벽을 생성
                    objects.add(new GhostHouse(xx * cellSize, yy * cellSize));
                }
            }
        }
        objects.add(pacman);
        objects.addAll(ghosts);

        for (Entity o : objects) {
            if (o instanceof Wall) {
                walls.add((Wall) o);
            }
        }
    }

    public static List<Wall> getWalls() {
        return walls;
    }

    public List<Entity> getEntities() {
        return objects;
    }

    //모든 객체의 상태 업데이트
    public void update() {
        for (Entity o: objects) {
            if (!o.isDestroyed()) o.update();
        }
    }

    //사용자 입력 관리
    public void input(KeyHandler k) {
        pacman.input(k);
    }

    //모든 객체 렌더링
    public void render(Graphics2D g) {
        for (Entity o: objects) {
            if (!o.isDestroyed()) o.render(g);
        }
    }

    public static Pacman getPacman() {
        return pacman;
    }
    public static Blinky getBlinky() {
        return blinky;
    }

    //팩맨이 PacGum, SuperPacGum 또는 유령과 접촉(충돌?)할 때 게임이 알림을 받는다
    @Override
    public void updatePacGumEaten(PacGum pg) {
        pg.destroy(); //La PacGum est détruite quand Pacman la mange
    }

    @Override
    public void updateSuperPacGumEaten(SuperPacGum spg) {
        spg.destroy(); //팩맨이 SuperPacGum을 먹으면 해당 SuperPacGum이 제거된다
        for (Ghost gh : ghosts) {
            gh.getState().superPacGumEaten(); //슈퍼 팩검이 먹혔을 때 특별한 전환이 존재하면, 유령들의 상태가 변경된다
        }
    }

    @Override
    public void updateGhostCollision(Ghost gh) {
        if (gh.getState() instanceof FrightenedMode) {
            gh.getState().eaten(); //유령이 먹혔을 때 특별한 전환이 존재하면, 그 상태가 그에 맞게 변경된다
        }else if (!(gh.getState() instanceof EatenMode)) {
            //팩맨이 겁먹지 않았고 먹히지도 않은 유령과 접촉하면 게임 오버!
            System.out.println("Game over !\nScore : " + GameLauncher.getUIPanel().getScore());
            System.exit(0); //TODO
        }
    }

    public static void setFirstInput(boolean b) {
        firstInput = b;
    }

    public static boolean getFirstInput() {
        return firstInput;
    }
}
