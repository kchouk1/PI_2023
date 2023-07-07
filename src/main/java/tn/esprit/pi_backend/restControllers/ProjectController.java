package tn.esprit.pi_backend.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi_backend.entities.Project;
import tn.esprit.pi_backend.services.IProjectService;
import tn.esprit.pi_backend.services.ProjectService;

import java.util.List;

@CrossOrigin(origins = {"http://127.0.0.1:4200","http://localhost:4200"})
@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private IProjectService projectService;

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody Project project) {
        Project existingProject = projectService.getProjectById(id);
        if (existingProject != null) {
            existingProject.setProjectName(project.getProjectName());
            existingProject.setProjectStatus(project.getProjectStatus());
            return projectService.updateProject(existingProject);
        }
        return null;
    }
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
