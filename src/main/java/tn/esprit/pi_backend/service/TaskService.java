package tn.esprit.pi_backend.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pi_backend.entities.Project;
import tn.esprit.pi_backend.entities.Task;
import tn.esprit.pi_backend.repositories.TaskRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
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

    public long getTaskCount() {
        return taskRepository.count();
    }


    @Override
    public int countPendingTasks() {
        String taskStatusPending = "Pending";
        return taskRepository.countByTaskStatus(taskStatusPending);
    }

    @Override
    public int countDoneTasks() {
        String taskStatusPending = "Done";
        return taskRepository.countByTaskStatus(taskStatusPending);
    }

    @Override
    public int countTODOTasks() {
        String taskStatusPending = "TODO";
        return taskRepository.countByTaskStatus(taskStatusPending);
    }

    @Override
    public int countTimeOutTasks() {
        String taskStatusPending = "TimeOut";
        return taskRepository.countByTaskStatus(taskStatusPending);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getAllTasksByProjectId(Long projectId) {
        return taskRepository.findAllByProjectId(projectId);
    }

    @Override
    @Transactional
    public Task updateTaskStatus(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTaskStatus("Done");
            try {
                Task updatedTask = taskRepository.save(task);
                System.out.println("Task with ID " + updatedTask.getId() + " has been updated. New status: " + updatedTask.getTaskStatus());
                return updatedTask;
            } catch (Exception e) {
                System.out.println("Failed to update task with ID: " + task.getId());
                e.printStackTrace();
                throw new RuntimeException("Failed to update task status");
            }
        } else {
            throw new NoSuchElementException("Task not found");
        }
    }

    @Override
    public int countTasksByStatus(String taskStatus) {
        return taskRepository.countByTaskStatus(taskStatus);
    }


}