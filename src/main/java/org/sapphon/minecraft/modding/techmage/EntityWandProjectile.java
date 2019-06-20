package org.sapphon.minecraft.modding.techmage;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.sapphon.minecraft.modding.minecraftpython.problemhandlers.JavaProblemHandler;

public class EntityWandProjectile extends EntityEgg {

	public EntityWandProjectile(World par1World, EntityLivingBase par2EntityLivingBase, MagicWand magicWand,
			boolean isInaccurate) {
		super(par1World, par2EntityLivingBase);
		this.wand = magicWand;
		if (isInaccurate)
			randomizeVelocity();
	}

	private MagicWand wand;

	@Override
	protected void onImpact(MovingObjectPosition raytraceHit) {
		Vec3 hitLocation = raytraceHit.hitVec;
		if (this.wand == null) {
			JavaProblemHandler.printErrorMessageToDialogBox(new Exception(
					"Problems with spell projectile not understanding which wand shot it."));
		} else if (hitLocation == null) {
			JavaProblemHandler.printErrorMessageToDialogBox(new Exception(
					"Problems with the projectile knowing where it landed."));
		}
		if (this.worldObj.isRemote) {
			wand.spellInterpreter.setupImpactVariablesInPython(hitLocation);
			wand.castStoredSpell();
			this.setDead();
		}
	}

	private void randomizeVelocity() {
		double xVelRandom = Math.random() + 0.5f;
		double yVelRandom = Math.random() + 0.5f;
		double zVelRandom = Math.random() + 0.5f;
		this.setVelocity(this.motionX * xVelRandom, this.motionY * yVelRandom, this.motionZ * zVelRandom);
	}

}
