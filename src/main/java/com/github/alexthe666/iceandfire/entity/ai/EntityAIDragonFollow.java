package com.github.alexthe666.iceandfire.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.enums.EnumOrder;

public class EntityAIDragonFollow extends EntityAIBase
{
    World theWorld;
    float maxDist;
    float minDist;
    private EntityDragonBase dragon;
    private EntityLivingBase theOwner;
    private double speed;
    private PathNavigate petPathfinder;
    private int counter;
    private boolean avoidsWater;

    public EntityAIDragonFollow(EntityDragonBase dragon, float minDist, float maxDist)
    {
        this.dragon = dragon;
        this.theWorld = dragon.worldObj;
        this.speed = 1;
        this.petPathfinder = dragon.getNavigator();
        this.minDist = minDist;
        this.maxDist = maxDist;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.dragon.isTamed())
        {
            return false;
        }
        else
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)this.dragon.getOwner();

            if (entitylivingbase == null)
            {
                return false;
            }
            else if (this.dragon.currentOrder != null && this.dragon.currentOrder != EnumOrder.FOLLOW)
            {
                return false;
            }
            else if (this.dragon.isSitting())
            {
                return false;
            }
            else if (this.dragon.getDistanceSqToEntity(entitylivingbase) < (double) (this.minDist * this.minDist))
            {
                return false;
            }
            else
            {
                this.theOwner = entitylivingbase;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.petPathfinder.noPath() && this.dragon.getDistanceSqToEntity(this.theOwner) > (double) (this.maxDist * this.maxDist) && !this.dragon.isSitting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.counter = 0;

    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.theOwner = null;
        this.petPathfinder.clearPathEntity();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.dragon.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, (float) this.dragon.getVerticalFaceSpeed());

        if (!this.dragon.isSitting())
        {
            if (--this.counter <= 0)
            {
                this.counter = 10;

                if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.speed))
                {
                    if (!this.dragon.getLeashed())
                    {
                        if (this.dragon.getDistanceSqToEntity(this.theOwner) >= 144.0D)
                        {
                            int i = MathHelper.floor_double(this.theOwner.posX) - 2;
                            int j = MathHelper.floor_double(this.theOwner.posZ) - 2;
                            int k = MathHelper.floor_double(this.theOwner.getBoundingBox().minY);

                            for (int l = 0; l <= 4; ++l)
                            {
                                for (int i1 = 0; i1 <= 4; ++i1)
                                {
                                    if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && World.doesBlockHaveSolidTopSurface(this.theWorld, new BlockPos(i + l, k - 1, j + i1)) && !this.theWorld.getBlockState(new BlockPos(i + l, k, j + i1)).getBlock().isNormalCube())
                                    {
                                        this.dragon.setLocationAndAngles((double) ((float) (i + l) + 0.5F), (double) k, (double) ((float) (j + i1) + 0.5F), this.dragon.rotationYaw, this.dragon.rotationPitch);
                                        this.petPathfinder.clearPathEntity();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
