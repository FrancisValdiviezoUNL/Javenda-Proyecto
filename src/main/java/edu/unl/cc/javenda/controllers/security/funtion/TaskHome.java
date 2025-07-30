package edu.unl.cc.javenda.controllers.security.funtion;

import edu.unl.cc.javenda.bussiness.SecurityFacade;
import edu.unl.cc.javenda.domain.common.funtion.StatusTask;
import edu.unl.cc.javenda.bussiness.SecurityFacadeTask;
import edu.unl.cc.javenda.controllers.security.UserSession;
import edu.unl.cc.javenda.domain.common.funtion.Task;
import edu.unl.cc.javenda.domain.security.User;
import edu.unl.cc.javenda.exception.EntityNotFoundException;
import edu.unl.cc.javenda.faces.FacesUtil;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.util.logging.Logger;

@Named
@ViewScoped
public class TaskHome implements java.io.Serializable{

    //private static Logger logger = Logger.getLogger(TaskHome.class.getName());

    @Serial
    private static final long serialVersionUID = 1L;

    private Long selectedTaskId;

    private Task task;

    private String redirectionUrl;

    @Inject
    SecurityFacadeTask securityFacadeTask;
    @Inject
    SecurityFacade securityFacade;

    public TaskHome() {
        this.task = new Task();
        this.task.setStatusTask(StatusTask.PENDIENTE);

    }
    @Inject
    private UserSession userSession;

    public String create() {
        try {
            User currentUser = userSession.getUser();
            if (currentUser == null || currentUser.getId() == null) {
                FacesUtil.addErrorMessage("No hay usuario autenticado correctamente");
                return null;
            }

            User managedUser = securityFacade.find(currentUser.getId());
            task.setUser(managedUser);

            task = securityFacadeTask.create(task);

            FacesUtil.addSuccessMessageAndKeep("Tarea Creada", "Tarea creada correctamente");
            return "taskList?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Inconveniente al crear la tarea: " + e.getMessage());
            return null;
        }
    }

    public void loadTask() {
        if (selectedTaskId != null) {
            try {
                task = securityFacadeTask.find(selectedTaskId);
                User currentUser = userSession.getUser();
                if (task.getUser() == null || !task.getUser().getId().equals(currentUser.getId())) {
                    FacesUtil.addErrorMessage("No tiene permiso para acceder a esta tarea.");
                    task = null;
                }
            } catch (EntityNotFoundException e) {
                FacesUtil.addErrorMessage("No se pudo encontrar la tarea con esa id: " + selectedTaskId);
                task = null;
            }
        } else {
            task = new Task();
        }
    }

    public String update() {
        try {
            if (task == null || task.getId() == null) {
                FacesUtil.addErrorMessage("No hay tarea seleccionada para actualizar.");
                return null;
            }

            User currentUser = userSession.getUser();
            if (currentUser == null || currentUser.getId() == null) {
                FacesUtil.addErrorMessage("No hay usuario autenticado correctamente.");
                return null;
            }

            if (task.getUser() == null || !task.getUser().getId().equals(currentUser.getId())) {
                FacesUtil.addErrorMessage("No tiene permisos para modificar esta tarea.");
                return null;
            }

            User managedUser = securityFacade.find(currentUser.getId());
            task.setUser(managedUser);

            securityFacadeTask.update(task);
            FacesUtil.addSuccessMessageAndKeep("Tarea actualizada correctamente");
            if ("calendar".equals(redirectionUrl)) {
                return null;
            } else {
                return "taskList?faces-redirect=true";
            }

        } catch (Exception e) {
            FacesUtil.addErrorMessage("Inconveniente al actualizar la tarea: " + e.getMessage());
            return null;
        }
    }
    public void deleteTask(Long id) {
        try {
            User currentUser = userSession.getUser();
            Task t = securityFacadeTask.find(id);
            if (t.getUser().getId().equals(currentUser.getId())) {
                securityFacadeTask.deleteLogical(id);
                FacesUtil.addSuccessMessage("Tarea eliminada correctamente.");
            } else {
                FacesUtil.addErrorMessage("No tiene permisos para eliminar esta tarea.");
            }
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Error al eliminar la tarea: " + e.getMessage());
        }
    }

    public void newTask() {
        this.task = new Task();
    }
    public void edit(Task t) {
        this.task = t;
        this.selectedTaskId = t.getId();
    }
    public boolean isManaged() {
        return this.task != null && this.task.getId() != null;
    }

    public Long getSelectedTaskId() {

        return selectedTaskId;
    }

    public void setSelectedTaskId(Long selectedTaskId) {

        this.selectedTaskId = selectedTaskId;
    }

    public Task getTask() {

        return task;
    }

    public void setTask(Task task) {

        this.task = task;
    }

    public String getRedirectionUrl() {
        return redirectionUrl;
    }

    public void setRedirectionUrl(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }
}
