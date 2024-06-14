package cn.nstarmc.ash3;

import com.mojang.brigadier.arguments.IntegerArgumentType;

import cn.nstarmc.ash3.config.AshConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class AshCommands {
	
	private static final String COMMAND_TOGGLE = "ash3toggle";
	private static final String COMMAND_RESET = "ash3reset";
	private static final String COMMAND_FPS = "ash3togglefps";
	private static final String COMMAND_COORDS = "ash3togglecoords";
	private static final String COMMAND_DIRECTION = "ash3toggledirection";
	private static final String COMMAND_COLOR = "ash3color";
	private static final String COMMAND_BACKGROUND_COLOR = "ash3backgroundcolor";
	private static final String COMMAND_LIGHTLEVEL = "ash3togglelightlevel";
	private static final String COMMAND_TIME = "ash3toggletime";
	private static final String COMMAND_CONCISE_COORDS = "ash3toggleconcisecoords";
	private static final String COMMAND_TOGGLE_BACKGROUND = "ash3togglebackground";

    public static AshConfig config;

	public static void registerCommands() {

		if (config == null) {
			config = AutoConfig.getConfigHolder(AshConfig.class).getConfig();
		}

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal(COMMAND_TOGGLE).executes(context -> {
				config.showHud = !config.showHud;
				AshMod.configManager.save();
				return 1;
			}));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal(COMMAND_RESET).executes(context -> {
				config = new AshConfig();
				AshMod.configManager.save();
				return 1;
			}));
		});
		
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal(COMMAND_FPS).executes(context -> {
				config.showFps = !config.showFps;
				AshMod.configManager.save();
				return 1;
			}));
		});

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal(COMMAND_COORDS).executes(context -> {
                config.showCoords = !config.showCoords;
                AshMod.configManager.save();
                return 1;
            }));
		});
        
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal(COMMAND_DIRECTION)
	            .executes(context -> {
                config.showDirection = !config.showDirection;
                AshMod.configManager.save();
                return 1;
            }));
		});
		
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal(COMMAND_COLOR)
	            .then(ClientCommandManager.argument("r", IntegerArgumentType.integer())
	                .then(ClientCommandManager.argument("g", IntegerArgumentType.integer())
	                	.then(ClientCommandManager.argument("b", IntegerArgumentType.integer())
	                		.executes(context -> {
	                			int r = IntegerArgumentType.getInteger(context,"r");
	                			int g = IntegerArgumentType.getInteger(context,"g");
	                			int b = IntegerArgumentType.getInteger(context,"b");

	                			config.hudColor = b + (g << 8) + (r << 16);
	                			AshMod.configManager.save();
	                			return 1;
	                		})))));
		});
        
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal(COMMAND_BACKGROUND_COLOR)
	            .then(ClientCommandManager.argument("colorAsARGBinteger", IntegerArgumentType.integer())
	                		.executes(context -> {
	                			int color = IntegerArgumentType.getInteger(context,"colorAsARGBinteger");
	                			
	                			config.hudBackgroundColor = color;
	                			AshMod.configManager.save();
	                			return 1;
	                		})));
		});
		
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal(COMMAND_LIGHTLEVEL)
	            .executes(context -> {
                config.showLightLevel = !config.showLightLevel;
                AshMod.configManager.save();
                return 1;
            }));
		});
		
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal(COMMAND_TIME)
	            .executes(context -> {
                config.showTime = !config.showTime;
                AshMod.configManager.save();
                return 1;
            }));
		});
		
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal(COMMAND_CONCISE_COORDS)
	            .executes(context -> {
                config.conciseCoords = !config.conciseCoords;
                AshMod.configManager.save();
                return 1;
            }));
		});
		
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal(COMMAND_TOGGLE_BACKGROUND)
	            .executes(context -> {
                config.showBackground = !config.showBackground;
                AshMod.configManager.save();
                return 1;
            }));
		});
    }
}