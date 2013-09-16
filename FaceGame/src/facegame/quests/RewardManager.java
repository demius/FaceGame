package com.example.FaceGame;

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

    private static int currentExperience =0;
    private static int questsSuccessfullyCompleted=0;

    /**
     * Set points and quests to zero
     */
    public static void reset(){
        currentExperience =0;
        questsSuccessfullyCompleted=0;
    }

    /**
     * Receive reward for completing a quest
     * @param size - the size of the reward
     */
    public static void awardReward(RewardSize size){

        switch (size){
         case SMALL:currentExperience+=SMALL_REWARD;
             break;
         case LARGE:currentExperience+=LARGE_REWARD;
             break;
     }
        questsSuccessfullyCompleted++;

    }

    /**
     * Return current experience points
     * @return
     */
    public static int getCurrentExperience(){
        return currentExperience;
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
        if(cost<=currentExperience){
            currentExperience-=cost;
            return true;
        }else{
            return false;
        }

    }


}
