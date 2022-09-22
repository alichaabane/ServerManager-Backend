package com.myApplication.server.service.impl;

import com.myApplication.server.enumeration.Status;
import com.myApplication.server.model.Server;
import com.myApplication.server.repository.ServerRepository;
import com.myApplication.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.Random;

import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;

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
        String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
    }

    private boolean isReachable(String ipAddress, int port, int timeOut) {
        try {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ipAddress, port), timeOut);
            }
            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    @Override
    public byte[] exportDataToExcel() {
        String[] columns = new String[]{"Id", "ipAddress", "name", "memory", "type", "imageUrl", "status"};
        Sheet sheet;

        try (Workbook workbook = new XSSFWorkbook()) {
            Collection<Server> servers = this.list(30);
            sheet = workbook.createSheet("Report of Servers");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            sheet.setColumnWidth(0, 2000);
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 8000);
            sheet.setColumnWidth(3, 6000);
            sheet.setColumnWidth(4, 8000);
            sheet.setColumnWidth(5, 12000);
            sheet.setColumnWidth(6, 8000);
            Font headerFont = workbook.createFont();
            headerFont.setFontName("Poppins");
            headerFont.setFontHeightInPoints((short)11);
            headerFont.setColor(IndexedColors.BLACK1.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setAlignment(CENTER);
            // Row for Header
            Row headerRow = sheet.createRow(0);
            // Header
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIndex = 1;
            for (Server server : servers) {
                System.out.println(server);
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(server.getId());
                row.createCell(1).setCellValue(server.getIpAddress());
                row.createCell(2).setCellValue(server.getName());
                row.createCell(3).setCellValue(String.format("%.2f", server.getMemory()) + " GB");
                row.createCell(4).setCellValue(server.getType());
                row.createCell(5).setCellValue(server.getImageUrl());
                row.createCell(6).setCellValue((server.getStatus().equals(Status.SERVER_DOWN) ? "DOWN" : "UP"));
            }
            workbook.write(out);
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
