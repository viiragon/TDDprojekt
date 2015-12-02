/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrzykladISOD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author Wojtek
 */
public class RaceResultsService {

    private Collection<Client> clients = new HashSet<>();

    public void addSubscriber(Client client) {
        clients.add(client);
    }
    
    public void removeSubscriber(Client client) {
        clients.remove(client);
    }

    public void send(Message message) {
        for (Client client : clients) {
            client.receive(message);
        }
    }
}
