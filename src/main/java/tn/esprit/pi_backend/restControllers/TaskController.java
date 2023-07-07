package tn.esprit.pi_backend.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi_backend.entities.Task;
import tn.esprit.pi_backend.services.ITaskService;

import java.util.List;
@CrossOrigin(origins = {"http://127.0.0.1:4200","http://localhost:4200"})
@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskService taskService;
    @PostMapping("/{projectId}")
    public Task createTask(@RequestBody Task task, @PathVariable Long projectId) {
        return taskService.createTask(task, projectId);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task existingTask = taskService.getTaskById(id);
        if (existingTask != null) {

            return taskService.updateTask(existingTask);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
    @GetMapping("/project/{projectId}")
    public List<Task> getAllTasksByProjectId(@PathVariable Long projectId) {
        return taskService.getAllTasksByProjectId(projectId);
    }
}
