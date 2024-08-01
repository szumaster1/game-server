package core.cache.def.impl;

import core.cache.Cache;
import core.cache.def.Definition;
import core.cache.misc.buffer.ByteBufferUtils;
import core.game.interaction.OptionHandler;
import core.game.node.entity.player.Player;
import core.game.node.scenery.Scenery;
import core.game.world.GameWorld;
import core.tools.Log;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static core.api.ContentAPIKt.getVarp;
import static core.api.ContentAPIKt.log;

public class SceneryDefinition extends Definition<Scenery> {

    private static final Map<Integer, SceneryDefinition> DEFINITIONS = new HashMap<Integer, SceneryDefinition>();

    private static final Map<String, OptionHandler> OPTION_HANDLERS = new HashMap<>();

    private short[] originalColors;

    public int[] childrenIds;

    private int[] modelIds;

    private int[] modelConfiguration;

    static int anInt3832;

    int[] anIntArray3833 = null;

    private int anInt3834;

    int anInt3835;

    static int anInt3836;

    private byte aByte3837;

    int anInt3838 = -1;

    boolean aBoolean3839;

    private int anInt3840;

    private int anInt3841;

    static int anInt3842;

    static int anInt3843;

    int anInt3844;

    boolean aBoolean3845;

    static int anInt3846;

    private byte aByte3847;

    private byte aByte3849;

    int anInt3850;

    int anInt3851;

    public boolean secondBool;

    public boolean aBoolean3853;

    int anInt3855;

    public boolean notClipped;

    int anInt3857;

    private byte[] aByteArray3858;

    int[] anIntArray3859;

    int anInt3860;

    int configFileId;

    private short[] modifiedColors;

    int anInt3865;

    boolean aBoolean3866;

    boolean aBoolean3867;

    public boolean projectileClipped;

    private int[] anIntArray3869;

    boolean aBoolean3870;

    public int sizeY;

    boolean aBoolean3872;

    boolean membersOnly;

    public boolean boolean1;

    private int anInt3875;

    public int animationId;

    private int anInt3877;

    private int anInt3878;

    public int clipType;

    private int anInt3881;

    private int anInt3882;

    private int anInt3883;

    Object loader;

    private int anInt3889;

    public int sizeX;

    public boolean aBoolean3891;

    int anInt3892;

    public int interactable;

    boolean aBoolean3894;

    boolean aBoolean3895;

    int anInt3896;

    int configId;

    private byte[] aByteArray3899;

    int anInt3900;

    private int anInt3902;

    int anInt3904;

    int anInt3905;

    boolean aBoolean3906;

    int[] anIntArray3908;

    private byte aByte3912;

    int anInt3913;

    private byte aByte3914;

    private int anInt3915;

    private int[][] anIntArrayArray3916;

    private int anInt3917;

    private short[] aShortArray3919;

    private short[] aShortArray3920;

    int anInt3921;

    private Object aClass194_3922;

    boolean aBoolean3923;

    boolean aBoolean3924;

    int walkingFlag;

    private boolean hasHiddenOptions;

    private short mapIcon;

    public SceneryDefinition() {
        anInt3835 = -1;
        anInt3860 = -1;
        configFileId = -1;
        aBoolean3866 = false;
        anInt3851 = -1;
        anInt3865 = 255;
        aBoolean3845 = false;
        aBoolean3867 = false;
        anInt3850 = 0;
        anInt3844 = -1;
        anInt3881 = 0;
        anInt3857 = -1;
        aBoolean3872 = true;
        anInt3882 = -1;
        anInt3834 = 0;
        options = new String[5];
        anInt3875 = 0;
        aBoolean3839 = false;
        anIntArray3869 = null;
        sizeY = 1;
        boolean1 = false;
        projectileClipped = true;
        anInt3883 = 0;
        aBoolean3895 = true;
        anInt3840 = 0;
        aBoolean3870 = false;
        anInt3889 = 0;
        aBoolean3853 = true;
        secondBool = false;
        clipType = 2;
        anInt3855 = -1;
        anInt3878 = 0;
        anInt3904 = 0;
        sizeX = 1;
        animationId = -1;
        notClipped = false;
        aBoolean3891 = false;
        anInt3905 = 0;
        name = "null";
        anInt3913 = -1;
        aBoolean3906 = false;
        membersOnly = false;
        aByte3914 = (byte) 0;
        anInt3915 = 0;
        anInt3900 = 0;
        interactable = -1;
        aBoolean3894 = false;
        aByte3912 = (byte) 0;
        anInt3921 = 0;
        anInt3902 = 128;
        configId = -1;
        anInt3877 = 0;
        walkingFlag = 0;
        anInt3892 = 64;
        aBoolean3923 = false;
        aBoolean3924 = false;
        anInt3841 = 128;
        anInt3917 = 128;
        mapIcon = -1;
    }

