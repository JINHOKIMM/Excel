package com.example.excel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.excel.entity.ExcelEntity;

// BbsEntity와 연결된 Repository
public interface ExcelRepository extends JpaRepository<ExcelEntity, Long> {
    // JpaRepository에서 기본 CRUD 메서드 제공
	
	// 제목에 특정 키워드가 포함된 게시글 조회
    List<ExcelEntity> findByTitleContaining(String keyword);
 
    @Query(value = "SELECT column_name " +
		            "FROM information_schema.columns " +
		            "WHERE table_name = 'bbs'" +
		            "ORDER BY ordinal_position", //생성 순서대로 정렬
	            nativeQuery = true)
	List<String> getBbsListColNm();

   
}
