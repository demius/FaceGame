package facegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

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
