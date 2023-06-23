package tn.esprit.pi_backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi_backend.entities.Task;

import java.util.List;


@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByProjectId(Long projectId);
}
