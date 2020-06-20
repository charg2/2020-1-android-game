//package kr.ac.kpu.game.charg2dang.cookierun.game.obj;
//
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.RectF;
//import android.util.Log;
//
//import java.util.ArrayList;
//
//import kr.ac.kpu.game.charg2dang.cookierun.R;
//import kr.ac.kpu.game.charg2dang.cookierun.game.iface.BoxCollidable;
//import kr.ac.kpu.game.charg2dang.cookierun.game.world.MainWorld;
//import kr.ac.kpu.game.charg2dang.cookierun.game.framework.GameObject;
//import kr.ac.kpu.game.charg2dang.cookierun.game.util.CollisionHelper;
//
//public class Plane implements GameObject, BoxCollidable
//{
//
//    public static final int BULLET_FIRE_INTERVAL_NSEC = 100_000_000;
//    private static final String TAG = Plane.class.getSimpleName();
//    private static final int SPEED = 400;
//    private static Bitmap bitmap;
////    private final Matrix matrix;
//    private static int halfSize;
//    private float dx;
//    private float x;
//    private float y;
//    private float dy;
//    private long lastFire;
//    private Joystick joystick;
//
//
//    public Plane(Resources res, float x, float y, float dx, float dy)
//    {
//        if(null == bitmap)
//        {
//            bitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
//            halfSize = bitmap.getHeight() / 2;
//        }
//
//        this.x = x;
//        this.y = y;
//
//        this.dx = dx;
//        this.dy = dy;
//    }
//
//    @Override
//    public void update()
//    {
//        MainWorld gw= MainWorld.get();
//        long now=gw.getCurrentFrameTimeNanos();
//        long elapsed=now-lastFire;
//        if(elapsed> BULLET_FIRE_INTERVAL_NSEC)
//        {
//            fire();
//            lastFire = now;
//        }
//
//        int xdir = joystick.getHorzDirection();
//        x += xdir * SPEED * gw.getTimeDiffInSecond();
//
//        if ( x < 0 )
//        {
//            x = 0;
//        }
//        else if(x > gw.getRight())
//        {
//            x = gw.getRight();
//        }
//
//        ArrayList<GameObject> enemies = gw.objectsAt(MainWorld.LayerType.enemy);
//            for(GameObject e : enemies)
//            {
//                if (false == (e instanceof Enemy))
//                {
//                    Log.d(TAG, "Ojbect at Layer.enemy is : " + e);
//                    continue;
//                }
//
//                Enemy enemy = (Enemy) e;
//                if (CollisionHelper.collides(enemy, this))
//                {
//                    gw.endGame();
////                    enemy.decreaseLife(this.power);
////                    toBeDeleted = true;
//                    break;
//                }
//            }
//    }
//
//
//
//    private void fire()
//    {
//        Bullet bullet = Bullet.get(x,y-halfSize);
//
//        MainWorld.get().add(MainWorld.LayerType.missile, bullet);
//    }
//
//
//    @Override
//    public void draw(Canvas canvas)
//    {
//        canvas.drawBitmap(bitmap, x - halfSize, y -halfSize, null);
//    }
//
////    public void head(float x, float y)
////    {
////        this.x = x;
////    }
//
//    @Override
//    public void getBox(RectF rect)
//    {
//        int hw = bitmap.getWidth() / 2;
//        int hh = bitmap.getHeight() / 2;
//
//        rect.left = x - hw;
//        rect.right = x + hw;
//        rect.top = y - hh;
//        rect.bottom = y + hh;
//    }
//
//	public void setJoystick(Joystick joystick)
//	{
//		this.joystick = joystick;
//	}
//}
