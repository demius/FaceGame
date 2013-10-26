package facegame.quests;

import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.Gdx;

import facegame.userinterface.FaceWrapper;
import facegame.utils.GameLog;

/**
 * @author laurent
 *
 */
public class QuestManager{

	private boolean allComplete;
	
	private QuestReader qReader;
	private Vector<Quest> questSequence;	
	/**
	 * @return		The Quest that is currently active
	 */
	public Quest getQuest() {return questSequence.elementAt(questIndex);}	
	public int getQuestIndex(){return questIndex;}
	private int questIndex = 0;	
	/**
	 * Increments the position of the current quest. i.e. Move on to the next quest.
	 */
	public void nextSequence() {++questIndex;};	

	/**
	 * Create a new instance of the QuestManager class. Initializes QuestReader and calls the readQuests() method from the QuestReader.
	 */
	public QuestManager() {
		qReader = new QuestReader();
		questSequence = qReader.readQuests();
		
		allComplete = false;
	}	

	/**
	 * Temporary - Prints out the information of all the Quests.
	 */
	public void listQuests() {
		for(int i = 0; i < questSequence.size(); ++i) {
			Quest quest = questSequence.elementAt(i);
			//System.out.println(quest.getName());
			
			Vector<QuestElement> qes = quest.getQuestElements();
			for(int j = 0; j < qes.size(); ++j) {
				QuestElement qe = qes.elementAt(j);
				Vector<String> dlg = qe.getDialogSequence();
				for(int k = 0; k < dlg.size(); ++k) {
					System.out.println(" " + qe.getNPC() + ": " + dlg.elementAt(k));
				}
			}
		}
	}
	
	/**Gets the dialog of the current quest element in the form "NPCName: elementDialog"
	 * @return		The String that represents current dialog from the quest element.
	 */
	public String getCurrentDialog() {
		String nameNPC = getQuest().getCurrentElement().getNPC();
		String dialog = getQuest().getCurrentElement().getCurrentDialog();
		return nameNPC + ": " + dialog;
	}
	
	/**Gets the name of the current quest.
	 * @return		A String containing the name of the current quest.
	 */
	public String getCurrentQuestName() {
		return getQuest().getName();
	}
	
	/**Returns the name of the npc in the front of the list
	 * @return		The name of the NPC currently at the head of the list
	 */
	public String getNPCName(){
		return getQuest().getCurrentElement().getNPC();
	}
	
	/**Increment the position of the current quest if there is a quest to follow.
	 * @return		False if there is no next quest and true if there is.
	 */
	public boolean increment() {		
		if(!getQuest().advanceProgress())
			{questIndex++;
			QuestProgress.updateProgress(questIndex+1);
			}
		
		if(questIndex < questSequence.size()){
			return true;
		}
		else{
			allComplete = true;
			return false;
		}
	}

	/** Gets and returns the total number of face
	 *@return returns the totol number of faces of the current quest element
	 */
	public int getTotalQuestFaces(){
		return getQuest().getTotalFaces();
	}


	public boolean isDialogComplete(){
		return getQuest().getCurrentElement().isDialogComplete();
	}
	
	/**Gets the current or previous dialog of an NPC that is involved in the current quest.
	 * @param name		The name of the NPC as a String.
	 * @return			The String corresponding to the NPCs dialog.
	 */
	public String getCorrespondingDialog(String name){
		if(!allComplete){
			if(name.equals(getQuest().getCurrentElement().getNPC())){
				return getCurrentDialog();
			}else{
				return getQuest().getPrevDialog(name);
			}
		}
		return "-!-Error. Retrieving dialog. (QuestElement.isInvolved())-!-";
	}
	
	public String getResponseDialog(boolean isSuccess){
		return getQuest().getCurrentElement().getResponseDialog(isSuccess);
	}
	
	
	/**A check to determine if the NPC is a previous NPC of the quest.
	 * @param name		The name of the NPC as a String.
	 * @return			True if the NPC is previously and false if it is not present in the current quest.
	 */
	public boolean isPrevNPC(String name){
		if(!allComplete){
			if(getQuest().hasPrevDialog(name))
				return true;
		}
		return false;
	}
	
	public String getCurrentNPC(){
		return getQuest().getCurrentElement().getNPC();

	}
	
	/**A check to determine if the NPC is the current active NPC of the quest.
	 * @param name		The name of the NPC as a String.
	 * @return			True if the NPC is current and false if it is previous or not present in the current quest.
	 */
	public boolean isCurrentNPC(String name){
		if(!allComplete){
			if(name.equals(getQuest().getCurrentElement().getNPC()))
				return true;
		}
		return false;		
	}
	
	public ArrayList<FaceWrapper> getNodeFaces(){
		if(getQuest().getCurrentElement().showFaces())
			return getQuest().getCurrentElement().getFaceList();
		return null;
	}
	
	public ArrayList<FaceWrapper> getQuestFaces(){
		return getQuest().getAllFaces();
	}
	
	public boolean questsComplete(){
		return allComplete || questSequence.size() == 0;
	}
	
	public boolean isTestNode(){
		if(allComplete)
			return false;
		
		return getQuest().isComplete();		
	}
	
	public FaceWrapper getTargetFace(){
		return getQuest().getTargetFace();
	}
	
	public int getNumQuests(){
		return questSequence.size();
	}

	public int getAvailableRewards(){
		int count=0;
		for(int i=0;i<questSequence.size();i++){
			count+=questSequence.get(i).getReward();
		}
		return count;
	}
	/**
	 * 
	 * @return The total penalties from all quests
	 */
	public int getAvailablePenalties(){
		int count=0;
		for(int i=0;i<questSequence.size();i++){
			count+=questSequence.get(i).getPenalty();			
		}
		return count;
	}
	
	public Quest.TASKTYPE getCurrentQuestTaskType(){
		return getQuest().getType();
	}
	
	public int getTotalGameFaces(){
		int total = 0;
		for (Quest q : questSequence) {
			total += q.getTotalFaces();
		}
		return total;
	}


	public void dispose(){
		qReader.dispose();
	}

}