    public static void main(String... args) throws Throwable {
        GameWorld.prompt(false);
        // if (true) {
        // for (int id = 0; id <= 27325; id++) {
        // ObjectDefinition def = ObjectDefinition.forId(id);
        // if (def.mapIcon > 69) {

        // def.mapIcon);
        // }
        // }
        // return; 2105
        // }
		/*ObjectDefinition def = ObjectDefinition.forId(2105);

		for (Field f : def.getClass().getDeclaredFields()) {
			if (!Modifier.isStatic(f.getModifiers())) {
				if (f.getType().isArray()) {
					Object object = f.get(def);
					if (object != null) {
						int length = Array.getLength(object);
						System.out.print(f.getName() + ", [");
						for (int i = 0; i < length; i++) {
							System.out.print(Array.get(object, i) + (i < (length - 1) ? ", " : "]"));
						}

						continue;
					}
				}

			}
		}
		for (Field f : def.getClass().getSuperclass().getDeclaredFields()) {
			if (!Modifier.isStatic(f.getModifiers())) {
				if (f.getType().isArray()) {
					Object object = f.get(def);
					if (object != null) {
						int length = Array.getLength(object);
						System.out.print(f.getName() + ", [");
						for (int i = 0; i < length; i++) {
							System.out.print(Array.get(object, i) + (i < (length - 1) ? ", " : "]"));
						}

						continue;
					}
				}

			}
		}*/
    }

    public static void parse() throws Throwable {
        for (int objectId = 0; objectId < Cache.getObjectDefinitionsSize(); objectId++) {
            byte[] data = Cache.getIndexes()[16].getFileData(getContainerId(objectId), objectId & 0xff);
            if (data == null) {
                SceneryDefinition.getDefinitions().put(objectId, new SceneryDefinition());
                //SystemLogger.logErr("Could not load object definitions for id " + objectId + " - no data!");
                continue;
            }
            SceneryDefinition def = SceneryDefinition.parseDefinition(objectId, ByteBuffer.wrap(data));
            if (def == null) {
                //	SystemLogger.logErr("Could not load object definitions for id " + objectId + " - no definitions found!");
                return;
            }
            SceneryDefinition.getDefinitions().put(objectId, def);
            data = null;
        }
    }

    public static SceneryDefinition forId(int objectId) {
        SceneryDefinition def = DEFINITIONS.get(objectId);
        if (def != null) {
            return def;
        }
        DEFINITIONS.put(objectId, def = new SceneryDefinition());
        def.id = objectId;
        return def;
    }


