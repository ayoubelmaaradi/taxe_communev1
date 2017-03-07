/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.TaxeAnnuel;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP
 */
@Stateless
public class TaxeAnnuelFacade extends AbstractFacade<TaxeAnnuel> {

    @PersistenceContext(unitName = "projet_java_taxPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaxeAnnuelFacade() {
        super(TaxeAnnuel.class);
    }
     public void clone(TaxeAnnuel taxeAnnuelSource, TaxeAnnuel taxeAnnuelDestaination) {
        taxeAnnuelDestaination.setId(taxeAnnuelSource.getId());
        taxeAnnuelDestaination.setAnnee(taxeAnnuelSource.getAnnee());
        taxeAnnuelDestaination.setLocale(taxeAnnuelSource.getLocale());
        taxeAnnuelDestaination.setNbrTrimesterPaye(taxeAnnuelSource.getNbrTrimesterPaye());
        taxeAnnuelDestaination.setTaxeTotale(taxeAnnuelSource.getTaxeTotale());

    }

    public TaxeAnnuel clone(TaxeAnnuel taxeAnnuel) {
        TaxeAnnuel cloned = new TaxeAnnuel();
        clone(taxeAnnuel, cloned);
        return cloned;
    }
    
}
