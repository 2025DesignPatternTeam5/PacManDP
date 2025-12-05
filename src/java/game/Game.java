package game;

import game.entities.*;
import game.entities.ghosts.*;
import game.entities.items.EffectItem;
import game.entities.items.Item;
import game.gameState.GameClearMode;
import game.gameState.GameOverMode;
import game.gameState.GameState;
import game.gameState.RunningMode;
import game.ghostFactory.*;
import game.ghostStates.EatenMode;
import game.ghostStates.FrightenedMode;
import game.ghostStates.GhostState;
import game.itemFactory.*;
import game.pacmanEffect.EffectCommand;
import game.utils.Awaiter;
import game.utils.CollisionDetector;
import game.utils.CsvReader;
import game.utils.KeyHandler;

import java.awt.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//게임 자체를 관리하는 클래스
public class Game implements Observer {
    //화면에 표시되는 모든 객체(엔티티)들을 목록으로 관리하기 위한 코드
    private List<Entity> objects = new ArrayList();
    private List<Ghost> ghosts = new ArrayList();
    private static List<Wall> walls = new ArrayList();

    private static Pacman pacman;
    private static Blinky blinky;
    private int pacGumCount = 0;

    private static boolean firstInput = false;
    private GhostState state;
    private int level = 2;

    protected GameState gameState;

    protected final GameState running;
    protected final GameState gameover;
    protected final GameState gameclear;

    private boolean isPause;

    public Game(){
        //게임 초기
        running=new RunningMode(this);
        gameover=new GameOverMode(this);
        gameclear=new GameClearMode(this);

        gameState=running;
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
        this.pacGumCount = 0;
        this.isPause = true;

        CollisionDetector collisionDetector = new CollisionDetector(this);
        AbstractGhostFactory abstractGhostFactory = null;
        List<AbstractItemFactory> itemfactories = new ArrayList<>();
        itemfactories.add(new CherryFactory());
        itemfactories.add(new AppleFactory());
        itemfactories.add(new WatermelonFactory());
        itemfactories.add(new BananaFactory()); // speed down
        itemfactories.add(new SpeedUpFactory()); // speed up
        itemfactories.add(new ShieldFactory()); // invulnerable
        itemfactories.add(new DemonFactory()); // direction reversal


        Random random = new Random();

        //레벨에는 '그리드(격자)'가 있으며, CSV 파일의 각 칸마다 포함된 문자에 따라 그리드의 해당 칸에 특정 엔티티를 표시한다.
        for(int xx = 0 ; xx < cellsPerRow ; xx++) {
            for(int yy = 0 ; yy < cellsPerColumn ; yy++) {
                String dataChar = data.get(yy).get(xx);
                if (dataChar.equals("x")) { //벽 생성
                    objects.add(new Wall(xx * cellSize, yy * cellSize));
                }else if (dataChar.equals("P")) { //팩맨 생성
                    pacman = new Pacman(xx * cellSize, yy * cellSize);
                    pacman.setCollisionDetector(collisionDetector);

                    //LifeUIPanel에게 Pacman 참조 연결
                    GameLauncher.setPacman(pacman);

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

                    Ghost ghost = abstractGhostFactory.makeGhost(xx * cellSize, yy * cellSize, level);
                    ghosts.add(ghost);
                    if (dataChar.equals("b")) {
                        blinky = (Blinky) ghost;
                    }
                }else if (dataChar.equals(".")) { //팩검(팩맨 먹이) 생성
                    // random하게 Item소환.
                    if (random.nextDouble() < 0.05) {
                        int randomIndex = random.nextInt(itemfactories.size());
                        Item item = itemfactories.get(randomIndex).makeItem(xx * cellSize, yy * cellSize);
                        objects.add(item);
                    }
                    else {
                        objects.add(new PacGum(xx * cellSize, yy * cellSize));
                        this.pacGumCount++;
                    }
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

        introEvent();
    }

    public static List<Wall> getWalls() {
        return walls;
    }

    public List<Entity> getEntities() {
        return objects;
    }

    //모든 객체의 상태 업데이트
    public void update() {
        if(isPause) //정지 상태이면 모든 update를 하지 못하도록 바로 리턴시킴
            return;

        for (Entity o: objects) {
            if (!o.isDestroyed()) o.update();
        }
        if (this.pacGumCount <= 0)
        {
            // pacMan이 apcGum을 모두 먹었다면,
            System.out.println("Level Clear!!");
            if(level <3){level++;}
            gameState.gameClear();
//            System.out.println("Game over !\nScore : " + GameLauncher.getUIPanel().getScore());
//            System.exit(0); //TODO
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
        pacman.initPacgumTimer();
        pg.destroy(); //La PacGum est détruite quand Pacman la mange
        this.pacGumCount--;
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
        }
        else if (!(gh.getState() instanceof EatenMode) && !pacman.getInvulnerable()) {
            //팩맨이 겁먹지 않았고 먹히지도 않은 유령과 접촉하면 게임 오버!
            System.out.println("Game over !\nScore : " + GameLauncher.getUIPanel().getScore());
            pacman.die();//팩맨 사망
        }
    }

    @Override
    public void updateEffectAdded(EffectCommand effect) {
    }

    @Override
    public void updateEffectRemoved(EffectCommand effect) {
    }

    @Override
    public void updateEffectTick(EffectCommand effect) {
    }

    public void updateItemEaten(Item item) {
        SoundManager.getInstance().play(SoundManager.Sound.PAC_FRUIT);

        if (item instanceof EffectItem) {
            pacman.addEffect(((EffectItem) item).getEffectCommand());
        }
        item.destroy();
    }

    @Override
    public void updatePacmanDead() {
        isPause = true;
        float length = SoundManager.getInstance().getClipLength(SoundManager.Sound.FAIL);
        Awaiter.delay(length, ()-> {
            resetMovingEntities();
            //TODO: pacman버프 적용중이던거 다 초기화시키는 코드 여기에 넣기
            for(Ghost g : ghosts) {
                g.setStayMode();
                g.getState().lvlGhost(level);
            }
        });

        if(pacman.isLifeZero()) {
            //완전히 게임오버
            gameState.die();
        }
    }

    private void introEvent() {
        float length = SoundManager.getInstance().getClipLength(SoundManager.Sound.START);
        SoundManager.getInstance().play(SoundManager.Sound.START);
        Awaiter.delay(length - 1, ()-> isPause = false);
    }

    private void resetMovingEntities() {
        pacman.resetPos();
        for(Ghost g : ghosts) {
            g.resetPos();
        }
        Awaiter.delay(2, ()->isPause = false);
    }

    public static void setFirstInput(boolean b) {
        firstInput = b;
    }

    public static boolean getFirstInput() {
        return firstInput;
    }

    public void switchGameOver() {
        gameState=gameover;
    }
    public void switchGameClear(){
        level++;
        gameState=gameclear;
    }
    public void switchRunning(){
        gameState=running;
    }
    public GameState getGameState() {return gameState;}
}