    public static SceneryDefinition parseDefinition(int objectId, ByteBuffer buffer) {
        SceneryDefinition def = new SceneryDefinition();
        def.id = objectId;
//		SystemLogger.logErr("----------------------------------------------------\n\n\n");
        while (true) {
            if (!buffer.hasRemaining()) {
                log(SceneryDefinition.class, Log.ERR, "Buffer empty for " + objectId);
                break;
            }
            int opcode = buffer.get() & 0xFF;
            //SystemLogger.logErr("Parsing object " + objectId + " op " + opcode);
            if (opcode == 1 || opcode == 5) {
                int length = buffer.get() & 0xff;
                if (def.modelIds == null) {
                    def.modelIds = new int[length];
                    if (opcode == 1) {
                        def.modelConfiguration = new int[length];
                    }
                    for (int i = 0; i < length; i++) {
                        def.modelIds[i] = buffer.getShort() & 0xFFFF;
                        if (opcode == 1) {
                            def.modelConfiguration[i] = buffer.get() & 0xFF;
                        }
                    }
                } else {
                    buffer.position(buffer.position() + (length * (opcode == 1 ? 3 : 2)));
                }
            } else if (opcode == 2) {
                def.name = ByteBufferUtils.getString(buffer);
            } else if (opcode == 14) {
                def.sizeX = buffer.get() & 0xFF;
            } else if (opcode == 15) {
                def.sizeY = buffer.get() & 0xFF;
            } else if (opcode == 17) {
                def.projectileClipped = false;
                def.clipType = 0;
            } else if (opcode == 18) {
                def.projectileClipped = false;
            } else if (opcode == 19) {
                def.interactable = buffer.get() & 0xFF;
            } else if (opcode == 21) {
                def.aByte3912 = (byte) 1;
            } else if (opcode == 22) {
                def.aBoolean3867 = true;
            } else if (opcode == 23) {
                def.boolean1 = true;
            } else if (opcode == 24) {
                def.animationId = buffer.getShort() & 0xFFFF;
                if (def.animationId == 65535) {
                    def.animationId = -1;
                }
            } else if (opcode == 27) {
                def.clipType = 1;
            } else if (opcode == 28) {
                def.anInt3892 = ((buffer.get() & 0xFF) << 2);
            } else if (opcode == 29) {
                def.anInt3878 = buffer.get();
            } else if (opcode == 39) {
                def.anInt3840 = buffer.get() * 5;
            } else if (opcode >= 30 && opcode < 35) {
                def.options[opcode - 30] = ByteBufferUtils.getString(buffer);
                if (def.options[opcode - 30].equals("Hidden")) {
                    def.options[opcode - 30] = null;
                    def.hasHiddenOptions = true;
                }
            } else if (opcode == 40) {
                int length = buffer.get() & 0xFF;
                def.originalColors = new short[length];
                def.modifiedColors = new short[length];
                for (int i = 0; i < length; i++) {
                    def.originalColors[i] = buffer.getShort();
                    def.modifiedColors[i] = buffer.getShort();
                }
            } else if (opcode == 41) {
                int length = buffer.get() & 0xFF;
                def.aShortArray3920 = new short[length];
                def.aShortArray3919 = new short[length];
                for (int i = 0; i < length; i++) {
                    def.aShortArray3920[i] = buffer.getShort();
                    def.aShortArray3919[i] = buffer.getShort();
                }
            } else if (opcode == 42) {
                int length = buffer.get() & 0xFF;
                def.aByteArray3858 = new byte[length];
                for (int i = 0; i < length; i++) {
                    def.aByteArray3858[i] = buffer.get();
                }
            } else if (opcode == 60) {
                def.mapIcon = buffer.getShort();
            } else if (opcode == 62) {
                def.aBoolean3839 = true;
            } else if (opcode == 64) {
                def.aBoolean3872 = false;
            } else if (opcode == 65) {
                def.anInt3902 = buffer.getShort() & 0xFFFF;
            } else if (opcode == 66) {
                def.anInt3841 = buffer.getShort() & 0xFFFF;
            } else if (opcode == 67) {
                def.anInt3917 = buffer.getShort() & 0xFFFF;
            } else if (opcode == 68) {
                buffer.getShort();
            } else if (opcode == 69) {
                def.walkingFlag = buffer.get() & 0xFF;
            } else if (opcode == 70) {
                def.anInt3883 = buffer.getShort() << 2;
            } else if (opcode == 71) {
                def.anInt3889 = buffer.getShort() << 2;
            } else if (opcode == 72) {
                def.anInt3915 = buffer.getShort() << 2;
            } else if (opcode == 73) {
                def.secondBool = true;
            } else if (opcode == 74) {
                def.notClipped = true;
            } else if (opcode == 75) {
                def.anInt3855 = buffer.get() & 0xFF;
            } else if (opcode == 77 || opcode == 92) {
                def.configFileId = buffer.getShort() & 0xFFFF;
                if (def.configFileId == 65535) {
                    def.configFileId = -1;
                }
                def.configId = buffer.getShort() & 0xFFFF;
                if (def.configId == 65535) {
                    def.configId = -1;
                }
                int defaultId = -1;
                if (opcode == 92) {
                    defaultId = buffer.getShort() & 0xFFFF;
                    if (defaultId == 65535) {
                        defaultId = -1;
                    }
                }
                int childrenAmount = buffer.get() & 0xFF;
                def.childrenIds = new int[childrenAmount + 2];
                for (int index = 0; childrenAmount >= index; index++) {
                    def.childrenIds[index] = buffer.getShort() & 0xFFFF;
                    if (def.childrenIds[index] == 65535) {
                        def.childrenIds[index] = -1;
                    }
                }
                def.childrenIds[childrenAmount + 1] = defaultId;
            } else if (opcode == 78) {
                def.anInt3860 = buffer.getShort() & 0xFFFF;
                def.anInt3904 = buffer.get() & 0xFF;
            } else if (opcode == 79) {
                def.anInt3900 = buffer.getShort() & 0xFFFF;
                def.anInt3905 = buffer.getShort() & 0xFFFF;
                def.anInt3904 = buffer.get() & 0xFF;
                int length = buffer.get() & 0xFF;
                def.anIntArray3859 = new int[length];
                for (int i = 0; i < length; i++) {
                    def.anIntArray3859[i] = buffer.getShort() & 0xFFFF;
                }
            } else if (opcode == 81) {
                def.aByte3912 = (byte) 2;
                def.anInt3882 = 256 * buffer.get() & 0xFF;
            } else if (opcode == 82 || opcode == 88) {
                // Nothing.
            } else if (opcode == 89) {
                def.aBoolean3895 = false;
            } else if (opcode == 90) {
                def.aBoolean3870 = true;
            } else if (opcode == 91) {
                def.membersOnly = true;
            } else if (opcode == 93) {
                def.aByte3912 = (byte) 3;
                def.anInt3882 = buffer.getShort() & 0xFFFF;
            } else if (opcode == 94) {
                def.aByte3912 = (byte) 4;
            } else if (opcode == 95) {
                def.aByte3912 = (byte) 5;
            } else if (opcode == 96 || opcode == 97) {
                //
            } else if (opcode == 100) {
                buffer.get();
                buffer.getShort();
            } else if (opcode == 101) {
                buffer.get();
            } else if (opcode == 102) {
                buffer.getShort();
            } else if (opcode == 249) { // cs2 scripts
                int length = buffer.get() & 0xFF;
                for (int i = 0; i < length; i++) {
                    boolean string = buffer.get() == 1;
                    ByteBufferUtils.getMedium(buffer); // script id
                    if (!string) {
                        buffer.getInt(); // Value
                    } else {
                        ByteBufferUtils.getString(buffer); // value
                    }
                }
            } else {
                if (opcode != 0) {
                    log(SceneryDefinition.class, Log.ERR, "Unhandled object definition opcode: " + opcode);
                }
                break;
            }
        }
        def.configureObject();
        if (def.notClipped) {
            def.clipType = 0;
            def.projectileClipped = false;
        }
        return def;
    }

