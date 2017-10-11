package FiltrageSimple;

import java.util.ArrayList;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

public class TestePuisEnumere {

	private Hashtable affectation;
	private Hashtable nonAffecte = new Hashtable();
	private ArrayList<Longueur> variable;
	private ArrayList<Longueur> listNonAffecte=new ArrayList<Longueur>();
	private ArrayList<Longueur> listAffecte=new ArrayList<Longueur>();
	private Hashtable domaine;
	private ArrayList<Double> contrainte;
	private int indexValeur;
	private Hashtable domaineSecond=new Hashtable();
	private Hashtable filtrage;
	private boolean leFiltrage;
	public static int compteur;
	
	public TestePuisEnumere(Hashtable affectation,ArrayList<Longueur> variable,Hashtable domaine,ArrayList<Double> contrainte,int indexValeur,boolean leFiltrage){
	
		this.affectation=affectation;
		this.variable=variable;
		this.domaine=domaine;
		this.contrainte=contrainte;
		this.indexValeur=indexValeur;
		this.leFiltrage=leFiltrage;
		if(indexValeur<variable.size())Calcul();//Condition d'arrêt de ma boucle de récursivité,
												//l'indice de la variable doit être inférieur au nombre de variable 
		else return;
	}
	
	@SuppressWarnings("unchecked")
	public void Calcul(){
		//Réinitialisation de tous les ensembles
		getListAffecte().clear();
		getListNonAffecte().clear();
		getNonAffecte().clear();
		
		for(int i=0;i<=getIndexValeur();i++){//Je détermine la liste des fils déjà coupés
			getListAffecte().add(getVariable().get(i));
		}
		
		int indice;
		//Je parcourre le domaine du fil que nous voulons couper		
		for(int i=0;i<((ArrayList<Longueur>)getDomaine().get(getVariable().get(getIndexValeur()))).size();i++){
			//Je clone le domaine que je vais filtrer
			for(int j=0;j<getVariable().size();j++){
				domaineSecond.put(getVariable().get(j), (((ArrayList<Longueur>)getDomaine().get(getVariable().get(j))).clone()));
			}
			indice=((ArrayList<Longueur>)getDomaine().get(getVariable().get(getIndexValeur()))).get(i).getIndex();
			if(getContrainte().get(indice)-getVariable().get(getIndexValeur()).getLongueur()>=0){
				//Je coupe dans le rouleau d'indice getIndex le fil d'index getIndexValeur
				getContrainte().set(indice,getContrainte().get(indice)-getVariable().get(getIndexValeur()).getLongueur());
				//Je filtre les domaines avant de faire d'itérer le processus
				if(this.leFiltrage)filtrage = new FiltrageDomaine(domaineSecond, getVariable(), getContrainte(), getIndexValeur()).getDomaine();
				else filtrage = new FiltrageDomaineFort(domaineSecond, getVariable(), getContrainte(), getIndexValeur()).getDomaine();
				
				//Je détermine la liste des fils non coupés, et leur domaine restant
				for(int j=getIndexValeur()+1;j<getVariable().size();j++){
					getListNonAffecte().add(getVariable().get(j));
					getNonAffecte().put(getVariable().get(j), filtrage.get(getVariable().get(i)));
				}
				//J'affecte dans l'ensemble des fils coupés, le fil qu'on vient de couper
				getAffectation().put(getVariable().get(getIndexValeur()), ((ArrayList<Longueur>)getDomaine().get(getVariable().get(getIndexValeur()))).get(i));
				//Je fait un discours sur le découpage déjà fait et celui qui reste à faire
				if(new Label(getAffectation(), getNonAffecte(), getListAffecte(), getListNonAffecte(),getContrainte()).isEtat())return;
				new TestePuisEnumere(getAffectation(), getVariable(), filtrage, getContrainte(), getIndexValeur()+1,this.leFiltrage);
					getContrainte().set(((ArrayList<Longueur>)getDomaine().get(getVariable().get(getIndexValeur()))).get(i).getIndex(),
							getContrainte().get(indice)+ getVariable().get(getIndexValeur()).getLongueur());
					getAffectation().remove(getVariable().get(getIndexValeur()));
				Discours.setDiscours("************************************************************************************\n");
			}
			domaineSecond.clear();
			getListNonAffecte().clear();
			getNonAffecte().clear();
		}
	}

	public Hashtable getAffectation() {
		return affectation;
	}

	public void setAffectation(Hashtable affectation) {
		this.affectation = affectation;
	}

	public Hashtable getNonAffecte() {
		return nonAffecte;
	}

	public void setNonAffecte(Hashtable nonAffecte) {
		this.nonAffecte = nonAffecte;
	}

	public ArrayList<Longueur> getVariable() {
		return variable;
	}

	public void setVariable(ArrayList<Longueur> variable) {
		this.variable = variable;
	}

	public ArrayList<Longueur> getListNonAffecte() {
		return listNonAffecte;
	}

	public void setListNonAffecte(ArrayList<Longueur> listNonAffecte) {
		this.listNonAffecte = listNonAffecte;
	}

	public ArrayList<Longueur> getListAffecte() {
		return listAffecte;
	}

	public void setListAffecte(ArrayList<Longueur> listAffecte) {
		this.listAffecte = listAffecte;
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
