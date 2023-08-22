package test.deloitte.sharepoint.v2.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {
        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        public String getNot_before() {
            return not_before;
        }

        public void setNot_before(String not_before) {
            this.not_before = not_before;
        }

        public String getExpires_on() {
            return expires_on;
        }

        public void setExpires_on(String expires_on) {
            this.expires_on = expires_on;
        }

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public TokenResponse(String token_type, String expires_in, String not_before, String expires_on, String resource, String access_token) {
            this.token_type = token_type;
            this.expires_in = expires_in;
            this.not_before = not_before;
            this.expires_on = expires_on;
            this.resource = resource;
            this.access_token = access_token;
        }

    public TokenResponse() {
    }
    @JsonProperty("token_type")
    private String token_type;
    @JsonProperty("expires_in")
    private String expires_in;
    @JsonProperty("not_before")
    private String not_before;
    @JsonProperty("expires_on")
    private String expires_on;
    @JsonProperty("resource")
    private String resource;
    @JsonProperty("access_token")
    private String access_token;

}