    final void configureObject() {
        if (interactable == -1) {
            interactable = 0;
            if (modelIds != null && (getModelConfiguration() == null || getModelConfiguration()[0] == 10)) {
                interactable = 1;
            }
            for (int i = 0; i < 5; i++) {
                if (options[i] != null) {
                    interactable = 1;
                    break;
                }
            }
        }
        if (childrenIds != null) {
            for (int childrenId : childrenIds) {
                SceneryDefinition def = forId(childrenId);
                def.configFileId = configFileId;
            }
        }
        if (anInt3855 == -1) {
            anInt3855 = clipType == 0 ? 0 : 1;
        }
        // Manual changes
        if (id == 31017) {
            sizeX = sizeY = 2;
        }
        if (id == 29292) {
            projectileClipped = false;
        }
    }

    public boolean hasActions() {
        if (interactable > 0) {
            return true;
        }
        if (childrenIds == null) {
            return hasOptions(false);
        }
        for (int childrenId : childrenIds) {
            if (childrenId != -1) {
                SceneryDefinition def = forId(childrenId);
                if (def.hasOptions(false)) {
                    return true;
                }
            }
        }
        return hasOptions(false);
    }

    public SceneryDefinition getChildObject(Player player) {
        if (childrenIds == null || childrenIds.length < 1) {
            return this;
        }
        int configValue = -1;
        if (player != null) {
            if (configFileId != -1) {
                VarbitDefinition def = VarbitDefinition.forObjectID(configFileId);
                if (def != null) {
                    configValue = def.getValue(player);
                }
            } else if (configId != -1) {
                configValue = getVarp(player, configId);
            }
        } else {
            configValue = 0;
        }
        SceneryDefinition childDef = getChildObjectAtIndex(configValue);
        if (childDef != null) childDef.configFileId = this.configFileId;
        return childDef;
    }

