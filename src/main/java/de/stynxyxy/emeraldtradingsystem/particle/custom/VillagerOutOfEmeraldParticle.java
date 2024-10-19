package de.stynxyxy.emeraldtradingsystem.particle.custom;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VillagerOutOfEmeraldParticle extends TextureSheetParticle {

    protected VillagerOutOfEmeraldParticle(ClientLevel level, double XCoord, double yCoord, double zCoord, SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, XCoord, yCoord, zCoord, xd, yd, zd);
        this.friction = 0.9F;
        this.xd = xd;
        this.yd = 0.01;
        this.zd = zd;
        this.lifetime = 120;
        this.quadSize *= 3;
        this.setSpriteFromAge(spriteSet);
        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;

    }

    @Override
    public void tick() {
        super.tick();
        this.fadeout();
    }
    public void fadeout() {
        //this.alpha = (-(1/(float)lifetime) * age +1);
        this.quadSize = ((-(this.quadSize/(float)lifetime) * age +1) / 2)  ;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }



    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        public Particle createParticle(SimpleParticleType p_106838_, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {

            return new VillagerOutOfEmeraldParticle(level,x,y,z,this.sprite,xd,yd,zd);
        }
    }
}



