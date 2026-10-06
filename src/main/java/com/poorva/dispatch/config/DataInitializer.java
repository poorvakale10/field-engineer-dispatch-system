package com.poorva.dispatch.config;
import com.poorva.dispatch.model.ServiceRequest;
import com.poorva.dispatch.repository.ServiceRequestRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
 @Bean CommandLineRunner seed(ServiceRequestRepository repo){return args->{
   if(repo.count()>0)return;
   save(repo,"Aarav Mehta","Mumbai","Internet connectivity failure","HIGH","Amit Patil","ASSIGNED");
   save(repo,"Riya Shah","Thane","Router replacement","MEDIUM","Neha Joshi","IN_PROGRESS");
   save(repo,"Kabir Desai","Pune","Signal quality issue","LOW","Rahul More","COMPLETED");
   save(repo,"Ananya Rao","Navi Mumbai","New connection installation","HIGH","","CREATED");
 };}
 private void save(ServiceRequestRepository r,String c,String l,String i,String p,String e,String s){
   ServiceRequest x=new ServiceRequest(); x.setCustomerName(c);x.setLocation(l);x.setIssue(i);x.setPriority(p);x.setEngineer(e);x.setStatus(s);r.save(x);
 }
}
