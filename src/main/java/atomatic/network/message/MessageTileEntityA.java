package atomatic.network.message;

import atomatic.tileentity.TileEntityA;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;

public class MessageTileEntityA implements IMessage, IMessageHandler<MessageTileEntityA, IMessage>
{
    public int x, y, z;
    public byte orientation, state;
    public String customName, owner;

    public MessageTileEntityA()
    {
    }

    public MessageTileEntityA(TileEntityA tileEntityA)
    {
        this.x = tileEntityA.xCoord;
        this.y = tileEntityA.yCoord;
        this.z = tileEntityA.zCoord;
        this.orientation = (byte) tileEntityA.getOrientation().ordinal();
        this.state = (byte) tileEntityA.getState();
        this.customName = tileEntityA.getCustomName();
        this.owner = tileEntityA.getOwner();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.orientation = buf.readByte();
        this.state = buf.readByte();
        int customNameLength = buf.readInt();
        this.customName = new String(buf.readBytes(customNameLength).array());
        int ownerLength = buf.readInt();
        this.owner = new String(buf.readBytes(ownerLength).array());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(orientation);
        buf.writeByte(state);
        buf.writeInt(customName.length());
        buf.writeBytes(customName.getBytes());
        buf.writeInt(owner.length());
        buf.writeBytes(owner.getBytes());
    }

    @Override
    public IMessage onMessage(MessageTileEntityA message, MessageContext ctx)
    {
        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld
                .getTileEntity(message.x, message.y, message.z);

        if (tileEntity instanceof TileEntityA)
        {
            ((TileEntityA) tileEntity).setOrientation(message.orientation);
            ((TileEntityA) tileEntity).setState(message.state);
            ((TileEntityA) tileEntity).setCustomName(message.customName);
            ((TileEntityA) tileEntity).setOwner(message.owner);
        }

        return null;
    }

    @Override
    public String toString()
    {
        return String
                .format("MessageTileEntityA - x:%s, y:%s, z:%s, orientation:%s, state:%s, customName:%s, owner:%s", x,
                        y, z, orientation, state, customName, owner);
    }
}
