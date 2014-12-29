package atomatic.proxy;

public class ServerProxy extends CommonProxy
{
    @Override
    public ClientProxy getClientProxy()
    {
        return null;
    }

    @Override
    public void registerRenderer()
    {
        // NO-OP
    }
}
