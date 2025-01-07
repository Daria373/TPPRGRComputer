package stu.cn.ua.production_computers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.cn.ua.production_computers.models.Components;
import stu.cn.ua.production_computers.repository.ComponentsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ComponentService {

    @Autowired
    private ComponentsRepository componentsRepository;

    public List<Components> getAllComponents() {
        return componentsRepository.findAll();
    }

    public Optional<Components> findComponentById(int id) {
        return componentsRepository.findById(id);
    }

    public void addComponent(Components component) {
        componentsRepository.save(component);
    }

    public void updateComponent(Components updatedComponent) {
        Components existingComponent = componentsRepository.findById(updatedComponent.getComponentId())
                .orElseThrow(() -> new IllegalArgumentException("Component not found"));

        existingComponent.setName(updatedComponent.getName());
        existingComponent.setPrice(updatedComponent.getPrice());
        existingComponent.setCategory(updatedComponent.getCategory());
        existingComponent.setOrderItems(updatedComponent.getOrderItems());

        componentsRepository.save(existingComponent);
    }

    public void deleteComponent(int id) {
        componentsRepository.deleteById(id);
    }
}
