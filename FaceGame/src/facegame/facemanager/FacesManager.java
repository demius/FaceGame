package facegame.facemanager;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import facegame.main.TextureChooser;
import facegame.utils.NewAssetManager;


/**
 * Created with IntelliJ IDEA.
 * User: Grant
 * Date: 2013/08/31
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class FacesManager {
    //number of faces of each race
    private int NUM_WHITE;
    private int NUM_BLACK;
    private int NUM_COLOURED;

    public enum HOMOGENEITY{homogeneous,heterogeneous};
    public enum ETHNICITY{white,black,coloured}; //add more ethnicities as we need them

    private TextureAtlas white_textures;
    private int white_textures_index=1;
    //private TextureAtlas white_normal;
    //private int white_normal_index=1;
    //private TextureAtlas white_heterogeneous;
    //private int white_heterogeneous_index=1;

   // private TextureAtlas black_homogeneous;
    //private int black_homogeneous_index=1;

    private TextureAtlas black_textures;
    private int black_textures_index=1;
    
    private TextureAtlas coloured_textures;
    private int coloured_textures_index=1;

    ArrayList<TextureRegion> faces;

    /**
     * Constructor initializes and loads all texture atlas's- three for each ethnicity, 1 for each spread of homogeneity
     */
    public FacesManager()
    {
    	NewAssetManager assetManager = NewAssetManager.getInstance();
    	
        faces=new ArrayList<TextureRegion>();

        //white_homogeneous= new TextureAtlas(Gdx.files.internal("Faces/White/white_male_homogeneous.txt"));
        //white_normal= new TextureAtlas(Gdx.files.internal("Faces/white/Normal/white_normal.txt"));
        //white_heterogeneous= new TextureAtlas(Gdx.files.internal("Faces/White/white_male_heterogeneous.txt"));

        //black_heterogeneous= new TextureAtlas(Gdx.files.internal("Faces/Black/black_male_heterogeneous.txt"));
        //black_homogeneous=new TextureAtlas(Gdx.files.internal("Faces/Black/black_male_homogeneous.txt"));
        if(TextureChooser.getWhitePath()!=null)
        white_textures = assetManager.get("Faces/White/white_male_textures.atlas");
        else
        	white_textures=assetManager.get("Faces/White/white_male_default.txt");
        NUM_WHITE=white_textures.getRegions().size;
        System.out.println("NumWhite: "+NUM_WHITE);
        
        if(TextureChooser.getBlackPath()!=null)
            black_textures = assetManager.get("Faces/Black/black_male_textures.atlas");
            else
            	black_textures=assetManager.get("Faces/Black/black_male_default.txt");
        NUM_BLACK=black_textures.getRegions().size;
        
        if(TextureChooser.getColouredPath()!=null)
            coloured_textures = assetManager.get("Faces/Coloured/coloured_male_textures.atlas");
            else
            	coloured_textures=assetManager.get("Faces/Coloured/coloured_male_default.txt");
        NUM_COLOURED=coloured_textures.getRegions().size;
               
    }

    /**
     * Request faces from the loaded TextureAtlases. This method will attempt to always deliver previously unused faces
     * by maintaining indices of used faces. However, there are a finite amount of faces- so if requests have exceeded
     * total available faces then they will begin to be reused.
     * @param number number of faces desired
     * @param ethnicity ethnicity of faces
     * @param homogeneity homogeneity of faces
     * @return An array of textureRegions containing the faces
     */
    public ArrayList<TextureRegion> getFaces(int number,ETHNICITY ethnicity,HOMOGENEITY homogeneity)
    {
        faces.clear();

        if(ethnicity==ETHNICITY.white)
        {
            
                for(int i=0;i<number;i++)
                {   if(white_textures_index>NUM_WHITE)
                    white_textures_index=1; //start reusing faces

                    faces.add(white_textures.findRegion("faceTex",white_textures_index));
                    white_textures_index++;
                }

         }
         
        else if(ethnicity==ETHNICITY.black){
                    for(int i=0;i<number;i++)
                    {
                        if(black_textures_index>NUM_BLACK)
                            black_textures_index=1; //have to start reusing faces
                       // System.out.println("Adding black face num: "+black_heterogeneous_index);
                        faces.add(black_textures.findRegion("faceTex",black_textures_index));
                        black_textures_index++;
                    }
                }
                
        else if(ethnicity==ETHNICITY.coloured){
                    for(int i=0;i<number;i++)
                    {
                        if(coloured_textures_index>NUM_COLOURED)
                            coloured_textures_index=1; //have to start reusing faces
                       // System.out.println("Adding black face num: "+black_heterogeneous_index);
                        faces.add(coloured_textures.findRegion("faceTex",coloured_textures_index));
                        coloured_textures_index++;
                    }
                }
              
        //extend for other ethnicities
        Collections.shuffle(faces);
        
        return faces;

    }

    /**
     * Returns an array of all previously used faces
     * @param ethnicity
     * @param homogeneity
     * @return
     */
    public ArrayList<TextureRegion> getUsedFaces(ETHNICITY ethnicity, HOMOGENEITY homogeneity)
    {
        faces.clear();

        if(ethnicity==ETHNICITY.white)
        {
                  for(int i=0;i<white_textures_index;i++) {
                      faces.add(white_textures.findRegion("faceTex",i+1));
                  }
              }
                
        else if(ethnicity==ETHNICITY.black)
        {
                        for(int i=0;i<black_textures_index;i++) {
                        faces.add(black_textures.findRegion("faceTex",i+1));
                    }
         }
        else if(ethnicity==ETHNICITY.coloured)
        {
        	 for(int i=0;i<coloured_textures_index;i++) {
                 faces.add(coloured_textures.findRegion("faceTex",i+1));
             }
        	
        }
               
        //extend for other ethnicities

        return faces;
    }

    /**
     * Clears the indices keeping track of used faces.
     * @param ethnicity
     * @param homogeneity
     */
    public void clearUsedFaces(ETHNICITY ethnicity, HOMOGENEITY homogeneity)
    {
        if(ethnicity==ETHNICITY.white)
        {
               white_textures_index=1;
                }
               
        else if(ethnicity==ETHNICITY.black)
        {
                black_textures_index=1;
                }
        else if(ethnicity==ETHNICITY.coloured){
        	coloured_textures_index=1;
        }
        //extend for other ethnicities
    }
    
    public void dispose(){

    	white_textures.dispose();
    	black_textures.dispose();
    	coloured_textures.dispose();

    	    	
    	NewAssetManager assetManager = NewAssetManager.getInstance();
    	 if(TextureChooser.getWhitePath()!=null)
    	       assetManager.remove("Faces/White/white_male_textures.atlas");
    	        else
    	        	assetManager.remove("Faces/White/white_male_default.txt");
    	        
    	        if(TextureChooser.getBlackPath()!=null)
    	            assetManager.remove("Faces/Black/black_male_textures.atlas");
    	            else
    	            	assetManager.remove("Faces/Black/black_male_default.txt");
    	        
    	        if(TextureChooser.getColouredPath()!=null)
    	            assetManager.remove("Faces/Coloured/coloured_male_textures.atlas");
    	            else
    	            	assetManager.remove("Faces/Coloured/coloured_male_default.txt");
    	
    	
    }

}
