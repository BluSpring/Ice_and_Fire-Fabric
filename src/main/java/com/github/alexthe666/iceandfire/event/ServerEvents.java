package com.github.alexthe666.iceandfire.event;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.entity.*;
import com.github.alexthe666.iceandfire.entity.ai.AiDebug;
import com.github.alexthe666.iceandfire.entity.ai.EntitySheepAIFollowCyclops;
import com.github.alexthe666.iceandfire.entity.ai.VillagerAIFearUntamed;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.IAnimalFear;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.item.*;
import com.github.alexthe666.iceandfire.message.MessagePlayerHitMultipart;
import com.github.alexthe666.iceandfire.message.MessageSwingArm;
import com.github.alexthe666.iceandfire.message.MessageSyncPath;
import com.github.alexthe666.iceandfire.misc.IafDamageRegistry;
import com.github.alexthe666.iceandfire.misc.IafTagRegistry;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.Pathfinding;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.pathjobs.AbstractPathJob;
import com.github.alexthe666.iceandfire.world.gen.WorldGenFireDragonCave;
import com.github.alexthe666.iceandfire.world.gen.WorldGenIceDragonCave;
import com.github.alexthe666.iceandfire.world.gen.WorldGenLightningDragonCave;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.registry.level.entity.trade.TradeRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.*;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.player.AttackEntityEvent;
import io.github.fabricators_of_create.porting_lib.event.common.BlockEvents;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServerEvents {

    public static final UUID ALEX_UUID = UUID.fromString("71363abe-fd03-49c9-940d-aae8b8209b7c");
    // FIXME :: No check for shouldFear()?
    private static final Predicate<LivingEntity> VILLAGER_FEAR = entity -> entity instanceof IVillagerFear;
    private final Random rand = new Random();

    private static void signalChickenAlarm(LivingEntity chicken, LivingEntity attacker) {
        final float d0 = IafConfig.cockatriceChickenSearchLength;
        final List<EntityCockatrice> list = chicken.level().getEntitiesOfClass(EntityCockatrice.class, (new AABB(chicken.getX(), chicken.getY(), chicken.getZ(), chicken.getX() + 1.0D, chicken.getY() + 1.0D, chicken.getZ() + 1.0D)).inflate(d0, 10.0D, d0));
        if (list.isEmpty()) return;

        for (final EntityCockatrice cockatrice : list) {
            if (!(attacker instanceof EntityCockatrice)) {
                if (!DragonUtils.hasSameOwner(cockatrice, attacker)) {
                    if (attacker instanceof Player player) {
                        if (!player.isCreative() && !cockatrice.isOwnedBy(player)) {
                            cockatrice.setTarget(player);
                        }
                    } else {
                        cockatrice.setTarget(attacker);
                    }
                }
            }
        }
    }

    private static void signalAmphithereAlarm(LivingEntity villager, LivingEntity attacker) {
        final float d0 = IafConfig.amphithereVillagerSearchLength;
        final List<EntityAmphithere> list = villager.level().getEntitiesOfClass(EntityAmphithere.class, (new AABB(villager.getX() - 1.0D, villager.getY() - 1.0D, villager.getZ() - 1.0D, villager.getX() + 1.0D, villager.getY() + 1.0D, villager.getZ() + 1.0D)).inflate(d0, d0, d0));
        if (list.isEmpty()) return;

        for (final Entity entity : list) {
            if (entity instanceof EntityAmphithere amphithere && !(attacker instanceof EntityAmphithere)) {
                if (!DragonUtils.hasSameOwner(amphithere, attacker)) {
                    if (attacker instanceof Player player) {
                        if (!player.isCreative() && !amphithere.isOwnedBy(player)) {
                            amphithere.setTarget(player);
                        }
                    } else {
                        amphithere.setTarget(attacker);
                    }
                }
            }
        }
    }

    private static boolean isInEntityTag(ResourceLocation loc, EntityType<?> type) {
        return type.is(TagKey.create(Registries.ENTITY_TYPE, loc));
    }

    public static boolean isLivestock(Entity entity) {
        return entity != null && isInEntityTag(IafTagRegistry.FEAR_DRAGONS, entity.getType());
    }

    public static boolean isVillager(Entity entity) {
        return entity != null && isInEntityTag(IafTagRegistry.VILLAGERS, entity.getType());
    }

    public static boolean isSheep(Entity entity) {
        return entity != null && isInEntityTag(IafTagRegistry.SHEEP, entity.getType());
    }

    public static boolean isChicken(Entity entity) {
        return entity != null && isInEntityTag(IafTagRegistry.CHICKENS, entity.getType());
    }

    public static boolean isCockatriceTarget(Entity entity) {
        return entity != null && isInEntityTag(IafTagRegistry.COCKATRICE_TARGETS, entity.getType());
    }

    public static boolean doesScareCockatrice(Entity entity) {
        return entity != null && isInEntityTag(IafTagRegistry.SCARES_COCKATRICES, entity.getType());
    }

    public static boolean isBlindMob(Entity entity) {
        return entity != null && isInEntityTag(IafTagRegistry.BLINDED, entity.getType());
    }

    public static boolean isRidingOrBeingRiddenBy(final Entity first, final Entity entityIn) {
        if (first == null || entityIn == null) {
            return false;
        }

        for (final Entity entity : first.getPassengers()) {
            if (entity.equals(entityIn) || isRidingOrBeingRiddenBy(entity, entityIn)) {
                return true;
            }
        }

        return false;
    }

    public ServerEvents() {
        ProjectileImpactEvent.PROJECTILE_IMPACT.register(this::onArrowCollide);
        ServerLifecycleEvents.SERVER_STARTING.register(ServerEvents::addNewVillageBuilding);
        AttackEntityEvent.ATTACK_ENTITY.register(this::onPlayerAttackMob);
        AttackEntityEvent.ATTACK_ENTITY.register(this::onPlayerAttack);
        LivingEntityEvents.FALL.register(this::onEntityFall);
        LivingHurtEvent.HURT.register(this::onEntityDamage);
        LivingEntityEvents.DROPS.register((target, source, drops, lootingLevel, recentlyHit) -> {
            makeItemDropsFireImmune(source, drops);
            onEntityDrop(target, drops);
            return false;
        });
        LivingAttackEvent.ATTACK.register(this::onLivingAttacked);
        LivingEntityEvents.CHANGE_TARGET.register(this::onLivingSetTarget);
        LivingDeathEvent.DEATH.register(this::onEntityDie);
        InteractionEvent.RIGHT_CLICK_ITEM.register((player, hand) -> {
            onEntityUseItem(player, hand);
            return CompoundEventResult.pass();
        });
        LivingEntityEvents.LivingTickEvent.TICK.register(this::onEntityUpdate);
        EntityInteractCallback.EVENT.register((player, hand, target) -> {
            return onEntityInteract(player, hand, target);
        });

        InteractionEvent.RIGHT_CLICK_BLOCK.register((player, hand, pos, face) -> {
            onPlayerRightClick(player, pos);
            return EventResult.pass();
        });

        BlockEvents.BreakEvent.BLOCK_BREAK.register(this::onBreakBlock);

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            onPlayerLeaveEvent(handler.getPlayer());
        });

        EntityEvents.ON_JOIN_WORLD.register((entity, world, loadedFromDisk) -> {
            onEntityJoinWorld(entity);
            return true;
        });

        onVillagerTrades();

        EntityStruckByLightningEvent.ENTITY_STRUCK_BY_LIGHTING.register(this::onLightningHit);
    }

    static {
        PlayerInteractionEvents.LEFT_CLICK_EMPTY.register(ServerEvents::onPlayerLeftClick);

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            onChestGenerated(id, tableBuilder);
        });
    }

    public void onArrowCollide(final ProjectileImpactEvent event) {
        if (event.getRayTraceResult() instanceof EntityHitResult result) {
            Entity shotEntity = result.getEntity();

            if (shotEntity instanceof EntityGhost) {
                event.setCanceled(true);
            } else if (event.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() != null) {
                Entity shootingEntity = arrow.getOwner();

                if (shootingEntity instanceof LivingEntity && isRidingOrBeingRiddenBy(shootingEntity, shotEntity)) {
                    if (shotEntity instanceof TamableAnimal tamable && tamable.isTame() && shotEntity.isAlliedTo(shootingEntity)) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    private static final String[] VILLAGE_TYPES = new String[]{"plains", "desert", "snowy", "savanna", "taiga"};

    public static void addNewVillageBuilding(final MinecraftServer server) {
        if (IafConfig.villagerHouseWeight > 0) {
            Registry<StructureTemplatePool> templatePoolRegistry = server.registryAccess().registry(Registries.TEMPLATE_POOL).orElseThrow();
            Registry<StructureProcessorList> processorListRegistry = server.registryAccess().registry(Registries.PROCESSOR_LIST).orElseThrow();
            for (String type : VILLAGE_TYPES) {
                IafVillagerRegistry.addBuildingToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("village/" + type + "/houses"), "iceandfire:village/" + type + "_scriber_1", IafConfig.villagerHouseWeight);
            }
        }

    }

    public void onPlayerAttackMob(AttackEntityEvent event) {
        if (event.getTarget() instanceof EntityMutlipartPart && event.getEntity() instanceof Player) {
            event.setCanceled(true);
            Entity parent = ((EntityMutlipartPart) event.getTarget()).getParent();
            try {
                //If the attacked entity is the parent itself parent will be null and also doesn't have to be attacked
                if (parent != null)
                    ((Player) event.getEntity()).attack(parent);
            } catch (Exception e) {
                IceAndFire.LOGGER.warn("Exception thrown while interacting with entity.", e);
            }
            int extraData = 0;
            if (event.getTarget() instanceof EntityHydraHead && parent instanceof EntityHydra) {
                extraData = ((EntityHydraHead) event.getTarget()).headIndex;
                ((EntityHydra) parent).triggerHeadFlags(extraData);
            }
            if (event.getTarget().level().isClientSide && parent != null) {
                IceAndFire.NETWORK_WRAPPER.sendToServer(new MessagePlayerHitMultipart(parent.getId(), extraData));
            }
        }
    }

    public void onEntityFall(final LivingEntityEvents.Fall.FallEvent event) {
        if (event.getEntity() instanceof Player) {
            EntityDataProvider.getCapability(event.getEntity()).ifPresent(data -> {
                if (data.miscData.hasDismounted) {
                    event.setDamageMultiplier(0);
                    data.miscData.setDismounted(false);
                }
            });
        }
    }


    public void onEntityDamage(LivingHurtEvent event) {
        if (event.getSource().is(DamageTypeTags.IS_PROJECTILE)) {
            float multi = 1;
            if (event.getEntity().getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ItemTrollArmor) {
                multi -= 0.1f;
            }
            if (event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ItemTrollArmor) {
                multi -= 0.3f;
            }
            if (event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ItemTrollArmor) {
                multi -= 0.2f;
            }
            if (event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ItemTrollArmor) {
                multi -= 0.1f;
            }
            event.setAmount(event.getAmount() * multi);
        }
        if (event.getSource().is(IafDamageRegistry.DRAGON_FIRE_TYPE) || event.getSource().is(IafDamageRegistry.DRAGON_ICE_TYPE) || event.getSource().is(IafDamageRegistry.DRAGON_LIGHTNING_TYPE)) {
            float multi = 1;
            if (event.getEntity().getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ItemScaleArmor ||
                    event.getEntity().getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ItemDragonsteelArmor) {
                multi -= 0.1f;
            }
            if (event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ItemScaleArmor ||
                    event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ItemDragonsteelArmor) {
                multi -= 0.3f;
            }
            if (event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ItemScaleArmor ||
                    event.getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ItemDragonsteelArmor) {
                multi -= 0.2f;
            }
            if (event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ItemScaleArmor ||
                    event.getEntity().getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ItemDragonsteelArmor) {
                multi -= 0.1f;
            }
            event.setAmount(event.getAmount() * multi);
        }
    }

    public void onEntityDrop(LivingEntity entity, Collection<ItemEntity> drops) {
        if (entity instanceof WitherSkeleton) {
           drops.add(new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(),
                    new ItemStack(IafItemRegistry.WITHERBONE.get(), entity.getRandom().nextInt(2))));
        }
    }

    public void makeItemDropsFireImmune(DamageSource source, Collection<ItemEntity> drops) {
        boolean makeFireImmune = false;

        if (source.getDirectEntity() instanceof LightningBolt bolt && bolt.getTags().contains(BOLT_DONT_DESTROY_LOOT)) {
            makeFireImmune = true;
        } else if (source.getEntity() instanceof Player player && player.getItemInHand(player.getUsedItemHand()).is(IafItemTags.MAKE_ITEM_DROPS_FIREIMMUNE)) {
            makeFireImmune = true;
        }

        if (makeFireImmune) {
            Set<ItemEntity> fireImmuneDrops = drops.stream().map(itemEntity -> new ItemEntity(itemEntity.level(), itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), itemEntity.getItem()) {
                @Override
                public boolean fireImmune() {
                    return true;
                }
            }).collect(Collectors.toSet());

            drops.clear();
            drops.addAll(fireImmuneDrops);
        }
    }

    public void onLivingAttacked(final LivingAttackEvent event) {
        if (event.getSource() != null && event.getSource().getEntity() != null) {
            Entity attacker = event.getSource().getEntity();

            if (attacker instanceof LivingEntity) {
                EntityDataProvider.getCapability(attacker).ifPresent(data -> {
                    if (data.miscData.loveTicks > 0) {
                        event.setCanceled(true);
                    }
                });

                if (isChicken(event.getEntity())) {
                    signalChickenAlarm(event.getEntity(), (LivingEntity) attacker);
                } else if (DragonUtils.isVillager(event.getEntity())) {
                    signalAmphithereAlarm(event.getEntity(), (LivingEntity) attacker);
                }
            }
        }

    }

    public void onLivingSetTarget(LivingEntityEvents.ChangeTarget.ChangeTargetEvent event) {
        final LivingEntity target = event.getOriginalTarget();
        if (target != null) {
            final Entity entity = event.getEntity();

            if (!(entity instanceof LivingEntity attacker))
                return;

            if (isChicken(target)) {
                signalChickenAlarm(target, attacker);
            } else if (DragonUtils.isVillager(target)) {
                signalAmphithereAlarm(target, attacker);
            }
        }
    }

    public void onPlayerAttack(final AttackEntityEvent event) {
        if (event.getTarget() != null && isSheep(event.getTarget())) {
            float dist = IafConfig.cyclopesSheepSearchLength;
            final List<Entity> list = event.getTarget().level().getEntities(event.getEntity(), event.getEntity().getBoundingBox().expandTowards(dist, dist, dist));
            if (!list.isEmpty()) {
                for (final Entity entity : list) {
                    if (entity instanceof EntityCyclops cyclops) {
                        if (!cyclops.isBlinded() && !event.getEntity().isCreative()) {
                            cyclops.setTarget(event.getEntity());
                        }
                    }
                }
            }
        }

        if (event.getTarget() instanceof EntityStoneStatue statue) {
            statue.setHealth(statue.getMaxHealth());

            if (event.getEntity() != null) {
                ItemStack stack = event.getEntity().getMainHandItem();
                event.getTarget().playSound(SoundEvents.STONE_BREAK, 2, 0.5F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.5F);

                if (stack.getItem().isCorrectToolForDrops(Blocks.STONE.defaultBlockState()) || stack.getItem().getDescriptionId().contains("pickaxe")) {
                    event.setCanceled(true);
                    statue.setCrackAmount(statue.getCrackAmount() + 1);

                    if (statue.getCrackAmount() > 9) {
                        CompoundTag writtenTag = new CompoundTag();
                        event.getTarget().saveWithoutId(writtenTag);
                        event.getTarget().playSound(SoundEvents.STONE_BREAK, 2, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.5F);
                        event.getTarget().remove(Entity.RemovalReason.KILLED);

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0) {
                            ItemStack statuette = new ItemStack(IafItemRegistry.STONE_STATUE.get());
                            CompoundTag tag = statuette.getOrCreateTag();
                            tag.putBoolean("IAFStoneStatuePlayerEntity", statue.getTrappedEntityTypeString().equalsIgnoreCase("minecraft:player"));
                            tag.putString("IAFStoneStatueEntityID", statue.getTrappedEntityTypeString());
                            tag.put("IAFStoneStatueNBT", writtenTag);
                            statue.addAdditionalSaveData(tag);

                            if (!statue.level().isClientSide()) {
                                statue.spawnAtLocation(statuette, 1);
                            }
                        } else {
                            if (!statue.level().isClientSide) {
                                statue.spawnAtLocation(Blocks.COBBLESTONE.asItem(), 2 + event.getEntity().getRandom().nextInt(4));
                            }
                        }

                        statue.remove(Entity.RemovalReason.KILLED);
                    }
                }
            }
        }
    }

    public void onEntityDie(LivingDeathEvent event) {
        EntityDataProvider.getCapability(event.getEntity()).ifPresent(data -> {
            if (event.getEntity().level().isClientSide()) {
                return;
            }

            if (!data.chainData.getChainedTo().isEmpty()) {
                ItemEntity entityitem = new ItemEntity(event.getEntity().level(),
                        event.getEntity().getX(),
                        event.getEntity().getY() + 1,
                        event.getEntity().getZ(),
                        new ItemStack(IafItemRegistry.CHAIN.get(), data.chainData.getChainedTo().size()));
                entityitem.setDefaultPickUpDelay();
                event.getEntity().level().addFreshEntity(entityitem);

                data.chainData.clearChains();
            }
        });

        if (event.getEntity().getUUID().equals(ServerEvents.ALEX_UUID)) {
            event.getEntity().spawnAtLocation(new ItemStack(IafItemRegistry.WEEZER_BLUE_ALBUM.get()), 1);
        }

        if (event.getEntity() instanceof Player && IafConfig.ghostsFromPlayerDeaths) {
            Entity attacker = event.getEntity().getLastHurtByMob();
            if (attacker instanceof Player && event.getEntity().getRandom().nextInt(3) == 0) {
                CombatTracker combat = event.getEntity().getCombatTracker();
                CombatEntry entry = combat.getMostSignificantFall();
                boolean flag = entry != null && (entry.source().is(DamageTypes.FALL) || entry.source().is(DamageTypes.DROWN) || entry.source().is(DamageTypes.LAVA));
                if (event.getEntity().hasEffect(MobEffects.POISON)) {
                    flag = true;
                }
                if (flag) {
                    Level world = event.getEntity().level();
                    EntityGhost ghost = IafEntityRegistry.GHOST.get().create(world);
                    ghost.copyPosition(event.getEntity());
                    if (!world.isClientSide) {
                        ghost.finalizeSpawn((ServerLevelAccessor) world, world.getCurrentDifficultyAt(event.getEntity().blockPosition()), MobSpawnType.SPAWNER, null, null);
                        world.addFreshEntity(ghost);
                    }
                    ghost.setDaytimeMode(true);
                }
            }
        }
    }

    public static int onEntityStopUsingItem(ItemStack stack, int duration) {
        if (stack.getItem() instanceof ItemDeathwormGauntlet || stack.getItem() instanceof ItemCockatriceScepter) {
            return 20;
        }

        return duration;
    }

    public void onEntityUseItem(Player player, InteractionHand hand) {
        if (player != null && player.getXRot() > 87 && player.getVehicle() != null && player.getVehicle() instanceof EntityDragonBase) {
            ((EntityDragonBase) player.getVehicle()).mobInteract(player, hand);
        }
/*        if (event.getEntity() instanceof EntityDragonBase && !event.getEntity().isAlive()) {
            event.setResult(Event.Result.DENY);
            ((EntityDragonBase) event.getEntityLiving()).mobInteract(event.getPlayer(), event.getHand());
        }*/
    }

    public void onEntityUpdate(LivingEntityEvents.LivingTickEvent event) {
        if (AiDebug.isEnabled() && event.getEntity() instanceof Mob && AiDebug.contains((Mob) event.getEntity())) {
            AiDebug.logData();
        }
    }

    public InteractionResult onEntityInteract(Player player, InteractionHand hand, Entity targetEntity) {
        AtomicReference<InteractionResult> result = new AtomicReference<>(InteractionResult.PASS);

        // Handle chain removal
        if (targetEntity instanceof LivingEntity target) {
            EntityDataProvider.getCapability(target).ifPresent(data -> {
                if (data.chainData.isChainedTo(player)) {
                    data.chainData.removeChain(player);

                    if (!player.level().isClientSide()) {
                        target.spawnAtLocation(IafItemRegistry.CHAIN.get(), 1);
                    }

                    result.set(InteractionResult.SUCCESS);
                }
            });
        }

        // Handle debug path render
        if (!player.level().isClientSide() && targetEntity instanceof Mob && player.getItemInHand(hand).getItem() == Items.STICK) {
            if (AiDebug.isEnabled())
                AiDebug.addEntity((Mob) targetEntity);
            if (Pathfinding.isDebug()) {
                if (AbstractPathJob.trackingMap.getOrDefault(player, UUID.randomUUID()).equals(targetEntity.getUUID())) {
                    AbstractPathJob.trackingMap.remove(player);
                    IceAndFire.sendMSGToPlayer(new MessageSyncPath(new HashSet<>(), new HashSet<>(), new HashSet<>()), (ServerPlayer) player);
                } else {
                    AbstractPathJob.trackingMap.put(player, targetEntity.getUUID());
                }
            }
        }

        return result.get();
    }

    // TODO :: Can this be moved into the item itself?
    public static void onPlayerLeftClick(PlayerInteractionEvents.LeftClickEmpty event) {
        onLeftClick(event.getEntity(), event.getItemStack());
        if (event.getLevel().isClientSide) {
            IceAndFire.sendMSGToServer(new MessageSwingArm());
        }
    }

    public static void onLeftClick(final Player playerEntity, final ItemStack stack) {
        if (stack.getItem() == IafItemRegistry.GHOST_SWORD.get()) {
            ItemGhostSword.spawnGhostSwordEntity(stack, playerEntity);
        }
    }

    public void onPlayerRightClick(Player player, BlockPos pos) {
        if (player != null && (player.level().getBlockState(pos).getBlock() instanceof AbstractChestBlock) && !player.isCreative()) {
            float dist = IafConfig.dragonGoldSearchLength;
            final List<Entity> list = player.level().getEntities(player, player.getBoundingBox().inflate(dist, dist, dist));
            if (!list.isEmpty()) {
                for (final Entity entity : list) {
                    if (entity instanceof EntityDragonBase dragon) {
                        if (!dragon.isTame() && !dragon.isModelDead() && !dragon.isOwnedBy(player)) {
                            dragon.setInSittingPose(false);
                            dragon.setOrderedToSit(false);
                            dragon.setTarget(player);
                        }
                    }
                }
            }
        }
        if (player.level().getBlockState(pos).getBlock() instanceof WallBlock) {
            ItemChain.attachToFence(player, player.level(), pos);
        }
    }

    public void onBreakBlock(BlockEvents.BreakEvent event) {
        if (event.getPlayer() != null && (event.getState().getBlock() instanceof AbstractChestBlock || event.getState().getBlock() == IafBlockRegistry.GOLD_PILE.get() || event.getState().getBlock() == IafBlockRegistry.SILVER_PILE.get() || event.getState().getBlock() == IafBlockRegistry.COPPER_PILE.get())) {
            final float dist = IafConfig.dragonGoldSearchLength;
            List<Entity> list = event.getLevel().getEntities(event.getPlayer(), event.getPlayer().getBoundingBox().inflate(dist, dist, dist));
            if (list.isEmpty()) return;

            for (Entity entity : list) {
                if (entity instanceof EntityDragonBase dragon) {
                    if (!dragon.isTame() && !dragon.isModelDead() && !dragon.isOwnedBy(event.getPlayer()) && !event.getPlayer().isCreative()) {
                        dragon.setInSittingPose(false);
                        dragon.setOrderedToSit(false);
                        dragon.setTarget(event.getPlayer());
                    }
                }
            }
        }
    }

    //@SubscribeEvent // FIXME :: Unused
    public static void onChestGenerated(ResourceLocation eventName, LootTable.Builder tableBuilder) {
        final boolean condition1 = eventName.equals(BuiltInLootTables.SIMPLE_DUNGEON)
                || eventName.equals(BuiltInLootTables.ABANDONED_MINESHAFT)
                || eventName.equals(BuiltInLootTables.DESERT_PYRAMID)
                || eventName.equals(BuiltInLootTables.JUNGLE_TEMPLE)
                || eventName.equals(BuiltInLootTables.STRONGHOLD_CORRIDOR)
                || eventName.equals(BuiltInLootTables.STRONGHOLD_CROSSING);

        if (condition1 || eventName.equals(BuiltInLootTables.VILLAGE_CARTOGRAPHER)) {
            LootPoolEntryContainer.Builder item = LootItem.lootTableItem(IafItemRegistry.MANUSCRIPT.get()).setQuality(20).setWeight(5);
            LootPool.Builder builder = new LootPool.Builder().name("iaf_manuscript").add(item).when(LootItemRandomChanceCondition.randomChance(0.35f)).setRolls(UniformGenerator.between(1, 4)).setBonusRolls(UniformGenerator.between(0, 3));
            tableBuilder.withPool(builder);
        }
        if (condition1
                || eventName.equals(BuiltInLootTables.IGLOO_CHEST)
                || eventName.equals(BuiltInLootTables.WOODLAND_MANSION)
                || eventName.equals(BuiltInLootTables.VILLAGE_TOOLSMITH)
                || eventName.equals(BuiltInLootTables.VILLAGE_ARMORER)) {


            LootPoolEntryContainer.Builder item = LootItem.lootTableItem(IafItemRegistry.SILVER_INGOT.get()).setQuality(15).setWeight(12);
            LootPool.Builder builder = new LootPool.Builder().name("iaf_silver_ingot").add(item).when(LootItemRandomChanceCondition.randomChance(0.5f)).setRolls(UniformGenerator.between(1, 3)).setBonusRolls(UniformGenerator.between(0, 3));
            tableBuilder.withPool(builder);

        } else if ((eventName.equals(WorldGenFireDragonCave.FIRE_DRAGON_CHEST)
                || eventName.equals(WorldGenFireDragonCave.FIRE_DRAGON_CHEST_MALE)
                || eventName.equals(WorldGenIceDragonCave.ICE_DRAGON_CHEST)
                || eventName.equals(WorldGenIceDragonCave.ICE_DRAGON_CHEST_MALE)
                || eventName.equals(WorldGenLightningDragonCave.LIGHTNING_DRAGON_CHEST)
                || eventName.equals(WorldGenLightningDragonCave.LIGHTNING_DRAGON_CHEST_MALE))) {
            LootPoolEntryContainer.Builder item = LootItem.lootTableItem(IafItemRegistry.WEEZER_BLUE_ALBUM.get()).setQuality(100).setWeight(1);
            LootPool.Builder builder = new LootPool.Builder().name("iaf_weezer").add(item).when(LootItemRandomChanceCondition.randomChance(0.01f)).setRolls(UniformGenerator.between(1, 1));
            tableBuilder.withPool(builder);
        }
    }

    public void onPlayerLeaveEvent(ServerPlayer player) {
        if (player != null && !player.getPassengers().isEmpty()) {
            for (Entity entity : player.getPassengers()) {
                entity.stopRiding();
            }
        }
    }

    public void onEntityJoinWorld(Entity ent) {
        if (!(ent instanceof Mob mob))
            return;

        try {
            if (isSheep(mob) && mob instanceof Animal animal) {
                animal.goalSelector.addGoal(8, new EntitySheepAIFollowCyclops(animal, 1.2D));
            }
            if (isVillager(mob) && IafConfig.villagersFearDragons) {
                mob.goalSelector.addGoal(1, new VillagerAIFearUntamed((PathfinderMob) mob, LivingEntity.class, 8.0F, 0.8D, 0.8D, VILLAGER_FEAR));
            }
            if (isLivestock(mob) && IafConfig.animalsFearDragons) {
                mob.goalSelector.addGoal(1, new VillagerAIFearUntamed((PathfinderMob) mob, LivingEntity.class, 30, 1.0D, 0.5D, entity -> entity instanceof IAnimalFear && ((IAnimalFear) entity).shouldAnimalsFear(mob)));
            }
        } catch (Exception e) {
            IceAndFire.LOGGER.warn("Tried to add unique behaviors to vanilla mobs and encountered an error");
        }
    }

    public void onVillagerTrades() {
        var trades = new Int2ObjectOpenHashMap<List<VillagerTrades.ItemListing>>();
        for (int i = 1; i <= 5; i++) {
            trades.put(i, new LinkedList<>());
        }

        IafVillagerRegistry.addScribeTrades(trades);

        trades.forEach((level, listing) -> {
            TradeRegistry.registerVillagerTrade(IafVillagerRegistry.SCRIBE.get(), level, listing.toArray(new VillagerTrades.ItemListing[0]));
        });
    }

    public static String BOLT_DONT_DESTROY_LOOT = "iceandfire.bolt_skip_loot";

    public void onLightningHit(final EntityStruckByLightningEvent event) {
        if ((event.getEntity() instanceof ItemEntity || event.getEntity() instanceof ExperienceOrb) && event.getLightning().getTags().contains(BOLT_DONT_DESTROY_LOOT)) {
            event.setCanceled(true);
        } else if (event.getLightning().getTags().contains(event.getEntity().getStringUUID())) {
            event.setCanceled(true);
        }
    }
}
