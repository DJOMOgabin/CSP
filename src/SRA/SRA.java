package SRA;

import FiltrageSimple.Discours;
import FiltrageSimple.Longueur;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import java.util.ArrayList;

public class SRA {

	private ArrayList<Longueur> variable;//Notre tableau de variable
	private ArrayList<Longueur> domaine;//Notre domaine de rouleau et leur longueur
	private ArrayList<Double> reste=new ArrayList<Double>();//le ArrayList de longueur de rouleau de cable
	private Hashtable affectation;//L'ensemble de fils qui ont d�j� �t� coup�s et le rouleau dans lequel ils ont �t� coup�
	private Hashtable tableau=new Hashtable();//L'ensemble de fils et leur domaine o� ils pourraient �tre coup�s
	private String tete="";
	public static int compteur=0;
	
	public SRA(ArrayList<Longueur> variable,ArrayList<Longueur> domaine){
		this.variable=variable;
		this.domaine=domaine;
		Retour();
	}
	
	public void Retour() {

		//Initialiser le reste des fils restant dans chaque rouleau
		for (int i = 0; i < getDomaine().size(); i++) getReste().add(getDomaine().get(i).getLongueur());

		//Je forme ma table de variable avec leur domaine
		//Je dois cloner le domaine pour qu'ils aient pas � manipuler le m�me domaine 
		for (int i = 0; i < getVariable().size(); i++) getTableau().put(getVariable().get(i), getDomaine().clone());
		//Je forme l'ent�te de ma pr�sentation
		this.tete = "---------------------------------------------------------------------------\n";
		this.tete += "------------------------ SIMPLE RETOUR EN ARRIERE -------------------------\n";
		this.tete += "---------------------------------------------------------------------------\n";

		//Nous essayons d'afficher tout ceci sur un �cran
		Discours.ReiniDiscours();
		Discours.setDiscours(this.tete);
		Discours.setDiscours("\n++++ Nous avons les affectations suivantes: ++++\n\n");
		new LabelSRA(new Hashtable(), getVariable(), true);
		new SimpleRetourArriere(new Hashtable(), getVariable(), getTableau(), getReste(), 0);
		if (compteur != 0) Discours.setDiscours("\n\n\nNOTRE CONFIGURATION COMPTE " + SRA.compteur + " SOLUTIONS!!!");
		else Discours.setDiscours("\n\n\nNOTRE CONFIGURATION N'A PAS SOLUTIONS!!!");
		SRA.compteur = 0;
	}

	
	public ArrayList<Longueur> getVariable() {
		return variable;
	}

	public void setVariable(ArrayList<Longueur> variable) {
		this.variable = variable;
	}

	public ArrayList<Longueur> getDomaine() {
		return domaine;
	}

	public void setDomaine(ArrayList<Longueur> domaine) {
		this.domaine = domaine;
	}

	public ArrayList<Double> getReste() {
		return reste;
	}

	public void setReste(ArrayList<Double> reste) {
		this.reste = reste;
	}

	public Hashtable getAffectation() {
		return affectation;
	}

	public void setAffectation(Hashtable affectation) {
		this.affectation = affectation;
	}

	public Hashtable getTableau() {
		return tableau;
	}

	public void setTableau(Hashtable tableau) {
		this.tableau = tableau;
	}
	
	
}
