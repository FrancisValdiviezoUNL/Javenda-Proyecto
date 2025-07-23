package edu.unl.cc.jbrew.controllers.security.funtion;

import edu.unl.cc.jbrew.bussiness.SecurityFacade;
import edu.unl.cc.jbrew.bussiness.SecurityFacadeTask;
import edu.unl.cc.jbrew.controllers.security.UserHome;
import edu.unl.cc.jbrew.controllers.security.UserSession;
import edu.unl.cc.jbrew.domain.common.funtion.Task;
import edu.unl.cc.jbrew.domain.security.User;
import edu.unl.cc.jbrew.exception.EntityNotFoundException;
import edu.unl.cc.jbrew.faces.FacesUtil;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.util.logging.Logger;

@Named
@ViewScoped
public class TaskHome implements java.io.Serializable{

    private static Logger logger = Logger.getLogger(TaskHome.class.getName());

    @Serial
    private static final long serialVersionUID = 1L;

    private Long selectedTaskId;

    private Task task;

    @Inject
    SecurityFacadeTask securityFacadeTask;
    @Inject
    SecurityFacade securityFacade;

    public TaskHome() {
        this.task = new Task();
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
            return "taskList?faces-redirect=true";

        } catch (Exception e) {
            FacesUtil.addErrorMessage("Inconveniente al actualizar la tarea: " + e.getMessage());
            return null;
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
}
