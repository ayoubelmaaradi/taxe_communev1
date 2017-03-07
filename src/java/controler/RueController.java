package controler;

import bean.AnnexeAdministratif;
import bean.Quartier;
import bean.Rue;
import bean.Secteur;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import service.RueFacade;

import java.io.Serializable;
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

@Named("rueController")
@SessionScoped
public class RueController implements Serializable {

    @EJB
    private service.RueFacade ejbFacade;

    @EJB
    private QuartierFacade quartierFacade;
    @EJB
    private AnnexeAdministratifFacade annexeAdministratifFacade;
    private List<Rue> items = null;
    private Rue selected;
    //pour la page adressage
    private Quartier quartier;
    private AnnexeAdministratif annexeAdministratif;
    private Secteur secteur;
    private String nomAnnex;
    private String nomQuartier;
    private String nomRue;

    public void findAnnexByName() {
        secteur.setAnnexeAdministratifs(annexeAdministratifFacade.findByName(nomAnnex));
    }

    public void findQuartierByName() {
        annexeAdministratif.setQuartiers(quartierFacade.findByName(nomQuartier));
    }

    public void findRueByName() {
        quartier.setRues(ejbFacade.findByName(nomRue));
    }

    public void findAnnexs() {
        secteur.setAnnexeAdministratifs(annexeAdministratifFacade.findBySecteur(secteur));
    }

    public void findQuartiers() {
        annexeAdministratif.setQuartiers(quartierFacade.findByAnnexe(annexeAdministratif));
    }

    public void findRues() {
        quartier.setRues(ejbFacade.findByQuartier(quartier));
    }

    public void findAnnexs(Secteur secteur1) {
        secteur.setAnnexeAdministratifs(annexeAdministratifFacade.findBySecteur(secteur1));
    }

    public void findQuartiers(AnnexeAdministratif annexeAdministratif1) {
        annexeAdministratif.setQuartiers(quartierFacade.findByAnnexe(annexeAdministratif1));
    }

    public void findRues(Quartier quartier1) {
        quartier.setRues(ejbFacade.findByQuartier(quartier1));
    }

    public RueController() {
    }

    public Rue getSelected() {
        if (selected == null) {
            selected = new Rue();
        }
        return selected;
    }

    public void setSelected(Rue selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RueFacade getFacade() {
        return ejbFacade;
    }

    public Rue prepareCreate() {
        selected = new Rue();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RueCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RueUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RueDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Rue> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
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

    public Rue getRue(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Rue> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Rue> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Rue.class)
    public static class RueControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RueController controller = (RueController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rueController");
            return controller.getRue(getKey(value));
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
            if (object instanceof Rue) {
                Rue o = (Rue) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Rue.class.getName()});
                return null;
            }
        }

    }

    public Quartier getQuartier() {
        if (quartier == null) {
            quartier = new Quartier();
        }
        return quartier;
    }

    public void setQuartier(Quartier quartier) {
        this.quartier = quartier;
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

    public QuartierFacade getQuartierFacade() {
        return quartierFacade;
    }

    public void setQuartierFacade(QuartierFacade quartierFacade) {
        this.quartierFacade = quartierFacade;
    }

    public AnnexeAdministratifFacade getAnnexeAdministratifFacade() {
        return annexeAdministratifFacade;
    }

    public void setAnnexeAdministratifFacade(AnnexeAdministratifFacade annexeAdministratifFacade) {
        this.annexeAdministratifFacade = annexeAdministratifFacade;
    }

    public String getNomAnnex() {
        return nomAnnex;
    }

    public void setNomAnnex(String nomAnnex) {
        this.nomAnnex = nomAnnex;
    }

    public String getNomQuartier() {
        return nomQuartier;
    }

    public void setNomQuartier(String nomQuartier) {
        this.nomQuartier = nomQuartier;
    }

    public String getNomRue() {
        return nomRue;
    }

    public void setNomRue(String nomRue) {
        this.nomRue = nomRue;
    }

}
