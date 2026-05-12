package com.narxoz.rpg.quest;

import java.util.List;

public class OrderedQuestIterator implements QuestIterator {

    private final List<Quest> snapshot;
    private int cursor;

    public OrderedQuestIterator(QuestLog questLog) {
        this.snapshot = questLog.snapshot();
        this.cursor = 0;
    }

    @Override
    public boolean hasNext() {
        return cursor<snapshot.size();
    }

    @Override
    public Quest next() {
        if(!hasNext()){
            return null;
        }
        Quest current=snapshot.get(cursor);
        cursor++;
        return current;
    }
}
