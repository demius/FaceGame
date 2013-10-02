package facegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class AtlasFileResolver implements FileHandleResolver {

	@Override
	public FileHandle resolve(String fileName) {
		return Gdx.files.local(fileName);
	}

}
