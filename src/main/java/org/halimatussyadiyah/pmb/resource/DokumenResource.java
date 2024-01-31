package org.halimatussyadiyah.pmb.resource;

import lombok.RequiredArgsConstructor;
import org.halimatussyadiyah.pmb.domain.Dokumen;
import org.halimatussyadiyah.pmb.domain.HttpResponse;
import org.halimatussyadiyah.pmb.dto.UserDTO;
import org.halimatussyadiyah.pmb.service.DokumenService;
import org.halimatussyadiyah.pmb.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static java.util.Map.of;

@RestController
@RequestMapping(path = "/dokumen")
@RequiredArgsConstructor
public class DokumenResource {
    private final DokumenService dokumenService;
    private final UserService userService;
    @GetMapping("/list")
    public ResponseEntity<HttpResponse> getDokumens(@AuthenticationPrincipal UserDTO user,
                                                     @RequestParam Optional<Integer> page,
                                                     @RequestParam Optional<Integer> size) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "page", dokumenService.getDokumens(page.orElse(0), size.orElse(10))))
                        .message("Dokumens retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createDokumen(@AuthenticationPrincipal UserDTO user,
                                                       @RequestBody Dokumen dokumen) {
        return ResponseEntity.created(URI.create(""))
                .body(
                        HttpResponse.builder()
                                .timeStamp(now().toString())
                                .data(of("user", userService.getUserByEmail(user.getEmail()),
                                        "dokumen", dokumenService.createDokumen(dokumen)))
                                .message("Dokumen created")
                                .status(CREATED)
                                .statusCode(CREATED.value())
                                .build());
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<HttpResponse> getDokumen(@AuthenticationPrincipal UserDTO user,
                                                    @PathVariable("id") Long id) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "dokumen", dokumenService.getDokumen(id)))
                        .message("Dokumen retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @GetMapping("/search")
    public ResponseEntity<HttpResponse> searchDokumen(@AuthenticationPrincipal UserDTO user,
                                                       Optional<String> name,
                                                       @RequestParam Optional<Integer> page,
                                                       @RequestParam Optional<Integer> size) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "page", dokumenService.searchDokumens(name.orElse(""), page.orElse(0), size.orElse(10))))
                        .message("Biodata retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @PutMapping("/update")
    public ResponseEntity<HttpResponse> updateDokumen(@AuthenticationPrincipal UserDTO user,
                                                       @RequestBody Dokumen dokumen) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "dokumen", dokumenService.updateDokumen(dokumen)))
                        .message("Dokumen updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }


}
