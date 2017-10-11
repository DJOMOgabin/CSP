package FiltrageSimple;

import java.util.ArrayList;


public class Fort {


	private Longueur variable;
	private ArrayList<Longueur> domaine;
	private ArrayList<Double> contrainte;
	private boolean estVide=false;
	
	public Fort(Longueur variable, ArrayList<Longueur> domaine,ArrayList<Double> contrainte){
		this.variable=variable;
		this.domaine=domaine;
		this.contrainte=contrainte;
		Calcul();
	}
	
	
	public void Calcul(){
		for(int i=0;i<domaine.size();i++){
			if(getContrainte().get(getDomaine().get(i).getIndex())<getVariable().getLongueur()){
				getDomaine().remove(i);
				i--;
			}else{
				setEstVide(false);
				break;
			}
		}
		if(getDomaine().isEmpty())setEstVide(true);
	}

	public Longueur getVariable() {
		return variable;
	}

	public void setVariable(Longueur variable) {
		this.variable = variable;
	}

	public ArrayList<Longueur> getDomaine() {
		return domaine;
	}

	public void setDomaine(ArrayList<Longueur> domaine) {
		this.domaine = domaine;
	}

	public ArrayList<Double> getContrainte() {
		return contrainte;
	}

	public void setContrainte(ArrayList<Double> contrainte) {
		this.contrainte = contrainte;
	}

	public boolean isEstVide() {
		return estVide;
	}

	public void setEstVide(boolean estVide) {
		this.estVide = estVide;
	}
	
}
