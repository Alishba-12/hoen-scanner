package com.skyscanner.hoenScanner.resources;

import com.skyscanner.hoenScanner.api.Search;
import com.skyscanner.hoenScanner.api.SearchResult;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {
    
    private final List<SearchResult> searchResults;
    
    public SearchResource(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }
    
    @POST
    public Response search(Search search) {
        if (search == null || search.getCity() == null || search.getCity().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("City parameter is required")
                .build();
        }
        
        String cityToSearch = search.getCity().trim().toLowerCase();
        
        List<SearchResult> filteredResults = searchResults.stream()
            .filter(result -> result.getCity().toLowerCase().equals(cityToSearch))
            .collect(Collectors.toList());
        
        return Response.ok(filteredResults).build();
    }
}
