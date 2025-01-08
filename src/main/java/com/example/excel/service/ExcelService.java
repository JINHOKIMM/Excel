package com.example.excel.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        logger.info("bbsList : {}", bbsList);
        logger.info("bbsListColNm : {}", bbsListColNm);

        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            XSSFSheet sheet = wb.createSheet("BBS Data");
            int rowCnt = 0;

            // 헤더 작성
            Row headerRow = sheet.createRow(rowCnt++);
            for (int i = 0; i < bbsListColNm.size(); i++) {
                headerRow.createCell(i).setCellValue(bbsListColNm.get(i));
            }

            // 본문 작성
            for (ExcelEntity ent : bbsList) {
                Row bodyRow = sheet.createRow(rowCnt++);
                for (int i = 0; i < bbsListColNm.size(); i++) {
                    String colName = bbsListColNm.get(i);
                    Object value = getValueByFieldName(ent, colName);
                    bodyRow.createCell(i).setCellValue(value != null ? value.toString() : "N/A");
                }
            }

            // 응답 설정
            String fileName = "bbs_data_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            wb.write(response.getOutputStream());
        } catch (Exception e) {
            logger.error("Error during Excel download", e);
            throw new RuntimeException("Excel download failed", e);
        }
    }

    private Object getValueByFieldName(ExcelEntity entity, String fieldName) {
        try {
            // 스네이크 케이스를 카멜 케이스로 변환
            String camelCaseField = snakeToCamel(fieldName);
            Field field = ExcelEntity.class.getDeclaredField(camelCaseField);
            field.setAccessible(true);
            Object value = field.get(entity);

            // 날짜 형식 처리
            if (value instanceof LocalDateTime) {
                return ((LocalDateTime) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
            return value;
        } catch (Exception e) {
            logger.error("Error accessing field: {}", fieldName, e);
            return "N/A";
        }
    }
    
    private String snakeToCamel(String fieldName) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = false;
        for (char c : fieldName.toCharArray()) {
            if (c == '_') {
                capitalizeNext = true;
            } else {
                result.append(capitalizeNext ? Character.toUpperCase(c) : c);
                capitalizeNext = false;
            }
        }
        return result.toString();
    }

}
