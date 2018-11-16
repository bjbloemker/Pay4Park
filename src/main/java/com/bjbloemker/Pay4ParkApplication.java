package com.bjbloemker;

import com.bjbloemker.core.MemoryManager;
import com.bjbloemker.resources.*;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class Pay4ParkApplication extends Application<Pay4ParkConfiguration> {

    public static void main(final String[] args) throws Exception {
        new Pay4ParkApplication().run(args);
    }

    @Override
    public String getName() {
        return "Pay4Park";
    }

    @Override
    public void run(final Pay4ParkConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new ParkResource());
        environment.jersey().register(new NotesResource());
        environment.jersey().register(new OrderResource());
        environment.jersey().register(new VisitorsResource());
        environment.jersey().register(new ReportResource());
        environment.jersey().register(new SearchResource());
        MemoryManager.initReports();
    }

}
