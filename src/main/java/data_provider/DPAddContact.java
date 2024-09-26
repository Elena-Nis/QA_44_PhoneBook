package data_provider;

import dto.ContactDtoLombok;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static utils.RandomUtils.*;

public class DPAddContact {

    @DataProvider
    public ContactDtoLombok[] addNewContactDP(){
        ContactDtoLombok contact1 = ContactDtoLombok.builder()
                .name(generateString(0))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactDtoLombok contact2 = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(0))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactDtoLombok contact3 = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateString(0))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactDtoLombok contact4= ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12) + "@")
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactDtoLombok contact5 = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateString(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactDtoLombok contact6 = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(0))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactDtoLombok contact7 = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateString(5) + "@")
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactDtoLombok contact8 = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12) + "Ф")
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactDtoLombok contact9 = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email("grom@net.com")
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactDtoLombok contact10 = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(0))
                .description(generateString(10))
                .build();
        ContactDtoLombok contact11 = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(0))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactDtoLombok contact12 = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generateString(11))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactDtoLombok contact13 = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(9))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactDtoLombok contact14 = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(16))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();

        return new ContactDtoLombok[]{contact1, contact2, contact3, contact4, contact5, contact6, contact7,
        contact8, contact9, contact10, contact11, contact12, contact13, contact14};
    }

    @DataProvider
    public Iterator<ContactDtoLombok> addNewContactDPFile(){
        List<ContactDtoLombok> contactList = new ArrayList<>();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/test/resources/wrong_email.csv"));
            String line = bufferedReader.readLine();
            while (line != null){
                //frodo1,baggins1,1234567890,frodo1mail.com,address 1,description1
                String[] splitArray = line.split(",");
                contactList.add(ContactDtoLombok.builder()
                        .name(splitArray[0])
                        .lastName(splitArray[1])
                        .phone(splitArray[2])
                        .email(splitArray[3])
                        .address(splitArray[4])
                        .description(splitArray[5])
                        .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contactList.listIterator();
    }
}