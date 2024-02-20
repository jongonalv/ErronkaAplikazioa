package arteMartzialak;

import javax.swing.UIManager;

public class fitxategiakKudeatuMain {
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}
		lehioItxura.jarriElementuak();
	}
}
