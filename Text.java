package com.vali.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Text {

	public float x, y;
	FreeTypeFontGenerator gen;
	FreeTypeFontParameter par;
	public BitmapFont font;
	public String text = "";
	String font_path;
	public GlyphLayout glyphLayout;
	
	public boolean center = false;
	public Text(float XX, float YY, int size, String text, String font_path){
		x = XX;
		y = YY;
		this.font_path = font_path;
		gen = new FreeTypeFontGenerator(Gdx.files.internal(font_path));
		par = new FreeTypeFontParameter();
		par.size = size;
		font = gen.generateFont(par);
		glyphLayout = new GlyphLayout(font, text);
		this.text = text;
	}
	public void setPos(float x, float y){
		this.x = x;
		this.y = y;
	}
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public void render(SpriteBatch sb){
		if(!center)
			font.draw(sb,text, x, y);
		else
			font.draw(sb,text, x - glyphLayout.width / 2, y);
	}
	public void SetText(String Text){
		text = Text;
		glyphLayout = new GlyphLayout(font, text);
	}
	public void SetScale(float scale){
		font.getData().setScale(scale);
	}
	
}
