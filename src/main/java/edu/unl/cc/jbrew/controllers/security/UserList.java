package edu.unl.cc.jbrew.controllers.security;

import edu.unl.cc.jbrew.bussiness.SecurityFacade;
import edu.unl.cc.jbrew.domain.security.User;
import edu.unl.cc.jbrew.exception.EntityNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class UserList implements java.io.Serializable {

    private static Logger logger = Logger.getLogger(UserList.class.getName());

    @Serial
    private static final long serialVersionUID = 1L;

    private String criteria;
    private List<User> users;

    @Inject
    private SecurityFacade securityFacade;

    public UserList() {
        users = new ArrayList<>();
    }

    @PostConstruct
    public void init() throws EntityNotFoundException {
        logger.info("*******POST CONSTRUCTOR: " + getCriteria() + " ********"); //metodo que se ejecuta despues del constructor
        this.search();
    }
    public void search() {
        try {
            logger.info("******* Ingreso a buscar con: " + getCriteria() + " ******");
            users= securityFacade.findUsers(getCriteriaBuffer());
        } catch (EntityNotFoundException e) {
            users.clear();
        }
    }
    public String edit (User selected) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("Selected", selected);
        return "userEdit?faces-redirect=true&id=" + selected.getId();
    }

    public void reset (){
        criteria = null;
        search();
    }
    private String getCriteriaBuffer(){
        return criteria != null && !criteria.isEmpty() ? criteria : "%"; //Esto hace que si esta vacio, se listen todo
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public SecurityFacade getSecurity() {
        return securityFacade;
    }

    public void setSecurityFacade(SecurityFacade securityFacade) {
        this.securityFacade = securityFacade;
    }
}

