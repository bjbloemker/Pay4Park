package com.bjbloemker.core;

import com.bjbloemker.api.ParkObj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryManager {
    public static List<ParkObj> parks = Collections.synchronizedList(new ArrayList<ParkObj>());

}
