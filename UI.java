package com.vali.lib;

import java.util.Stack;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UI implements InputProcessor{

	public Stack<Button> buttonStack;
	public Stack<Panel> panelStack;
	public Stack<Gauge> gaugeStack;
	public Cursor cursor;
	
	
	public UI(Cursor cursor){
		this.cursor = cursor;
		buttonStack = new Stack<Button>();
		panelStack = new Stack<Panel>();
		gaugeStack = new Stack<Gauge>();
	}
	
	public void addButton(Button button)
	{
		buttonStack.add(button);
	}
	public void addPanel(Panel panel)
	{
		panelStack.add(panel);
	}
	public void addGauge(Gauge gauge)
	{
		gaugeStack.add(gauge);
	}
	public boolean cursorOnButton(Button button){
		if(button.enabled)
		{
			float cx = cursor.x + cursor.tex.getWidth() / 2;
			float cy = cursor.y + cursor.tex.getHeight() / 2;
			
			if(cx > button.x && cx < button.x + button.tex.getWidth() &&
			   cy > button.y && cy < button.y + button.tex.getHeight())
				return true;
		}
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(button == Buttons.LEFT)
		{
			for(int i = 0; i < buttonStack.size(); i++){
				if(cursorOnButton(buttonStack.get(i))){
					buttonStack.get(i).press();
				}
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(button == Buttons.LEFT)
			for(int i = 0; i < buttonStack.size(); i++){
				if(buttonStack.get(i).pressed){
					buttonStack.get(i).unpress();
				}
			}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		for(int i = 0; i < buttonStack.size(); i++){
			if(!cursorOnButton(buttonStack.get(i))){
				buttonStack.get(i).pressed = false;
			}
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void render(SpriteBatch sb){
		for(int i = 0; i < buttonStack.size(); i++){
			buttonStack.get(i).draw(sb);
		}
		for(int i = 0; i < panelStack.size(); i++){
			panelStack.get(i).draw(sb);
		}
		for(int i = 0; i < gaugeStack.size(); i++){
			gaugeStack.get(i).draw(sb);
		}
	}
	
	public void update(){
		for(int i = 0; i < buttonStack.size(); i++){
			buttonStack.get(i).update();
		}
		for(int i = 0; i < panelStack.size(); i++){
			panelStack.get(i).update();
		}
		for(int i = 0; i < gaugeStack.size(); i++){
			gaugeStack.get(i).update();
		}

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
}
