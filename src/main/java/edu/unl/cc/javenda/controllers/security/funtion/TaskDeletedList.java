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
public class TaskDeletedList implements Serializable {

    private static Logger logger = Logger.getLogger(UserList.class.getName());

    private List<Task> deletedTasks;

    @Inject
    private SecurityFacadeTask securityFacadeTask;

    @Inject
    private UserSession userSession;

    private String criterio;

    @PostConstruct
    public void init() {
        User currentUser = userSession.getUser();
        if (currentUser != null) {
            deletedTasks = securityFacadeTask.findDeletedTasksByUser(currentUser.getId());
        } else {
            deletedTasks = new ArrayList<>();
        }
    }

    public String restore(Task task) {
        try {
            task.setStatusTaskBD(StatusTaskBD.ACTIVE);
            securityFacadeTask.update(task);
            FacesUtil.addSuccessMessage("Tarea restaurada correctamente");
            return "taskList?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Error al restaurar la tarea: " + e.getMessage());
            return null;
        }
    }
    public void search() {
        try {
            User currentUser = userSession.getUser();
            if (currentUser == null || currentUser.getId() == null) {
                FacesUtil.addErrorMessage("No hay usuario autenticado.");
                deletedTasks = new ArrayList<>();
                return;
            }

            String criterioBuscado = getCriteriaBuffer();
            logger.info("Buscando tareas eliminadas con criterio: " + criterioBuscado);
            deletedTasks = securityFacadeTask.findDeletedTasksByUserWithLike(criterioBuscado, currentUser.getId());

        } catch (Exception e) {
            deletedTasks = new ArrayList<>();
            FacesUtil.addErrorMessage("No se encontraron tareas eliminadas.");
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

    public List<Task> getDeletedTasks() {
        return deletedTasks;
    }

    public void setDeletedTasks(List<Task> deletedTasks) {
        this.deletedTasks = deletedTasks;
    }
}