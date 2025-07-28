package edu.unl.cc.javenda.bussiness;

import edu.unl.cc.javenda.bussiness.services.TaskRepository;
import edu.unl.cc.javenda.domain.common.funtion.StatusTaskBD;
import edu.unl.cc.javenda.domain.common.funtion.Task;
import edu.unl.cc.javenda.exception.EntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class SecurityFacadeTask implements Serializable {

    @Inject
    private TaskRepository taskRepository;


    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public Task update(Task task) throws Exception {
        if (task.getId() == null) {
            return create(task);
        }
        return taskRepository.save(task);
    }
    public Task find(Long id) throws EntityNotFoundException {
        return taskRepository.findtaks(id);
    }

    public List<Task> findTask(String criterio) throws EntityNotFoundException {
        return taskRepository.findWithLike(criterio);
    }

    public List<Task> findTaskUser(String criterio, Long id_user) throws EntityNotFoundException {
        return taskRepository.findWithLikeTask(criterio, id_user);
    }
    public List<Task> findAllByUser(Long userId) {
        return taskRepository.findAllByUser(userId);
    }

    public void deleteLogical(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("status", StatusTaskBD.DELETE);
        taskRepository.deleteLogical("Task.statusDelete", params);
    }
    public List<Task> findDeletedTasksByUser(Long userId) {
        return taskRepository.findDeletedByUser(userId);
    }
    public List<Task> findDeletedTasksByUserWithLike(String criterio, Long userId) {
        return taskRepository.findDeletedByUserWithLike(criterio, userId);
    }

    public List<Task> findPendingTasksToday(Long userId) {
        return taskRepository.findPendingTasksToday(userId);
    }

    public List<Task> findTasksInProcess(Long userId) {
        return taskRepository.findInProcessTasks(userId);
    }

    public List<Task> findLateTasks(Long userId) {
        return taskRepository.findLateTasks(userId);
    }
}
