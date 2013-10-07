package facegame.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.badlogic.gdx.utils.DataOutput;
import com.badlogic.gdx.utils.TimeUtils;

import facegame.quests.Quest.TASKTYPE;

public class GameLog {

	private static GameLog log = new GameLog();
	private DataOutput out;
	private AverageStats aveStats;
	private boolean isLogComplete;
	private FileOutputStream fos;
	private String logBuffer = "";
	
	private GameLog(){
		aveStats = new AverageStats();		
		isLogComplete = false;
		
		Date date = new Date(TimeUtils.millis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yy");
		String fileNameDate = sdf.format(date);
		
		logBuffer += logHeader();		
		
		try {
			fos = new FileOutputStream("../Gamelog-"+ fileNameDate +".log");
			//fos.write(logHeader().getBytes(Charset.forName("UTF-8")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} /*catch(IOException e){
			e.printStackTrace();
		}*/
	}
	
	public static GameLog getInstance(){
		return log;
	}
	
	public void writeToLog(QuestLogEntry entry){
		logBuffer += entry.toString();
		/*try{
			fos.write(entry.toString().getBytes(Charset.forName("UTF-8")));
		} catch(IOException e){
			e.printStackTrace();
		}*/
	}
	
	public void writeToLogFinal(){
		if(!isLogComplete){
			isLogComplete = true;
			logBuffer += aveStats.toString();
			/*try{
				fos.write(aveStats.toString().getBytes(Charset.forName("UTF-8")));
			} catch(IOException e){
				e.printStackTrace();
			}*/
		}
	}
	
	private String logHeader(){
		Date date = new Date(TimeUtils.millis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		String formattedDate = sdf.format(date);
		sdf = new SimpleDateFormat("h:mm a");
		String formattedTime = sdf.format(date);
		
		return 		"=====================NEW GAME=====================\r\n"
				+	formattedDate + "\r\n"
				+	formattedTime + "\r\n"
				+	"==================================================\r\n\r\n";
	}
	
	public void writeToFile(){
		try{
			out = new DataOutput(fos);
			out.write(logBuffer.getBytes(Charset.forName("UTF-8")));
		} catch(IOException e){
			e.printStackTrace();
		}		
	}
	
	/**QuestLogEntry class provides a structure for quest data that is to be written
	 * to the log file.
	 * @author laurent
	 */
	public class QuestLogEntry{
		
		private String name, ethnicity, type, questTime, outcome;
		private int totalFaces, reward;
		
		public QuestLogEntry(String name, String ethn, int rwrd, TASKTYPE type,
								int faces, String time, boolean outcome){
			this.name = name;
			this.ethnicity = ethn;
			this.reward = rwrd;
			this.totalFaces = faces;
			this.questTime = time;
			
			if(type.equals(TASKTYPE.newFace))
				this.type = "Identify Novel Face";
			else if(type.equals(TASKTYPE.seenFace))
				this.type = "Identify Familiar Face";
			else
				this.type = "Recognize or Not";
			
			if(outcome)
				this.outcome = "Successful";
			else
				this.outcome = "Unsuccessful";
		}
		
		@Override
		public String toString() {
			return 		"======================QUEST=======================\r\n"
					+ 	" Name: " + name + "\r\n"
					+	" Ethnicity: " + ethnicity + "\r\n"
					+	" Reward type: " + reward + "\r\n"
					+ 	" Task type: " + type + "\r\n"
					+ 	" Faces shown: " + totalFaces + "\r\n"
					+ 	" Time for completion: " + questTime + "\r\n"
					+ 	" Quest outcome: " + outcome + "\r\n\r\n";
		} 
	}
	
	public AverageStats getStatsInstance(){
		return aveStats;
	}
	
	public class AverageStats{
		private int numberOfQuests, completedQuests, successfulQuests, unsuccessfulQuests;
		public void addToQuests(){numberOfQuests++;}
		public void addCompleteQuest(){completedQuests++;}
		public void addToSuccess(){successfulQuests++;}
		public void addToUnsuccessful(){unsuccessfulQuests++;}
		
		private int numberOfNovelFaceQuests, numberOfFamiliarFaceQuests, numberOfRecognizeOrNotQuests;
		public void addToNovelQuests(){numberOfNovelFaceQuests++;}
		public void addToFamilarQuests(){numberOfFamiliarFaceQuests++;}
		public void addToRecognizeQuests(){numberOfRecognizeOrNotQuests++;};
		
		private int whiteMaleFaces, colouredMaleFaces, blackMaleFaces;
		public void addWhiteMale(int count){whiteMaleFaces += count;}
		public void addBlackMale(int count){blackMaleFaces += count;}
		public void addColouredMale(int count){colouredMaleFaces += count;}
		
		protected AverageStats(){
			numberOfQuests = 0;
			completedQuests = 0;
			successfulQuests = 0;
			unsuccessfulQuests = 0;
			numberOfNovelFaceQuests = 0;
			numberOfFamiliarFaceQuests = 0;
			numberOfRecognizeOrNotQuests = 0;
			whiteMaleFaces = 0;
			colouredMaleFaces = 0;
			blackMaleFaces = 0;
		}
		
		@Override
		public String toString(){
			return 		"===================AVERAGE STATS==================\r\n"
					+	"Quests available: " + numberOfQuests + " (" + numberOfFamiliarFaceQuests + " familiar, "
					+ 	numberOfNovelFaceQuests + " novel, " + numberOfRecognizeOrNotQuests + " recognize each)\r\n"
					+	"Quests completed: " + completedQuests + " (" + successfulQuests + " successful, "
					+ 	unsuccessfulQuests + " unsuccessful)\r\n"
					+	"Faces viewed: " + (whiteMaleFaces+colouredMaleFaces+blackMaleFaces) 
					+ 	" (" + whiteMaleFaces + " white, " + blackMaleFaces + " black, " + colouredMaleFaces + " coloured) " + "\r\n"
					+	"==================================================\r\n";
		}
	}
}
