package fudan.se.lab2.repository;
import fudan.se.lab2.domain.Administrator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends CrudRepository<Administrator, Long> {
    //Administrator findById(Long id);
    Administrator findByName(String name);
}
