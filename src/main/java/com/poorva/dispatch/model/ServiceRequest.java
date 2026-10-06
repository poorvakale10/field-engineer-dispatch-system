package com.poorva.dispatch.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="service_requests")
public class ServiceRequest {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private String customerName;
    @Column(nullable=false) private String location;
    @Column(nullable=false, length=500) private String issue;
    @Column(nullable=false) private String priority;
    private String engineer;
    @Column(nullable=false) private String status;
    private LocalDateTime createdAt = LocalDateTime.now();

    public ServiceRequest() {}
    public Long getId(){return id;}
    public String getCustomerName(){return customerName;}
    public String getLocation(){return location;}
    public String getIssue(){return issue;}
    public String getPriority(){return priority;}
    public String getEngineer(){return engineer;}
    public String getStatus(){return status;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setId(Long v){id=v;}
    public void setCustomerName(String v){customerName=v;}
    public void setLocation(String v){location=v;}
    public void setIssue(String v){issue=v;}
    public void setPriority(String v){priority=v;}
    public void setEngineer(String v){engineer=v;}
    public void setStatus(String v){status=v;}
    public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
