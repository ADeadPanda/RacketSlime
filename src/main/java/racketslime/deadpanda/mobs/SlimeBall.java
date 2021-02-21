package racketslime.deadpanda.mobs;

import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntitySlime;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import racketslime.deadpanda.Main;
import racketslime.deadpanda.utils.color;

//Creates The "Ball" entity
public class SlimeBall extends EntitySlime {
    private Main plugin = Main.getInstance();
    private float health = 999999999;

    public SlimeBall(Location location) {
        super(EntityTypes.SLIME, ((CraftWorld) location.getWorld()).getHandle());
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setCustomName(new ChatComponentText(color.Set("&a&lSlimeBall")));
        this.setCustomNameVisible(true);
        this.getWorld().addEntity(this);
        this.setSize(4, true);
    }


    @Override
    protected void initPathfinder() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
    }

}
