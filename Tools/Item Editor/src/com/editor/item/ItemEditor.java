package com.editor.item;

import com.alex.loaders.items.ItemDefinitions;
import com.editor.Main;
import com.editor.Utils;
import com.editor.util.SpringUtilities;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;

@SuppressWarnings("ALL")
public class ItemEditor extends JFrame {
    private static final long serialVersionUID = -3870086097297420720L;
    private final ItemDefinitions defs;
    private final ItemSelection is;
    private JTextField xOffset2dField;
    private JTextField yan2dField;
    private JTextField array1;
    private JTextField array2;
    private JTextField array3;
    private JTextField array4;
    private JTextField array5;
    private JTextField array6;
    private JTextArea clientScriptOutput;
    private JTextField csk1;
    private JTextField csk2;
    private JTextField csk3;
    private JTextField csk4;
    private JTextField csk5;
    private JTextField csk6;
    private JTextField csk7;
    private JTextField csv1;
    private JTextField csv2;
    private JTextField csv3;
    private JTextField csv4;
    private JTextField csv5;
    private JTextField csv6;
    private JTextField csv7;
    private JTextField equipSlot;
    private JTextField equipType;
    private JTextField femaleEquip1Field;
    private JTextField femaleEquip2Field;
    private JTextField femaleEquip3Field;
    private JTextField groundOptions;
    private JTextField maleDialogueHeadPrimaryField;
    private JTextField ambienceTextField;
    private JTextField diffusionTextField;
    private JTextField mWieldXTextField;
    private JTextField mWieldYTextField;
    private JTextField mWieldZTextField;
    private JTextField fWieldXTextField;
    private JTextField fWieldYTextField;
    private JTextField fWieldZTextField;
    private JTextField int18;
    private JTextField int19;
    private JTextField femaleDialogueHeadPrimaryField;
    private JTextField int20;
    private JTextField int21;
    private JTextField int22;
    private JTextField int23;
    private JTextField maleDialogueHeadAccessoryField;
    private JTextField femaleDialogueHeadAccessoryField;
    private JTextField zan2dField;
    private JTextField int6;
    private JTextField fScaleXTextField;
    private JTextField fScaleYTextField;
    private JTextField fScaleZTextField;
    private JTextField inventoryModelField;
    private JTextField Zoom2dField;
    private JTextField invOptions;
    private JTextField itemName;
    private JTextField lend;
    private JTextField maleEquip1Field;
    private JTextField maleEquip2Field;
    private JTextField maleEquip3Field;
    private JCheckBox membersOnly;
    private JTextField modelColors;
    private JTextField yOffset2dField;
    private JTextField xan2dField;
    private JTextField note;
    private JTextField stackAmts;
    private JTextField stackIDs;
    private JCheckBox stackable;
    public JTextField switchLend;
    public JTextField switchNote;
    public JTextField teamId;
    public JTextField textureColors;
    public JCheckBox unnoted;
    public JTextField value;

