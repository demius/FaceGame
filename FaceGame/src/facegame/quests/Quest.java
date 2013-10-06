package facegame.quests;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import facegame.userinterface.FaceWrapper;
import facegame.utils.GameLog;

public class Quest {
	
	private String questName;
	/**Getter for the name of this Quest.
	 * @return		The name of the Quest.
	 */
	public String getName(){return questName;}

	private int length;
	/**
	 * @return		The length of the quest. The length describes how many quest elements it contains.
	 */
	public int getQuestLength() {return length;}
	
	private Vector<QuestElement> sequence;
	/**Getter for the QuestElement at the current index.
	 * @return		The current QuestElement that contains the NPC and it's respective dialogue.
	 */
	public QuestElement getCurrentElement(){return sequence.elementAt(questProgressIndex);}	

	private int questProgressIndex = 0;
	
	private String ethnicity, reward;
	
	public enum TASKTYPE{newFace, seenFace, multipleFace}
	private TASKTYPE taskType;
	public TASKTYPE getType(){return taskType;}
	
	private int totalFaces;
	private int targetIndex;
	public int getTargetIndex(){return targetIndex;}
	private FaceWrapper targetFace;
	public FaceWrapper getTargetFace(){return targetFace;}
	private ArrayList<FaceWrapper> faces;
	
	private ArrayList<FaceWrapper> seenFaces, unseenFaces;
	
	private QuestTimer questTimer = null;
	public void setQuestTimer(QuestTimer qt){questTimer = qt;}
	public QuestTimer getQuestTimer(){return questTimer;}
	
	private GameLog log;
	
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
	public Quest(String name, Vector<QuestElement> elementSequence, int length, String ethnicity, 
			String reward, String taskType, int totalFaces, ArrayList<TextureRegion> faceList) {
		log = GameLog.getInstance();
		log.getStatsInstance().addToQuests();
		
		questName = name;
		
		sequence = elementSequence;
		this.length = length;
		this.ethnicity = ethnicity;
		this.reward = reward;
		this.totalFaces = totalFaces;
		
		faces = new ArrayList<FaceWrapper>(); 
		
		//Here the images are added to the correct quest elements
		if(taskType.equals("Identify Familiar Face")){
			this.taskType = TASKTYPE.seenFace;
			log.getStatsInstance().addToFamilarQuests();
		}
		else if(taskType.equals("Identify Novel Face")){
			this.taskType = TASKTYPE.newFace;
			log.getStatsInstance().addToNovelQuests();
		}
		else if(taskType.equals("Identify Single Face")){
			this.taskType = TASKTYPE.multipleFace;
			//TODO add single face quest types to the log
		}
		
		if(this.ethnicity.equals("Black Male")){
			log.getStatsInstance().addBlackMale(totalFaces);
		}
		else if(this.ethnicity.equals("White Male")){
			log.getStatsInstance().addWhiteMale(totalFaces);
		}
		else{
			;
		}
		
		
		switch (this.taskType){
		case newFace:
			newFaceTask(faceList);
			break;
		case seenFace:
			familiarFaceTask(faceList);
			break;
		case multipleFace:
			multipleFaceTask(faceList);
			break;
		}
	}
	
	private void newFaceTask(ArrayList<TextureRegion> faceList){
		int listPos = 0;
		Random r = new Random();
		targetIndex = r.nextInt(totalFaces);
		
		for(int i = 0; i < faceList.size(); i++)
			faces.add(new FaceWrapper(i, faceList.get(i)));
		
		targetFace = faces.get(targetIndex);
		
		for(int i = 0; i < sequence.size(); i++){
			QuestElement qe = sequence.elementAt(i);
			int facesRequired = qe.getFacesNumber();	
			
			int j = 0;
			while(j < facesRequired){
				if(listPos != targetIndex){
					qe.addFaceSprite(faces.get(listPos));
					faces.get(listPos).setSeen(true);
					j++;
				}
				listPos++;
			}
		}
	}
	
