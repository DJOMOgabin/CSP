package FiltrageSimple;

import java.util.ArrayList;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

//Nous filtrons les domaines des fils qui ne sont pas encore coupés
public class FiltrageDomaine {
	
	private Hashtable domaine;//La table de variable et leur domaine
	private ArrayList<Double> contrainte;//La liste de fils restant dans chaque rouleau
	private int indexValeur;//L'indice du fils dont nous voulons couper dans un rouleau
	private ArrayList<Longueur> variable;//La liste des variables(des fils)
	private ArrayList<Longueur> domaineFiltrer=new ArrayList<Longueur>();//On remet les valeurs des elements precedamment filtré
	
	public FiltrageDomaine(Hashtable domaine,ArrayList<Longueur> variable, ArrayList<Double> contrainte,int indexValeur){
		this.domaine=domaine;
		this.contrainte=contrainte;
		this.indexValeur=indexValeur;
		this.variable=variable;
		Filtre();
	}
	
	@SuppressWarnings("unchecked")
	public void Filtre(){
		for(int j=getIndexValeur()+1;j<getVariable().size();j++){//On filtre les domaines dans l'ordre croissant de l'heuristique
			for(int k=0;k<((ArrayList<Longueur>)getDomaine().get(getVariable().get(j))).size();k++){//On parcourre le domaine de chaque variables non affectées
				if(getContrainte().get(((ArrayList<Longueur>)getDomaine().get(getVariable().get(j))).get(k).getIndex())-getVariable().get(j).getLongueur()<0){
					((ArrayList<Longueur>)getDomaine().get(getVariable().get(j))).remove(k);
					k--;
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

	public ArrayList<Longueur> getVariable() {
		return variable;
	}

	public ArrayList<Longueur> getDomaineFiltrer() {
		return domaineFiltrer;
	}

	public void setDomaineFiltrer(ArrayList<Longueur> domaineFiltrer) {
		this.domaineFiltrer = domaineFiltrer;
	}

	public void setVariable(ArrayList<Longueur> variable) {
		this.variable = variable;
	}
	
}
