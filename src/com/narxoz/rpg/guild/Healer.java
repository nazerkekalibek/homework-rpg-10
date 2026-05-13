package com.narxoz.rpg.guild;

public class Healer extends GuildMember {

    public Healer(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void prepareAid(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch(topic){
            case "orders":
                System.out.println("[Healer "+getName()+"] received order from "+from.getName()+" -> brewing potions for: "+payload);
                break;
            case "healing":
                System.out.println("[Healer "+getName()+"] coordinating healing with "+from.getName()+" -> "+payload);
                break;
            default:
                System.out.println("[Healer "+getName()+"] heard '"+topic+"' from "+from.getName()+" -> "+payload);
        }
    }
}
