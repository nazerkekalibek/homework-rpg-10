package com.narxoz.rpg.guild;

public class Loremaster extends GuildMember{
    public Loremaster(String name, GuildMediator mediator){
        super(name, mediator);
    }

    public void shareLore(String topic, String payload){
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload){
        switch(topic){
            case "lore":
                System.out.println("[Loremaster "+getName()+"] cross-references lore from "+from.getName()+" -> "+payload);
                break;
            case "curse":
                System.out.println("[Loremaster " + getName()+ "] warns about curse mentioned by " + from.getName()+ " -> " + payload);
                break;
            case "history":
                System.out.println("[Loremaster "+getName()+"] recalls history relevant to " + from.getName()+ " -> " + payload);
                break;
            case "orders":
                System.out.println("[Loremaster "+getName()+"] adds historical context for " + from.getName()+ "'s order -> " + payload);
                break;
            default:
                System.out.println("[Loremaster " + getName()+ "] heard '" + topic + "' from " + from.getName()+ " -> " + payload);
        }
    }

}
