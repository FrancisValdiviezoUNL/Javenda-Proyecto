package edu.unl.cc.javenda.controllers.security.funtion;

import edu.unl.cc.javenda.bussiness.SecurityFacadeTask;
import edu.unl.cc.javenda.controllers.security.UserSession;
import edu.unl.cc.javenda.domain.common.funtion.Task;
import edu.unl.cc.javenda.domain.security.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped
public class DashboardNotification {

    @Inject
    private SecurityFacadeTask securityFacadeTask;

    @Inject
    private UserSession userSession;

    private List<Task> tasksPendingToday;
    private List<Task> tasksInProcess;
    private List<Task> tasksLate;

    @PostConstruct
    public void init() {
        Long userId = userSession.getUser().getId();
        tasksPendingToday = securityFacadeTask.findPendingTasksToday(userId);
        tasksInProcess = securityFacadeTask.findTasksInProcess(userId);
        tasksLate = securityFacadeTask.findLateTasks(userId);

        if (!tasksPendingToday.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Tienes tareas para hoy", "Revisa tus tareas pendientes."));
        }
    }

    public List<Task> getTasksLate() {
        return tasksLate;
    }

    public void setTasksLate(List<Task> tasksLate) {
        this.tasksLate = tasksLate;
    }

    public List<Task> getTasksInProcess() {
        return tasksInProcess;
    }

    public void setTasksInProcess(List<Task> tasksInProcess) {
        this.tasksInProcess = tasksInProcess;
    }

    public List<Task> getTasksPendingToday() {
        return tasksPendingToday;
    }

    public void setTasksPendingToday(List<Task> tasksPendingToday) {
        this.tasksPendingToday = tasksPendingToday;
    }
}