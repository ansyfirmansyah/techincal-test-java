package com.example.interview.service;

import com.example.interview.dto.SampleResponseDto;
import com.example.interview.entity.SampleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class SampleService {

    public List<SampleResponseDto> getAllData(Pageable pageable) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<SampleResponse[]> result = restTemplate.exchange(
                "https://jsonplaceholder.typicode.com/posts",
                HttpMethod.GET,
                entity,
                SampleResponse[].class
        );
        System.out.println(result);
        List<SampleResponse> sampleResponses = Arrays.asList(result.getBody());
        List<SampleResponseDto> sampleResponseDtos = new ArrayList<>();
        int startIndex = pageable.getPageNumber() * pageable.getPageSize();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), sampleResponses.size());
        for (int i = startIndex; i < endIndex; i++) {
            sampleResponseDtos.add(new SampleResponseDto(
                    sampleResponses.get(i).getId(),
                    sampleResponses.get(i).getTitle()
            ));
        }
        return sampleResponseDtos;
    }
}
