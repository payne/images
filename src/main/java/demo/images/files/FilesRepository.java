package demo.images.files;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FilesRepository extends JpaRepository<Files, Long> {

    boolean existsByFileNameIgnoreCase(String fileName);

}
