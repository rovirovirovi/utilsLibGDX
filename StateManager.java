package com.vali.lib;

import java.util.Stack;

import com.vali.game.MyGdxGame;

public class StateManager {

	public static Stack<State> states = new Stack<State>();
	
	MyGdxGame mainClass;
	public StateManager(MyGdxGame mainClass){
		this.mainClass = mainClass;
	}
	
	public void loadState(State s){
		s.stateManager = this;
		if(states.isEmpty()){
			states.add(s);
		}
		else{
			states.pop();
			states.add(s);
		}
		if(!(s instanceof EmptyState))
			mainClass.changedState();
	}
	public static State getState(){
		if(!states.isEmpty())
			return states.peek();
		return null;
	}
	public void resetState(){
		State s;
		s = states.peek();
		loadState(s);
	}
	public void update(){
			if(!states.isEmpty())
				states.peek().update();
			if(!states.isEmpty())
				states.peek().render();
	}
	public void resize(int width, int height){
		if(!states.isEmpty())
			states.peek().resize(width, height);
	}
	
}
