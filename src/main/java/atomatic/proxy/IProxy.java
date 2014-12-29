package atomatic.proxy;

public interface IProxy
{
    public abstract ClientProxy getClientProxy();

    public abstract void registerEventHandlers();

    public abstract void registerRenderer();
}
