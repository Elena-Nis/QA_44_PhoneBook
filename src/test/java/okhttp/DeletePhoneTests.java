package okhttp;

import com.google.gson.Gson;
import dto.*;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

public class DeletePhoneTests implements BaseApi {
    TokenDto token;
    String contactId;
    SoftAssert softAssert = new SoftAssert();
    ErrorMessageDto responseError;

    @BeforeClass
    public void loginUser() {
        UserDto user = new UserDto(getProperty("data.properties", "email"),
                getProperty("data.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            token = GSON.fromJson(response.body().string(), TokenDto.class);
            System.out.println(token.getToken());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeMethod
    public void addNewContact() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH)
                .addHeader("Authorization", token.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            System.out.println("add new contact response code --> " + response.code());
            String message = (GSON.fromJson(response.body().string(), ResponseMessageDto.class)).getMessage();
            System.out.println(message);
            contactId = message.substring(23);
            System.out.println(contactId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deletePositiveTest() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH + "/" + contactId)
                .addHeader("Authorization", token.getToken())
                .delete()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(response.isSuccessful());
    }

    //******************************* HW_15 *******************************************

    @Test
    public void deleteNegativeTestBadRequest_400() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH + "/" + contactId + 134)
                .addHeader("Authorization", token.getToken())
                .delete()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            responseError = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            System.out.println("**** Response Bad Request 400 **** " + responseError);
            System.out.println("**** Error **** " + responseError.getError());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        softAssert.assertEquals(response.code(), 400);
        softAssert.assertEquals(responseError.getError(), "Bad Request");
        softAssert.assertAll();
    }

    @Test
    public void deleteNegativeTestServerError_500() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH + "/")
                .addHeader("Authorization", token.getToken())
                .delete()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            responseError = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            System.out.println("**** Response Internal Server Error 500 **** " + responseError);
            System.out.println("**** Error **** " + responseError.getError());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        softAssert.assertEquals(response.code(), 500);
        softAssert.assertEquals(responseError.getError(), "Internal Server Error");
        softAssert.assertAll();

    }

    @Test
    public void deleteNegativeTestForbiddenAccess_403() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH + "/" + contactId)
                .addHeader("Authorization", "")
                .delete()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            responseError = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            System.out.println("**** Response Forbidden Access Error 403 **** " + responseError);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 403);
    }

    @Test
    public void deleteNegativeTestUnauthorized_401() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH + "/" + contactId)
                .addHeader("Authorization", "invalid token")
                .delete()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            responseError = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            System.out.println("**** Response Unauthorized Error 401 **** " + responseError);
            System.out.println("**** Error **** " + responseError.getError());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        softAssert.assertEquals(response.code(), 401);
        softAssert.assertEquals(responseError.getError(), "Unauthorized");
        softAssert.assertAll();

    }

    @Test
    public void deleteNegativeTestBadRequest_404() {
        Request request = new Request.Builder()
                .url("https://contactapp-backend.herokuapp.com/v1/contacts/" + token.getToken())
                .addHeader("Authorization", token.getToken())
                .delete()
                .build();
        Response response;

        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            System.out.println("**** Response code **** " + response.code());
            System.out.println(response.body().string());

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        Assert.assertEquals(response.code(), 404);
    }
}