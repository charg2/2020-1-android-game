package kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.CookieState;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.ColliderTag;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.FSM;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.Framework;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameTimer;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GiantComponent;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.NerfComponent;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.ScoreManager;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.UiBridge;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.BoxCollidable;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.HitTrigger;
import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameObject;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.Terrain;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.ui.HPBar;

public class Cookie extends  GameObject implements BoxCollidable
{
    private static final String TAG = Cookie.class.getSimpleName();
    private static int halfSize;
    private float mass = 7.5f;

    private final float gravitiAcceleration = 9.8f;
    private float scale = 1.8f;
    private boolean isGround = false;
    public final int jumpCount = 2;

    // ui
    private HPBar hpBar;
    private final float maxHP = 50.f;
    private float currentHP = maxHP;


    // component
    private HitTrigger      hitTrigger      = new HitTrigger(1.0f);
    private GiantComponent  giantComponent  = new GiantComponent(this);
    private NerfComponent   nerfComponent   = new NerfComponent(this);
    private CookieState     cookieState     = CookieState.run;
    private FSM             stateMachine    = new RunState(this);
    private Stack<FSM>      stateStack      = new Stack<>();


    private float grivitySpeed;
    private static Cookie instance;

    private RectF colliderBox;//= new RectF();
    private RectF obstacleBox;//= new RectF();
    private RectF itemBox;


    public static Cookie getInstance()
    {
        if(instance == null)
            instance = new Cookie(0, 0);

        return instance;
    }

    private Cookie(float x, float y)
    {
        Resources res =Framework.getInstance().getResources();// gw.getResources();

        halfSize = stateMachine.getFab().getHeight() / 2;

        this.x = UiBridge.x(x);
        this.y = UiBridge.y(y);

        isGround = false;
    }

    @Override
    public void update(long timeDiffNanos)
    {
        updateForComponent(timeDiffNanos);
        updateForStateMachine(timeDiffNanos);

        // 충돌 처리 단계에서 발발닥이 땅에 있는것이 아니라면 중력 작용.
        if(isGround == false)
        {
            grivitySpeed += GameTimer.getInstance().getDeltaSecondsSingle() * gravitiAcceleration * mass;

            y += grivitySpeed;
        }
        else
        {
            grivitySpeed = 0; // 땅바닥과 충돌함.
        }

        // 매회 땅바닥과충돌 체크했는지 확이한기 위해서../.
        isGround = false;
    }

    @Override
    public void updateAfterCollide()
    {
        if(currentHP <= 0.0f)
        {
            CookieState temp = cookieState;
            if(CookieState.death != temp) // 한번만..
            {
                this.pushState(new DeathState(this));

                ScoreManager.getInstance().save();
            }
        }
    }


    public void updateForComponent(long timeDiffNanos)
    {
        hitTrigger.update(timeDiffNanos);
        giantComponent.update(timeDiffNanos);
        nerfComponent.update(timeDiffNanos);
    }

    public void updateForStateMachine(long timeDiffNanos)
    {
        stateMachine.update(timeDiffNanos);
        if(stateStack.size() > 0)
        {
            stateMachine.exit();
            stateMachine = stateStack.pop();
            stateStack.clear();
        }
    }

    public void pushState(FSM state)
    {
        stateStack.push(state);
    }

    @Override
    public void draw(Canvas canvas)
    {
        stateMachine.draw(canvas);
    }
    @Override
    public ColliderTag getTag()
    {
        return ColliderTag.Player;
    }
    @Override
    public RectF getColliderBox()
    {
        return this.colliderBox;
    }
    @Override
    public void onCollision(BoxCollidable o1)
    {
        ColliderTag tag = o1.getTag();
        switch (tag)
        {
//            case Coin:  case Item:
//                break;
//
//            case Obstacle:
//                break;
            case Terrain:
            {
                this.isGround = true;
                grivitySpeed = 0;
                this.y = (((Terrain)o1).getColliderBox().top -  (getColliderBox().bottom - getColliderBox().top )  /2 );
                break;
            }
            default:
                return;
        }

    }


    public void decreaseHP(float damage, boolean isOverraped)
    {
        if(isOverraped == true)
        {
            if( hitTrigger.canHitted() == true )
            {
                hitTrigger.hit();

                this.currentHP -= damage;

                this.pushState(new DamageState(this));
            }
        }
        else
        {
            this.currentHP -= damage;
        }

//        if(this.currentHP <= 1.0f)  // 체력이 0 이하면
//        {
//            this.currentHP = 0.0f;
////            if(CookieState.death != cookieState) // 한번만..
//////            {
//////                cookieState = CookieState.death;
//////                this.pushState(new DeathState(this));
//////            }
//        }
    }



    public float getMass(){return mass;}
    public boolean canHitted()
    {
        return hitTrigger.canHitted();
    }
    public float getHeight()
    {
        return halfSize * 2;
    }
    public void setFSMState(CookieState state)
    {
        this.cookieState = state;
    }
    public boolean isGround()  {  return this.isGround;  }
    public boolean isGiantMode()  {  return this.giantComponent.canGrow;  }
    public float getScale()  {  return this.scale;  }
    public float getX()  {  return this.x;  }
    public float getY()  {  return this.y;  }
    public void setPosition(float x, float y )  {  this.x = x; this.y = y;  }
    public void setScale(float scale)  { this.scale = scale; }
    public void move(float x, float y) { this.x = this.x + x; this.y = this.y + y;  }
    public void setGround(boolean value)  {  this.isGround = value;  }
    public void turnOnGiantMode(){ this.giantComponent.doAutoGrew(); }
    public void setColliderBoxForObstacle(RectF colliderBox) { this.colliderBox = colliderBox; }
    public void setColliderBox(RectF colliderBox) { this.colliderBox = colliderBox; }
    public final float getMaxHP() { return maxHP; }
    public float getCurrentHP() { return currentHP; }

    public void reset()
    {
        currentHP = maxHP;

        pushState(new RunState(this));
    }

    public CookieState getFSMState() {  return cookieState; }
}
