/** Generated by YAKINDU Statechart Tools code generator. */
package fr.univcotedazur.kairos.webots.polycreate.statechart;

import com.yakindu.core.IStatemachine;
import com.yakindu.core.ITimed;
import com.yakindu.core.ITimerService;
import com.yakindu.core.rx.Observable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ControllerStateMachine implements IStatemachine, ITimed {
	public enum State {
		MAIN_REGION_STOPPED,
		MAIN_REGION_DODGEOBSTACLE,
		MAIN_REGION_DODGEOBSTACLE_R1_DODGERIGHTOBSTACLE,
		MAIN_REGION_DODGEOBSTACLE_R1_DODGELEFTOBSTACLE,
		MAIN_REGION_RELEASINGWASTE,
		MAIN_REGION_NEARWASTE,
		MAIN_REGION_MOVING,
		MAIN_REGION_MOVING_INNER_REGION_WASTEGRABBED,
		MAIN_REGION_MOVING_INNER_REGION_WASTEDETECTED,
		MAIN_REGION_MOVING_INNER_REGION_FORWARD,
		_REGION1_CHECKOBSTACLE,
		_REGION1_STOPCHECKING,
		FAKE_F,
		$NULLSTATE$
	};
	
	private State[] historyVector = new State[1];
	private final State[] stateVector = new State[3];
	
	private ITimerService timerService;
	
	private final boolean[] timeEvents = new boolean[2];
	
	private BlockingQueue<Runnable> internalEventQueue = new LinkedBlockingQueue<Runnable>();
	private BlockingQueue<Runnable> inEventQueue = new LinkedBlockingQueue<Runnable>();
	private boolean fakeEvent;
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		synchronized(ControllerStateMachine.this) {
			return isExecuting;
		}
	}
	
	protected void setIsExecuting(boolean value) {
		synchronized(ControllerStateMachine.this) {
			this.isExecuting = value;
		}
	}
	private long stateConfVectorPosition;
	
	protected long getStateConfVectorPosition() {
		synchronized(ControllerStateMachine.this) {
			return stateConfVectorPosition;
		}
	}
	
	protected void setStateConfVectorPosition(long value) {
		synchronized(ControllerStateMachine.this) {
			this.stateConfVectorPosition = value;
		}
	}
	public ControllerStateMachine() {
		for (int i = 0; i < 3; i++) {
			stateVector[i] = State.$NULLSTATE$;
		}
		for (int i = 0; i < 1; i++) {
			historyVector[i] = State.$NULLSTATE$;
		}
		
		clearInEvents();
		clearInternalEvents();
		
		setTurnFinished(false);
		
		isExecuting = false;
	}
	
	public synchronized void enter() {
		if (timerService == null) {
			throw new IllegalStateException("Timer service must be set.");
		}
		
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		enterSequence_main_region_default();
		enterSequence__region1_default();
		enterSequence_fake_default();
		isExecuting = false;
	}
	
	public synchronized void exit() {
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		exitSequence_main_region();
		exitSequence__region1();
		exitSequence_fake();
		isExecuting = false;
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public synchronized boolean isActive() {
		return stateVector[0] != State.$NULLSTATE$||stateVector[1] != State.$NULLSTATE$||stateVector[2] != State.$NULLSTATE$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public synchronized boolean isFinal() {
		return false;
	}
	private void clearInEvents() {
		myEvent = false;
		obstacleNear = false;
		objectNear = false;
		nothingInFront = false;
		obstacleDetected = false;
		noObstacle = false;
		start = false;
		rightObstacleDetected = false;
		leftObstacleDetected = false;
		stop = false;
		nearWaste = false;
		atGarbageDisposal = false;
		wasteDetected = false;
		wasteGripped = false;
		wasteReleased = false;
		timeEvents[0] = false;
		timeEvents[1] = false;
	}
	
	private void clearInternalEvents() {
		fakeEvent = false;
	}
	
	private void microStep() {
		long transitioned = -1;
		
		stateConfVectorPosition = 0;
		
		switch (stateVector[0]) {
		case MAIN_REGION_STOPPED:
			transitioned = main_region_Stopped_react(transitioned);
			break;
		case MAIN_REGION_DODGEOBSTACLE_R1_DODGERIGHTOBSTACLE:
			transitioned = main_region_DodgeObstacle_r1_DodgeRightObstacle_react(transitioned);
			break;
		case MAIN_REGION_DODGEOBSTACLE_R1_DODGELEFTOBSTACLE:
			transitioned = main_region_DodgeObstacle_r1_DodgeLeftObstacle_react(transitioned);
			break;
		case MAIN_REGION_RELEASINGWASTE:
			transitioned = main_region_ReleasingWaste_react(transitioned);
			break;
		case MAIN_REGION_NEARWASTE:
			transitioned = main_region_NearWaste_react(transitioned);
			break;
		case MAIN_REGION_MOVING_INNER_REGION_WASTEGRABBED:
			transitioned = main_region_Moving_inner_region_WasteGrabbed_react(transitioned);
			break;
		case MAIN_REGION_MOVING_INNER_REGION_WASTEDETECTED:
			transitioned = main_region_Moving_inner_region_WasteDetected_react(transitioned);
			break;
		case MAIN_REGION_MOVING_INNER_REGION_FORWARD:
			transitioned = main_region_Moving_inner_region_Forward_react(transitioned);
			break;
		default:
			break;
		}
		
		if (getStateConfVectorPosition()<1) {
			switch (stateVector[1]) {
			case _REGION1_CHECKOBSTACLE:
				transitioned = _region1_checkObstacle_react(transitioned);
				break;
			case _REGION1_STOPCHECKING:
				transitioned = _region1_StopChecking_react(transitioned);
				break;
			default:
				break;
			}
		}
		if (getStateConfVectorPosition()<2) {
			switch (stateVector[2]) {
			case FAKE_F:
				transitioned = fake_f_react(transitioned);
				break;
			default:
				break;
			}
		}
	}
	
	private void runCycle() {
		if (timerService == null) {
			throw new IllegalStateException("Timer service must be set.");
		}
		
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		nextEvent();
		do { 
			microStep();
			
			clearInEvents();
			
			clearInternalEvents();
			
			nextEvent();
		} while ((((((((((((((((((myEvent || obstacleNear) || objectNear) || nothingInFront) || obstacleDetected) || noObstacle) || start) || rightObstacleDetected) || leftObstacleDetected) || stop) || nearWaste) || atGarbageDisposal) || wasteDetected) || wasteGripped) || wasteReleased) || fakeEvent) || timeEvents[0]) || timeEvents[1]));
		
		isExecuting = false;
	}
	
	protected void nextEvent() {
		if(!internalEventQueue.isEmpty()) {
			internalEventQueue.poll().run();
			return;
		}
		if(!inEventQueue.isEmpty()) {
			inEventQueue.poll().run();
			return;
		}
	}
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public synchronized boolean isStateActive(State state) {
	
		switch (state) {
		case MAIN_REGION_STOPPED:
			return stateVector[0] == State.MAIN_REGION_STOPPED;
		case MAIN_REGION_DODGEOBSTACLE:
			return stateVector[0].ordinal() >= State.
					MAIN_REGION_DODGEOBSTACLE.ordinal()&& stateVector[0].ordinal() <= State.MAIN_REGION_DODGEOBSTACLE_R1_DODGELEFTOBSTACLE.ordinal();
		case MAIN_REGION_DODGEOBSTACLE_R1_DODGERIGHTOBSTACLE:
			return stateVector[0] == State.MAIN_REGION_DODGEOBSTACLE_R1_DODGERIGHTOBSTACLE;
		case MAIN_REGION_DODGEOBSTACLE_R1_DODGELEFTOBSTACLE:
			return stateVector[0] == State.MAIN_REGION_DODGEOBSTACLE_R1_DODGELEFTOBSTACLE;
		case MAIN_REGION_RELEASINGWASTE:
			return stateVector[0] == State.MAIN_REGION_RELEASINGWASTE;
		case MAIN_REGION_NEARWASTE:
			return stateVector[0] == State.MAIN_REGION_NEARWASTE;
		case MAIN_REGION_MOVING:
			return stateVector[0].ordinal() >= State.
					MAIN_REGION_MOVING.ordinal()&& stateVector[0].ordinal() <= State.MAIN_REGION_MOVING_INNER_REGION_FORWARD.ordinal();
		case MAIN_REGION_MOVING_INNER_REGION_WASTEGRABBED:
			return stateVector[0] == State.MAIN_REGION_MOVING_INNER_REGION_WASTEGRABBED;
		case MAIN_REGION_MOVING_INNER_REGION_WASTEDETECTED:
			return stateVector[0] == State.MAIN_REGION_MOVING_INNER_REGION_WASTEDETECTED;
		case MAIN_REGION_MOVING_INNER_REGION_FORWARD:
			return stateVector[0] == State.MAIN_REGION_MOVING_INNER_REGION_FORWARD;
		case _REGION1_CHECKOBSTACLE:
			return stateVector[1] == State._REGION1_CHECKOBSTACLE;
		case _REGION1_STOPCHECKING:
			return stateVector[1] == State._REGION1_STOPCHECKING;
		case FAKE_F:
			return stateVector[2] == State.FAKE_F;
		default:
			return false;
		}
	}
	
	public synchronized void setTimerService(ITimerService timerService) {
		this.timerService = timerService;
	}
	
	public ITimerService getTimerService() {
		return timerService;
	}
	
	public synchronized void raiseTimeEvent(int eventID) {
		inEventQueue.add(() -> {
			timeEvents[eventID] = true;
		});
		runCycle();
	}
	
	
	protected void raiseFakeEvent() {
		internalEventQueue.add(() -> {
			fakeEvent = true;
		});
	}
	
	private boolean myEvent;
	
	
	public void raiseMyEvent() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				myEvent = true;
			});
			runCycle();
		}
	}
	
	private boolean obstacleNear;
	
	
	public void raiseObstacleNear() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				obstacleNear = true;
			});
			runCycle();
		}
	}
	
	private boolean objectNear;
	
	
	public void raiseObjectNear() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				objectNear = true;
			});
			runCycle();
		}
	}
	
	private boolean nothingInFront;
	
	
	public void raiseNothingInFront() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				nothingInFront = true;
			});
			runCycle();
		}
	}
	
	private boolean obstacleDetected;
	
	
	public void raiseObstacleDetected() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				obstacleDetected = true;
			});
			runCycle();
		}
	}
	
	private boolean noObstacle;
	
	
	public void raiseNoObstacle() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				noObstacle = true;
			});
			runCycle();
		}
	}
	
	private boolean start;
	
	
	public void raiseStart() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				start = true;
			});
			runCycle();
		}
	}
	
	private boolean rightObstacleDetected;
	
	
	public void raiseRightObstacleDetected() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				rightObstacleDetected = true;
			});
			runCycle();
		}
	}
	
	private boolean leftObstacleDetected;
	
	
	public void raiseLeftObstacleDetected() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				leftObstacleDetected = true;
			});
			runCycle();
		}
	}
	
	private boolean stop;
	
	
	public void raiseStop() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				stop = true;
			});
			runCycle();
		}
	}
	
	private boolean nearWaste;
	
	
	public void raiseNearWaste() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				nearWaste = true;
			});
			runCycle();
		}
	}
	
	private boolean atGarbageDisposal;
	
	
	public void raiseAtGarbageDisposal() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				atGarbageDisposal = true;
			});
			runCycle();
		}
	}
	
	private boolean wasteDetected;
	
	
	public void raiseWasteDetected() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				wasteDetected = true;
			});
			runCycle();
		}
	}
	
	private boolean wasteGripped;
	
	
	public void raiseWasteGripped() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				wasteGripped = true;
			});
			runCycle();
		}
	}
	
	private boolean wasteReleased;
	
	
	public void raiseWasteReleased() {
		synchronized(ControllerStateMachine.this) {
			inEventQueue.add(() -> {
				wasteReleased = true;
			});
			runCycle();
		}
	}
	
	private boolean turnLeft;
	
	
	protected void raiseTurnLeft() {
		synchronized(ControllerStateMachine.this) {
			turnLeft = true;
			turnLeftObservable.next(null);
		}
	}
	
	private Observable<Void> turnLeftObservable = new Observable<Void>();
	
	public Observable<Void> getTurnLeft() {
		return turnLeftObservable;
	}
	
	private boolean turnRight;
	
	
	protected void raiseTurnRight() {
		synchronized(ControllerStateMachine.this) {
			turnRight = true;
			turnRightObservable.next(null);
		}
	}
	
	private Observable<Void> turnRightObservable = new Observable<Void>();
	
	public Observable<Void> getTurnRight() {
		return turnRightObservable;
	}
	
	private boolean goForward;
	
	
	protected void raiseGoForward() {
		synchronized(ControllerStateMachine.this) {
			goForward = true;
			goForwardObservable.next(null);
		}
	}
	
	private Observable<Void> goForwardObservable = new Observable<Void>();
	
	public Observable<Void> getGoForward() {
		return goForwardObservable;
	}
	
	private boolean goBackward;
	
	
	protected void raiseGoBackward() {
		synchronized(ControllerStateMachine.this) {
			goBackward = true;
			goBackwardObservable.next(null);
		}
	}
	
	private Observable<Void> goBackwardObservable = new Observable<Void>();
	
	public Observable<Void> getGoBackward() {
		return goBackwardObservable;
	}
	
	private boolean dodgeRightObstacle;
	
	
	protected void raiseDodgeRightObstacle() {
		synchronized(ControllerStateMachine.this) {
			dodgeRightObstacle = true;
			dodgeRightObstacleObservable.next(null);
		}
	}
	
	private Observable<Void> dodgeRightObstacleObservable = new Observable<Void>();
	
	public Observable<Void> getDodgeRightObstacle() {
		return dodgeRightObstacleObservable;
	}
	
	private boolean dodgeLeftObstacle;
	
	
	protected void raiseDodgeLeftObstacle() {
		synchronized(ControllerStateMachine.this) {
			dodgeLeftObstacle = true;
			dodgeLeftObstacleObservable.next(null);
		}
	}
	
	private Observable<Void> dodgeLeftObstacleObservable = new Observable<Void>();
	
	public Observable<Void> getDodgeLeftObstacle() {
		return dodgeLeftObstacleObservable;
	}
	
	private boolean checkObstacle;
	
	
	protected void raiseCheckObstacle() {
		synchronized(ControllerStateMachine.this) {
			checkObstacle = true;
			checkObstacleObservable.next(null);
		}
	}
	
	private Observable<Void> checkObstacleObservable = new Observable<Void>();
	
	public Observable<Void> getCheckObstacle() {
		return checkObstacleObservable;
	}
	
	private boolean goToWaste;
	
	
	protected void raiseGoToWaste() {
		synchronized(ControllerStateMachine.this) {
			goToWaste = true;
			goToWasteObservable.next(null);
		}
	}
	
	private Observable<Void> goToWasteObservable = new Observable<Void>();
	
	public Observable<Void> getGoToWaste() {
		return goToWasteObservable;
	}
	
	private boolean grabWaste;
	
	
	protected void raiseGrabWaste() {
		synchronized(ControllerStateMachine.this) {
			grabWaste = true;
			grabWasteObservable.next(null);
		}
	}
	
	private Observable<Void> grabWasteObservable = new Observable<Void>();
	
	public Observable<Void> getGrabWaste() {
		return grabWasteObservable;
	}
	
	private boolean movingWaste;
	
	
	protected void raiseMovingWaste() {
		synchronized(ControllerStateMachine.this) {
			movingWaste = true;
			movingWasteObservable.next(null);
		}
	}
	
	private Observable<Void> movingWasteObservable = new Observable<Void>();
	
	public Observable<Void> getMovingWaste() {
		return movingWasteObservable;
	}
	
	private boolean releasingWaste;
	
	
	protected void raiseReleasingWaste() {
		synchronized(ControllerStateMachine.this) {
			releasingWaste = true;
			releasingWasteObservable.next(null);
		}
	}
	
	private Observable<Void> releasingWasteObservable = new Observable<Void>();
	
	public Observable<Void> getReleasingWaste() {
		return releasingWasteObservable;
	}
	
	private boolean turnFinished;
	
	public synchronized boolean getTurnFinished() {
		synchronized(ControllerStateMachine.this) {
			return turnFinished;
		}
	}
	
	public void setTurnFinished(boolean value) {
		synchronized(ControllerStateMachine.this) {
			this.turnFinished = value;
		}
	}
	
	/* Entry action for state 'DodgeRightObstacle'. */
	private void entryAction_main_region_DodgeObstacle_r1_DodgeRightObstacle() {
		raiseDodgeRightObstacle();
	}
	
	/* Entry action for state 'DodgeLeftObstacle'. */
	private void entryAction_main_region_DodgeObstacle_r1_DodgeLeftObstacle() {
		raiseDodgeLeftObstacle();
	}
	
	/* Entry action for state 'ReleasingWaste'. */
	private void entryAction_main_region_ReleasingWaste() {
		raiseReleasingWaste();
	}
	
	/* Entry action for state 'NearWaste'. */
	private void entryAction_main_region_NearWaste() {
		raiseGrabWaste();
	}
	
	/* Entry action for state 'WasteGrabbed'. */
	private void entryAction_main_region_Moving_inner_region_WasteGrabbed() {
		raiseMovingWaste();
	}
	
	/* Entry action for state 'WasteDetected'. */
	private void entryAction_main_region_Moving_inner_region_WasteDetected() {
		raiseGoToWaste();
	}
	
	/* Entry action for state 'Forward'. */
	private void entryAction_main_region_Moving_inner_region_Forward() {
		raiseGoForward();
	}
	
	/* Entry action for state 'checkObstacle'. */
	private void entryAction__region1_checkObstacle() {
		timerService.setTimer(this, 0, 28, false);
		
		raiseCheckObstacle();
	}
	
	/* Entry action for state 'f'. */
	private void entryAction_fake_f() {
		timerService.setTimer(this, 1, 100, true);
	}
	
	/* Exit action for state 'checkObstacle'. */
	private void exitAction__region1_checkObstacle() {
		timerService.unsetTimer(this, 0);
	}
	
	/* Exit action for state 'f'. */
	private void exitAction_fake_f() {
		timerService.unsetTimer(this, 1);
	}
	
	/* 'default' enter sequence for state Stopped */
	private void enterSequence_main_region_Stopped_default() {
		stateVector[0] = State.MAIN_REGION_STOPPED;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state DodgeRightObstacle */
	private void enterSequence_main_region_DodgeObstacle_r1_DodgeRightObstacle_default() {
		entryAction_main_region_DodgeObstacle_r1_DodgeRightObstacle();
		stateVector[0] = State.MAIN_REGION_DODGEOBSTACLE_R1_DODGERIGHTOBSTACLE;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state DodgeLeftObstacle */
	private void enterSequence_main_region_DodgeObstacle_r1_DodgeLeftObstacle_default() {
		entryAction_main_region_DodgeObstacle_r1_DodgeLeftObstacle();
		stateVector[0] = State.MAIN_REGION_DODGEOBSTACLE_R1_DODGELEFTOBSTACLE;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state ReleasingWaste */
	private void enterSequence_main_region_ReleasingWaste_default() {
		entryAction_main_region_ReleasingWaste();
		stateVector[0] = State.MAIN_REGION_RELEASINGWASTE;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state NearWaste */
	private void enterSequence_main_region_NearWaste_default() {
		entryAction_main_region_NearWaste();
		stateVector[0] = State.MAIN_REGION_NEARWASTE;
		stateConfVectorPosition = 0;
	}
	
	/* 'default' enter sequence for state Moving */
	private void enterSequence_main_region_Moving_default() {
		enterSequence_main_region_Moving_inner_region_default();
	}
	
	/* 'default' enter sequence for state WasteGrabbed */
	private void enterSequence_main_region_Moving_inner_region_WasteGrabbed_default() {
		entryAction_main_region_Moving_inner_region_WasteGrabbed();
		stateVector[0] = State.MAIN_REGION_MOVING_INNER_REGION_WASTEGRABBED;
		stateConfVectorPosition = 0;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state WasteDetected */
	private void enterSequence_main_region_Moving_inner_region_WasteDetected_default() {
		entryAction_main_region_Moving_inner_region_WasteDetected();
		stateVector[0] = State.MAIN_REGION_MOVING_INNER_REGION_WASTEDETECTED;
		stateConfVectorPosition = 0;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state Forward */
	private void enterSequence_main_region_Moving_inner_region_Forward_default() {
		entryAction_main_region_Moving_inner_region_Forward();
		stateVector[0] = State.MAIN_REGION_MOVING_INNER_REGION_FORWARD;
		stateConfVectorPosition = 0;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state checkObstacle */
	private void enterSequence__region1_checkObstacle_default() {
		entryAction__region1_checkObstacle();
		stateVector[1] = State._REGION1_CHECKOBSTACLE;
		stateConfVectorPosition = 1;
	}
	
	/* 'default' enter sequence for state StopChecking */
	private void enterSequence__region1_StopChecking_default() {
		stateVector[1] = State._REGION1_STOPCHECKING;
		stateConfVectorPosition = 1;
	}
	
	/* 'default' enter sequence for state f */
	private void enterSequence_fake_f_default() {
		entryAction_fake_f();
		stateVector[2] = State.FAKE_F;
		stateConfVectorPosition = 2;
	}
	
	/* 'default' enter sequence for region main region */
	private void enterSequence_main_region_default() {
		react_main_region__entry_Default();
	}
	
	/* 'default' enter sequence for region inner region */
	private void enterSequence_main_region_Moving_inner_region_default() {
		react_main_region_Moving_inner_region__entry_Default();
	}
	
	/* 'default' enter sequence for region null */
	private void enterSequence__region1_default() {
		react__region1__entry_Default();
	}
	
	/* 'default' enter sequence for region fake */
	private void enterSequence_fake_default() {
		react_fake__entry_Default();
	}
	
	/* Default exit sequence for state Stopped */
	private void exitSequence_main_region_Stopped() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state DodgeObstacle */
	private void exitSequence_main_region_DodgeObstacle() {
		exitSequence_main_region_DodgeObstacle_r1();
	}
	
	/* Default exit sequence for state DodgeRightObstacle */
	private void exitSequence_main_region_DodgeObstacle_r1_DodgeRightObstacle() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state DodgeLeftObstacle */
	private void exitSequence_main_region_DodgeObstacle_r1_DodgeLeftObstacle() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state ReleasingWaste */
	private void exitSequence_main_region_ReleasingWaste() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state NearWaste */
	private void exitSequence_main_region_NearWaste() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state Moving */
	private void exitSequence_main_region_Moving() {
		exitSequence_main_region_Moving_inner_region();
	}
	
	/* Default exit sequence for state WasteGrabbed */
	private void exitSequence_main_region_Moving_inner_region_WasteGrabbed() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state WasteDetected */
	private void exitSequence_main_region_Moving_inner_region_WasteDetected() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state Forward */
	private void exitSequence_main_region_Moving_inner_region_Forward() {
		stateVector[0] = State.$NULLSTATE$;
		stateConfVectorPosition = 0;
	}
	
	/* Default exit sequence for state checkObstacle */
	private void exitSequence__region1_checkObstacle() {
		stateVector[1] = State.$NULLSTATE$;
		stateConfVectorPosition = 1;
		
		exitAction__region1_checkObstacle();
	}
	
	/* Default exit sequence for state StopChecking */
	private void exitSequence__region1_StopChecking() {
		stateVector[1] = State.$NULLSTATE$;
		stateConfVectorPosition = 1;
	}
	
	/* Default exit sequence for state f */
	private void exitSequence_fake_f() {
		stateVector[2] = State.$NULLSTATE$;
		stateConfVectorPosition = 2;
		
		exitAction_fake_f();
	}
	
	/* Default exit sequence for region main region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
		case MAIN_REGION_STOPPED:
			exitSequence_main_region_Stopped();
			break;
		case MAIN_REGION_DODGEOBSTACLE_R1_DODGERIGHTOBSTACLE:
			exitSequence_main_region_DodgeObstacle_r1_DodgeRightObstacle();
			break;
		case MAIN_REGION_DODGEOBSTACLE_R1_DODGELEFTOBSTACLE:
			exitSequence_main_region_DodgeObstacle_r1_DodgeLeftObstacle();
			break;
		case MAIN_REGION_RELEASINGWASTE:
			exitSequence_main_region_ReleasingWaste();
			break;
		case MAIN_REGION_NEARWASTE:
			exitSequence_main_region_NearWaste();
			break;
		case MAIN_REGION_MOVING_INNER_REGION_WASTEGRABBED:
			exitSequence_main_region_Moving_inner_region_WasteGrabbed();
			break;
		case MAIN_REGION_MOVING_INNER_REGION_WASTEDETECTED:
			exitSequence_main_region_Moving_inner_region_WasteDetected();
			break;
		case MAIN_REGION_MOVING_INNER_REGION_FORWARD:
			exitSequence_main_region_Moving_inner_region_Forward();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r1 */
	private void exitSequence_main_region_DodgeObstacle_r1() {
		switch (stateVector[0]) {
		case MAIN_REGION_DODGEOBSTACLE_R1_DODGERIGHTOBSTACLE:
			exitSequence_main_region_DodgeObstacle_r1_DodgeRightObstacle();
			break;
		case MAIN_REGION_DODGEOBSTACLE_R1_DODGELEFTOBSTACLE:
			exitSequence_main_region_DodgeObstacle_r1_DodgeLeftObstacle();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region inner region */
	private void exitSequence_main_region_Moving_inner_region() {
		switch (stateVector[0]) {
		case MAIN_REGION_MOVING_INNER_REGION_WASTEGRABBED:
			exitSequence_main_region_Moving_inner_region_WasteGrabbed();
			break;
		case MAIN_REGION_MOVING_INNER_REGION_WASTEDETECTED:
			exitSequence_main_region_Moving_inner_region_WasteDetected();
			break;
		case MAIN_REGION_MOVING_INNER_REGION_FORWARD:
			exitSequence_main_region_Moving_inner_region_Forward();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region null */
	private void exitSequence__region1() {
		switch (stateVector[1]) {
		case _REGION1_CHECKOBSTACLE:
			exitSequence__region1_checkObstacle();
			break;
		case _REGION1_STOPCHECKING:
			exitSequence__region1_StopChecking();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region fake */
	private void exitSequence_fake() {
		switch (stateVector[2]) {
		case FAKE_F:
			exitSequence_fake_f();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region__entry_Default() {
		enterSequence_main_region_Stopped_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region_Moving_inner_region__entry_Default() {
		enterSequence_main_region_Moving_inner_region_Forward_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react__region1__entry_Default() {
		enterSequence__region1_checkObstacle_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_fake__entry_Default() {
		enterSequence_fake_f_default();
	}
	
	private long react(long transitioned_before) {
		return transitioned_before;
	}
	
	private long main_region_Stopped_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0) {
			if (start) {
				exitSequence_main_region_Stopped();
				enterSequence_main_region_Moving_default();
				transitioned_after = 0;
			}
		}
		return transitioned_after;
	}
	
	private long main_region_DodgeObstacle_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0) {
			if (noObstacle) {
				exitSequence_main_region_DodgeObstacle();
				enterSequence_main_region_Moving_inner_region_Forward_default();
				transitioned_after = 0;
			}
		}
		return transitioned_after;
	}
	
	private long main_region_DodgeObstacle_r1_DodgeRightObstacle_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0) {
		}
		/* If no transition was taken then execute local reactions */
		if (transitioned_after==transitioned_before) {
			transitioned_after = main_region_DodgeObstacle_react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long main_region_DodgeObstacle_r1_DodgeLeftObstacle_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0) {
		}
		/* If no transition was taken then execute local reactions */
		if (transitioned_after==transitioned_before) {
			transitioned_after = main_region_DodgeObstacle_react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long main_region_ReleasingWaste_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0) {
			if (wasteReleased) {
				exitSequence_main_region_ReleasingWaste();
				enterSequence_main_region_Moving_inner_region_Forward_default();
				transitioned_after = 0;
			}
		}
		return transitioned_after;
	}
	
	private long main_region_NearWaste_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0) {
			if (wasteGripped) {
				exitSequence_main_region_NearWaste();
				enterSequence_main_region_Moving_inner_region_WasteGrabbed_default();
				transitioned_after = 0;
			}
		}
		return transitioned_after;
	}
	
	private long main_region_Moving_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0) {
			if (leftObstacleDetected) {
				exitSequence_main_region_Moving();
				enterSequence_main_region_DodgeObstacle_r1_DodgeLeftObstacle_default();
				transitioned_after = 0;
			} else {
				if (rightObstacleDetected) {
					exitSequence_main_region_Moving();
					enterSequence_main_region_DodgeObstacle_r1_DodgeRightObstacle_default();
					transitioned_after = 0;
				}
			}
		}
		return transitioned_after;
	}
	
	private long main_region_Moving_inner_region_WasteGrabbed_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0) {
			if (atGarbageDisposal) {
				exitSequence_main_region_Moving();
				enterSequence_main_region_ReleasingWaste_default();
				transitioned_after = 0;
			}
		}
		/* If no transition was taken then execute local reactions */
		if (transitioned_after==transitioned_before) {
			transitioned_after = main_region_Moving_react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long main_region_Moving_inner_region_WasteDetected_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0) {
			if (nearWaste) {
				exitSequence_main_region_Moving();
				enterSequence_main_region_NearWaste_default();
				transitioned_after = 0;
			}
		}
		/* If no transition was taken then execute local reactions */
		if (transitioned_after==transitioned_before) {
			transitioned_after = main_region_Moving_react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long main_region_Moving_inner_region_Forward_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<0) {
			if (wasteDetected) {
				exitSequence_main_region_Moving_inner_region_Forward();
				enterSequence_main_region_Moving_inner_region_WasteDetected_default();
				main_region_Moving_react(0);
				
				transitioned_after = 0;
			}
		}
		/* If no transition was taken then execute local reactions */
		if (transitioned_after==transitioned_before) {
			transitioned_after = main_region_Moving_react(transitioned_before);
		}
		return transitioned_after;
	}
	
	private long _region1_checkObstacle_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<1) {
			if (timeEvents[0]) {
				exitSequence__region1_checkObstacle();
				enterSequence__region1_checkObstacle_default();
				transitioned_after = 1;
			} else {
				if (leftObstacleDetected) {
					exitSequence__region1_checkObstacle();
					enterSequence__region1_StopChecking_default();
					transitioned_after = 1;
				} else {
					if (rightObstacleDetected) {
						exitSequence__region1_checkObstacle();
						enterSequence__region1_StopChecking_default();
						transitioned_after = 1;
					}
				}
			}
		}
		return transitioned_after;
	}
	
	private long _region1_StopChecking_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<1) {
			if (noObstacle) {
				exitSequence__region1_StopChecking();
				enterSequence__region1_checkObstacle_default();
				transitioned_after = 1;
			}
		}
		return transitioned_after;
	}
	
	private long fake_f_react(long transitioned_before) {
		long transitioned_after = transitioned_before;
		
		if (transitioned_after<2) {
		}
		/* If no transition was taken then execute local reactions */
		if (transitioned_after==transitioned_before) {
			if (timeEvents[1]) {
				raiseFakeEvent();
			}
			transitioned_after = react(transitioned_before);
		}
		return transitioned_after;
	}
	
}
