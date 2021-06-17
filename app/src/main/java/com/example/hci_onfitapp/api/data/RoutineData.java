package com.example.hci_onfitapp.api.data;

import android.service.autofill.UserData;

import com.example.hci_onfitapp.api.User;
import com.example.hci_onfitapp.api.model.PagedList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RoutineData implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("date")
    @Expose
    private long date;
    @SerializedName("averageRating")
    @Expose
    private int averageRating;
    @SerializedName("isPublic")
    @Expose
    private boolean isPublic;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("metadata")
    @Expose
    private Object metadata;
    @SerializedName("category")
    @Expose
    private CategoryData category;
    @SerializedName("user")
    @Expose
    private User user;

    private PagedList<CycleData> routineCycles = null;
    private String image;

    public RoutineData() {
    }

    public RoutineData(int id, String name, String detail, long date, int averageRating, boolean isPublic, String difficulty, Object metadata, CategoryData category, User user) {
        super();
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.date = date;
        this.averageRating = averageRating;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.metadata = metadata;
        this.category = category;
        this.user = user;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
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

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public CategoryData getCategory() {
        return category;
    }

    public void setCategory(CategoryData category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRoutineCycles(PagedList<CycleData> cycles){
        this.routineCycles = cycles;
    }

    public PagedList<CycleData> getRoutineCycles(){
        return routineCycles;
    }

    @Override
    public String toString() {
        return "RoutineData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", date='" + date + '\'' +
                ", averageRating=" + averageRating +
                ", diffilcuty='" + difficulty + '\'' +
                ", user=" + user +
                ", category=" + category +
                '}';
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage(String image){
        return image;
    }
}
