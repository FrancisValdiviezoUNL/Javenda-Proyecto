package edu.unl.cc.javenda.domain.common.funtion;

import edu.unl.cc.javenda.domain.security.User;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "Task.findLikeName", query = "select o from Task o where o.theme like :theme"),
        @NamedQuery(name = "Task.findById", query = "select o from Task o where o.id = :id "),
        @NamedQuery(name = "Task.findLikeNameAndUser", query = "SELECT t FROM Task t WHERE LOWER(t.theme) LIKE :theme AND t.user.id = :userId"),
        @NamedQuery(name = "Task.findByUser", query = "SELECT t FROM Task t WHERE t.user.id = :userID"),
        @NamedQuery(name = "Task.statusDelete", query = "UPDATE Task t SET t.statusTaskBD = :status WHERE t.id = :id")

})

public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(nullable = false, length = 100)
    private String theme;

    @NotNull @NotEmpty
    @Column(nullable = false, length = 500)
    private String description;

    @NotNull @NotEmpty
    @Column(nullable = false, length = 100)
    private String matery;

    @Column(nullable = false)
    private LocalDate date_initial;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime hours;

    @Enumerated(EnumType.STRING)
    private StatusTask statusTask;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTaskBD statusTaskBD = StatusTaskBD.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;


    public Task() {
    }

    /**
     *
     * @param id
     * @param theme
     * @param description
     * @param matery
     * @param date
     * @param hours
     * @throws IllegalArgumentException
     */
    public Task(Long id, String theme, String description, String matery, StatusTask status, LocalDate date_initial, LocalDate date, LocalTime hours)
            throws IllegalArgumentException{
        this();
        this.id = id;
        validateThemeRestriction(theme);
        this.theme= theme.trim();
        validateDescriptionRestriction(description);
        this.description = description.trim();
        validateMateryRestriction(matery);
        this.matery = matery.trim();
        this.statusTask = status;
        this.date_initial = date_initial;
        this.date = date;
        this.hours = hours;
    }

    /**
     *
     * @param text
     * @throws IllegalArgumentException
     */
    private void validateThemeRestriction(String text) throws IllegalArgumentException{
        if (text == null || text.isEmpty()){
            throw new IllegalArgumentException("Valor requerido para tema");
        }

        if (text.trim().length() < 20){
            throw new IllegalArgumentException("Valor para el tema debe supera los 20 caracteres");
        }
    }
    private void validateDescriptionRestriction(String text) throws IllegalArgumentException{
        if (text == null || text.isEmpty()){
            throw new IllegalArgumentException("Valor requerido para descripcion");
        }

        if (text.trim().length() < 100){
            throw new IllegalArgumentException("La descripcion no debe supera los 100 caracteres");
        }
    }
    private void validateMateryRestriction(String text) throws IllegalArgumentException{
        if (text == null || text.isEmpty()){
            throw new IllegalArgumentException("Valor requerido para materia");
        }

        if (text.trim().length() < 25){
            throw new IllegalArgumentException("El nombre de la materia no debe superar los 25 caracteres");
        }
    }
    public Boolean isLate (){
        LocalDateTime dateTask = LocalDateTime.of(this.date, this.hours);
        return dateTask.isBefore(LocalDateTime.now());
    }
    public String timeOfTask() {
        if (date == null || hours == null) return "Fecha u hora no definida";

        LocalDateTime dateTask = LocalDateTime.of(this.date, this.hours);
        LocalDateTime dateNow = LocalDateTime.now();

        if (dateNow.isAfter(dateTask)) {
            Duration late = Duration.between(dateTask, dateNow);
            return "Atrasado " + formatTime(late);
        } else {
            Duration availableTime = Duration.between(dateNow, dateTask);
            return "Faltan " + formatTime(availableTime);
        }
    }

    private String formatTime(Duration duration) {
        long days = duration.toDays();
        long hours = duration.minusDays(days).toHours();
        long minutes = duration.minusDays(days).minusHours(hours).toMinutes();

        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append("d ");
        if (hours > 0) sb.append(hours).append("h ");
        if (minutes > 0) sb.append(minutes).append("m");

        return sb.toString().trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMatery() {
        return matery;
    }

    public void setMatery(String matery) {
        this.matery = matery;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate_initial() {
        return date_initial;
    }

    public void setDate_initial(LocalDate date_initial) {
        this.date_initial = date_initial;
    }

    public LocalTime getHours() {
        return hours;
    }

    public void setHours(LocalTime hours) {
        this.hours = hours;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StatusTask getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(StatusTask statusTask) {
        this.statusTask = statusTask;
    }

    public StatusTaskBD getStatusTaskBD() {
        return statusTaskBD;
    }

    public void setStatusTaskBD(StatusTaskBD statusTaskBD) {
        this.statusTaskBD = statusTaskBD;
    }
}

