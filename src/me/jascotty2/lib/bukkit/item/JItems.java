/**
 * Copyright (C) 2011 Jacob Scott <jascottytechie@gmail.com>
 * Description: enum to define items & information about them
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.jascotty2.lib.bukkit.item;

import me.jascotty2.lib.io.CheckInput;
import java.util.Arrays;
import java.util.LinkedList;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

/**
 * @author jacob
 */
public enum JItems {

	AIR("Air", 0, false),
	STONE("Stone", 1, "4@8+263=8, 4@3+5@2=3"),
	GRASS("Grass", 2, false),
	DIRT("Dirt", 3),
	COBBLESTONE("Cobblestone", 4),
	WOOD("Wood", 5, "17=4"),
	SPRUCE_WOOD("Spruce Wood", 5, (short) 1, "17:1=4"),
	BIRCH_WOOD("Birch Wood", 5, (short) 2, "17:2=4"),
	JUNGLE_WOOD("Jungle Wood", 5, (short) 3, "17:3=4"),
	SAPLING("Sapling", 6, (short) 0),
	SPRUCE_SAPLING("Spruce Sapling", 6, (short) 1),
	PINE_SAPLING("Pine Sapling", 6, (short) 2),
	JUNGLE_SAPLING("Jungle Sapling", 6, (short) 3),
	BEDROCK("Bedrock", 7, false),
	WATER("Water", 8, false),
	STATIONARY_WATER("Stationary Water", 9, false),
	LAVA("Lava", 10, false),
	STATIONARY_LAVA("Stationary Lava", 11, false),
	SAND("Sand", 12),
	GRAVEL("Gravel", 13),
	GOLD_ORE("Gold Ore", 14),
	IRON_ORE("Iron Ore", 15),
	COAL_ORE("Coal Ore", 16, false),
	LOG("Log", 17, (short) 0),
	SPRUCE_LOG("Spruce Log", 17, (short) 1),
	BIRCH_LOG("Birch Log", 17, (short) 2),
	JUNGLE_LOG("Jungle Log", 17, (short) 3),
	LEAVES("Leaves", 18, (short) 0),
	SPRUCE_LEAVES("Spruce Leaves", 18, (short) 1),
	BIRCH_LEAVES("Birch Leaves", 18, (short) 2),
	JUNGLE_LEAVES("Jungle Leaves", 18, (short) 3),
	SPONGE("Sponge", 19, false),
	GLASS("Glass", 20, "12@8+263=8, 12@3+5@2=3, 12@12+369=12"),
	LAPIS_ORE("Lapis Lazuli Ore", 21, false),
	LAPIS_BLOCK("Lapis Lazuli Block", 22, "351:11@9"),
	DISPENSER("Dispenser", 23, "4@7+331+261"),
	SANDSTONE("Sandstone", 24, "12@4"),
	CHISELED_SANDSTONE("Chiseled Sandstone", 24, (short) 1),
	SMOOTH_SANDSTONE("Smooth Sandstone", 24, (short) 1, "24@4"),
	NOTE_BLOCK("Note Block", 25, "5@8+331"),
	BED_BLOCK("Bed Block", 26, false),
	POWERED_RAIL("Powered Rail", 27, "266@6+331+280=6"),
	DETECTOR_RAIL("Detector Rail", 28, "265@6+331+70=6"),
	STICKY_PISTON_BASE("Sticky Piston", 29, "33+341"),
	COBWB("Cobweb", 30, false),
	DEAD_FERN("Dead Fern", 31, (short) 0), // non-natural dead shrub
	TALL_GRASS("Tall Grass", 31, (short) 1),
	FERN("Fern", 31, (short) 2, false), // or "Living Shrub"
	DEAD_SHRUB("Dead Shrub", 32, false),
	PISTON_BASE("Piston", 33, "5@3+4@4+265+331"),
	PISTON_EXTENSION("Piston Extension", 34, false), // non-placable item
	WOOL("White Wool", 35, (short) 0, "287@9"),
	ORANGE_WOOL("Orange Wool", 35, (short) 1, "35+351:1"),
	MAGENTA_WOOL("Magenta Wool", 35, (short) 2, "35+351:2"),
	LIGHT_BLUE_WOOL("Light Blue Wool", 35, (short) 3, "35+351:3"),
	YELLOW_WOOL("Yellow Wool", 35, (short) 4, "35+351:4"),
	LIGHT_GREEN_WOOL("Light Green Wool", 35, (short) 5, "35+351:5"),
	PINK_WOOL("Pink Wool", 35, (short) 6, "35+351:6"),
	DARK_GRAY_WOOL("Dark Gray Wool", 35, (short) 7, "35+351:7"),
	LIGHT_GRAY_WOOL("Light Gray Wool", 35, (short) 8, "35+351:8"),
	CYAN_WOOL("Cyan Wool", 35, (short) 9, "35+351:9"),
	PURPLE_WOOL("Purple Wool", 35, (short) 10, "35+351:10"),
	BLUE_WOOL("Blue Wool", 35, (short) 11, "35+351:11"),
	BROWN_WOOL("Brown Wool", 35, (short) 12, "35+351:12"),
	DARK_GREEN_WOOL("Dark Green Wool", 35, (short) 13, "35+351:13"),
	RED_WOOL("Red Wool", 35, (short) 14, "35+351:14"),
	BLACK_WOOL("Black Wool", 35, (short) 15, "35+351:15"),
	PISTON_MOVING_PIECE("Piston Moving Piece", 36, false), // non-placable item
	YELLOW_FLOWER("Yellow Flower", 37),
	RED_ROSE("Red Rose", 38),
	BROWN_MUSHROOM("Brown Mushroom", 39),
	RED_MUSHROOM("Red Mushroom", 40),
	GOLD_BLOCK("Gold Block", 41, "266@9"),
	IRON_BLOCK("Iron Block", 42, "265@9"),
	DOUBLE_STEP("Double Slab", 43, (short) 0, "44@2", false),
	SANDSTONE_DOUBLE_STEP("Sandstone Double Slab", 43, (short) 1, "44:1@2", false),
	WOOD_DOUBLE_STEP("Wood Double Slab", 43, (short) 2, "44:2@2", false),
	COBBLESTONE_DOUBLE_STEP("Cobblestone Double Slab", 43, (short) 3, "44:3@2", false),
	BRICK_DOUBLE_STEP("Brick Double Slab", 43, (short) 4, "44:4@2", false),
	STONE_BRICK_DOUBLE_STEP("Stone Brick Double Slab", 43, (short) 5, "44:5@2", false),
	STEP("Slab", 44, (short) 0, "1@3=3"),
	SANDSTONE_STEP("Sandstone Slab", 44, (short) 1, "24@3=3"),
	WOOD_STEP("Wood Slab", 44, (short) 2, "5@3=3"),
	COBBLESTONE_STEP("Cobblestone Slab", 44, (short) 3, "4@3=3"),
	BRICK_STEP("Brick Slab", 44, (short) 4, "45@3=3"),
	STONE_BRICK_STEP("Stone Brick Slab", 44, (short) 5, "98@3=3"),
	BRICK("Brick Block", 45, "336@4"),
	TNT("TNT", 46, "12@4+289@5"),
	BOOKSHELF("Bookshelf", 47, "5@6+340@3"),
	MOSSY_COBBLESTONE("Moss Stone", 48),
	OBSIDIAN("Obsidian", 49),
	TORCH("Torch", 50, "280+263=4"),
	FIRE("Fire", 51, false),
	//ETERNAL_FIRE("EternalFire", 51, 16, false),
	MOB_SPAWNER("Monster Spawner", 52, false),
	WOOD_STAIRS("Wooden Stairs", 53, "5@6=4"),
	CHEST("Chest", 54, "5@8"),
	REDSTONE_WIRE("Redstone Wire", 55, false),
	DIAMOND_ORE("Diamond Ore", 56, false),
	DIAMOND_BLOCK("Diamond Block", 57, "264@9"),
	WORKBENCH("Crafting Table", 58, "5@4"),
	CROPS("Crops", 59, false),
	SOIL("Soil", 60, false),
	FURNACE("Furnace", 61, "4@8"),
	BURNING_FURNACE("Burning Furnace", 62, false),
	SIGN_POST("Sign Post", 63, false),
	WOODEN_DOOR("Wooden Door Block", 64, false),
	LADDER("Ladder", 65, "280@7=2"),
	RAILS("Rails", 66, "265@6+280=16"),
	COBBLESTONE_STAIRS("Cobblestone Stairs", 67, "4@6"),
	WALL_SIGN("Wall Sign", 68, false),
	LEVER("Lever", 69, "4+280"),
	STONE_PLATE("Stone Pressure Plate", 70, "1@2"),
	IRON_DOOR_BLOCK("Iron Door Block", 71, false),
	WOOD_PLATE("Wooden Pressure Plate", 72, "5@2"),
	REDSTONE_ORE("Redstone Ore", 73, false),
	GLOWING_REDSTONE_ORE("Glowing Redstone Ore", 74, false),
	REDSTONE_TORCH_OFF("Redstone Torch Off", 75, false),
	REDSTONE_TORCH("Redstone Torch", 76, "280+331"),
	STONE_BUTTON("Stone Button", 77, "1@2"),
	SNOW("Snow Covering", 78, false),
	ICE("Ice", 79, false),
	SNOW_BLOCK("Snow Block", 80),
	CACTUS("Cactus", 81),
	CLAY("Clay Block", 82, "337@4"),
	SUGAR_CANE_BLOCK("Sugar Cane Block", 83, false),
	JUKEBOX("Jukebox", 84, "5@8+264"),
	FENCE("Fence", 85, "280@6"),
	PUMPKIN("Pumpkin", 86),
	NETHERRACK("Netherrack", 87),
	SOUL_SAND("Soulsand", 88),
	GLOWSTONE("Glowstone", 89, "348@9"),
	PORTAL("Portal", 90, false),
	JACK_O_LANTERN("Jack O'Lantern", 91),
	CAKE_BLOCK("Cake Block", 92, false),
	DIODE_BLOCK_OFF("Repeater Block Off", 93, false),
	DIODE_BLOCK_ON("Repeater Block On", 94, false),
	LOCKED_CHEST("Locked Chest", 95, false),
	TRAPDOOR("Trapdoor", 96, "5@6=2"),
	SILVERFISH_STONE("Silverfish Stone", 97, false),
	SILVERFISH_COBBLESTONE("Silverfish Cobblestone", 97, (short) 1, false),
	SILVERFISH_STONEBRICK("Silverfish Stone Brick", 97, (short) 2, false),
	STONE_BRICK("Stone Brick", 98, (short) 0, "1@4=4"),
	MOSSY_STONE_BRICK("Mossy Stone Brick", 98, (short) 1),
	CRACKED_STONE_BRICK("Cracked Stone Brick", 98, (short) 2),
	CHISELED_STONE_BRICK("Chiseled Stone Brick", 98, (short) 3),
	BROWN_MUSHROOM_BLOCK("Brown Mushroom Block", 99, false),
	RED_MUSHROOM_BLOCK("Red Mushroom Block", 100, false),
	IRON_BARS("Iron Bars", 101, "265@6=16"),
	GLASS_PANE("Glass Pane", 102, "20@6=16"),
	MELON("Melon", 103, "360@9"),
	PUMPKIN_STEM("Pumpkin Stem Block", 104, false),
	MELON_STEM("Melon Stem Block", 105, false),
	VINE("Vine", 106),
	FENCE_GATE("Fence Gate", 107, "4@280+5@2"),
	BRICK_STAIRS("Brick Stairs", 108, "45@6=4"),
	STONE_STAIRS("Stone Stairs", 109, "98@6=4"),
	MYCELIUM("Mycelium", 110, false),
	LILLY_PAD("Lilly Pad", 111),
	NETHER_BRICK_BLOCK("Nether Brick Block", 112),
	NETHER_BRICK_FENCE("Nether Brick Fence", 113, "112@6=6"),
	NETHER_BRICK_STAIRS("Nether Brick Stairs", 114, "112@6=6"),
	NETHER_WART_Block("Nether Wart Block", 115, false),
	ENCHANTMENT_TABLE("Enchantment Table", 116, "49@4+264@2+340"),
	BREWING_STAND_BLOCK("Brewing Stand", 117, false),
	CAULDRON_BLOCK("Cauldron", 118, false),
	END_PORTAL("End Portal", 119, false),
	END_PORTAL_FRAME("End Portal Frame", 120, false),
	END_STONE("End Stone", 121, false),
	DRAGON_EGG("Dragon Egg", 122, false),
	REDSTONE_LAMP_ON("Redstone Lamp ON", 123, false),
	REDSTONE_LAMP_OFF("Redstone Lamp", 124, "331@4+89"),
	// legacy support.. not bothering with crafting recipies for now..
	WOODEN_DOUBLE_SLAB("Wooden Double Slab", 125),
	WOODEN_SLAB("Wooden Slab", 126),
	COCOA_POD("Cocoa Pod", 127, false),
	SANDSTONE_STAIRS("Sandstone Stairs", 128),
	EMERALD_ORE("Emerald Ore", 129, false),
	ENDER_CHEST("Ender Chest", 130),
	TRIPWIRE_HOOK("Tripwire Hook", 131),
	// 132: tripwire
	EMERALD_BLOCK("Emerald Block", 133),
	SPRUCE_WOODEN_STAIRS("Spruce Wood Stairs", 134),
	BIRCH_WOOD_STAIRS("Birch Wood Stairs", 135),
	JUNGLE_WOOD_STAIRS("Jungle Wood Stairs", 136),
	COMMAND_BLOCK("Command Block", 137, false),
	BEACON_BLOCK("Beacon", 138),
	COBBLESTONE_WALL("Cobblestone Wall", 139),
	FLOWER_POT_BLOCK("Flower Pot", 140, false),
	WOODEN_BUTTON("Wooden Button", 143),
	SKELETON_HEAD_BLOCK("Skeleton Head Block", 144, false),
	WITHER_SKELETON_HEAD_BLOCK("Wither Skeleton Head Block", 144, (short) 1, false),
	ZOMBIE_HEAD_BLOCK("Zombie Head Block", 144, (short) 2, false),
	HUMAN_HEAD_BLOCK("Human Head Block", 144, (short) 3, false),
	CREEPER_HEAD_BLOCK("Creeper Head Block", 144, (short) 4, false),
	ANVIL("Anvil", 145),
	// 1.5.1 - 2
	TRAPPED_CHEST("Trapped Chest", 146),
	GOLD_PRESSURE_PLATE("Gold Pressure Plate", 147),
	IRON_PRESSURE_PLATE("Iron Pressure Plate", 148),
	DAYLIGHT_SENSOR("Daylight Sensor", 151),
	REDSTONE_BLOCK("Redstone Block", 152),
	NETHER_QUARTZ_ORE("Nether Quartz Ore", 153),
	HOPPER("Hopper",  154),
	QUARTZ_BLOCK("Quartz Block", 155),
	QUARTZ_STAIRS("Quartz Stairs", 156),
	ACTIVATOR_RAIL("Activator Rail", 157),
	DROPPER("Dropper", 158),
	// 1.6
	//colored clay
	WHITE_STAINED_CLAY("White Stained Clay", 159, (short) 0),
	ORANGE_STAINED_CLAY("Orange Stained Clay", 159, (short) 1),
	MAGENTA_STAINED_CLAY("Magenta Stained Clay", 159, (short) 2),
	LIGHT_BLUE_STAINED_CLAY("Light Blue Stained Clay", 159, (short) 3),
	YELLOW_STAINED_CLAY("Yellow Stained Clay", 159, (short) 4),
	LIGHT_GREEN_STAINED_CLAY("Light Green Stained Clay", 159, (short) 5),
	PINK_STAINED_CLAY("Pink Stained Clay", 159, (short) 6),
	DARK_GRAY_STAINED_CLAY("Dark Gray Stained Clay", 159, (short) 7),
	LIGHT_GRAY_STAINED_CLAY("Light Gray Stained Clay", 159, (short) 8),
	CYAN_STAINED_CLAY("Cyan Stained Clay", 159, (short) 9),
	PURPLE_STAINED_CLAY("Purple Stained Clay", 159, (short) 10),
	BLUE_STAINED_CLAY("Blue Stained Clay", 159, (short) 11),
	BROWN_STAINED_CLAY("Brown Stained Clay", 159, (short) 12),
	DARK_GREEN_STAINED_CLAY("Dark Green Stained Clay", 159, (short) 13),
	RED_STAINED_CLAY("Red Stained Clay", 159, (short) 14),
	BLACK_STAINED_CLAY("Black Stained Clay", 159, (short) 15),
	//colored stained glass
	WHITE_STAINED_GLASS("White Stained Glass", 160, (short) 0),
	ORANGE_STAINED_GLASS("Orange Stained Glass", 160, (short) 1),
	MAGENTA_STAINED_GLASS("Magenta Stained Glass", 160, (short) 2),
	LIGHT_BLUE_STAINED_GLASS("Light Blue Stained Glass", 160, (short) 3),
	YELLOW_STAINED_GLASS("Yellow Stained Glass", 160, (short) 4),
	LIGHT_GREEN_STAINED_GLASS("Light Green Stained Glass", 160, (short) 5),
	PINK_STAINED_GLASS("Pink Stained Glass", 160, (short) 6),
	DARK_GRAY_STAINED_GLASS("Dark Gray Stained Glass", 160, (short) 7),
	LIGHT_GRAY_STAINED_GLASS("Light Gray Stained Glass", 160, (short) 8),
	CYAN_STAINED_GLASS("Cyan Stained Glass", 160, (short) 9),
	PURPLE_STAINED_GLASS("Purple Stained Glass", 160, (short) 10),
	BLUE_STAINED_GLASS("Blue Stained Glass", 160, (short) 11),
	BROWN_STAINED_GLASS("Brown Stained Glass", 160, (short) 12),
	DARK_GREEN_STAINED_GLASS("Dark Green Stained Glass", 160, (short) 13),
	RED_STAINED_GLASS("Red Stained Glass", 160, (short) 14),
	BLACK_STAINED_GLASS("Black Stained Glass", 160, (short) 15),
	
