package facegame.quests;

import java.util.Vector;

import facegame.gameworld.NPC;

/**
 * @author laurent
 *
 */
public class QuestElement {

	private String elementNPC;

	private Vector<String> dialogSeq;
	
	private int length;
	/**
	 * @return		The length of the element. The length describes the number of dialog sequences in the element.
	 */
	public int getDialogLength() {return length;}
	/**
	 * @return		The dialogue String at the current index of the dialogue sequence.
	 */
	public String getCurrentDialog(){return dialogSeq.elementAt(dialogIndex);}

	private int dialogIndex = 0;
	/**Increments the index of the current dialog sequence position
	 * @return		True if there is another dialog element in the quest element, else it returns false.
	 */
	public boolean incrementDialogIndex() {
		dialogIndex++;
		if(dialogIndex == length)
			return false;
		else
			return true;
	}

	/**Constructs a QuestElement object. QuestElement stores the dialogue that belongs to an NPC in a particular Quest.
	 * @param npc			The NPC that is involved in the Quest.
	 * @param dialog		A collection of dialogue that the NPC uses for the active Quest.
	 * @param l				The length of the dialog sequence.
	 */
	public QuestElement(String npc, Vector<String> dialog, int l) {		
		elementNPC = npc;
		dialogSeq = dialog;
		length = l;
	}

	//Temp
	public String getNPC(){return elementNPC;}
	public Vector<String> getDialogSequence(){return dialogSeq;}
}