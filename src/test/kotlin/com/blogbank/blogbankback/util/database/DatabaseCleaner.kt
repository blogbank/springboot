package com.blogbank.blogbankback.util.database

import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DatabaseCleaner(
    private val entityManager: EntityManager
) {

    private lateinit var tableNames: List<String>

    @PostConstruct
    fun init() {
        // H2 데이터베이스의 모든 테이블 이름을 조회함
        val query = entityManager.createNativeQuery(
            "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'"
        )

        @Suppress("UNCHECKED_CAST")
        tableNames = query.resultList as List<String>
    }

    // 모든 테이블의 데이터를 삭제함
    @Transactional
    fun execute() {
        // 외래 키 무결성 제약 조건을 임시로 비활성화
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()

        // 모든 테이블의 데이터 삭제
        tableNames.forEach { tableName ->
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
        }

        // 외래 키 무결성 제약 조건을 다시 활성화
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()

        // EntityManager 캐시를 초기화하여 일관성을 보장함
        entityManager.flush()
        entityManager.clear()
    }

    // 특정 테이블만 삭제함 (필요시 사용)
    @Transactional
    fun executeForTable(tableName: String) {
        entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
        entityManager.flush()
        entityManager.clear()
    }
}