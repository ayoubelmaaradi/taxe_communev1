/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.TauxTaxeRetard;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP
 */
@Stateless
public class TauxTaxeRetardFacade extends AbstractFacade<TauxTaxeRetard> {

    @PersistenceContext(unitName = "projet_java_taxPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TauxTaxeRetardFacade() {
        super(TauxTaxeRetard.class);
    }
     public void clone(TauxTaxeRetard tauxTaxeRetardSource, TauxTaxeRetard tauxTaxeRetardDestaination) {
        tauxTaxeRetardDestaination.setId(tauxTaxeRetardSource.getId());
        tauxTaxeRetardDestaination.setCategorie(tauxTaxeRetardSource.getCategorie());
        tauxTaxeRetardDestaination.setTauxAutreRetard(tauxTaxeRetardSource.getTauxAutreRetard());
        tauxTaxeRetardDestaination.setTauxPremierRetard(tauxTaxeRetardSource.getTauxPremierRetard());

    }

    public TauxTaxeRetard clone(TauxTaxeRetard tauxTaxeRetard) {
        TauxTaxeRetard cloned = new TauxTaxeRetard();
        clone(tauxTaxeRetard, cloned);
        return cloned;
    }
    
}
