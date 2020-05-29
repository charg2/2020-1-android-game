package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclePool
{
	HashMap<Class, ArrayList<Object>> map = new HashMap<>();

	public RecyclePool()  { }

	public void add(Object obj)
	{
		Class theClass = obj.getClass();
		ArrayList<Object> list =  map.get(theClass);
		if(list == null)
		{
			 list = new ArrayList<>();
			 map.put(theClass, list);
		}
		list.add(obj);
	}

	public Object get(Class clazz)
	{
		ArrayList<Object> list = map.get(clazz);
		if(list == null)
		{
			return null;
		}
		if(list.size() == 0)
		{
			return null;
		}

		return list.remove(0);
	}


}
