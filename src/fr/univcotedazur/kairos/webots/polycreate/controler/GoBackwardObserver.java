package fr.univcotedazur.kairos.webots.polycreate.controler;
import com.yakindu.core.rx.Observer;


public class GoBackwardObserver implements Observer<Void> {
	
	PolyCreateControler controler;
	
	public GoBackwardObserver(PolyCreateControler controler) {
		this.controler = controler;
	}

	@Override
	public void next(Void value) {
		System.out.println("Moving backward");
		controler.doGoBackward();
	}

}
