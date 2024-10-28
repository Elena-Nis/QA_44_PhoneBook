package okhttp;

import dto.ErrorMessageDto;
import dto.TokenDto;
import dto.UserDto;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static utils.RandomUtils.*;

public class RegistrationTests implements BaseApi {

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void registarationPositiveTest() {
        UserDto user = new UserDto(generateEmail(10), "Qwerty123!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_PATH)
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
    public void registarationPositiveTest_validateToken() throws IOException {
        UserDto user = new UserDto(generateEmail(10), "Qwerty123!");
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
        if (response.isSuccessful()) {
            TokenDto token = GSON.fromJson(response.body().string(), TokenDto.class);
            System.out.println(token.getToken());
            Assert.assertEquals(response.code(), 200);
        } else {
            ErrorMessageDto errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            System.out.println(errorMessage.getError());
            Assert.fail("responce code -->" + response.code());
        }
    }
        @Test
        public void registarationNegativeTest_ExRes400 () throws IOException {
            UserDto user = new UserDto(generateEmail(10), "Qwerty123");
            RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
            Request request = new Request.Builder()
                    .url(BASE_URL+REGISTRATION_PATH)
                    .post(requestBody)
                    .build();
            Response response;
            try {
                response = OK_HTTP_CLIENT.newCall(request).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ErrorMessageDto errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
           softAssert.assertEquals(response.code(),400);
           softAssert.assertEquals(errorMessage.getStatus(),400);
           softAssert.assertEquals(errorMessage.getError(),"Bad Request");
           softAssert.assertTrue(errorMessage.getMessage().toString().contains("password= At least 8 characters"));
           softAssert.assertAll();


        }

    }

