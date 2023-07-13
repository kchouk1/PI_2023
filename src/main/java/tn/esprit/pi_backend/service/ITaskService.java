package tn.esprit.pi_backend.service;
import tn.esprit.pi_backend.entities.Task;

import java.util.List;
import java.util.Optional;

public interface ITaskService {
    public Task createTask(Task task,Long projectId);
    public Task getTaskById(Long id);
    public List<Task> getAllTasks();
    public Task updateTask(Task task);
    public void deleteTask(Long id);
    public List<Task> getAllTasksByProjectId(Long projectId);
    public Task updateTaskStatus(Long id);
    //public Task updateTaskStatusToTimeOut(Long id);
    int countTasksByStatus(String taskStatus);
    public long getTaskCount();
    public int countPendingTasks();
    public int countDoneTasks();
    public int countTODOTasks();
    public int countTimeOutTasks();
}
