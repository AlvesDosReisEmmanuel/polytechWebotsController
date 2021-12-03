/*******************************************************************************
 * Copyright (c) 2017 I3S and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     UCA - I3S Laboratory - julien.deantoni@univ-cotedazur.fr -> initial API
 *******************************************************************************/

package fr.univcotedazur.kairos.webots.polycreate.controler;

import java.util.Random;

//import org.eclipse.january.dataset.Dataset;
//import org.eclipse.january.dataset.DatasetFactory;

import com.cyberbotics.webots.controller.Camera;
import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.GPS;
import com.cyberbotics.webots.controller.LED;
import com.cyberbotics.webots.controller.Motor;
import com.cyberbotics.webots.controller.Pen;
import com.cyberbotics.webots.controller.PositionSensor;
import com.cyberbotics.webots.controller.Receiver;
import com.cyberbotics.webots.controller.Supervisor;
import com.cyberbotics.webots.controller.TouchSensor;
import com.yakindu.core.TimerService;
import com.yakindu.core.rx.Observer;

import fr.univcotedazur.kairos.webots.polycreate.statechart.ControllerStateMachine;
import fr.univcotedazur.kairos.webots.polycreate.statechart.ControllerStateMachine.State;

class MyObserver implements Observer<Void> {
	@Override
	public void next(Void value) {

	}
}

public class PolyCreateControler extends Supervisor {

	private static boolean isTurning = false;

	static int MAX_SPEED = 16;
	static int NULL_SPEED = 0;
	static int HALF_SPEED = 8;
	static int MIN_SPEED = -16;

	static double WHEEL_RADIUS = 0.031;
	static double AXLE_LENGTH = 0.271756;
	static double ENCODER_RESOLUTION = 507.9188;
	protected static ControllerStateMachine theFSM;

	/**
	 * the inkEvaporation parameter in the WorldInfo element of the robot scene may
	 * be interesting to access
	 */
	public Pen pen = null;

	public Pen getPen() {
		return pen;
	}

	public Motor[] gripMotors = new Motor[2];
	public DistanceSensor gripperSensor = null;

	public Motor leftMotor = null;
	public Motor rightMotor = null;

	public PositionSensor leftSensor = null;
	public PositionSensor rightSensor = null;

	public LED ledOn = null;
	public LED ledPlay = null;
	public LED ledStep = null;

	public TouchSensor leftBumper = null;
	public TouchSensor rightBumper = null;

	public DistanceSensor leftCliffSensor = null;
	public DistanceSensor rightCliffSensor = null;
	public DistanceSensor frontLeftCliffSensor = null;
	public DistanceSensor frontRightCliffSensor = null;

	public DistanceSensor frontDistanceSensor = null;
	public DistanceSensor frontLeftDistanceSensor = null;
	public DistanceSensor frontRightDistanceSensor = null;

	public Camera frontCamera = null;
	public Camera backCamera = null;

	public Receiver receiver = null;

	public GPS gps = null;

	public int timestep = Integer.MAX_VALUE;
	public Random random = new Random();

	boolean isThereObstacleRight() {
		if (isThereCollisionAtRight() || frontRightDistanceSensor.getValue() < 250
				|| frontDistanceSensor.getValue() < 250) {
			return true;
		} else
			return false;
	}

	boolean isThereObstacleRightForTurning() {
		if (isThereCollisionAtRight() || frontRightDistanceSensor.getValue() < 1000) {
			return true;
		} else
			return false;
	}

	boolean isThereObstacleLeftForTurning() {
		if (isThereCollisionAtLeft() || frontDistanceSensor.getValue() < 250) {
			return true;
		} else
			return false;
	}

	boolean isThereObstacleLeft() {
		if (isThereCollisionAtLeft() || frontLeftDistanceSensor.getValue() < 250) {
			return true;
		} else
			return false;
	}