	ACACIA_LEAVES("Acacia Leaves", 161, (byte) 0),
	DARK_OAK_LEAVES("Dark Oak Leaves", 161, (byte) 1),
	ACACIA_LOG("Acacia Leaves", 161, (byte) 0),
	DARK_OAK_LOG("Dark Oak Leaves", 161, (byte) 1),
	ACACIA_WOOD_STAIRS("Acacia Wood Stairs", 163),
	DARK_OAK_WOOD_STAIRS("Dark Oak Wood Stairs", 164),
	//SLIME_BLOCK("Slime Block", 165),
	//MINECRAFT_BARRIER("Barrier", 166),
	//IRON_TRAPDOOR("Iron Trapdoor", 167),
	HAY_BLOCK("Hay Block", 170),
	WHITE_CARPET("White Carpet", 171, (short) 0),
	ORANGE_CARPET("Orange Carpet", 171, (short) 1),
	MAGENTA_CARPET("Magenta Carpet", 171, (short) 2),
	LIGHT_BLUE_CARPET("Light Blue Carpet", 171, (short) 3),
	YELLOW_CARPET("Yellow Carpet", 171, (short) 4),
	LIGHT_GREEN_CARPET("Light Green Carpet", 171, (short) 5),
	PINK_CARPET("Pink Carpet", 171, (short) 6),
	DARK_GRAY_CARPET("Dark Gray Carpet", 171, (short) 7),
	LIGHT_GRAY_CARPET("Light Gray Carpet", 171, (short) 8),
	CYAN_CARPET("Cyan Carpet", 171, (short) 9),
	PURPLE_CARPET("Purple Carpet", 171, (short) 10),
	BLUE_CARPET("Blue Carpet", 171, (short) 11),
	BROWN_CARPET("Brown Carpet", 171, (short) 12),
	DARK_GREEN_CARPET("Dark Green Carpet", 171, (short) 13),
	RED_CARPET("Red Carpet", 171, (short) 14),
	BLACK_CARPET("Black Carpet", 171, (short) 15),
	
