package com.example.demo.Dao;

import com.example.demo.Model.Vo;
import com.example.demo.Service.Interface;

import java.sql.*;
import java.util.ArrayList;

public class JDBC implements Interface {

    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/maindatabase";
    String username = "jerry";
    String password = "Jerry123";

    private static String GET_ALL_EMPLOYEE = "select * from Employees";
    private static String GET_ONE_EMPLOYEE = "select * from Employees where id = ?";
    private static String CREATE_EMPLOYEE = "insert into employees (id,EnglishName,ChineseName)value(?,?,?)";
    private static String UPDATE_EMPLOYEE = "update employees set EnglishName = ? , ChineseName = ? where id = ?";
    private static String DELETE_EMPLOYEE = "delete from employees where id = ?";

    @Override
    public ArrayList<Vo> getAllEmployee() {
        ArrayList<Vo> EmployeeList = new ArrayList<Vo>();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            pstmt = con.prepareStatement(GET_ALL_EMPLOYEE);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Vo EmployeeVo = new Vo();

                EmployeeVo.setId(rs.getInt("id"));
                EmployeeVo.setEnglishName(rs.getString("EnglishName"));
                EmployeeVo.setChineseName(rs.getString("ChineseName"));
                EmployeeList.add(EmployeeVo);

            }
            pstmt.executeQuery();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return EmployeeList;
    }

    @Override
    public Vo getOneEmployee(int employeeId) {
        Vo vo = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            pstmt = con.prepareStatement(GET_ONE_EMPLOYEE);
            pstmt.setInt(1, employeeId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                vo = new Vo();

                vo.setId(rs.getInt("id"));
                vo.setEnglishName(rs.getString("EnglishName"));
                vo.setChineseName(rs.getString("ChineseName"));
            }
            pstmt.executeQuery();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return vo;
    }

    public Vo createEmployee(int id, String EnglishName, String ChineseName) {
        Vo vo = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            pstmt = con.prepareStatement(CREATE_EMPLOYEE);

            pstmt.setInt(1, id);
            pstmt.setString(2, EnglishName);
            pstmt.setString(3, ChineseName);
            pstmt.executeUpdate();

            vo = new Vo();
            vo.setId(id);
            vo.setEnglishName(EnglishName);
            vo.setChineseName(ChineseName);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException(se.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
        return vo;
    }

    @Override
    public Vo updateEmployee(int id, String EnglishName, String ChineseName) {
        Vo vo = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            pstmt = con.prepareStatement(UPDATE_EMPLOYEE);

            pstmt.setString(1, EnglishName);
            pstmt.setString(2, ChineseName);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();

            vo = new Vo();
            vo.setId(id);
            vo.setEnglishName(EnglishName);
            vo.setChineseName(ChineseName);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException(se.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return vo;
    }

    @Override
    public ArrayList<Vo> deleteEmployee(int employeeId) {
        ArrayList<Vo> list = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            pstmt = con.prepareStatement(DELETE_EMPLOYEE);

            pstmt.setInt(1, employeeId);
            pstmt.executeUpdate();

            list = new JDBC().getAllEmployee();


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException(se.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return list;
    }


    public static void main(String[] args) {
        JDBC dao = new JDBC();

//        List<Object> selectAllList = dao.getAllEmployee();
//        for (Object obj : selectAllList) {
//            System.out.println(obj);
//        }

        ///////////////////////////////////////////
//        Object selectOneList = dao.getOneEmployee(1);
//        System.out.println(selectOneList);

        //////////////////////////////////////////

//        Vo list = dao.createEmployee(8, "jerry", "王章權");
//        System.out.println(list);

        ///////////////////////////////////////////////

//        Vo vo = dao.updateEmployee(5, "update", "更新");
//        System.out.println(vo);
        ////////////////////////////////////////////////

        ArrayList<Vo> list = dao.deleteEmployee(4);
        System.out.println(list);
    }
}
