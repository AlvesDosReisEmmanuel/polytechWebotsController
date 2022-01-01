package fr.univcotedazur.kairos.webots.polycreate.controler;
import com.yakindu.core.rx.Observer;

public class FullTurnObserver implements Observer<Void> {
	
	PolyCreateControler controler;
	
	public FullTurnObserver(PolyCreateControler controler) {
		this.controler = controler;
	}

	@Override
	public void next(Void value) {
		System.out.println("Full turn");
		controler.FullTurn();
	}


}
