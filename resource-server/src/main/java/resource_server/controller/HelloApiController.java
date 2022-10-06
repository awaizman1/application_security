package resource_server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import resource_server.generated.api.HelloApi;

@RestController
public class HelloApiController implements HelloApi {

    @Override
    public ResponseEntity<String> helloGet() {
        return ResponseEntity.ok("hello");
    }
}