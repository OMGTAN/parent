package org.example;

import javax.swing.text.EditorKit;
import java.util.*;

class MyCalendarTwo {


    Map<Integer, Integer> map = new TreeMap<>();
    Map<Integer, Integer> doubleConfilct = new TreeMap<>();

    public MyCalendarTwo() {

    }
    
    public boolean book(int start, int end) {
        Set<Map.Entry<Integer, Integer>> entries1 = doubleConfilct.entrySet();
        Iterator<Map.Entry<Integer, Integer>> iterator1 = entries1.iterator();
        while (iterator1.hasNext()) {
            Map.Entry<Integer, Integer> next =  iterator1.next();
            Integer key = next.getKey();
            Integer value = next.getValue();
            if(end<=start){
                break;
            }
            if((start>=key&&start<value)||(end>key&&end<value)||(key>=start&&key<end)||(value>start&&value<end)){
                return false;
            }
        }
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        Iterator<Map.Entry<Integer, Integer>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> next = iterator.next();
            Integer key = next.getKey();
            Integer value = next.getValue();
            if(end<=start||start >value){
                map.put(start,end);
                break;
            }
            if((key>=start&&key<end)&&(value>start&&value<end)){
                iterator.remove();
                map.put(start,value);
                doubleConfilct.put(key,value);
                start=value;
                continue;
            }
            if(start>=key&&start<value){
                if(end<=value){
                    doubleConfilct.put(start,end);
                    break;
                }else {
                    doubleConfilct.put(start,value);
                    start=value;
                    continue;
                }
            }
            if(end>key&&end<value){
                doubleConfilct.put(key,end);
                iterator.remove();
                map.put(start,value);
                break;
            }
        }
        if(map.size()==0){
            map.put(start, end);
        }
        return true;
    }

    public static void main(String[] args) {
        MyCalendarTwo myCalendarTwo = new MyCalendarTwo();
        int [][] a = new int[][]{{10,20},{50,60},{10,40},{5,15},{5,10},{25,55}};
        for (int i = 0; i < a.length; i++) {
            int[] ints = a[i];
            boolean book = myCalendarTwo.book(ints[0], ints[1]);
            System.out.print(book+", ");
        }
    }
}