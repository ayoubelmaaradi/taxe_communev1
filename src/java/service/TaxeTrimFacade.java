/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.AnnexeAdministratif;
import bean.Categorie;
import bean.Quartier;
import bean.Rue;
import bean.Secteur;
import bean.TaxeTrim;
import controler.util.SearchUtil;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP
 */
@Stateless
public class TaxeTrimFacade extends AbstractFacade<TaxeTrim> {

    @PersistenceContext(unitName = "projet_java_taxPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaxeTrimFacade() {
        super(TaxeTrim.class);
    }

    public void clone(TaxeTrim taxeTrimSource, TaxeTrim taxeTrimDestaination) {
        taxeTrimDestaination.setId(taxeTrimSource.getId());
        taxeTrimDestaination.setAutresMoisRetard(taxeTrimSource.getAutresMoisRetard());
        taxeTrimDestaination.setLocale(taxeTrimSource.getLocale());
        taxeTrimDestaination.setDatePaiement(taxeTrimSource.getDatePaiement());
        taxeTrimDestaination.setMontant(taxeTrimSource.getMontant());
        taxeTrimDestaination.setMontantRetard(taxeTrimSource.getMontantRetard());
        taxeTrimDestaination.setMontantTotal(taxeTrimSource.getMontantTotal());
        taxeTrimDestaination.setNbrMoisRetard(taxeTrimSource.getNbrMoisRetard());
        taxeTrimDestaination.setNombreClients(taxeTrimSource.getNombreClients());
        taxeTrimDestaination.setNombreNuit(taxeTrimSource.getNombreNuit());
        taxeTrimDestaination.setNumeroTrim(taxeTrimSource.getNumeroTrim());
        taxeTrimDestaination.setRedevable(taxeTrimSource.getRedevable());
        taxeTrimDestaination.setTaxeAnnuel(taxeTrimSource.getTaxeAnnuel());

    }

    public TaxeTrim clone(TaxeTrim taxeTrim) {
        TaxeTrim cloned = new TaxeTrim();
        clone(taxeTrim, cloned);
        return cloned;
    }
    
    public List<TaxeTrim> findTaxByCritere(String activite, int firstYear, int secondYear, Rue rue, Quartier quartier, AnnexeAdministratif annex, Secteur secteur) {
        String rqt = "SELECT tax FROM TaxeTrim tax where 1=1";
        if (!activite.equals("")) {
            rqt += SearchUtil.addConstraint("tax.locale", "activite", "=", activite);
        }
        if (firstYear > 0 & secondYear > 0) {
            rqt += "AND tax.taxeAnnuel in (" + firstYear + "," + secondYear + ")";
        }
        if (rue != null) {
            rqt += "AND tax.local.rue.id=" + rue.getId();
        }
        if (quartier != null) {
            rqt += "tax.local.rue.quartier.id=" + quartier.getId();
        }
        if (annex != null) {
            rqt += "AND tax.local.rue.quartier.annexeAdministratif.id=" + annex.getId();
        }
        if (secteur != null) {
            rqt += "AND tax.local.rue.quartier.annexeAdministratif.secteur.id=" + secteur.getId();
        }

        return em.createQuery(rqt).getResultList();
    }
    // hadi recherche ta3 taxetrime bga3 les crétére 
    public List<TaxeTrim> findLocaleByCretere(Date dateMin, Date dateMax, Double montantMin, Double montantMax, int nombreNuitMin, int nombreNuitMax, String local, String redevable, Categorie categorie, Secteur secteur, AnnexeAdministratif annexeAdministratif, Quartier quartier, Rue rue) {
        {
            String requete = "SELECT ta FROM TaxeTrim ta WHERE 1=1  ";
            requete += SearchUtil.addConstraintMinMaxDate("ta", "datePaiement", dateMin, dateMax);
            if (!local.equals("")) {
                requete += " AND ta.locale.reference='" + local + "'";

            }
            if (!redevable.equals("")) {
                requete += " AND ta.redevable.cin='" + redevable + "'";

            }
            if (categorie != null) {
                requete += " AND ta.locale.categorie.nom='" + categorie.getNom() + "'";
            }
            if (secteur != null) {
                requete += " AND ta.locale.rue.quartier.annexeAdministratif.secteur='" + secteur.getNomSecteur() + "'";
            }
            if (annexeAdministratif != null) {
                requete += " AND ta.locale.rue.quartier.annexeAdministratif='" + annexeAdministratif.getNom() + "'";
            }

            if (quartier != null) {
                requete += " AND ta.locale.rue.quartier='" + quartier.getNom() + "'";
            }
            if (rue != null) {
                requete += " AND ta.locale.rue='" + rue.getNom() + "'";
            }
            requete += SearchUtil.addConstraintMinMax("ta", "montantTotal", montantMin, montantMax);
            if (nombreNuitMin < 0 && nombreNuitMax < 0) {
                requete += SearchUtil.addConstraintMinMax("ta", "nombreNuit", null, null);
            } else if (nombreNuitMin < 0) {
                requete += SearchUtil.addConstraintMinMax("ta", "nombreNuit", null, nombreNuitMax);

            } else if (nombreNuitMax < 0) {
                requete += SearchUtil.addConstraintMinMax("ta", "nombreNuit", nombreNuitMin, null);

            } else if (nombreNuitMin > 0 && nombreNuitMax > 0) {
                requete += SearchUtil.addConstraintMinMax("ta", "nombreNuit", nombreNuitMin, nombreNuitMax);

            }

            return em.createQuery(requete).getResultList();

        }
    }
    // recherche des taxex par diff criteres


}
