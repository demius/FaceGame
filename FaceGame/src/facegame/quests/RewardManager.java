package facegame.quests;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created with IntelliJ IDEA.
 * User: Grant
 * Date: 2013/09/15
 * Time: 3:24 PM
 */
public class RewardManager {

    public static enum RewardSize{NONE,SMALL,LARGE};
    public static final int SMALL_REWARD=500;
    public static final int LARGE_REWARD=800;

    private static  int currentScore =0;
    private static  int questsSuccessfullyCompleted=0;
    private static int totalNumQuests=0;
    private static int availableRewards=0;
    

    /**
     * Set points and quests to zero
     */
    public  static void initialize(int numQuests,int availableSmallRewards,int availableLargeRewards){
        currentScore =0;
        questsSuccessfullyCompleted=0;
        totalNumQuests=numQuests;
        availableRewards=availableSmallRewards*SMALL_REWARD + availableLargeRewards*LARGE_REWARD;
       
    }
    
    public static int getAvailableRewards(){
    	return availableRewards;
    }
    
        
    public static void setTotalQuests(int num){
    	totalNumQuests=num;
    }

    /**
     * Receive reward for completing a quest
     * @param size - the size of the reward
     */
    public static  void awardReward(RewardSize size){

        switch (size){
         case SMALL:currentScore+=SMALL_REWARD;
             break;
         case LARGE:currentScore+=LARGE_REWARD;
             break;
     }
        questsSuccessfullyCompleted++;

    }

    /**
     * Return current experience points
     * @return
     */
    public static int getCurrentScore(){
        return currentScore;
    }

    /**
     * Return the number of quests successfully completed
     * @return
     */
    public static int getQuestsSuccessfullyCompleted(){
        return questsSuccessfullyCompleted;
    }

    /**
     * purchase something using points
     * @param cost the cost of the item
     * @return true if successfully purchased, false if insufficient points
     */
    public static boolean purchase(int cost){
        if(cost<=currentScore){
            currentScore-=cost;
            return true;
        }else{
            return false;
        }

    }
    
   

}
