import java.rmi.RemoteException;

/**
 * Created by dell on 11/22/14.
 */
public interface Hello2Intf {
    public String getHello() throws RemoteException;
    public void setHello(String message) throws RemoteException;
}
