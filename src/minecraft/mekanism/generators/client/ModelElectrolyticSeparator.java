package mekanism.generators.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelElectrolyticSeparator extends ModelBase
{
    ModelRenderer A;
    ModelRenderer B;
    ModelRenderer C;
    ModelRenderer D;
    ModelRenderer E;
    ModelRenderer F;
    ModelRenderer G;
    ModelRenderer H;
    ModelRenderer I;
    ModelRenderer J;
    ModelRenderer K;
    ModelRenderer L;
    ModelRenderer M;
    ModelRenderer N;
    ModelRenderer OUTPUT;
    ModelRenderer P;
    ModelRenderer Q;
    ModelRenderer R;
    ModelRenderer S;
    ModelRenderer T;
    ModelRenderer U;
    ModelRenderer V;
  
    public ModelElectrolyticSeparator()
    {
    	textureWidth = 128;
    	textureHeight = 128;
    
    	A = new ModelRenderer(this, 0, 0);
      	A.addBox(-8F, 0F, -8F, 16, 1, 16);
      	A.setRotationPoint(0F, 23F, 0F);
      	A.setTextureSize(128, 128);
      	A.mirror = true;
      	setRotation(A, 0F, 0F, 0F);
      	B = new ModelRenderer(this, 85, 0);
      	B.addBox(-2F, 0F, -2F, 4, 10, 4);
      	B.setRotationPoint(4F, 12F, -4F);
      	B.setTextureSize(128, 128);
      	B.mirror = true;
      	setRotation(B, 0F, 1.570796F, 0F);
      	C = new ModelRenderer(this, 66, 0);
      	C.addBox(-2F, 0F, -2F, 4, 10, 4);
      	C.setRotationPoint(-4F, 12F, -4F);
      	C.setTextureSize(128, 128);
      	C.mirror = true;
      	setRotation(C, 0F, 1.570796F, 0F);
      	D = new ModelRenderer(this, 48, 19);
      	D.addBox(-1F, -1F, 0F, 2, 2, 6);
      	D.setRotationPoint(-2F, 18F, -4F);
      	D.setTextureSize(128, 128);
      	D.mirror = true;
      	setRotation(D, 0F, 1.570796F, 0F);
      	E = new ModelRenderer(this, 0, 19);
      	E.addBox(0F, 0F, -6F, 4, 15, 14);
      	E.setRotationPoint(0F, 8F, 8F);
      	E.setTextureSize(128, 128);
      	E.mirror = true;
      	setRotation(E, 0F, 1.570796F, 0F);
      	F = new ModelRenderer(this, 103, 0);
      	F.addBox(-3F, -1F, -1F, 6, 2, 2);
      	F.setRotationPoint(4F, 20F, 1F);
      	F.setTextureSize(128, 128);
      	F.mirror = true;
      	setRotation(F, 0F, 1.570796F, 0F);
      	G = new ModelRenderer(this, 103, 0);
      	G.addBox(-3F, 0F, 0F, 6, 2, 2);
      	G.setRotationPoint(-5F, 20F, 1F);
      	G.setTextureSize(128, 128);
      	G.mirror = true;
      	setRotation(G, 0F, 1.570796F, 0F);
      	H = new ModelRenderer(this, 0, 50);
      	H.addBox(-2F, 0F, 0F, 4, 12, 2);
        H.setRotationPoint(-8F, 11F, 6F);
        H.setTextureSize(128, 128);
        H.mirror = true;
        setRotation(H, 0F, 1.570796F, 0F);
        I = new ModelRenderer(this, 41, 28);
        I.addBox(0F, 0F, 0F, 6, 1, 6);
        I.setRotationPoint(-7F, 22F, -7F);
        I.setTextureSize(128, 128);
        I.mirror = true;
        setRotation(I, 0F, 0F, 0F);
        J = new ModelRenderer(this, 41, 28);
        J.addBox(0F, 0F, 0F, 6, 1, 6);
        J.setRotationPoint(1F, 22F, -7F);
        J.setTextureSize(128, 128);
        J.mirror = true;
        setRotation(J, 0F, 0F, 0F);
        K = new ModelRenderer(this, 66, 16);
        K.addBox(0F, 0F, 0F, 2, 1, 2);
        K.setRotationPoint(-5F, 11F, -5F);
        K.setTextureSize(128, 128);
        K.mirror = true;
        setRotation(K, 0F, 0F, 0F);
        L = new ModelRenderer(this, 85, 15);
        L.addBox(0F, 0F, 0F, 2, 2, 2);
        L.setRotationPoint(3F, 10F, -5F);
        L.setTextureSize(128, 128);
        L.mirror = true;
        setRotation(L, 0F, 0F, 0F);
        M = new ModelRenderer(this, 66, 22);
        M.addBox(-1F, 0F, -1F, 2, 2, 6);
        M.setRotationPoint(-4F, 9F, -4F);
        M.setTextureSize(128, 128);
        M.mirror = true;
        setRotation(M, 0F, 1.570796F, 0F);
        N = new ModelRenderer(this, 66, 32);
        N.addBox(-1F, -1F, -1F, 2, 6, 2);
        N.setRotationPoint(0F, 12F, -4F);
        N.setTextureSize(128, 128);
        N.mirror = true;
        setRotation(N, 0F, 0F, 0F);
        OUTPUT = new ModelRenderer(this, 66, 42);
        OUTPUT.addBox(-1F, -1F, -1F, 4, 2, 2);
        OUTPUT.setRotationPoint(0F, 16F, -5F);
        OUTPUT.setTextureSize(128, 128);
        OUTPUT.mirror = true;
        setRotation(OUTPUT, 0F, 1.570796F, 0F);
        P = new ModelRenderer(this, 85, 21);
        P.addBox(-1F, -1F, -1F, 2, 3, 2);
        P.setRotationPoint(5F, 9F, -4F);
        P.setTextureSize(128, 128);
        P.mirror = true;
        setRotation(P, 0F, 0F, 0.6457718F);
        Q = new ModelRenderer(this, 0, 65);
        Q.addBox(0F, 0F, -2F, 3, 11, 6);
        Q.setRotationPoint(-8F, 12F, 0F);
        Q.setTextureSize(128, 128);
        Q.mirror = true;
        setRotation(Q, 0F, 0F, 0F);
        R = new ModelRenderer(this, 0, 65);
        R.addBox(0F, 0F, -3F, 3, 11, 6);
        R.setRotationPoint(8F, 12F, 1F);
        R.setTextureSize(128, 128);
        R.mirror = true;
        setRotation(R, 0F, 3.141593F, 0F);
        S = new ModelRenderer(this, 0, 84);
        S.addBox(0F, 0F, 0F, 4, 1, 1);
        S.setRotationPoint(2F, 16F, 3F);
        S.setTextureSize(128, 128);
        S.mirror = true;
        setRotation(S, 0F, 0F, 0F);
        T = new ModelRenderer(this, 0, 84);
        T.addBox(0F, 0F, 0F, 4, 1, 1);
        T.setRotationPoint(-6F, 16F, 3F);
        T.setTextureSize(128, 128);
        T.mirror = true;
        setRotation(T, 0F, 0F, 0F);
        U = new ModelRenderer(this, 0, 87);
        U.addBox(0F, 0F, 0F, 4, 1, 1);
        U.setRotationPoint(-6F, 18F, 3F);
        U.setTextureSize(128, 128);
        U.mirror = true;
        setRotation(U, 0F, 0F, 0F);
        V = new ModelRenderer(this, 0, 87);
        V.addBox(0F, 0F, 0F, 4, 1, 1);
        V.setRotationPoint(2F, 18F, 3F);
        V.setTextureSize(128, 128);
        V.mirror = true;
        setRotation(V, 0F, 0F, 0F);
    }
    
    public void render(float size)
    {
    	A.render(size);
    	B.render(size);
    	C.render(size);
    	D.render(size);
    	E.render(size);
    	F.render(size);
    	G.render(size);
    	H.render(size);
    	I.render(size);
    	J.render(size);
    	K.render(size);
    	L.render(size);
    	M.render(size);
    	N.render(size);
    	OUTPUT.render(size);
    	P.render(size);
    	Q.render(size);
    	R.render(size);
    	S.render(size);
    	T.render(size);
    	U.render(size);
    	V.render(size);
    }
  
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {	
    	super.render(entity, f, f1, f2, f3, f4, f5);
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	A.renderWithRotation(f5);
    	B.renderWithRotation(f5);
    	C.renderWithRotation(f5);
    	D.renderWithRotation(f5);
    	E.renderWithRotation(f5);
    	F.renderWithRotation(f5);
    	G.renderWithRotation(f5);
    	H.renderWithRotation(f5);
    	I.renderWithRotation(f5);
    	J.renderWithRotation(f5);
    	K.renderWithRotation(f5);
    	L.renderWithRotation(f5);
    	M.renderWithRotation(f5);
    	N.renderWithRotation(f5);
    	OUTPUT.renderWithRotation(f5);
    	P.renderWithRotation(f5);
    	Q.renderWithRotation(f5);
    	R.renderWithRotation(f5);
    	S.renderWithRotation(f5);
    	T.renderWithRotation(f5);
    	U.renderWithRotation(f5);
    	V.renderWithRotation(f5);
    }
  
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
    	model.rotateAngleX = x;
    	model.rotateAngleY = y;
    	model.rotateAngleZ = z;
    }
  
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
    	super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
