package Modules.User.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTest {


    //here we test that, there should be same hashcode for same input string
    @Test
    void getHashCodeTest() {
        assertTrue(Hash.getHashCode("abcd").equals(Hash.getHashCode("abcd")));
    }
}