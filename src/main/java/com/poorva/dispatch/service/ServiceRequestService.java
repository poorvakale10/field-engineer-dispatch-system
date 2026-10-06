package com.poorva.dispatch.service;
import com.poorva.dispatch.model.ServiceRequest;
import com.poorva.dispatch.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceRequestService {
    private final ServiceRequestRepository repo;
    public ServiceRequestService(ServiceRequestRepository repo){this.repo=repo;}
    public List<ServiceRequest> all(){return repo.findAll();}
    public List<ServiceRequest> search(String q){
        if(q==null || q.isBlank()) return all();
        return repo.findByCustomerNameContainingIgnoreCaseOrLocationContainingIgnoreCaseOrStatusContainingIgnoreCase(q,q,q);
    }
    public ServiceRequest find(Long id){return repo.findById(id).orElseThrow();}
    public ServiceRequest create(ServiceRequest r){r.setStatus("CREATED"); return repo.save(r);}
    public ServiceRequest update(Long id, ServiceRequest f){
        ServiceRequest r=find(id);
        r.setCustomerName(f.getCustomerName()); r.setLocation(f.getLocation());
        r.setIssue(f.getIssue()); r.setPriority(f.getPriority());
        r.setEngineer(f.getEngineer()); r.setStatus(f.getStatus());
        return repo.save(r);
    }
    public long count(){return repo.count();}
    public long status(String s){return repo.findAll().stream().filter(r->s.equals(r.getStatus())).count();}
}