    public SceneryDefinition getChildObjectAtIndex(int index) {
        if (childrenIds == null || childrenIds.length < 1) {
            return this;
        }
        if (index < 0 || index >= childrenIds.length - 1 || childrenIds[index] == -1) {
            int objectId = childrenIds[childrenIds.length - 1];
            if (objectId != -1) {
                return forId(objectId);
            }
            return this;
        }
        return forId(childrenIds[index]);
    }

    public VarbitDefinition getConfigFile() {
        if (configFileId != -1) {
            return VarbitDefinition.forObjectID(configFileId);
        }
        return null;
    }

    public boolean isaBoolean3839() {
        return aBoolean3839;
    }

    public void setaBoolean3839(boolean aBoolean3839) {
        this.aBoolean3839 = aBoolean3839;
    }

    public short[] getOriginalColors() {
        return originalColors;
    }

    public int[] getChildrenIds() {
        return childrenIds;
    }

    public static int getAnInt3832() {
        return anInt3832;
    }

    public int[] getAnIntArray3833() {
        return anIntArray3833;
    }

    public int getAnInt3834() {
        return anInt3834;
    }

    public int getAnInt3835() {
        return anInt3835;
    }

    public static int getAnInt3836() {
        return anInt3836;
    }

    public byte getaByte3837() {
        return aByte3837;
    }

    public int getAnInt3838() {
        return anInt3838;
    }

    public int getAnInt3840() {
        return anInt3840;
    }

    public int getAnInt3841() {
        return anInt3841;
    }

    public static int getAnInt3842() {
        return anInt3842;
    }

    public static int getAnInt3843() {
        return anInt3843;
    }

    public int getAnInt3844() {
        return anInt3844;
    }

    public boolean isaBoolean3845() {
        return aBoolean3845;
    }

    public static int getAnInt3846() {
        return anInt3846;
    }

    public byte getaByte3847() {
        return aByte3847;
    }

    public byte getaByte3849() {
        return aByte3849;
    }

    public int getAnInt3850() {
        return anInt3850;
    }

    public int getAnInt3851() {
        return anInt3851;
    }

    public boolean isSecondBool() {
        return secondBool;
    }

    public boolean isaBoolean3853() {
        return aBoolean3853;
    }

    public int getAnInt3855() {
        return anInt3855;
    }

    public boolean isFirstBool() {
        return notClipped;
    }

    public int getAnInt3857() {
        return anInt3857;
    }

    public byte[] getaByteArray3858() {
        return aByteArray3858;
    }

    public int[] getAnIntArray3859() {
        return anIntArray3859;
    }

    public int getAnInt3860() {
        return anInt3860;
    }

    @Override
    public String[] getOptions() {
        return options;
    }

    public int getVarbitID() {
        return configFileId;
    }

    public short[] getModifiedColors() {
        return modifiedColors;
    }

    public int getAnInt3865() {
        return anInt3865;
    }

    public boolean isaBoolean3866() {
        return aBoolean3866;
    }

    public boolean isaBoolean3867() {
        return aBoolean3867;
    }

    public boolean isProjectileClipped() {
        return projectileClipped;
    }

    public int[] getAnIntArray3869() {
        return anIntArray3869;
    }

    public boolean isaBoolean3870() {
        return aBoolean3870;
    }

    public int getSizeY() {
        return sizeY;
    }

