package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.GuildMediator;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;
import java.util.List;

public class CouncilEngine{
    private static class CountingMediator implements GuildMediator{
        private final GuildMediator delegate;
        int dispatches=0;

        CountingMediator(GuildMediator delegate){
            this.delegate=delegate;
        }

        @Override
        public void register(com.narxoz.rpg.guild.GuildMember member){
            delegate.register(member);
        }

        @Override
        public void dispatch(String topic,com.narxoz.rpg.guild.GuildMember from,String payload){
            dispatches++;
            delegate.dispatch(topic, from, payload);
        }
    }

    public CouncilRunResult runCouncil(List<Hero> party, QuestLog questLog, GuildMediator hall){
        System.out.println();
        System.out.println("--- CouncilEngine: planning session begins ---");

        for(Hero hero : party){
            System.out.println("Hero present: "+hero.getName()+" (HP="+hero.getHp()+", ATK="+hero.getAttackPower()+", DEF="+hero.getDefense()+")");
        }

        int questsTraversed=0;
        int messagesRouted=0;

        System.out.println();
        System.out.println("  >> Pass 1: ordered traversal");
        QuestIterator ordered=questLog.ordered();
        while(ordered.hasNext()){
            Quest quest=ordered.next();
            questsTraversed++;
            System.out.println("[Iterator/ordered] "+quest.getTitle()+" ("+quest.getPriority()+", reward="+quest.getRewardGold()+")");

            hall.dispatch("orders",new SystemSpeaker("CouncilEngine", hall),"Plan logistics for: "+quest.getTitle());
            messagesRouted++;
        }

        System.out.println();
        System.out.println("  >> Pass 2: priorityAtLeast(HIGH) traversal");
        QuestIterator highPriority=questLog.priorityAtLeast(QuestPriority.HIGH);
        while(highPriority.hasNext()){
            Quest quest=highPriority.next();
            questsTraversed++;
            System.out.println("[Iterator/priority>=HIGH] "+quest.getTitle());

            hall.dispatch("scouting",new SystemSpeaker("CouncilEngine", hall),"Need scouting before tackling: "+quest.getTitle());
            messagesRouted++;
        }

        int membersNotified=messagesRouted;

        System.out.println("--- CouncilEngine: planning session ends ---");
        System.out.println();

        return new CouncilRunResult(questsTraversed, messagesRouted, membersNotified);
    }

    private static class SystemSpeaker extends com.narxoz.rpg.guild.GuildMember{
        SystemSpeaker(String name, GuildMediator mediator){
            super(name, mediator);
        }

        @Override
        public void receive(String topic,com.narxoz.rpg.guild.GuildMember from,String payload){
        }
    }
}
