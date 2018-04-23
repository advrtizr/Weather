package com.codemkr.android.weather.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Weather {

    @SerializedName("query")
    @Expose
    private Query query;
    private UUID mUUID;
    private boolean mUpdated;
    private String mLocationKey;

    public Weather() {
        this(UUID.randomUUID());
    }

    public Weather (UUID id){
        mUUID = id;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public String getLocationKey() {
        return mLocationKey;
    }

    public void setLocationKey(String location) {
        mLocationKey = location;
    }

    public boolean isUpdated() {
        return mUpdated;
    }

    public void setUpdated(boolean updated) {
        mUpdated = updated;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

}
