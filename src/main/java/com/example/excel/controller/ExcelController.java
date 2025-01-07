package com.example.excel.controller;

import java.util.List;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.excel.entity.ExcelEntity;
import com.example.excel.service.ExcelService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/bbs")
public class ExcelController {
	private final ExcelService bbsService;

    // 생성자를 통한 의존성 주입
    public ExcelController(ExcelService bbsService) {
        this.bbsService = bbsService;
    }
    
 // 1. 모든 게시글 조회
    @GetMapping
    public List<ExcelEntity> getAllBbs() {
        return bbsService.findAll();
    }

    // 2. 특정 ID의 게시글 조회
    @GetMapping("/{id}")
    public ExcelEntity getBbsById(@PathVariable Long id) {
        return bbsService.findById(id);
    }

    // 3. 새 게시글 저장
    @PostMapping
    public ExcelEntity createBbs(@RequestBody ExcelEntity bbsEntity) {
	    	System.out.println("Title: " + bbsEntity.getTitle());
	        System.out.println("Content: " + bbsEntity.getContent());
	        System.out.println("Author: " + bbsEntity.getAuthor());
        return bbsService.save(bbsEntity);
    }

    // 4. 게시글 삭제
    @DeleteMapping("/{id}")
    public void deleteBbs(@PathVariable Long id) {
        bbsService.deleteById(id);
    }

    // 5. 제목에 특정 키워드가 포함된 게시글 검색
    @GetMapping("/search")
    public List<ExcelEntity> searchBbsByTitle(@RequestParam String keyword) {
        return bbsService.searchByTitleContaining(keyword);
    }
    
    // 6. 엑셀 다운로드 
    @GetMapping("/excel")
    public void excelDownload(HttpServletResponse response){
    	bbsService.excelDownload(response);
    }
    
    
    
}
