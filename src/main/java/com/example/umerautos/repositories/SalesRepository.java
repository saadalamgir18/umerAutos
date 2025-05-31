package com.example.umerautos.repositories;

import com.example.umerautos.entities.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SalesRepository extends JpaRepository<Sales, UUID> {

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
            and paymentStatus = 'Paid'
            GROUP BY p.id, p.name
            """)
    List<Object[]> findTodaySalesSummary(Pageable pageable);

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


    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM Sales s WHERE FUNCTION('DATE', s.createdAt) = CURRENT_DATE and s.paymentStatus = 'PAID' ")
    int findTodayTotalSalesAmount();

    @Query("""
            SELECT
                p.id,
                p.name,
                SUM(s.quantitySold),
               COALESCE(SUM(s.totalAmount), 0)
            FROM Sales s
            JOIN s.product p
            WHERE FUNCTION('DATE', s.createdAt) = :date
            GROUP BY p.id, p.name
            """)
    List<Object[]> findSalesSummaryByDate(@Param("date") LocalDate date);


    @Query("SELECT SUM(s.totalAmount) FROM Sales s WHERE s.createdAt BETWEEN :startOfMonth AND :today and s.paymentStatus = 'PAID'")
    int getMonthlyRevenue(@Param("startOfMonth") Timestamp startOfMonth, @Param("today") Timestamp today);


}
