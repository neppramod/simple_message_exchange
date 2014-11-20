
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import net.jini.discovery.LookupDiscovery;
import net.jini.discovery.DiscoveryListener;
import net.jini.discovery.DiscoveryEvent;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceItem;
import net.jini.core.lookup.ServiceRegistration;
import net.jini.core.lease.Lease;
import net.jini.core.lookup.ServiceID ;
import net.jini.lease.LeaseListener;
import net.jini.lease.LeaseRenewalEvent;
import net.jini.lease.LeaseRenewalManager;

public class HelloServer implements  DiscoveryListener, LeaseListener{
    protected LeaseRenewalManager leaseManager = new LeaseRenewalManager();
    protected ServiceID serviceID = null;
    protected HelloIntf helloIntf = null;

    public static void main(String[] args) {
        HelloServer s = new HelloServer();

        Object keepAlive = new Object();

        synchronized (keepAlive) {
            try {
                keepAlive.wait();
            } catch(InterruptedException e) {}
        }


    }

    public HelloServer() {
        helloIntf = new HelloImpl();

        System.setSecurityManager(new RMISecurityManager());

         LookupDiscovery discover = null;
        try {
            discover = new LookupDiscovery(LookupDiscovery.ALL_GROUPS);
        } catch(Exception e) {
            System.out.println("Discovery failed " + e.toString());
            System.exit(1);
        }

        discover.addDiscoveryListener(this);
    }

    @Override
    public void discovered(DiscoveryEvent discoveryEvent) {
ServiceRegistrar[] registrars = discoveryEvent.getRegistrars();

        for (int n = 0; n < registrars.length; n++) {
            ServiceRegistrar registrar = registrars[n];

            // serviceID could be null, don't worry about that
            ServiceItem item = new ServiceItem(serviceID, helloIntf, null);
            ServiceRegistration reg = null;

            try {
                reg = registrar.register(item, Lease.FOREVER);
            } catch(RemoteException e) {
                System.out.println("Register exception " + e.toString());
                continue;
            }

            System.out.println("Service registered with id " + reg.getServiceID());

            // set lease renewal in place
            leaseManager.renewUntil(reg.getLease(), Lease.FOREVER, this);

        }
    }

    @Override
    public void discarded(DiscoveryEvent discoveryEvent) {

    }

    @Override
    public void notify(LeaseRenewalEvent leaseRenewalEvent) {
        System.out.println("Lease experied " + leaseRenewalEvent.toString());
    }
}
