package edu.unl.cc.javenda.controllers.security.funtion;

import edu.unl.cc.javenda.bussiness.SecurityFacadeTask;
import edu.unl.cc.javenda.controllers.security.UserList;
import edu.unl.cc.javenda.controllers.security.UserSession;
import edu.unl.cc.javenda.domain.common.funtion.Task;
import edu.unl.cc.javenda.domain.security.User;
import edu.unl.cc.javenda.exception.EntityNotFoundException;
import edu.unl.cc.javenda.faces.FacesUtil;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class TaskList implements Serializable {
    private static Logger logger = Logger.getLogger(UserList.class.getName());

    @Serial
    private static final long serialVersionUID = 1L;

    private String criterio;
    private List<Task> task;

    @Inject
    SecurityFacadeTask securityFacadeTask;
    @Inject
    private UserSession userSession;

    public TaskList() {
        task = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        logger.info("****** POST CONSTRUCTOR: " + getCriterio() + " ******");
        this.search();
    }

    public void search()  {
        try {
            User currentUser = userSession.getUser();
            if (currentUser == null || currentUser.getId() == null) {
                FacesUtil.addErrorMessage("No hay usuario autenticado.");
                task.clear();
                return;
            }

            logger.info("Buscando tareas con criterio: " + getCriteriaBuffer() + " y usuario ID: " + currentUser.getId());
            task = securityFacadeTask.findTaskUser(getCriteriaBuffer(), currentUser.getId());

        } catch (EntityNotFoundException e) {
            task.clear();
            FacesUtil.addErrorMessage("No se encontraron tareas.");
        }
    }
    /**
public void search()  {
    try {
        User currentUser = userSession.getUser();
        logger.info("****** Ingreso a buscar con: " + getCriteriaBuffer() + " ******");
        task = securityFacadeTask.findTask(getCriteriaBuffer());
    } catch (EntityNotFoundException e) {
        task.clear();
    }
}**/

    public String edit(Task _selectedtaks){
        return "taskEdit?faces-redirect=true&id=" + _selectedtaks.getId();
    }

    public void reset() {
        criterio = null;
        task.clear();
    }

    private String getCriteriaBuffer(){

        return criterio != null && !criterio.isEmpty()? criterio : "%";
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> task) {
        this.task = task;
    }
}
