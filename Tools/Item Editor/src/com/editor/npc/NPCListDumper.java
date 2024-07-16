package com.editor.npc;

import com.alex.loaders.npcs.NPCDefinitions;
import com.alex.store.Store;
import com.alex.utils.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NPCListDumper {
    private static Store STORE;

    public static void main(String[] args) throws IOException {
        STORE = new Store("C:/Users/szumaster/Documents/GitLab/2009scape/Server/data/cache");
        File file = new File("C:/Users/szumaster/Documents/GitLab/2009scape/Server/data/cache/NPCs.txt");
        if (file.exists()) {
            file.delete();
        } else {
            file.createNewFile();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.append("//\n");
        writer.flush();

        for (int id = 0; id < Utils.getNPCDefinitionsSize(STORE) - 18433; ++id) {
            NPCDefinitions def = NPCDefinitions.getNPCDefinition(STORE, id);
            writer.append(id + " - " + def.name);
            writer.newLine();
            System.out.println(id + " - " + def.name);
            writer.flush();
        }

        writer.close();
    }
}
