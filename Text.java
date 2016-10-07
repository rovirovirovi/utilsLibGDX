package com.vali.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Text {

	FreeTypeFontGenerator gen;
	FreeTypeFontParameter par;
	public BitmapFont font;
	public String text = "";
	String font_path;
	public GlyphLayout glyphLayout;
	public float x,y, scale = 1, rotation = 0;
	boolean flipX, flipY;
	public float colR = 1f, colG = 1f, colB = 1f, colA = 1f;
	private boolean inLerpPos = false, inLerpRot = false;
	protected boolean inLerpCol = false;
	private float lerpPosTargetX, lerpPosTargetY, lerpRotTarget, lerpPosTime, lerpRotTime, lerpColTargetR, lerpColTargetG, lerpColTargetB, lerpColTargetA, lerpColTime;
	private Callback colCallback, posCallback, rotCallback, writeCallback;
	public boolean autoWrite = false;
	String autoWriteText = "";
	public float autoWriteSpeed = .1f;
	float autoWriteTimer = 0f;
	int autoWritePos = 0;
	
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
	public void autoWrite(boolean value, float speed, Callback callback){
		autoWrite = value;
		autoWritePos = 0;
		autoWriteSpeed = speed;
		writeCallback = callback;
		autoWriteText="";
	}
	public void setColor(float r, float g, float b, float a){
		this.colR = r;
		this.colG = g;
		this.colB = b;
		this.colA = a;
		font.setColor(r, g, b, a);
	}
	
	public void lerpPos(float targetX, float targetY, float time, Callback cb){
		inLerpPos = true;
		lerpPosTargetX = targetX;
		lerpPosTargetY = targetY;
		lerpPosTime = time;
		posCallback = cb;
	}
	public void lerpRot(float targetRot, float time, Callback cb){
		inLerpPos = true;
		lerpRotTarget = targetRot;
		lerpRotTime = time;
		rotCallback = cb;
	}
	public void lerpColor(float tr, float tg, float tb, float ta, float time, Callback cb){
		this.lerpColTargetR = tr;
		this.lerpColTargetG = tg;
		this.lerpColTargetB = tb;
		this.lerpColTargetA = ta;
		this.lerpColTime = time;
		inLerpCol = true;
		colCallback = cb;
	}
	
	public void update(){
		handleLerp();
		handleAutoWrite();
	}
	
	public void handleAutoWrite(){
		if(autoWrite){
			autoWriteTimer += Gdx.graphics.getDeltaTime();
			if(autoWriteTimer >= autoWriteSpeed){
				autoWriteText += text.charAt(autoWritePos);
				autoWriteTimer = 0;
				if(autoWritePos < text.length()-1)
					autoWritePos++;
				else
				{
					autoWrite = false;
					if(writeCallback != null)
						writeCallback.callback();
				}
			}	
		}
	}
	
	protected void handleLerp(){
		if(inLerpPos){
			x = Utils.lerp(x, lerpPosTargetX, lerpPosTime * Gdx.graphics.getDeltaTime());
			y = Utils.lerp(y, lerpPosTargetY, lerpPosTime * Gdx.graphics.getDeltaTime());
			if(x == lerpPosTargetX && y == lerpPosTargetY){
				inLerpPos = false;
				if(posCallback != null)
					posCallback.callback();
			}
		}
		if(inLerpRot){
			rotation = Utils.LerpDegrees(rotation, lerpRotTarget, lerpRotTime * Gdx.graphics.getDeltaTime());
			if(rotation == lerpRotTarget)
			{
				inLerpRot = false;
				if(rotCallback != null)
					rotCallback.callback();
			}
		}
		if(inLerpCol){
			colR = Utils.lerp(colR, lerpColTargetR, lerpColTime * Gdx.graphics.getDeltaTime());
			colG = Utils.lerp(colG, lerpColTargetG, lerpColTime * Gdx.graphics.getDeltaTime());
			colB = Utils.lerp(colB, lerpColTargetB, lerpColTime * Gdx.graphics.getDeltaTime());
			colA = Utils.lerp(colA, lerpColTargetA, lerpColTime * Gdx.graphics.getDeltaTime());
			
			if(colR == lerpColTargetR && colG == lerpColTargetG && colB == lerpColTargetB && ((lerpColTargetA > colA && colA > lerpColTargetA - .005f) || (lerpColTargetA < colA && colA < lerpColTargetA + .005f))){
				inLerpCol = false;
				if(colCallback != null)
					colCallback.callback();
			}
		}
	}
	public void setPosCallback(Callback cb){
		this.posCallback = cb;
	}
	public void setRotCallback(Callback cb){
		this.rotCallback = cb;
	}
	public void setColCallback(Callback cb){
		this.colCallback = cb;
	}
	public void draw(SpriteBatch sb){
		drawSelf(sb);
	}
	protected void drawSelf(SpriteBatch sb){
		sb.setColor(colR, colG, colB, colA);
		if(!autoWrite){
			if(!center)
				font.draw(sb,text, x, y);
			else
				font.draw(sb,text, x - (glyphLayout.width / 2) * scale, y);
		}
		else
		{
			if(!center)
				font.draw(sb,autoWriteText, x, y);
			else
				font.draw(sb,autoWriteText, x - (glyphLayout.width / 2) * scale, y);
		}
		sb.setColor(Color.WHITE);
	}
	public void SetText(String Text){
		text = Text;
		glyphLayout = new GlyphLayout(font, text);
	}
	public void SetScale(float scale){
		font.getData().setScale(scale);
		this.scale = scale;
	}
	
}
