package ru.isands.test.estore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isands.test.estore.model.dto.PositionDTO;
import ru.isands.test.estore.model.entity.directory.Position;
import ru.isands.test.estore.service.impl.PositionServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job-title")
@CrossOrigin(origins = {"http://localhost:8082", "http://127.0.0.1:8082"})
@Tag(name = "Должности")
public class JobTitleController {

    private final PositionServiceImpl jobTitleService;

    @PostMapping
    @Operation(summary = "Создание должности")
    public ResponseEntity<Long> createType(@RequestBody PositionDTO PositionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobTitleService.createJobTitle(PositionDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить должность")
    public ResponseEntity<PositionDTO> getType(@PathVariable Long id) {
        return ResponseEntity.ok(jobTitleService.getJobTitle(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех должностей")
    public ResponseEntity<List<Position>> getAllType(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(jobTitleService.getJobTitles(page, size));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение должности")
    public ResponseEntity<Void> updateType(@PathVariable Long id, @RequestBody PositionDTO PositionDTO) {
        jobTitleService.updateJobTitle(PositionDTO, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление должности")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        jobTitleService.deleteJobTitle(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
