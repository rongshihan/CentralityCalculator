package Tool;

import java.util.*;

public class SortMap {
    /**
     * 对hashmap值排序
     * @param map
     * @return
     */
    public LinkedHashMap<String, Double> sortMap(HashMap<String,Double> map){
        ArrayList<Map.Entry<String,Double>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());//降序
            }
        });

        LinkedHashMap<String,Double> result = new LinkedHashMap<>();
        for (Map.Entry<String,Double> entry : list){
            result.put(entry.getKey(),entry.getValue());
        }
        return result;
    }
}
