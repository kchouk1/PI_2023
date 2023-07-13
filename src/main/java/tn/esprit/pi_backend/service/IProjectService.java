package tn.esprit.pi_backend.service;
import tn.esprit.pi_backend.entities.Project;

import java.util.List;
import java.util.Optional;

public interface IProjectService {
    public Project createProject(Project project);
    public Project getProjectById(Long id);
    public List<Project> getAllProjects();
    public Project updateProject(Project project);
    public void deleteProject(Long id);
}
