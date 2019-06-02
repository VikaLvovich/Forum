package by.bsuir.forum.repository;

import by.bsuir.forum.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {

    Section getSectionById(Long Id);

    List<Section> findAll();
}
