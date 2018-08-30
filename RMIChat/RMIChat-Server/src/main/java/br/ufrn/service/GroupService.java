package br.ufrn.service;

import br.ufrn.domain.Group;
import br.ufrn.domain.User;
import br.ufrn.exceptions.GroupNotExistsException;

import java.io.Serializable;
import java.util.List;

/**
 * Interfaca para o serviço de grupos.
 */
public interface GroupService {

    /**
     * Cria um grupo.
     *
     * @param name
     * @param creator
     */
    void createGroup(String name, User creator);

    /**
     * Busca um grupo pelo seu ID
     *
     * @param groupId
     * @return
     * @throws GroupNotExistsException Caso o grupo não exista.
     */
    Group findGroupById(String groupId) throws GroupNotExistsException;

    /**
     * Ingressa um usuário no grupo.
     *
     * @param groupId
     * @param user
     * @throws GroupNotExistsException
     */
    void joinGroup(String groupId, User user) throws GroupNotExistsException;

    /**
     * Retira um usuário do grupo.
     *
     * @param groupId
     * @param user
     * @throws GroupNotExistsException
     */
    void quitGroup(String groupId, User user) throws GroupNotExistsException;

    /**
     * Envia uma mensagem para o grupo.
     *
     * @param groupId
     * @param username
     * @param messageContent
     * @throws GroupNotExistsException
     */
    void sendMessageToGroup(String groupId, String username, String messageContent) throws GroupNotExistsException;

    /**
     * Lista todos os grupos que o usuário está vinculado.
     * @param username
     * @return
     */
    List<Group> listGroups(String username);

    /**
     * Lista todos os grupos existentes.
     *
     * @return
     */
    List<Group> listGroups();
}