	//
	HARDENED_CLAY("Hardened Clay", 172),
	COAL_BLOCK("Coal Block", 173),
	PACKED_ICE("Packed Ice", 174),
	SUNFLOWER("Sunflower", 175, (byte) 0),
	LILAC("Lilac", 175, (byte) 1),
	DOUBLE_TALL_GRASS("Double Tall Grass", 175, (byte) 2),
	LARGE_FERN("Large Fern", 175, (byte) 3),
	ROSE_BUSH("Rose Bush", 175, (byte) 4),
	PEONY("Peony", 175, (byte) 5),
	//
	// Items
	IRON_SPADE("Iron Shovel", 256, "280@2+265", (short) 251),
	IRON_PICKAXE("Iron Pickaxe", 257, "280@2+265@3", (short) 251),
	IRON_AXE("Iron Axe", 258, "280@2+265@3", (short) 251),
	FLINT_AND_STEEL("Flint And Steel", 259, "318+265", (short) 65),
	APPLE("Apple", 260),
	BOW("Bow", 261, "280@3+287@3", 1, (short) 385),
	ARROW("Arrow", 262, "280+318+288"),
	COAL("Coal", 263, (short) 0),
	CHARCOAL("Charcoal", 263, (short) 1),
	DIAMOND("Diamond", 264),
	IRON_INGOT("Iron Ingot", 265, "15@8+263=8, 15@3+5@2=3, 15@12+369=12"),
	GOLD_INGOT("Gold Ingot", 266, "14@8+263=8, 14@3+5@2=3, 14@12+369=12, 371@9"),
	IRON_SWORD("Iron Sword", 267, "280+265@2", (short) 251),
	WOOD_SWORD("Wooden Sword", 268, "280+5@2", (short) 60),
	WOOD_SPADE("Wooden Shovel", 269, "280@2+5", (short) 60),
	WOOD_PICKAXE("Wooden Pickaxe", 270, "280@2+5@3", (short) 60),
	WOOD_AXE("Wooden Axe", 271, "280@2+5@3", (short) 60),
	STONE_SWORD("Stone Sword", 272, "280+5@2", (short) 132),
	STONE_SPADE("Stone Shovel", 273, "280@2+4", (short) 132),
	STONE_PICKAXE("Stone Pickaxe", 274, "280@2+4@3", (short) 132),
	STONE_AXE("Stone Axe", 275, "280@2+4@3", (short) 132),
	DIAMOND_SWORD("Diamond Sword", 276, "280+264@2", (short) 1562),
	DIAMOND_SPADE("Diamond Shovel", 277, "280@2+264", (short) 1562),
	DIAMOND_PICKAXE("Diamond Pickaxe", 278, "280@2+264@3", (short) 1562),
	DIAMOND_AXE("Diamond Axe", 279, "280@2+264@3", (short) 1562),
	STICK("Stick", 280, "5@2=4"),
	BOWL("Bowl", 281, "5@3=4"),
	MUSHROOM_SOUP("Mushroom Soup", 282, "281+39+40", 1),
	GOLD_SWORD("Gold Sword", 283, "280+266@2", (short) 33),
	GOLD_SPADE("Gold Shovel", 284, "280@2+266", (short) 33),
	GOLD_PICKAXE("Gold Pickaxe", 285, "280@2+266@3", (short) 33),
	GOLD_AXE("Gold Axe", 286, "280@2+266@3", (short) 33),
	STRING("String", 287),
	FEATHER("Feather", 288),
	SULPHUR("Gunpowder", 289),
	WOOD_HOE("WoodenHoe", 290, "280@2+5@2", (short) 60),
	STONE_HOE("StoneHoe", 291, (short) 132),
	IRON_HOE("IronHoe", 292, (short) 251),
	DIAMOND_HOE("Diamond Hoe", 293, (short) 1562),
	GOLD_HOE("Gold Hoe", 294, (short) 33),
	SEEDS("Seeds", 295),
	WHEAT("Wheat", 296),
	BREAD("Bread", 297, "296@3"),
	LEATHER_HELMET("Leather Helmet", 298, "334@5", (short) 34),
	LEATHER_CHESTPLATE("Leather Chestplate", 299, "334@8", (short) 49),
	LEATHER_LEGGINGS("Leather Leggings", 300, "334@7", (short) 46),
	LEATHER_BOOTS("Leather Boots", 301, "334@4", (short) 40),
	// chainmail is currently illegal, but should be added later..
	CHAINMAIL_HELMET("Chainmail Helmet", 302, (short) 67, false),
	CHAINMAIL_CHESTPLATE("Chainmail Chestplate", 303, (short) 96, false),
	CHAINMAIL_LEGGINGS("Chainmail Leggings", 304, (short) 92, false),
	CHAINMAIL_BOOTS("Chainmail Boots", 305, (short) 79, false),
	IRON_HELMET("Iron Helmet", 306, "265@5", (short) 136),
	IRON_CHESTPLATE("Iron Chestplate", 307, "265@8", (short) 192),
	IRON_LEGGINGS("Iron Leggings", 308, "265@7", (short) 184),
	IRON_BOOTS("Iron Boots", 309, "265@4", (short) 160),
	DIAMOND_HELMET("Diamond Helmet", 310, "265@5", (short) 272),
	DIAMOND_CHESTPLATE("Diamond Chestplate", 311, "265@8", (short) 285),
	DIAMOND_LEGGINGS("Diamond Leggings", 312, "265@7", (short) 368),
	DIAMOND_BOOTS("Diamond Boots", 313, "265@4", (short) 320),
	GOLD_HELMET("Gold Helmet", 314, "265@5", (short) 68),
	GOLD_CHESTPLATE("Gold Chestplate", 315, "265@8", (short) 96),
	GOLD_LEGGINGS("Gold Leggings", 316, "265@7", (short) 92),
	GOLD_BOOTS("Gold Boots", 317, "265@4", (short) 80),
	FLINT("Flint", 318),
	PORK("Raw Porkchop", 319),
	GRILLED_PORK("Cooked Porkchop", 320, "319@8+263=8, 319@3+5@2=3, 319@12+369=12"),
	PAINTING("Painting", 321, "280@8+15"),
	GOLDEN_APPLE("Golden Apple", 322, "260+371@8"),
	ENCHANTED_GOLDEN_APPLE("Enchanted Golden Apple", 322, (short) 1, "260+41@8"),
	SIGN("Sign", 323, "280+5@6", 1),
	WOOD_DOOR("Wooden Door", 324, "5@6", 1),
	BUCKET("Bucket", 325, "265@3", 1),
	WATER_BUCKET("Water Bucket", 326, 1),
	LAVA_BUCKET("Lava Bucket", 327, 1),
	MINECART("Minecart", 328, "265@5", 1),
	SADDLE("Saddle", 329, 1),
	IRON_DOOR("Iron Door", 330, "265@6", 1),
	REDSTONE("Redstone", 331),
	SNOW_BALL("Snowball", 332, 16),
	BOAT("Boat", 333, "5@5", 1),
	LEATHER("Leather", 334),
	MILK_BUCKET("Milk Bucket", 335, 1),
	CLAY_BRICK("Clay Brick", 336, "337@8+263=8, 337@3+5@2=3, 337@12+369=12"),
	CLAY_BALL("Clay Balls", 337),
	SUGAR_CANE("Sugar Cane", 338),
	PAPER("Paper", 339, "338@3=3"),
	BOOK("Book", 340, "339@3"),
	SLIME_BALL("Slimeball", 341),
	STORAGE_MINECART("Storage Minecart", 342, "328+54", 1),
	POWERED_MINECART("Powered Minecart", 343, "328+61", 1),
	EGG("Egg", 344, 16),
	COMPASS("Compass", 345, "265@4+331"),
	FISHING_ROD("Fishing Rod", 346, "280@3+287@2", (short) 65), // was 33, but 1.0.0 ItemFishingRod.java says 64.
	WATCH("Clock", 347, "266@4+331"),
	GLOWSTONE_DUST("Glowstone Dust", 348),
	RAW_FISH("Raw Fish", 349),
	COOKED_FISH("Cooked Fish", 350, "349@8+263=8, 349@3+5@2=3, 349@12+369=12"),
	INK_SACK("Ink Sac", 351, (short) 0),
	RED_DYE("Red Dye", 351, (short) 1, "38=2"),
	GREEN_DYE("Green Dye", 351, (short) 2, "81@8+263=8, 81@3+5@2=3, 81@12+369=12"),
	BROWN_DYE("Cocoa Bean", 351, (short) 3), // no current method for crafting cocoa
	BLUE_DYE("Lapis Lazuli", 351, (short) 4, "22=9"),
	PURPLE_DYE("Purple Dye", 351, (short) 5, "351:4+351:1=2"),
	CYAN_DYE("Cyan Dye", 351, (short) 6, "351:4+351:2=2"),
	LIGHT_GRAY_DYE("Light Gray Dye", 351, (short) 7, "351:8+351:15=2"),
	GRAY_DYE("Gray Dye", 351, (short) 8, "351:15+351=2"),
	PINK_DYE("Pink Dye", 351, (short) 9, "351:1+351=2"),
	LIME_DYE("Lime Dye", 351, (short) 10, "351:2+351:15=2"),
	YELLOW_DYE("Yellow Dye", 351, (short) 11, "37=2"),
	LIGHT_BLUE_DYE("Light Blue Dye", 351, (short) 12, "351:4+351:15=2"),
	MAGENTA_DYE("Magenta Dye", 351, (short) 13, "351:4+351:9=2"),
	ORANGE_DYE("Orange Dye", 351, (short) 14, "351:1+351:11=2"),
	BONEMEAL("Bone Meal", 351, (short) 15, "352=2"),
	BONE("Bone", 352),
	SUGAR("Sugar", 353, "338"),
	CAKE("Cake", 354, "335@3+353@2+344+296@3", 1),
	BED("Bed", 355, "5@3+35@3", 1),
	DIODE("Redstone Repeater", 356, "1@3+76@2+331"),
	COOKIE("Cookie", 357, "296@2+351:3"),
	MAP("Map", 358, "339@8+345"),
	SHEARS("Shears", 359, "265@2", (short) 239),
	MELON_SLICE("Melon Slice", 360),
	PUMPKIN_SEEDS("Pumpkin Seeds", 361, "360"),
	MELON_SEEDS("Melon Seeds", 362, "360"),
	RAW_BEEF("Raw Beef", 363),
	STEAK("Steak", 364, "363@8+263=8, 363@3+5@2=3, 363@12+369=12"),
	RAW_CHICKEN("Raw Chicken", 365),
	COOKED_CHICKEN("Cooked Chicken", 366, "365@8+263=8, 365@3+5@2=3, 365@12+369=12"),
	ROTTEN_FLESH("Rotten Flesh", 367),
	ENDER_PEARL("Ender Pearl", 368),
	BLAZE_ROD("Blaze Rod", 369),
	GHAST_TEAR("Ghast Tear", 370),
	GOLD_NUGGET("Gold Nugget", 371),
	NETHER_WART("Nether Wart", 372),
	
