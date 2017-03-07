/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.AnnexeAdministratif;
import bean.Quartier;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP
 */
@Stateless
public class QuartierFacade extends AbstractFacade<Quartier> {

   
    @PersistenceContext(unitName = "projet_java_taxPU")
    private EntityManager em;

    public List<Quartier> findByAnnexe(AnnexeAdministratif administratif) {
        return em.createQuery("SELECT q FROM Quartier q WHERE q.annexeAdministratif.id =" + administratif.getId()).getResultList();
    }
    public List<Quartier> findByName(String nom) {
        return em.createQuery("SELECT q FROM Quartier q WHERE q.nom='" + nom + "'").getResultList();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuartierFacade() {
        super(Quartier.class);
    }
    public void clone(Quartier quartierSource, Quartier quartierDestaination) {
        quartierDestaination.setId(quartierSource.getId());
        quartierDestaination.setAnnexeAdministratif(quartierSource.getAnnexeAdministratif());
        quartierDestaination.setNom(quartierSource.getNom());
        quartierDestaination.setNumAbreviation(quartierSource.getNumAbreviation());

    }

    public Quartier clone(Quartier quartier) {
        Quartier cloned = new Quartier();
        clone(quartier, cloned);
        return cloned;
    }
}
