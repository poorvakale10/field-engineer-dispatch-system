package com.poorva.dispatch.repository;
import com.poorva.dispatch.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest,Long> {
    List<ServiceRequest> findByCustomerNameContainingIgnoreCaseOrLocationContainingIgnoreCaseOrStatusContainingIgnoreCase(
        String customerName, String location, String status);
}
