package org.example;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class UndergroundSystem {

    Map<Integer, Pair<String,Integer>> checkeIn ;
    Map<String, Pair<Double,Integer>> total;

    public UndergroundSystem() {
        checkeIn = new HashMap<Integer, Pair<String,Integer>>();
        total = new HashMap<String, Pair<Double,Integer>>();
    }
    
    public void checkIn(int id, String stationName, int t) {
        checkeIn.put(id,new Pair(stationName,t));
    }
    
    public void checkOut(int id, String stationName, int t) {
        Pair pair = checkeIn.get(id);
        String start = (String)pair.getKey();
        Pair count = total.getOrDefault(start +" "+ stationName, new Pair(0d, 0));
        total.put(start +" "+ stationName, new Pair(((double)count.getKey())+t-(int)pair.getValue(), (int)count.getValue()+1));
    }
    
    public double getAverageTime(String startStation, String endStation) {
        Pair<Double, Integer> doubleIntegerPair = total.get(startStation +" "+ endStation);

        return doubleIntegerPair.getKey()/doubleIntegerPair.getValue();
    }

    public static void main(String[] args) {
        UndergroundSystem undergroundSystem = new UndergroundSystem();
        String[] s = new String[]{"checkIn","checkIn","checkIn","checkOut","checkOut","checkOut","getAverageTime","getAverageTime","checkIn","getAverageTime","checkOut","getAverageTime"};
        String a = "45,a,3],[32,aa,8],[27,a,10],[45,ab,15],[27,ab,20],[32,b,22],[aa,b],[a,ab],[10,a,24],[a,ab],[10,ab,38],[a,ab";
        String[] split = a.split("],\\[");
        for (int i = 0; i < s.length; i++) {
            String s1 = s[i];
            if(s1.equals("checkIn")){
                String s2 = split[i];
                String[] split1 = s2.split(",");
                undergroundSystem.checkIn(Integer.valueOf(split1[0]),split1[1],Integer.valueOf(split1[2]));
            }else if(s1.equals("checkOut")){
                String s2 = split[i];
                String[] split1 = s2.split(",");
                undergroundSystem.checkOut(Integer.valueOf(split1[0]),split1[1],Integer.valueOf(split1[2]));
            }else{
                String s2 = split[i];
                String[] split1 = s2.split(",");
                double averageTime = undergroundSystem.getAverageTime(split1[0], split1[1]);
                System.out.println(averageTime);

            }
        }
    }
}
