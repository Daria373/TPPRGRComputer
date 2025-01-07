
package stu.cn.ua.production_computers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import stu.cn.ua.production_computers.models.Components;
import stu.cn.ua.production_computers.service.ComponentService;

import java.util.List;

@Controller
@RequestMapping("/components")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    @GetMapping
    public String getAllComponents(Model model) {
        List<Components> components = componentService.getAllComponents();
        model.addAttribute("components", components);
        return "components";
    }

    @GetMapping("/add")
    public String showAddComponentForm(Model model) {
        model.addAttribute("component", new Components());
        return "add-component";
    }

    @PostMapping("/add")
    public String createComponent(@ModelAttribute("component") Components component) {
        componentService.addComponent(component);
        return "redirect:/components";
    }

    @GetMapping("/edit/{id}")
    public String showEditComponentForm(@PathVariable int id, Model model) {
        componentService.findComponentById(id).ifPresent(component -> model.addAttribute("component", component));
        return "edit-component";
    }


    @PostMapping("/update/{id}")
    public String updateComponent(@PathVariable int id, @ModelAttribute("component") Components updatedComponent) {
        updatedComponent.setComponentId(id);
        componentService.updateComponent(updatedComponent);
        return "redirect:/components";
    }

    @GetMapping("/delete/{id}")
    public String deleteComponent(@PathVariable int id) {
        componentService.deleteComponent(id);
        return "redirect:/components";
    }
}
