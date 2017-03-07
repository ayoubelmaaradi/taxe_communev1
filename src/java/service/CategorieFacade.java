/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Categorie;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP
 */
@Stateless
public class CategorieFacade extends AbstractFacade<Categorie> {

    @PersistenceContext(unitName = "projet_java_taxPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategorieFacade() {
        super(Categorie.class);
    }
    
    
     public void clone(Categorie categorieSource,Categorie categorieDestaination){
        categorieDestaination.setId(categorieSource.getId());
        categorieDestaination.setNom(categorieSource.getNom());
       
    }
    public Categorie clone(Categorie categorie){
        Categorie cloned=new Categorie();
        clone(categorie, cloned);
        return cloned;
    }
}
