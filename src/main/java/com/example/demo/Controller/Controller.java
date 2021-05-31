package com.example.demo.Controller;


import com.example.demo.Model.ColumnVo;
import com.example.demo.Service.Service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


import io.swagger.v3.oas.models.media.XML;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


import javax.xml.bind.annotation.XmlSchema;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private Service service;


    @GetMapping("/GetAllEmployees")
    public String getAllEmployee(Model model) {
        model.addAttribute("employee", service.getAllEmployee());
        return "GetAllEmployees";
    }

    @GetMapping("/GetOneEmployee/{employeeId}")
    public String getOneEmployee(@RequestParam int employeeId, Model model) {
        model.addAttribute("employee", service.getOneEmployee(employeeId));
        return "GetOneEmployee";
    }

    @PostMapping("/AddEmployee")
    public String createEmployee(@RequestParam int id, @RequestParam String EnglishName, @RequestParam String ChineseName, Model model) {
        model.addAttribute("employee", service.createEmployee(id, EnglishName, ChineseName));
        return "AddEmployee";
    }

    @PostMapping("/UpdateEmployee")
    public String updateEmployee(@RequestParam int id, @RequestParam String EnglishName, @RequestParam String ChineseName, Model model) {

        model.addAttribute("employee", service.updateEmployee(id, EnglishName, ChineseName));
        return "UpdateEmployee";
    }


    @GetMapping("/DeleteEmployee/{employeeId}")
    public String deleteEmployee(@RequestParam int employeeId, Model model) {
        model.addAttribute("employee", service.deleteEmployee(employeeId));
        return "DeleteEmployee";

    }

    @GetMapping("/HomePage")
    public String index() {
        return "HomePage";
    }

    @PostMapping("/HomePage")
    public String UploadFile(@RequestParam("file") MultipartFile multipartFile, Model model) throws IOException {
        List<String> errorMsg = new ArrayList<>();
        if(!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String extension = fileName.substring(fileName.lastIndexOf(".")+1);
            if (extension.equals("csv")) {

                service.UploadFile(multipartFile);
                service.transferFile(service.UploadFile(multipartFile));
                service.csvJson(service.UploadFile(multipartFile));
                service.DeleteFile(multipartFile);
            }
            else{
                errorMsg.add("請上傳.csv檔");
                model.addAttribute("errorMsg", errorMsg);
                return "HomePage";
            }

        }else{
            errorMsg.add("請選擇檔案");
            model.addAttribute("errorMsg", errorMsg);
            return "HomePage";
        }

        return "redirect:/listFile";
    }

    @GetMapping("/listFile")
    public String ListFile(Model model) {
        List<String>  list = service.listFile();
        model.addAttribute("list", list);
        return "listFile";
    }

    @GetMapping("/DownloadFile/{filename}")
    public ResponseEntity<StreamingResponseBody> DownloadFile(@PathVariable String filename) throws FileNotFoundException {
        StreamingResponseBody body = service.DownloadFile(filename);
        String extension = filename.substring(filename.lastIndexOf(".")+1);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment;filename=output."+extension)
                .body(body);
    }

//    public static void main(String[] args) throws Exception {
//        File input = new File("C:\\Users\\jerrywang\\Desktop\\local\\addresses.csv");
//        File output = new File("C:\\Users\\jerrywang\\Desktop\\local\\output.xml");
//
//        CsvSchema csvSchema = CsvSchema.builder().build().withHeader();
////        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
//
//        CsvMapper csvMapper = new CsvMapper();
//
////        MappingIterator<Map<?, ?>> mappingIterator = csvMapper.readerFor(Map.class).with(csvSchema).readValues(input);
////        List<Map<?, ?>> data = mappingIterator.readAll();
//        List<Object> data = csvMapper.readerFor(Map.class).with(csvSchema).readValues(input).readAll();
//
//        XmlMapper mapper = new XmlMapper();
//
//        mapper.writerWithDefaultPrettyPrinter().writeValue(output, data);
//    }

    public static void main(String[] args) throws IOException {
        File input = new File("C:\\Users\\jerrywang\\Desktop\\local\\addresses.csv");
        File output = new File("C:\\Users\\jerrywang\\Desktop\\local\\output.xml");

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper
                .typedSchemaFor(ColumnVo.class)
                .withHeader()
                .withComments();
        MappingIterator<ColumnVo> mappingIterator = csvMapper
                .readerWithTypedSchemaFor(ColumnVo.class)
                .with(csvSchema)
                .readValues(input);

        List<ColumnVo> data = mappingIterator.readAll();
        XmlMapper mapper = new XmlMapper();
//        JsonMapper mapper = new JsonMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(output, data);


        data.forEach(System.out::println);
    }
}
















