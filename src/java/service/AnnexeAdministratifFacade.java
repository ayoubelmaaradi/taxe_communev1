/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.AnnexeAdministratif;
import bean.Secteur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP
 */
@Stateless
public class AnnexeAdministratifFacade extends AbstractFacade<AnnexeAdministratif> {
 @PersistenceContext(unitName = "projet_java_taxPU")
    private EntityManager em;

    public List<AnnexeAdministratif> findBySecteur(Secteur secteur) {
        return em.createQuery("SELECT a FROM AnnexeAdministratif a WHERE a.secteur.id =" + secteur.getId()).getResultList();
    }

    public List<AnnexeAdministratif> findByName(String nom) {
        return em.createQuery("SELECT a FROM AnnexeAdministratif a WHERE a.nom='" + nom + "'").getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnnexeAdministratifFacade() {
        super(AnnexeAdministratif.class);
    }

     public void clone(AnnexeAdministratif annexeAdministratifSource,AnnexeAdministratif annexeAdministratifDestaination){
        annexeAdministratifDestaination.setAbreviation(annexeAdministratifSource.getAbreviation());
        annexeAdministratifDestaination.setNom(annexeAdministratifSource.getNom());
        annexeAdministratifDestaination.setId(annexeAdministratifSource.getId());
        annexeAdministratifDestaination.setSecteur(annexeAdministratifSource.getSecteur());
        
    }
    public AnnexeAdministratif clone(AnnexeAdministratif annexeAdministratif){
        AnnexeAdministratif cloned=new AnnexeAdministratif();
        clone(annexeAdministratif, cloned);
        return cloned;
    }
}
