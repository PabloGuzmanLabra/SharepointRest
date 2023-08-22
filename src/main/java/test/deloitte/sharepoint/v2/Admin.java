package test.deloitte.sharepoint.v2;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class Admin {
    @GetMapping("/{id}")
    public ResponseEntity<String> getItem(@PathVariable Long id) {
        // Simulate fetching an item from a database
        String item = "Item with ID " + id;
        return ResponseEntity.ok(item);
    }
    @GetMapping("/")
    public ResponseEntity<String> getItem() {
        // Simulate fetching an item from a database
        String item = "Item with ID ";
        return ResponseEntity.ok(item);
    }
}
