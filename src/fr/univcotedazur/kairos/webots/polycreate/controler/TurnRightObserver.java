package fr.univcotedazur.kairos.webots.polycreate.controler;
import com.yakindu.core.rx.Observer;


public class TurnRightObserver implements Observer<Void>{
	
	PolyCreateControler controler;
	
	public TurnRightObserver(PolyCreateControler controler) {
		this.controler = controler;
	}

	@Override
	public void next(Void value) {
		System.out.println("Turn Right");
		controler.turnRight();
	}

}
