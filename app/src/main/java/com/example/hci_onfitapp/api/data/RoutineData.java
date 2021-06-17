package com.example.hci_onfitapp.api.data;

import android.service.autofill.UserData;

import com.example.hci_onfitapp.api.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RoutineData implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("detail")
        @Expose
        private String detail;
        @SerializedName("isPublic")
        @Expose
        private boolean isPublic;
        @SerializedName("difficulty")
        @Expose
        private String difficulty;
        @SerializedName("category")
        @Expose
        private CategoryData category;
        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("metadata")
        @Expose
        private Object metadata;

        private String image;

        /**
         * No args constructor for use in serialization
         *
         */
        public RoutineData() {
        }

        public RoutineData(Integer id, String name, String detail, boolean isPublic, String difficulty, CategoryData category, User userData, Object metadata, String image) {
            super();
            this.id = id;
            this.name = name;
            this.detail = detail;
            this.isPublic = isPublic;
            this.difficulty = difficulty;
            this.category = category;
            this.user = userData;
            this.metadata = metadata;
            this.image = image;
        }

        public Integer getId(){
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public boolean isIsPublic() {
            return isPublic;
        }

        public void setIsPublic(boolean isPublic) {
            this.isPublic = isPublic;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(String difficulty) {
            this.difficulty = difficulty;
        }

        public CategoryData getCategory() {
            return category;
        }

        public void setCategory(CategoryData category) {
            this.category = category;
        }

        public User getUser(){
            return user;
        }

        public void setUser(User userData) {
            this.user = userData;
        }

        public Object getMetadata() {
            return metadata;
        }

        public void setMetadata(Object metadata) {
            this.metadata = metadata;
        }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage(String image){
            return image;
    }
}
