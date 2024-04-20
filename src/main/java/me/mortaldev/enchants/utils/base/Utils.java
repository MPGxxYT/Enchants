package me.mortaldev.enchants.utils.base;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Utils {
    /**
     * Returns the given value clamped between the minimum and maximum values.
     *
     * @param value The value to be clamped.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return The clamped value. If the value is less than the minimum value, the minimum value is returned.
     * If the value is greater than the maximum value, the maximum value is returned. Otherwise, the value itself is returned.
     */
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }


    /**
     * Toggles a boolean value by flipping its state.
     *
     * @param b The boolean value to toggle.
     * @return The toggled boolean value.
     */
    public static Boolean toggle(Boolean b) {
        return !b;
    }

    /**
     * Checks if a player has any of the specified permissions.
     *
     * @param player The player to check permissions for.
     * @param permissions The permissions to check.
     * @return {@code true} if the player has any of the specified permissions, {@code false} otherwise.
     */
    public static boolean hasAnyPermission(Player player, String... permissions) {
        for (String permission : permissions) {
            if (player.hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Sorts a HashMap by the value of the Integer objects in ascending order.
     *
     * @param hashMap The HashMap to be sorted.
     * @return A new HashMap with the original values sorted by their Integer values in ascending order.
     */
    public static HashMap<Object, Integer> sortByValue(HashMap<Object, Integer> hashMap) {
        // Create a list from elements of HashMap
        List<Map.Entry<Object, Integer>> list = new LinkedList<>(hashMap.entrySet());

        // Sort the list
        list.sort(Map.Entry.comparingByValue());

        // put data from sorted list to hashmap
        HashMap<Object, Integer> linkedHashMap = new LinkedHashMap<>();
        for (Map.Entry<Object, Integer> entry : list) {
            linkedHashMap.put(entry.getKey(), entry.getValue());
        }
        return linkedHashMap;
    }


    /**
     * Sorts a HashMap<Object, AtomicInteger> by the value of the AtomicInteger objects in ascending order.
     *
     * @param hashMap The HashMap to be sorted.
     * @return A new HashMap<Object, AtomicInteger> with the original values sorted by their AtomicInteger values in ascending order.
     */
    public static HashMap<Object, AtomicInteger> sortByValueAtomic(HashMap<Object, AtomicInteger> hashMap) {
        HashMap<Object, Integer> copyMap = new HashMap<>();
        hashMap.forEach((key, value) -> copyMap.put(key, value.get()));

        // Create a list from elements of HashMap
        List<Map.Entry<Object, Integer>> list = new LinkedList<>(copyMap.entrySet());

        // Sort the list
        list.sort(Map.Entry.comparingByValue());

        // put data from sorted list to hashmap
        HashMap<Object, AtomicInteger> linkedHashMap = new LinkedHashMap<>();
        for (Map.Entry<Object, Integer> entry : list) {
            linkedHashMap.put(entry.getKey(), new AtomicInteger(entry.getValue()));
        }
        return linkedHashMap;
    }

    /**
     * Generates a set of Blocks in the shape of a cube around the given location with specified radius.
     *
     * @param location The center location of the cube
     * @param radius   The radius of the cube
     * @return A Set of Blocks representing the cube
     */
    public static Set<Block> cubicAround(Location location, int radius) {
        Set<Block> cube = new HashSet<>();
        Block center = location.getBlock();
        for(int x = -radius; x <= radius; x++) {
            for(int y = -radius; y <= radius; y++) {
                for(int z = -radius; z <= radius; z++) {
                    Block b = center.getRelative(x, y, z);
                    cube.add(b);
                }
            }
        }
        return cube;
    }

    /**
     * Generates a set of Blocks in the shape of a sphere around the given location with specified radius.
     *
     * @param location the center location of the sphere
     * @param radius   the radius of the sphere
     * @return a Set of Blocks representing the sphere
     */
    public static Set<Block> sphereAround(Location location, int radius) {
        Set<Block> sphere = Collections.newSetFromMap(new ConcurrentHashMap<>());
        Block center = location.getBlock();
        for(int x = -radius; x <= radius; x++) {
            for(int y = -radius; y <= radius; y++) {
                int zsq = radius * radius - x * x - y * y;
                if (zsq < 0) continue;
                int distSqrt = (int) Math.floor(Math.sqrt(zsq));
                for(int z = -distSqrt; z <= distSqrt; z++) {
                    Block b = center.getRelative(x, y, z);
                    if(center.getLocation().distance(b.getLocation()) < radius - 0.5) {
                        sphere.add(b);
                    }
                }
            }
        }
        return sphere;
    }

    /**
     * Compares a string with a list of strings, ignoring case.
     *
     * @param string   The string to compare.
     * @param strings  The list of strings to compare with.
     * @return         {@code true} if there is a string in the list that matches the given string (ignoring case), {@code false} otherwise.
     */
    public static Boolean stringsEqualsIgnoreCase(String string, ArrayList<String> strings){
        for (String s : strings) {
            if (string.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the sum of all elements in a list of Long values.
     *
     * @param longList The list of Long values to calculate the sum of.
     * @return The sum of all elements in the list.
     */
    public static Long sumOfList(List<Long> longList){
        return longList.stream().mapToLong(Long::longValue).sum();
    }

    /**
     * Determines whether a given location is within a defined area.
     *
     * @param loc  The location to check.
     * @param loc1 The first corner of the defined area.
     * @param loc2 The second corner of the defined area.
     * @return {@code true} if the location is within the defined area, {@code false} otherwise.
     */
    public static boolean locationIsWithin(Location loc, Location loc1, Location loc2) {
        double x1 = Math.min(loc1.getX(), loc2.getX());
        double y1 = Math.min(loc1.getY(), loc2.getY());
        double z1 = Math.min(loc1.getZ(), loc2.getZ());
        double x2 = Math.max(loc1.getX(), loc2.getX());
        double y2 = Math.max(loc1.getY(), loc2.getY());
        double z2 = Math.max(loc1.getZ(), loc2.getZ());
        Location l1 = new Location(loc1.getWorld(), x1, y1, z1);
        Location l2 = new Location(loc1.getWorld(), x2, y2, z2);
        return loc.getBlockX() >= l1.getBlockX() && loc.getBlockX() <= l2.getBlockX()
                && loc.getBlockY() >= l1.getBlockY() && loc.getBlockY() <= l2.getBlockY()
                && loc.getBlockZ() >= l1.getBlockZ() && loc.getBlockZ() <= l2.getBlockZ();
    }

    /**
     * Deletes a folder and all its content recursively.
     *
     * @param file The folder to be deleted.
     * @return true if the folder and all its content were successfully deleted, false otherwise.
     */
    public static boolean deleteFolder(File file) {
        try (Stream<Path> files = Files.walk(file.toPath())) {
            files.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            return true;
        } catch (IOException e) {
            Bukkit.getLogger().warning(e.getMessage());
            return false;
        }
    }

}