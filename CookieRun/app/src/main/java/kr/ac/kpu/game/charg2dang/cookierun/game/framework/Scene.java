package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.LayerType;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.PauseReason;
import kr.ac.kpu.game.charg2dang.cookierun.game.enumeration.SceneType;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.BoxCollidable;
import kr.ac.kpu.game.charg2dang.cookierun.game.iface.Recyclable;
import kr.ac.kpu.game.charg2dang.cookierun.game.obj.cookie.Cookie;
import kr.ac.kpu.game.charg2dang.cookierun.game.util.CollisionHelper;

public abstract class Scene
{
    private static final    String              TAG = Scene.class.getSimpleName();
    protected static        Scene               instance;
    protected               RecyclePool         recyclePool = new RecyclePool();
    protected               View                view;
    protected               boolean             paused = false;

    protected static        SceneType                           currentSceneType        = SceneType.max;
    private                 long                                frameTimeNanos;
    private                 long                                timeDiffNanos;
    protected static        Rect                                rect;
    protected               ArrayList<ArrayList<GameObject>>    layers;
    private                 CollisionHelper                     collisionHelper         = new CollisionHelper();

    protected PauseReason currentPauseReason = PauseReason.None;

    private void initLayers()
    {
        layers = new ArrayList<>();
        int layerCount = getLayerCount();

        for(int i = 0; i < layerCount; i++)
        {
            ArrayList<GameObject> layer = new ArrayList<>();
            layers.add(layer);
        }
    }

    public void initResources(View view)
    {
        this.view = view;

        initLayers();
        initObjects();
    }

    public void draw(Canvas canvas)
    {
        for(ArrayList<GameObject> objects : layers)
        {
            for (GameObject o : objects)
            {
                o.draw(canvas);
            }
        }
    }

    public static SceneType getCurrentSceneType()
    {
        return Scene.currentSceneType;
    }


    public void update(long frameTimeNanos)
    {
        if(paused == false)
        {
            this.timeDiffNanos = frameTimeNanos - this.frameTimeNanos;
            this.frameTimeNanos = frameTimeNanos;
            if (rect == null)
            {
                return;
            }

            for (ArrayList<GameObject> objects : layers)
            {
                for (GameObject o : objects)
                {
                    o.update(timeDiffNanos);
                }
            }

            removeTrashObjects();
        }
    }


    public void updateAfterCollide()
    {
        if(paused == false)
        {
            for (ArrayList<GameObject> objects : layers)
            {
                for (GameObject o : objects)
                {
                    o.updateAfterCollide();
                }
            }

            removeTrashObjects();
        }
    }



    public void collide(long frameTimeNanos)
    {
        if (layers.size() == 0)
        {
            return;
        }

        ArrayList<GameObject> items     = layers.get(LayerType.item.ordinal());
        ArrayList<GameObject> players   = layers.get(LayerType.player.ordinal());
        ArrayList<GameObject> obstacles = layers.get(LayerType.obstacle.ordinal());
        ArrayList<GameObject> terrains  = layers.get(LayerType.terrain.ordinal());

        for( GameObject player : players )
        {
            RectF box = ((Cookie)player).getColliderBox();
            if(box == null)
            {
                return;
            }

            for (GameObject item : items)
            {
                collisionHelper.collides((BoxCollidable) player, (BoxCollidable) item);
            }

//            if(((Cookie)player).isGiantMode() == false )
//            {
                for (GameObject obstacle : obstacles)
                {
                    collisionHelper.collides((BoxCollidable) player, (BoxCollidable) obstacle);
                }
//            }


            for (GameObject terrain : terrains)
            {
                collisionHelper.collides((BoxCollidable) player, (BoxCollidable) terrain);
            }

            return;
        }
    }


    protected void removeTrashObjects()
    {
        for( ArrayList<GameObject> layer : layers)
        {
            for (Iterator<GameObject> iterator = layer.iterator(); iterator.hasNext(); )
            {
                GameObject go = iterator.next();
                if (go.getState() == false)
                {
                    iterator.remove();

                    if (go instanceof Recyclable)
                    {
                        ((Recyclable) go).recycle();
                        getRecyclePool().add(go);
                    }
                }
            }
        }
    }

    public void add(final int index, final GameObject obj)
    {
        view.post(new Runnable()
        {
            @Override
            public void run()
            {
                ArrayList<GameObject> objects = layers.get(index);
                objects.add(obj);
            }
        });
    }

    protected Scene()  { }
    public boolean onTouchEvent(MotionEvent event) {  return false;  }
    protected void initObjects(){};

    public static void setRect(Rect rect)
    {
        Scene.rect  = rect;
    }

    public ArrayList<GameObject> getLayer(LayerType lt)
    {
        return this.layers.get(lt.ordinal());
    }

    public RecyclePool getRecyclePool()
    {
        return this.recyclePool;
    }

    abstract protected int getLayerCount();
    public int getLeft()  {  return this.rect.left; }
    public int getRight()  { return this.rect.right; }
    public int getTop()  {  return this.rect.top; }
    public int getBottom()  { return this.rect.bottom;  }

    public Resources getResources()
    {
        return this.view.getResources();
    }

    public Context getContext()
    {
        return view.getContext();
    }

    public long getTimeDiffNanos()
    {
        return timeDiffNanos;
    }

    public void pause(PauseReason reason)
    {
        paused = true;
        currentPauseReason = reason;

        onPause(reason);
    }

    public void resume()
    {
        paused = false;

        onResume();
    }

    public void reset()
    {
        onReset();
    }

    protected abstract void onReset();
    protected abstract void onResume();
    protected abstract void onPause(PauseReason reason);

    public void setCurrentSceneType(SceneType currentSceneType)
    {
        Scene.currentSceneType = currentSceneType;
    }

    public boolean getPaused()
    {
        return paused;
    }
}
