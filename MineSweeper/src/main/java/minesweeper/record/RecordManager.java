package minesweeper.record;

import minesweeper.model.GameDifficult;

import java.io.Serializable;
import java.util.*;

public class RecordManager implements Serializable {
   private static final int MAX_RECORDS_SIZE = 10;
   private final Map<GameDifficult, List<Record>> recordMap;

    public RecordManager() {
        recordMap = new EnumMap<>(GameDifficult.class);
        for (GameDifficult i : GameDifficult.values()){
            recordMap.put(i, new ArrayList<>());
        }
    }

    public void addRecord(GameDifficult difficult, Record record){
        List<Record> list = recordMap.get(difficult);
        list.add(record);
        list.sort(Comparator.comparingInt(Record::getTime));

        if(list.size() > MAX_RECORDS_SIZE){
            list.remove(list.size() - 1);
        }
    }

    public List<Record> getRecords(String dif){
        GameDifficult difficult = GameDifficult.valueOf(dif);
        return new ArrayList<>(recordMap.get(difficult));
    }

    public void clearAllRecords(){
        for(List<Record> i : recordMap.values()){
            i.clear();
        }
    }
}
