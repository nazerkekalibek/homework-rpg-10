package com.narxoz.rpg.guild;

public class Quartermaster extends GuildMember {

    public Quartermaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void requestSupplies(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch(topic){
            case "orders":
                System.out.println("[Quartermaster "+getName()+"] received order from "+from.getName()+" -> preparing supply crates for: "+payload);
                break;
            case "scouting":
                System.out.println("[Quartermaster "+getName()+"] noted scouting report from "+from.getName()+" -> adjusting gear list for: "+payload);
                break;
            case "supplies":
                System.out.println("[Quartermaster "+getName()+"] cross-checking supply note from "+from.getName()+" -> "+payload);
                break;
            default:
                System.out.println("[Quartermaster "+getName()+"] heard '"+topic+"' from "+from.getName()+" -> "+payload);
        }
    }
}
