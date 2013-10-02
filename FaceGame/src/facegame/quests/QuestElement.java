package facegame.quests;

import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;

import facegame.userinterface.FaceWrapper;

/**
 * @author laurent
 *
 */
public class QuestElement {

	private String elementNPC;
	private ArrayList<FaceWrapper> faceList;
	public ArrayList<FaceWrapper> getFaceList(){return faceList;}
	
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
	/**Gets the index of the dialog in the current quest element.
	 * @return		An int representing the current index of the dialog.
	 */
	public int getDialogIndex(){return dialogIndex;}
	
	private int numberOfFaces;
	public int getFacesNumber(){return numberOfFaces;}
	
	private String successDialog = "", failureDialog = "";
	private boolean isTestNode = false;
	
	private int showFaceAtIndex;

	/**Constructs a QuestElement object. QuestElement stores the dialogue that belongs to an NPC in a particular Quest.
	 * @param npc			The NPC that is involved in the Quest.
	 * @param dialog		A collection of dialogue that the NPC uses for the active Quest.
	 * @param l				The length of the dialog sequence.
	 * @param numFaces		The number of faces that the player will be exposed to in the current element.
	 */
	public QuestElement(String npc, Vector<String> dialog, int l, int numFaces, int facesPos) {		
		elementNPC = npc;
		dialogSeq = dialog;
		length = l;
		numberOfFaces = numFaces;
		faceList = new ArrayList<FaceWrapper>();
		showFaceAtIndex = facesPos;
	}
	
	/**
	 * @param npc
	 * @param dialog
	 * @param l
	 * @param numFaces
	 * @param success
	 * @param fail
	 */
	public QuestElement(String npc, Vector<String> dialog, int l, int numFaces, String success, String fail) {		
		elementNPC = npc;
		dialogSeq = dialog;
		length = l;
		numberOfFaces = numFaces;
		successDialog = success;
		failureDialog = fail;
		isTestNode = true;
		faceList = new ArrayList<FaceWrapper>();
		showFaceAtIndex = -1;
	}

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
	
	/**Use to identify the final dialog element in the current quest element.
	 * @return		String representing the dialog.
	 */
	public String getLastDialog(){
		return dialogSeq.elementAt(length-1);
	}
	
	public boolean isTestNode(){
		return (dialogIndex == length-1 && isTestNode);
	}
	
	public boolean isDialogComplete(){
		return(dialogIndex == length-1);
	}
	
	public void addFaceSprite(FaceWrapper face){
		faceList.add(face);
	}

	//Temp
	public String getNPC(){return elementNPC;}
	public Vector<String> getDialogSequence(){return dialogSeq;}
	
	/**
	 * @param isSuccess
	 * @return
	 */
	public String getResponseDialog(boolean isSuccess) {
		if(isSuccess)
			return elementNPC + ": " + successDialog;
		else
			return elementNPC + ": " + failureDialog;
	}
	/**
	 * @return
	 */
	public boolean showFaces() {
		//System.out.println("dialogIndex: " + dialogIndex + ", faces at index:" + showFaceAtIndex);
		if(dialogIndex == showFaceAtIndex)
			return true;
		
		return false;
	}
}