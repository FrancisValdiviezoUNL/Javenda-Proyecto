package edu.unl.cc.javenda.controllers.security.funtion;

import edu.unl.cc.javenda.controllers.security.UserSession;
import edu.unl.cc.javenda.exception.EntityNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import edu.unl.cc.javenda.bussiness.SecurityFacadeTask;
import edu.unl.cc.javenda.domain.security.User;
import edu.unl.cc.javenda.domain.common.funtion.Task;
import java.util.List;

import java.io.Serializable;
import java.time.LocalDateTime;

@Named("scheduleView")
@ViewScoped
public class ScheduleView implements Serializable {
    private ScheduleModel eventModel;
    private ScheduleEvent<?> event = new DefaultScheduleEvent<>();

    @Inject
    private SecurityFacadeTask securityFacadeTask;

    @Inject
    private UserSession userSession;

    private Task selectedTask;

    @Inject
    private TaskHome taskHome;

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();

        User currentUser = userSession.getUser();
        if (currentUser != null) {
            List<Task> tareas = securityFacadeTask.findAllByUser(currentUser.getId());
            for (Task t : tareas) {
                LocalDateTime end = t.getDate().atTime(t.getHours());

                boolean isLate = t.isLate();

                ScheduleEvent<?> ev = DefaultScheduleEvent.builder()
                        .title(t.getTheme())
                        .startDate(end)
                        .endDate(end)
                        .description(t.getDescription())
                        .styleClass(isLate ? "event-late" : "event-normal")
                        .data(t.getId())
                        .build();

                eventModel.addEvent(ev);
            }
        }
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        this.event = selectEvent.getObject();

        Long taskId = (Long) event.getData();
        if (taskId != null) {
            try {
                Task selectedTask = securityFacadeTask.find(taskId);
                taskHome.setTask(selectedTask);
                taskHome.setRedirectionUrl("calendar");
            } catch (EntityNotFoundException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo cargar la tarea."));
            }
        }
    }
    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        LocalDateTime start = selectEvent.getObject();
        LocalDateTime end = start.plusHours(1);

        this.event = DefaultScheduleEvent.builder()
                .startDate(start)
                .endDate(end)
                .title("Nuevo evento")
                .build();
    }

    // Getter/Setter
    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleEvent<?> getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
    }

    public Task getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }
}