package edu.unl.cc.javenda.bussiness.services;

import edu.unl.cc.javenda.domain.common.funtion.StatusTaskBD;
import edu.unl.cc.javenda.domain.common.funtion.Task;
import edu.unl.cc.javenda.exception.EntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class TaskRepository {

    @Inject
    CrudGenericService crudGenericService;

    public TaskRepository() {
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Task save(Task task){
        if (task.getId() == null){
            return crudGenericService.create(task);
        } else {
            return crudGenericService.update(task);
        }
    }

    public Task findtaks(@NotNull Long id) throws EntityNotFoundException {
        Task task = crudGenericService.find(Task.class, id);
        if (task == null){
            throw new EntityNotFoundException("Tarea no encontrado con [" + id + "]");
        }
        return task;
    }

    public Task find(@NotNull String tema) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("theme", tema.toLowerCase());
        Task taskFound = (Task) crudGenericService.findSingleResultOrNullWithNamedQuery("Task.findLikeName", params);
        if (taskFound == null){
            throw new EntityNotFoundException("Tarea no encontrado con [" + tema + "]");
        }
        return taskFound;
    }
    public List<Task> findWithLike(@NotNull String tema) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("theme", tema.toLowerCase() + "%");
        return crudGenericService.findWithNamedQuery("Task.findLikeName", params);
    }

    public List<Task> findWithLikeTask(String criterio, Long userId) {
        return crudGenericService.findWithQuery(
                "SELECT t FROM Task t WHERE t.statusTaskBD = :status AND t.user.id = :userId AND LOWER(t.theme) LIKE :criterio",
                Map.of(
                        "status", StatusTaskBD.ACTIVE,
                        "userId", userId,
                        "criterio", "%" + criterio.toLowerCase() + "%"
                )
        );
    }
    public List<Task> findAllByUser(Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userID", userId);
        return crudGenericService.findWithNamedQuery("Task.findByUser", params);
    }
    public void deleteLogical(String namedQuery, Map<String, Object> parameters) {
        crudGenericService.updateOrDeleteWithNamedQuery(namedQuery, parameters);
    }
    public List<Task> findDeletedByUser(Long userId) {
        return crudGenericService.findWithQuery(
                "SELECT t FROM Task t WHERE t.statusTaskBD = :status AND t.user.id = :userId",
                Map.of(
                        "status", StatusTaskBD.DELETE,
                        "userId", userId
                )
        );
    }
    public List<Task> findDeletedByUserWithLike(String criterio, Long userId) {
        return crudGenericService.findWithQuery(
                "SELECT t FROM Task t WHERE t.statusTaskBD = :status AND t.user.id = :userId AND LOWER(t.theme) LIKE :criterio",
                Map.of(
                        "status", StatusTaskBD.DELETE,
                        "userId", userId,
                        "criterio", "%" + criterio.toLowerCase() + "%"
                )
        );
    }

}
