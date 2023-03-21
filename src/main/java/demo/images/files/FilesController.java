package demo.images.files;

import demo.images.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/filess")
public class FilesController {

    private final FilesService filesService;

    public FilesController(final FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("filess", filesService.findAll());
        return "files/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("files") final FilesDTO filesDTO) {
        return "files/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("files") @Valid final FilesDTO filesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("fileName") && filesService.fileNameExists(filesDTO.getFileName())) {
            bindingResult.rejectValue("fileName", "Exists.files.fileName");
        }
        if (bindingResult.hasErrors()) {
            return "files/add";
        }
        filesService.create(filesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("files.create.success"));
        return "redirect:/filess";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("files", filesService.get(id));
        return "files/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("files") @Valid final FilesDTO filesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final FilesDTO currentFilesDTO = filesService.get(id);
        if (!bindingResult.hasFieldErrors("fileName") &&
                !filesDTO.getFileName().equalsIgnoreCase(currentFilesDTO.getFileName()) &&
                filesService.fileNameExists(filesDTO.getFileName())) {
            bindingResult.rejectValue("fileName", "Exists.files.fileName");
        }
        if (bindingResult.hasErrors()) {
            return "files/edit";
        }
        filesService.update(id, filesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("files.update.success"));
        return "redirect:/filess";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        filesService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("files.delete.success"));
        return "redirect:/filess";
    }

}
