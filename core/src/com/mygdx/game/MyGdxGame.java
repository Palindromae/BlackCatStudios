package com.mygdx.game;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.dongbat.jbump.Item;
import com.mygdx.game.BlackCore.*;
import com.mygdx.game.BlackScripts.BasicCharacterController;
import com.mygdx.game.BlackScripts.CollisionDetection;
import com.mygdx.game.BlackScripts.ItemFactory;
import com.mygdx.game.BlackScripts.PhysicsSuperController;
import com.mygdx.game.CoreData.Items.Items;
import jdk.javadoc.internal.doclets.formats.html.markup.Script;

public class MyGdxGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	BTexture texture;
	GameObjectHandler gameObjectHandler;

	BlackScriptManager ScriptManager;
	FixedTimeController fixedTime;
	CollisionDetection collisionDetection;
	PhysicsSuperController physicsController;
	GameObject obj;
	GameObject obj2;
	GameObject obj3;

	BatchDrawer batch;
	
	@Override
	public void create () {
		collisionDetection = new CollisionDetection();
		physicsController = new PhysicsSuperController();
		batch = new BatchDrawer();
		try {
			ScriptManager = new BlackScriptManager(); // this exception shouldnt happen but just incase
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Rectangle rect = new Rectangle();
		rect.x = 800/2 - 64/2;
		rect.y = 20;
		rect.width = 30;
		rect.height = 30;

		batch = new BatchDrawer();
		gameObjectHandler = new GameObjectHandler(batch);

		fixedTime = new FixedTimeController();




		texture = new BTexture("badlogic.jpg",200,600);
		texture.setWrap(Texture.TextureWrap.MirroredRepeat);



		obj = new GameObject(new Rectangle(800/2 - 64/2,20,200,600),texture);
		obj2 = new GameObject(new Rectangle(800/2 - 64/2,20,200,600),texture);
		obj.addDynamicCollider();
		obj2.addDynamicCollider();



		//	gameObjectHandler.Instantiate(obj);







		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,400);




		//Try out script manager

		BlackScriptTest testScript = new BlackScriptTest();

		ScriptManager.tryAppendLooseScript(testScript);
		ScriptManager.tryAppendLooseScript(physicsController);


		BasicCharacterController characterController = new BasicCharacterController();
		characterController.camera = camera;

		obj.AppendScript(characterController);

		ItemFactory factory = new ItemFactory();
		ScriptManager.tryAppendLooseScript(factory);


		ItemFactory.factory.implementItems();

		System.out.println(factory.produceItem(Items.Lettuce).name);

	}

	@Override
	public void render () {

		if(fixedTime.doTimeStep()){
			//the time step is within accumilator

			while (fixedTime.accumulator> fixedTime.dt){
				ScriptManager.RunFixedUpdate((float)fixedTime.dt);
				fixedTime.accumulator-= fixedTime.dt;
			}
		}




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
