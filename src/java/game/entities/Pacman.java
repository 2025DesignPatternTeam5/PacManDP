package game.entities;

import game.*;
import game.entities.ghosts.Ghost;
import game.entities.items.Item;
import game.entities.items.SpeedUp;
import game.utils.CollisionDetector;
import game.utils.KeyHandler;
import game.utils.WallCollisionDetector;
import game.pacmanEffect.*;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//팩맨 클래스
public class Pacman extends MovingEntity implements Sujet {
    private final float PACGUM_INIT_TIMER = 15f;//팩검 타이머 초기화 상수

    private CollisionDetector collisionDetector;
    private List<Observer> observerCollection;
    private List<EffectCommand> activeEffects; // 아이템으로 인한 상태 관리
    private float pacgumSoundTimer;
    private boolean isDead;

    public Pacman(int xPos, int yPos) {
        super(32, xPos, yPos, 2, "pacman.png", 4, 0.3f);
        observerCollection = new ArrayList<>();
        activeEffects = new ArrayList<>();
        pacgumSoundTimer = 0f;
        isDead = false;
    }

    //이동 처리
    public void input(KeyHandler k) {
        int new_xSpd = 0;
        int new_ySpd = 0;
        boolean inLeft = k.k_left.isPressed;
        boolean inRight = k.k_right.isPressed;
        boolean inUp = k.k_up.isPressed;
        boolean inDown = k.k_down.isPressed;

        if (!onTheGrid()) return; //팩맨은 게임 구역의 한 칸(cell)에 있어야 한다
        if (!onGameplayWindow()) return; //팩맨은 게임 구역 안에 있어야 한다

        // 혼란 상태: 키 반전 (왼쪽 키 -> 오른쪽 이동, 위쪽 키 -> 아래 이동)
        if (this.confuse) {
           boolean temp = inLeft;
           inLeft = inRight;
           inRight = temp;

           temp = inDown;
           inDown = inUp;
           inUp = temp;
        }


        //누른 키에 따라 팩맨의 방향이 그에 맞게 바뀐다
        if (inLeft && xSpd >= 0 && !WallCollisionDetector.checkWallCollision(this, -spd, 0)) {
            new_xSpd = -spd;
        }
        if (inRight && xSpd <= 0 && !WallCollisionDetector.checkWallCollision(this, spd, 0)) {
            new_xSpd = spd;
        }
        if (inUp && ySpd >= 0 && !WallCollisionDetector.checkWallCollision(this, 0, -spd)) {
            new_ySpd = -spd;
        }
        if (inDown && ySpd <= 0 && !WallCollisionDetector.checkWallCollision(this, 0, spd)) {
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

    private EffectCommand findEffectClass(EffectCommand newEffect) {
        for (EffectCommand effect : activeEffects) {
            if (effect.getClass().equals(newEffect.getClass())) {
                return effect;
            }
        }
        return null;
    }

    public void addEffect(EffectCommand newEffect) {
        // 중복 체크 (이미 있는 효과면 타이머만 리셋)
        EffectCommand effect = findEffectClass(newEffect);
        if (effect != null) {
            effect.resetTimer(); // 지속시간 초기화
            System.out.println(newEffect.getClass().getSimpleName() + " 시간 연장됨!");
            return;
        }
        // 새로운 효과면 적용 및 리스트 추가
        newEffect.apply(this);
        activeEffects.add(newEffect);
        notifyObserverEffectAdded(newEffect);
    }

    @Override
    public void update() {
        if(isDead) //죽었다면 어떤 상호작용도 일어나지 않도록 바로 리턴
            return;
//        System.out.println("first:" + xPos + " " + yPos + " " + xSpd + " " + ySpd + " " + spd);
        Iterator<EffectCommand> iterator = activeEffects.iterator();
        while (iterator.hasNext()) {
            EffectCommand effect = iterator.next();
            effect.update(); // timer++
            if (effect.isExpired()) {
                effect.remove(this); // 스탯 원상복구
                iterator.remove();   // 리스트에서 삭제
                notifyObserverEffectRemoved(effect);
            }
            else notifyObserverEffectTick(effect);
        }

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
            notifyObserverGhostCollision(gh);
        }

        Item item = (Item) collisionDetector.checkCollision(this, Item.class);
        if (item != null) {
            notifyObserverItemEaten(item);
        }

        // 팩맨의 다음 잠재적 위치에 벽이 없으면, 팩맨의 위치를 갱신한다
//        System.out.println(xPos + ", " + yPos);
        updatexySpd();
//        System.out.println("second:" + xPos + " " + yPos + " " + xSpd + " " + ySpd + " " + spd);
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

    @Override
    public void notifyObserverEffectAdded(EffectCommand newEffect) {
        observerCollection.forEach(obs-> obs.updateEffectAdded(newEffect));
    }

    @Override
    public void notifyObserverEffectRemoved(EffectCommand newEffect) {
        observerCollection.forEach(obs -> obs.updateEffectRemoved(newEffect));
    }

    @Override
    public void notifyObserverEffectTick(EffectCommand effect) {
        observerCollection.forEach(obs -> obs.updateEffectTick(effect));
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
