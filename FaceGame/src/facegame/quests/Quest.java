package facegame.quests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
	
	private String ethnicity, homogeneity, reward;
	
	public enum TASKTYPE{newFace, seenFace}
	private TASKTYPE taskType;
	
	private int totalFaces;
	private int targetIndex;
	public int getTargetIndex(){return targetIndex;}
	private Sprite targetSprite;
	public Sprite getTargetSprite(){return targetSprite;}
	private ArrayList<TextureRegion> faces;
	
	/** Constructor instantiates a new instance of the Quest class.  
	 * @param name				The name used to describe the Quest.
	 * @param elementSequence	The collection of QuestElements that makes up the Quest.
	 * @param length			The length of the quest.
	 * @param ethnicity			The ethnicity relative to this quest.
	 * @param homogeneity		The homogeneity of the faces involved in this quest.
	 * @param reward			The reward given by this quest.
	 * @param taskType			The type of task that the user is required to complete.
	 * @param totalFaces		The total faces that are shown in this quest.
	 * @param faceList			A list of the TextureRegions representing the faces for this quest.
	 */
	public Quest(String name, Vector<QuestElement> elementSequence, int length, String ethnicity, String homogeneity, 
			String reward, String taskType, int totalFaces, ArrayList<TextureRegion> faceList) {
		questName = name;		
		sequence = elementSequence;
		this.length = length;
		this.ethnicity = ethnicity;
		this.homogeneity = homogeneity;
		this.reward = reward;
		this.totalFaces = totalFaces;
		this.faces = faceList;
		
		//Here the images are added to the correct quest elements
		if(taskType.equals("Identify Familiar Face"))
			this.taskType = TASKTYPE.seenFace;
		else
			this.taskType = TASKTYPE.newFace;
		
		switch (this.taskType){
		case newFace:
			newFaceTask();
			break;
		case seenFace:
			seenFaceTask();
			break;
		}
	}
	
	private void newFaceTask(){
		int listPos = 0;
		Random r = new Random();
		targetIndex = r.nextInt(totalFaces);
		targetSprite = new Sprite(faces.get(targetIndex));
		
		for(int i = 0; i < sequence.size(); i++){
			QuestElement qe = sequence.elementAt(i);
			int facesRequired = qe.getFacesNumber();	
			
			int j = 0;
			while(j < facesRequired){
				if(listPos != targetIndex){
					qe.addFaceSprite(new Sprite(faces.get(listPos)));
					j++;
				}
				listPos++;
			}
		}
	}
	
	private void seenFaceTask(){
		
		//TODO add functionality to include more quest variability
		
		targetIndex = 0;
		
		for(int i = 0; i < sequence.size(); i++){
			QuestElement qe = sequence.elementAt(i);
			int facesRequired = qe.getFacesNumber();
			
			if(facesRequired > 0){
				targetSprite = new Sprite(faces.get(targetIndex)); 
				qe.addFaceSprite(targetSprite);
			
				for(int j = 1; j < facesRequired; j++)
					qe.addFaceSprite(new Sprite(faces.get(j)));
			
				for(int j = facesRequired-1; j > 0; j--)
					faces.remove(j);
			}
		}
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
	
	public boolean isComplete(){
		if(questProgressIndex == length-1 && getCurrentElement().isTestNode())
			return true;
		else
			return false;
	}
	
	public ArrayList<TextureRegion> getAllFaces(){
		return faces;
	}

	//Temp
	public Vector<QuestElement> getQuestElements(){return sequence;}
}