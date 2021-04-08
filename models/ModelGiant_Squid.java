// Made with Blockbench 3.8.3
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports

public static class ModelGiant_Squid extends EntityModel<Entity> {
	private final ModelRenderer Main;
	private final ModelRenderer Head;
	private final ModelRenderer st3;
	private final ModelRenderer st2;
	private final ModelRenderer st1;
	private final ModelRenderer lt1;
	private final ModelRenderer lt2;

	public ModelGiant_Squid() {
		textureWidth = 256;
		textureHeight = 256;

		Main = new ModelRenderer(this);
		Main.setRotationPoint(0.0F, 24.0F, -1.0F);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 0.0F, 4.0F);
		Main.addChild(Head);
		Head.setTextureOffset(74, 74).addBox(-2.0F, -3.0F, -27.0F, 5.0F, 3.0F, 34.0F, 0.0F, false);
		Head.setTextureOffset(15, 0).addBox(-5.0F, -1.0F, -26.0F, 11.0F, 0.0F, 13.0F, 0.0F, false);

		st3 = new ModelRenderer(this);
		st3.setRotationPoint(3.0F, 0.0F, 11.0F);
		Main.addChild(st3);
		st3.setTextureOffset(0, 0).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 26.0F, 0.0F, false);

		st2 = new ModelRenderer(this);
		st2.setRotationPoint(0.0F, 0.0F, 11.0F);
		Main.addChild(st2);
		st2.setTextureOffset(0, 27).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 26.0F, 0.0F, false);

		st1 = new ModelRenderer(this);
		st1.setRotationPoint(-2.0F, 0.0F, 11.0F);
		Main.addChild(st1);
		st1.setTextureOffset(0, 73).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 26.0F, 0.0F, false);

		lt1 = new ModelRenderer(this);
		lt1.setRotationPoint(3.0F, -3.0F, 11.0F);
		Main.addChild(lt1);
		lt1.setTextureOffset(0, 73).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 72.0F, 0.0F, false);

		lt2 = new ModelRenderer(this);
		lt2.setRotationPoint(-2.0F, -3.0F, 11.0F);
		Main.addChild(lt2);
		lt2.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 72.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		// previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		Main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}