package studio.lothus.delivery.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemCreator {

    private ItemStack stack;
    private ItemMeta meta;

    private net.minecraft.server.v1_8_R3.ItemStack itemStack;

    public ItemCreator(Material material) {
        stack = new ItemStack(material);
        meta = stack.getItemMeta();
    }

    public ItemCreator(Material material, String display) {
        if (material == Material.SKULL_ITEM) {
            stack = new ItemStack(material, 0, (short)3);
        } else {
            stack = new ItemStack(material);
        }
        meta = stack.getItemMeta();
        meta.setDisplayName(display);
    }

    public ItemCreator addNBTTag(NBTTagCompound tagCompound) {
        itemStack = CraftItemStack.asNMSCopy(stack);
        itemStack.setTag(tagCompound);
        stack = CraftItemStack.asBukkitCopy(itemStack);
        return this;
    }

    public ItemCreator setDisplayName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemCreator setAmount(int amount) {
        stack.setAmount(amount);
        return this;
    }

    public ItemCreator setId(int id) {
        stack.setDurability((short) id);
        return this;
    }

    public ItemCreator setLore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemCreator setLore(List<String> lore) {
        meta.setLore(lore);
        return this;
    }

    public ItemCreator addEnchant(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemCreator addItemFlag(ItemFlag... flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemCreator withSkullOwner(String owner) {
        SkullMeta m = (SkullMeta) meta;
        m.setOwner(owner);
        meta = m;
        stack.setItemMeta(m);
        return this;
    }

    public ItemCreator withSkullURL(String url) {
        SkullMeta skullMeta = (SkullMeta) meta;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        stack.setItemMeta(skullMeta);
        return this;
    }

    public ItemCreator withSkinProperty(Property property) {
        SkullMeta skullMeta = (SkullMeta) meta;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", property);
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        stack.setItemMeta(skullMeta);
        return this;
    }
    public ItemStack build() {
        stack.setItemMeta(meta);
        return stack;
    }
}
