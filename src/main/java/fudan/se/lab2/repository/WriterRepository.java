package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Writer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterRepository extends CrudRepository<Writer, Long> {

    Writer findByWriternameAndEmail(String writername,String email);
}
