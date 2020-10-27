package com.task2.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task2.model.ImgEntity;
import com.task2.model.Report;
import com.task2.model.User;
import com.task2.repository.ReportRepository;
import com.task2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    public static final String UPLOAD_DIR = "/opt/files/";
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping
    @CrossOrigin
    public ResponseEntity<?> report(@RequestParam(value = "title",required = false) String title,
                                    @RequestParam(value = "des",required = false) String des,
                                    @RequestParam(value = "file",required = false) MultipartFile[] files) throws JsonProcessingException {

//        if (file.isEmpty()) {
//        return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
        String a = "";
        ArrayList<String> fileNames = new ArrayList<>();

        for (MultipartFile file : files){
            HashMap myMap = new HashMap();
            String fileName =new Date().getTime() + StringUtils.cleanPath(file.getOriginalFilename()).replaceAll("\\s+","") ;
            try {
                 Path path = Paths.get(UPLOAD_DIR + fileName);

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//                fileNames.add(new ImgEntity(fileName));
                fileNames.add(fileName.replaceAll("\\s+",""));
//                fileNames.add(path.toString().replaceAll("\\s+",""));
                    a+=fileName+',';
                    myMap.put("imgname",fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String aPlus = a.substring(0, a.length()-1);




        Report newReport = new Report();

        //status mac dinh
        newReport.setStatus("new");
        //creat_by_macdinh
        newReport.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        newReport.setCreatedByUserId(user.getId());
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        newReport.setCreatedDate(new Date());
        if (des != null) {
            newReport.setDes(des);
        } else {
            newReport.setDes("không có nội dung");
        }
        if (title != null) {
            newReport.setTitle(title);
        } else {
            newReport.setTitle("không có tiêu đề");
        }
        newReport.setImgName(aPlus);
        reportRepository.save(newReport);
        System.out.println(des);
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
        }

        return ResponseEntity.ok("succes");
    }
    //nhan dât
    @GetMapping
    public ResponseEntity<?> getReportData(){
        List<ImgEntity> imgEntities = new ArrayList<>();
        for(Report report : reportRepository.findAll()){
            ImgEntity imgEntity = new ImgEntity();
            imgEntity.setStatus(report.getStatus());
            imgEntity.setId(report.getId());
            imgEntity.setDes(report.getDes());
            imgEntity.setTitle(report.getTitle());
            imgEntity.setCreatedDate(report.getCreatedDate());
            imgEntity.setCreatedBy(report.getCreatedBy());
            imgEntity.setCreatedByUserId(report.getCreatedByUserId());
            imgEntity.setImgNames(Arrays.asList(report.getImgName().split(",")));
            imgEntities.add(imgEntity);
        }

        return ResponseEntity.ok(imgEntities);
    }
    //edit data
    @PutMapping
    public ResponseEntity<?> editReportData(@RequestParam("status") String status,@RequestParam("id") long id) {
        Report editReport = reportRepository.findReportById(id);
        editReport.setStatus(status);
        reportRepository.save(editReport);

        return ResponseEntity.ok(editReport);
    }

    //xoa datas
    @PostMapping("/delete")
    public ResponseEntity<?> deleteReportData(@RequestParam("id") long id){

        Report report = reportRepository.findReportById(id);
        reportRepository.delete(report);

        return ResponseEntity.ok("xoa thanh cong");
    }
    //loc data(new,process,done)

    @GetMapping("/{name}")
    public ResponseEntity<?> filterData(@PathVariable("name") String name) {
    List<Report> reportList = reportRepository.reportDataList(name);
    return ResponseEntity.ok(reportList);
    }
}
