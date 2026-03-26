package com.skyscanner.hoenScanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.skyscanner.hoenScanner.api.SearchResult;
import com.skyscanner.hoenScanner.resources.SearchResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {
    
    private List<SearchResult> searchResults = new ArrayList<>();
    
    public static void main(String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }
    
    @Override
    public void initialize(Bootstrap<HoenScannerConfiguration> bootstrap) {
        // Nothing needed here
    }
    
    @Override
    public void run(HoenScannerConfiguration configuration, Environment environment) throws Exception {
        // Load rental cars
        ObjectMapper mapper = new ObjectMapper();
        InputStream rentalCarsStream = getClass().getClassLoader().getResourceAsStream("rental_cars.json");
        if (rentalCarsStream != null) {
            List<SearchResult> rentalCars = mapper.readValue(rentalCarsStream, new TypeReference<List<SearchResult>>() {});
            searchResults.addAll(rentalCars);
        }
        
        // Load hotels
        InputStream hotelsStream = getClass().getClassLoader().getResourceAsStream("hotels.json");
        if (hotelsStream != null) {
            List<SearchResult> hotels = mapper.readValue(hotelsStream, new TypeReference<List<SearchResult>>() {});
            searchResults.addAll(hotels);
        }
        
        // Register the resource with the search results
        environment.jersey().register(new SearchResource(searchResults));
    }
}
