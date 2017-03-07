/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.TauxTaxe;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP
 */
@Stateless
public class TauxTaxeFacade extends AbstractFacade<TauxTaxe> {

    @PersistenceContext(unitName = "projet_java_taxPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TauxTaxeFacade() {
        super(TauxTaxe.class);
    }
    public void clone(TauxTaxe tauxTaxeSource, TauxTaxe tauxTaxeDestaination) {
        tauxTaxeDestaination.setId(tauxTaxeSource.getId());
        tauxTaxeDestaination.setCategorie(tauxTaxeSource.getCategorie());
        tauxTaxeDestaination.setTaux(tauxTaxeSource.getTaux());

    }

    public TauxTaxe clone(TauxTaxe tauxTaxe) {
        TauxTaxe cloned = new TauxTaxe();
        clone(tauxTaxe, cloned);
        return cloned;
    }
    
    
}
