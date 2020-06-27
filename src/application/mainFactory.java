package application;

public class mainFactory {
	private static Main main;
	private mainFactory() {

	}

	public static Main createMain() {
		if (main == null)
			main = new Main();
		return main;

	}

}
