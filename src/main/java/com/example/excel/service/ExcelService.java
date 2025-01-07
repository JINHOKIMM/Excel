package com.example.excel.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.excel.entity.ExcelEntity;
import com.example.excel.repository.ExcelRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExcelService {
	private final ExcelRepository bbsRepository;
	Logger logger = LoggerFactory.getLogger(getClass());
	
    // 생성자를 통해 Repository 주입 (의존성 주입)
    public ExcelService(ExcelRepository bbsRepository) {
        this.bbsRepository = bbsRepository;
    }
    
    // 모든 게시글 조회
    public List<ExcelEntity> findAll() {
        return bbsRepository.findAll();
    }

    // 게시글 저장
    public ExcelEntity save(ExcelEntity bbsEntity) {
        return bbsRepository.save(bbsEntity);
    }

    // ID로 게시글 조회
    public ExcelEntity findById(Long id) {
        return bbsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }

    // ID로 게시글 삭제
    public void deleteById(Long id) {
        bbsRepository.deleteById(id);
    }

    // 제목에 특정 키워드가 포함된 게시글 조회
    public List<ExcelEntity> searchByTitleContaining(String keyword) {
        return bbsRepository.findByTitleContaining(keyword);
    }

	public void excelDownload(HttpServletResponse response) {
		
		List<ExcelEntity> bbsList = bbsRepository.findAll();
		List<String> bbsListColNm = bbsRepository.getBbsListColNm();
		
		logger.info("bbsList : {}",bbsList);
		logger.info("bbsListColNm : {}",bbsListColNm);
				
        XSSFWorkbook wb = new XSSFWorkbook();
        
        XSSFSheet sheet =  wb.createSheet();
        
        int rowCnt = 0;
        
        Row headerRow = sheet.createRow(rowCnt++);
        for (int i = 0; i < bbsListColNm.size(); i++) {
            headerRow.createCell(i).setCellValue(bbsListColNm.get(i));
        }
        
        for (ExcelEntity ent : bbsList) {
			Row bodyRow = sheet.createRow(rowCnt++);
			bodyRow.createCell(0).setCellValue(ent.getId());
			bodyRow.createCell(1).setCellValue(ent.getTitle());
			bodyRow.createCell(2).setCellValue(ent.getContent());
			bodyRow.createCell(3).setCellValue(ent.getAuthor());
			bodyRow.createCell(4).setCellValue(ent.getCreatedAt().toString()); // or format it
			bodyRow.createCell(5).setCellValue(ent.getUpdatedAt().toString());
		}
        
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
        
        try {
			wb.write(response.getOutputStream());
			wb.close();
		} catch (Exception e) {
			logger.error("Error during Excel download", e);
		    throw new RuntimeException("Excel download failed");
		}

		
	}
}
