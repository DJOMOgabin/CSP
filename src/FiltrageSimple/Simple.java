package FiltrageSimple;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import java.util.ArrayList;

public class Simple {

	public Simple(ArrayList<Longueur> variable, ArrayList<Longueur> domaine,boolean typeFiltre) {
		Analyse(variable,domaine,typeFiltre);
	}

	public static boolean branche=true;
	
	public void Analyse(ArrayList<Longueur> variable, ArrayList<Longueur> domaine,boolean typeFiltre){
		
		ArrayList<Double> reste = new ArrayList<Double>();
		Hashtable tableau = new Hashtable();
		//ArrayList<String> discour = new ArrayList<String>();
		Hashtable affectation = new Hashtable();
		TestePuisEnumere.compteur=0;

		//Initialement, le reste est �gale au nombre de rouleau et leur longueur
		for(int j=0;j<domaine.size();j++)	reste.add(domaine.get(j).getLongueur());
		//Je forme ma table de variables avec leur domaine
		for(int i=0;i<variable.size();i++) tableau.put(variable.get(i),domaine.clone());//Je clone les domaines de d�finitions pour qu'il aient le m�me
																						//domaine mais ne manipule pas le m�me objet
		
		//On commence � �crire le label
		Discours.ReiniDiscours();
		String tete =       "---------------------------------------------------------------------------\n";
		if(typeFiltre)tete+="----------- ALGORITHME TESTER PUIS ENUMERER (Filtrage Simple) -------------\n";
		else tete+=         "----------- ALGORITHME TESTER PUIS ENUMERER (Filtrage Fort) ---------------\n";
		tete+=              "---------------------------------------------------------------------------\n";
		Discours.setDiscours(tete);		
		//On filtre le domaine initiale
		Hashtable filtre;
		if(typeFiltre)filtre = new FiltrageDomaine(tableau, variable, reste, -1).getDomaine();
		else filtre = new FiltrageDomaineFort(tableau, variable, reste, -1).getDomaine();
		//Je fait un discours sur le d�coupage d�j� fait et celui qui reste � faire du domaine initiale
		new Label(affectation, filtre, new ArrayList<Longueur>(), variable,reste);
		//Si on ne trouve aucune solution, 
		//On appelle la fonction qui �ffectue l'agorithme de mani�re it�re
		new TestePuisEnumere(affectation, variable, tableau, reste, 0,typeFiltre);
		if(TestePuisEnumere.compteur!=0)Discours.setDiscours("\n\nNOTRE CONFIGURATION COMPTE "+TestePuisEnumere.compteur+" SOLUTIONS!!!");
		else Discours.setDiscours("\n\nNOTRE CONFIGURATION COMPTE N'A PAS DE SOLUTIONS!!!");
		
		
	}

}