	POTION("Potion", 373), // will need to add values later, so far unknown
	POTION_REGENERATION("Regeneration Potion", 373, (short) 1), // Potion of Regeneration (0:45)
	POTION_MOVESPEED("Speed Potion", 373, (short) 2), // Potion of Swiftness (3:00)
	POTION_FIRERESISTANCE("Fire Resistance Potion", 373, (short) 3), // Potion of Fire Resistance (3:00)
	POTION_POISON("Poison Potion", 373, (short) 4), // Potion of Poison (0:45)
	POTION_HEAL("Health Potion", 373, (short) 5), // Potion of Healing (Instant Health)
	POTION_CLEAR("Potion of Night Vision", 373, (short) 6), 
	POTION_CLEAR_2("Clear Potion", 373, (short) 7), // Clear Potion (no effects)
	POTION_WEAKNESS("Weakness Potion", 373, (short) 8), // Potion of Weakness (1:30)
	POTION_DAMEGEBOOST("Strength Potion", 373, (short) 9), // Potion of Strength (3:00)
	POTION_MOVESLOWDOWN("Slowness Potion", 373, (short) 10), // Potion of Slowness (1:30)
	POTION_DIFFUSE("Diffuse Potion", 373, (short) 11), // Diffuse Potion (no effects)
	POTION_HARM("Harm Potion", 373, (short) 12), // Potion of Harming (Instant Damage)
	POTION_ARTLESS("Artless Potion", 373, (short) 13), // Artless Potion (no effects)
	POTION_THIN("Potion of Invisibility", 373, (short) 14),
	POTION_THIN_2("Thin Potion", 373, (short) 15), // Thin Potion (no effects)
	POTION_AWKWARD("Awkward Potion", 373, (short) 16), // Awkward Potion (no effects)
	POTION_REGENERATION_2("Regeneration Potion", 373, (short) 17), // Potion of Regeneration (0:45)
	POTION_MOVESPEED_2("Speed Potion", 373, (short) 18), // Potion of Swiftness (3:00)
	POTION_FIRERESISTANCE_2("Fire Resistance Potion", 373, (short) 19), // Potion of Fire Resistance (3:00)
	POTION_POISON_2("Poison Potion", 373, (short) 20), // Potion of Poison (0:45)

