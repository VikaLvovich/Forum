package by.bsuir.forum.controller;

import by.bsuir.forum.entity.Section;
import by.bsuir.forum.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class SectionsController {

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionsController(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @GetMapping("sections")
    public String displayAllTopics(Model model) {
        List<Section> sections = sectionRepository.findAll(new Sort(Sort.Direction.DESC, "name"));
        model.addAttribute("sections", sections);
        return "sections";
    }

    @PostMapping("sections")
    public View addSection(@RequestParam("name") String name, @RequestParam("description") String description,
                         HttpServletRequest request) {
        Section section = new Section();
        section.setName(name);
        section.setDescription(description);
        section.setCreatedDate(LocalDateTime.now());
        sectionRepository.save(section);
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/sections");
    }
}
