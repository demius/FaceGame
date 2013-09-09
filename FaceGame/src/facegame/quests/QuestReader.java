package facegame.quests;

import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class QuestReader {

	private Vector<Quest> questSequence;

	/**
	 * The constructor of QuestReader initializes the collection of Quests that will be determined from the XML file.
	 */
	public QuestReader() {
		questSequence = new Vector<Quest>();
	}

	/**
	 * @return		The collections of Quests interpreted from the input XML file.
	 */
	public Vector<Quest> readQuests() {
		FileHandle fh = Gdx.files.internal("data/quest_content.xml");

		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse ("bin/" + fh.file());

			// normalize text representation
            NodeList questList = doc.getDocumentElement().getChildNodes();
            
            //Loop through quests creating new Quest obj for each
            for(int i = 0; i < questList.getLength(); ++i) {
            	if(questList.item(i) instanceof Element){
            		Node quest = questList.item(i);
            		String questName = quest.getAttributes().getNamedItem("name").getNodeValue();
            		
            		Vector<QuestElement> questElements = new Vector<QuestElement>();
            		int elementLength = 0;
            		
            		NodeList sequence = quest.getChildNodes();
            		
            		for (int j = 0; j < sequence.getLength(); ++j) {
            			if(sequence.item(j) instanceof Element) {
            				Node seqNode = sequence.item(j);
            				
            				NodeList seqList = seqNode.getChildNodes();
            				
            				for(int k = 0; k < seqList.getLength(); ++k) {
            					if(seqList.item(k) instanceof Element) {
            						Node seqPoint = seqList.item(k);
            						
            						NodeList seqContents = seqPoint.getChildNodes();
            						
            						Vector<String> dialogue = new Vector<String>();
            						int dialogLength = 0;
									String name = "";
            						
            						for(int m = 0; m < seqContents.getLength(); ++m) {
            							if(seqContents.item(m) instanceof Element) {
        									        									
            								//Specifies the name of the NPC
            								if(seqContents.item(m).getNodeName().equals("npc")) {
            									name = seqContents.item(m).getTextContent();
            								}
            								//Specifies the dialogue sequence
            								else {
            									Node dialogSeq = seqContents.item(m);
            									
            									NodeList dialogList = dialogSeq.getChildNodes();
            									
            									for(int n = 0; n < dialogList.getLength(); ++n) {
            										//The NPC's quest dialogue sequence
            										if(dialogList.item(n) instanceof Element) {
            											Node dialogElement = dialogList.item(n);
            											
            											String dialogText = dialogElement.getTextContent();
            											dialogue.add(dialogText);
            											dialogLength++;
            										}
            									}
            								}            								
            							}            							
            						}
            						//Create the QuestElement containing the NPC and Dialogue sequence
            						QuestElement qe = new QuestElement(name, dialogue, dialogLength);
        							questElements.add(qe);
        							elementLength++;
            					}
            				}
            			}
            		}
                	//Create the Quest object
            		Quest q = new Quest(questName, questElements, elementLength);
            		questSequence.add(q);
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

		return questSequence;
	}
}