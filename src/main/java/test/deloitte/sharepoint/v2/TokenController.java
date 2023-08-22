package test.deloitte.sharepoint.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.server.PathParam;
import jdk.jfr.Category;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import test.deloitte.sharepoint.v2.models.MetadataRequest;
import test.deloitte.sharepoint.v2.models.TokenResponse;
import test.deloitte.sharepoint.v2.services.sharepointServices;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TokenController {

    private String bearerToken; // To store the token
    private String  clientId ="39c70194-53a8-4c96-9e55-3b78bb5a9600@957f9cd2-cf38-4399-9416-a342e75e0503";
    private String  resource = "00000003-0000-0ff1-ce00-000000000000/centertestdomain.sharepoint.com@957f9cd2-cf38-4399-9416-a342e75e0503";
    private String  clientSecret = "f+JLadz8WsiatjG5k8gzA2YgcsP0fmplN3rq+qlJ3wc=";
    sharepointServices services = new sharepointServices();

    @PostMapping("/get-token")
    public ResponseEntity<String> getToken(
            @RequestParam String clientId,
            @RequestParam String clientSecret,
            @RequestParam String resource) {

        String tokenUrl = "https://accounts.accesscontrol.windows.net/957f9cd2-cf38-4399-9416-a342e75e0503/tokens/OAuth/2";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Accept", "application/json;odata=verbose");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("resource", resource);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request,String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            TokenResponse tokenResponse = objectMapper.readValue(response.getBody().toString(), TokenResponse.class);
            System.out.println("Token Type: " + tokenResponse.getToken_type());
            System.out.println("Expires In: " + tokenResponse.getExpires_in());
            bearerToken = tokenResponse.getToken_type()+" "+tokenResponse.getAccess_token();
            // ... print other properties
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Token obtained and stored.");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Token retrieval failed.");
        }
    }

    @GetMapping("/check-token")
    public ResponseEntity<String> checkToken() {
        this.getToken(this.clientId, this.clientSecret, this.resource);
        if (bearerToken != null) {
            return ResponseEntity.ok("Bearer Token exists.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bearer Token not found.");
        }
    }


    @PostMapping("/generate-document/{site}/{library}/{folder}/{subfolder}")
    public ResponseEntity<String> generateDocument(@PathVariable String site, @PathVariable String library, @PathVariable String folder, @PathVariable String subfolder, @RequestParam("file") MultipartFile file,@RequestParam("Category") String category,@RequestParam("Access") Boolean access) throws IOException {

        this.getToken(this.clientId, this.clientSecret, this.resource);
        services.generateContentDirectory(this.bearerToken,site,library,folder,subfolder,file, category,access);
        if (bearerToken != null) {
            return ResponseEntity.ok("Bearer Token exists.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bearer Token not found.");
        }
    }


    @PostMapping("/generate-metadata/{site}/{library}/{folder}")
    public ResponseEntity<String> generateMetaData(@PathVariable String site, @PathVariable String library, @PathVariable String folder ) {

        this.getToken(this.clientId, this.clientSecret, this.resource);
        services.AssignMetadataLib(this.bearerToken,library,site,folder);

        if (bearerToken != null) {
            return ResponseEntity.ok("Metadata Assigned");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("problems");
        }
    }

}
