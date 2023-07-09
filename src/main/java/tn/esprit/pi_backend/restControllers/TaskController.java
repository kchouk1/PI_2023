package tn.esprit.pi_backend.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi_backend.entities.Task;
import tn.esprit.pi_backend.services.IEmailService;
import tn.esprit.pi_backend.services.ITaskService;

import java.util.List;
@CrossOrigin(origins = {"http://127.0.0.1:4200","http://localhost:4200"})
@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IEmailService emailService;
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
    @GetMapping("/Status/{id}")
    public Task updateTaskStatus(@PathVariable Long id) {
        Task existingTask = taskService.getTaskById(id);
        if (existingTask != null) {
            existingTask.setTaskStatus("Done");
            return taskService.updateTask(existingTask);
        }
        return null;
    }
    @GetMapping("/Status/TimeOut/{id}")
    public Task updateTaskStatusToTimeOut(@PathVariable Long id) {
        Task existingTask = taskService.getTaskById(id);
        if (existingTask != null) {
            existingTask.setTaskStatus("TimeOut");
            String text = "⚠️ Deadline Alert! ⚠️\n\n"
                    + "Dear Responsible,\n\n"
                    + "We regret to inform you that the following task has expired:\n"
                    + "Task Name: " + existingTask.getTaskName() + "\n"
                    + "Task Description: !!!!!!!!!!!!!!!!!!!!! "  + "\n"
                    + "Please take immediate action to address this matter.\n\n"
                    + "Thank you for your attention.\n"
                    + "Best regards,\n"
                    + "Your Team 😔";
            String subject = "Task Deadline Expired";
            String emailAddress = "wajdi.hassyaoui@esprit.tn";
            emailService.sendFormationAddedEmail(emailAddress, subject, text);
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
