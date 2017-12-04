package com.concretepage;

import java.util.Arrays;
import java.util.List;

import com.concretepage.jewelry.entity.Jewelry;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.concretepage.auth.entity.User;
public class SpringRestClient {
    public static final String REST_SERVICE_URI = "http://gimbarrwow2.azurewebsites.net/jewelry";

    /*
     * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
     */

    private static HttpHeaders getHeaders(){
        String plainCredentials="qwerty:qwerty";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public static  RestTemplate getRestTemplate() {
        // TODO: Fix the RestTemplate to be a singleton instance.
        RestTemplate restTemplate = new  RestTemplate();

        // Set the request factory.
        // IMPORTANT: This section I had to add for POST request. Not needed for GET
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        // Add converters
        // Note I use the Jackson Converter, I removed the http form converter
        // because it is not needed when posting String, used for multipart forms.
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        return restTemplate;
    }


    /*
     * Send a GET request to get list of all users.
   to create a new user.
     */
    private static void createUser() {

try{

      RestTemplate restTemplate = getRestTemplate();
        System.out.println("\nTesting create User API----------");

    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        Object user = new User("qwerty","qwerty",true);

    HttpEntity<Object> request = new HttpEntity<Object>(user);
    ResponseEntity<String> response = restTemplate.exchange(REST_SERVICE_URI+"/add", HttpMethod.POST, request, String.class);
    String result = response.getBody();
    System.out.println(result);}

     catch (HttpClientErrorException e) {
        System.out.println(e.getStatusCode());
        System.out.println(e.getResponseBodyAsString());
    }
    }

    /*
     * Send a GET request to get list of all users.
   to create a new user.
     */


    private static void getUser(){
        System.out.println("\nTesting getUser API----------");
        RestTemplate restTemplate = getRestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/user/q", HttpMethod.GET, request, User.class);
        User user = response.getBody();
        System.out.println(user);
    }

    private static void createGood() {

        try{

            RestTemplate restTemplate = getRestTemplate();
            System.out.println("\nTesting create User API----------");

            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            Object jewelry = new Jewelry("7389159856694","к863дв","кольцо","серебро 925", 1024);

            HttpEntity<Object> request = new HttpEntity<Object>(jewelry, getHeaders());

            ResponseEntity<String> response = restTemplate.exchange(REST_SERVICE_URI+"/add", HttpMethod.POST, request, String.class);
            String result = response.getBody();
            System.out.println(result);}

        catch (HttpClientErrorException e) {
            System.out.println(e.getStatusCode());
            System.out.println(e.getResponseBodyAsString());
        }
    }


    private static void getGood(){

        System.out.println("\nTesting getUser API----------");
        RestTemplate restTemplate = getRestTemplate();
        String barcode = "7350037006694";
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<Jewelry> response = restTemplate.exchange(REST_SERVICE_URI+"/get/"+barcode, HttpMethod.GET, request, Jewelry.class);
        Jewelry jewelry = response.getBody();
        System.out.println(response);
    }

    private static void getAllGoods(){

        HttpHeaders headers = getHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = getRestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Jewelry[]> responseEntity = restTemplate.exchange(REST_SERVICE_URI + "/test", HttpMethod.GET, requestEntity, Jewelry[].class);
        Jewelry[] jewelries = responseEntity.getBody();
        for(Jewelry jewelry : jewelries) {
            System.out.println("Щтрих код: "+jewelry.getBarCode()+", Артикль: "+jewelry.getArticle()
                    +", Категория: "+jewelry.getCategory() +", Цена: " +jewelry.getCost());}
    }



    /*
     * Send a DELETE request to delete all users.
     */



    public static void main(String args[]){
        getAllGoods();
    }

}