package com.bjbloemker.core;

import com.bjbloemker.api.NoteObj;
import com.bjbloemker.api.OrderObj;
import com.bjbloemker.api.ParkObj;
import com.bjbloemker.api.VisitorObj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryManager {
    public static List<ParkObj> parks = Collections.synchronizedList(new ArrayList<ParkObj>());
    public static List<NoteObj> notes = Collections.synchronizedList(new ArrayList<NoteObj>());
    public static List<OrderObj> orders = Collections.synchronizedList(new ArrayList<OrderObj>());
    public static List<VisitorObj> visitors = Collections.synchronizedList(new ArrayList<VisitorObj>());




    public static void requestAddToVisitor(VisitorObj visitor){
        boolean add = true;

        for(VisitorObj v : visitors){
            if(v.getEmail().equals(visitor.getEmail()))
                add = false;
        }

        if(add)
            MemoryManager.visitors.add(visitor);
    }

}
