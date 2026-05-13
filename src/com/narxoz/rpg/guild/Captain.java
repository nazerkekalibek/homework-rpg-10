package com.narxoz.rpg.guild;

public class Captain extends GuildMember {

    public Captain(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void issueOrder(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch(topic){
            case "supplies":
                System.out.println("[Captain "+getName()+"] acknowledged supply note from "+from.getName()+ " -> " + payload);
                break;
            case "scouting":
                System.out.println("[Captain "+getName()+"] integrating scout report from "+from.getName()+" into the plan -> "+payload);
                break;
            case "healing":
                System.out.println("[Captain "+getName()+ "] noted medical plan from " + from.getName()+ " -> " + payload);
                break;
            default:
                System.out.println("[Captain "+getName()+"] heard '"+topic+"' from "+from.getName()+" -> "+payload);
        }
    }
}
