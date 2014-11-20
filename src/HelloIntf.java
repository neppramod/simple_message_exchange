import java.rmi.RemoteException;

public interface HelloIntf {
    public String getHello() throws RemoteException;
    public void setHello(String message) throws RemoteException;
}
