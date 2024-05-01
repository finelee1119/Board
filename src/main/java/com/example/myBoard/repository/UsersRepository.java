package com.example.myBoard.repository;

import com.example.myBoard.constant.Gender;
import com.example.myBoard.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UsersRepository extends JpaRepository<Users,Long> {
    //문제 1. 여성의 이름 중 "w"또는 "m"을 포함하는 자료를 검색하시오.
    List<Users> findByGenderAndNameContains(Gender gender, String name);

    List<Users> findByNameLikeAndGenderOrNameLikeAndGender(String str1, Gender gender1, String str2, Gender gender2);

    //문제 2. 이메일에 net을 포함하는 데이터 건수를 출력하시오.
    List<Users> findByEmailContains(String email);

    //문제 3. 가장 최근 한달이내에 업데이트된 자료 중 이름 첫자가 "J"인 자료를 출력하시오.
    List<Users> findByCreatedAtBetweenAndNameStartingWith(LocalDateTime startDate, LocalDateTime endDate, String name);

    //문제 4. 가장 최근 생성된 자료 10건을 ID, 이름, 성별, 생성일 만 출력하시오.
    List<Users> findFirst10ByOrderByCreatedAtDesc();

    //문제 5. "Red"를 좋아하는 / 남성 / 이메일 계정 중 / 사이트를 제외한 계정만 출력하시오.
    //(예, apenley2@tripod.com  → apenley2)
    List<Users> findEmailByGenderAndLikeColor(Gender gender, String color);

    //문제 6. 갱신일이 생성일 이전인 잘못된 데이터를 출력하시오.
    List<Users> findByUpdatedAtBefore(LocalDateTime now);

    //문제 7. 이메일에 edu를 갖는 여성 데이터를 가장 최근 데이터부터 보이도록 출력하시오.
    List<Users> findByGenderAndEmailContainsOrderByCreatedAtDesc(Gender gender, String email);

    //문제 8. 좋아하는 색상별로 오름차순 정렬하고 같은 색상 데이터는 이름의 내림차순으로 출력하시오.
    List<Users> findByOrderByLikeColorAscNameDesc();

    //문제 9. 전체 자료를 가장 최근 입력한 자료 순으로 정렬 및 페이징 처리하고
    //한 페이지당 10건씩 출력하되,
    //그 중 1번째 페이지를 출력하시오.
    Page<Users> findByOrderByCreatedAtDesc(Pageable paging);

    //문제10. 남성 자료를
    //ID의 내림차순으로 정렬한 후
    //한페이지당 3건을 출력하되
    //그 중 2번째 페이지 자료를 출력하시오.
    Page<Users> findByGenderOrderByIdDesc(Gender gender, Pageable paging);

    //문제11. 지난달의 모든 자료를 검색하여 출력하시오.
    List<Users> findByCreatedAtBetweenOrderByCreatedAtAsc(LocalDateTime startDate, LocalDateTime endDate);
};
