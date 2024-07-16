package com.editor.npc;

import com.alex.loaders.npcs.NPCDefinitions;
import com.alex.store.Store;
import com.alex.utils.Utils;
import com.editor.Main;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class NPCSelection extends JFrame {
    private static final long serialVersionUID = -3068337441331467664L;
    public static Store STORE;
    private DefaultListModel npcsListmodel;
    private JList npcsList;

    public NPCSelection(String cache) throws IOException {
        STORE = new Store(cache);
        this.setTitle("NPC Selection");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.initComponents();
    }

    public NPCSelection() {
        this.initComponents();
    }

    private void initComponents() {
        JButton editButton = new JButton();
        JButton addButton = new JButton();
        JButton duplicateButton = new JButton();
        JButton deleteButton = new JButton();
        JMenuBar jMenuBar1 = new JMenuBar();
        JMenu jMenu1 = new JMenu();
        JMenuItem exitButton = new JMenuItem();
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.npcsListmodel = new DefaultListModel();
        this.npcsList = new JList(this.npcsListmodel);
        this.npcsList.setSelectionMode(1);
        this.npcsList.setLayoutOrientation(0);
        this.npcsList.setVisibleRowCount(-1);
        JScrollPane jScrollPane1 = new JScrollPane(this.npcsList);
        editButton.setText("Edit");
        editButton.addActionListener(e -> {
            NPCDefinitions defs = (NPCDefinitions) NPCSelection.this.npcsList.getSelectedValue();
            if (defs != null) {
                new NPCEditor(NPCSelection.this, defs);
            }

        });
        addButton.setText("Add New");
        addButton.addActionListener(e -> {
            NPCDefinitions defs = new NPCDefinitions(NPCSelection.STORE, NPCSelection.this.getNewNPCID(), false);
            if (defs != null && defs.id != -1) {
                new NPCEditor(NPCSelection.this, defs);
            }

        });
        duplicateButton.setText("Duplicate");
        duplicateButton.addActionListener(e -> {
            NPCDefinitions defs = (NPCDefinitions) NPCSelection.this.npcsList.getSelectedValue();
            if (defs != null) {
                defs = (NPCDefinitions) defs.clone();
                if (defs != null) {
                    defs.id = NPCSelection.this.getNewNPCID();
                    if (defs.id != -1) {
                        new NPCEditor(NPCSelection.this, defs);
                    }
                }
            }

        });
        deleteButton.setText("Delete");
        deleteButton.addActionListener(e -> {
            NPCDefinitions defs = (NPCDefinitions) NPCSelection.this.npcsList.getSelectedValue();
            JFrame frame = new JFrame();
            int result = JOptionPane.showConfirmDialog(frame, "Do you really want to delete item " + defs.id);
            if (result == 0) {
                if (defs == null) {
                    return;
                }

                NPCSelection.STORE.getIndexes()[19].removeFile(defs.getArchiveId(), defs.getFileId());
                NPCSelection.this.removeNPCDefs(defs);
                Main.log("ItemSelection", "Item " + defs.id + " removed.");
            }

        });
        jMenu1.setText("File");
        exitButton.setText("Close");
        exitButton.addActionListener(evt -> NPCSelection.this.exitButtonActionPerformed(evt));
        jMenu1.add(exitButton);
        jMenuBar1.add(jMenu1);
        this.setJMenuBar(jMenuBar1);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING, false).addComponent(jScrollPane1, -2, 200, -2).addGroup(layout.createSequentialGroup().addComponent(editButton).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(addButton))).addGap(0, 0, 32767)).addGroup(layout.createSequentialGroup().addComponent(duplicateButton).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(deleteButton))).addContainerGap(-1, 32767)));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1, -2, 279, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(editButton).addComponent(addButton)).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(duplicateButton).addComponent(deleteButton)).addContainerGap(-1, 32767)));
        this.pack();
        this.addAllNPCs();
    }

    public static void main(String[] args) throws IOException {
        STORE = new Store("cache/", false);
        EventQueue.invokeLater(() -> (new NPCSelection()).setVisible(true));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    public int getNewNPCID() {
        try {
            JFrame var3 = new JFrame();
            String parent1 = JOptionPane.showInputDialog(var3, "Enter new NPC ID:");
            return Integer.parseInt(parent1);
        } catch (Exception var31) {
            JFrame parent = new JFrame();
            JOptionPane.showMessageDialog(parent, "Please enter a valid integer!");
            Main.log("NPCSelection", "Non-valid character entered for new NPC ID");
            return -1;
        }
    }

    public void addAllNPCs() {
        for (int id = 0; id < Utils.getNPCDefinitionsSize(STORE)/* - 15615*/; ++id) {
            this.addNPCDefs(NPCDefinitions.getNPCDefinition(STORE, id));
        }

        Main.log("NPCSelection", "All NPCs Loaded");
    }

    public void addNPCDefs(final NPCDefinitions defs) {
        EventQueue.invokeLater(() -> NPCSelection.this.npcsListmodel.addElement(defs));
    }

    public void updateNPCDefs(final NPCDefinitions defs) {
        EventQueue.invokeLater(() -> {
            int index = NPCSelection.this.npcsListmodel.indexOf(defs);
            if (index == -1) {
                NPCSelection.this.npcsListmodel.addElement(defs);
            } else {
                NPCSelection.this.npcsListmodel.setElementAt(defs, index);
            }

        });
    }

    public void removeNPCDefs(final NPCDefinitions defs) {
        EventQueue.invokeLater(() -> NPCSelection.this.npcsListmodel.removeElement(defs));
    }
}
