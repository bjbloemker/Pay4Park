package com.bjbloemker.core;

import com.bjbloemker.api.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MemoryManager {
    public static List<ParkObj> parks = Collections.synchronizedList(new ArrayList<ParkObj>());
    public static List<NoteObj> notes = Collections.synchronizedList(new ArrayList<NoteObj>());
    public static List<OrderObj> orders = Collections.synchronizedList(new ArrayList<OrderObj>());
    public static List<VisitorObj> visitors = Collections.synchronizedList(new ArrayList<VisitorObj>());

    //these reports can only occur once so they are statically defined
    //no need using UUIDs, because there is only 2
    public static final String ADMISSION_REPORT_ID = "998";
    public static final String ADMISSION_REPORT_TITLE = "Admissions report";
    public static final String REVENUE_REPORT_ID = "999";
    public static final String REVENUE_REPORT_TITLE = "Revenue report";

    public static List<ReportObj> reports = Collections.synchronizedList(new ArrayList<ReportObj>());

    public static void initReports(){
        ReportObj admissionReport = new Report(ADMISSION_REPORT_ID, ADMISSION_REPORT_TITLE);
        ReportObj revenueReport = new Report(REVENUE_REPORT_ID, REVENUE_REPORT_TITLE);
        reports.add(admissionReport);
        reports.add(revenueReport);
    }

    public static VisitorObj requestAddToVisitor(VisitorObj visitor){
        boolean add = true;

        for(VisitorObj v : visitors)
            if(v.getEmail().equals(visitor.getEmail()))
                add = false;

        if(add){
            MemoryManager.visitors.add(visitor);
            return visitor;
        }
        return null;//null if visitor already exist

    }

}
