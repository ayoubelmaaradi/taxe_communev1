/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Secteur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP
 */
@Stateless
public class SecteurFacade extends AbstractFacade<Secteur> {

    @PersistenceContext(unitName = "projet_java_taxPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecteurFacade() {
        super(Secteur.class);
    }
    public void clone(Secteur secteurSource, Secteur secteurDestaination) {
        secteurDestaination.setId(secteurSource.getId());
        secteurDestaination.setAbreviation(secteurSource.getAbreviation());
        secteurDestaination.setNomSecteur(secteurSource.getNomSecteur());

    }

    public Secteur clone(Secteur secteur) {
        Secteur cloned = new Secteur();
        clone(secteur, cloned);
        return cloned;
    }
    
}
