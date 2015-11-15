package com.vali.game;

import java.util.Stack;

public class StateManager {

	public Stack<State> states = new Stack<State>();
	
	public StateManager(){
		if(states.isEmpty())
			states.add(new Playstate());
	}
	public void loadState(State s){
		states.pop();
		states.add(s);
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
			states.peek().draw();
	}
	
	
}
