package com.sk89q.wg_regions_52;
/*
 * WorldEdit
 * Copyright (C) 2010 sk89q <http://www.sk89q.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * Extension of Vector that supports being compared as ints (for accuracy).
 *
 * @author sk89q
 */
public class BlockVector extends Vector {
    /**
     * Construct the Vector object.
     *
     * @param pt
     */
    public BlockVector(Vector pt) {
        super(pt);
    }

    /**
     * Construct the Vector object.
     * 
     * @param x 
     * @param y 
     * @param z 
     */
    public BlockVector(int x, int y, int z) {
        super(x, y, z);
    }

    /**
     * Construct the Vector object.
     *
     * 
     * @param x 
     * @param y 
     * @param z 
     */
    public BlockVector(float x, float y, float z) {
        super(x, y, z);
    }

    /**
     * Construct the Vector object.
     *
     * 
     * @param x 
     * @param y 
     * @param z 
     */
    public BlockVector(double x, double y, double z) {
        super(x, y, z);
    }
    

    /**
     * Construct the Vector object.
     *
     * 
     * @param x 
     * @param y 
     * @param z 
     */
    public BlockVector(com.sk89q.worldedit.BlockVector copy) {
        super(copy.getX(), copy.getY(), copy.getZ());
    }
    
    /**
     * Checks if another object is equivalent.
     *
     * @param obj
     * @return whether the other object is equivalent
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector)) {
            return false;
        }
        Vector other = (Vector)obj;
        return (int)other.getX() == (int)this.x && (int)other.getY() == (int)this.y
                && (int)other.getZ() == (int)this.z;

    }

    /**
     * Gets the hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return (Integer.valueOf((int)x).hashCode() >> 13) ^
               (Integer.valueOf((int)y).hashCode() >> 7) ^
                Integer.valueOf((int)z).hashCode();
    }
}