	public PolyCreateControler() {
		timestep = (int) Math.round(this.getBasicTimeStep());

		pen = createPen("pen");

		gripMotors[0] = createMotor("motor 7");
		gripMotors[1] = createMotor("motor 7 left");
		gripperSensor = createDistanceSensor("gripper middle distance sensor");
		gripperSensor.enable(timestep);

		leftMotor = createMotor("left wheel motor");
		rightMotor = createMotor("right wheel motor");
		leftMotor.setPosition(Double.POSITIVE_INFINITY);
		rightMotor.setPosition(Double.POSITIVE_INFINITY);
		leftMotor.setVelocity(0.0);
		rightMotor.setVelocity(0.0);

		leftSensor = createPositionSensor("left wheel sensor");
		rightSensor = createPositionSensor("right wheel sensor");
		leftSensor.enable(timestep);
		rightSensor.enable(timestep);

		ledOn = createLED("led_on");
		ledPlay = createLED("led_play");
		ledStep = createLED("led_step");

		leftBumper = createTouchSensor("bumper_left");
		rightBumper = createTouchSensor("bumper_right");
		leftBumper.enable(timestep);
		rightBumper.enable(timestep);

		leftCliffSensor = createDistanceSensor("cliff_left");
		rightCliffSensor = createDistanceSensor("cliff_right");
		frontLeftCliffSensor = createDistanceSensor("cliff_front_left");
		frontRightCliffSensor = createDistanceSensor("cliff_front_right");
		leftCliffSensor.enable(timestep);
		rightCliffSensor.enable(timestep);
		frontLeftCliffSensor.enable(timestep);
		frontRightCliffSensor.enable(timestep);

		frontDistanceSensor = createDistanceSensor("front distance sensor");
		frontDistanceSensor.enable(timestep);
		frontLeftDistanceSensor = createDistanceSensor("front left distance sensor");
		frontLeftDistanceSensor.enable(timestep);
		frontRightDistanceSensor = createDistanceSensor("front right distance sensor");
		frontRightDistanceSensor.enable(timestep);

		frontCamera = createCamera("frontCamera");
		frontCamera.enable(timestep);
		frontCamera.recognitionEnable(timestep);

		backCamera = createCamera("backCamera");
		backCamera.enable(timestep);
		backCamera.recognitionEnable(timestep);

		receiver = createReceiver("receiver");
		receiver.enable(timestep);

		gps = createGPS("gps");
		gps.enable(timestep);

		PolyCreateControler ctrl = this;

		theFSM = new ControllerStateMachine();
		TimerService timer = new TimerService();
		theFSM.setTimerService(timer);
		

		theFSM.getGoForward().subscribe(new MyObserver() {
			@Override
			public void next(Void value) {
				//theFSM.setObstacleDetectedBool(false);

				System.out.println("Raise goForward");
				// System.out.println(isTurning);
				goForward();
				// theFSM.setTurnFinished(false);
			}
		});

		theFSM.getDodgeObstacle().subscribe(new MyObserver() {
			@Override
			public void next(Void value) {
				isTurning = true;
				/*
				System.out.println("DodgingObstacle");

				System.out.print("ObstacleDetectedBool : ");
				System.out.println(theFSM.getObstacleDetectedBool());

				System.out.print("Null State ? ");
				System.out.println(theFSM.isStateActive(State.$NULLSTATE$));

				System.out.print("DodgeObstacle State ? ");
				System.out.println(theFSM.isStateActive(State.MAIN_REGION_DODGEOBSTACLE));

				System.out.print("Moving Forward State ? ");
				System.out.println(theFSM.isStateActive(State.MAIN_REGION_MOVING_INNER_REGION_FORWARD));

				System.out.print("Stopped State ? ");
				System.out.println(theFSM.isStateActive(State.MAIN_REGION_STOPPED));

				//System.out.print("CheckObstacle State ? ");
				//System.out.println(theFSM.isStateActive(State.R2_CHECKOBSTACLE));
				System.out.print("StopChecking State ? ");System.out.println(theFSM.isStateActive(State.R2_STOPCHECKING));
				//
				//

				System.out.print("Fake State ? ");
				System.out.println(theFSM.isStateActive(State.FAKE_F));

				System.out.print("Turning value: ");
				System.out.println(isTurning);
				*/
				
				goBackward();
				passiveWait(0.5);
				System.out.println("goBackward");
				while (isThereObstacleLeftForTurning()) {
					turn(45 * Math.PI / 180);
				}
				isTurning = false;
				System.out.println("Raise NoObstacle");
				theFSM.raiseNoObstacle();
				// theFSM.setTurnFinished(true);

			}

		});
		/**
		 * theFSM.getDodgeLeftObstacle().subscribe(new MyObserver() {
		 * 
		 * @Override public void next(Void value) { isTurning=true;
		 *           System.out.println("DodgingLeftObstacle");
		 *           System.out.println(isTurning); goBackward(); passiveWait(0.5);
		 *           while(isThereObstacleRightForTurning()) { turn(-45*Math.PI/180); }
		 *           isTurning=false; System.out.println("RaisingNoObstacle");
		 *           theFSM.raiseNoObstacle(); //theFSM.setTurnFinished(true);
		 * 
		 *           } });
		 */

		theFSM.getCheckObstacle().subscribe(new MyObserver() {
			@Override
			public void next(Void value) {
				//System.out.print("Wow");
				/*
				 * if(isThereObstacleRight()&&isThereObstacleLeft()) {
				 * System.out.println("Left && Right Detected");
				 * theFSM.raiseRightObstacleDetected(); } else if(isThereCollisionAtLeft() ||
				 * frontLeftDistanceSensor.getValue() < 250) {
				 * theFSM.raiseLeftObstacleDetected();
				 * System.out.println("LeftObstacleDetected");
				 * 
				 * } else if(isThereCollisionAtRight()|| frontRightDistanceSensor.getValue() <
				 * 250 ||frontDistanceSensor.getValue() < 250) {
				 * theFSM.raiseRightObstacleDetected();
				 * System.out.println("RightObstacleDetected");
				 * 
				 * }
				 */

				// passiveWait(1);
				if (isThereObstacleRight() || isThereObstacleLeft()) {
					System.out.println("raise ObstacleDetected");
					theFSM.setObstacleDetectedBool(true);
					theFSM.raiseObstacleDetected();
				}
			}
		});
		
		
				
		
		
		 theFSM.enter();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("Shutdown hook ran!");
				ctrl.delete();
			}
		});

	}

	public void openGripper() {
		gripMotors[0].setPosition(0.5);
		gripMotors[1].setPosition(0.5);
	}

	public void closeGripper() {
		gripMotors[0].setPosition(-0.2);
		gripMotors[1].setPosition(-0.2);
	}

	/**
	 * give the obstacle distance from the gripper sensor. max distance (i.e., no
	 * obstacle detected) is 1500
	 * 
	 * @return
	 */
	public double getObjectDistanceToGripper() {
		return gripperSensor.getValue();
	}

	public boolean isThereCollisionAtLeft() {
		return (leftBumper.getValue() != 0.0);
	}

	public boolean isThereCollisionAtRight() {
		return (rightBumper.getValue() != 0.0);
	}

	public void flushIRReceiver() {
		while (receiver.getQueueLength() > 0)
			receiver.nextPacket();
	}

	public boolean isThereVirtualwall() {
		return (receiver.getQueueLength() > 0);
	}

	public void goForward() {
		leftMotor.setVelocity(MAX_SPEED);
		rightMotor.setVelocity(MAX_SPEED);
	}

	public void goBackward() {
		leftMotor.setVelocity(-HALF_SPEED);
		rightMotor.setVelocity(-HALF_SPEED);
	}

	public void stop() {
		leftMotor.setVelocity(NULL_SPEED);
		rightMotor.setVelocity(NULL_SPEED);
	}

	public void passiveWait(double sec) {
		double start_time = getTime();
		do {
			step(timestep);
		} while (start_time + sec > getTime());
	}

	public double randdouble() {
		return random.nextDouble();
	}

	public void turn(double angle) {
		stop();
		double l_offset = leftSensor.getValue();
		double r_offset = rightSensor.getValue();
		step(timestep);
		double neg = (angle < 0.0) ? -1.0 : 1.0;
		leftMotor.setVelocity(neg * HALF_SPEED);
		rightMotor.setVelocity(-neg * HALF_SPEED);
		double orientation;
		do {
			double l = leftSensor.getValue() - l_offset;
			double r = rightSensor.getValue() - r_offset;
			double dl = l * WHEEL_RADIUS; // distance covered by left wheel in meter
			double dr = r * WHEEL_RADIUS; // distance covered by right wheel in meter
			orientation = neg * (dl - dr) / AXLE_LENGTH; // delta orientation in radian
			step(timestep);
		} while (orientation < neg * angle);
		stop();
		step(timestep);
	}

	/**
	 * The values are returned as a 3D-vector, therefore only the indices 0, 1, and
	 * 2 are valid for accessing the vector. The returned vector indicates the
	 * absolute position of the GPS device. This position can either be expressed in
	 * the cartesian coordinate system of Webots or using
	 * latitude-longitude-altitude, depending on the value of the
	 * gpsCoordinateSystem field of the WorldInfo node. The gpsReference field of
	 * the WorldInfo node can be used to define the reference point of the GPS. The
	 * wb_gps_get_speed function returns the current GPS speed in meters per second.
	 * 
	 * @return
	 */
	public double[] getPosition() {
		return gps.getValues();
	}

	public static void main(String[] args) {
		PolyCreateControler controler = new PolyCreateControler();
		theFSM.raiseStart();
		while (true) {
			if (!isTurning) {
				controler.passiveWait(0.1);
				// System.out.println("Advancing");
			} else {
				 //System.out.println("Turning in progress");
				try {
					System.out.println("Sleeping");
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		/**
		 * try { controler.openGripper(); controler.pen.write(true);
		 * controler.ledOn.set(1); controler.passiveWait(0.5); System.out.println("let's
		 * start");
		 * 
		 * while (true) {
		 */

		/**
		 * The position and orientation are expressed relatively to the camera (the
		 * relative position is the one of the center of the object which can differ
		 * from its origin) and the units are meter and radian.
		 * https://www.cyberbotics.com/doc/reference/camera?tab-language=python#wb_camera_has_recognition
		 */
		/**
		 * Node anObj = controler.getFromDef("can"); //should not be there, only to have
		 * another orientation for testing... controler.passiveWait(0.1);
		 * 
		 * // System.out.println("the orientation of the can is "
		 * +controler.computeRelativeObjectOrientation(anObj.getPosition(),anObj.getOrientation()));
		 * 
		 * System.out.println("-> the orientation of the robot is "
		 * +Math.atan2(controler.getSelf().getOrientation()[0],
		 * controler.getSelf().getOrientation()[8])); System.out.println(" the position
		 * of the robot is "
		 * +Math.round(controler.getSelf().getPosition()[0]*100)+";"+Math.round(controler.getSelf().getPosition()[2]*100));
		 * 
		 * System.out.println(" front distance:
		 * "+controler.frontDistanceSensor.getValue());
		 * 
		 * CameraRecognitionObject[] backObjs =
		 * controler.backCamera.getRecognitionObjects(); if (backObjs.length > 0) {
		 * CameraRecognitionObject obj = backObjs[0]; int oid = obj.getId(); // Node
		 * obj2 = controler.getFromId(oid); double[] backObjPos = obj.getPosition();
		 */
		/**
		 * The position and orientation are expressed relatively to the camera (the
		 * relative position is the one of the center of the object which can differ
		 * from its origin) and the units are meter and radian.
		 */
		/**
		 * System.out.println(" I saw an object on back Camera at :
		 * "+backObjPos[0]+","+backObjPos[1]); } CameraRecognitionObject[] frontObjs =
		 * controler.frontCamera.getRecognitionObjects(); if (frontObjs.length > 0) {
		 * for(CameraRecognitionObject obj : frontObjs) { double[] frontObjPos =
		 * obj.getPosition(); System.out.println(" I saw "+obj.getModel()+" on front
		 * Camera at : "+((double)Math.round(frontObjPos[1]*1000))/10+";
		 * "+Math.round(frontObjPos[0]*180/Math.PI)); } } System.out.println(" gripper
		 * distance sensor is "+controler.getObjectDistanceToGripper()); if
		 * (controler.isThereVirtualwall()) { System.out.println("Virtual wall
		 * detected\n"); controler.turn(Math.PI); } else if
		 * (controler.isThereCollisionAtLeft() ||
		 * controler.frontLeftDistanceSensor.getValue() < 250) { System.out.println("
		 * Left obstacle detected\n"); controler.goBackward();
		 * controler.passiveWait(0.5); controler.turn(Math.PI *
		 * controler.randdouble()+0.6);
		 * 
		 * } else if (controler.isThereCollisionAtRight()||
		 * controler.frontRightDistanceSensor.getValue() < 250 ||
		 * controler.frontDistanceSensor.getValue() < 250) { System.out.println(" Right
		 * obstacle detected\n"); controler.goBackward(); controler.passiveWait(0.5);
		 * controler.turn(-Math.PI * controler.randdouble()+0.6); } else {
		 * controler.goForward(); } controler.flushIRReceiver();
		 * 
		 * 
		 * 
		 * 
		 * 
		 * }
		 * 
		 * }catch (Exception e) { controler.delete(); }
		 * 
		 */

	}

	@Override
	protected void finalize() {
		this.delete();
		super.finalize();
	}

}