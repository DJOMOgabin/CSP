package FiltrageSimple;

import java.util.ArrayList;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

public class FiltrageDomaineFort {
	
	private Hashtable domaine;//La table de variable et leur domaine
	private ArrayList<Longueur> variable;//l'ensemble des variables
	private ArrayList<Double> contrainte;//la liste des longueurs des fils restant dans les rouleau
	private int indexValeur;//L'indice du fil donc nous voulons couper dans le rouleau
	private ArrayList<Longueur> testFiltreur;//Une copie d'un domaine qui va permettre de filtrer le domaine qui le précède  
	private boolean etat;

	public FiltrageDomaineFort(Hashtable domaine,ArrayList<Longueur> variable, ArrayList<Double> contrainte,int indexValeur) {
		// TODO Auto-generated constructor stub
		this.domaine=domaine;
		this.variable=variable;
		this.contrainte=contrainte;
		this.indexValeur=indexValeur;
		FiltreFort();
	}
	
	@SuppressWarnings("unchecked")
	public void FiltreFort(){
		double possi;
		int indice;
		ArrayList<Double> testContrainte;
		for(int i=getIndexValeur()+1;i<getVariable().size();i++){//On fait varier les variables non affectées
			for(int j=0;j<((ArrayList<Longueur>)getDomaine().get(getVariable().get(i))).size();j++){//On parcourre le domaine de chaque variable
																									//non affectées
				testContrainte=(ArrayList<Double>)getContrainte().clone();
				indice=((ArrayList<Longueur>)getDomaine().get(getVariable().get(i))).get(j).getIndex();
				if(testContrainte.get(indice)-getVariable().get(i).getLongueur()>=0){
					//On prend la valeur du rouleau si on suppose qu'il est coupé					
					possi = testContrainte.get(indice)-getVariable().get(i).getLongueur();
					testContrainte.set(indice, possi);
					for(int k=i+1;k<getVariable().size();k++){//On fait varier la seconde valeur du couple de variables non affectées 
														  	 //celui  qui va nous permettre de filtre le domaine de la variable i
						this.testFiltreur=(ArrayList<Longueur>)((ArrayList<Longueur>)getDomaine().get(getVariable().get(k))).clone();
						//Si le second ensemble est vide, on enlève du domaine du premier ensemble la valeur du rouleau qui le rend vide
						etat=new Fort(getVariable().get(k), getTestFiltreur(), testContrainte).isEstVide();
						if(etat){
							//On remet l'ancienne valeur du fil supposé coupé
							Discours.setDiscours(""+etat+" "+getVariable().get(i).getIndex()+
									" "+((ArrayList<Longueur>)getDomaine().get(getVariable().get(i))).get(j).getLongueur()+"\n");
							possi = getContrainte().get(indice)+getVariable().get(i).getLongueur();
							testContrainte.set(indice, possi);
							((ArrayList<Longueur>)getDomaine().get(getVariable().get(i))).remove(j);
							j--;
							break; 
						}
						this.testFiltreur.clear();
					}		
					testContrainte.clear();
				}else{
					((ArrayList<Longueur>)getDomaine().get(getVariable().get(i))).remove(j);
					j--;
					testContrainte.clear();
				}
			}
		}
	}

	public Hashtable getDomaine() {
		return domaine;
	}

	public void setDomaine(Hashtable domaine) {
		this.domaine = domaine;
	}

	public ArrayList<Longueur> getVariable() {
		return variable;
	}

	public void setVariable(ArrayList<Longueur> variable) {
		this.variable = variable;
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

	public ArrayList<Longueur> getTestFiltreur() {
		return testFiltreur;
	}

	public void setTestFiltreur(ArrayList<Longueur> testFiltreur) {
		this.testFiltreur = testFiltreur;
	}

}
