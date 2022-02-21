package com.ets.filesystem.web.model;

import lombok.*;
import org.springframework.stereotype.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class File {
    private String name;
    private String url;
    private String uploadDir;
    private String docType;
    private byte[] data;

}
