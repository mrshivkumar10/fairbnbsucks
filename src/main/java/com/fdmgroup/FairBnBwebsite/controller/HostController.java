package com.fdmgroup.FairBnBwebsite.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.fdmgroup.FairBnBwebsite.model.ContactDetail;
import com.fdmgroup.FairBnBwebsite.model.Host;
import com.fdmgroup.FairBnBwebsite.service.ContactDetailService;
import com.fdmgroup.FairBnBwebsite.service.CustomerService;
import com.fdmgroup.FairBnBwebsite.service.HostService;

@Controller
public class HostController {
	
	private final HostService hostService;
	private final ContactDetailService contactDetailService;
	
	@Autowired
	public HostController(HostService hostService, ContactDetailService contactDetailService) {
		this.hostService = hostService;
		this.contactDetailService = contactDetailService;
	}
	
	@GetMapping("/hostindex")
	public String HostIndex(Model model){
	
		model.addAttribute("hostAttr", hostService.getAllHosts());
		return "index-hosts";
	}
	
	@GetMapping("/hostsignup")
	public String HostSignupForm(Model model) {
		Iterable<ContactDetail> contactDetails = contactDetailService.getAllContactDetails();
		model.addAttribute("contactDetailsAttr", contactDetails);
		model.addAttribute("hostAttr", hostService.createHost());
		return "add-host";
	}
	
	@PostMapping("/addhost")
	public String addContactDetail(@Valid Host host, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-host";
		}
		hostService.saveHost(host);
		model.addAttribute("hostAttr", hostService.getAllHosts());
		model.addAttribute("contactDetailsAttr", contactDetailService.getAllContactDetails());
		return "index-hosts";
	}
	
	@GetMapping("/edithost/{id}")
	public String HostUpdateForm(@PathVariable("id") int hostId, Model model) {
		Host host = hostService.getHostById(hostId);
		model.addAttribute("hostAttr", host);
		return "update-host";
	}
	
	@PostMapping("/updatehost/{id}")
	public String updateContactDetail(@PathVariable("id") int hostId,
			@Valid Host host, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "update-host";
		}
		
		host.setHostId(hostId);
		hostService.editHost(host);
		model.addAttribute("hostAttr", hostService.getAllHosts());
		return "index-hosts";
	}
	
	@GetMapping("/deletehost/{id}")
	public String deletePropertyType(@PathVariable("id") int hostId, Model model) {
		hostService.deleteHostById(hostId);
		model.addAttribute("hostAttr", hostService.getAllHosts());
		return "index-hosts";
	}
}
