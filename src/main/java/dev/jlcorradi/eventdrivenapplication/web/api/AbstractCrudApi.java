package dev.jlcorradi.eventdrivenapplication.web.api;

import dev.jlcorradi.eventdrivenapplication.common.CrudService;
import dev.jlcorradi.eventdrivenapplication.web.HttpUtils;
import dev.jlcorradi.eventdrivenapplication.web.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractCrudApi<D, K, S extends CrudService<D, K>> {
    public static final String SORT_PARAM_NAME = "sort";
    public static final String PAGE_SIZE_PARAM_NAME = "pageSize";
    public static final String PAGE_NUMBER_PARAM_NAME = "pageNumber";
    public static final String RECORD_INSERTED_SUCCESSFULLY_MSG = "Record Inserted Successfully";
    public static final String RECORD_UPDATED_SUCCESSFULLY_MSG = "Record Updated Successfully";
    public static final String RECORD_DELETED_SUCCESSFULLY_MSG = "Record Deleted Successfully";

    private final S service;

    @PostMapping
    public ResponseEntity<D> post(@Validated @RequestBody D request) {
        log.info("Posting Object: {}", request);
        D dto = service.create(request);
        HttpUtils.addMessageHeader(MessageType.SUCCESS, RECORD_INSERTED_SUCCESSFULLY_MSG);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> put(@Validated @PathVariable("id") K id, @RequestBody D request) {
        log.info("Putting to id: {} - Object: {}", id, request);
        D dto = service.update(id, request);
        HttpUtils.addMessageHeader(MessageType.SUCCESS, RECORD_UPDATED_SUCCESSFULLY_MSG);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> get(@PathVariable("id") K id) {
        D dto = service.get(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<D> delete(@PathVariable("id") K id) {
        log.info("Deleting object with id: {}", id);
        service.delete(id);
        HttpUtils.addMessageHeader(MessageType.SUCCESS, RECORD_DELETED_SUCCESSFULLY_MSG);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<Page<D>> list(@RequestParam(value = PAGE_NUMBER_PARAM_NAME, defaultValue = "0") int pageNumber,
                                        @RequestParam(value = PAGE_SIZE_PARAM_NAME, defaultValue = "20") int pageSize,
                                        @RequestParam(value = SORT_PARAM_NAME, defaultValue = "") String sort) {

        PageRequest pageRequest = StringUtils.hasText(sort) ?
                PageRequest.of(pageNumber, pageSize, resolveSort(sort)) :
                PageRequest.of(pageNumber, pageSize);

        Page<D> dto = service.list(pageRequest);
        return ResponseEntity.ok(dto);
    }

    public static Sort resolveSort(String sort) {
        Pattern pattern = Pattern.compile("(.*)\\((.*)\\)");
        Matcher matcher = pattern.matcher(sort);
        if (!matcher.matches()) {
            return Sort.by(Sort.Order.asc(sort));
        }
        String result = matcher.group(2).toUpperCase(Locale.ROOT);
        return "ASC".equals(result) ? Sort.by(Sort.Order.asc(matcher.group(1))) :
                Sort.by(Sort.Order.desc(matcher.group(1)));
    }
}