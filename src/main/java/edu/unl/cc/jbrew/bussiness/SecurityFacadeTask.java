package edu.unl.cc.jbrew.bussiness;

import edu.unl.cc.jbrew.bussiness.services.TaskRepository;
import edu.unl.cc.jbrew.domain.common.funtion.Task;
import edu.unl.cc.jbrew.exception.EntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.List;

@Stateless
public class SecurityFacadeTask implements Serializable {

    @Inject
    private TaskRepository taskRepository;


    public Task create(Task task) throws Exception {
        try {
            taskRepository.find(task.getTheme());
        } catch (EntityNotFoundException e){
            Task taskPersisted = taskRepository.save(task);
            return taskPersisted;
        }
        throw new Exception("Ya existe un Tarea con ese tema");
    }

    public Task update(Task task) throws Exception {
        if (task.getId() == null){
            return create(task);
        }
        try {
            Task taskFound = taskRepository.find(task.getTheme());
            if  (!taskFound.getId().equals(task.getId())){
                throw new Exception("Ya existe Tarea con ese tema");
            }
        } catch (EntityNotFoundException ignored) {
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

}
