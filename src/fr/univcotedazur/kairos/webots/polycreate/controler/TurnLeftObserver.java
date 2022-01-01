package fr.univcotedazur.kairos.webots.polycreate.controler;
import com.yakindu.core.rx.Observer;


public class TurnLeftObserver implements Observer<Void>{
	
	PolyCreateControler controler;
	
	public TurnLeftObserver(PolyCreateControler controler) {
		this.controler = controler;
	}

	@Override
	public void next(Void value) {
		System.out.println("Turn left");
		controler.turnLeft();
	}

	

}
