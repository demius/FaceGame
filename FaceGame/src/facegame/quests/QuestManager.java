package facegame.quests;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import facegame.facemanager.FacesManager;

/**
 * @author laurent
 *
 */
public class QuestManager implements LifecycleListener{

	private FacesManager facesManager;
	
	private QuestReader qReader;
	private Vector<Quest> questSequence;	
	/**
	 * @return		The Quest that is currently active
	 */
	public Quest getQuest() {return questSequence.elementAt(questIndex);}	
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
		
		facesManager = new FacesManager();
	}	

	/**
	 * Temporary - Prints out the information of all the Quests.
	 */
	public void listQuests() {
		for(int i = 0; i < questSequence.size(); ++i) {
			Quest quest = questSequence.elementAt(i);
			System.out.println(quest.getName());

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
	
	/**Increment the position of the current quest if there is a quest to follow.
	 * @return		False if there is no next quest and true if there is.
	 */
	public boolean increment() {
		if(!getQuest().advanceProgress())
			questIndex++;
		
		if(questIndex < questSequence.size())
			return true;
		else
			return false;
	}
	
	public boolean isInvolved(String name){
		System.out.println(name + " check against " + getQuest().getCurrentElement().getNPC());
		if(name.equals(getQuest().getCurrentElement().getNPC())){
			return true;
		}
		return false;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}