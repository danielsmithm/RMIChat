package br.ufrn;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ServiceLocator {

	public static <T> T lookupFor(String rmiUrl) throws RemoteException, NotBoundException, MalformedURLException {
		return (T) Naming.lookup(rmiUrl);
	}

}
