package cx.mia.samsara.api;

import cx.mia.samsara.Samsara;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public enum Sound {
    DEATH               ("samsara:death",               true,   SoundCategory.VOICE,    Duration.ofSeconds(104, 70794000)),
    DEATH_SHORT         ("samsara:death.short",         false,  SoundCategory.VOICE,    Duration.ofSeconds(4, 250000000)),
    GENESIS_BIRTH       ("samsara:genesis.birth",       true,   SoundCategory.VOICE,    Duration.ofSeconds(152)),
    GENESIS_EMERGENCE   ("samsara:genesis.emergence",   true,   SoundCategory.VOICE,    Duration.ofSeconds(216)),
    GENESIS_INCEPTION   ("samsara:genesis.inception",   true,   SoundCategory.VOICE,    Duration.ofSeconds(120)),
    GENESIS_INCEPTION2  ("samsara:genesis.inception2",  true,   SoundCategory.VOICE,    Duration.ofSeconds(152)),
    LIFE_ADULTHOOD      ("samsara:life.adulthood",      true,   SoundCategory.VOICE,    Duration.ofSeconds(57, 600000000)),
    LIFE_ELDER          ("samsara:life.elder",          true,   SoundCategory.VOICE,    Duration.ofSeconds(57, 600000000)),
    LIFE_INFANCY        ("samsara:life.infancy",        true,   SoundCategory.VOICE,    Duration.ofSeconds(60, 631587000)),
    LIFE_INFANCY2       ("samsara:life.infancy2",       true,   SoundCategory.VOICE,    Duration.ofSeconds(60, 631587000)),
    REGENERATION        ("samsara:regeneration",        true,   SoundCategory.VOICE,    Duration.ofSeconds(184));

    private final String sound;
    private final boolean loop;
    private final Duration duration;
    private final SoundCategory category;

    private static final HashMap<Player, HashMap<Sound, ArrayList<BukkitTask>>> loopers = new HashMap<>();

    Sound(String sound, boolean loop, SoundCategory category, Duration duration) {
        this.sound = sound;
        this.loop = loop;
        this.category = category;
        this.duration = duration;
    }

    public void playSound(Player player) {
        playSound(player, player.getLocation(), this, this.getCategory(), 1000f, 1f);
    }
    public void playSound(Player player, float volume) {
        playSound(player, player.getLocation(), this, this.getCategory(), volume, 1f);
    }
    public void playSound(Player player, Sound sound) {
        playSound(player, player.getLocation(), sound, sound.getCategory(), 1f, 1f);
    }
    public void playSound(Player player, Sound sound, float volume) {
        playSound(player, player.getLocation(), sound, sound.getCategory(), volume, 1f);
    }
    public void playSound(Player player, SoundCategory category) {
        playSound(player, player.getLocation(), this, category, 1f, 1f);
    }
    public void playSound(Player player, SoundCategory category, float volume) {
        playSound(player, player.getLocation(), this, category, volume, 1f);
    }
    public void playSound(Player player, Sound sound, SoundCategory category, float volume) {
        playSound(player, player.getLocation(), sound, category, volume, 1f);
    }
    public void playSound(Player player, Sound sound, SoundCategory category, float volume, float pitch) {
        playSound(player, player.getLocation(), sound, category, volume, pitch);
    }
    public void playSound(Player player, Location location, Sound sound, SoundCategory category, float volume, float pitch) {
        player.playSound(location, sound.getSound(), category, volume, pitch);
        Samsara.getInstance().getLogger().debug("Sound " + sound.getSound() + " played for " + player.getName());
        long durationSeconds = sound.getDuration().getSeconds() + sound.getDuration().getNano() / 1000000000;
        if (sound.getLoop()) {
            addTask(player, sound, Samsara.getInstance().getScheduler().delay(
                            durationSeconds*20,
                            new Runnable() {
                                public void run() {
                                    playSound(player, location, sound, category, volume, pitch);
                                }
                            }));
            Samsara.getInstance().getLogger().debug("Looping sound " + getSound() + " scheduled with a delay of " + durationSeconds + " seconds" );
        }
    }

    public void stopSound(Player player) {
        stopSound(player, this, getCategory());
    }
    public void stopSound(Player player, Sound sound) {
        stopSound(player, sound, sound.getCategory());
    }
    public void stopSound(Player player, SoundCategory category) {
        stopSound(player, this, category);
    }
    public void stopSound(Player player, Sound sound, SoundCategory category) {
        player.stopSound(sound.getSound(), category);
        Samsara.getInstance().getLogger().debug("Sound " + sound.getSound() + " stopped for " + player.getName());

        if (getLoopers().get(player).containsKey(sound)) {
            getLoopers().get(player).get(sound).forEach(BukkitTask::cancel);
            removeTasks(player, sound);
        }
    }

    public String getSound() {
        return this.sound;
    }

    public boolean getLoop() {
        return this.loop;
    }

    private SoundCategory getCategory() {
        return this.category;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public static HashMap<Player, HashMap<Sound, ArrayList<BukkitTask>>> getLoopers() {
        return Sound.loopers;
    }

    public static void addTask(Player player, Sound sound, BukkitTask task) {
        if (getLoopers().get(player).containsKey(sound)) {
            getLoopers().get(player).get(sound).add(task);
        } else {
            getLoopers().get(player).put(sound, new ArrayList<>(Arrays.asList(task)));
        }
    }

    public static void removeTasks(Player player, Sound sound) {
        getLoopers().get(player).remove(sound);
    }
}