package kz.iitu.spring_lab_01.controller;

import kz.iitu.spring_lab_01.service.CatalogService;
import org.springframework.web.bind.annotation.*;
import org.springframework.aop.support.AopUtils;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/lab4")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    // Эндпоинт 1: GET http://localhost:8080/api/lab4/item/1
    @GetMapping("/item/{id}")
    public String getItem(@PathVariable long id) {
        return catalogService.findById(id);
    }

    // Эндпоинт 2: GET http://localhost:8080/api/lab4/items?limit=5
    @GetMapping("/items")
    public List<String> getItems(@RequestParam(defaultValue = "10") int limit) {
        return catalogService.findAll(limit);
    }

    // Эндпоинт 3: DELETE http://localhost:8080/api/lab4/item/1
    @DeleteMapping("/item/{id}")
    public String deleteItem(@PathVariable long id) {
        return catalogService.remove(id);
    }

    @GetMapping("/proxy")
    public Map<String, String> proxyInfo() {
        return Map.of(
                "className",  catalogService.getClass().getName(),
                "superClass", catalogService.getClass().getSuperclass().getSimpleName(),
                "isAopProxy", String.valueOf(AopUtils.isAopProxy(catalogService)),
                "isCglib",    String.valueOf(AopUtils.isCglibProxy(catalogService))
        );
    }

    @GetMapping("/remove-twice/{id}")
    public String removeTwice(@PathVariable long id) {
        return catalogService.removeTwice(id);
    }
}