package okhttp;

import dto.ErrorMessageDto;
import dto.TokenDto;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.annotations.Test;
import static utils.PropertiesReader.getProperty;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.asserts.SoftAssert;

public class LoginTests implements BaseApi {
    SoftAssert softAssert = new SoftAssert();

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
}