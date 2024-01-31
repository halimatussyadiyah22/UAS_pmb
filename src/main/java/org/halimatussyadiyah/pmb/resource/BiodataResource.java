package org.halimatussyadiyah.pmb.resource;

import lombok.RequiredArgsConstructor;
import org.halimatussyadiyah.pmb.domain.Biodata;
import org.halimatussyadiyah.pmb.domain.Card;
import org.halimatussyadiyah.pmb.domain.Dokumen;
import org.halimatussyadiyah.pmb.domain.HttpResponse;
import org.halimatussyadiyah.pmb.dto.UserDTO;
import org.halimatussyadiyah.pmb.report.BiodataReport;
import org.halimatussyadiyah.pmb.report.CardReport;
import org.halimatussyadiyah.pmb.service.BiodataService;
import org.halimatussyadiyah.pmb.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static java.util.Map.of;
import static org.springframework.http.MediaType.parseMediaType;

@RestController
@RequestMapping(path = "/biodata")
@RequiredArgsConstructor
public class BiodataResource {
    private final BiodataService biodataService;
    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<HttpResponse> getBiodatas(@AuthenticationPrincipal UserDTO user,
                                                     @RequestParam Optional<Integer> page,
                                                     @RequestParam Optional<Integer> size) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "page", biodataService.getBiodata(page.orElse(0), size.orElse(10)),
                                "stats", biodataService.getStats()))
                        .message("Biodata retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createBiodata(@AuthenticationPrincipal UserDTO user,
                                                       @RequestBody Biodata biodata) {
        return ResponseEntity.created(URI.create(""))
                .body(
                        HttpResponse.builder()
                                .timeStamp(now().toString())
                                .data(of("user", userService.getUserByEmail(user.getEmail()),
                                        "biodata", biodataService.createBiodata(biodata)))
                                .message("Biodata created")
                                .status(CREATED)
                                .statusCode(CREATED.value())
                                .build());
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<HttpResponse> getBiodata(@AuthenticationPrincipal UserDTO user,
                                                   @PathVariable("id") Long id) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "biodata", biodataService.getBiodata(id)))
                        .message("Biodata retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @GetMapping("/search")
    public ResponseEntity<HttpResponse> searchBiodata(@AuthenticationPrincipal UserDTO user,
                                                       Optional<String> name,
                                                       @RequestParam Optional<Integer> page,
                                                       @RequestParam Optional<Integer> size) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "page", biodataService.searchBiodata(name.orElse(""), page.orElse(0), size.orElse(10))))
                        .message("Biodata retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @PutMapping("/update")
    public ResponseEntity<HttpResponse> updateBiodata(@AuthenticationPrincipal UserDTO user,
                                                      @RequestBody Biodata biodata) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "biodata", biodataService.updateBiodata(biodata)))
                        .message("Biodata updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @PostMapping("/card/created")
    public ResponseEntity<HttpResponse> createCard(@AuthenticationPrincipal UserDTO user,
                                                      @RequestBody Card card) {
        return ResponseEntity.created(URI.create(""))
                .body(
                        HttpResponse.builder()
                                .timeStamp(now().toString())
                                .data(of("user", userService.getUserByEmail(user.getEmail()),
                                        "card", biodataService.createCard(card)))
                                .message("Card created")
                                .status(CREATED)
                                .statusCode(CREATED.value())
                                .build());
    }

    @GetMapping("/card/new")
    public ResponseEntity<HttpResponse> newCard(@AuthenticationPrincipal UserDTO user) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "cards", biodataService.getBiodata()))
                        .message("Cards retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/card/list")
    public ResponseEntity<HttpResponse> getCards(@AuthenticationPrincipal UserDTO user,
                                                    @RequestParam Optional<Integer> page,
                                                    @RequestParam Optional<Integer> size) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "page", biodataService.getCards(page.orElse(0), size.orElse(10))))
                        .message("Card retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/card/get/{id}")
    public ResponseEntity<HttpResponse> getCard(@AuthenticationPrincipal UserDTO user, @PathVariable("id") Long id) {
        Card card = biodataService.getCard(id);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "card", card, "customer", card.getBiodata()))
                        .message("Card retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping("/card/addtobiodata/{id}")
    public ResponseEntity<HttpResponse> addCardToBiodata(@AuthenticationPrincipal UserDTO user,
                                                             @PathVariable("id") Long id,
                                                             @RequestBody Card card) {
        biodataService.addCardToBiodata(id, card);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "biodata", biodataService.getBiodata()))
                        .message(String.format("Invoice added to customer with ID: %s", id))
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/download/biodatas")
    public ResponseEntity<Resource> downloadBiodataReport() {
        List<Biodata> biodata = new ArrayList<>();
        biodataService.getBiodata().iterator().forEachRemaining(biodata::add);
        BiodataReport report = new BiodataReport(biodata);
        HttpHeaders headers = new HttpHeaders();
        headers.add("File-Name", "customer-report.xlsx");
        headers.add(CONTENT_DISPOSITION, "attachment;File-Name=customer-report.xlsx");
        return ResponseEntity.ok().contentType(parseMediaType("application/vnd.ms-excel"))
                .headers(headers).body(report.export());
    }

    @GetMapping("/download/cards")
    public ResponseEntity<Resource> downloadCardReport() {
        List<Card> cards = new ArrayList<>();
        biodataService.getCards().iterator().forEachRemaining(cards::add);
        CardReport report = new CardReport(cards);
        HttpHeaders headers = new HttpHeaders();
        headers.add("File-Name", "invoices-report.xlsx");
        headers.add(CONTENT_DISPOSITION, "attachment;File-Name=invoices-report.xlsx");
        return ResponseEntity.ok().contentType(parseMediaType("application/vnd.ms-excel"))
                .headers(headers).body(report.export());
    }

}
