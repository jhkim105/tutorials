package com.example.beanio.example;

import lombok.Getter;
import java.util.Date;
import lombok.ToString;

@Getter
@ToString
public class Employee {
    String firstName;
    String lastName;
    String title;
    int salary;
    Date hireDate;
}