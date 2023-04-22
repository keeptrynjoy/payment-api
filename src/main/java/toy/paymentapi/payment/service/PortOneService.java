package toy.paymentapi.payment.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import toy.paymentapi.payment.dto.PortOneAccessDto;

import static toy.paymentapi.payment.service.PortOneRestApi.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortOneService {

    @Value("${imp_key}")
    private String key;

    @Value("${imp_secret}")
    private String secret;

    public PortOneAccessDto getToken(){
        RestTemplate restTemplate = new RestTemplate();

        //create post-entity
        HttpEntity<PortOneAccessDto> request = new HttpEntity<>(new PortOneAccessDto(key, secret));

        ResponseEntity<String> parentResponse = restTemplate
                .exchange(creatApiUrl(AUTHENTICATE.getUrl()), HttpMethod.POST, request, String.class);

        if(!parentResponse.getStatusCode().isError()){
            Gson gson = new Gson();

            return gson.fromJson(parentResponse.getBody(), PortOneAccessDto.class);
        }

        throw new RuntimeException("An access token was not issued from Port One.");
    }


    private String creatApiUrl(String resource){

        StringBuilder sb = new StringBuilder();

        sb.append(HOST.getUrl());
        sb.append(resource);

        String getTokenUrl = sb.toString();

        return getTokenUrl;
    }
}
