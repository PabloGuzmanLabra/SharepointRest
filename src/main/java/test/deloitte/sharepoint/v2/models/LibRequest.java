package test.deloitte.sharepoint.v2.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LibRequest {

        @JsonProperty("BaseTemplate")
        private int BaseTemplate;

        @JsonProperty("Title")
        private String Title;

        @JsonProperty("AllowContentTypes")
        private boolean AllowContentTypes;

        @JsonProperty("ContentTypesEnabled")
        private boolean ContentTypesEnabled;

        // Getters and setters
        public int getBaseTemplate() {
            return BaseTemplate;
        }

        public void setBaseTemplate(int baseTemplate) {
            BaseTemplate = baseTemplate;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public boolean isAllowContentTypes() {
            return AllowContentTypes;
        }

        public void setAllowContentTypes(boolean allowContentTypes) {
            AllowContentTypes = allowContentTypes;
        }

        public boolean isContentTypesEnabled() {
            return ContentTypesEnabled;
        }

        public void setContentTypesEnabled(boolean contentTypesEnabled) {
            ContentTypesEnabled = contentTypesEnabled;
        }
    }

