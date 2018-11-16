package com.bjbloemker.resources;

import com.bjbloemker.api.ParkObj;
import com.bjbloemker.api.ReportObj;
import com.bjbloemker.core.MemoryManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class ReportResourceTest extends DataForTesting {

    ReportResource ReportResource = new ReportResource();

    @BeforeEach
    void init(){
        MemoryManager.parks.clear();
        MemoryManager.orders.clear();
        MemoryManager.notes.clear();
        MemoryManager.visitors.clear();
        MemoryManager.reports.clear();
        MemoryManager.initReports();

    }

    @Test
    void getReports() {
        Response result = ReportResource.getReports();
        ReportObj report1 = MemoryManager.reports.get(0);
        ReportObj report2 = MemoryManager.reports.get(1);
        String expect = "[{\"rid\":\""+report1.getRid()+"\",\"name\":\""+report1.getTitle()+"\"},{\"rid\":\""+report2.getRid()+"\",\"name\":\""+report2.getTitle()+"\"}]";
        assertEquals(expect, result.getEntity());
    }

    @Test
    void searchReportsNullDates() {
        buildParkAndSetupOrder();
        buildParkAndSetupOrder();//set up 2 parks with 2 orders and 2 customers (all with same info but diff IDs)
        Response result = ReportResource.searchReports("998", "", "");
        ReportObj report1 = MemoryManager.reports.get(0);
        ParkObj park1 = MemoryManager.parks.get(0);
        ParkObj park2 = MemoryManager.parks.get(1);
        String expect = "{\"rid\":\""+report1.getRid()+"\",\"name\":\""+report1.getTitle()+"\",\"start_date\":\"\",\"end_date\":\"\",\"total_admissions\":2,\"detail_by_park\":[{\"pid\":\""+park1.getPIDAsString()+"\",\"name\":\""+parkName+"\",\"admissions\":1},{\"pid\":\""+park2.getPIDAsString()+"\",\"name\":\""+parkName+"\",\"admissions\":1}]}";
        assertEquals(expect, result.getEntity());
    }

    @Test
    void searchReportsReverseDates() {
        Response result = ReportResource.searchReports("998", "20180412", "20170804");
        assertEquals(400, result.getStatus());
    }

    @Test
    void searchReportsBadDate() {
        Response result = ReportResource.searchReports("998", "20170804563", "20170804564");
        assertEquals(400, result.getStatus());
    }

    @Test
    void searchReportsInvalidStartDate() {
        Response result = ReportResource.searchReports("998", "20170833", "20171231");
        assertEquals(400, result.getStatus());
    }

    @Test
    void searchReportsInvalidEndDate() {
        Response result = ReportResource.searchReports("998", "20170814", "20171232");
        assertEquals(400, result.getStatus());
    }

    @Test
    void searchReportsValidRevenue() {
        buildParkAndSetupOrder();
        buildParkAndSetupOrder();//set up 2 parks with 2 orders and 2 customers (all with same info but diff IDs)
        Response result = ReportResource.searchReports("999", "", "");
        ReportObj report2 = MemoryManager.reports.get(0);
        ParkObj park1 = MemoryManager.parks.get(0);
        ParkObj park2 = MemoryManager.parks.get(1);
        String expect = "{\"rid\":\""+MemoryManager.REVENUE_REPORT_ID+"\",\"name\":\""+MemoryManager.REVENUE_REPORT_TITLE+"\",\"start_date\":\"\",\"end_date\":\"\",\"total_orders\":2,\"total_revenue\":10.0,\"detail_by_park\":[{\"pid\":\""+park1.getPIDAsString()+"\",\"name\":\""+parkName+"\",\"orders\":1,\"revenue\":5.0},{\"pid\":\""+park2.getPIDAsString()+"\",\"name\":\""+parkName+"\",\"orders\":1,\"revenue\":5.0}]}";
        assertEquals(expect, result.getEntity());
    }

    @Test
    void searchReportsValidRevenueWithEndDate() {
        buildParkAndSetupOrder();
        buildParkAndSetupOrder();//set up 2 parks with 2 orders and 2 customers (all with same info but diff IDs)
        Response result = ReportResource.searchReports("999", "", "20180101");
        ReportObj report2 = MemoryManager.reports.get(0);
        ParkObj park1 = MemoryManager.parks.get(0);
        ParkObj park2 = MemoryManager.parks.get(1);
        String expect = "{\"rid\":\""+MemoryManager.REVENUE_REPORT_ID+"\",\"name\":\""+MemoryManager.REVENUE_REPORT_TITLE+"\",\"start_date\":\"\",\"end_date\":\"2018-01-01\",\"total_orders\":0,\"total_revenue\":0.0,\"detail_by_park\":[{\"pid\":\""+park1.getPIDAsString()+"\",\"name\":\""+parkName+"\",\"orders\":0,\"revenue\":0.0},{\"pid\":\""+park2.getPIDAsString()+"\",\"name\":\""+parkName+"\",\"orders\":0,\"revenue\":0.0}]}";
        assertEquals(expect, result.getEntity());
    }

    @Test
    void validateDateBadRid1(){
        assertEquals(400, ReportResource.searchReports("fake_ird", "20160101", "20181301").getStatus());
    }

    @Test
    void validateDateBadDate1(){
        assertEquals(400, ReportResource.searchReports("999", "", "20181301").getStatus());
    }

    @Test
    void validateVariousGoodDates(){
        //addresses branches
        assertEquals(200, ReportResource.searchReports("998", "20180101", "20180201").getStatus());
        assertEquals(200, ReportResource.searchReports("998", "20180301", "20180401").getStatus());
        assertEquals(200, ReportResource.searchReports("998", "20180501", "20180601").getStatus());
        assertEquals(200, ReportResource.searchReports("998", "20180701", "20180801").getStatus());
        assertEquals(200, ReportResource.searchReports("998", "20180901", "20181001").getStatus());
        assertEquals(200, ReportResource.searchReports("998", "20181101", "20181201").getStatus());
    }


    @AfterAll
    static void cleanUp(){
        MemoryManager.parks.clear();
        MemoryManager.orders.clear();
        MemoryManager.notes.clear();
        MemoryManager.visitors.clear();
        MemoryManager.reports.clear();
    }

}