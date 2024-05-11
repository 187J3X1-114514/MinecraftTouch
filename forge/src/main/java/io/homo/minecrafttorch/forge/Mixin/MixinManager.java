package io.homo.minecrafttorch.forge.Mixin;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import io.homo.minecrafttouch.common.MinecraftTouch;
import java.util.List;
import java.util.Set;

public class MixinManager implements IMixinConfigPlugin {
    private boolean isFrameworkInstalled;
    @Override
    public void onLoad(String mixinPackage) {
        try {
            Class.forName("io.homo.minecrafttorch.forge.MinecraftTouchForge", false, this.getClass().getClassLoader());
            isFrameworkInstalled = true;//MinecraftTouch.ConfigHandler.configData.enableMod;

        } catch (Exception e) {
            isFrameworkInstalled = false;
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        MinecraftTouch.LOGGER.info(targetClassName+" "+mixinClassName);
        return isFrameworkInstalled;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

}
