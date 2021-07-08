package net.skyhcf.hub.armor;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.DyeColor;

@Data
@AllArgsConstructor
public class Armor {

    private String id;
    private String displayName;
    private DyeColor color;
    private boolean permissionRequired;

}
