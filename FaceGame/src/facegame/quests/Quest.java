package facegame.quests;

import java.util.Vector;

public class Quest {
	
	private String questName;
	/**
	 * Getter for the name of this Quest.
	 * @return		The name of the Quest.
	 */
	public String getName(){return questName;}

	private int length;
	/**
	 * @return		The length of the quest. The length describes how many quest elements it contains.
	 */
	public int getQuestLength() {return length;}
	
	private Vector<QuestElement> sequence;
	/**
	 * Getter for the QuestElement at the current index.
	 * @return		The current QuestElement that contains the NPC and it's respective dialogue.
	 */
	public QuestElement getCurrentElement(){return sequence.elementAt(questProgressIndex);}	

	private int questProgressIndex = 0;
	/**
	 * Increment the index of the current position in the Quest.
	 * @return		True if there is another quest element in the current quest, else it returns false.
	 */
	public boolean advanceProgress(){
		if(!getCurrentElement().incrementDialogIndex())
			questProgressIndex++;
		
		if(questProgressIndex == length){
			return false;
		}
		else
			return true;
	}	
	
	private String ethnicity, homogeneity, reward, taskType;
	private int totalFaces;
	
	/** Constructor instantiates a new instance of the Quest class.  
	 * @param name				The name used to describe the Quest.
	 * @param elementSequence	The collection of QuestElements that makes up the Quest.
	 * @param length			The length of the quest.
	 * @param ethnicity			The ethnicity relative to this quest.
	 * @param homogeneity		The homogeneity of the faces involved in this quest.
	 * @param reward			The reward given by this quest.
	 * @param taskType			The type of task that the user is required to complete.
	 * @param totalFaces		The total faces that are shown in this quest.
	 */
	public Quest(String name, Vector<QuestElement> elementSequence, int length, String ethnicity, String homogeneity, 
			String reward, String taskType, int totalFaces) {
		questName = name;		
		sequence = elementSequence;
		this.length = length;
		this.ethnicity = ethnicity;
		this.homogeneity = homogeneity;
		this.reward = reward;
		this.taskType = taskType;
		this.totalFaces = totalFaces;
	}
	
	/**Returns the total number of faces used in the quest
	 * @return		returns the variable totalFaces
	 */
	public int getTotalFaces(){
		return totalFaces;
	}
	
	/**Checks whether an NPC has previously featured in the current active quest.
	 * @param npcName	The name of the NPC in question.
	 * @return			True if the NPC has featured, and false if it has not.
	 */
	public boolean hasPrevDialog(String npcName){
		//Check that the current quest element is not the first in the quest.
		if(questProgressIndex != 0){
			//Checks the NPC against previous elements
			for(int i = questProgressIndex-1; i >= 0; i--){
				if(npcName.equals(sequence.elementAt(i).getNPC())){
					return true;
				}
			}			
		}
		return false;
	}
	
	/**Gets the dialog of an NPC that is not the current NPC - the NPCs last dialog in the current quest. 
	 * @param npcName	The name of the NPC as a String.
	 * @return			The NPCs last spoken dialog as a String.
	 */
	public String getPrevDialog(String npcName){
		for(int i = questProgressIndex; i >= 0; i--){
			if(npcName.equals(sequence.elementAt(i).getNPC())){
				return npcName + ": " + sequence.elementAt(i).getLastDialog();
			}
		}
		return "-!-Error. No previous dialog-!-";
	}

	//Temp
	public Vector<QuestElement> getQuestElements(){return sequence;}
}