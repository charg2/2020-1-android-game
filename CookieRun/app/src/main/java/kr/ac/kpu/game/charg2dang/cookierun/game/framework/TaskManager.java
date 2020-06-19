package kr.ac.kpu.game.charg2dang.cookierun.game.framework;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class TaskManager
{

	private  TaskManager instance;

	public TaskManager getInstance()
	{
		if(null == instance)
		{
			instance = new TaskManager();
		}

		return instance;
	}


	ArrayList<Task> taskQueue;
	public void update()
	{
		for (Task tsk: taskQueue )
		{
//			// 조건에 만족하는 이벤ㄴ ㅢㄹ행
		}

	}


}
