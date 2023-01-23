package com.mygdx.game;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.BlackCore.*;
import com.mygdx.game.BlackCore.Pathfinding.*;
import com.mygdx.game.BlackScripts.*;

public class MyGdxGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	BTexture texture;
	GameObjectHandler gameObjectHandler;

	BlackScriptManager ScriptManager;
	FixedTimeController fixedTime;
	CollisionDetection collisionDetection;
	PhysicsSuperController physicsController;

	CreateGameWorld GameWorld;
	RunInteract interact;
	MasterChef masterChef;
	SoundFrame soundFrame = new SoundFrame();
	LoadSounds soundLoader = new LoadSounds();

	BatchDrawer batch;

	public static PathfindingConfig pathfindingConfig;

	CustomerManager customerManager;

	@Override
	public void create () {
		soundLoader.loadAllSounds(soundFrame);
		long id = soundFrame.playSound("Main Screen");
		soundFrame.setLooping(id, "Main Screen");
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


		texture = new BTexture("badlogic.jpg",20,20);
		texture.setWrap(Texture.TextureWrap.MirroredRepeat);

		GameWorld = new CreateGameWorld();

		GridSettings gridsets = new GridSettings();
		gridsets.scaleOfGrid = 25;
		gridsets.XSizeOfFloor = 1000;
		gridsets.ZSizeOfFloor = 600;

		GridPartition gPart = new GridPartition(gridsets);

		pathfindingConfig = new PathfindingConfig();
		pathfindingConfig.DiagonalCost = 1;
		pathfindingConfig.StepCost = 1;
		pathfindingConfig.DiagonalCost = 1;
		pathfindingConfig.PathMutliplier = 1;
		pathfindingConfig.maxIterations = 500;
		pathfindingConfig.DistanceCost = 1;


		GameWorld.Instantiate(gPart);



		interact = new RunInteract(GameWorld);





		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,480);




		//Try out script manager

		BlackScriptTest testScript = new BlackScriptTest();

		ScriptManager.tryAppendLooseScript(testScript);
		ScriptManager.tryAppendLooseScript(physicsController);


		BasicCharacterController characterController = new BasicCharacterController();
		characterController.camera = camera;

		//obj.AppendScript(characterController);

		ItemFactory factory = new ItemFactory();
		ScriptManager.tryAppendLooseScript(factory);


		ItemFactory.factory.implementItems();

		//System.out.println(factory.produceItem(Items.Lettuce).name);

	//	List<Vector2> a = gPart.pathfindFrom(0,0,3,3,config);
	//	System.out.println(a.size());
	//	for (Vector2 v2: a
	//		 ) {
	///		System.out.print(a);
		//}

		masterChef = new MasterChef();
		masterChef.camera = camera;
		masterChef.chefTex = texture;
		masterChef.KitchenPartition = gPart;

		ScriptManager.tryAppendLooseScript(masterChef);

		customerManager = new CustomerManager(GameWorld.Tables,gPart);
		CustomerManager.customermanager.setCustomerTexture(texture);

		CustomerManager.customermanager.WaitingPositions = GameWorld.CustomerWaitingLocations;
		CustomerManager.customermanager.spawningLocation = GameWorld.CustomerSpawnLocations;
		CustomerManager.customermanager.TableRadius =  GameWorld.TableRadius;



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
