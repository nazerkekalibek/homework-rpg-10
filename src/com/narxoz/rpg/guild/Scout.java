package com.narxoz.rpg.guild;

public class Scout extends GuildMember {

    public Scout(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void reportRoute(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch(topic){
            case "orders":
                System.out.println("[Scout "+getName() +"] received order from "+from.getName()+" -> mapping route for: "+payload);
                break;
            case "scouting":
                System.out.println("[Scout "+getName()+"] comparing notes with "+from.getName()+" -> "+payload);
                break;
            default:
                System.out.println("[Scout "+getName()+"] heard '"+topic+"' from "+from.getName()+" -> "+payload);
        }
    }
}
