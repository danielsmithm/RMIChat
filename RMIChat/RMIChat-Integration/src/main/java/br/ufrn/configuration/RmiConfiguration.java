package br.ufrn.configuration;

/**
 * Classe para guardar as constantes de configuração do servidor RMI.
 * 
 * @author Daniel Smith
 *
 */
public class RmiConfiguration {

	public static final String RMI_HOST = "rmi://localhost";
	public static final String URL_CHAT_FACADE = RMI_HOST + "/ComputeEngine";
	public static final int RMI_PORT = 1099;

}
