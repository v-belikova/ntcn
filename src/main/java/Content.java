import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
    public class Content {
        Integer id;
        String title;
        String description;
        String image;
        String userId;
        String username;
         ArrayList<Content> content = new ArrayList<>();


        Integer numberOfElements;

    public ArrayList<Content> getContent() {
        return content;
    }

    public void setContent(ArrayList<Content> content) {
        this.content = content;
    }

    Integer idTag;
        String titleTag;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }



        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getIdTag() {
            return idTag;
        }

        public void setIdTag(int idTag) {
            this.idTag = idTag;
        }

        public String getTitleTag() {
            return titleTag;
        }

        public void setTitleTag(String titleTag) {
            this.titleTag = titleTag;
        }
    }
