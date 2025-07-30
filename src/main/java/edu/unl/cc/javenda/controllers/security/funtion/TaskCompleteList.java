package edu.unl.cc.javenda.controllers.security.funtion;

import edu.unl.cc.javenda.bussiness.SecurityFacadeTask;
import edu.unl.cc.javenda.controllers.security.UserList;
import edu.unl.cc.javenda.controllers.security.UserSession;
import edu.unl.cc.javenda.domain.common.funtion.StatusTaskBD;
import edu.unl.cc.javenda.domain.common.funtion.Task;
import edu.unl.cc.javenda.domain.security.User;
import edu.unl.cc.javenda.faces.FacesUtil;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class TaskCompleteList implements Serializable {

    private static Logger logger = Logger.getLogger(UserList.class.getName());

    private List<Task> completeTasks;

    @Inject
    private SecurityFacadeTask securityFacadeTask;

    @Inject
    private UserSession userSession;

    private String criterio;

    @PostConstruct
    public void init() {
        User currentUser = userSession.getUser();
        if (currentUser != null) {
            completeTasks = securityFacadeTask.findTaskCompleteByUser(currentUser.getId());
        } else {
            completeTasks = new ArrayList<>();
        }
    }


    public void search() {
        try {
            User currentUser = userSession.getUser();
            if (currentUser == null || currentUser.getId() == null) {
                FacesUtil.addErrorMessage("No hay usuario autenticado.");
                completeTasks = new ArrayList<>();
                return;
            }

            String criterioBuscado = getCriteriaBuffer();
            logger.info("Buscando tareas completada con criterio: " + criterioBuscado);
            completeTasks = securityFacadeTask.findTaskCompleteUser(criterioBuscado, currentUser.getId());

        } catch (Exception e) {
            completeTasks = new ArrayList<>();
            FacesUtil.addErrorMessage("No se encontraron tareas completadas.");
        }
    }
    public void reset() {
        criterio = null;
        init();
    }

    private String getCriteriaBuffer() {
        return (criterio != null && !criterio.isEmpty()) ? "%" + criterio + "%" : "%";
    }

    // Getters y setters para 'criterio'
    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public List<Task> getCompleteTasks() {
        return completeTasks;
    }

    public void setCompleteTasks(List<Task> completeTasks) {
        this.completeTasks = completeTasks;
    }
}