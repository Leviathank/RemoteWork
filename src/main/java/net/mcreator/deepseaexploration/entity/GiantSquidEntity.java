
package net.mcreator.deepseaexploration.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.world.IWorldReader;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.data.Main;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import net.mcreator.deepseaexploration.DeepSeaExplorationModElements;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@DeepSeaExplorationModElements.ModElement.Tag
public class GiantSquidEntity extends DeepSeaExplorationModElements.ModElement {
	public static EntityType entity = null;
	public GiantSquidEntity(DeepSeaExplorationModElements instance) {
		super(instance, 1);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.WATER_CREATURE)
				.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new)
				.size(2.3000000000000003f, 1f)).build("giant_squid").setRegistryName("giant_squid");
		elements.entities.add(() -> entity);
		elements.items.add(
				() -> new SpawnEggItem(entity, -3407872, -205, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("giant_squid_spawn_egg"));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
			boolean biomeCriteria = false;
			if (ForgeRegistries.BIOMES.getKey(biome).equals(new ResourceLocation("deep_cold_ocean")))
				biomeCriteria = true;
			if (!biomeCriteria)
				continue;
			biome.getSpawns(EntityClassification.WATER_CREATURE).add(new Biome.SpawnListEntry(entity, 6, 1, 4));
		}
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				SquidEntity::func_223365_b);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
			return new MobRenderer(renderManager, new ModelCollosalSquid(), 1f) {
				@Override
				public ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("deep_sea_exploration:textures/collosal_squid.png");
				}
			};
		});
	}
	public static class CustomEntity extends CreatureEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 8;
			setNoAI(false);
			this.moveController = new MovementController(this) {
				@Override
				public void tick() {
					if (CustomEntity.this.areEyesInFluid(FluidTags.WATER))
						CustomEntity.this.setMotion(CustomEntity.this.getMotion().add(0, 0.005, 0));
					if (this.action == MovementController.Action.MOVE_TO && !CustomEntity.this.getNavigator().noPath()) {
						double dx = this.posX - CustomEntity.this.getPosX();
						double dy = this.posY - CustomEntity.this.getPosY();
						double dz = this.posZ - CustomEntity.this.getPosZ();
						dy = dy / (double) MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
						CustomEntity.this.rotationYaw = this.limitAngle(CustomEntity.this.rotationYaw,
								(float) (MathHelper.atan2(dz, dx) * (double) (180 / (float) Math.PI)) - 90, 90);
						CustomEntity.this.renderYawOffset = CustomEntity.this.rotationYaw;
						CustomEntity.this.setAIMoveSpeed(MathHelper.lerp(0.125f, CustomEntity.this.getAIMoveSpeed(),
								(float) (this.speed * CustomEntity.this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue())));
						CustomEntity.this.setMotion(CustomEntity.this.getMotion().add(0, CustomEntity.this.getAIMoveSpeed() * dy * 0.1, 0));
					} else {
						CustomEntity.this.setAIMoveSpeed(0);
					}
				}
			};
			this.navigator = new SwimmerPathNavigator(this, this.world);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(1, new PanicGoal(this, 1.2));
			this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
			this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1, 40));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.WATER;
		}

		@Override
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.squid.ambient"));
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.squid.squirt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.squid.death"));
		}

		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if (source == DamageSource.DROWN)
				return false;
			return super.attackEntityFrom(source, amount);
		}

		@Override
		protected void registerAttributes() {
			super.registerAttributes();
			if (this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null)
				this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2);
			if (this.getAttribute(SharedMonsterAttributes.MAX_HEALTH) != null)
				this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
			if (this.getAttribute(SharedMonsterAttributes.ARMOR) != null)
				this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0);
			if (this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) == null)
				this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
			this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0);
		}

		@Override
		public boolean canBreatheUnderwater() {
			return true;
		}

		@Override
		public boolean isNotColliding(IWorldReader worldreader) {
			return worldreader.checkNoEntityCollision(this, VoxelShapes.create(this.getBoundingBox()));
		}

		@Override
		public boolean isPushedByWater() {
			return false;
		}
	}

	// Made with Blockbench 3.8.3
	// Exported for Minecraft version 1.15 - 1.16
	// Paste this class into your mod and generate all required imports
	public static class ModelCollosalSquid extends EntityModel<Entity> {
		private final ModelRenderer Main;
		private final ModelRenderer s;
		private final ModelRenderer s2;
		private final ModelRenderer s3;
		private final ModelRenderer s4;
		private final ModelRenderer s5;
		private final ModelRenderer s6;
		private final ModelRenderer s7;
		private final ModelRenderer s8;
		private final ModelRenderer s9;
		private final ModelRenderer s10;
		private final ModelRenderer lr;
		private final ModelRenderer ll;
		private final ModelRenderer top;
		public ModelCollosalSquid() {
			textureWidth = 512;
			textureHeight = 512;
			Main = new ModelRenderer(this);
			Main.setRotationPoint(0.0F, 24.0F, -1.0F);
			s = new ModelRenderer(this);
			s.setRotationPoint(6.0F, -16.0F, 25.0F);
			Main.addChild(s);
			s.setTextureOffset(195, 76).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 43.0F, 0.0F, false);
			s2 = new ModelRenderer(this);
			s2.setRotationPoint(7.0F, -7.0F, 25.0F);
			Main.addChild(s2);
			s2.setTextureOffset(155, 191).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 43.0F, 0.0F, false);
			s3 = new ModelRenderer(this);
			s3.setRotationPoint(7.0F, -3.0F, 25.0F);
			Main.addChild(s3);
			s3.setTextureOffset(148, 146).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 43.0F, 0.0F, false);
			s4 = new ModelRenderer(this);
			s4.setRotationPoint(2.0F, -2.0F, 25.0F);
			Main.addChild(s4);
			s4.setTextureOffset(148, 101).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 43.0F, 0.0F, false);
			s5 = new ModelRenderer(this);
			s5.setRotationPoint(-2.0F, -2.0F, 25.0F);
			Main.addChild(s5);
			s5.setTextureOffset(101, 144).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 43.0F, 0.0F, false);
			s6 = new ModelRenderer(this);
			s6.setRotationPoint(-7.0F, -3.0F, 25.0F);
			Main.addChild(s6);
			s6.setTextureOffset(0, 144).addBox(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 43.0F, 0.0F, false);
			s7 = new ModelRenderer(this);
			s7.setRotationPoint(-7.0F, -7.0F, 25.0F);
			Main.addChild(s7);
			s7.setTextureOffset(101, 99).addBox(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 43.0F, 0.0F, false);
			s8 = new ModelRenderer(this);
			s8.setRotationPoint(-6.0F, -16.0F, 25.0F);
			Main.addChild(s8);
			s8.setTextureOffset(0, 99).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 43.0F, 0.0F, false);
			s9 = new ModelRenderer(this);
			s9.setRotationPoint(-2.0F, -16.0F, 25.0F);
			Main.addChild(s9);
			s9.setTextureOffset(0, 45).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 43.0F, 0.0F, false);
			s10 = new ModelRenderer(this);
			s10.setRotationPoint(2.0F, -16.0F, 25.0F);
			Main.addChild(s10);
			s10.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 43.0F, 0.0F, false);
			lr = new ModelRenderer(this);
			lr.setRotationPoint(-7.0F, -11.0F, 25.0F);
			Main.addChild(lr);
			lr.setTextureOffset(0, 0).addBox(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 97.0F, 0.0F, false);
			ll = new ModelRenderer(this);
			ll.setRotationPoint(7.0F, -11.0F, 25.0F);
			Main.addChild(ll);
			ll.setTextureOffset(0, 99).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 97.0F, 0.0F, false);
			top = new ModelRenderer(this);
			top.setRotationPoint(6.0F, -16.0F, 25.0F);
			Main.addChild(top);
			top.setTextureOffset(101, 0).addBox(-20.0F, -2.0F, -48.0F, 28.0F, 18.0F, 34.0F, 0.0F, false);
			top.setTextureOffset(101, 52).addBox(-31.0F, 7.0F, -57.0F, 49.0F, 1.0F, 23.0F, 0.0F, false);
			top.setTextureOffset(191, 0).addBox(-13.0F, 0.0F, -14.0F, 14.0F, 14.0F, 14.0F, 0.0F, false);
		}

		@Override
		public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			// previously the render function, render code was moved to a method below
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			Main.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	}
}
