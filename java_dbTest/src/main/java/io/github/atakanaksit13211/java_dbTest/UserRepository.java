package io.github.atakanaksit13211.java_dbTest;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Long> {

}