	POTION_DIGSPEED("Haste Potion", 373, (short) 3),
	POTION_DIGSLOWDOWN("Fatigue Potion", 373, (short) 4),
	POTION_JUMP("Jump Potion", 373, (short) 8),
	POTION_CONFUSION("Nausea Potion", 373, (short) 9),
	POTION_RESISTANCE("Resistance Potion", 373, (short) 11),
	POTION_WATERBREATHING("Water Breathing Potion", 373, (short) 13),
	POTION_INVISIBILITY("Invisibility Potion", 373, (short) 14),
	POTION_BLINDNESS("Blindness Potion", 373, (short) 15),
	POTION_NIGHTVISION("Night Vision Potion", 373, (short) 16),
	POTION_HUNGER("Hunger Potion", 373, (short) 17),

	GLASS_BOTTLE("Glass Bottle", 374, "20@3"),
	SPIDER_EYE("Spider Eye", 375),
	FERMENTED_SPIDER_EYE("Fermented Spider Eye", 376, "375+39+352"),
	BLAZE_POWDER("Blaze Powder", 377, "369=2"),
	MAGMA_CREAM("Magma Cream", 378, "377+341"),
	BREWING_STAND("Brewing Stand", 379, "4@3+369"),
	CAULDRON("Cauldron", 380, "265@7"),
	EYE_OF_ENDER("Eye of Ender", 381, "368+377"),
	GLISTERING_MELON("Glistering Melon", 382, "360+371"),
	
