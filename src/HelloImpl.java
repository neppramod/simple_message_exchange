import java.rmi.RemoteException;

public class HelloImpl implements HelloIntf, Hello2Intf, java.io.Serializable {
    @Override
    public String getHello() throws RemoteException {
        return "Server is sending hello!!";
    }

    @Override
    public void setHello(String message) throws RemoteException {
        System.out.print("Server: Message from client :");
        System.out.println(message);
    }
}
