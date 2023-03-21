package demo.images.files;

import demo.images.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FilesService {

    private final FilesRepository filesRepository;

    public FilesService(final FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    public List<FilesDTO> findAll() {
        final List<Files> filess = filesRepository.findAll(Sort.by("id"));
        return filess.stream()
                .map((files) -> mapToDTO(files, new FilesDTO()))
                .toList();
    }

    public FilesDTO get(final Long id) {
        return filesRepository.findById(id)
                .map(files -> mapToDTO(files, new FilesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FilesDTO filesDTO) {
        final Files files = new Files();
        mapToEntity(filesDTO, files);
        return filesRepository.save(files).getId();
    }

    public void update(final Long id, final FilesDTO filesDTO) {
        final Files files = filesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(filesDTO, files);
        filesRepository.save(files);
    }

    public void delete(final Long id) {
        filesRepository.deleteById(id);
    }

    private FilesDTO mapToDTO(final Files files, final FilesDTO filesDTO) {
        filesDTO.setId(files.getId());
        filesDTO.setFileName(files.getFileName());
        filesDTO.setTimeUploaded(files.getTimeUploaded());
        filesDTO.setBytes(files.getBytes());
        return filesDTO;
    }

    private Files mapToEntity(final FilesDTO filesDTO, final Files files) {
        files.setFileName(filesDTO.getFileName());
        files.setTimeUploaded(filesDTO.getTimeUploaded());
        files.setBytes(filesDTO.getBytes());
        return files;
    }

    public boolean fileNameExists(final String fileName) {
        return filesRepository.existsByFileNameIgnoreCase(fileName);
    }

}
