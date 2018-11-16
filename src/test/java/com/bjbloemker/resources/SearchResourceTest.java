package com.bjbloemker.resources;

import com.bjbloemker.core.MemoryManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchResourceTest extends DataForTesting {

    SearchResource SearchResource = new SearchResource();

    @BeforeEach
    void init(){
        MemoryManager.parks.clear();
        MemoryManager.orders.clear();
        MemoryManager.notes.clear();
        MemoryManager.visitors.clear();
    }

    @Test
    void searchNullKey() {
        assertEquals(200, SearchResource.search(null, null, null).getStatus());
    }

    @Test
    void searchSomeStuff() {
        buildParkAndSetupOrder();
        buildPark();
        buildParkAndSetupOrder();
        assertEquals(200, SearchResource.search("park", null, null).getStatus());
    }





    @AfterAll
    static void cleanUp(){
        MemoryManager.parks.clear();
        MemoryManager.orders.clear();
        MemoryManager.notes.clear();
        MemoryManager.visitors.clear();
    }


}