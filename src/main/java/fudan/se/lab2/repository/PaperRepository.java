package fudan.se.lab2.repository;

import fudan.se.lab2.domain.Paper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PaperRepository extends CrudRepository<Paper,Long> {
    List<Paper>findByTitleAndAuthorname(String title,String authorname);
    Paper findByTitle(String title);
    List<Paper>  findByAuthorname(String authorname);
    Set<Paper>findAll();
}
