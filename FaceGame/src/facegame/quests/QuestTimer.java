package facegame.quests;

import com.badlogic.gdx.utils.TimeUtils;


public class QuestTimer {
	
	private long startTime;
	private float questTime;
	public float getQuestTime(){return questTime;}
	private String timeString;
	public String getTimeString(){return timeString;}
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
		convertTimeToString(questTime);
	}
	
	public void convertTimeToString(float t){
		int hours = (int) t / 3600;
	    int remainder = (int) t - hours * 3600;
	    int mins = remainder / 60;
	    remainder = remainder - mins * 60;
	    int secs = remainder;
	    
	    if(hours == 0){
	    	if(mins == 0)
	    		timeString = secs + "s";
	    	else
	    		timeString = mins + "m " + secs + "s";
	    }
	    else
	    	timeString = hours + "h " + mins + "m " + secs + "s";
	    	
	    //System.out.println(t + " - " + timeString);
	}
}
