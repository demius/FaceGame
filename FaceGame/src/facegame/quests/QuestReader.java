package facegame.quests;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import facegame.facemanager.FacesManager;
import facegame.facemanager.FacesManager.ETHNICITY;

public class QuestReader {

	private Vector<Quest> questSequence;
	private FacesManager facesManager;

	/**
	 * The constructor of QuestReader initializes the collection of Quests that will be determined from the XML file.
	 */
	public QuestReader() {
		questSequence = new Vector<Quest>();
		facesManager = new FacesManager();
	}

	/**
	 * @return		The collections of Quests interpreted from the input XML file.
	 */
	public Vector<Quest> readQuests() {
		
		//Get a list of all the quest xml files
		File f = new File("bin/quests");
		File[] list = f.listFiles();
		
		if(list.length == 0){
			System.out.println("-!-Error. No quest files found. Please create quests using the QuestBuilder "
					+ "before you play the game-!-");
			System.exit(0);
		}

		//Loop through all the quest xml files
		for(int i = 0; i < list.length; i++){
			if(!list[i].getName().equals(".quest.xml") && list[i].getName().endsWith("_quest.xml")){
				String questFileName = list[i].getName();
				System.out.println(questFileName);
				
				try {
					DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
					Document doc = docBuilder.parse (list[i]);
					
					NodeList quest = doc.getDocumentElement().getChildNodes();
					
					String questName = "", ethnicity = "", task_type = "";
					int reward = 0, penalty = 0;
					int totalfaces = 0;
					
					//loop through the contents of the quest
					for(int j = 0; j < quest.getLength(); ++j) {
		            	if(quest.item(j) instanceof Element){
		            		Node currentNode = quest.item(j);
		            		
		            		if(currentNode.getNodeName().equals("quest_name"))
		            			questName = currentNode.getTextContent();
		            		else if(currentNode.getNodeName().equals("ethnicity"))
		            			ethnicity = currentNode.getTextContent();
		            		else if(currentNode.getNodeName().equals("totalfaces"))
		            			totalfaces = Integer.parseInt(currentNode.getTextContent());
		            		else if(currentNode.getNodeName().equals("reward"))
		            			reward = Integer.parseInt(currentNode.getTextContent());
		            		else if(currentNode.getNodeName().equals("penalty"))
		            			penalty = Integer.parseInt(currentNode.getTextContent());
		            		else if(currentNode.getNodeName().equals("task_type"))
		            			task_type = currentNode.getTextContent();
		            		else if(currentNode.getNodeName().equals("quest_sequence")){		            			
		            			
		            			Vector<QuestElement> questElements = new Vector<QuestElement>();
		                		int elementLength = 0;
		                		
		                		NodeList sequence = currentNode.getChildNodes();	//Include one 'quest_node' element
		                		
		                		for (int k = 0; k < sequence.getLength(); ++k) {
		                			if(sequence.item(k) instanceof Element) {
		                				Node questNode = sequence.item(k);				//'quest_node' element
		                				
		                				NodeList seqList = questNode.getChildNodes();	//Include 'npc', 'dialog_seq', num_of_faces' elements
		                				
		                				int dialogLength = 0;
		                				String name = "";
		                				int numFaces = 0;
		                				Vector<String> dialog = new Vector<String>();
		                				String successD = null, failureD = null;
		                				int facesPosition = 0;
		                				
		                				for(int m = 0; m < seqList.getLength(); ++m) {
		                					if(seqList.item(m) instanceof Element) {
		                						Node seqPoint = seqList.item(m);
		                						
		                						//Check for NPC name
		                						if(seqPoint.getNodeName().equals("NPC"))
		                							name = seqPoint.getTextContent();
		                						else if(seqPoint.getNodeName().equals("dialogue_sequence")){
		                							NodeList dialogSeq = seqList.item(m).getChildNodes(); //Includes the dialog nodes
		                							
		                							for(int n = 0; n < dialogSeq.getLength(); n++){
		                								if(dialogSeq.item(n) instanceof Element){
		                									Node currentDialog = dialogSeq.item(n);
		                									//Test for the success and failure dialogs
		                									if(currentDialog.getNodeName().equals("success_dialogue")){
		                										successD = currentDialog.getTextContent(); 
		                									}
		                									else if(currentDialog.getNodeName().equals("failure_dialogue")){
		                										failureD = currentDialog.getTextContent();
		                									}
		                									else{
		                										if(currentDialog.hasAttributes()){
		                											if(currentDialog.getAttributes().item(0).getTextContent().equals("true")){
		                												facesPosition = dialogLength;
		                												//System.out.println(facesPosition + ": set face show");
		                											}
		                										}
		                										dialog.add(dialogSeq.item(n).getTextContent());
		                										dialogLength++;
		                									}
		                								}
		                							}
		                						}
		                						else if(seqPoint.getNodeName().equals("num_faces"))	
		                							numFaces = Integer.parseInt(seqPoint.getTextContent());
		                							            							
		                					}
		                				}
		                				//Create the QuestElement containing the NPC and Dialogue sequence
		                				QuestElement qe = null;
		                				if(successD != null && failureD != null){
		                					qe = new QuestElement(name, dialog, dialogLength, numFaces, successD, failureD);
		                				}
		                				else{	
		                					qe = new QuestElement(name, dialog, dialogLength, numFaces, facesPosition);
		                				}
		                				questElements.add(qe);
		                				elementLength++;
		                			}
		                		}
		                    	//Create the Quest object
		                		ArrayList<TextureRegion> faceList = facesManager.getFaces(totalfaces, 
		                				ETHNICITY.valueOf(ethnicity.toLowerCase()));
		                		Quest q = new Quest(questName, questElements, elementLength, ethnicity,
		                				reward, penalty, task_type, totalfaces, faceList);
		                		questSequence.add(q);
		            		}
		            	}
					}					
				} catch (SAXParseException err) {
					System.out.println ("** Parsing error" + ", line " 
							+ err.getLineNumber () + ", uri " + err.getSystemId ());
					System.out.println(" " + err.getMessage ());
				}catch (SAXException e) {
					Exception x = e.getException ();
					((x == null) ? e : x).printStackTrace ();
				}catch (Throwable t) {
					t.printStackTrace ();
				}
			}
		}
		return questSequence;
	}
	
	public void dispose(){
		facesManager.dispose();
	}
}