package ichikawa.littleInv_Android.GameLogic;

import gameLogic.GameContainer;
import gameLogic.mode.PlayMode;
import gameLogic.mode.Result_ClaerMode;
import gameLogic.mode.Result_GameOverMode;
import gameLogic.mode.StartMode;

/**
 * Android向け実装の、ゲームコンテナ
 * @author n-dolphin
 * @version 1.00 2014/01/17
 */
public class Android_GameContainer extends GameContainer{
	
	/**
	 * コンストラクタ
	 * @param Width 
	 * @param Height 
	 */
	public Android_GameContainer(Integer Width, Integer Height) {
		super(Width, Height, new Android_DrawImplement(Width, Height));
		
		super.addMode(new StartMode());
		super.addMode(new PlayMode());
		super.addMode(new Result_ClaerMode());
		super.addMode(new Result_GameOverMode());
		
		super.changeMode(StartMode.name);
	}
	
	@Override
	public <Canvas> void  update(Canvas panel){
		super.di.updatePanel(panel);
		super.changeMode(super.currentMode.update());
	}

}
