package Modules.User;

import java.io.*;
import Modules.User.controller.*;
import Modules.User.model.User;
import Utils.Constants;


import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UserView{

    private LogIn li;

    public boolean signUp(Statement stmt) throws SQLException {

        String name;
        String email;
        char rl;
        String address;
        String password,pswd;
        SignUp su = new SignUp();


        Scanner sc= new Scanner(System.in);
        Constants.printAndSpeak("Please enter your name: ");
        name=sc.nextLine();
        Constants.printAndSpeak("Please enter your email: ");
        email=sc.nextLine();
        Constants.printAndSpeak("Please enter 'c' for customer role, enter 'v' for vendor role : ");
        rl=sc.nextLine().charAt(0);
        while(rl!='v' && rl!='c')
        {
            Constants.printAndSpeak("Please Enter valid input : 'c' for customer role, Enter 'v' for vendor role : ");
            rl=sc.nextLine().charAt(0);
        }
        Constants.printAndSpeak("Please Enter your address: ");
        address=sc.nextLine();
        Constants.printAndSpeak("Please Enter your password: ");
        if(System.console()!=null) {
            char[] passwordChars = System.console().readPassword();
            password = new String(passwordChars);
        }else{
            password=sc.nextLine();
        }

        Constants.printAndSpeak("Please Reenter your password: ");
        if(System.console()!=null) {
            char[] passwordChars = System.console().readPassword();
            pswd = new String(passwordChars);
        }else{
            pswd=sc.nextLine();
        }

        if(!password.equals(pswd))
        {
            Constants.printAndSpeak("Sorry, password doesn't match");
            return false;
        }


        if(su.checkIsUserExist(stmt,email,rl))
        {
            Constants.printAndSpeak("Sorry, user already exist");
            return false;
        }
        else
        {
            if(su.register(stmt,email,rl,name,password,address)!=1)
            {
                Constants.printAndSpeak("Something went wrong, we can not sign you up");
                return false;
            }
        }

        Constants.printAndSpeak("Thank you, for registration. Now you can log in to the system.");
        return true;


    }

    public boolean logIn(Statement stmt) throws SQLException {
        String email;
        String password;
        char rl;

        Scanner sc= new Scanner(System.in);
        Constants.printAndSpeak("\nPlease Enter your email: ");
        email=sc.nextLine();
        Constants.printAndSpeak("Please Enter your password: ");
        if(System.console()!=null) {
            char[] passwordChars = System.console().readPassword();
            password = new String(passwordChars);
        }else{
            password=sc.nextLine();
        }

        Constants.printAndSpeak("Please Enter 'c' for customer role, Enter 'v' for vendor role, Enter 'a' for admin role : ");
        rl=sc.nextLine().charAt(0);
        while(rl!='v' && rl!='c' && rl!='a')
        {
            Constants.printAndSpeak("Please Enter valid input : 'c' for customer role, Enter 'v' for vendor role , Enter 'a' for admin role : ");
            rl=sc.nextLine().charAt(0);
        }

        SignUp su = new SignUp();
        li = new LogIn();

        if(su.checkIsUserExist(stmt,email,rl))
        {
            if(li.isCredentialCorrect(stmt,email,rl,password))
            {
                //System.out.println("Welcome to Parkingpool");
                return true;
            }
            else{
                Constants.printAndSpeak("incorrect credentials");
            }

        }
        else{
            Constants.printAndSpeak("User does not exist");
        }

        return false;
    }

    public User getUser()
    {
        return li.user;
    }
}