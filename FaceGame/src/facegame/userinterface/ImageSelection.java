package facegame.userinterface;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ImageSelection {
	private Image faceImage;
	private boolean faceSeen;
	private CheckBox seenCheckBox, notSeenCheckBox;
	private Table table;
	private boolean isOneChecked;	
	public boolean isOneChecked(){return isOneChecked;}
	
	public ImageSelection(FaceWrapper face){
		TextureAtlas atlas = new TextureAtlas("dialog/dialog.pack");
		Skin skin = new Skin(Gdx.files.internal("dialog/dialogSkin.json"), atlas);
		
		table = new Table(skin);
		
		faceImage = new Image(face.getSpriteDrawable());
		seenCheckBox = new CheckBox("Seen", skin);
		notSeenCheckBox = new CheckBox("Not Seen", skin);
		
		isOneChecked = false;
		faceSeen = face.getSeen();
	}
	
	public ArrayList<Actor> getActors(){
		ArrayList<Actor> list = new ArrayList<Actor>();
		list.add(seenCheckBox);
		list.add(notSeenCheckBox);
		list.add(faceImage);
		return list;
	}
	
	public Table setBounds(float x, float y, float width, float height, float checkBoxHeight){
		
		
		
		table.setBounds(x, y, width, height + checkBoxHeight);
		table.add(faceImage);
		table.row();
		table.add(seenCheckBox).align(Align.left);
		table.row();
		table.add(notSeenCheckBox).align(Align.left);
		table.row();
		
		ClickListener clickListener = new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				CheckBox cb = ((CheckBox)event.getListenerActor());
				//Toggle so only one box can be checked
				if(cb.equals(notSeenCheckBox)){
					if(seenCheckBox.isChecked())
						seenCheckBox.setChecked(false);
				}
				else if(cb.equals(seenCheckBox)){
					if(notSeenCheckBox.isChecked())
						notSeenCheckBox.setChecked(false);
				}
				
				//Set the flag to true if one of the boxes is checked
				if(seenCheckBox.isChecked() || notSeenCheckBox.isChecked())
					isOneChecked = true;
				else
					isOneChecked = false;
			}	
		};
		
		seenCheckBox.addListener(clickListener);
		seenCheckBox.setChecked(false);
		notSeenCheckBox.addListener(clickListener);
		notSeenCheckBox.setChecked(false);
		
		table.debug();
		return table;
	}

	/*public void setBounds(float x, float y, float width, float height, float checkBoxHeight){
		
		faceImage.setBounds(x, y, width, height);
		seenCheckBox.setBounds(x, y-checkBoxHeight/2, width, checkBoxHeight/2);
		seenCheckBox.setBounds(x, y-checkBoxHeight, width, checkBoxHeight/2);
		
		ClickListener clickListener = new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				CheckBox cb = ((CheckBox)event.getListenerActor());
				//Toggle so only one box can be checked
				if(cb.equals(notSeenCheckBox)){
					if(seenCheckBox.isChecked())
						seenCheckBox.setChecked(false);
				}
				else if(cb.equals(seenCheckBox)){
					if(notSeenCheckBox.isChecked())
						notSeenCheckBox.setChecked(false);
				}
				
				//Set the flag to true if one of the boxes is checked
				if(seenCheckBox.isChecked() || notSeenCheckBox.isChecked())
					isOneChecked = true;
				else
					isOneChecked = false;
			}	
		};
		
		seenCheckBox.addListener(clickListener);
		seenCheckBox.setChecked(false);
		notSeenCheckBox.addListener(clickListener);
		notSeenCheckBox.setChecked(false);
		
	}*/
	
	public boolean checkSelection(){
		if(seenCheckBox.isChecked() && faceSeen)
			return true;
		if(notSeenCheckBox.isChecked() && !faceSeen)
			return true;
		
		return false;
			
	}

	public float getX() {
		return table.getX();
	}

	public float getWidth() {
		return table.getWidth();
	}

	public void setX(float f) {
		table.setX(f);
	}
}
