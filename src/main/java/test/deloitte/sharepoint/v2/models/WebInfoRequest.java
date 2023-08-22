package test.deloitte.sharepoint.v2.models;

public class WebInfoRequest {
        public WebInfoParametersRequest getParameters() {
            return parameters;
        }

        public void setParameters(WebInfoParametersRequest parameters) {
            this.parameters = parameters;
        }

        public WebInfoRequest(WebInfoParametersRequest parameters) {
            this.parameters = parameters;
        }

        private WebInfoParametersRequest parameters;

        // Getters and setters
    }


