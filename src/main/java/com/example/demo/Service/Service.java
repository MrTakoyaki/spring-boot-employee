package com.example.demo.Service;

import com.example.demo.Dao.JDBC;
import com.example.demo.Model.ColumnVo;
import com.example.demo.Model.Vo;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service


public class Service implements Interface {


//    ArrayList<Vo> volist = new ArrayList<Vo>() {
//        {
//            add(new Vo(1, "Tom", "吳銘祥"));
//            add(new Vo(2, "Freddy", "范建銘"));
//            add(new Vo(3, "Ben", "徐晨峰"));
//            add(new Vo(4, "Sean", "林盛祥"));
//            add(new Vo(5, "CT", "呂金定"));
//            add(new Vo(6, "J.C.", "薛任哲"));
//        }
//    };
JDBC dao = new JDBC();
    public ArrayList<Vo> getAllEmployee() {

        return dao.getAllEmployee();
    }

    public Vo getOneEmployee(int employeeId) {

        return dao.getOneEmployee(employeeId);
    }

    public Vo createEmployee(int id, String EnglishName, String ChineseName) {
        Vo employeeVo = new Vo(id, EnglishName, ChineseName);
        employeeVo.setId(id);
        employeeVo.setEnglishName(EnglishName);
        employeeVo.setChineseName(ChineseName);
        dao.createEmployee(id,EnglishName,ChineseName);
        return employeeVo;
    }

    public Vo updateEmployee(int id, String EnglishName, String ChineseName) {
        dao.updateEmployee(id, EnglishName, ChineseName);
        Vo employeeVo = new Vo(id, EnglishName, ChineseName);
        employeeVo.setId(id);
        employeeVo.setEnglishName(EnglishName);
        employeeVo.setChineseName(ChineseName);
        return employeeVo;
    }

    public ArrayList<Vo> deleteEmployee(int employeeId) {
        return dao.deleteEmployee(employeeId);
    }

    public File UploadFile(MultipartFile multipartFile) throws IOException {
        Path path = Paths.get("D:\\democsv\\upload");
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            Files.copy(multipartFile.getInputStream(), path.resolve(multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
        }
        return new File(path + "\\" + multipartFile.getOriginalFilename());
    }

    public XmlMapper transferFile(File input) throws IOException {
        String fileName = input.getName();
        String noExtension = fileName.substring(0,fileName.lastIndexOf("."));
        File output = new File("D:\\democsv\\upload\\"+noExtension+".xml");
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper
                .typedSchemaFor(ColumnVo.class)
                .withHeader()
                .withComments();
        MappingIterator<Vo> mappingIterator = csvMapper
                .readerWithSchemaFor(ColumnVo.class)
                .with(csvSchema)
                .readValues(input);

        List<Vo> data = mappingIterator.readAll();
        XmlMapper mapper = new XmlMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(output, data);
//        data.forEach(System.out::println);
        return mapper;
    }

    public StreamingResponseBody DownloadFile(String filename) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("D:\\democsv\\upload\\" + filename);
        return outputStream -> FileCopyUtils.copy(inputStream, outputStream);
    }

    public List<String> listFile() {
        List<String> filenames = new ArrayList<>();
        File file = new File("D:\\democsv\\upload\\");
        File[] files = file.listFiles();
        assert files != null;
        for (File f:files) {
            filenames.add(f.getName());
        }
        return filenames;
    }

    public void DeleteFile(MultipartFile input){
        String fileName = input.getOriginalFilename();
        String noExtension = fileName.substring(0, fileName.lastIndexOf("."));
        File path = new File("D:\\democsv\\upload\\" + noExtension + ".csv");
        path.delete();
    }

    public JsonMapper csvJson(File input) throws IOException {
        String fileName = input.getName();
        String noExtension = fileName.substring(0, fileName.lastIndexOf("."));
        File output = new File("D:\\democsv\\upload\\" + noExtension +".json");
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper
                .typedSchemaFor(ColumnVo.class)
                .withHeader()
                .withComments();
        MappingIterator<Vo> mappingIterator = csvMapper
                .readerWithSchemaFor(ColumnVo.class)
                .with(csvSchema)
                .readValues(input);

        List<Vo> data = mappingIterator.readAll();
        JsonMapper mapper = new JsonMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(output, data);
        return mapper;
    }

//    public static File convertTest(MultipartFile file) throws IOException {
//        File convFile = new File(file.getOriginalFilename());
//        convFile.createNewFile();
//        FileOutputStream fos = new FileOutputStream(convFile);
//        fos.write(file.getBytes());
//        fos.close();
//        return convFile;
//    }

//    public ByteArrayOutputStream inputFile(MultipartFile file) throws IOException {
//        BufferedInputStream is = new BufferedInputStream(file.getInputStream()); // 建立檔案輸入串流
//        ByteArrayOutputStream os = new ByteArrayOutputStream(); // 建立ByteArray輸出串流
//        int result;
//        while ((result = is.read()) != -1) { // 從輸入串流讀取資料
//            os.write((byte) result); // 將讀取的資料寫出至輸出串流
//        }
//        return os;
//    }
}
