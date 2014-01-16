package ichikawa.littleInv_Android.GameLogic;

import gameLogic.DrawImplement.DrawImplementIF;
import gameLogic.mode.Bean.ButtonBean;
import gameLogic.mode.Bean.FieldConfigBean;
import gameLogic.mode.Bean.TextBean;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;

/**
 * Android依存コードの実装
 * @author n-dolphin
 * @version 1.00 2014/01/17
 */
public class Android_DrawImplement implements DrawImplementIF {

	/**
	 * 描画対象
	 */
	private  Canvas g = null;

	/**
	 * 描画オプション
	 */
	Paint p = new Paint();
	
	/**
	 *全モードで共有される設定情報 
	 */
	FieldConfigBean fc = null;
	
	/**
	 * 実際のスクリーンの幅
	 */
	Integer realScreenWidth  ;
	
	/**
	 * 実際のスクリーンの高さ
	 */
	Integer realScreenHeight  ;
	
	/**
	 * コンストラクタ
	 * @param realScreenWidth 実際のスクリーンの幅
	 * @param realScreenHeight 実際のスクリーンの高さ
	 */
	public Android_DrawImplement(Integer realScreenWidth ,Integer realScreenHeight){
		this.realScreenWidth = realScreenWidth;
		this.realScreenHeight = realScreenHeight;
	}
	

	@Override
	public void setConfig(FieldConfigBean FC){
		this.fc = FC;					
	}
	
	@SuppressWarnings("hiding")
	@Override
	public  <Canvas> void updatePanel(Canvas panel) {
		this.g = (android.graphics.Canvas) panel;
	}
	
	@Override
	public void setBackground() {
		this.g.drawColor(Color.BLACK);	
	}
	
	@Override
	public void drawMyPlane(Integer x, Integer y) {	
	    int xPoints[] = {x, x+fc.myWidth/2,x-fc.myWidth/2};
	    int yPoints[] = {y, y+fc.myHeight/2, y+fc.myHeight/2};

	   p.setColor(Color.WHITE);
	   drawPoly(g,xPoints,yPoints,p);
	}
	

	@Override
	public void drawMyMissile(Boolean isMyMissileActive,Integer x,Integer y) {
		if(isMyMissileActive){
			p.setColor(Color.WHITE);
			g.drawRect(x, y,x+fc.missileWidth, y+fc.missileHeight,p);
		}
	}

	@Override
	public void drawEnemyPlane(Boolean[] isEnemyAlive,Integer[] x,Integer[] y) {
		
		for (int i = 0; i < fc.numOfEnemy; i++) {
			// ミサイルの配置
			if (isEnemyAlive[i]) {
				int xPoints[] = {x[i], x[i]+fc.enemyWidth/2,x[i]-fc.enemyWidth/2};
			    int yPoints[] = {y[i], y[i]-fc.enemyHeight/2, y[i]-fc.enemyHeight/2};
			    
				p.setColor(Color.RED);
				drawPoly(g,xPoints,yPoints,p);
			
			}
		}

	}

	@Override
	public void drawEnemyMissile(Boolean[] isEnemyMissileActive,Integer[] x,Integer[] y) {
		for (int i = 0; i < fc.numOfEnemy; i++) {
			// ミサイルの配置
			if (isEnemyMissileActive[i]) {
				p.setColor(Color.RED);
				g.drawRect(x[i], y[i],x[i]+fc.missileWidth, y[i]+fc.missileHeight,p);
			}
		}
	}

	@Override
	public void drawButton(ButtonBean Button) {

		insideOf_DrawText(Button);		
		p.setStyle(Style.STROKE);
		g.drawRect(Button.getCollisionAreaX(),Button.getCollisionAreaY(),Button.getCollisionAreaX()+ (int)(Button.getCollisionAreaWidth()*0.8), Button.getCollisionAreaY()+Button.getCollisionAreaHeight(),p);
	}

	@Override
	public void drawText(TextBean Text) {
		insideOf_DrawText(Text);
	}
	
	private void insideOf_DrawText(TextBean Bean){
	
		p.setColor(Color.argb(Bean.getColorAlpha(),Bean.getColorR(),Bean.getColorG(),Bean.getColorB()));
		
		p.setAntiAlias(true);
		p.setTextSize(Bean.getFontSize());
		
		g.drawText(Bean.getText(), Bean.getX() , Bean.getY(),p);
	}

	/**
	 * polygonを描画
	 * @param canvas 
	 * @param Xpoints 
	 * @param Ypoints 
	 * @param paint 
	 */
	private void drawPoly(Canvas canvas, int[] Xpoints,int[] Ypoints,Paint paint) {
	    // line at minimum...
	    if (Xpoints.length != Ypoints.length || Xpoints.length < 2) {
	        return;
	    }

	    // paint	
	    paint.setStyle(Style.FILL);

	    // path

	    Path polyPath = new Path();
	 
	    polyPath.moveTo(Xpoints[0], Ypoints[0]);
	    
	    for (int i = 0; i < Xpoints.length; i++) {
	        polyPath.lineTo(Xpoints[i], Ypoints[i]);
	    }
	    polyPath.lineTo(Xpoints[0], Ypoints[0]);

	    // draw
	    canvas.drawPath(polyPath, paint);
	}
}
