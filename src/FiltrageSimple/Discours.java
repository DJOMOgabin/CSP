package FiltrageSimple;

//Nous créons une variable dans laquelle nous allons mettre notre 
//commentaire pour pouvoir l'appeller partout dans le programme
public class Discours {
	
	private static String discours="";
	
	public static String getDiscours(){
		return Discours.discours;
	}
	
	public static void setDiscours(String discours){
		Discours.discours+=discours;
	}
	
	public static void ReiniDiscours(){
		Discours.discours="";
	}

	/**
	 * @param args
	 */
	

}
