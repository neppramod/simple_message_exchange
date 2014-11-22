import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

import net.jini.discovery.LookupDiscovery;
import net.jini.discovery.DiscoveryListener;
import net.jini.discovery.DiscoveryEvent;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceTemplate;

public class HelloClient implements DiscoveryListener{

    public static void main(String[] args) {
        new HelloClient();

        try {
            Thread.sleep(100000L);
        } catch(InterruptedException e) {}
    }

    public HelloClient() {
        System.setSecurityManager(new RMISecurityManager());

        LookupDiscovery discover = null;

        try {
            discover = new LookupDiscovery(LookupDiscovery.ALL_GROUPS);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        }

        discover.addDiscoveryListener(this);
    }

    @Override
    public void discovered(DiscoveryEvent discoveryEvent) {
        ServiceRegistrar[] registrars = discoveryEvent.getRegistrars();
        HelloIntf helloIntf = null;
        Hello2Intf hello2Intf = null;
        Class[] classes = new Class[] {HelloIntf.class, Hello2Intf.class};
        ServiceTemplate template = new ServiceTemplate(null, classes, null);

        for (int n = 0; n < registrars.length; n++) {
            System.out.println("Lookup service found");
            ServiceRegistrar registrar = registrars[n];

            try {
                helloIntf = (HelloIntf) registrar.lookup(template);
                hello2Intf = (Hello2Intf) registrar.lookup(template);
            } catch (RemoteException e) {
                System.out.println("HelloIntf null");
                continue;
            }

            // Use helloIntf object we got from server
            try {
                System.out.println("Client: From server: " + helloIntf.getHello());
                System.out.println("Client 2: From server: " + hello2Intf.getHello());
                helloIntf.setHello("I am client, saying hi to server");
                hello2Intf.setHello("I am client 2, saying hi to server");
            } catch (RemoteException e) {
                e.printStackTrace();
                continue;
            }

            // Should I exit now or not, Have to look at this later
            System.exit(0);
        }
    }

    @Override
    public void discarded(DiscoveryEvent discoveryEvent) {

    }
}
