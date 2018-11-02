package com.bjbloemker.resources;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;


@Path("/hello")
public class HelloWorldResource {
    @GET
    public String helloWorld(@QueryParam("name") String name) {
        if(name != null)
            return "Hello " + name + "!";
        else
            return "Hello not World! ";

    }
}