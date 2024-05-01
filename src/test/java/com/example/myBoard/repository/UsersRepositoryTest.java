package com.example.myBoard.repository;

import com.example.myBoard.constant.Gender;
import com.example.myBoard.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class UsersRepositoryTest {
    @Autowired
    UsersRepository usersRepository;

    //문제 1. 여성의 이름 중 "w"또는 "m"을 포함하는 자료를 검색하시오.
    @Test
    void findByGenderAndNameContains() {
        usersRepository.findByGenderAndNameContains(Gender.Female, "w")
                .forEach(users -> System.out.println(users));

        usersRepository.findByGenderAndNameContains(Gender.Female, "m")
                .forEach(users -> System.out.println(users));
    }

    @Test
    void 문제1() {
        usersRepository.findByNameLikeAndGenderOrNameLikeAndGender("%w%", Gender.Female, "%m%", Gender.Female)
                .forEach(users -> System.out.println(users));
    }

    //문제 2. 이메일에 net을 포함하는 데이터 건수를 출력하시오.
    @Test
    void findByEmailContains() {
        System.out.println(usersRepository.findByEmailContains("net").stream().count());
    }

    //문제 3. 가장 최근 한달이내에 업데이트된 자료 중 이름 첫자가 "J"인 자료를 출력하시오.
    @Test
    void findByCreatedAtBetweenAndNameStartingWith() {
        usersRepository.findByCreatedAtBetweenAndNameStartingWith(LocalDateTime.now().minusDays(31L), LocalDateTime.now(), "J")
                .forEach(users -> System.out.println(users));
    }

    //문제 4. 가장 최근 생성된 자료 10건을 ID, 이름, 성별, 생성일 만 출력하시오.
    @Test
    void findFirst10ByOrderByCreatedAtDesc() {
        List<Users> usersList = usersRepository.findFirst10ByOrderByCreatedAtDesc();
        for (Users user : usersList) {
            System.out.println("ID: " + user.getId() +
                    ", 이름: " + user.getName() +
                    ", 성별: " + user.getGender() +
                    ", 생성일: " + user.getCreatedAt());
        }
    }

    //문제 5. "Red"를 좋아하는 / 남성 / 이메일 계정 중 / 사이트를 제외한 계정만 출력하시오.
    //(예, apenley2@tripod.com  → apenley2)
    @Test
    void findEmailByGenderAndLikeColor() {
        List<Users> usersList = usersRepository.findEmailByGenderAndLikeColor(Gender.Male, "Red");
        for (Users user : usersList) {
            String email = user.getEmail();
            String username = email.substring(0, email.indexOf('@'));
            System.out.println("계정: " + username);
        }
    }

    //문제 6. 갱신일이 생성일 이전인 잘못된 데이터를 출력하시오.
    @Test
    void findByUpdatedAtBefore() {
        LocalDateTime referenceDateTime = LocalDateTime.now(); // 예시로 현재 시간을 기준으로 설정

        List<Users> usersList = usersRepository.findByUpdatedAtBefore(referenceDateTime);
        for (Users user : usersList) {
            LocalDateTime updatedAt = user.getUpdatedAt();
            LocalDateTime createdAt = user.getCreatedAt();
            if (updatedAt.isBefore(createdAt)) {
                System.out.println(user);
            }
        }
    }

    @Test
    void 문제6() {
        List<Users> users = usersRepository.findAll();
        for(Users user : users) {
            if(user.getUpdatedAt().isBefore(user.getCreatedAt())) {
                System.out.println(user);
            }
        }
    }

    //문제 7. 이메일에 edu를 갖는 여성 데이터를 가장 최근 데이터부터 보이도록 출력하시오.
    @Test
    void findByGenderAndEmailContainsOrderByCreatedAtDesc() {
        usersRepository.findByGenderAndEmailContainsOrderByCreatedAtDesc(Gender.Female, "edu")
                .forEach(users -> System.out.println(users));
    }

    //문제 8. 좋아하는 색상별로 오름차순 정렬하고 같은 색상 데이터는 이름의 내림차순으로 출력하시오.
    @Test
    void findByOrderByLikeColorAscNameDesc() {
        usersRepository.findByOrderByLikeColorAscNameDesc()
                .forEach(users -> System.out.println(users));
    }

    //문제 9. 전체 자료를 가장 최근 입력한 자료 순으로 정렬 및 페이징 처리하고
    //한 페이지당 10건씩 출력하되,
    //그 중 1번째 페이지를 출력하시오.
    @Test
    void findByOrderByCreatedAtDesc() {
        Pageable pageable = PageRequest.of(0,10);
        Page<Users> result = usersRepository
                .findByOrderByCreatedAtDesc(pageable);
        result.getContent().forEach(user -> System.out.println(user));
        //총 페이지 수
        System.out.println("Total Pages : " + result.getTotalPages());
        //현재 페이지 번호(0부터 시작)
        System.out.println("Page Number : " + result.getNumber());
        //페이지 당 데이터 개수
        System.out.println("Page Size : " + result.getSize());
    }

    //문제10. 남성 자료를
    //ID의 내림차순으로 정렬한 후
    //한페이지당 3건을 출력하되
    //그 중 2번째 페이지 자료를 출력하시오.
    @Test
    void findByGenderOrderByIdDesc() {
        Pageable pageable = PageRequest.of(1, 3);
        Page<Users> result = usersRepository
                .findByGenderOrderByIdDesc(Gender.Male, pageable);
        result.getContent().forEach(user -> System.out.println(user));
        //총 페이지 수
        System.out.println("Total Pages : " + result.getTotalPages());
        //현재 페이지 번호(0부터 시작)
        System.out.println("Page Number : " + result.getNumber());
        //페이지 당 데이터 개수
        System.out.println("Page Size : " + result.getSize());
    }

    //문제11. 지난달의 모든 자료를 검색하여 출력하시오.
    @Test
    void findByCreatedAtBetweenOrderByCreatedAtAsc() {
        LocalDate baseDate = LocalDate.now(); // 현재 날짜
        YearMonth lastMonth = YearMonth.from(baseDate.minusMonths(1)); // 지난 달
        LocalDateTime startOfMonth = lastMonth.atDay(1).atStartOfDay(); // 지난 달의 시작일
        LocalDateTime endOfMonth = lastMonth.atEndOfMonth().atTime(23, 59, 59); // 지난 달의 마지막일

        List<Users> usersList = usersRepository.findByCreatedAtBetweenOrderByCreatedAtAsc(startOfMonth, endOfMonth);
        usersList.forEach(users -> System.out.println(users));
    }

}