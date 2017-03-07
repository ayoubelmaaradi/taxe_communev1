/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.User;
import controler.util.HashageUtil;
import controler.util.JsfUtil;
import controler.util.SessionUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Younes
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "projet_java_taxPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    @Override
    public User find(Object id) {
        try {
            User user = (User) em.createQuery("select u from User u where u.login = '" + id + "'").getSingleResult();
            if (user != null) {
                return user;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Login incorrect");
        }
        return null;
    }
//
//    public void seDeConnnecter() {
//        historiqueConnexionFacade.createDeConnexion();
//        SessionUtil.getSession().invalidate();
//
//    }
//
//    public List<User> findUserByTaxeGenerale(Date datePresentationMin, Date datePresentationMax, Activite activite, Secteure secteure, AnnexeAdministrative annexeAdministrative, Quartier quartier, Rue rue, List<String> typesTaxe) {
//        List<User> userBoison = new ArrayList<>();
//        List<User> userSejour = new ArrayList<>();
//        List<User> user37 = new ArrayList<>();
//        List<User> user38 = new ArrayList<>();
//        List<User> users = new ArrayList<>();
//        if (datePresentationMin != null && datePresentationMax != null) {
//            if (datePresentationMin.compareTo(datePresentationMax) > 0) {
//                Date date1 = datePresentationMin;
//                datePresentationMin = datePresentationMax;
//                datePresentationMax = date1;
//                System.out.println("les date m9lobin o hahoma t9ado ");
//                System.out.println("ha la date min " + datePresentationMin);
//                System.out.println("ha date max " + datePresentationMax);
//            }
//        }
//        for (int i = 0; i < typesTaxe.size(); i++) {
//            if (typesTaxe.get(i).equals("Boison")) {
//                userBoison = findUserByTaxe("TaxeBoisonAnuelle", "taxeBoisonAnuelle", "TaxeBoisonTrimistrielle", "taxeBoisonTrimistrielle", datePresentationMin, datePresentationMax, activite, secteure, annexeAdministrative, quartier, rue);
//                users = findUsersIntersection(userBoison, users);
//            } else if (typesTaxe.get(i).equals("Sejour")) {
//                userSejour = findUserByTaxe("TaxeSejourAnuelle", "taxeSejourAnuelle", "TaxeSejourTrimistrielle", "taxeSejourTrimistrielle", datePresentationMin, datePresentationMax, activite, secteure, annexeAdministrative, quartier, rue);
//                users = findUsersIntersection(userSejour, users);
//            } else if (typesTaxe.get(i).equals("37")) {
//                user37 = findUserByTaxe("Taxe37TrimistrielleItem", "taxe37Trimistrielleitem", "Taxe37TrimistrielleItem", "taxe37Trimistrielleitem", datePresentationMin, datePresentationMax, activite, secteure, annexeAdministrative, quartier, rue);
//                users = findUsersIntersection(user37, users);
//            } else if (typesTaxe.get(i).equals("38")) {
//                user38 = findUserByTaxe("Taxe38TrimistrielleItem", "taxe38TrimistrielleItem", "Taxe38TrimistrielleItem", "taxe38TrimistrielleItem", datePresentationMin, datePresentationMax, activite, secteure, annexeAdministrative, quartier, rue);
//                users = findUsersIntersection(user38, users);
//            }
//        }
//        return users;
//    }

//    public List<User> findUserByTaxe(String taxeBeanAnnuel, String abreviationBeanAnnuel, String taxeBeanTrim, String abreviationBeanTrim, Date datePresentationMin, Date datePresentationMax, Activite activite, Secteure secteure, AnnexeAdministrative annexeAdministrative, Quartier quartier, Rue rue) {
//        String ajouteCritaireAnnuel = "";
//        String variable = ".user";
//        String ajouteCritaireTrimistriel = "";
//        if (taxeBeanAnnuel.equals("Taxe37TrimistrielleItem") || taxeBeanAnnuel.equals("Taxe38TrimistrielleItem")) {
//            if (taxeBeanAnnuel.equals("Taxe37TrimistrielleItem")) {
//                ajouteCritaireTrimistriel = statistiqueFacade.constructRequetteStatistique37Et38Item(false, abreviationBeanTrim, "", activite, rue, quartier, annexeAdministrative, secteure, datePresentationMax, datePresentationMin, null, null, true);
//            } else if (taxeBeanAnnuel.equals("Taxe37TrimistrielleItem")) {
//                ajouteCritaireTrimistriel = statistiqueFacade.constructRequetteStatistique37Et38Item(false, abreviationBeanTrim, "", activite, rue, quartier, annexeAdministrative, secteure, datePresentationMax, datePresentationMin, null, null, false);
//            }
//        } else {
//            ajouteCritaireAnnuel = statistiqueFacade.constructRequetteStatistique(true, abreviationBeanTrim, abreviationBeanAnnuel, activite, rue, quartier, annexeAdministrative, secteure, datePresentationMax, datePresentationMin, null, null, false);
//            ajouteCritaireTrimistriel = statistiqueFacade.constructRequetteStatistique(false, abreviationBeanTrim, abreviationBeanAnnuel, activite, rue, quartier, annexeAdministrative, secteure, datePresentationMax, datePresentationMin, null, null, false);
//        }
//        List<User> userAnuel = em.createQuery("SELECT DISTINCT(" + taxeBeanAnnuel + variable + ") FROM " + taxeBeanAnnuel + " " + abreviationBeanAnnuel
//                + "  WHERE " + abreviationBeanAnnuel + variable + ".login != null" + ajouteCritaireAnnuel).getResultList();
//        if(taxeBeanTrim.equals("taxe37Trimistrielleitem")){
//            ajouteCritaireTrimistriel = statistiqueFacade.constructRequetteStatistique37Et38Item(false, abreviationBeanTrim, abreviationBeanAnnuel, activite, rue, quartier, annexeAdministrative, secteure, datePresentationMax, datePresentationMin, null, null, true);
//        }else if(taxeBeanTrim.equals("taxe38Trimistrielleitem")){
//            ajouteCritaireTrimistriel = statistiqueFacade.constructRequetteStatistique37Et38Item(false, abreviationBeanTrim, abreviationBeanAnnuel, activite, rue, quartier, annexeAdministrative, secteure, datePresentationMax, datePresentationMin, null, null, false);
//        }else{
//            ajouteCritaireTrimistriel = statistiqueFacade.constructRequetteStatistique(false, abreviationBeanTrim, abreviationBeanAnnuel, activite, rue, quartier, annexeAdministrative, secteure, datePresentationMax, datePresentationMin, null, null, false);
//        }
//        List<User> userTrim = em.createQuery("SELECT DISTINCT(" + taxeBeanTrim + variable + ") FROM " + taxeBeanTrim + " " + abreviationBeanTrim
//                + "  WHERE " + abreviationBeanTrim + ".user.login != null" + ajouteCritaireTrimistriel).getResultList();
//        return findUsersIntersection(userAnuel, userTrim);
//     
    public int changePassword(String login, String oldPassword, String newPassword, String newPasswordConfirmation) {
        System.out.println("voila hana dkhalt le service verifierPassword");
        User loadedeUser = find(login);

        if (!newPasswordConfirmation.equals(newPassword)) {
            return -1;
        } else if (!loadedeUser.getPassword().equals(HashageUtil.sha256(oldPassword))) {
            return -2;
        } else if (oldPassword.equals(newPassword)) {
            return -3;
        }
        loadedeUser.setPassword(HashageUtil.sha256(newPassword));
        edit(loadedeUser);
        return 1;
    }

    public void changeData(User user) {
        User loadedUser = find(user.getLogin());
        cloneData(user, loadedUser);
        edit(loadedUser);
    }

    public void cloneData(User userSource, User userDestination) {
        userDestination.setNom(userSource.getNom());
        userDestination.setPrenom(userSource.getPrenom());
        userDestination.setTel(userSource.getTel());
        userDestination.setEmail(userSource.getEmail());
    }

    public int seConnecter(User user) {
        if (user == null || user.getLogin() == null) {
            JsfUtil.addErrorMessage("Veuilliez saisir votre login");
            return -5;
        } else {
            User loadedUser = find(user.getLogin());
            if (loadedUser == null) {
                return -4;
            } else if (!loadedUser.getPassword().equals(HashageUtil.sha256(user.getPassword()))) {

                if (loadedUser.getNbrCnx() < 3) {
                    System.out.println("hana loadedUser.getNbrCnx() < 3 ::: " + loadedUser.getNbrCnx());
                    loadedUser.setNbrCnx(loadedUser.getNbrCnx() + 1);
                    edit(loadedUser);
                    return -7;
                } else {//(loadedUser.getNbrCnx() >= 3)
                    System.out.println("hana loadedUser.getNbrCnx() >= 3::: " + loadedUser.getNbrCnx());
                    loadedUser.setBlocked(1);
                    edit(loadedUser);
                    return -3;
                }
            } else if (loadedUser.getBlocked() == 1) {
                JsfUtil.addErrorMessage("Cet utilisateur est bloqué");
                return -2;
            } else {
                loadedUser.setNbrCnx(0);
                edit(loadedUser);
                user = clone(loadedUser);
                user.setPassword(null);
                return 1;
            }
        }
    }

    public List<Boolean> getPrivileges() {
        User loadeUser = find(SessionUtil.getConnectedUser().getLogin());
        List<Boolean> privileges = new ArrayList();

        return privileges;
    }

//    public List<User> findByCommune(Commune commune) {
//        if (commune != null && commune.getId() != null) {
//            return em.createQuery("SELECT u FROM User u WHERE  u.commune.id=" + commune.getId()).getResultList();
//        }
//        return new ArrayList();
//    }
    public User clone(User user) {
        User clone = new User();
        clone.setLogin(user.getLogin());
        clone.setBlocked(user.getBlocked());
        clone.setNbrCnx(user.getNbrCnx());
        clone.setNom(user.getNom());
        clone.setPassword(user.getPassword());
        clone.setPrenom(user.getPrenom());
        clone.setTel(user.getTel());
        clone.setAdmin(user.isAdmin());
        return clone;
    }

//    @Override
//    public void create(User user) {
//        user.setCommune(SessionUtil.getCurrentCommune());
//        super.create(user);
//        SessionUtil.getCurrentCommune().getUsers().add(user);
//
//    }
//
//    @Override
//    public void edit(User user) {
//        super.edit(user);
//        int index = SessionUtil.getCurrentCommune().getUsers().indexOf(user);
//        if (index != -1) {
//            SessionUtil.getCurrentCommune().getUsers().set(index, user);
//        }
//    }
//
//    @Override
//    public void remove(User user) {
//        super.remove(user);
//        int index = SessionUtil.getCurrentCommune().getUsers().indexOf(user);
//        if (index != -1) {
//            SessionUtil.getCurrentCommune().getUsers().remove(index);
//        }
//    }
//
//    @Override
//    public List<User> findAll() {
//        return findByCommune(SessionUtil.getCurrentCommune());
//    }
    public String findLogin(User user) {
        return (String) em.createQuery("SELECT u.login FROM User u WHERE u.login='" + user.getLogin() + "'").getSingleResult();
    }

    //-----------------------------------------------------------------------------------
    public Object[] addUser(User user) {

        if ("".equals(user.getLogin()) || user.getLogin() == null) {
            return new Object[]{-1, null};
        } else if ("".equals(user.getPassword()) || user.getPassword() == null) {
            return new Object[]{-2, null};
        }
        User loadedUser = find(user.getLogin());
        if (loadedUser != null) {
            return new Object[]{-3, null};
        } else {
            user.setNbrCnx(0);
            user.setBlocked(0);
            user.setPassword(HashageUtil.sha256(user.getPassword()));
            create(user);
            return new Object[]{1, user};
        }

    }

    public Object[] seConnecte(User user) {

        User loadedUser = find(user.getLogin());

        if (loadedUser == null) {
            JsfUtil.addErrorMessage("ERROR!!");

            return new Object[]{-1, null};

        } else if (!loadedUser.getPassword().equals(user.getPassword())) {

            return new Object[]{-2, null};

        } else if (loadedUser.getBlocked() == 1) {

            return new Object[]{-3, null};

        } else {

            return new Object[]{1, loadedUser};

        }

    }
}
