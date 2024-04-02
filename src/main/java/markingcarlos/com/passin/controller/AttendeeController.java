package markingcarlos.com.passin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attenddes")
public class AttendeeController {
    @GetMapping
    public ResponseEntity<String> getTeste(){
        return ResponseEntity.ok("sucesso!");
    }
}
