package com.helper.dnd.controller;

import com.helper.dnd.dbutil.EntityFieldChecker;
import com.helper.dnd.dbutil.EntitySpecifications;
import com.helper.dnd.model.Monster;
import com.helper.dnd.repository.MonsterRepository;
import com.helper.dnd.service.MonsterFullDataLoadService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MonsterController {

  @Autowired private MonsterRepository monsterRepository;
  @Autowired private MonsterFullDataLoadService service;

  @GetMapping("/fetch-and-save")
  public ResponseEntity<String> fetchDataAndSave() {
    try {
      service.fetchDataAndSave();
      return ResponseEntity.ok("Data fetched and saved successfully");
    } catch (Exception e) {
      return ResponseEntity.of(Optional.of("Error occurred while fetching and saving data: " + e));
    }
  }

  @GetMapping("/monsters")
  public List<Monster> getMonsters(@RequestParam Map<String, String> queryParams) {

    if (!EntityFieldChecker.areQueryParamsSupported(Monster.class, queryParams)) {
      return null;
    }

    Specification<Monster> specification =
        EntitySpecifications.buildSpecification(Monster.class, queryParams);
    return monsterRepository.findAll(specification);
  }
}
