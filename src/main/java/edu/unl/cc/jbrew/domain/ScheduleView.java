package edu.unl.cc.jbrew.domain;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Named("scheduleView")
@ViewScoped
public class ScheduleView implements Serializable {
    private ScheduleModel eventModel;
    private ScheduleEvent<?> event;

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();

        // Simulación de tarea
        DefaultScheduleEvent<?> tarea1 = DefaultScheduleEvent.builder()
                .title("Tarea Matemáticas")
                .description("Resolver ejercicios de álgebra")
                .startDate(LocalDateTime.of(2025, 7, 8, 10, 0))  // Fija la fecha y hora
                .endDate(LocalDateTime.of(2025, 7, 8, 12, 0))
                .data(new Tarea("Matemáticas", "Pendiente"))
                .build();

        eventModel.addEvent(tarea1);
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        this.event = selectEvent.getObject();
        PrimeFaces.current().executeScript("PF('taskDialog').show()");
    }

    public void onDateSelect(org.primefaces.event.SelectEvent<Date> selectEvent) {
        // Aquí se podría abrir un formulario para crear nueva tarea
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleEvent<?> getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
    }

    public static class Tarea {
        private String materia;
        private String estado;

        public Tarea(String materia, String estado) {
            this.materia = materia;
            this.estado = estado;
        }

        public String getMateria() { return materia; }
        public String getEstado() { return estado; }
    }
}
