package com.myApplication.server.service.impl;

import com.myApplication.server.enumeration.Status;
import com.myApplication.server.model.Server;
import com.myApplication.server.repository.ServerRepository;
import com.myApplication.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;


    @Override
    public Server create(Server server) {
        log.info("Saving a new server  :  {} ", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }


    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("pinging server IP  :  {} ", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepository.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching  all servers  ... ");
        if (limit >= 0) {
            return serverRepository.findAll(PageRequest.of(0, limit)).toList();
        }
        return serverRepository.findAll(PageRequest.of(0, 0)).toList();
    }

    @Override
    public Server get(long id) {
        log.info("Fetching server by Id : {} ", id);
        if (serverRepository.findById(id).isPresent()) {
            return serverRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Server update(Server server) {
        log.info("update a server  :  {} ", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting a server by Id : {} ", id);
        serverRepository.deleteById(id);
        return true;
    }


    private String setServerImageUrl() {
        String[] imageNames = { "server1.png", "server2.png", "server3.png", "server4.png" };
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
    }

    private boolean isReachable(String ipAddress, int port, int timeOut) {
        try {
            try(Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ipAddress, port), timeOut);
            }
            return true;
        }catch (IOException exception){
            return false;
        }
    }
}