	SPAWN_EGG_CREEPER("Creeper Egg", 383, (short) 50, false),
	SPAWN_EGG_SKELETON("Skeleton Egg", 383, (short) 51, false),
	SPAWN_EGG_SPIDER("Spider Egg", 383, (short) 52, false),
	SPAWN_EGG_GIANT("Giant Egg", 383, (short) 53, false),
	SPAWN_EGG_ZOMBIE("Zombie Egg", 383, (short) 54, false),
	SPAWN_EGG_SLIME("Slime Egg", 383, (short) 55, false),
	SPAWN_EGG_GHAST("Ghast Egg", 383, (short) 56, false),
	SPAWN_EGG_PIGZOMBIE("PigZombie Egg", 383, (short) 57, false),
	SPAWN_EGG_ENDERMAN("Enderman Egg", 383, (short) 58, false),
	SPAWN_EGG_CAVESPIDER("CaveSpider Egg", 383, (short) 59, false),
	SPAWN_EGG_SILVERFISH("Silverfish Egg", 383, (short) 60, false),
	SPAWN_EGG_BLAZE("Blaze Egg", 383, (short) 61, false),
	SPAWN_EGG_LAVASLIME("LavaSlime Egg", 383, (short) 62, false),
	SPAWN_EGG_ENDERDRAGON("EnderDragon Egg", 383, (short) 63, false),
	SPAWN_EGG_PIG("Pig Egg", 383, (short) 90, false),	
	SPAWN_EGG_SHEEP("Sheep Egg", 383, (short) 91, false),
	SPAWN_EGG_COW("Cow Egg", 383, (short) 92, false),
	SPAWN_EGG_CHICKEN("Chicken Egg", 383, (short) 93, false),
	SPAWN_EGG_WOLF("Wolf Egg", 383, (short) 94, false),
	SPAWN_EGG_MUSHROOMCOW("MushroomCow Egg", 383, (short) 95, false),
	SPAWN_EGG_SNOWMAN("SnowMan Egg", 383, (short) 96, false),
	SPAWN_EGG_VILLAGER("Villager Egg", 383, (short) 97, false),
	SPAWN_EGG_OCELOT("Ocelot Egg", 383, (short) 98, false),
	SPAWN_EGG_IRON_GOLEM("Iron Golem Egg", 383, (short) 99, false), // will be added later (not yet implemented in mc)
	
	EXP_BOTTLE("Bottle o' Enchanting", 384, false), // (Experience bottle)
	FIREBALL("Fire Charge", 385, "377+289+263=3, 377+289+263:1=3"),
	// legacy support: not bothering with crafting recipies for now
	BOOK_AND_QUILL("Book and Quill", 386),
	WRITTEN_BOOK("Written Book", 387),
	EMERALD("Emerald", 388),
	ITEM_FRAME("Item Frame", 389),
	FLOWER_POT("Flower Pot", 390),
	CARROT("Carrot", 391),
	POTATO("Potato", 392),
	BAKED_POTATO("Baked Potato", 393),
	POISONOUS_POTATO("Poisonous Potato", 394),
	EMPTY_MAP("Empty Map", 395),
	GOLDEN_CARROT("Golden Carrot", 396),
	SKELETON_HEAD("Skeleton Head", 397),
	WITHER_SKELETON_HEAD("Wither Skeleton Head", 397, (short) 1),
	ZOMBIE_HEAD("Zombie Head", 397, (short) 2),
	HUMAN_HEAD("Human Head", 397, (short) 3),
	CREEPER_HEAD("Creeper Head", 397, (short) 4),
	CARROT_ON_A_STICK("Carrot on a Stick", 398, (short) 26),
	NETHER_STAR("Nether Star", 399),
	PUMPKIN_PIE("Pumpkin Pie", 400),
	// 1.4.6
	FIREWORK_ROCKET("Firework Rocket", 401),
	FIREWORK_STAR("Firework Star", 402),
	ENCHANTED_BOOK("Enchanted Book", 403),
	// 1.5.1 - 2
	REDSTONE_COMPARATOR("Redstone Comparator", 404),
	NETHER_BRICK("Nether Brick", 405),
	NETHER_QUARTZ("Nether Quartz", 406),
	TNT_MINECART("TNT Minecart", 407),
	HOPPER_MINECART("Hopper Minecart", 408),
	// 1.6
	IRON_HORSE_ARMOR("Iron Horse Armor", 417),
	GOLD_HORSE_ARMOR("Gold Horse Armor", 418),
	DIAMOND_HORSE_ARMOR("Diamond Horse Armor", 419),
	LEAD("Lead", 420),
	NAME_TAG("Name Tag", 421),
	// 1.7
	COMMAND_BLOCK_MINECART("Command Block Minecart", 422),
	// Records
	GOLD_RECORD("13 Disk", 2256, 1), // Gold Record
	GREEN_RECORD("Cat Disc", 2257, 1), // Green Record
	ORANGE_RECORD("Blocks Disc", 2258, 1),
	RED_RECORD("Chirp Disc", 2259, 1),
	LIME_RECORD("Far Disc", 2260, 1),
	BLUE_RECORD("Mall Disc", 2261, 1),
	PURPLE_RECORD("Mellohi Disc", 2262, 1),
	BLACK_RECORD("Stal Disc", 2263, 1),
	WHITE_RECORD("Strad Disc", 2264, 1),
	GREEN_LIME_RECORD("Ward Disc", 2265, 1), // has half green, half lime
	BROKEN_RECORD("11 Disc", 2266, 1),
	// 1.6?
	CYAN_RECORD("Wait Disk", 2267, 1);
// Item Information
	private int itemId;
	private short itemData;
	private boolean isLegal = true,  // is is legitemitely obtainable
			hasData = false; // if data defines this item
	private int maxStack = 64;
	private short maxdamage = 0; // indicates that this is a tool
	public String name = null;//, category = null;
	private LinkedList<CraftRecipe> recipes = new LinkedList<CraftRecipe>();
	// what item ids' data resemble max damage value
	// (must be in ascending order)
	private static int[] tools = new int[]{
		256, 257, 258, 259, 261, 267, 268, 269, 270, 271,
		272, 273, 274, 275, 276, 277, 278, 278, 279,
		283, 284, 285, 286, 290, 291, 292, 293, 294,
		298, 299, 300, 301, 302, 303, 304, 305,
		306, 307, 308, 309, 310, 311, 312, 313,
		314, 315, 316, 317, 346, 359, 398};
	// items (other than tools) that aren't good to stack
	private static int[] unsafeStack = new int[]{
		282, 325, 326, 327, 335};

