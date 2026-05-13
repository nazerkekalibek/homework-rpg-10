package com.narxoz.rpg.guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuildHall implements GuildMediator {

    private final Map<String, List<GuildMember>> membersByTopic = new HashMap<>();

    @Override
    public void register(GuildMember member) {
        if(member instanceof Quartermaster){
            addSubscriber("supplies", member);
            addSubscriber("orders", member);
            addSubscriber("scouting", member);
        } else if (member instanceof Scout){
            addSubscriber("scouting", member);
            addSubscriber("orders", member);
        } else if (member instanceof Healer){
            addSubscriber("healing", member);
            addSubscriber("orders", member);
        } else if (member instanceof Captain){
            addSubscriber("orders", member);
            addSubscriber("supplies", member);
            addSubscriber("scouting", member);
            addSubscriber("healing", member);
        } else if (member instanceof Loremaster){
            addSubscriber("lore", member);
            addSubscriber("curse", member);
            addSubscriber("history", member);
            addSubscriber("orders", member);
        } else {
            addSubscriber("announcements", member);
        }
    }

    @Override
    public void dispatch(String topic, GuildMember from, String payload) {
        System.out.println("[GuildHall] '"+topic+"' from "+from.getName()+" -> "+payload);

        List<GuildMember> subscribers=subscribersFor(topic);
        for(GuildMember member : subscribers){
            if(member==from){
                continue;
            }
            member.receive(topic, from, payload);
        }
    }

    protected void addSubscriber(String topic, GuildMember member) {
        membersByTopic.computeIfAbsent(topic, key -> new ArrayList<>()).add(member);
    }

    protected List<GuildMember> subscribersFor(String topic) {
        return membersByTopic.getOrDefault(topic, List.of());
    }
}