    public boolean isaBoolean3872() {
        return aBoolean3872;
    }

    public boolean isaBoolean3873() {
        return membersOnly;
    }

    public boolean getThirdBoolean() {
        return boolean1;
    }

    public int getAnInt3875() {
        return anInt3875;
    }

    public int getAddObjectCheck() {
        return animationId;
    }

    public int getAnInt3877() {
        return anInt3877;
    }

    public int getAnInt3878() {
        return anInt3878;
    }

    public int getClipType() {
        return clipType;
    }

    public int getAnInt3881() {
        return anInt3881;
    }

    public int getAnInt3882() {
        return anInt3882;
    }

    public int getAnInt3883() {
        return anInt3883;
    }

    public Object getLoader() {
        return loader;
    }

    public int getAnInt3889() {
        return anInt3889;
    }

    public int getSizeX() {
        return sizeX;
    }

    public boolean isaBoolean3891() {
        return aBoolean3891;
    }

    public int getAnInt3892() {
        return anInt3892;
    }

    public int getInteractable() {
        return interactable;
    }

    public boolean isaBoolean3894() {
        return aBoolean3894;
    }

    public boolean isaBoolean3895() {
        return aBoolean3895;
    }

    public int getAnInt3896() {
        return anInt3896;
    }

    public int getConfigId() {
        return configId;
    }

    public byte[] getaByteArray3899() {
        return aByteArray3899;
    }

    public int getAnInt3900() {
        return anInt3900;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getAnInt3902() {
        return anInt3902;
    }

    public int getAnInt3904() {
        return anInt3904;
    }

    public int getAnInt3905() {
        return anInt3905;
    }

    public boolean isaBoolean3906() {
        return aBoolean3906;
    }

    public int[] getAnIntArray3908() {
        return anIntArray3908;
    }

    public byte getaByte3912() {
        return aByte3912;
    }

    public int getAnInt3913() {
        return anInt3913;
    }

    public byte getaByte3914() {
        return aByte3914;
    }

    public int getAnInt3915() {
        return anInt3915;
    }

    public int[][] getAnIntArrayArray3916() {
        return anIntArrayArray3916;
    }

    public int getAnInt3917() {
        return anInt3917;
    }

    public short[] getaShortArray3919() {
        return aShortArray3919;
    }

    public short[] getaShortArray3920() {
        return aShortArray3920;
    }

    public int getAnInt3921() {
        return anInt3921;
    }

    public Object getaClass194_3922() {
        return aClass194_3922;
    }

    public boolean isaBoolean3923() {
        return aBoolean3923;
    }

    public boolean isaBoolean3924() {
        return aBoolean3924;
    }

    public int[] getModelIds() {
        return modelIds;
    }

    public boolean hasAction(String action) {
        if (options == null) {
            return false;
        }
        for (String option : options) {
            if (option == null) {
                continue;
            }
            if (option.equalsIgnoreCase(action)) {
                return true;
            }
        }
        return false;
    }

    public static Map<Integer, SceneryDefinition> getDefinitions() {
        return DEFINITIONS;
    }

    public static OptionHandler getOptionHandler(int nodeId, String name) {
        SceneryDefinition def = forId(nodeId);
        OptionHandler handler = def.getConfiguration("option:" + name);
        if (handler != null) {
            return handler;
        }
        return OPTION_HANDLERS.get(name);
    }

    public static boolean setOptionHandler(String name, OptionHandler handler) {
        return OPTION_HANDLERS.put(name, handler) != null;
    }

    public boolean isHasHiddenOptions() {
        return hasHiddenOptions;
    }

    public void setHasHiddenOptions(boolean hasHiddenOptions) {
        this.hasHiddenOptions = hasHiddenOptions;
    }

    public int getWalkingFlag() {
        return walkingFlag;
    }

    public int[] getModelConfiguration() {
        return modelConfiguration;
    }

    public void setModelConfiguration(int[] modelConfiguration) {
        this.modelConfiguration = modelConfiguration;
    }

    public short getMapIcon() {
        return mapIcon;
    }

    public static int getContainerId(int id) {
        return id >>> 8;
    }
}