	private JItems(String name, int id) {
		if (SetID(id)) {
			this.name = name;
		}
	}

	private JItems(String name, int id, boolean legal) {
		if (SetID(id)) {
			this.name = name;
			isLegal = legal;
		}
	}

	private JItems(String name, int id, short dat) {
		if (SetID(id)) {
			this.name = name;
			itemData = dat;
			hasData = true;
		}
	}

	private JItems(String name, int id, int maxStack) {
		if (SetID(id)) {
			this.name = name;
			this.maxStack = maxStack;
		}
	}

	private JItems(String name, int id, int maxStack, boolean legal) {
		if (SetID(id)) {
			this.name = name;
			this.maxStack = maxStack;
			isLegal = legal;
		}
	}

	private JItems(String name, int id, short dat, boolean legal) {
		if (SetID(id)) {
			this.name = name;
			itemData = dat;
			isLegal = legal;
			hasData = true;
		}
	}

	private JItems(String name, int id, String craftRecipies) {
		if (SetID(id)) {
			this.name = name;
			_setRecipe(craftRecipies);
		}
	}

	private JItems(String name, int id, String craftRecipies, boolean legal) {
		if (SetID(id)) {
			this.name = name;
			isLegal = legal;
			_setRecipe(craftRecipies);
		}
	}

	private JItems(String name, int id, short dat, String craftRecipies) {
		if (SetID(id)) {
			this.name = name;
			itemData = dat;
			_setRecipe(craftRecipies);
			hasData = true;
		}
	}

	private JItems(String name, int id, String craftRecipies, int maxStack) {
		if (SetID(id)) {
			this.name = name;
			this.maxStack = maxStack;
			_setRecipe(craftRecipies);
		}
	}

	private JItems(String name, int id, String craftRecipies, int maxStack, boolean legal) {
		if (SetID(id)) {
			this.name = name;
			isLegal = legal;
			this.maxStack = maxStack;
			_setRecipe(craftRecipies);
		}
	}

	private JItems(String name, int id, String craftRecipies, short maxDamage) {
		if (SetID(id)) {
			this.name = name;
			this.maxdamage = maxDamage;
			this.maxStack = maxdamage > 0 ? 1 : 64;
			_setRecipe(craftRecipies);
			hasData = true;
		}
	}

	private JItems(String name, int id, String craftRecipies, short maxDamage, boolean legal) {
		if (SetID(id)) {
			this.name = name;
			isLegal = legal;
			this.maxdamage = maxDamage;
			this.maxStack = maxdamage > 0 ? 1 : 64;
			_setRecipe(craftRecipies);
			hasData = true;
		}
	}
    
    private JItems(String name, int id, String craftRecipies, int maxStack, short maxDamage) {
		if (SetID(id)) {
			this.name = name;
			this.maxStack = maxStack;
			this.maxdamage = maxDamage;
			_setRecipe(craftRecipies);
			hasData = true;
		}
	}

	private JItems(String name, int id, short dat, String craftRecipies, int maxStack, short maxDamage) {
		if (SetID(id)) {
			this.name = name;
			itemData = dat;
			this.maxStack = maxStack;
			this.maxdamage = maxDamage;
			_setRecipe(craftRecipies);
			hasData = true;
		}
	}

	private JItems(String name, int id, short dat, String craftRecipies, boolean legal) {
		if (SetID(id)) {
			this.name = name;
			itemData = dat;
			isLegal = legal;
			_setRecipe(craftRecipies);
			hasData = true;
		}
	}

	private JItems(int id) {
		if (SetID(id)) {
			AutoName();
		}
	}

	private JItems(int id, short dat) {
		if (SetID(id)) {
			itemData = dat;
			AutoName();
			hasData = true;
		}
	}

//    private JItems(ItemStack i) {
//        itemId = i.getTypeId();
//        //if(maxdamage==0)// i.getDurability()<MAX_DATA)
//        itemData = (short) i.getDurability();
//        name = "";
//    }
	private boolean SetID(int id) {
		if (org.bukkit.Material.getMaterial(id) != null) {
			itemId = id;
			return true;
		} else {
			//System.out.println("invalid item: " + id + " (is this plugin up-to-date?)");
			itemId = -1;
			name = "";
			isLegal = false;
			maxStack = 0;
			return false;
		}
	}

	private void AutoName() {
		if (org.bukkit.Material.getMaterial(itemId) != null) {
			name = org.bukkit.Material.getMaterial(itemId).name();
		} else {
			name = "";
		}
	}

	public int ID() {
		return itemId;
	}

	public short Data() {
		return itemData;
	}

	public short MaxDamage() {
		return maxdamage;
	}

