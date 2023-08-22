package test.deloitte.sharepoint.v2.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import test.deloitte.sharepoint.v2.models.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class sharepointServices {
    private String mainUrl = "https://centertestdomain.sharepoint.com";
    public boolean createSite(String siteName, String bearer){
        String url = mainUrl+"/_api/web/webinfos/add";

        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("Authorization", bearer);
        if(!this.existSite(bearer, siteName)){
            WebInfoParametersRequest requestParameter = new WebInfoParametersRequest(siteName,siteName,"sts");
            WebInfoRequest request = new WebInfoRequest(requestParameter);
            Map<String, Object> requestBody = new HashMap<>();
            Map<String, String> parameterValues = new HashMap<>();
            parameterValues.put("Url",siteName);
            parameterValues.put("Title",siteName);
            parameterValues.put("WebTemplate", "sts");

            requestBody.put("parameters", parameterValues);

            HttpEntity< Map<String, Object> > httpEntity = new HttpEntity<>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        }
        return true;
    }

    public boolean existSite(String bearer, String siteName){
        String url =  mainUrl+"/"+siteName+"/_api/web/lists";
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("Authorization", bearer);

        HttpEntity httpEntity = new HttpEntity<>( headers);

        RestTemplate restTemplate = new RestTemplate();
        try{
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            return true;
        }catch(HttpStatusCodeException exception){
            if(exception.getStatusCode() == HttpStatus.NOT_FOUND){
                return false;
            }
            return false; // Implement exception
        }
    }

    public boolean createLib(String bearer, String lib, String site,String folder){
        if(!existsLib(bearer, lib, site)){
            String url =  mainUrl+"/"+site+"/_api/web/lists";
            HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", "application/json");
            headers.set("Authorization", bearer);

            LibRequest request = new LibRequest();
            Map<String, Object> requestValues = new HashMap<>();
            requestValues.put("BaseTemplate",101);
            requestValues.put("Title",lib);
            requestValues.put("AllowContentTypes",true);
            requestValues.put("ContentTypesEnabled",true);

            request.setBaseTemplate(101);
            request.setTitle(lib);
            request.setAllowContentTypes(true);
            request.setContentTypesEnabled(true);
            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestValues, headers);

            RestTemplate restTemplate = new RestTemplate();
            try{
                HttpStatusCode response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class).getStatusCode();
                if(response == HttpStatus.CREATED){

                    AssignMetadataLib(bearer,lib,site,folder);
                    return true;
                }
            }catch(HttpStatusCodeException exception){
                return false;
            }
            return true;
        }
        return true;
    }

    public boolean creteFolder(String bearer, String lib, String site, String folder, String subFolder){

            String url =  mainUrl+"/"+site+"/_api/web/lists/getbytitle('"+lib+"')/rootfolder/folders";
            HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", "application/json");
            headers.set("Authorization", bearer);

            CreateFolderRequest request = new CreateFolderRequest();
            Map<String, Object> requestValues = new HashMap<>();
            if(subFolder!= null){
                request.setServerRelativeUrl( mainUrl+"/"+site+"/"+lib+"/"+folder+"/"+subFolder);
                requestValues.put("ServerRelativeUrl",mainUrl+"/"+site+"/"+lib+"/"+folder+"/"+subFolder);
            }
            else{
                request.setServerRelativeUrl( mainUrl+"/"+site+"/"+lib+"/"+folder+"/");
                requestValues.put("ServerRelativeUrl",mainUrl+"/"+site+"/"+lib+"/"+folder);
            }
            HttpEntity< Map<String, Object>> httpEntity = new HttpEntity<>(requestValues, headers);

            RestTemplate restTemplate = new RestTemplate();
            HttpStatusCode response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class).getStatusCode();


            return  response ==HttpStatus.CREATED   ;
    }


    public boolean existsLib(String bearer, String lib, String site){
        String url =  mainUrl+"/"+site+"/_api/web/lists/getbytitle('"+lib+"')/items";
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("Authorization", bearer);

        HttpEntity httpEntity = new HttpEntity<>( headers);

        RestTemplate restTemplate = new RestTemplate();
        try{
            HttpStatusCode response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class ).getStatusCode();
            return response == HttpStatus.OK;
        }catch(HttpStatusCodeException exception){
            if(exception.getStatusCode() == HttpStatus.NOT_FOUND){

                return false;
            }
            return false; // Implement exception
        }
    }

    public void AssignMetadataLib(String bearer, String lib , String site, String folder)   {
        String url = mainUrl+"/"+site+"/_api/Web/GetFolderByServerRelativePath(decodedurl='/"+site+"/"+lib+"')/Properties";
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("Authorization", bearer);

        HttpEntity httpEntity = new HttpEntity<>( headers);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class );
        String jsonObject = response.getBody();

        try {

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(jsonObject, Map.class);
            String vti_x005f_listid = (String) map.get("vti_x005f_namespacelistid");


            ObjectMapper objectMapper = new ObjectMapper();
            itemsResponse itemsResponse = new itemsResponse();
            itemsResponse.setVti_x005f_listid(vti_x005f_listid);
            itemsResponse.vti_x005f_namespacelistid =itemsResponse.vti_x005f_namespacelistid.replace("{","").replace("}","");
            url = mainUrl+"/"+site+"/_api/web/lists(guid'"+itemsResponse.vti_x005f_namespacelistid+"')/Fields";


            Map<String, Object> requestValues = new HashMap<>();
            Map<String, Object> requestMetadata = new HashMap<>();
            requestMetadata.put("type","SP.Field");

            requestValues.put("__metadata",requestMetadata);
            requestValues.put("Title","Category");
            requestValues.put("FieldTypeKind",3);
            requestValues.put("StaticName","Category");
            requestValues.put("TypeDisplayName","Contract/Design");
            requestValues.put("TypeShortDescription","Contract/Design");

            headers = new org.springframework.http.HttpHeaders();
            headers.set("Accept", "application/json;odata=verbose");
            headers.set("Content-Type", "application/json;odata=verbose");
            headers.set("Authorization", bearer);

            httpEntity = new HttpEntity<>(requestValues, headers);


            HttpEntity<String> responseUpdate = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class );


            requestValues.put("__metadata",requestMetadata);
            requestValues.put("Title","Access");
            requestValues.put("FieldTypeKind",8);
            requestValues.put("TypeAsString","Boolean");
            requestValues.put("StaticName","Access");
            requestValues.put("TypeDisplayName","Yes/No");
            requestValues.put("TypeShortDescription","Yes/No (check box)");

            responseUpdate = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class );

        } catch (HttpStatusCodeException e) {
            HttpStatusCode responseStatusCode = e.getStatusCode();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return;
    }

    public Boolean assignFile(String bearer, String siteName, String lib, String folder, String subFolder,  MultipartFile file) throws IOException {
        String url = "";
        if(subFolder!=null){
            url = mainUrl+"/"+siteName+"/_api/Web/GetFolderByServerRelativePath(decodedurl='/"+siteName+"/"+lib+"/"+folder+"/"+subFolder+"')/Files/add(url='"+file.getOriginalFilename()+"', overwrite=true)";
        }else{
            url = mainUrl+"/"+siteName+"/_api/Web/GetFolderByServerRelativePath(decodedurl='/"+siteName+"/"+lib+"/"+folder+"')/Files/add(url='"+file.getOriginalFilename()+"', overwrite=true)";
        }

        byte[] fileContent = file.getBytes();
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Accept", "application/json;odata=verbose");
        headers.set("Content-Type", "application/json");
        headers.set("content-length", ""+file.getBytes().length);
        headers.set("Authorization", bearer);

        HttpEntity<byte[]> httpEntity  = new HttpEntity<>(fileContent , headers);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> responseUpdate = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class );


        return true;
    }

    public Boolean updateMetadataFile(String bearer, String siteName, String lib, String folder, String subFolder,  MultipartFile file, String category, boolean access) throws JsonProcessingException {
        String url = "";
        if(subFolder != null ){
            url = mainUrl+"/"+siteName+"/_api/Web/GetFileByServerRelativePath(decodedurl='/"+siteName+"/"+lib+"/"+folder+"/"+subFolder+"/"+file.getOriginalFilename()+"')/ListItemAllFields";
        }else{
            url = mainUrl+"/"+siteName+"/_api/Web/GetFileByServerRelativePath(decodedurl='/"+siteName+"/"+lib+"/"+folder+"/"+file.getOriginalFilename()+"')/ListItemAllFields";
        }
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Accept", "application/json;odata=verbose");
        headers.set("Content-Type", "application/json;odata=verbose");
        headers.set("Authorization", bearer);

        HttpEntity<String> httpEntity  = new HttpEntity<>( headers);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> getFileDetails = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class );
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(getFileDetails.getBody(), Map.class);
        Map<String, Object> d = (Map<String, Object>) map.get("d");
        Map<String, String> _metadata = (Map<String, String>) d.get("__metadata");
        String eTag = _metadata.get("etag");
        eTag= eTag.replace("\"","");


        headers.set("X-HTTP-Method", "PATCH");
        headers.set("IF-MATCH", "\""+eTag+"\"");


        Map<String, Object> request = new HashMap<>();
        Map<String, String> requestMetadata = new HashMap<>();
        requestMetadata.put("type","SP.Data."+uppercaseFirstCharacter(lib)+"Item");
        request.put("__metadata", requestMetadata);
        request.put("Category", category);
        request.put("Access", access);


        HttpEntity httpEntityMetadata = new HttpEntity<>(request, headers);
        HttpStatusCode responseMetadata = restTemplate.exchange(url, HttpMethod.POST, httpEntityMetadata, String.class ).getStatusCode();

        return responseMetadata == HttpStatus.NO_CONTENT;
    }

    public static String uppercaseFirstCharacter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public boolean generateContentDirectory(String bearer, String siteName, String lib, String folder, String subFolder,  MultipartFile file, String category, boolean access) throws IOException {
        byte[] data = file.getBytes();

        createSite(siteName, bearer);
        createLib(bearer,lib,siteName,folder);
        creteFolder(bearer,lib,siteName,folder,null);
        if(subFolder!= null){
            creteFolder(bearer,lib,siteName,folder,subFolder);
        }
        assignFile(bearer, siteName, lib, folder, subFolder, file);
        updateMetadataFile(bearer, siteName, lib, folder, subFolder, file, category, access);
        return true;
    }
}
