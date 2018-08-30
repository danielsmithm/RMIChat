package br.ufrn.utils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Service locator da aplicação.
 */
public class ServiceLocator {

	public static <T> T lookupForRmiObject(String rmiUrl) throws RemoteException, NotBoundException, MalformedURLException {
		return (T) Naming.lookup(rmiUrl);
	}

}
