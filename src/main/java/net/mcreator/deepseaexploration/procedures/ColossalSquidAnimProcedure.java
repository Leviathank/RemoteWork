package net.mcreator.deepseaexploration.procedures;

import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.animation.builder.AnimationBuilder;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.entity.Entity;

import net.mcreator.deepseaexploration.DeepSeaExplorationModElements;

import java.util.Map;

@DeepSeaExplorationModElements.ModElement.Tag
public class ColossalSquidAnimProcedure extends DeepSeaExplorationModElements.ModElement {
	public ColossalSquidAnimProcedure(DeepSeaExplorationModElements instance) {
		super(instance, 2);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				System.err.println("Failed to load dependency entity for procedure ColossalSquidAnim!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof IAnimatedEntity) {
			new Object() {
				@OnlyIn(Dist.CLIENT)
				void playAnimation(Entity entity, String animationID) {
					IAnimatedEntity aniEntity = (IAnimatedEntity) entity;
					aniEntity.getAnimationManager().get("controller").setAnimation(new AnimationBuilder().addAnimation(animationID, (true)));
				}
			}.playAnimation(entity, "CollosalSquid.animation.json");
		}
	}
}
