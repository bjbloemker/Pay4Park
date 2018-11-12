package com.bjbloemker.core;

import com.bjbloemker.api.NoteObj;
import com.bjbloemker.api.OrderObj;
import com.bjbloemker.api.ParkObj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryManager {
    public static List<ParkObj> parks = Collections.synchronizedList(new ArrayList<ParkObj>());
    public static List<NoteObj> notes = Collections.synchronizedList(new ArrayList<NoteObj>());
    public static List<OrderObj> orders = Collections.synchronizedList(new ArrayList<OrderObj>());

}
