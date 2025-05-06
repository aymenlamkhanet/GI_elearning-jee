package com.example.Gii.repository;



import com.example.Gii.entity.Cours;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CoursRepository extends MongoRepository<Cours, String> {
    List<Cours> findByNiveau(String niveau);
    List<Cours> findByModule(String module);
    List<Cours> findTop5ByOrderByIdDesc();
}
