package tn.esprit.pi_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pi_backend.entities.Project;
import tn.esprit.pi_backend.entities.Task;
import tn.esprit.pi_backend.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements ITaskService{
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private IProjectService projectService;

    @Override
    public Task createTask(Task task, Long projectId) {
        Project project = projectService.getProjectById(projectId);
        if (project != null) {
            task.setProject(project);
            return taskRepository.save(task);
        }
        return null;
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public List<Task> getAllTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getAllTasksByProjectId(Long projectId) {
        return taskRepository.findAllByProjectId(projectId);
    }

}