    public ItemEditor(ItemSelection is, ItemDefinitions defs) {
        this.defs = defs;
        this.is = is;
        this.setIconImage(Main.iconImage);
        this.initComponents();
        this.setResizable(false);
        this.setTitle("Item Editor");
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initComponents() {
        JTabbedPane jTabbedPane1 = new JTabbedPane();
        JPanel jPanel1 = new JPanel();
        JLabel jLabel1 = new JLabel();
        this.itemName = new JTextField();
        JLabel jLabel2 = new JLabel();
        this.value = new JTextField();
        this.teamId = new JTextField();
        JLabel jLabel3 = new JLabel();
        this.membersOnly = new JCheckBox();
        JLabel jLabel20 = new JLabel();
        this.equipSlot = new JTextField();
        JLabel jLabel21 = new JLabel();
        this.equipType = new JTextField();
        JLabel jLabel22 = new JLabel();
        this.stackIDs = new JTextField();
        JLabel jLabel23 = new JLabel();
        this.stackAmts = new JTextField();
        JLabel jLabel24 = new JLabel();
        this.stackable = new JCheckBox();
        JLabel jLabel58 = new JLabel();
        this.switchNote = new JTextField();
        JLabel jLabel59 = new JLabel();
        this.note = new JTextField();
        this.unnoted = new JCheckBox();
        JLabel jLabel60 = new JLabel();
        JLabel jLabel61 = new JLabel();
        this.switchLend = new JTextField();
        this.lend = new JTextField();
        JPanel jPanel2 = new JPanel();
        JPanel helperPanel2 = new JPanel();
        JPanel helperPanel3 = new JPanel();
        JPanel jPanel8 = new JPanel();
        JPanel helperPanel8 = new JPanel();
        JPanel helperPanel9 = new JPanel();
        JPanel helperPanel10 = new JPanel();
        JPanel helperPanel11 = new JPanel();
        JLabel zoom2dLabel = new JLabel("Zoom2d", JLabel.TRAILING);
        this.Zoom2dField = new JTextField(10);
        JLabel xan2dLabel = new JLabel("xAngle2d", JLabel.TRAILING);
        this.xan2dField = new JTextField(10);
        JLabel yan2dLabel = new JLabel("yAngle2d", JLabel.TRAILING);
        this.yan2dField = new JTextField(10);
        JLabel zan2dLabel = new JLabel("zAngle2d", JLabel.TRAILING);
        this.zan2dField = new JTextField(10);
        JLabel xOffset2dLabel = new JLabel("xOffset2d", JLabel.TRAILING);
        this.xOffset2dField = new JTextField(10);
        JLabel yOffset2dLabel = new JLabel("yOffset2d", JLabel.TRAILING);
        this.yOffset2dField = new JTextField(10);
        JLabel inventoryModelLabel = new JLabel();
        this.inventoryModelField = new JTextField();
        JLabel maleDialogueHeadPrimaryLabel = new JLabel("Male Dialogue Head", JLabel.TRAILING);
        this.maleDialogueHeadPrimaryField = new JTextField(10);
        JLabel maleDialogueHeadAccessoryLabel = new JLabel("Male Dialogue Head Accessory", JLabel.TRAILING);
        this.maleDialogueHeadAccessoryField = new JTextField(10);
        JLabel maleEquip1Label = new JLabel("Male Equip Primary", JLabel.TRAILING);
        this.maleEquip1Field = new JTextField(10);
        JLabel maleEquip2Label = new JLabel("Male Equip Secondary", JLabel.TRAILING);
        this.maleEquip2Field = new JTextField(10);
        JLabel maleEquip3Label = new JLabel("Male Equip Tertiary", JLabel.TRAILING);
        this.maleEquip3Field = new JTextField(10);
        JLabel femaleDialogueHeadPrimaryLabel = new JLabel("Female Dialogue Head", JLabel.TRAILING);
        this.femaleDialogueHeadPrimaryField = new JTextField(10);
        JLabel femaleDialogueHeadAccessoryLabel = new JLabel("Female Dialogue Head Accessory", JLabel.TRAILING);
        this.femaleDialogueHeadAccessoryField = new JTextField(10);
        JLabel femaleEquip1Label = new JLabel("Female Equip Primary", JLabel.TRAILING);
        this.femaleEquip1Field = new JTextField(10);
        JLabel femaleEquip2Label = new JLabel("Female Equip Secondary", JLabel.TRAILING);
        this.femaleEquip2Field = new JTextField(10);
        JLabel femaleEquip3Label = new JLabel("Female Equip Tertiary", JLabel.TRAILING);
        this.femaleEquip3Field = new JTextField(10);
        JPanel jPanel3 = new JPanel();
        JLabel jLabel16 = new JLabel();
        this.invOptions = new JTextField();
        JLabel jLabel17 = new JLabel();
        this.groundOptions = new JTextField();
        JLabel jLabel18 = new JLabel();
        this.modelColors = new JTextField();
        JLabel jLabel19 = new JLabel();
        this.textureColors = new JTextField();
        JPanel jPanel5 = new JPanel();
        JLabel jLabel25 = new JLabel();
        this.array1 = new JTextField();
        JLabel jLabel27 = new JLabel();
        JLabel jLabel28 = new JLabel();
        JLabel jLabel29 = new JLabel();
        JLabel jLabel30 = new JLabel();
        JLabel jLabel31 = new JLabel();
        this.array2 = new JTextField();
        this.array3 = new JTextField();
        this.array4 = new JTextField();
        this.array5 = new JTextField();
        this.array6 = new JTextField();
        JLabel jLabel37 = new JLabel();
        this.int6 = new JTextField();
        JLabel jLabel38 = new JLabel();
        this.fScaleXTextField = new JTextField();
        JLabel jLabel39 = new JLabel();
        this.fScaleYTextField = new JTextField();
        JLabel jLabel40 = new JLabel();
        this.fScaleZTextField = new JTextField();
        JLabel jLabel41 = new JLabel();
        this.ambienceTextField = new JTextField();
        JLabel jLabel42 = new JLabel();
        this.diffusionTextField = new JTextField();
        JLabel maleWieldXLabel = new JLabel();
        this.mWieldXTextField = new JTextField();
        JLabel maleWieldYLabel = new JLabel();
        this.mWieldYTextField = new JTextField();
        JLabel maleWieldZLabel = new JLabel();
        this.mWieldZTextField = new JTextField();
        JPanel jPanel6 = new JPanel();
        JLabel fWieldXLabel = new JLabel();
        this.fWieldXTextField = new JTextField();
        JLabel fWieldYLabel = new JLabel();
        this.fWieldYTextField = new JTextField();
        JLabel fWieldZLabel = new JLabel();
        this.fWieldZTextField = new JTextField();
        JLabel jLabel49 = new JLabel();
        this.int18 = new JTextField();
        JLabel jLabel50 = new JLabel();
        this.int19 = new JTextField();
        JLabel jLabel51 = new JLabel();
        this.int20 = new JTextField();
        JLabel jLabel52 = new JLabel();
        this.int21 = new JTextField();
        JLabel jLabel53 = new JLabel();
        this.int22 = new JTextField();
        JLabel jLabel54 = new JLabel();
        this.int23 = new JTextField();
        JPanel jPanel7 = new JPanel();
        JScrollPane jScrollPane1 = new JScrollPane();
        this.clientScriptOutput = new JTextArea();
        JLabel jLabel26 = new JLabel();
        JLabel jLabel55 = new JLabel();
        JLabel jLabel56 = new JLabel();
        JLabel jLabel57 = new JLabel();
        this.csk1 = new JTextField();
        this.csk2 = new JTextField();
        this.csk3 = new JTextField();
        this.csk4 = new JTextField();
        this.csk5 = new JTextField();
        this.csk6 = new JTextField();
        this.csk7 = new JTextField();
        this.csv1 = new JTextField();
        this.csv2 = new JTextField();
        this.csv3 = new JTextField();
        this.csv4 = new JTextField();
        this.csv5 = new JTextField();
        this.csv6 = new JTextField();
        this.csv7 = new JTextField();
        JLabel currentViewLabel = new JLabel();
        JMenuBar jMenuBar1 = new JMenuBar();
        JMenu jMenu1 = new JMenu();
        JMenuItem reloadMenuBtn = new JMenuItem();
        JMenuItem saveMenuBtn = new JMenuItem();
        JMenuItem addModelMenuBtn = new JMenuItem();
        JMenuItem exportMenuBtn = new JMenuItem();
        JMenuItem exitMenuBtn = new JMenuItem();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jLabel1.setText("Name");
        this.itemName.setText(this.defs.getName());
        jLabel2.setText("Value");
        this.value.setText("" + this.defs.getCost());
        this.teamId.setText("" + this.defs.getTeamId());
        jLabel3.setText("Team");
        this.membersOnly.setSelected(this.defs.isMembersOnly());
        this.membersOnly.setText("Members Only");
        jLabel20.setText("Equip Slot");
        this.equipSlot.setText("" + this.defs.getEquipSlot());
        jLabel21.setText("Equip Type");
        this.equipType.setText("" + this.defs.getEquipType());
        jLabel22.setText("Stack IDs");
        this.stackIDs.setText(this.getStackIDs());
        jLabel23.setText("Stack Amounts");
        this.stackAmts.setText(this.getStackAmts());
        jLabel24.setText("Stackable");
        this.stackable.setSelected(this.defs.getStackable() == 1);
        jLabel58.setText("Switch Note Item ID");
        this.switchNote.setText("" + this.defs.switchNoteItemId);
        jLabel59.setText("Noted Item ID");
        this.note.setText("" + this.defs.notedItemId);
        this.unnoted.setSelected(this.defs.isUnnoted());
        this.unnoted.setText("Unnoted");
        jLabel60.setText("Switch Lend Item ID");
        jLabel61.setText("Lent Item ID");
        this.switchLend.setText("" + this.defs.getSwitchLendItemId());
        this.lend.setText("" + this.defs.getLendedItemId());
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.unnoted).addComponent(this.membersOnly).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jLabel20).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.equipSlot, -2, 100, -2)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jLabel1).addGap(18, 18, 18).addComponent(this.itemName, -2, 100, -2)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(jLabel2).addComponent(jLabel3)).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.teamId, -2, 100, -2).addComponent(this.value, -2, 100, -2))).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jLabel24).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.stackable, -2, 100, -2))).addGap(126, 126, 126).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.stackAmts, -2, 300, -2).addComponent(this.stackIDs, -2, 300, -2).addComponent(jLabel22).addComponent(jLabel23))).addGroup(jPanel1Layout.createSequentialGroup().addComponent(jLabel21).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.equipType, -2, 100, -2)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(jLabel58).addComponent(jLabel59)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false).addComponent(this.switchNote, -1, 100, 32767).addComponent(this.note)).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(jLabel60).addComponent(jLabel61)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false).addComponent(this.switchLend, -1, 100, 32767).addComponent(this.lend)))).addContainerGap(36, 32767)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel1).addComponent(this.itemName, -2, -1, -2).addComponent(jLabel22)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.value, -2, -1, -2).addComponent(jLabel2)).addComponent(this.stackIDs, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.teamId, -2, -1, -2).addComponent(jLabel3).addComponent(jLabel23)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(this.stackAmts, -2, -1, -2).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel24).addComponent(this.stackable, -2, -1, -2))).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.membersOnly).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel20).addComponent(this.equipSlot, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel21).addComponent(this.equipType, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel58).addComponent(this.switchNote, -2, -1, -2).addComponent(jLabel60).addComponent(this.switchLend, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel59).addComponent(this.note, -2, -1, -2).addComponent(jLabel61).addComponent(this.lend, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.unnoted).addContainerGap(13, 32767)));
        jTabbedPane1.addTab("General", jPanel1);
        zoom2dLabel.setText("Zoom2d");
        this.Zoom2dField.setText("" + this.defs.getInvModelZoom());
        xan2dLabel.setText("xAngle2d");
        this.xan2dField.setText("" + this.defs.getXan2d());
        yan2dLabel.setText("yAngle2d");
        this.yan2dField.setText("" + this.defs.getYan2d());
        zan2dLabel.setText("zAngle2d");
        this.zan2dField.setText("" + this.defs.Zan2d);
        xOffset2dLabel.setText("xOffset2d");
        this.xOffset2dField.setText("" + this.defs.getxOffset2d());
        yOffset2dLabel.setText("yOffset2d");
        this.yOffset2dField.setText("" + this.defs.getyOffset2d());
        inventoryModelLabel.setText("Inventory Model");
        this.inventoryModelField.setText("" + this.defs.getInvModelId());
        maleDialogueHeadPrimaryLabel.setText("Male Dialogue Head");
        this.maleDialogueHeadPrimaryField.setText("" + this.defs.primaryMaleDialogueHead);
        maleDialogueHeadAccessoryLabel.setText("Male Dialogue Head Accessory");
        this.maleDialogueHeadAccessoryField.setText("" + this.defs.secondaryMaleDialogueHead);
        maleEquip1Label.setText("Male Equip 1");
        this.maleEquip1Field.setText("" + this.defs.getMaleEquipModelId1());
        maleEquip2Label.setText("Male Equip 2");
        this.maleEquip2Field.setText("" + this.defs.getMaleEquipModelId2());
        maleEquip3Label.setText("Male Equip 3");
        this.maleEquip3Field.setText("" + this.defs.getMaleEquipModelId3());
        femaleDialogueHeadPrimaryLabel.setText("Female Dialogue Head");
        this.femaleDialogueHeadPrimaryField.setText("" + this.defs.primaryFemaleDialogueHead);
        femaleDialogueHeadAccessoryLabel.setText("Female Dialogue Head Accessory");
        this.femaleDialogueHeadAccessoryField.setText("" + this.defs.secondaryFemaleDialogueHead);
        femaleEquip1Label.setText("Female Equip 1");
        this.femaleEquip1Field.setText("" + this.defs.getFemaleEquipModelId1());
        femaleEquip2Label.setText("Female Equip 2");
        this.femaleEquip2Field.setText("" + this.defs.getFemaleEquipModelId2());
        femaleEquip3Label.setText("Female Equip 3");
        this.femaleEquip3Field.setText("" + this.defs.getFemaleEquipModelId3());


        jPanel2.setLayout(new BorderLayout());
        jPanel2.setPreferredSize(new Dimension(300, 300));
        helperPanel3.setPreferredSize(new Dimension(300, 300));
        SpringLayout experimentLayout = new SpringLayout();
        helperPanel2.setPreferredSize(new Dimension(300, 300));
        helperPanel2.setLayout(experimentLayout);
        helperPanel2.add(zoom2dLabel);
        zoom2dLabel.setLabelFor(this.Zoom2dField);
        helperPanel2.add(this.Zoom2dField);//

        helperPanel2.add(xan2dLabel);
        xan2dLabel.setLabelFor(this.xan2dField);
        helperPanel2.add(this.xan2dField);//

        helperPanel2.add(yan2dLabel);
        yan2dLabel.setLabelFor(this.yan2dField);
        helperPanel2.add(this.yan2dField);//

        helperPanel2.add(zan2dLabel);
        zan2dLabel.setLabelFor(this.zan2dField);
        helperPanel2.add(this.zan2dField);//

        helperPanel2.add(xOffset2dLabel);
        xOffset2dLabel.setLabelFor(this.xOffset2dField);
        helperPanel2.add(this.xOffset2dField);//

        helperPanel2.add(yOffset2dLabel);
        yOffset2dLabel.setLabelFor(this.yOffset2dField);
        helperPanel2.add(this.yOffset2dField);//

        SpringUtilities.makeCompactGrid(helperPanel2,
                6, 2, //rows, cols
                12, 12,        //initX, initY
                24, 24);       //xPad, yPad

        helperPanel2.setOpaque(true);
        jPanel2.add(helperPanel3, BorderLayout.CENTER);
        jPanel2.add(helperPanel2, BorderLayout.LINE_START);


        jPanel8.setLayout(new BorderLayout());
        //this.jPanel8.setPreferredSize(new Dimension(300, 300));

        // !-- THIS GETS ADDED TO PAGE_START --!
        helperPanel10.setPreferredSize(new Dimension(700, 30));
        helperPanel10.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 0;       //reset to default
        c.ipady = 10;       //reset to default
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 10;
        c.anchor = GridBagConstraints.LAST_LINE_START; //bottom of space
        c.insets = new Insets(15, 98, 30, 0);  //top padding
        c.gridx = 8;       //aligned with button 2
        c.gridy = 5;       //third row
        helperPanel10.add(inventoryModelLabel, c);
        inventoryModelLabel.setLabelFor(this.inventoryModelField);
        c.ipadx = 0;       //reset to default
        c.ipady = 10;       //reset to default
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.gridheight = 30;
        c.gridwidth = 30;
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(15, 210, 30, 388);  //top padding
        c.gridx = 8;       //aligned with button 2
        c.gridy = 5;       //third row
        helperPanel10.add(this.inventoryModelField, c);


        // !-- THIS GETS ADDED TO CENTER --!
        // MALE MODEL EDITS
        SpringLayout experiment2Layout = new SpringLayout();
        //this.helperPanel8.setPreferredSize(new Dimension(300, 300));
        helperPanel8.setLayout(experiment2Layout);

        helperPanel8.add(maleDialogueHeadPrimaryLabel);
        maleDialogueHeadPrimaryLabel.setLabelFor(this.maleDialogueHeadPrimaryField);
        helperPanel8.add(this.maleDialogueHeadPrimaryField);//

        helperPanel8.add(maleDialogueHeadAccessoryLabel);
        maleDialogueHeadAccessoryLabel.setLabelFor(this.maleDialogueHeadAccessoryField);
        helperPanel8.add(this.maleDialogueHeadAccessoryField);//

        helperPanel8.add(maleEquip1Label);
        maleEquip1Label.setLabelFor(this.maleEquip1Field);
        helperPanel8.add(this.maleEquip1Field);//

        helperPanel8.add(maleEquip2Label);
        maleEquip2Label.setLabelFor(this.maleEquip2Field);
        helperPanel8.add(this.maleEquip2Field);//

        helperPanel8.add(maleEquip3Label);
        maleEquip3Label.setLabelFor(this.maleEquip3Field);
        helperPanel8.add(this.maleEquip3Field);//

        SpringUtilities.makeCompactGrid(helperPanel8, 5, 2, //rows, cols
                12, 12,        //initX, initY
                24, 24);       //xPad, yPad

        // !-- THIS GETS ADDED TO CENTER --!
        // FEMALE MODEL EDITS
        SpringLayout experiment3Layout = new SpringLayout();
        //this.helperPanel9.setPreferredSize(new Dimension(300, 300));
        helperPanel9.setLayout(experiment3Layout);

        helperPanel9.add(femaleDialogueHeadPrimaryLabel);
        femaleDialogueHeadPrimaryLabel.setLabelFor(this.femaleDialogueHeadPrimaryField);
        helperPanel9.add(this.femaleDialogueHeadPrimaryField);//

        helperPanel9.add(femaleDialogueHeadAccessoryLabel);
        femaleDialogueHeadAccessoryLabel.setLabelFor(this.femaleDialogueHeadAccessoryField);
        helperPanel9.add(this.femaleDialogueHeadAccessoryField);//

        helperPanel9.add(femaleEquip1Label);
        femaleEquip1Label.setLabelFor(this.femaleEquip1Field);
        helperPanel9.add(this.femaleEquip1Field);//

        helperPanel9.add(femaleEquip2Label);
        femaleEquip2Label.setLabelFor(this.femaleEquip2Field);
        helperPanel9.add(this.femaleEquip2Field);//

        helperPanel9.add(femaleEquip3Label);
        femaleEquip3Label.setLabelFor(this.femaleEquip3Field);
        helperPanel9.add(this.femaleEquip3Field);//

        SpringUtilities.makeCompactGrid(helperPanel9, 5, 2, //rows, cols
                12, 12,        //initX, initY
                24, 24);       //xPad, yPad


        helperPanel8.setOpaque(true);
        helperPanel9.setOpaque(true);

        helperPanel11.setLayout(new GridLayout(1, 0));
        helperPanel11.add(helperPanel8);
        helperPanel11.add(helperPanel9);
        jPanel8.add(helperPanel10, BorderLayout.PAGE_START);
        jPanel8.add(helperPanel11, BorderLayout.CENTER);
        jTabbedPane1.addTab("Item Sprite", jPanel2);
        jTabbedPane1.addTab("Models", jPanel8);
        jLabel16.setText("Inventory Options");
        this.invOptions.setText(this.getInventoryOpts());
        jLabel17.setText("Ground Options");
        this.groundOptions.setText(this.getGroundOpts());
        jLabel18.setText("Model Colors");
        this.modelColors.setText(this.getChangedModelColors());
        jLabel19.setText("Texture Colors");
        this.textureColors.setText(this.getChangedTextureColors());
        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addComponent(jLabel16).addComponent(this.invOptions, -2, 300, -2).addComponent(jLabel17).addComponent(this.groundOptions, -2, 300, -2).addComponent(jLabel18).addComponent(this.modelColors, -2, 300, -2).addComponent(jLabel19).addComponent(this.textureColors, -2, 300, -2)).addContainerGap(312, 32767)));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(jLabel16).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.invOptions, -2, -1, -2).addGap(18, 18, 18).addComponent(jLabel17).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.groundOptions, -2, -1, -2).addGap(18, 18, 18).addComponent(jLabel18).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.modelColors, -2, -1, -2).addGap(18, 18, 18).addComponent(jLabel19).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.textureColors, -2, -1, -2).addContainerGap(47, 32767)));
        jTabbedPane1.addTab("Options", jPanel3);
        jLabel25.setText("unknownArray1");
        this.array1.setText(this.getUnknownArray1());
        jLabel27.setText("unknownArray2");
        jLabel28.setText("unknownArray3");
        jLabel29.setText("unknownArray4");
        jLabel30.setText("unknownArray5");
        jLabel31.setText("unknownArray6");
        this.array2.setText(this.getUnknownArray2());
        this.array3.setText(this.getUnknownArray3());
        this.array4.setText(this.getUnknownArray4());
        this.array5.setText(this.getUnknownArray5());
        this.array6.setText(this.getUnknownArray6());

        jLabel37.setText("unknownInt6");
        this.int6.setText("" + this.defs.dummyItem);
        jLabel38.setText("Floor Scale X");
        this.fScaleXTextField.setText("" + this.defs.floorScaleX);
        jLabel39.setText("Floor Scale Y");
        this.fScaleYTextField.setText("" + this.defs.floorScaleY);
        jLabel40.setText("Floor Scale Z");
        this.fScaleZTextField.setText("" + this.defs.floorScaleZ);
        jLabel41.setText("Ambience");
        this.ambienceTextField.setText("" + this.defs.ambience);
        jLabel42.setText("Diffusion");
        this.diffusionTextField.setText("" + this.defs.diffusion);
        maleWieldXLabel.setText("Male Wield X");
        this.mWieldXTextField.setText("" + this.defs.maleWieldX);
        maleWieldYLabel.setText("Male Wield Y");
        this.mWieldYTextField.setText("" + this.defs.maleWieldY);
        maleWieldZLabel.setText("Male Wield Z");
        this.mWieldZTextField.setText("" + this.defs.maleWieldZ);
        fWieldXLabel.setText("Female Wield X");
        this.fWieldXTextField.setText("" + this.defs.femaleWieldX);
        fWieldYLabel.setText("Female Wield Y");
        this.fWieldYTextField.setText("" + this.defs.femaleWieldY);
        fWieldZLabel.setText("Female Wield Z");
        this.fWieldZTextField.setText("" + this.defs.femaleWieldZ);

        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addGroup(jPanel5Layout.createParallelGroup(Alignment.LEADING, false).addGroup(jPanel5Layout.createSequentialGroup().addGroup(jPanel5Layout.createParallelGroup(Alignment.LEADING).addComponent(fWieldZLabel)).addGap(18, 18, 18).addGroup(jPanel5Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addComponent(this.fWieldZTextField, -2, 200, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(jLabel41).addGap(18, 18, 18).addComponent(this.ambienceTextField, -2, 100, -2)).addGroup(jPanel5Layout.createSequentialGroup().addComponent(jLabel42).addGap(18, 18, 18).addComponent(this.diffusionTextField, -2, 100, -2)))).addGroup(jPanel5Layout.createSequentialGroup().addComponent(fWieldYLabel).addGap(18, 18, 18).addComponent(this.fWieldYTextField, -2, 200, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(jLabel40).addGap(18, 18, 18).addComponent(this.fScaleZTextField, -2, 100, -2)).addGroup(jPanel5Layout.createSequentialGroup().addComponent(fWieldXLabel).addGap(18, 18, 18).addComponent(this.fWieldXTextField, -2, 200, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(jLabel39).addGap(18, 18, 18).addComponent(this.fScaleYTextField, -2, 100, -2)).addGroup(jPanel5Layout.createSequentialGroup().addComponent(maleWieldZLabel).addGap(18, 18, 18).addComponent(this.mWieldZTextField, -2, 200, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(jLabel38).addGap(18, 18, 18).addComponent(this.fScaleXTextField, -2, 100, -2)).addGroup(jPanel5Layout.createSequentialGroup().addComponent(maleWieldYLabel).addGap(18, 18, 18).addComponent(this.mWieldYTextField, -2, 200, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(jLabel37).addGap(18, 18, 18).addComponent(this.int6, -2, 100, -2)).addGroup(jPanel5Layout.createSequentialGroup().addComponent(maleWieldXLabel).addGap(18, 18, 18).addComponent(this.mWieldXTextField, -2, 200, -2).addGap(73, 73, 73))).addContainerGap(64, 32767)));
        jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addGroup(jPanel5Layout.createParallelGroup(Alignment.BASELINE).addComponent(maleWieldXLabel).addComponent(this.mWieldXTextField, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel5Layout.createParallelGroup(Alignment.BASELINE).addComponent(maleWieldYLabel).addComponent(this.mWieldYTextField, -2, -1, -2).addComponent(jLabel37).addComponent(this.int6, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel5Layout.createParallelGroup(Alignment.BASELINE).addComponent(maleWieldZLabel).addComponent(this.mWieldZTextField, -2, -1, -2).addComponent(jLabel38).addComponent(this.fScaleXTextField, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel5Layout.createParallelGroup(Alignment.BASELINE).addComponent(fWieldXLabel).addComponent(this.fWieldXTextField, -2, -1, -2).addComponent(jLabel39).addComponent(this.fScaleYTextField, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel5Layout.createParallelGroup(Alignment.BASELINE).addComponent(fWieldYLabel).addComponent(this.fWieldYTextField, -2, -1, -2).addComponent(jLabel40).addComponent(this.fScaleZTextField, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel5Layout.createParallelGroup(Alignment.BASELINE).addComponent(fWieldZLabel).addComponent(this.fWieldZTextField, -2, -1, -2).addComponent(jLabel41).addComponent(this.ambienceTextField, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel5Layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel42).addComponent(this.diffusionTextField, -2, -1, -2)).addContainerGap(-1, 32767)));

        jTabbedPane1.addTab("Offsets/Scale", jPanel5);
        jLabel49.setText("unknownInt18");
        this.int18.setText("" + this.defs.unknownInt18);
        jLabel50.setText("unknownInt19");
        this.int19.setText("" + this.defs.unknownInt19);
        jLabel51.setText("unknownInt20");
        this.int20.setText("" + this.defs.unknownInt20);
        jLabel52.setText("unknownInt21");
        this.int21.setText("" + this.defs.unknownInt21);
        jLabel53.setText("unknownInt22");
        this.int22.setText("" + this.defs.unknownInt22);
        jLabel54.setText("unknownInt23");
        this.int23.setText("" + this.defs.unknownInt23);
        this.clientScriptOutput.setColumns(20);
        this.clientScriptOutput.setRows(5);
        this.clientScriptOutput.setText(this.getClientScripts());
        jScrollPane1.setViewportView(this.clientScriptOutput);
        jLabel26.setText("KEY");
        jLabel55.setText("VALUE");
        jLabel56.setText("Add the keys to the left to the boxes to edit them.");
        jLabel57.setText("Add new keys in the boxes to give the item new clientscripts.");
        GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1, -2, 305, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(Alignment.LEADING).addComponent(jLabel57).addGroup(jPanel7Layout.createParallelGroup(Alignment.TRAILING).addGroup(jPanel7Layout.createSequentialGroup().addGroup(jPanel7Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel7Layout.createParallelGroup(Alignment.TRAILING).addComponent(this.csk2, -2, 75, -2).addComponent(this.csk1, -2, 75, -2).addComponent(this.csk3, -2, 75, -2).addComponent(this.csk4, -2, 75, -2).addComponent(this.csk5, -2, 75, -2).addComponent(this.csk6, -2, 75, -2).addComponent(this.csk7, -2, 75, -2)).addComponent(jLabel26)).addGap(58, 58, 58).addGroup(jPanel7Layout.createParallelGroup(Alignment.LEADING).addComponent(this.csv1, -2, 75, -2).addComponent(this.csv2, -2, 75, -2).addComponent(this.csv3, -2, 75, -2).addComponent(this.csv4, -2, 75, -2).addComponent(this.csv5, -2, 75, -2).addComponent(this.csv6, -2, 75, -2).addComponent(this.csv7, -2, 75, -2).addComponent(jLabel55))).addComponent(jLabel56))).addContainerGap(-1, 32767)));
        jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addGroup(jPanel7Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addGroup(jPanel7Layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel26).addComponent(jLabel55)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.csk1, -2, -1, -2).addComponent(this.csv1, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.csk2, -2, -1, -2).addComponent(this.csv2, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.csk3, -2, -1, -2).addComponent(this.csv3, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.csk4, -2, -1, -2).addComponent(this.csv4, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.csk5, -2, -1, -2).addComponent(this.csv5, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.csk6, -2, -1, -2).addComponent(this.csv6, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(Alignment.BASELINE).addComponent(this.csk7, -2, -1, -2).addComponent(this.csv7, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED, 20, 32767).addComponent(jLabel56).addPreferredGap(ComponentPlacement.RELATED).addComponent(jLabel57)).addComponent(jScrollPane1)).addContainerGap()));
        jTabbedPane1.addTab("Clientscripts", jPanel7);
        currentViewLabel.setText("Currently Viewing Definitions of Item: " + this.defs.getId() + " - " + this.defs.getName());
        jMenu1.setText("File");
        reloadMenuBtn.setText("Reload");
        reloadMenuBtn.addActionListener(ItemEditor.this::reloadMenuBtnActionPerformed);
        jMenu1.add(reloadMenuBtn);
        saveMenuBtn.setText("Save");
        saveMenuBtn.addActionListener(ItemEditor.this::saveMenuBtnActionPerformed);
        jMenu1.add(saveMenuBtn);
        addModelMenuBtn.setText("Add Model");
        addModelMenuBtn.addActionListener(ItemEditor.this::addModelMenuBtnActionPerformed);
        jMenu1.add(addModelMenuBtn);
        exportMenuBtn.setText("Export to .txt");
        exportMenuBtn.addActionListener(ItemEditor.this::exportMenuBtnActionPerformed);
        jMenu1.add(exportMenuBtn);
        exitMenuBtn.setText("Exit");
        exitMenuBtn.addActionListener(ItemEditor.this::exitMenuBtnActionPerformed);
        jMenu1.add(exitMenuBtn);
        jMenuBar1.add(jMenu1);
        this.setJMenuBar(jMenuBar1);

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(jTabbedPane1).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(currentViewLabel).addContainerGap(-1, 32767)));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 11, 32767).addComponent(currentViewLabel).addPreferredGap(ComponentPlacement.RELATED).addComponent(jTabbedPane1, -2, 300, -2)));
        this.pack();
    }

    private void exitMenuBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void exportMenuBtnActionPerformed(ActionEvent evt) {
        this.export();
    }

    private void addModelMenuBtnActionPerformed(ActionEvent evt) {
        this.addModel();
    }

    private void saveMenuBtnActionPerformed(ActionEvent evt) {
        this.save();
    }

    private void reloadMenuBtnActionPerformed(ActionEvent evt) {
        this.itemName.setText(this.defs.getName());
        this.value.setText("" + this.defs.getCost());
        this.teamId.setText("" + this.defs.getTeamId());
        this.membersOnly.setSelected(this.defs.isMembersOnly());
        this.equipSlot.setText("" + this.defs.getEquipSlot());
        this.equipType.setText("" + this.defs.getEquipType());
        this.stackIDs.setText(this.getStackIDs());
        this.stackAmts.setText(this.getStackAmts());
        this.stackable.setSelected(this.defs.getStackable() == 1);
        this.Zoom2dField.setText("" + this.defs.getInvModelZoom());
        this.xan2dField.setText("" + this.defs.getXan2d());
        this.yan2dField.setText("" + this.defs.getYan2d());
        this.xOffset2dField.setText("" + this.defs.getxOffset2d());
        this.yOffset2dField.setText("" + this.defs.getyOffset2d());
        this.inventoryModelField.setText("" + this.defs.getInvModelId());
        this.maleEquip1Field.setText("" + this.defs.getMaleEquipModelId1());
        this.femaleEquip1Field.setText("" + this.defs.getFemaleEquipModelId1());
        this.maleEquip2Field.setText("" + this.defs.getMaleEquipModelId2());
        this.femaleEquip2Field.setText("" + this.defs.getFemaleEquipModelId2());
        this.maleEquip3Field.setText("" + this.defs.getMaleEquipModelId3());
        this.femaleEquip3Field.setText("" + this.defs.getFemaleEquipModelId3());
        this.invOptions.setText(this.getInventoryOpts());
        this.groundOptions.setText(this.getGroundOpts());
        this.modelColors.setText(this.getChangedModelColors());
        this.textureColors.setText(this.getChangedTextureColors());
        this.switchNote.setText("" + this.defs.switchNoteItemId);
        this.note.setText("" + this.defs.notedItemId);
        this.unnoted.setSelected(this.defs.isUnnoted());
        this.switchLend.setText("" + this.defs.getSwitchLendItemId());
        this.lend.setText("" + this.defs.getLendedItemId());
        this.array1.setText(this.getUnknownArray1());
        this.array2.setText(this.getUnknownArray2());
        this.array3.setText(this.getUnknownArray3());
        this.array4.setText(this.getUnknownArray4());
        this.array5.setText(this.getUnknownArray5());
        this.array6.setText(this.getUnknownArray6());
        this.maleDialogueHeadPrimaryField.setText("" + this.defs.primaryMaleDialogueHead);
        this.femaleDialogueHeadPrimaryField.setText("" + this.defs.primaryFemaleDialogueHead);
        this.maleDialogueHeadAccessoryField.setText("" + this.defs.secondaryMaleDialogueHead);
        this.femaleDialogueHeadAccessoryField.setText("" + this.defs.secondaryFemaleDialogueHead);
        this.zan2dField.setText("" + this.defs.Zan2d);
        this.int6.setText("" + this.defs.dummyItem);
        this.fScaleXTextField.setText("" + this.defs.floorScaleX);
        this.fScaleYTextField.setText("" + this.defs.floorScaleY);
        this.fScaleZTextField.setText("" + this.defs.floorScaleZ);
        this.ambienceTextField.setText("" + this.defs.ambience);
        this.diffusionTextField.setText("" + this.defs.diffusion);
        this.mWieldXTextField.setText("" + this.defs.maleWieldX);
        this.mWieldYTextField.setText("" + this.defs.maleWieldY);
        this.mWieldZTextField.setText("" + this.defs.maleWieldZ);
        this.fWieldXTextField.setText("" + this.defs.femaleWieldX);
        this.fWieldYTextField.setText("" + this.defs.femaleWieldY);
        this.fWieldZTextField.setText("" + this.defs.femaleWieldZ);
        this.int18.setText("" + this.defs.unknownInt18);
        this.int19.setText("" + this.defs.unknownInt19);
        this.int20.setText("" + this.defs.unknownInt20);
        this.int21.setText("" + this.defs.unknownInt21);
        this.int22.setText("" + this.defs.unknownInt22);
        this.int23.setText("" + this.defs.unknownInt23);
        this.clientScriptOutput.setText(this.getClientScripts());
    }

    private void export() {
        File f = new File(System.getProperty("user.home") + "/Dumps/items/");
        f.mkdirs();
        String lineSep = System.lineSeparator();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.home") + "/Dumps/items/" + this.defs.id + ".txt"), StandardCharsets.UTF_8))) {
            writer.write("name = " + this.defs.getName());
            writer.write(lineSep);
            writer.write("value = " + this.defs.getCost());
            writer.write(lineSep);
            writer.write("team id = " + this.defs.getTeamId());
            writer.write(lineSep);
            writer.write("members only = " + this.defs.isMembersOnly());
            writer.write(lineSep);
            writer.write("equip slot = " + this.defs.getEquipSlot());
            writer.write(lineSep);
            writer.write("equip type = " + this.defs.getEquipType());
            writer.write(lineSep);
            writer.write("stack ids = " + this.getStackIDs());
            writer.write(lineSep);
            writer.write("stack amounts = " + this.getStackAmts());
            writer.write(lineSep);
            writer.write("stackable = " + this.defs.getStackable());
            writer.write(lineSep);
            writer.write("inv model zoom = " + this.defs.getInvModelZoom());
            writer.write(lineSep);
            writer.write("model rotation 1 = " + this.defs.getXan2d());
            writer.write(lineSep);
            writer.write("model rotation 2 = " + this.defs.getYan2d());
            writer.write(lineSep);
            writer.write("model offset 1 = " + this.defs.getxOffset2d());
            writer.write(lineSep);
            writer.write("model offset 2 = " + this.defs.getyOffset2d());
            writer.write(lineSep);
            writer.write("inv model id = " + this.defs.getInvModelId());
            writer.write(lineSep);
            writer.write("male equip model id 1 = " + this.defs.getMaleEquipModelId1());
            writer.write(lineSep);
            writer.write("female equip model id 1 = " + this.defs.getFemaleEquipModelId1());
            writer.write(lineSep);
            writer.write("male equip model id 2 = " + this.defs.getMaleEquipModelId2());
            writer.write(lineSep);
            writer.write("female equip model id 2 = " + this.defs.getFemaleEquipModelId2());
            writer.write(lineSep);
            writer.write("male equip model id 3 = " + this.defs.getMaleEquipModelId3());
            writer.write(lineSep);
            writer.write("female equip model id 3 = " + this.defs.getFemaleEquipModelId3());
            writer.write(lineSep);
            writer.write("inventory options = " + this.getInventoryOpts());
            writer.write(lineSep);
            writer.write("ground options = " + this.getGroundOpts());
            writer.write(lineSep);
            writer.write("changed model colors = " + this.getChangedModelColors());
            writer.write(lineSep);
            writer.write("changed texture colors = " + this.getChangedTextureColors());
            writer.write(lineSep);
            writer.write("switch note item id = " + this.defs.switchNoteItemId);
            writer.write(lineSep);
            writer.write("noted item id = " + this.defs.notedItemId);
            writer.write(lineSep);
            writer.write("unnoted = " + this.defs.isUnnoted());
            writer.write(lineSep);
            writer.write("switch lend item id = " + this.defs.getSwitchLendItemId());
            writer.write(lineSep);
            writer.write("lended item id = " + this.defs.getLendedItemId());
            writer.write(lineSep);
            writer.write("unknownArray1 = " + this.getUnknownArray1());
            writer.write(lineSep);
            writer.write("unknownArray2 = " + this.getUnknownArray2());
            writer.write(lineSep);
            writer.write("unknownArray3 = " + this.getUnknownArray3());
            writer.write(lineSep);
            writer.write("unknownArray4 = " + this.getUnknownArray4());
            writer.write(lineSep);
            writer.write("unknownArray5 = " + this.getUnknownArray5());
            writer.write(lineSep);
            writer.write("unknownArray6 = " + this.getUnknownArray6());
            writer.write(lineSep);
            writer.write("unknownInt1 = " + this.defs.primaryMaleDialogueHead);
            writer.write(lineSep);
            writer.write("unknownInt2 = " + this.defs.primaryFemaleDialogueHead);
            writer.write(lineSep);
            writer.write("unknownInt3 = " + this.defs.secondaryMaleDialogueHead);
            writer.write(lineSep);
            writer.write("unknownInt4 = " + this.defs.secondaryFemaleDialogueHead);
            writer.write(lineSep);
            writer.write("spriteCameraYaw = " + this.defs.Zan2d);
            writer.write(lineSep);
            writer.write("unknownInt6 = " + this.defs.dummyItem);
            writer.write(lineSep);
            writer.write("unknownInt7 = " + this.defs.floorScaleX);
            writer.write(lineSep);
            writer.write("unknownInt8 = " + this.defs.floorScaleY);
            writer.write(lineSep);
            writer.write("unknownInt9 = " + this.defs.floorScaleZ);
            writer.write(lineSep);
            writer.write("unknownInt10 = " + this.defs.ambience);
            writer.write(lineSep);
            writer.write("unknownInt11 = " + this.defs.diffusion);
            writer.write(lineSep);
            writer.write("unknownInt12 = " + this.defs.maleWieldX);
            writer.write(lineSep);
            writer.write("unknownInt13 = " + this.defs.maleWieldY);
            writer.write(lineSep);
            writer.write("unknownInt14 = " + this.defs.maleWieldZ);
            writer.write(lineSep);
            writer.write("unknownInt15 = " + this.defs.femaleWieldX);
            writer.write(lineSep);
            writer.write("unknownInt16 = " + this.defs.femaleWieldY);
            writer.write(lineSep);
            writer.write("unknownInt17 = " + this.defs.femaleWieldZ);
            writer.write(lineSep);
            writer.write("unknownInt18 = " + this.defs.unknownInt18);
            writer.write(lineSep);
            writer.write("unknownInt19 = " + this.defs.unknownInt19);
            writer.write(lineSep);
            writer.write("unknownInt20 = " + this.defs.unknownInt20);
            writer.write(lineSep);
            writer.write("unknownInt21 = " + this.defs.unknownInt21);
            writer.write(lineSep);
            writer.write("unknownInt22 = " + this.defs.unknownInt22);
            writer.write(lineSep);
            writer.write("unknownInt23 = " + this.defs.unknownInt23);
            writer.write(lineSep);
            writer.write("Clientscripts");
            writer.write(lineSep);
            if (this.defs.clientScriptData != null) {

                for (Object o : this.defs.clientScriptData.keySet()) {
                    int key = (Integer) o;
                    Object value = this.defs.clientScriptData.get(key);
                    writer.write("KEY: " + key + ", VALUE: " + value);
                    writer.write(lineSep);
                }
            }
        } catch (IOException var151) {
            Main.log("ItemEditor", "Failed to export Item Defs to .txt");
        }

    }

    private void save() {
        try {
            this.defs.setName(this.itemName.getText());
            this.defs.setCost(Integer.parseInt(this.value.getText()));
            this.defs.setTeamId(Integer.parseInt(this.teamId.getText()));
            this.defs.setMembersOnly(this.membersOnly.isSelected());
            this.defs.setEquipSlot(Integer.parseInt(this.equipSlot.getText()));
            this.defs.setEquipType(Integer.parseInt(this.equipType.getText()));
            String[] var17;
            int invOpts;
            if (!this.stackIDs.getText().isEmpty()) {
                var17 = this.stackIDs.getText().split(";");

                for (invOpts = 0; invOpts < this.defs.getStackIds().length; ++invOpts) {
                    this.defs.getStackIds()[invOpts] = Integer.parseInt(var17[invOpts]);
                }
            }

            if (!this.stackAmts.getText().isEmpty()) {
                var17 = this.stackAmts.getText().split(";");

                for (invOpts = 0; invOpts < this.defs.getStackAmounts().length; ++invOpts) {
                    this.defs.getStackAmounts()[invOpts] = Integer.parseInt(var17[invOpts]);
                }
            }

            //this.defs.setStackable(Integer.parseInt(this.stackable.getText()));
            this.defs.setStackable((this.stackable.isSelected()) ? 1 : 0);
            this.defs.setInvModelZoom(Integer.parseInt(this.Zoom2dField.getText()));
            this.defs.setXan2d(Integer.parseInt(this.xan2dField.getText()));
            this.defs.setYan2d(Integer.parseInt(this.yan2dField.getText()));
            this.defs.setxOffset2d(Integer.parseInt(this.xOffset2dField.getText()));
            this.defs.setyOffset2d(Integer.parseInt(this.yOffset2dField.getText()));
            this.defs.setInvModelId(Integer.parseInt(this.inventoryModelField.getText()));
            this.defs.maleEquip1 = Integer.parseInt(this.maleEquip1Field.getText());
            this.defs.maleEquip2 = Integer.parseInt(this.maleEquip2Field.getText());
            this.defs.maleEquipModelId3 = Integer.parseInt(this.maleEquip3Field.getText());
            this.defs.femaleEquip1 = Integer.parseInt(this.femaleEquip1Field.getText());
            this.defs.femaleEquip2 = Integer.parseInt(this.femaleEquip2Field.getText());
            this.defs.femaleEquipModelId3 = Integer.parseInt(this.femaleEquip3Field.getText());
            var17 = this.groundOptions.getText().split(";");

            for (invOpts = 0; invOpts < this.defs.getGroundOptions().length; ++invOpts) {
                this.defs.getGroundOptions()[invOpts] = var17[invOpts].equals("null") ? null : var17[invOpts];
            }

            String[] var19 = this.invOptions.getText().split(";");

            for (int i = 0; i < this.defs.getInventoryOptions().length; ++i) {
                this.defs.getInventoryOptions()[i] = var19[i].equals("null") ? null : var19[i];
            }

            this.defs.resetModelColors();
            int len$;
            int i$;
            String t;
            String[] editedColor;
            String[] var18;
            String[] var21;
            if (!this.modelColors.getText().isEmpty()) {
                var18 = this.modelColors.getText().split(";");
                var21 = var18;
                len$ = var18.length;

                for (i$ = 0; i$ < len$; ++i$) {
                    t = var21[i$];
                    editedColor = t.split("=");
                    this.defs.changeModelColor(Integer.valueOf(editedColor[0]), Integer.valueOf(editedColor[1]));
                }
            }

            this.defs.resetTextureColors();
            if (!this.textureColors.getText().isEmpty()) {
                var18 = this.textureColors.getText().split(";");
                var21 = var18;
                len$ = var18.length;

                for (i$ = 0; i$ < len$; ++i$) {
                    t = var21[i$];
                    editedColor = t.split("=");
                    this.defs.changeTextureColor(Short.valueOf(editedColor[0]), Short.valueOf(editedColor[1]));
                }
            }

            this.defs.notedItemId = Integer.valueOf(this.note.getText());
            this.defs.switchNoteItemId = Integer.valueOf(this.switchNote.getText());
            this.defs.lendedItemId = Integer.valueOf(this.lend.getText());
            this.defs.switchLendItemId = Integer.valueOf(this.switchLend.getText());
            this.defs.setUnnoted(this.unnoted.isSelected());
            int var20;
            if (!this.array1.getText().isEmpty()) {
                var18 = this.array1.getText().split(";");

                for (var20 = 0; var20 < this.defs.recolorPalette.length; ++var20) {
                    this.defs.recolorPalette[var20] = (byte) Integer.parseInt(var18[var20]);
                }
            }

            if (!this.array2.getText().isEmpty()) {
                var18 = this.array2.getText().split(";");

                for (var20 = 0; var20 < this.defs.unknownArray2.length; ++var20) {
                    this.defs.unknownArray2[var20] = Integer.parseInt(var18[var20]);
                }
            }

            if (!this.array3.getText().isEmpty()) {
                var18 = this.array3.getText().split(";");

                for (var20 = 0; var20 < this.defs.unknownArray3.length; ++var20) {
                    this.defs.unknownArray3[var20] = (byte) Integer.parseInt(var18[var20]);
                }
            }

            if (!this.array4.getText().isEmpty()) {
                var18 = this.array4.getText().split(";");

                for (var20 = 0; var20 < this.defs.unknownArray4.length; ++var20) {
                    this.defs.unknownArray4[var20] = Integer.parseInt(var18[var20]);
                }
            }

            if (!this.array5.getText().isEmpty()) {
                var18 = this.array5.getText().split(";");

                for (var20 = 0; var20 < this.defs.unknownArray5.length; ++var20) {
                    this.defs.unknownArray5[var20] = Integer.parseInt(var18[var20]);
                }
            }

            if (!this.array6.getText().isEmpty()) {
                var18 = this.array6.getText().split(";");

                for (var20 = 0; var20 < this.defs.unknownArray6.length; ++var20) {
                    this.defs.unknownArray6[var20] = (byte) Integer.parseInt(var18[var20]);
                }
            }

            this.defs.primaryMaleDialogueHead = Integer.parseInt(this.maleDialogueHeadPrimaryField.getText());
            this.defs.primaryFemaleDialogueHead = Integer.parseInt(this.femaleDialogueHeadPrimaryField.getText());
            this.defs.secondaryMaleDialogueHead = Integer.parseInt(this.maleDialogueHeadAccessoryField.getText());
            this.defs.secondaryFemaleDialogueHead = Integer.parseInt(this.femaleDialogueHeadAccessoryField.getText());
            this.defs.Zan2d = Integer.parseInt(this.zan2dField.getText());
            this.defs.dummyItem = Integer.parseInt(this.int6.getText());
            this.defs.floorScaleX = Integer.parseInt(this.fScaleXTextField.getText());
            this.defs.floorScaleY = Integer.parseInt(this.fScaleYTextField.getText());
            this.defs.floorScaleZ = Integer.parseInt(this.fScaleZTextField.getText());
            this.defs.ambience = Integer.parseInt(this.ambienceTextField.getText());
            this.defs.diffusion = Integer.parseInt(this.diffusionTextField.getText());
            this.defs.maleWieldX = Integer.parseInt(this.mWieldXTextField.getText());
            this.defs.maleWieldY = Integer.parseInt(this.mWieldYTextField.getText());
            this.defs.maleWieldZ = Integer.parseInt(this.mWieldZTextField.getText());
            this.defs.femaleWieldX = Integer.parseInt(this.fWieldXTextField.getText());
            this.defs.femaleWieldY = Integer.parseInt(this.fWieldYTextField.getText());
            this.defs.femaleWieldZ = Integer.parseInt(this.fWieldZTextField.getText());
            this.defs.unknownInt18 = Integer.parseInt(this.int18.getText());
            this.defs.unknownInt19 = Integer.parseInt(this.int19.getText());
            this.defs.unknownInt20 = Integer.parseInt(this.int20.getText());
            this.defs.unknownInt21 = Integer.parseInt(this.int21.getText());
            this.defs.unknownInt22 = Integer.parseInt(this.int22.getText());
            this.defs.unknownInt23 = Integer.parseInt(this.int23.getText());

            try {
                if (!this.csk1.getText().isEmpty() && !this.csv1.getText().isEmpty()) {
                    try {
                        this.defs.clientScriptData.remove(this.csk1);
                        this.defs.clientScriptData.put(Integer.parseInt(this.csk1.getText()), Integer.parseInt(this.csv1.getText()));
                    } catch (Exception var181) {
                        this.defs.clientScriptData.remove(this.csk1);
                        this.defs.clientScriptData.put(Integer.parseInt(this.csk1.getText()), this.csv1.getText());
                    }
                }

                if (!this.csk2.getText().isEmpty() && !this.csv2.getText().isEmpty()) {
                    try {
                        this.defs.clientScriptData.remove(this.csk2);
                        this.defs.clientScriptData.put(Integer.parseInt(this.csk2.getText()), Integer.parseInt(this.csv2.getText()));
                    } catch (Exception var171) {
                        this.defs.clientScriptData.remove(this.csk2);
                        this.defs.clientScriptData.put(Integer.parseInt(this.csk2.getText()), this.csv2.getText());
                    }
                }

                if (!this.csk3.getText().isEmpty() && !this.csv3.getText().isEmpty()) {
                    try {
                        this.defs.clientScriptData.remove(this.csk3);
                        this.defs.clientScriptData.put(Integer.parseInt(this.csk3.getText()), Integer.parseInt(this.csv3.getText()));
                    } catch (Exception var16) {
                        this.defs.clientScriptData.remove(this.csk3);
                        this.defs.clientScriptData.put(Integer.parseInt(this.csk3.getText()), this.csv3.getText());
                    }
                }

                if (!this.csk4.getText().isEmpty() && !this.csv4.getText().isEmpty()) {
                    try {
                        this.defs.clientScriptData.remove(this.csk4);
                        this.defs.clientScriptData.put(Integer.parseInt(this.csk4.getText()), Integer.parseInt(this.csv4.getText()));
                    } catch (Exception var15) {
                        this.defs.clientScriptData.remove(this.csk4);
                        this.defs.clientScriptData.put(Integer.parseInt(this.csk4.getText()), this.csv4.getText());
                    }
                }

                if (!this.csk5.getText().isEmpty() && !this.csv5.getText().isEmpty()) {
                    try {
                        this.defs.clientScriptData.remove(this.csk5);
                        this.defs.clientScriptData.put(Integer.parseInt(this.csk5.getText()), Integer.parseInt(this.csv5.getText()));
                    } catch (Exception var14) {
                        this.defs.clientScriptData.remove(this.csk5);
                        this.defs.clientScriptData.put(Integer.parseInt(this.csk5.getText()), this.csv5.getText());
                    }
                }

                if (!this.csk6.getText().isEmpty() && !this.csv6.getText().isEmpty()) {
                    try {
                        this.defs.clientScriptData.remove(this.csk6);
                        this.defs.clientScriptData.put(Integer.parseInt(this.csk6.getText()), Integer.parseInt(this.csv6.getText()));
                    } catch (Exception var13) {
                        this.defs.clientScriptData.remove(this.csk6);
                        this.defs.clientScriptData.put(Integer.parseInt(this.csk6.getText()), this.csv6.getText());
                    }
                }

                if (!this.csk7.getText().isEmpty() && !this.csv7.getText().isEmpty()) {
                    try {
                        this.defs.clientScriptData.remove(this.csk7);
                        this.defs.clientScriptData.put(Integer.parseInt(this.csk7.getText()), Integer.parseInt(this.csv7.getText()));
                    } catch (Exception var12) {
                        this.defs.clientScriptData.remove(this.csk7);
                        this.defs.clientScriptData.put(Integer.parseInt(this.csk7.getText()), this.csv7.getText());
                    }
                }
            } catch (Exception var191) {
                this.defs.clientScriptData = new HashMap(1);
            }

            ItemSelection var10001 = this.is;
            this.defs.write(ItemSelection.STORE);
            this.is.updateItemDefs(this.defs);
        } catch (Exception var201) {
            Main.log("ItemEditor", "Cannot save. Please check for mistypes.");
        }

    }

    private void addModel() {
        JFrame frame = new JFrame();
        int result = JOptionPane.showConfirmDialog(frame, "Do you want to specify a model ID?");
        StringBuilder var10001;
        ItemSelection var10002;
        if (result == 0) {
            JFrame fc1 = new JFrame();
            String returnVal1 = JOptionPane.showInputDialog(fc1, "Enter new model ID:");
            if (Integer.parseInt(returnVal1) != -1) {
                JFileChooser file2 = new JFileChooser();
                file2.setFileSelectionMode(0);
                int var9 = file2.showOpenDialog(this);
                if (var9 == 0) {
                    File file1 = file2.getSelectedFile();

                    try {
                        var10001 = (new StringBuilder()).append("The model ID of the recently packed model is: ");
                        Main.log("ItemEditor", var10001.append(Utils.packCustomModel(ItemSelection.STORE, Utils.getBytesFromFile(new File(file1.getPath())), Integer.parseInt(returnVal1))).toString());
                    } catch (IOException var12) {
                        Main.log("ItemEditor", "There was an error packing the model.");
                    }
                }
            }
        } else if (result == 1) {
            JFileChooser fc11 = new JFileChooser();
            fc11.setFileSelectionMode(0);
            int returnVal11 = fc11.showOpenDialog(this);
            if (returnVal11 == 0) {
                File file21 = fc11.getSelectedFile();

                try {
                    var10001 = (new StringBuilder()).append("The model ID of the recently packed model is: ");
                    Main.log("ItemEditor", var10001.append(Utils.packCustomModel(ItemSelection.STORE, Utils.getBytesFromFile(new File(file21.getPath())))).toString());
                } catch (IOException var11) {
                    Main.log("ItemEditor", "There was an error packing the model.");
                }
            }
        }

    }

    public String getInventoryOpts() {
        StringBuilder text = new StringBuilder();
        String[] arr$ = this.defs.getInventoryOptions();
        int len$ = arr$.length;

        for (String option : arr$) {
            text.append(option == null ? "null" : option).append(";");
        }

        return text.toString();
    }

    public String getGroundOpts() {
        StringBuilder text = new StringBuilder();
        String[] arr$ = this.defs.getGroundOptions();
        int len$ = arr$.length;

        for (String option : arr$) {
            text.append(option == null ? "null" : option).append(";");
        }

        return text.toString();
    }

    public String getChangedModelColors() {
        StringBuilder text = new StringBuilder();
        if (this.defs.originalModelColors != null) {
            for (int i = 0; i < this.defs.originalModelColors.length; ++i) {
                text.append(this.defs.originalModelColors[i]).append("=").append(this.defs.modifiedModelColors[i]).append(";");
            }
        }

        return text.toString();
    }

    public String getChangedTextureColors() {
        StringBuilder text = new StringBuilder();
        if (this.defs.originalTextureColors != null) {
            for (int i = 0; i < this.defs.originalTextureColors.length; ++i) {
                text.append(this.defs.originalTextureColors[i]).append("=").append(this.defs.modifiedTextureColors[i]).append(";");
            }
        }

        return text.toString();
    }

    public String getStackIDs() {
        StringBuilder text = new StringBuilder();

        try {
            int[] e = this.defs.getStackIds();
            int len$ = e.length;

            for (int index : e) {
                text.append(index).append(";");
            }
        } catch (Exception var6) {
        }

        return text.toString();
    }

    public String getClientScripts() {
        StringBuilder text = new StringBuilder();
        String lineSep = System.lineSeparator();
        if (this.defs.clientScriptData != null) {
            for (Iterator i$ = this.defs.clientScriptData.keySet().iterator(); i$.hasNext(); text.append(lineSep)) {
                int key = (Integer) i$.next();
                Object value = this.defs.clientScriptData.get(key);
                text.append("KEY: ").append(key).append(", VALUE: ").append(value);
            }
        }

        return text.toString();
    }

    public String getStackAmts() {
        StringBuilder text = new StringBuilder();

        try {
            int[] e = this.defs.getStackAmounts();
            int len$ = e.length;

            for (int index : e) {
                text.append(index).append(";");
            }
        } catch (Exception var6) {
        }

        return text.toString();
    }

    public String getUnknownArray1() {
        StringBuilder text = new StringBuilder();

        try {
            byte[] e = this.defs.recolorPalette;
            int len$ = e.length;

            for (byte index : e) {
                text.append(index).append(";");
            }
        } catch (Exception var6) {
        }

        return text.toString();
    }

    public String getUnknownArray2() {
        StringBuilder text = new StringBuilder();

        try {
            int[] e = this.defs.unknownArray2;
            int len$ = e.length;

            for (int index : e) {
                text.append(index).append(";");
            }
        } catch (Exception var6) {
        }

        return text.toString();
    }

    public String getUnknownArray3() {
        StringBuilder text = new StringBuilder();

        try {
            byte[] e = this.defs.unknownArray3;
            int len$ = e.length;

            for (byte index : e) {
                text.append(index).append(";");
            }
        } catch (Exception var6) {
        }

        return text.toString();
    }

    public String getUnknownArray4() {
        StringBuilder text = new StringBuilder();

        try {
            int[] e = this.defs.unknownArray4;
            int len$ = e.length;

            for (int index : e) {
                text.append(index).append(";");
            }
        } catch (Exception var6) {
        }

        return text.toString();
    }

    public String getUnknownArray5() {
        StringBuilder text = new StringBuilder();

        try {
            int[] e = this.defs.unknownArray5;
            int len$ = e.length;

            for (int index : e) {
                text.append(index).append(";");
            }
        } catch (Exception var6) {
        }

        return text.toString();
    }

    public String getUnknownArray6() {
        StringBuilder text = new StringBuilder();

        try {
            byte[] e = this.defs.unknownArray6;
            int len$ = e.length;

            for (byte index : e) {
                text.append(index).append(";");
            }
        } catch (Exception var6) {
        }

        return text.toString();
    }
}