	private void familiarFaceTask(ArrayList<TextureRegion> faceList){
		//TODO add functionality to include more quest variability
		targetIndex = 0;
		
		for(int i = 0; i < faceList.size(); i++)
			faces.add(new FaceWrapper(i, faceList.get(i)));
		
		for(int i = 0; i < sequence.size(); i++){
			QuestElement qe = sequence.elementAt(i);
			int facesRequired = qe.getFacesNumber();
			
			if(facesRequired > 0){
				targetFace = faces.get(targetIndex); 
				qe.addFaceSprite(targetFace);
				faces.get(targetIndex).setSeen(true);
			
				for(int j = 1; j < facesRequired; j++){
					qe.addFaceSprite(faces.get(j));
					faces.get(j).setSeen(true);
				}
			
				for(int j = facesRequired-1; j > 0; j--)
					faces.remove(j);
			}
		}
	}
	
	private void multipleFaceTask(ArrayList<TextureRegion> faceList){
		seenFaces = new ArrayList<FaceWrapper>();
		unseenFaces = new ArrayList<FaceWrapper>();
		
		int faceIndex = 0;		
		
		for(int i = 0; i < sequence.size()-1; i++){
			QuestElement qe = sequence.elementAt(i);
			int facesRequired = qe.getFacesNumber();
			
			if(facesRequired > 0){
			
				for(int j = 0; j < facesRequired; j++){
					FaceWrapper newFace = new FaceWrapper(faceIndex, faceList.get(faceIndex));
					newFace.setSeen(true);
					qe.addFaceSprite(newFace);
					seenFaces.add(newFace);
					faceIndex++;
				}
			
				for(int j = faceIndex; j < faceList.size(); j++)
					unseenFaces.add(new FaceWrapper(j, faceList.get(j)));
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
	
	/**Increment the index of the current position in the Quest.
	 * @return		True if there is another quest element in the current quest, else it returns false.
	 */
	public boolean advanceProgress(){
		//Start the quest timer at the beginning of the quest
		if(questTimer == null && questProgressIndex == 0 && getCurrentElement().getDialogIndex() == 0){
			questTimer = new QuestTimer();
		}
		
		if(!getCurrentElement().incrementDialogIndex())
			questProgressIndex++;
		
		if(questProgressIndex == length){
			//End the quest timer
			if(!questTimer.isTimerComplete()){
				questTimer.finishTime();
				logQuest();
			}
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
	
	public ArrayList<FaceWrapper> getAllFaces(){
		if(taskType.equals(TASKTYPE.multipleFace)){
			ArrayList<FaceWrapper> returnList = new ArrayList<FaceWrapper>();
			
			int requiredFaces = getCurrentElement().getFacesNumber();
			int seenIndex = 0, unseenIndex = 0;
			
			Random r = new Random();
			for(int i = 0; i < requiredFaces; i++){
				if(r.nextInt(2) == 0){
					if(seenIndex < seenFaces.size()){
						returnList.add(seenFaces.get(seenIndex));
						seenIndex++;
					}
					else{
						returnList.add(unseenFaces.get(unseenIndex));
						unseenIndex++;
					}
				}
				else{
					if(unseenIndex < unseenFaces.size()){
						returnList.add(unseenFaces.get(unseenIndex));
						unseenIndex++;
					}
					else{
						returnList.add(seenFaces.get(seenIndex));
						seenIndex++;
					}
				}
			}
			
			return returnList; 
		}
		return faces;
	}
	
	/**Outputs all of the quest details to the log file.  
	 */
	public void logQuest(){
		GameLog.QuestLogEntry logEntry = log.new QuestLogEntry(questName, ethnicity, reward, taskType.name(),
				totalFaces, questTimer.getTimeString());
		
		log.writeToLog(logEntry);
	}
	
	public boolean hasReward(){
		if(this.reward.equals("None"))
			return false;
		else
			return true;
	}
	
	public RewardManager.RewardSize getReward(){
		if(this.reward.equals("None"))
			return RewardManager.RewardSize.NONE;
		else if(this.reward.equals("Small"))
			return RewardManager.RewardSize.SMALL;
		else
			return RewardManager.RewardSize.LARGE;
	}
	
	public String getRewardString(){
		return this.reward;
	}
	
	public String getQuestName(){
		return questName;
	}

	//Temp
	public Vector<QuestElement> getQuestElements(){return sequence;}
}