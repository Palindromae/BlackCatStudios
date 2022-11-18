package com.mygdx.game;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.BlackCore.*;
import com.mygdx.game.BlackScripts.BasicCharacterController;

public class MyGdxGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	BTexture texture;
	GameObjectHandler gameObjectHandler;

	BlackScriptManager ScriptManager;

	GameObject obj;

	BatchDrawer batch;
	
	@Override
	public void create () {
		batch = new BatchDrawer();
		try {
			ScriptManager = new BlackScriptManager(); // this exception shouldnt happen but just incase
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Rectangle rect = new Rectangle();
		rect.x = 800/2 - 64/2;
		rect.y = 20;
		rect.width = 64;
		rect.height = 64;

		batch = new BatchDrawer();
		gameObjectHandler = new GameObjectHandler(batch);



		texture = new BTexture("badlogic.jpg",null,600);
		texture.setWrap(Texture.TextureWrap.MirroredRepeat);



		obj = new GameObject(rect,texture);
		gameObjectHandler.Instantiate(obj);


		Quaternion rotation = new Quaternion();
		rotation.set(new Vector3(0,1,0),10);

		obj.tranform.rotation = rotation;





		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,400);




		//Try out script manager

		BlackScriptTest testScript = new BlackScriptTest();

		ScriptManager.tryAppendLooseScript(testScript);


		BasicCharacterController characterController = new BasicCharacterController();
		characterController.camera = camera;

		obj.AppendScript(characterController);


	}

	@Override
	public void render () {

		//RUN UPDATE FIRST
		ScriptManager.RunUpdate();

		camera.update();



		ScreenUtils.clear(1, 0, 0, 1);
		batch.RenderTextures(camera.combined);


	}
	
	@Override
	public void dispose () {
		gameObjectHandler.dispose();
		batch.dispose();
	}
}
