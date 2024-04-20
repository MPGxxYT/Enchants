package me.mortaldev.enchants.modules.enchants.actions;

import com.destroystokyo.paper.ParticleBuilder;
import me.mortaldev.enchants.Main;
import me.mortaldev.enchants.utils.base.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;

import java.util.Set;

/**
 * The Explosive class represents an explosive object.
 * It can cause an explosion, break blocks in a certain radius, and create explosion particles and sounds.
 */
public class Explosive {
    private static final int STARTING_EXPLOSION_RADIUS = 1;

    private final Block block;
    private final int explosivenessLevel;

    public Explosive(Block block, int level) {
        this.block = block;
        this.explosivenessLevel = level + STARTING_EXPLOSION_RADIUS;
    }

    /**
     * Causes an explosive object to explode, breaking blocks in a certain radius and creating explosion particles and sounds.
     * This method should be called to initiate the explosion.
     */
    public void explode() {
        explodeRadius();
        showExplodeParticle();
        playExplodeSound();
    }

    private void explodeRadius() {
        Set<Block> blocks = Utils.sphereAround(block.getLocation(), explosivenessLevel);
        for (Block blockToBreak : blocks) {
            scheduleBlockBreak(blockToBreak);
        }
    }

    private void showExplodeParticle() {
        new ParticleBuilder(Particle.EXPLOSION_LARGE).location(block.getLocation().toCenterLocation()).spawn();
    }

    private void playExplodeSound() {
        float volume = (float) (0.2 * (explosivenessLevel - STARTING_EXPLOSION_RADIUS));
        block.getWorld().playSound(block.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, volume, 1);
    }

    private void scheduleBlockBreak(Block blockToBreak) {
        if (!blockToBreak.getType().equals(Material.BEDROCK)) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), blockToBreak::breakNaturally);
        }
    }
}
