package game.entities;

import game.*;
import game.entities.ghosts.Ghost;
import game.entities.items.Item;
import game.utils.CollisionDetector;
import game.utils.KeyHandler;
import game.utils.WallCollisionDetector;
import game.pacmanStates.*;

import java.util.ArrayList;
import java.util.List;

//팩맨 클래스
public class Pacman extends MovingEntity implements Sujet {
    private final float PACGUM_INIT_TIMER = 15f;//팩검 타이머 초기화 상수

    private PacmanState state;

    protected final PacmanState normalState;
    protected final PacmanState speedUpState;
    protected final PacmanState phantomState;

    private CollisionDetector collisionDetector;
    private List<Observer> observerCollection;
    private float pacgumSoundTimer;
    private boolean isDead;

    public Pacman(int xPos, int yPos) {
        super(32, xPos, yPos, 2, "pacman.png", 4, 0.3f);
        observerCollection = new ArrayList<>();
        pacgumSoundTimer = 0f;
        isDead = false;
        this.normalState = new NormalState(this);
        this.speedUpState = new SpeedUpState(this);
        this.phantomState = new PhantomState(this);
        this.state = this.normalState;
    }

    //이동 처리
    public void input(KeyHandler k) {
        int new_xSpd = 0;
        int new_ySpd = 0;

        if (!onTheGrid()) return; //팩맨은 게임 구역의 한 칸(cell)에 있어야 한다
        if (!onGameplayWindow()) return; //팩맨은 게임 구역 안에 있어야 한다

        //누른 키에 따라 팩맨의 방향이 그에 맞게 바뀐다
        if (k.k_left.isPressed && xSpd >= 0 && !WallCollisionDetector.checkWallCollision(this, -spd, 0)) {
            new_xSpd = -spd;
        }
        if (k.k_right.isPressed && xSpd <= 0 && !WallCollisionDetector.checkWallCollision(this, spd, 0)) {
            new_xSpd = spd;
        }
        if (k.k_up.isPressed && ySpd >= 0 && !WallCollisionDetector.checkWallCollision(this, 0, -spd)) {
            new_ySpd = -spd;
        }
        if (k.k_down.isPressed && ySpd <= 0 && !WallCollisionDetector.checkWallCollision(this, 0, spd)) {
            new_ySpd = spd;
        }

        if (new_xSpd == 0 && new_ySpd == 0) return;

        if (!Game.getFirstInput()) Game.setFirstInput(true);

        if (Math.abs(new_xSpd) != Math.abs(new_ySpd)) {
            xSpd = new_xSpd;
            ySpd = new_ySpd;
        } else {
            if (xSpd != 0) {
                xSpd = 0;
                ySpd = new_ySpd;
            }else{
                xSpd = new_xSpd;
                ySpd = 0;
            }
        }
    }

    @Override
    public void update() {
        if(isDead) //죽었다면 어떤 상호작용도 일어나지 않도록 바로 리턴
            return;

        state.update();
        //팩맨이 PacGum, SuperPacGum 또는 유령과 접촉했는지 매번 확인하고, 그에 따라 옵저버들에게 알림을 보낸다
        PacGum pg = (PacGum) collisionDetector.checkCollision(this, PacGum.class);
        if (pg != null) {
            notifyObserverPacGumEaten(pg);
        }

        SuperPacGum spg = (SuperPacGum) collisionDetector.checkCollision(this, SuperPacGum.class);
        if (spg != null) {
            notifyObserverSuperPacGumEaten(spg);
        }

        Ghost gh = (Ghost) collisionDetector.checkCollision(this, Ghost.class);
        if (gh != null) {
            if (!state.isInvincible()) {
                notifyObserverGhostCollision(gh);
            } else {
                System.out.println("Phantom state.(무적)");
            }
        }

        Item item = (Item) collisionDetector.checkCollision(this, Item.class);
        if (item != null) {
            notifyObserverItemEaten(item);
        }

        // 팩맨의 다음 잠재적 위치에 벽이 없으면, 팩맨의 위치를 갱신한다
        int currentSpeed = state.getSpeed();
//        System.out.println(xPos + ", " + yPos);
        if ((xPos % 4 != 0) || (yPos % 4 != 0)) {
            currentSpeed = 2;
        }
        if (xSpd != 0) {
            if (xSpd > 0) xSpd = currentSpeed;
            else xSpd = -currentSpeed;
        }
        if (ySpd != 0) {
            if (ySpd > 0) ySpd = currentSpeed;
            else ySpd = -currentSpeed;
        }


        if (!WallCollisionDetector.checkWallCollision(this, xSpd, ySpd)) {
            updatePosition();
        }

        //팩검 타이머가 0이 되면 팩검 섭취 사운드를 정지시킴.
        if (pacgumSoundTimer > 0f) {
            pacgumSoundTimer--;
            if (pacgumSoundTimer <= 0f) {
                SoundManager.getInstance().stop(SoundManager.Sound.PAC_DOT);
            }
        }
    }

    public void setCollisionDetector(CollisionDetector collisionDetector) {
        this.collisionDetector = collisionDetector;
    }

    @Override
    public void registerObserver(Observer observer) {
        observerCollection.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObserverPacGumEaten(PacGum pg) {
        observerCollection.forEach(obs -> obs.updatePacGumEaten(pg));
    }

    @Override
    public void notifyObserverSuperPacGumEaten(SuperPacGum spg) {
        observerCollection.forEach(obs -> obs.updateSuperPacGumEaten(spg));
    }

    @Override
    public void notifyObserverGhostCollision(Ghost gh) {
        observerCollection.forEach(obs -> obs.updateGhostCollision(gh));
    }

    @Override
    public void notifyObserverItemEaten(Item item) {
        observerCollection.forEach(obs-> obs.updateItemEaten(item));
    }

    public void switchNormalState() {
        this.state = normalState;
        this.spd = state.getSpeed();
    }

    public void switchSpeedUpState() {
        speedUpState.resetTimer();
        this.state = speedUpState;
        this.spd = state.getSpeed();
    }

    public void switchPhantomState() {
        phantomState.resetTimer();
        this.state = phantomState;
        this.spd = state.getSpeed();
    }

    @Override
    public void notifyObserverPacmanDead() {
        observerCollection.forEach(obs -> obs.updatePacmanDead());
    }

    //팩맨의 팩검 섭취 사운드 출력 관련 타이머를 초기화하는 함수.팩맨이 팩검 섭취 시 옵저버가 이 함수를 실행하도록 해야함.
    public void initPacgumTimer() {
        pacgumSoundTimer = PACGUM_INIT_TIMER;
    }

    //사망
    public void die() {
        isDead = true;
        SoundManager.getInstance().stop(SoundManager.Sound.PAC_DOT);
        SoundManager.getInstance().play(SoundManager.Sound.FAIL);
        notifyObserverPacmanDead();
    }
}
