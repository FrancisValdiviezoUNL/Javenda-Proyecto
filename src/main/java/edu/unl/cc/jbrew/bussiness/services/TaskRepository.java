package edu.unl.cc.jbrew.bussiness.services;

import edu.unl.cc.jbrew.domain.common.funtion.Task;
import edu.unl.cc.jbrew.exception.EntityNotFoundException;
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
}
