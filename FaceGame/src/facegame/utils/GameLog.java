package facegame.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.utils.DataOutput;
import com.badlogic.gdx.utils.TimeUtils;

public class GameLog {

	private static GameLog log = new GameLog();
	private DataOutput out;
	private AverageStats aveStats;
	private boolean isLogComplete;
	
	private GameLog(){
		aveStats = new AverageStats();
		
		isLogComplete = false;
		
		try {
			Date date = new Date(TimeUtils.millis());
			SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yy");
			String fileNameDate = sdf.format(date);
			out = new DataOutput(new FileOutputStream("../Gamelog-"+ fileNameDate +".log"));
			out.write(logHeader().getBytes(Charset.forName("UTF-8")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static GameLog getInstance(){
		return log;
	}
	
	public void writeToLog(QuestLogEntry entry){
		try{
			out.write(entry.toString().getBytes(Charset.forName("UTF-8")));
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void writeToLogFinal(){
		if(!isLogComplete){
			isLogComplete = true;
		
			try{
				out.write(aveStats.toString().getBytes(Charset.forName("UTF-8")));
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	private String logHeader(){
		Date date = new Date(TimeUtils.millis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		String formattedDate = sdf.format(date);
		sdf = new SimpleDateFormat("h:mm a");
		String formattedTime = sdf.format(date);
		
		return 		"=====================NEW GAME=====================\n"
				+	formattedDate + "\n"
				+	formattedTime + "\n"
				+	"==================================================\n\n";
	}
	
	/**QuestLogEntry class provides a structure for quest data that is to be written
	 * to the log file.
	 * @author laurent
	 */
	public class QuestLogEntry{
		
		private String name, ethnicity, homogeneity, reward, type, questTime;
		private int totalFaces;
		
		public QuestLogEntry(String name, String ethn, String homog, String rwrd, String type,
				int faces, String time){
			this.name = name;
			this.ethnicity = ethn;
			this.homogeneity = homog;
			this.reward = rwrd;
			this.type = type;
			this.totalFaces = faces;
			this.questTime = time;
		}
		
		@Override
		public String toString() {
			return 		"======================QUEST=======================\n"
					+ 	" Name: " + name + "\n"
					+	" Ethnicity: " + ethnicity + "\n"
					+ 	" Homogeneity: " + homogeneity + "\n"
					+	" Reward type: " + reward + "\n"
					+ 	" Task type: " + type + "\n"
					+ 	" Faces shown: " + totalFaces + "\n"
					+ 	" Time for completion: " + questTime + "\n\n";
		} 
	}
	
	public AverageStats getStatsInstance(){
		return aveStats;
	}
	
	public class AverageStats{
		private int numberOfQuests;
		public void addToQuests(){numberOfQuests++;}
		
		private int numberOfNovelFaceQuests, numberOfFamiliarFaceQuests;
		public void addToNovelQuests(){numberOfNovelFaceQuests++;}
		public void addToFamilarQuests(){numberOfFamiliarFaceQuests++;}
		
		private int whiteMaleFaces, colouredMaleFaces, blackMaleFaces;
		public void addWhiteMale(int count){whiteMaleFaces += count;}
		public void addBlackMale(int count){blackMaleFaces += count;}
		public void addColouredMale(int count){colouredMaleFaces += count;}
		
		protected AverageStats(){
			numberOfQuests = 0;
			numberOfNovelFaceQuests = 0;
			numberOfFamiliarFaceQuests = 0;
			whiteMaleFaces = 0;
			colouredMaleFaces = 0;
			blackMaleFaces = 0;
		}
		
		@Override
		public String toString(){
			return 		"===================AVERAGE STATS====================\n"
					+	"Quests attempted: " + numberOfQuests + " (" + numberOfFamiliarFaceQuests + " familiar, "
					+ 	numberOfNovelFaceQuests + " novel)\n"
					+	"Faces viewed: " + (whiteMaleFaces+colouredMaleFaces+blackMaleFaces) 
					+ 	" (" + whiteMaleFaces + " white, " + blackMaleFaces + " black, " + colouredMaleFaces + " coloured) " + "\n"
					+	"====================================================\n";
		}
	}
}
