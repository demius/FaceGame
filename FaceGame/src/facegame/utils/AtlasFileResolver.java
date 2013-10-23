package facegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
/**
 * Class to handle loading of external face images, or default images- depending on user selection
 * @author Grant
 *
 */
public class AtlasFileResolver implements FileHandleResolver {

	@Override
	public FileHandle resolve(String fileName) {
		FileHandle localHandle = Gdx.files.local(fileName);
        if (localHandle.exists())
            {
        	return localHandle;
            }
        else{
        	
        	return Gdx.files.internal(fileName);
            
	}
       
	}

}
