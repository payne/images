package demo.images.files;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FilesDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String fileName;

    private LocalDateTime timeUploaded;

    private Integer bytes;

}
