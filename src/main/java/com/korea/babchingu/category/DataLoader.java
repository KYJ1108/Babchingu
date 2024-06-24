//package com.korea.babchingu.category;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//
//import java.util.List;
//
//public class DataLoader  implements CommandLineRunner {
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // 데이터베이스에 기존 데이터가 있는지 확인
//        List<Category> existingLocals = categoryRepository.findAll();
//
//        // 데이터가 없는 경우에만 새로운 데이터 추가
//        if (existingLocals.isEmpty()) {
//            categoryRepository.save(new Category("서울"));
//            categoryRepository.save(new Category("대전"));
//            categoryRepository.save(new Category("인천"));
//            categoryRepository.save(new Category("대구"));
//            categoryRepository.save(new Category("광주"));
//            categoryRepository.save(new Category("울산"));
//            categoryRepository.save(new Category("부산"));
//            categoryRepository.save(new Category("경기"));
//            categoryRepository.save(new Category("충남"));
//            categoryRepository.save(new Category("충북"));
//            categoryRepository.save(new Category("강원"));
//            categoryRepository.save(new Category("경남"));
//            categoryRepository.save(new Category("경북"));
//            categoryRepository.save(new Category("전북"));
//            categoryRepository.save(new Category("전남"));
//            categoryRepository.save(new Category("제주"));
//
//            categoryRepository.save(new Category("한식"));
//            categoryRepository.save(new Category("중식"));
//            categoryRepository.save(new Category("일식"));
//            categoryRepository.save(new Category("아시아"));
//            categoryRepository.save(new Category("양식"));
//            categoryRepository.save(new Category("디저트"));
//
//            categoryRepository.save(new Category("여"));
//            categoryRepository.save(new Category("남"));
//        }
//    }
//
//}