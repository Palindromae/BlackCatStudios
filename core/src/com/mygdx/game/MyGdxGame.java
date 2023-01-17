package com.mygdx.game;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.dongbat.jbump.Grid;
import com.dongbat.jbump.Item;
import com.mygdx.game.BlackCore.*;
import com.mygdx.game.BlackCore.Pathfinding.*;
import com.mygdx.game.BlackScripts.BasicCharacterController;
import com.mygdx.game.BlackScripts.CollisionDetection;
import com.mygdx.game.BlackScripts.CustomerManager;
import com.mygdx.game.BlackScripts.GridWorld;
import com.mygdx.game.BlackScripts.ItemFactory;
import com.mygdx.game.BlackScripts.PhysicsSuperController;
import com.mygdx.game.CoreData.Items.Items;
import jdk.javadoc.internal.doclets.formats.html.markup.Script;

import java.util.List;

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

	CustomerManager customerManager;

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



		batch = new BatchDrawer();
		gameObjectHandler = new GameObjectHandler(batch);

		fixedTime = new FixedTimeController();


		texture = new BTexture("badlogic.jpg",null,600);
		texture.setWrap(Texture.TextureWrap.MirroredRepeat);



		obj = new GameObject(new Rectangle(800/2 - 64/2,20,200,600),texture);
		obj2 = new GameObject(new Rectangle(800/2 - 64/2,20,200,600),texture);
		obj2.transform.position = new Vector3(700,0,0);
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

		//System.out.println(factory.produceItem(Items.Lettuce).name);

		GridSettings gridsets = new GridSettings();
		gridsets.scaleOfGrid = .25f;
		gridsets.XSizeOfFloor = 10;
		gridsets.ZSizeOfFloor = 10;

		GridPartition gPart = new GridPartition(gridsets);

		PathfindingConfig config = new PathfindingConfig();
		config.DiagonalCost = 1;
		config.StepCost = 1;
		config.DiagonalCost = 1;
		config.PathMutliplier = 1;
		config.maxIterations = 200;
		List<Vector2> a = gPart.pathfindFrom(0,0,3,3,config);
		System.out.println(a.size());
		for (Vector2 v2: a
			 ) {
			System.out.print(a);
		}

		customerManager = new CustomerManager();
		CustomerManager.customermanager.setCustomerTexture(texture);

		ScriptManager.tryAppendLooseScript(customerManager);

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
