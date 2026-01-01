package yourscraft.jasdewstarfield.noenchantment;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(NoEnchantment.MODID)
public class NoEnchantment {
    public static final String MODID = "noenchantment";
    private static final Logger LOGGER = LogUtils.getLogger();

    public NoEnchantment(IEventBus modEventBus, ModContainer modContainer) {

        modContainer.registerConfig(ModConfig.Type.COMMON, NoEnchantmentConfig.SPEC);

        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.addListener(this::onServerStarting);

        LOGGER.info("[No Enchantment] Mod loaded!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("[No Enchantment] HELLO FROM COMMON SETUP");
    }

    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("[No Enchantment] HELLO from server starting");
    }
}
