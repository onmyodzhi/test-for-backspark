package com.sidorov.backspark.socks.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.sidorov.backspark.exceptions.files.CSVFormatException;
import com.sidorov.backspark.exceptions.files.FileProcessingException;
import com.sidorov.backspark.exceptions.socks.InvalidSockDataException;
import com.sidorov.backspark.exceptions.socks.SockAlreadyExistsException;
import com.sidorov.backspark.exceptions.socks.SockNotFoundException;
import com.sidorov.backspark.socks.models.DTOs.SockDTO;
import com.sidorov.backspark.socks.models.Sock;
import com.sidorov.backspark.socks.models.mappers.SockMapper;
import com.sidorov.backspark.socks.repositories.SockRepository;
import com.sidorov.backspark.socks.specifications.filters.SockFilter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SockService {

    SockRepository sockRepository;
    SockMapper sockMapper;

    public Long getCountOfSockByFilter(Map<String, String> params) {
        Specification<Sock> spec = getSocksBySpecifications(params);
        return sockRepository.findAll(spec)
                .stream()
                .mapToLong(Sock::getCount)
                .sum();
    }

    public void increaseCountOfSocks(Map<String, String> params, Long amount) {
        Specification<Sock> spec = getSocksBySpecifications(params);

        Sock sock = sockRepository.findOne(spec)
                .orElseThrow(() -> new SockNotFoundException(params));

        Long currentCount = sock.getCount() + amount;
        sock.setCount(currentCount);

        sockRepository.save(sock);
    }

    public void decreaseCountOfSocks(Map<String, String> params, Long amount) {
        Specification<Sock> spec = getSocksBySpecifications(params);
        Sock sock = sockRepository.findOne(spec)
                .orElseThrow(() -> new SockNotFoundException(params));

        Long currentCount = sock.getCount() - amount;
        sock.setCount(currentCount);

        sockRepository.save(sock);
    }

    public SockDTO editSock(Long id, SockDTO sock) {
        sockRepository.findById(id)
                .orElseThrow(() -> new SockNotFoundException(id));

        Sock newSocks = sockMapper.toEntity(sock);
        if (sockRepository.existsSock(newSocks).isEmpty()) {
            newSocks.setId(id);
            newSocks = sockRepository.save(newSocks);
        } else throw new SockAlreadyExistsException();

        return sockMapper.toDTO(newSocks);
    }

    @Transactional
    public void processSockBatch(MultipartFile file) {
        List<String[]> records = readFile(file);

        for (String[] record : records) {
            Sock sock = parseSock(record);
            saveSock(sock);
        }
    }

    private List<String[]> readFile(MultipartFile file) {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CSVReader csvReader = new CSVReader(reader);
            return csvReader.readAll();
        } catch (IOException e) {
            throw new FileProcessingException(file);
        } catch (CsvException e) {
            throw new CSVFormatException(file);
        }
    }

    private Sock parseSock(String[] record) {
        Sock sock = new Sock();

        if (record.length < 3) {
            throw new InvalidSockDataException(record);
        }

        try {
            sock.setColor(record[0]);
            sock.setCottonPercentage(Short.parseShort(record[1]));
            sock.setCount(Long.parseLong(record[2]));
        } catch (NumberFormatException e) {
            throw new InvalidSockDataException(record);
        }

        return sock;
    }

    private void saveSock(Sock sock) {
        Optional<Sock> optionalSock = sockRepository.existsSock(sock);
        if (optionalSock.isEmpty()) {
            sockRepository.save(sock);
        } else {
            Sock existingSock = optionalSock.get();

            Long existingQuantity = existingSock.getCount();
            existingQuantity += sock.getCount();

            existingSock.setCount(existingQuantity);

            sockRepository.save(existingSock);
        }
    }

    private Specification<Sock> getSocksBySpecifications(Map<String, String> params) {
        SockFilter sockFilter = new SockFilter(params);
        return sockFilter.getSpec();
    }
}
