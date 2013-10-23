package facegame.quests;


/**
 * Class to manage the basic reward system
 */
public class RewardManager {

    
   
    private static  int currentScore =0;
    private static  int questsSuccessfullyCompleted=0;
    private static int totalNumQuests=0;
    private static int availableRewards=0;
    

    /**
     * Set points and quests to zero
     * @param numQuests - the total number of quests loaded
     * @param totalRewards - the total available rewards from all quests
     */
    public  static void initialize(int numQuests,int totalRewards){
        currentScore =0;
        questsSuccessfullyCompleted=0;
        totalNumQuests=numQuests;
        availableRewards=totalRewards;
       
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
    public static  void awardReward(int reward){

        currentScore+=reward;
        questsSuccessfullyCompleted++;

    }
    /**
     * Deduct a penalty from the score for failing a quest
     * @param penalty
     */
    public static void penalize(int penalty){
    	currentScore-=penalty;
    	if(currentScore<0)
    		currentScore=0;
    }

    /**
     * Return current experience points
     * @return int
     */
    public static int getCurrentScore(){
        return currentScore;
    }

    /**
     * Return the number of quests successfully completed
     * @return int
     */
    public static int getQuestsSuccessfullyCompleted(){
        return questsSuccessfullyCompleted;
    }

    /**
     * purchase something using points. This was never fully implemented.
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
