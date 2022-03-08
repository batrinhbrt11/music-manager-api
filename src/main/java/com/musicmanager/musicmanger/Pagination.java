package com.musicmanager.musicmanger;
import java.util.*;

public class Pagination {
    public static final int PAGE_SIZE = 10;
    int page;
    List list;
    public Pagination(int page, List list) {
        this.page=page;
        this.list=list;
    }
    public List paginationsList() {
        int startItem = (page -1 ) * PAGE_SIZE;
        int endItem =page*PAGE_SIZE;
        if(startItem >list.size() ){
            return new ArrayList<>();
        }
        if(endItem > list.size()){
           return  list.subList(startItem,list.size());
        }else{
           
           return   list.subList(startItem, endItem);
        }
    }
}
