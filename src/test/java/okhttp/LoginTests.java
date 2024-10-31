package okhttp;

import dto.ErrorMessageDto;
import dto.TokenDto;
import dto.UserDto;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.generateEmail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.asserts.SoftAssert;

public class LoginTests implements BaseApi {
    SoftAssert softAssert = new SoftAssert();
    UserDto user;

    @Test
    public void loginPositiveTestProperties() throws IOException {
        Map<String, String> emailPassword = new HashMap<>();
        emailPassword.put("username", getProperty("data.properties", "email"));
        emailPassword.put("password",getProperty("data.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(emailPassword), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        String responseBody = null;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            responseBody = response.body().string();
            TokenDto token = GSON.fromJson(responseBody, TokenDto.class);
            if (token.getToken() == null) {
                throw new NullPointerException("Token is null");
            }
            softAssert.assertEquals(response.code(), 200);
            System.out.println("**** Response Body ****" + responseBody);
        } catch (IOException e) {
            // IO exception - handling input/output exceptions
            ErrorMessageDto errorMessage = GSON.fromJson(responseBody, ErrorMessageDto.class);
            System.out.println("**** errorMessage **** " + errorMessage);
        } catch (NullPointerException e) {
            // Null pointer exception - handling an object reference that is null
            ErrorMessageDto errorMessage = GSON.fromJson(responseBody, ErrorMessageDto.class);
            softAssert.assertEquals(errorMessage.getStatus(), 401);
            softAssert.assertEquals(errorMessage.getError(), "Unauthorized");
            softAssert.assertTrue(errorMessage.getMessage().toString().contains("Login or Password incorrect"));
            System.out.println("**** Status, Error, Message **** " + errorMessage.getStatus() + " " + errorMessage.getError() + " " + errorMessage.getMessage());
        } catch (Exception e) {
            // Exception - handling all other exceptions
            System.out.println("**** Unknown error ****");
        }
        softAssert.assertAll();
    }

    @Test
    public void loginNegativeTest_wrongPassword() throws IOException {
        Map<String, String> emailPassword = new HashMap<>();
        emailPassword.put("username", getProperty("data.properties", "email"));
        emailPassword.put("password", "Qwerty123!----");
        RequestBody requestBody = RequestBody.create(GSON.toJson(emailPassword), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        String responseBody = null;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            responseBody = response.body().string();
            TokenDto token = GSON.fromJson(responseBody, TokenDto.class);
            if (token.getToken() == null) {
                throw new NullPointerException("Token is null");
            }
            softAssert.assertEquals(response.code(), 401);
            System.out.println("**** Response Body ****" + responseBody);
        } catch (IOException e) {
            // IO exception - handling input/output exceptions
            ErrorMessageDto errorMessage = GSON.fromJson(responseBody, ErrorMessageDto.class);
            System.out.println("**** errorMessage **** " + errorMessage);
        } catch (NullPointerException e) {
            // Null pointer exception - handling an object reference that is null
            ErrorMessageDto errorMessage = GSON.fromJson(responseBody, ErrorMessageDto.class);
            softAssert.assertEquals(errorMessage.getStatus(), 401);
            softAssert.assertEquals(errorMessage.getError(), "Unauthorized");
            softAssert.assertTrue(errorMessage.getMessage().toString().contains("Login or Password incorrect"));
            System.out.println("**** Status, Error, Message **** " + errorMessage.getStatus() + " " + errorMessage.getError() + " " + errorMessage.getMessage());
        } catch (Exception e) {
            // Exception - handling all other exceptions
            System.out.println("**** Unknown error ****");
        }
        softAssert.assertAll();
    }

    @Test
    public void loginNegativeTest_wrongEmailUnregUser() throws IOException {
        Map<String, String> emailPassword = new HashMap<>();
        emailPassword.put("username", "kit@mail.com");
        emailPassword.put("password",getProperty("data.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(emailPassword), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        String responseBody = null;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            responseBody = response.body().string();
            TokenDto token = GSON.fromJson(responseBody, TokenDto.class);
            if (token.getToken() == null) {
                throw new NullPointerException("Token is null");
            }
            softAssert.assertEquals(response.code(), 401);
            System.out.println("**** Response Body ****" + responseBody);
        } catch (IOException e) {
            // IO exception - handling input/output exceptions
            ErrorMessageDto errorMessage = GSON.fromJson(responseBody, ErrorMessageDto.class);
            System.out.println("**** errorMessage **** " + errorMessage);
        } catch (NullPointerException e) {
            // Null pointer exception - handling an object reference that is null
            ErrorMessageDto errorMessage = GSON.fromJson(responseBody, ErrorMessageDto.class);
            softAssert.assertEquals(errorMessage.getStatus(), 401);
            softAssert.assertEquals(errorMessage.getError(), "Unauthorized");
            softAssert.assertTrue(errorMessage.getMessage().toString().contains("Login or Password incorrect"));
            System.out.println("**** Status, Error, Message **** " + errorMessage.getStatus() + " " + errorMessage.getError() + " " + errorMessage.getMessage());
        } catch (Exception e) {
            // Exception - handling all other exceptions
            System.out.println("**** Unknown error ****");
        }
        softAssert.assertAll();
    }

    @Test
    public void loginNegativeTest_wrongEmailWOAt() throws IOException {
        Map<String, String> emailPassword = new HashMap<>();
        emailPassword.put("username", "qa_mailmail.com");
        emailPassword.put("password", getProperty("data.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(emailPassword), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        String responseBody = null;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            responseBody = response.body().string();
            TokenDto token = GSON.fromJson(responseBody, TokenDto.class);
            if (token.getToken() == null) {
                throw new NullPointerException("Token is null");
            }
            softAssert.assertEquals(response.code(), 401);
            System.out.println("**** Response Body ****" + responseBody);
        } catch (IOException e) {
            // IO exception - handling input/output exceptions
            ErrorMessageDto errorMessage = GSON.fromJson(responseBody, ErrorMessageDto.class);
            System.out.println("**** errorMessage **** " + errorMessage);
        } catch (NullPointerException e) {
            // Null pointer exception - handling an object reference that is null
            ErrorMessageDto errorMessage = GSON.fromJson(responseBody, ErrorMessageDto.class);
            softAssert.assertEquals(errorMessage.getStatus(), 401);
            softAssert.assertEquals(errorMessage.getError(), "Unauthorized");
            softAssert.assertTrue(errorMessage.getMessage().toString().contains("Login or Password incorrect"));
            System.out.println("**** Status, Error, Message **** " + errorMessage.getStatus() + " " + errorMessage.getError() + " " + errorMessage.getMessage());
        } catch (Exception e) {
            // Exception - handling all other exceptions
            System.out.println("**** Unknown error ****");
        }
        softAssert.assertAll();
    }
    //Alexey's tests

    @BeforeMethod
    public void registrationUser() {
        user = new UserDto(generateEmail(10), "Qwerty123!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("registration response is successful --> " + response.isSuccessful());
    }

    @Test
    public void loginPositiveTest() {
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void loginNegativeTest_wrongPassword_401() throws IOException {
        user.setPassword("another_pass");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(response.code() == 401){
            ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            Assert.assertTrue(errorMessageDto.getMessage().toString().equals("Login or Password incorrect"));
        }else {
            Assert.fail("something went wrong, response code --> "+response.code());
        }
    }
}