	public boolean IsTool() {
		return maxdamage > 0;
	}

	public boolean IsLegal() {
		return isLegal;
	}

	public int MaxStackSize() {
		return maxStack;
	}

	public String IdDatStr() {
		return String.format("%d:%d", itemId, itemData);
	}

	public String getName() {
		return name == null ? this.name() : name;
	}

	public void setName(String newname) {
		if (newname != null) {
			this.name = newname;
		}
	}

	protected void setMaxStack(int stack) {
		maxStack = stack;
	}

	private JItem _getIdDat(String datStr) {
		String s1 = datStr.contains(":") ? datStr.substring(0, datStr.indexOf(':')).trim() : datStr,
				s2 = datStr.contains(":") ? datStr.substring(datStr.indexOf(':') + 1).trim() : "";
		JItem r = new JItem();
		r.itemId = CheckInput.GetInt(s1, -1);
		r.itemDat = CheckInput.GetShort(s2, (short) 0);
		if (r.itemId > 0) {
			return r;
		}
		return null;
	}

	private CraftRecipe _getRecipe(String craftStr) {
		CraftRecipe ret = new CraftRecipe();

		// get result amount
		if (craftStr.contains("=")) {
			if (craftStr.split("=").length > 2 || craftStr.length() == craftStr.indexOf('=')) {
				return null;
			}
			ret.resultAmount = CheckInput.GetInt(craftStr.substring(craftStr.indexOf('=') + 1), 0);
			craftStr = craftStr.substring(0, craftStr.indexOf('='));
		} else {
			ret.resultAmount = 1;
		}
		// extract all items
		for (String i : craftStr.split("\\+")) {
			if (i.contains("@")) {
				if (i.length() > i.indexOf('@')) {
					ret.AddItem(_getIdDat(i.substring(0, i.indexOf('@'))),
							CheckInput.GetInt(i.substring(i.indexOf('@') + 1), 0));
				} else {
					ret.AddItem(_getIdDat(i.substring(0, i.indexOf('@'))));
				}
			} else {
				ret.AddItem(_getIdDat(i));
			}
		}
		return ret.totalItems() == 0 ? null : ret;
	}

	private void _setRecipe(String craft) {
		if (craft != null) {
			for (String i : craft.split(",")) {
				CraftRecipe toAdd = _getRecipe(i);
				if (toAdd != null) {
					recipes.add(toAdd);
				}
			}
		}
	}

	public boolean HasRecipe() {
		return recipes.size() > 0;
	}

	public Kit[] GetRecipeAsKit(int index) {
		if (index >= 0 && index < recipes.size()) {
			return recipes.get(index).getKits();
		}
		return null;
	}

	public Kit[] GetFullRecipeKits() {
		LinkedList<Kit> ret = new LinkedList<Kit>();
		for (CraftRecipe c : recipes) {
			ret.addAll(Arrays.asList(c.getKits()));
		}
		return ret.toArray(new Kit[0]);
	}

	public CraftRecipe GetRecipe(int index) {
		if (index >= 0 && index < recipes.size()) {
			return recipes.get(index);
		}
		return null;
	}

	public CraftRecipe[] GetRecipes() {
		return recipes.toArray(new CraftRecipe[0]);
	}

	public ItemStack toItemStack() {
		//return isEntity() || itemId < 0 ? null : new ItemStack(itemId, 1, (short) 0, itemData);
		return isEntity() || itemId < 0 ? null : 
				(itemData <= Byte.MAX_VALUE ? 
				new ItemStack(itemId, 1, (short) 0, (byte) itemData) 
				: new ItemStack(itemId, 1, itemData));
	}

	public ItemStack toItemStack(int amount) {
		//return isEntity() || itemId < 0 ? null : new ItemStack(itemId, amount, (short) 0, itemData);
		return isEntity() || itemId < 0 ? null : 
				(itemData <= Byte.MAX_VALUE ? 
				new ItemStack(itemId, amount, (short) 0, (byte) itemData) 
				: new ItemStack(itemId, amount, itemData));
	}

	// creatures are numbered starting at 4000
	public boolean isEntity() {
		return itemId >= 4000 && itemId < 5000;
	}

	public static JItems getItem(int id) {
		for (JItems i : JItems.values()) {
			if (i.ID() == id && i.Data() == 0) {
				return i;
			}
		}
		return null;
	}

	public static JItems getItem(int id, short dat) {
		for (JItems i : JItems.values()) {
			if (i.ID() == id && i.Data() == dat) {
				return i;
			}
		}
		return null;
	}

	public static JItems getItem(Material material) {
		for (JItems i : JItems.values()) {
			if (i.ID() == material.getId() && i.Data() == 0) {
				return i;
			}
		}
		return null;
	}

	public static JItems findItem(Item search) {
		if (search == null || search.getItemStack() == null) {
			return null;
		}
		int id = search.getItemStack().getTypeId();
		short dat = search.getItemStack().getData().getData();
		for (JItems i : JItems.values()) {
			if (i.ID() == id && i.Data() == dat) {
				return i;
			}
		}
		return null;
	}
	
	public static int getMaxStack(int id) {
		JItems i = getItem(id);
		return i == null ? 64 : i.MaxStackSize();
	}

	public static int getMaxStack(int id, short dat) {
		JItems i = getItem(id, dat);
		return i == null ? 64 : i.MaxStackSize();
	}

	public static int getMaxStack(ItemStack it) {
		JItems i = getItem(it.getTypeId(), it.getData() == null ? 0 : it.getData().getData());
		return i == null ? 64 : i.MaxStackSize();
	}
	
	public static boolean isStackable(ItemStack it) {
		int i = Arrays.binarySearch(unsafeStack, it.getTypeId());
		return i < 0 && !isTool(it.getTypeId());
	}

	public static boolean isStackable(int id) {
		int i = Arrays.binarySearch(unsafeStack, id);
		return i < 0 && !isTool(id);
	}
	
	public static boolean isTool(int id) {
		int i = Arrays.binarySearch(tools, id);
		return i >= 0 && tools[i] == id;
	}

	public static boolean isTool(ItemStack it) {
		JItems ji = getItem(it.getTypeId(), it.getData() == null ? 0 : it.getData().getData());
		return ji != null && ji.IsTool();
	}
	
	public static boolean hasData(int id) {
		JItems i = getItem(id);
		return i != null && i.hasData;
	}

	@Override
	public String toString() {
		return String.format("%s (%d:%d)", getName(), itemId, itemData);
	}

	public boolean equals(ItemStack i) {
		return i != null
				&& itemId == i.getTypeId()
				&& (IsTool() || (!hasData || (hasData && itemData == i.getDurability())));
	}
} // end class JItems

