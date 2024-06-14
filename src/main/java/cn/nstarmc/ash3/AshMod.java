package cn.nstarmc.ash3;

import cn.nstarmc.ash3.config.AshConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigManager;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class AshMod implements ClientModInitializer {

    public static final String MOD_ID = "ash3";
    public static ConfigManager<AshConfig> configManager;

    @Override
    public void onInitializeClient() {
        configManager = (ConfigManager<AshConfig>) AutoConfig.register(AshConfig.class, GsonConfigSerializer::new);

        KeyBinding toggleAsh = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.ash3.toggleAsh", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "key.categories.misc"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleAsh.wasPressed()) {
                AshCommands.config.showHud = !AshCommands.config.showHud;
                configManager.save();
            }
        });

//        GuiRegistry registry = AutoConfig.getGuiRegistry(AshConfig.class);
//        registry.registerPredicateProvider((i13n, field, config, defaults, guiProvider) -> {
//            ConfigEntryBuilder ENTRY_BUILDER = ConfigEntryBuilder.create();
//
//            String[] ints = new String[3];
//
//            ints[0] = "Left";
//            ints[1] = "Center";
//            ints[2] = "Right";
//
//            return Collections.singletonList(ENTRY_BUILDER.startSelector(Text.translatable(i13n), ints, alignToString((Integer) Utils.getUnsafely(field, config, (Object)null))).setDefaultValue(() -> {
//                return alignToString(Utils.getUnsafely(field, defaults));
//            }).setSaveConsumer((newValue) -> {
//                int intValue = 0;
//                if(newValue.equals("Center"))
//                    intValue = 1;
//                else if(newValue.equals("Right"))
//                    intValue = 2;
//
//                Utils.setUnsafely(field, config, intValue);
//            }).build());
//        }, (field) -> {
//            return field.getName().equals("align");
//        });
//
//        registry.registerPredicateProvider((i13n, field, config, defaults, guiProvider) -> {
//            ConfigEntryBuilder ENTRY_BUILDER = ConfigEntryBuilder.create();
//
//            String[] ints = new String[3];
//
//            ints[0] = "Top";
//            ints[1] = "Middle";
//            ints[2] = "Bottom";
//
//            return Collections.singletonList(ENTRY_BUILDER.startSelector(Text.translatable(i13n), ints, verticalAlignToString((Integer) Utils.getUnsafely(field, config, (Object)null))).setDefaultValue(() -> {
//                return verticalAlignToString(Utils.getUnsafely(field, defaults));
//            }).setSaveConsumer((newValue) -> {
//                int intValue = 0;
//                if(newValue.equals("Middle"))
//                    intValue = 1;
//                else if(newValue.equals("Bottom"))
//                    intValue = 2;
//
//                Utils.setUnsafely(field, config, intValue);
//            }).build());
//        }, (field) -> {
//            return field.getName().equals("verticalAlign");
//        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            AshCommands.registerCommands();
        });

    }

//    private String alignToString(int align) {
//        switch (align) {
//            case 0:
//                return "Left";
//            case 1:
//                return "Center";
//            case 2:
//                return "Right";
//            default:
//                return "";
//        }
//    }
//
//    private String verticalAlignToString(int verticalAlign) {
//        switch (verticalAlign) {
//            case 0:
//                return "Top";
//            case 1:
//                return "Middle";
//            case 2:
//                return "Bottom";
//            default:
//                return "";
//        }
//    }
}