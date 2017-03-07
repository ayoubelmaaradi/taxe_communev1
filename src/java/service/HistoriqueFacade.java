/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Historique;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ayoub
 */
@Stateless
public class HistoriqueFacade extends AbstractFacade<Historique> {

    @PersistenceContext(unitName = "projet_java_taxPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoriqueFacade() {
        super(Historique.class);
    }

    public void clone(Historique historiqueSource, Historique historiqueDestaination) {
        historiqueDestaination.setId(historiqueSource.getId());

    }

    public Historique clone(Historique historique) {
        Historique cloned = new Historique();
        clone(historique, cloned);
        return cloned;
    }

}
