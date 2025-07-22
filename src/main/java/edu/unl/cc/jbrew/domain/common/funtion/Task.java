package edu.unl.cc.jbrew.domain.common.funtion;

import edu.unl.cc.jbrew.domain.security.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "Task.findLikeName", query = "select o from Task o where o.theme like :theme"),
        @NamedQuery(name = "Task.findById", query = "select o from Task o where o.id = :id "),
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID")
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
    public Task(Long id, String theme, String description, String matery, LocalDate date_initial, LocalDate date, LocalTime hours)
            throws IllegalArgumentException{
        this();
        this.id = id;
        validateThemeRestriction(theme);
        this.theme= theme.trim();
        validateDescriptionRestriction(description);
        this.description = description.trim();
        validateMateryRestriction(matery);
        this.matery = matery.trim();
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
}
