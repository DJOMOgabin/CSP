package SRA;

import java.util.ArrayList;

import FiltrageSimple.Longueur;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

public class SimpleRetourArriere {

	private Hashtable affectation;
	private ArrayList<Longueur> variable;
	private Hashtable domaine;
	private ArrayList<Double> contrainte;
	private int indexValeur;
	
	public SimpleRetourArriere(Hashtable affectation ,ArrayList<Longueur> variable,Hashtable domaine, ArrayList<Double> contrainte,int indexValeur){
	
		this.affectation=affectation;
		this.variable=variable;
		this.domaine=domaine;
		this.contrainte=contrainte;
		this.indexValeur=indexValeur;
		if(getIndexValeur()<getVariable().size())Calcul();
		else return;
	}
	
	@SuppressWarnings("unchecked")
	public void Calcul(){
		double valeur;
		int indice;
		//for(int i=getIndexValeur();i<getVariable().size();i++){
			for(int j=((ArrayList<Longueur>)getDomaine().get(getVariable().get(getIndexValeur()))).size()-1;j>=0;j--){
				valeur=getVariable().get(getIndexValeur()).getLongueur();
				indice = ((ArrayList<Longueur>)getDomaine().get(getVariable().get(getIndexValeur()))).get(j).getIndex();
				//On doit ajouter la valeur dans l'affectattion pour quelle soit consistante
				getAffectation().put(getVariable().get(getIndexValeur()),
						((ArrayList<Longueur>)getDomaine().get(getVariable().get(getIndexValeur()))).get(j));
				//Si l'affectation est consistant
				if(getContrainte().get(indice)-valeur>=0){
					//Nous reduisons le fil dans le rouleau correspondant
					getContrainte().set(indice, getContrainte().get(indice)-valeur);
					//Nous affichons à l'écran pour dire que c'est éffectivement consistant
					new LabelSRA(getAffectation(),getVariable(),true);
					//Nous passons à la prochaine variable
					new SimpleRetourArriere(getAffectation(), getVariable(), getDomaine(), getContrainte(), getIndexValeur()+1);
					//Une fois une solution trouvée, nous passons enlevons la dernière variable et nous passons à la valeur 
					//suivante de cette variable
					getContrainte().set(indice, getContrainte().get(indice)+valeur);					
					getAffectation().remove(getVariable().get(getIndexValeur()));
				}else{
					//Nous affichons à l'écran pour dire que cette affectation n'est pas consistante
					new LabelSRA(getAffectation(),getVariable(),false);
					//Nous enlevons cette dernière affectation qui l'a rend inconsistante
					getAffectation().remove(getVariable().get(getIndexValeur()));
				}
			}
		//}
	}
	
	
	public Hashtable getAffectation() {
		return affectation;
	}
	public void setAffectation(Hashtable affectation) {
		this.affectation = affectation;
	}
	public ArrayList<Longueur> getVariable() {
		return variable;
	}
	public void setVariable(ArrayList<Longueur> variable) {
		this.variable = variable;
	}
	public Hashtable getDomaine() {
		return domaine;
	}
	public void setDomaine(Hashtable domaine) {
		this.domaine = domaine;
	}
	public ArrayList<Double> getContrainte() {
		return contrainte;
	}
	public void setContrainte(ArrayList<Double> contrainte) {
		this.contrainte = contrainte;
	}
	public int getIndexValeur() {
		return indexValeur;
	}
	public void setIndexValeur(int indexValeur) {
		this.indexValeur = indexValeur;
	}

}
