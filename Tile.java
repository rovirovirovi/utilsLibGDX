package com.vali.lib;

public class Tile extends Entity{

	Animation anim;
	public Tile(float XX, float YY, String path, int ID) {
		super(XX, YY, path, 16, 16);
		anim = addAnimation(tex, new int[]{ID}, 0, false, 16, 16);
		playAnimation(anim);
	}

}
