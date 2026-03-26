package com.skyscanner.hoenScanner.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Search {
    @JsonProperty
    private String city;
    
    public Search() {
        // Default constructor for Jackson deserialization
    }
    
    public Search(String city) {
        this.city = city;
    }
    
    public String getCity() {
        return city;
    }
}
