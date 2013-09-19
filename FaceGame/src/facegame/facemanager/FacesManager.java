package facegame.facemanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: Grant
 * Date: 2013/08/31
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class FacesManager {

    final static int FACES_PER_ATLAS=50;

    public enum HOMOGENEITY{homogeneous,heterogeneous};
    public enum ETHNICITY{white,black}; //add more ethnicities as we need them

    private TextureAtlas white_homogeneous;
    private int white_homogeneous_index=1;
    //private TextureAtlas white_normal;
    //private int white_normal_index=1;
    private TextureAtlas white_heterogeneous;
    private int white_heterogeneous_index=1;

    private TextureAtlas black_homogeneous;
    private int black_homogeneous_index=1;

    private TextureAtlas black_heterogeneous;
    private int black_heterogeneous_index=1;

    ArrayList<TextureRegion> faces;

    /**
     * Constructor initializes and loads all texture atlas's- three for each ethnicity, 1 for each spread of homogeneity
     */
    public FacesManager()
    {
        faces=new ArrayList<TextureRegion>();

        white_homogeneous= new TextureAtlas(Gdx.files.internal("Faces/White/white_male_homogeneous.txt"));
        //white_normal= new TextureAtlas(Gdx.files.internal("Faces/white/Normal/white_normal.txt"));
        white_heterogeneous= new TextureAtlas(Gdx.files.internal("Faces/White/white_male_heterogeneous.txt"));

        black_heterogeneous= new TextureAtlas(Gdx.files.internal("Faces/Black/black_male_heterogeneous.txt"));
        black_homogeneous=new TextureAtlas(Gdx.files.internal("Faces/Black/black_male_homogeneous.txt"));
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
            switch(homogeneity){
            case heterogeneous:{
                for(int i=0;i<number;i++)
                {
                 if(white_heterogeneous_index>FACES_PER_ATLAS)
                     white_heterogeneous_index=1; //have to start reusing faces

                 faces.add(white_heterogeneous.findRegion("het",white_heterogeneous_index));
                 white_heterogeneous_index++;
                }
            }
            break;
            case homogeneous: {
                for(int i=0;i<number;i++)
                {   if(white_homogeneous_index>FACES_PER_ATLAS)
                    white_homogeneous_index=1; //start reusing faces

                    faces.add(white_homogeneous.findRegion("homo",white_homogeneous_index));
                    white_homogeneous_index++;
                }

            }
            break;
           /* case normal: {
                for(int i=0;i<number;i++)
                {
                    if(white_normal_index>FACES_PER_ATLAS)
                        white_normal_index=1;//start reusing faces

                    faces.add(white_normal.findRegion("nor",white_normal_index));
                    white_normal_index++;
                }
            }    */


        }
        }
        else if(ethnicity==ETHNICITY.black){
            switch(homogeneity){
                case heterogeneous:{
                    for(int i=0;i<number;i++)
                    {
                        if(black_heterogeneous_index>FACES_PER_ATLAS)
                            black_heterogeneous_index=1; //have to start reusing faces
                       // System.out.println("Adding black face num: "+black_heterogeneous_index);
                        faces.add(black_heterogeneous.findRegion("het",black_heterogeneous_index));
                        black_heterogeneous_index++;
                    }
                }
                break;
                case homogeneous: {
                    for(int i=0;i<number;i++)
                    {   if(black_homogeneous_index>FACES_PER_ATLAS)
                        black_homogeneous_index=1; //start reusing faces

                        faces.add(black_homogeneous.findRegion("homo",black_homogeneous_index));
                        black_homogeneous_index++;
                    }

                }
                break;
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
            switch(homogeneity){
              case homogeneous:{
                  for(int i=0;i<white_homogeneous_index;i++) {
                      faces.add(white_homogeneous.findRegion("homo",i+1));
                  }
              }
                break;
                case heterogeneous: {
                    for(int i=0;i<white_heterogeneous_index;i++) {
                        faces.add(white_heterogeneous.findRegion("het",i+1));
                    }
                }
                break;
                /*case normal: {
                    for(int i=0;i<white_normal_index;i++) {
                        faces.add(white_normal.findRegion("nor",i+1));
                    }
                }  */
            }
        }
        else if(ethnicity==ETHNICITY.black)
        {
            switch(homogeneity){
                case homogeneous:{
                    for(int i=0;i<black_homogeneous_index;i++) {
                        faces.add(black_homogeneous.findRegion("homo",i+1));
                    }
                }
                break;
                case heterogeneous: {
                    for(int i=0;i<black_heterogeneous_index;i++) {
                        faces.add(black_heterogeneous.findRegion("het",i+1));
                    }
                }
                break;
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
            switch(homogeneity){
                case heterogeneous:{
                    white_heterogeneous_index=1;
                }
                break;
                case homogeneous: {
                    white_homogeneous_index=1;
                }
                break;
                /*case normal: {
                   white_normal_index=1;
                } */
            }
        }
        else if(ethnicity==ETHNICITY.black)
        {
            switch(homogeneity){
                case heterogeneous:{
                    black_heterogeneous_index=1;
                }
                break;
                case homogeneous: {
                    black_homogeneous_index=1;
                }
                break;
                /*case normal: {
                   white_normal_index=1;
                } */
            }

        }
        //extend for other ethnicities
    }
    
    public void dispose(){
    	white_homogeneous.dispose();
    	white_heterogeneous.dispose();
    	black_homogeneous.dispose();
    	black_heterogeneous.dispose();
    }

}
