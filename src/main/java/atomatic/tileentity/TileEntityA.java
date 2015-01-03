package atomatic.tileentity;

import atomatic.network.PacketHandler;
import atomatic.network.message.MessageTileEntityA;
import atomatic.reference.Names;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityA extends TileEntity
{
    protected ForgeDirection orientation;
    protected byte state;
    protected String customName;
    protected String owner;

    public TileEntityA()
    {
        orientation = ForgeDirection.SOUTH;
        state = 0;
        customName = "";
        owner = "";
    }

    public ForgeDirection getOrientation()
    {
        return orientation;
    }

    public void setOrientation(ForgeDirection orientation)
    {
        this.orientation = orientation;
    }

    public void setOrientation(int orientation)
    {
        this.orientation = ForgeDirection.getOrientation(orientation);
    }

    public short getState()
    {
        return state;
    }

    public void setState(byte state)
    {
        this.state = state;
    }

    public String getCustomName()
    {
        return customName;
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        if (nbtTagCompound.hasKey(Names.NBT.DIRECTION))
        {
            this.orientation = ForgeDirection.getOrientation(nbtTagCompound.getByte(Names.NBT.DIRECTION));
        }

        if (nbtTagCompound.hasKey(Names.NBT.STATE))
        {
            this.state = nbtTagCompound.getByte(Names.NBT.STATE);
        }

        if (nbtTagCompound.hasKey(Names.NBT.CUSTOM_NAME))
        {
            this.customName = nbtTagCompound.getString(Names.NBT.CUSTOM_NAME);
        }

        if (nbtTagCompound.hasKey(Names.NBT.OWNER))
        {
            this.owner = nbtTagCompound.getString(Names.NBT.OWNER);
        }
    }

    protected void readNBT(NBTTagCompound nbtTagCompound)
    {

    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setByte(Names.NBT.DIRECTION, (byte) orientation.ordinal());
        nbtTagCompound.setByte(Names.NBT.STATE, state);

        if (this.hasCustomName())
        {
            nbtTagCompound.setString(Names.NBT.CUSTOM_NAME, customName);
        }

        if (this.hasOwner())
        {
            nbtTagCompound.setString(Names.NBT.OWNER, owner);
        }
    }

    protected void writeNBT(NBTTagCompound nbtTagCompound)
    {

    }

    public boolean hasCustomName()
    {
        return customName != null && customName.length() > 0;
    }

    public boolean hasOwner()
    {
        return owner != null && owner.length() > 0;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        writeNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        super.onDataPacket(net, pkt);
        readNBT(pkt.func_148857_g());
    }
}
