package controler;

import bean.AnnexeAdministratif;
import bean.Categorie;
import bean.Locale;
import bean.Quartier;
import bean.Redevable;
import bean.Secteur;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import service.LocaleFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import service.AnnexeAdministratifFacade;
import service.QuartierFacade;
import service.RedevableFacade;
import service.RueFacade;

@Named("localeController")
@SessionScoped
public class LocaleController implements Serializable {

    @EJB
    private service.LocaleFacade ejbFacade;
    @EJB
    private RueFacade rueFacade;
    @EJB
    private QuartierFacade quartierFacade;
    @EJB
    private AnnexeAdministratifFacade annexeAdministratifFacade;
    @EJB
    private RedevableFacade redevableFacade;
    private List<Locale> items = null;
    private List<Locale> itemsRecherche;
    private Locale selected;
    //create
    private Quartier quartier;
    private AnnexeAdministratif annexeAdministratif;
    private Secteur secteur;
    //chercher le redevable d'un locale dans la creation $serach locale
    private Categorie categorie;
    private String gerantCinRc;
    private String proprietaireCinRc;

    public void findProprietaireByCinOrRc() {
        selected.setProprietaire(redevableFacade.findByCinRc(proprietaireCinRc));
    }

    public void findGerantByCinOrRc() {
        selected.setGerant(redevableFacade.findByCinRc(gerantCinRc));
    }

//    public Redevable findRedevable(String str) {
//        List<Redevable> list = redevableFacade.findByCin(str);
//        if (!list.isEmpty()) {
//            return list.get(0);
//        } else {
//            list = redevableFacade.findByRc(str);
//            if (!list.isEmpty()) {
//                return list.get(0);
//            } else {
//                return null;
//            }
//        }
//
//    }

    //la recherche des locale avec plusieurs criteres
    public void findByCreteria() {
        itemsRecherche = ejbFacade.findByGerantOrProprietaire(categorie, redevableFacade.findByCinRc(proprietaireCinRc), selected.getActivite(), selected.getReference(), redevableFacade.findByCinRc(gerantCinRc));
    }

    public void findAnnexs() {
        secteur.setAnnexeAdministratifs(annexeAdministratifFacade.findBySecteur(secteur));
    }

    public void findQuartiers() {
        annexeAdministratif.setQuartiers(quartierFacade.findByAnnexe(annexeAdministratif));
    }

    public void findRues() {
        quartier.setRues(rueFacade.findByQuartier(quartier));
    }

    public LocaleController() {
    }

    public Locale getSelected() {
        if (selected == null) {
            selected = new Locale();
        }
        return selected;
    }

    public void setSelected(Locale selected) {
        this.selected = selected;
    }

    public List<Locale> getItemsRecherche() {
        if (itemsRecherche == null) {
            itemsRecherche = new ArrayList<>();
        }
        return itemsRecherche;
    }

    public void setItemsRecherche(List<Locale> itemsRecherche) {
        this.itemsRecherche = itemsRecherche;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LocaleFacade getFacade() {
        return ejbFacade;
    }

    public LocaleFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(LocaleFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public RueFacade getRueFacade() {
        return rueFacade;
    }

    public void setRueFacade(RueFacade rueFacade) {
        this.rueFacade = rueFacade;
    }

    public Locale prepareCreate() {
        selected = new Locale();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        getFacade().generateReference(selected.getRue(), selected);
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LocaleCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LocaleUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("LocaleDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Locale> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (null != persistAction) {
                    switch (persistAction) {
                        case CREATE:
//                        if(getFacade().findByReference(selected).get(0)==null){
                            getFacade().edit(selected);
//                        }else{
                            JsfUtil.addSuccessMessage("Locale bien crée");
//                        }
                            break;
                        case UPDATE:
                            getFacade().edit(selected);
                            JsfUtil.addSuccessMessage(successMessage);
                            break;
                        default:
                            getFacade().remove(selected);
                            JsfUtil.addSuccessMessage(successMessage);
                            break;
                    }
                }
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Locale getLocale(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Locale> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Locale> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Locale.class)
    public static class LocaleControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LocaleController controller = (LocaleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "localeController");
            return controller.getLocale(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Locale) {
                Locale o = (Locale) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Locale.class.getName()});
                return null;
            }
        }

    }

    public Quartier getQuartier() {
        return quartier;
    }

    public void setQuartier(Quartier quartier) {
        this.quartier = quartier;
    }

    public QuartierFacade getQuartierFacade() {
        if (quartierFacade == null) {
            quartierFacade = new QuartierFacade();
        }
        return quartierFacade;
    }

    public void setQuartierFacade(QuartierFacade quartierFacade) {
        this.quartierFacade = quartierFacade;
    }

    public AnnexeAdministratifFacade getAnnexeAdministratifFacade() {
        if (annexeAdministratifFacade == null) {
            annexeAdministratifFacade = new AnnexeAdministratifFacade();
        }
        return annexeAdministratifFacade;
    }

    public void setAnnexeAdministratifFacade(AnnexeAdministratifFacade annexeAdministratifFacade) {
        this.annexeAdministratifFacade = annexeAdministratifFacade;
    }

    public AnnexeAdministratif getAnnexeAdministratif() {
        if (annexeAdministratif == null) {
            annexeAdministratif = new AnnexeAdministratif();
        }
        return annexeAdministratif;
    }

    public void setAnnexeAdministratif(AnnexeAdministratif annexeAdministratif) {
        this.annexeAdministratif = annexeAdministratif;
    }

    public Secteur getSecteur() {
        if (secteur == null) {
            secteur = new Secteur();
        }
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public String getGerantCinRc() {
        return gerantCinRc;
    }

    public void setGerantCinRc(String gerantCinRc) {
        this.gerantCinRc = gerantCinRc;
    }

    public String getProprietaireCinRc() {
        return proprietaireCinRc;
    }

    public void setProprietaireCinRc(String proprietaireCinRc) {
        this.proprietaireCinRc = proprietaireCinRc;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

}
