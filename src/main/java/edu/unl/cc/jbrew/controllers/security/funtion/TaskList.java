package edu.unl.cc.jbrew.controllers.security.funtion;

import edu.unl.cc.jbrew.bussiness.SecurityFacadeTask;
import edu.unl.cc.jbrew.controllers.security.UserList;
import edu.unl.cc.jbrew.domain.common.funtion.Task;
import edu.unl.cc.jbrew.exception.EntityNotFoundException;
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
            logger.info("****** Ingreso a buscar con: " + getCriteriaBuffer() + " ******");
            task = securityFacadeTask.findTask(getCriteriaBuffer());
        } catch (EntityNotFoundException e) {
            task.clear();
        }
    }

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
