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