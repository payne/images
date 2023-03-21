package demo.images.files;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/filess", produces = MediaType.APPLICATION_JSON_VALUE)
public class FilesResource {

    private final FilesService filesService;

    public FilesResource(final FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping
    public ResponseEntity<List<FilesDTO>> getAllFiless() {
        return ResponseEntity.ok(filesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilesDTO> getFiles(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(filesService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFiles(@RequestBody @Valid final FilesDTO filesDTO) {
        return new ResponseEntity<>(filesService.create(filesDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFiles(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FilesDTO filesDTO) {
        filesService.update(id, filesDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFiles(@PathVariable(name = "id") final Long id) {
        filesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
