package tests;

import dto.ContactDtoLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import java.io.IOException;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

public class DeleteContactsTests extends ApplicationManager {

    UserDto user = new UserDto("asd@qwe.com", "Privet$123457890");
    AddPage addPage;

    @BeforeMethod
    public void login(){
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user).clickBtnLoginPositive();
        addPage = clickButtonsOnHeader(HeaderMenuItem.ADD);
    }


    @Test
    public void deleteContactByIDTestAPI() throws IOException {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive();
        String contactID = "3598b9e7-229e-4789-95ea-2192362edf94";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpDelete request = new HttpDelete("https://contactapp-telran-backend.herokuapp.com/v1/contacts/"+contactID);
        request.setHeader("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYXNkQHF3ZS5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTcyNzk1NTMzNCwiaWF0IjoxNzI3MzU1MzM0fQ.zdYUPjobZn7H6QL8YDMkFCcgE-D-QP87LUt0Ipk6_hE");
        CloseableHttpResponse response = client.execute(request);
        int res = response.getCode();
        System.out.println("Response code: " + res );
        assert response.getCode() == 200;
    }

    @Test
    public void deleteContact() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .clickBtnRemoveContact()
                .isContactDelete(contact));
    }
}
