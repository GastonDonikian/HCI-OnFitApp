package com.example.hci_onfitapp.api.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryData {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("detail")
        @Expose
        private String detail;

        /**
         * No args constructor for use in serialization
         *
         */
        public CategoryData() {
        }

        /**
         *
         * @param name
         * @param id
         * @param detail
         */
        public CategoryData(int id, String name, String detail) {
            super();
            this.id = id;
            this.name = name;
            this.detail = detail;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

}
