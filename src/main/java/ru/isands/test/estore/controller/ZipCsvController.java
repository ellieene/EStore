package ru.isands.test.estore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.isands.test.estore.service.impl.ZipCsvService;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class ZipCsvController {
    private final ZipCsvService zipCsvService;

    public ZipCsvController(ZipCsvService zipCsvService) {
        this.zipCsvService = zipCsvService;
    }

    @PostMapping("/zip")
    public ResponseEntity<String> uploadZipFile(@RequestParam("file") MultipartFile file) {
        try {
            zipCsvService.processZipFile(file);
            return ResponseEntity.ok("Данные из ZIP успешно загружены в базу");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Ошибка обработки ZIP: " + e.getMessage());
        }
    }
}
