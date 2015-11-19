package com.vali.lib;

public class Tile extends Entity{

	Animation anim;
	public Tile(float XX, float YY, String path, int ID) {
		super(XX, YY, path, 32, 32);
		tag = "Tile";
		anim = addAnimation(tex, new int[]{ID}, 0, false, 32, 32);
		if(ID == 4)
			offsetY = -12;
		playAnimation(anim);
	}

}
