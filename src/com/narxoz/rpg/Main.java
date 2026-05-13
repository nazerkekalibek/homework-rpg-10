package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.Captain;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.GuildMediator;
import com.narxoz.rpg.guild.Healer;
import com.narxoz.rpg.guild.Loremaster;
import com.narxoz.rpg.guild.Quartermaster;
import com.narxoz.rpg.guild.Scout;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===");

        List<Hero> party=new ArrayList<>();
        party.add(new Hero("Aldric the Brave", 120, 30, 18, 15, 50));
        party.add(new Hero("Lyra Moonwhisper", 90, 60, 22, 10, 40));
        party.add(new Hero("Borin Ironfoot", 150, 10, 25, 22, 30));

        System.out.println();
        System.out.println("-- Party assembled --");
        for(Hero hero : party){
            System.out.println("  "+hero);
        }

        QuestLog questLog=new QuestLog();
        questLog.add(new Quest("Clear goblin den near Eastwatch", QuestPriority.NORMAL, 120, false));
        questLog.add(new Quest("Escort merchant caravan to Highgate", QuestPriority.LOW, 80, false));
        questLog.add(new Quest("Slay the dragon of Ember Peak", QuestPriority.URGENT, 1000, true));
        questLog.add(new Quest("Investigate haunted ruins of Old Narxoz", QuestPriority.HIGH, 400, false));
        questLog.add(new Quest("Deliver healing herbs to the front line", QuestPriority.HIGH, 150, true));
        questLog.add(new Quest("Catalog the library's cursed tomes", QuestPriority.LOW, 60, false));

        System.out.println();
        System.out.println("-- Quest log loaded (" +questLog.size()+ " quests) --");

        GuildMediator hall=new GuildHall();
        Quartermaster qm=new Quartermaster("Hilda", hall);
        Scout scout=new Scout("Finn", hall);
        Healer healer=new Healer("Sera", hall);
        Captain captain=new Captain("Garrick", hall);

        System.out.println();
        System.out.println("-- Guild officers registered --");
        System.out.println("  Quartermaster: "+qm.getName());
        System.out.println("  Scout: "+scout.getName());
        System.out.println("  Healer: "+healer.getName());
        System.out.println("  Captain: "+captain.getName());

        // ==================
        // PART 1 DEMO:Iterator pattern in action
        // ==================
        System.out.println();
        System.out.println("===Iterator demo-same QuestLog, three orders===");

        System.out.println();
        System.out.println("[1] ordered()-arrival order:");
        QuestIterator it1=questLog.ordered();
        while(it1.hasNext()){
            Quest q=it1.next();
            System.out.println(" - "+q.getTitle()+" ["+q.getPriority()+"]");
        }

        System.out.println();
        System.out.println("[2] reverse()-newest to oldest:");
        QuestIterator it2=questLog.reverse();
        while (it2.hasNext()){
            Quest q=it2.next();
            System.out.println(" - "+q.getTitle()+" ["+q.getPriority()+"]");
        }

        System.out.println();
        System.out.println("[3] priorityAtLeast(HIGH)-only HIGH and URGENT:");
        QuestIterator it3=questLog.priorityAtLeast(QuestPriority.HIGH);
        while(it3.hasNext()) {
            Quest q=it3.next();
            System.out.println(" - "+q.getTitle()+" ["+q.getPriority()+"]");
        }

        // ===============
        // PART 2 DEMO:Mediator pattern-colleagues coordinate via hall
        // ===============
        System.out.println();
        System.out.println("===Mediator demo-colleagues coordinate by topic===");

        System.out.println();
        System.out.println("Captain issues an order:");
        captain.issueOrder("orders", "Prepare to march at dawn");

        System.out.println();
        System.out.println("Scout reports a route:");
        scout.reportRoute("scouting", "Pass through Crowwood is clear");

        System.out.println();
        System.out.println("Healer prepares aid:");
        healer.prepareAid("healing", "Stocked 20 healing potions");

        System.out.println();
        System.out.println("Quartermaster requests supplies:");
        qm.requestSupplies("supplies", "Need 50 arrows and trail rations");

        // ========
        // PART 3:CouncilEngine ties Iterator+Mediator together
        // ========
        CouncilEngine engine=new CouncilEngine();
        CouncilRunResult result=engine.runCouncil(party, questLog, hall);

        System.out.println("-- Final CouncilRunResult --");
        System.out.println("  "+result);

        // ==========
        // PART 4: Open/Closed proof
        // ==========
        System.out.println();
        System.out.println("===Part 4: Open/Closed Proof===");

        System.out.println();
        System.out.println("[4] rewardSorted()-new iterator class, no existing code changed:");
        QuestIterator it4=questLog.rewardSorted();
        while(it4.hasNext()){
            Quest q=it4.next();
            System.out.println(" - "+q.getTitle()+" (reward="+q.getRewardGold() + ")");
        }

        System.out.println();
        System.out.println("Loremaster joins the hall-existing colleagues unchanged:");
        Loremaster loremaster=new Loremaster("Old Tomas", hall);
        System.out.println("Loremaster registered: "+loremaster.getName());

        System.out.println();
        System.out.println("Captain issues an order about the cursed library:");
        captain.issueOrder("orders", "Investigate the cursed tomes carefully");

        System.out.println();
        System.out.println("Loremaster shares a cautionary tale:");
        loremaster.shareLore("curse", "The tomes whisper at midnight-bring silver seals");

        System.out.println();
        System.out.println("=== Demo finished ===");
    }
}
