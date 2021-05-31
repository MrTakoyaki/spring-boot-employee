package com.example.demo.Service;

import com.example.demo.Model.Vo;

import java.util.ArrayList;

public interface Interface {
    public ArrayList<Vo> getAllEmployee() throws RuntimeException;

    public Vo getOneEmployee(int employeeId);

    public Vo createEmployee(int id,String EnglishName, String ChineseName);

    public Vo updateEmployee(int id, String EnglishName , String ChineseName);

    public ArrayList<Vo> deleteEmployee(int employeeId);

}
