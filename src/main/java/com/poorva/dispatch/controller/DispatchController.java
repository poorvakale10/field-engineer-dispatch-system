package com.poorva.dispatch.controller;
import com.poorva.dispatch.model.ServiceRequest;
import com.poorva.dispatch.service.ServiceRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class DispatchController {
    private final ServiceRequestService service;
    public DispatchController(ServiceRequestService service){this.service=service;}

    @GetMapping("/") public String dashboard(Model m){
        m.addAttribute("requests",service.all().stream().limit(6).toList());
        m.addAttribute("total",service.count()); m.addAttribute("created",service.status("CREATED"));
        m.addAttribute("assigned",service.status("ASSIGNED")); m.addAttribute("progress",service.status("IN_PROGRESS"));
        m.addAttribute("completed",service.status("COMPLETED")); return "dashboard";
    }
    @GetMapping("/login") public String login(){return "login";}
    @GetMapping("/requests") public String requests(@RequestParam(required=false) String q,Model m){
        m.addAttribute("requests",service.search(q)); m.addAttribute("q",q==null?"":q); return "requests";
    }
    @GetMapping("/requests/new") public String form(Model m){
        ServiceRequest r=new ServiceRequest(); r.setPriority("MEDIUM"); r.setStatus("CREATED"); m.addAttribute("request",r); return "request-form";
    }
    @PostMapping("/requests") public String create(@ModelAttribute ServiceRequest r){service.create(r);return "redirect:/requests";}
    @GetMapping("/requests/{id}/edit") public String edit(@PathVariable Long id,Model m){m.addAttribute("request",service.find(id));return "request-form";}
    @PostMapping("/requests/{id}") public String update(@PathVariable Long id,@ModelAttribute ServiceRequest r){service.update(id,r);return "redirect:/requests";}
    @GetMapping("/api/requests") @ResponseBody public List<ServiceRequest> api(){return service.all();}
    @GetMapping("/health") @ResponseBody public String health(){return "UP";}
}
