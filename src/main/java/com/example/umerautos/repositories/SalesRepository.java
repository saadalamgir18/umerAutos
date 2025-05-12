package com.example.umerautos.repositories;

import com.example.umerautos.entities.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SalesRepository extends JpaRepository<Sales, UUID> {


//    native query
//    @Query(value = """
//            SELECT
//                p.id,
//                p.name,
//                SUM(s.quantity_sold),
//                SUM(s.total_amount)
//            FROM sales s
//            JOIN products p ON s.product_id = p.id
//            WHERE DATE(s.created_at) = CURDATE()-1
//            GROUP BY p.id, p.name
//            """,
//            nativeQuery = true)
//    List<Object[]> findTodaySalesSummary();


    @Query("""
            SELECT
                p.id,
                p.name,
                SUM(s.quantitySold),
                SUM(s.totalAmount),
                SUM(s.totalAmount) - SUM(p.purchasePrice * s.quantitySold) AS profit
            FROM Sales s
            JOIN s.product p
            WHERE FUNCTION('DATE', s.createdAt) = CURRENT_DATE
            GROUP BY p.id, p.name
            """)
    List<Object[]> findTodaySalesSummary();

    @Query("""
            SELECT
                p.id,
                p.name,
                s.quantitySold,
                s.totalAmount,
                (s.totalAmount - p.purchasePrice) * s.quantitySold AS profit,
                s.id,
                s.createdAt
            FROM Sales s
            JOIN s.product p
            """)
    Page<Object[]> findAllSales(Pageable pageable);


    @Query("SELECT SUM(s.totalAmount) FROM Sales s WHERE FUNCTION('DATE', s.createdAt) = CURRENT_DATE ")
    double findTodayTotalSalesAmount();

    @Query("""
            SELECT
                p.id,
                p.name,
                SUM(s.quantitySold),
                SUM(s.totalAmount)
            FROM Sales s
            JOIN s.product p
            WHERE FUNCTION('DATE', s.createdAt) = :date
            GROUP BY p.id, p.name
            """)
    List<Object[]> findSalesSummaryByDate(@Param("date") LocalDate date);


    @Query("SELECT SUM(s.totalAmount) FROM Sales s WHERE s.createdAt BETWEEN :startOfMonth AND :today")
    double getMonthlyRevenue(@Param("startOfMonth") Timestamp startOfMonth, @Param("today") Timestamp today);


}
