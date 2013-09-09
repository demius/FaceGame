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
		
		if(questProgressIndex == length)
			return false;
		else
			return true;
	}	
	

	/**
	 * Constructor instantiates a new instance of the Quest class.  
	 * @param n			The name used to describe the Quest.
	 * @param seq		The collection of QuestElements that makes up the Quest.
	 * @param l			The length of the quest.
	 */
	public Quest(String n, Vector<QuestElement> seq, int l) {
		questName = n;		
		sequence = seq;
		length = l;
	}

	//Temp
	public Vector<QuestElement> getQuestElements(){return sequence;}
}