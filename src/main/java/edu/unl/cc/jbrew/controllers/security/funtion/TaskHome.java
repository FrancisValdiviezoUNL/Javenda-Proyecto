package edu.unl.cc.jbrew.controllers.security.funtion;

import edu.unl.cc.jbrew.bussiness.SecurityFacadeTask;
import edu.unl.cc.jbrew.controllers.security.UserHome;
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

    public TaskHome() {
    }

    public String create() {
        try {
            task = securityFacadeTask.create(task);
            //decryptPassword(user);
            FacesUtil.addSuccessMessageAndKeep("Tarea creada correctamente");
            return "taskList?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Inconveniente al crear la tarea: " + e.getMessage());
            return null;
        }
    }

    public void loadTask() {
       // logger.info("Loading user with id: " + selectedTaskId);
        if (selectedTaskId != null) {
            try {
                task = securityFacadeTask.find(selectedTaskId);
            } catch (EntityNotFoundException e) {
                FacesUtil.addErrorMessage("No se pudo encontrar la tarea con esa id: " + selectedTaskId);
            }
        } else {
            task = new Task();
        }
    }

    public String update() {
        try {
            securityFacadeTask.update(task);
            FacesUtil.addSuccessMessageAndKeep("Tarea actualizada correctamente");
            return "taskList?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Inconveniente al actualizar la tarea: " + e.getMessage());
            return null;
        }
    }

    public boolean isManaged(){
        return this.task.getId() != null;
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
