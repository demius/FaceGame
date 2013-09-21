package facegame.quests;

import com.badlogic.gdx.utils.TimeUtils;


public class QuestTimer {
	
	private long startTime;
	private float questTime;
	public float getQuestTime(){return questTime;}
	private boolean timerComplete;
	public boolean isTimerComplete(){return timerComplete;}
	
	public QuestTimer(){
		startTime = TimeUtils.millis();
		timerComplete = false;
	}
	
	public void finishTime(){
		long time =  TimeUtils.millis() - startTime;
		questTime = time/1000.0f;
		timerComplete = true;
	}
}
