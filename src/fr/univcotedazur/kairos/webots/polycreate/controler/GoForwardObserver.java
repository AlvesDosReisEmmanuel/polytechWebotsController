package fr.univcotedazur.kairos.webots.polycreate.controler;
import com.yakindu.core.rx.Observer;

public class GoForwardObserver implements Observer<Void>  {
	PolyCreateControler controler;
	
	public GoForwardObserver(PolyCreateControler controler) {
		this.controler = controler;
	}

	@Override
	public void next(Void value) {
		System.out.println("Moving forward");
		controler.doGoForward();
	}

}






	
	